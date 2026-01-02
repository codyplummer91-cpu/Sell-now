# API Documentation

This document provides detailed information about the APIs and data sources used by the Sell Now plugin.

## OSRS Wiki Price API

The primary data source for the Sell Now plugin is the OSRS Wiki Real-time Price API.

### Base URL
```
https://prices.runescape.wiki/api/v1/osrs/
```

### Authentication
- **Required**: User-Agent header
- **Format**: `User-Agent: RuneLite Sell-Now Plugin`
- **Rate Limits**: ~600 requests per minute (generous for normal use)

### Endpoints

#### 1. Latest Prices

**Endpoint**: `/latest`

**Method**: GET

**Description**: Returns the latest high and low prices for all tradeable items.

**Request**:
```http
GET https://prices.runescape.wiki/api/v1/osrs/latest
User-Agent: RuneLite Sell-Now Plugin
```

**Response**:
```json
{
  "data": {
    "2": {
      "high": 175,
      "highTime": 1641234567,
      "low": 171,
      "lowTime": 1641234560
    },
    "4": {
      "high": 85,
      "highTime": 1641234567,
      "low": 82,
      "lowTime": 1641234561
    }
  }
}
```

**Response Fields**:
- `data`: Object containing item data, keyed by item ID
- `high`: Current high price (instant sell price)
- `highTime`: Unix timestamp of last high price update
- `low`: Current low price (instant buy price)
- `lowTime`: Unix timestamp of last low price update

**Notes**:
- Prices are in GP (gold pieces)
- Timestamps are Unix epoch (seconds since 1970-01-01)
- Not all items have both high and low prices
- Some items may be missing if no recent trades

#### 2. Item Mapping

**Endpoint**: `/mapping`

**Method**: GET

**Description**: Returns a complete mapping of item IDs to item information.

**Request**:
```http
GET https://prices.runescape.wiki/api/v1/osrs/mapping
User-Agent: RuneLite Sell-Now Plugin
```

**Response**:
```json
[
  {
    "id": 2,
    "name": "Cannonball",
    "examine": "Ammo for the Dwarf Cannon.",
    "members": true,
    "lowalch": 2,
    "limit": 11000,
    "value": 5,
    "highalch": 3,
    "icon": "iVBORw0KG..."
  },
  {
    "id": 4,
    "name": "Bronze med helm",
    "examine": "A medium sized helmet made of bronze.",
    "members": false,
    "lowalch": 4,
    "limit": 125,
    "value": 11,
    "highalch": 6,
    "icon": "iVBORw0KG..."
  }
]
```

**Response Fields**:
- `id`: Item ID (integer)
- `name`: Item name (string)
- `examine`: Examine text (string)
- `members`: Members-only item (boolean)
- `lowalch`: Low alchemy value (integer)
- `limit`: GE buy limit per 4 hours (integer)
- `value`: Item value in shops (integer)
- `highalch`: High alchemy value (integer)
- `icon`: Base64-encoded icon image (string)

#### 3. Time Series Data (Not Currently Used)

**Endpoint**: `/timeseries`

**Method**: GET

**Description**: Historical price data for items.

**Parameters**:
- `timestep`: Time interval (5m, 1h, 6h)
- `id`: Item ID(s) to retrieve

**Example**:
```
GET /timeseries?timestep=5m&id=2
```

**Note**: This endpoint could be used in future versions for historical analysis.

#### 4. 5-Minute Averages (Not Currently Used)

**Endpoint**: `/5m`

**Method**: GET

**Description**: 5-minute average prices for all items.

**Response Structure**: Similar to `/latest`

**Use Case**: Could be used for more stable price tracking.

## Implementation Details

### Plugin API Usage

#### Price Fetching

```java
// Fetch latest prices
Request request = new Request.Builder()
    .url("https://prices.runescape.wiki/api/v1/osrs/latest")
    .header("User-Agent", "RuneLite Sell-Now Plugin")
    .build();

Response response = httpClient.newCall(request).execute();
String responseBody = response.body().string();
JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
JsonObject data = json.getAsJsonObject("data");
```

#### Data Processing

```java
// Process each item
data.entrySet().forEach(entry -> {
    int itemId = Integer.parseInt(entry.getKey());
    JsonObject priceData = entry.getValue().getAsJsonObject();
    
    // Prefer high price (instant sell)
    long price = priceData.has("high") 
        ? priceData.get("high").getAsLong()
        : priceData.get("low").getAsLong();
    
    // Update cache
    updateItemPrice(itemId, price);
});
```

### Caching Strategy

The plugin implements a multi-level caching system:

1. **In-Memory Cache**: ConcurrentHashMap for thread-safe access
2. **Update Interval**: 5 minutes default (configurable)
3. **Cache Invalidation**: Manual clear or plugin restart

```java
private final Map<Integer, ItemPriceData> priceCache = new ConcurrentHashMap<>();
```

### Error Handling

```java
try {
    // API call
} catch (IOException e) {
    log.error("Error fetching prices from OSRS Wiki", e);
    // Continue with cached data
}
```

## Rate Limiting

### OSRS Wiki API Limits

- **Requests per minute**: ~600 (very generous)
- **Recommended interval**: 5+ minutes between full updates
- **Burst handling**: API is tolerant of occasional bursts

### Plugin Rate Limiting

```java
private static final long UPDATE_INTERVAL_MINUTES = 5;

executor.scheduleAtFixedRate(
    this::updateAllPrices,
    UPDATE_INTERVAL_MINUTES,
    UPDATE_INTERVAL_MINUTES,
    TimeUnit.MINUTES
);
```

## Alternative APIs (Future Support)

### GE Tracker API

**Base URL**: `https://www.ge-tracker.com/api/`

**Endpoints**:
- `/items/latest`: Latest prices
- `/items/{id}`: Individual item data

**Note**: Requires API key (paid service)

### Official RuneScape API

**Base URL**: `https://secure.runescape.com/m=itemdb_oldschool/`

**Endpoints**:
- `/api/catalogue/detail.json?item={id}`: Item details
- `/api/graph/{id}.json`: Price graph data

**Limitations**:
- Not real-time (updates once daily)
- No bulk endpoint
- Rate limits are strict

## Data Models

### ItemPriceData

```java
public class ItemPriceData {
    private final int itemId;           // Unique item identifier
    private final String itemName;       // Display name
    private long currentPrice;          // Current market price
    private long allTimeHigh;           // Highest price seen
    private long lastUpdated;           // Last update timestamp
}
```

### Price Tier Calculation

```java
public int getColorTier() {
    if (currentPrice < 10_000) return 0;      // Gray
    if (currentPrice < 100_000) return 1;     // White
    if (currentPrice < 1_000_000) return 2;   // Green
    if (currentPrice < 10_000_000) return 3;  // Blue
    if (currentPrice < 100_000_000) return 4; // Purple
    if (currentPrice < 1_000_000_000) return 5; // Orange
    return 6;                                  // Red (1b+)
}
```

## Testing API Calls

### Using cURL

```bash
# Test latest prices endpoint
curl -H "User-Agent: RuneLite Sell-Now Plugin" \
  https://prices.runescape.wiki/api/v1/osrs/latest

# Test mapping endpoint
curl -H "User-Agent: RuneLite Sell-Now Plugin" \
  https://prices.runescape.wiki/api/v1/osrs/mapping
```

### Using Postman

1. Create new GET request
2. Set URL to endpoint
3. Add header: `User-Agent: RuneLite Sell-Now Plugin`
4. Send request
5. Inspect response

### Using Browser Developer Tools

```javascript
// Fetch latest prices
fetch('https://prices.runescape.wiki/api/v1/osrs/latest', {
  headers: {
    'User-Agent': 'RuneLite Sell-Now Plugin'
  }
})
.then(response => response.json())
.then(data => console.log(data));
```

## API Best Practices

### Do's
- ✓ Include User-Agent header
- ✓ Cache responses appropriately
- ✓ Handle errors gracefully
- ✓ Respect rate limits
- ✓ Use appropriate update intervals

### Don'ts
- ✗ Spam the API with frequent requests
- ✗ Omit User-Agent header
- ✗ Make requests in tight loops
- ✗ Store and redistribute API data long-term
- ✗ Use for commercial purposes without permission

## Troubleshooting

### Common Issues

**1. 403 Forbidden**
- **Cause**: Missing User-Agent header
- **Solution**: Add proper User-Agent header

**2. 429 Too Many Requests**
- **Cause**: Rate limit exceeded
- **Solution**: Increase update interval

**3. Connection Timeout**
- **Cause**: Network issues or API downtime
- **Solution**: Implement retry logic with exponential backoff

**4. Empty Response**
- **Cause**: Item not traded recently
- **Solution**: Handle null/missing prices gracefully

### Error Codes

| Code | Meaning | Solution |
|------|---------|----------|
| 200 | Success | Process normally |
| 403 | Forbidden | Check User-Agent |
| 404 | Not Found | Verify endpoint URL |
| 429 | Rate Limited | Reduce request frequency |
| 500 | Server Error | Retry with backoff |
| 503 | Service Unavailable | API maintenance, retry later |

## API Changes and Versioning

### API Version: v1

The OSRS Wiki API is currently at version 1 (v1). Changes are expected to be backwards compatible.

### Monitoring Changes

- Check OSRS Wiki Discord for announcements
- Monitor GitHub issues for reported problems
- Subscribe to API status updates

### Deprecation Policy

The OSRS Wiki team typically provides:
- Advance notice of breaking changes
- Migration guides for new versions
- Backwards compatibility period

## Resources

### Documentation
- OSRS Wiki API Docs: https://oldschool.runescape.wiki/w/RuneScape:Real-time_Prices
- RuneLite Plugin Guide: https://github.com/runelite/runelite/wiki/Building-with-Gradle

### Support
- OSRS Wiki Discord: https://discord.gg/osrswiki
- RuneLite Discord: https://discord.gg/runelite

### Tools
- JSON Viewer: https://jsonviewer.stack.hu/
- API Testing: Postman, Insomnia
- Rate Limit Testing: Apache JMeter

## Contributing

If you want to add support for additional APIs or improve the existing integration:

1. Review the API documentation
2. Implement the API client
3. Add error handling
4. Write tests
5. Submit a pull request

See CONTRIBUTING.md for detailed guidelines.
