# Sell Now Plugin - Technical Documentation

## Overview

The Sell Now plugin is a RuneLite plugin designed to help OSRS players identify when items in their inventory, bank, or Grand Exchange are at their all-time high prices. This helps players make informed decisions about when to sell their items for maximum profit.

## Architecture

### Core Components

1. **SellNowPlugin** - Main plugin class that manages lifecycle
   - Registers overlay
   - Initializes price service
   - Handles game state events

2. **SellNowConfig** - Configuration interface
   - Defines user-configurable settings
   - Color customization for price tiers
   - Toggle features on/off

3. **GEPriceService** - Price data management
   - Fetches prices from OSRS Wiki API
   - Maintains price cache
   - Tracks all-time high prices
   - Updates prices periodically

4. **ItemPriceData** - Data model
   - Stores current price
   - Tracks all-time high
   - Calculates color tier
   - Determines if at ATH

5. **SellNowOverlay** - Visual rendering
   - Draws highlights around items
   - Shows tooltips with price info
   - Handles multiple interfaces (inventory, bank, GE)

## Price Tier System

Items are color-coded based on their current price:

| Price Range | Color | Tier |
|-------------|-------|------|
| < 10k gp | Gray | 0 |
| 10k - 100k gp | White | 1 |
| 100k - 1m gp | Green | 2 |
| 1m - 10m gp | Blue | 3 |
| 10m - 100m gp | Purple | 4 |
| 100m - 1b gp | Orange | 5 |
| >= 1b gp | Red | 6 |

## API Integration

### OSRS Wiki Price API

The plugin uses the OSRS Wiki Price API as its primary data source:

- **Latest Prices**: `https://prices.runescape.wiki/api/v1/osrs/latest`
  - Returns current high/low prices for all tradeable items
  - Updated every 5 minutes from real-time GE data
  
- **Item Mapping**: `https://prices.runescape.wiki/api/v1/osrs/mapping`
  - Provides item ID to name mappings
  - Used for tooltip display

### API Response Format

**Latest Prices Response:**
```json
{
  "data": {
    "2": {
      "high": 175,
      "highTime": 1641234567,
      "low": 171,
      "lowTime": 1641234560
    }
  }
}
```

**Mapping Response:**
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
  }
]
```

## How It Works

1. **Initialization**
   - Plugin starts and registers overlay
   - Price service loads item name mappings
   - Initial price fetch from OSRS Wiki

2. **Price Tracking**
   - Prices are fetched every 5 minutes (configurable)
   - Each item's current price is compared to its historical high
   - If current >= historical high, the item is marked as "at ATH"
   - Historical highs persist during the plugin session

3. **Visual Highlighting**
   - Overlay renders on each frame
   - Scans inventory, bank, and GE interface widgets
   - For each item at ATH, draws a colored border
   - Border color determined by price tier
   - Thickness is configurable

4. **Tooltips**
   - When hovering over a highlighted item
   - Shows item name, current price, and ATH price
   - Displays "AT ALL-TIME HIGH!" message

## Widget IDs

The plugin monitors these RuneLite widget IDs:

- **Inventory**: Widget 149, Component 0
- **Bank**: Widget 12, Component 12
- **GE Inventory**: Widget 465, Component 7

## Performance Considerations

- Price updates are rate-limited to prevent API abuse
- Uses concurrent hash maps for thread-safe caching
- Only renders when interfaces are visible
- Minimal CPU overhead per frame

## Future Enhancements

Potential improvements for future versions:

1. **Multiple API Sources**
   - Add support for GE Tracker API
   - Official RuneScape item database
   - Fallback sources if primary is unavailable

2. **Advanced Features**
   - Price alerts/notifications
   - Historical price graphs
   - Profit calculation
   - Market trend indicators
   - Export price history

3. **Performance**
   - Persistent storage of ATH data
   - Smart caching strategies
   - Background worker threads

4. **UI Improvements**
   - Customizable highlight styles
   - Mini price display on items
   - Detailed price panel

## Dependencies

- **RuneLite Client**: Core client API
- **Lombok**: Reduces boilerplate code
- **OkHttp**: HTTP client (provided by RuneLite)
- **Gson**: JSON parsing (provided by RuneLite)

## Testing

Since this is a RuneLite plugin, testing involves:

1. **Manual Testing**
   - Run plugin in RuneLite development environment
   - Check inventory with various items
   - Verify highlights appear correctly
   - Test configuration changes

2. **API Testing**
   - Verify API endpoints are accessible
   - Check response parsing
   - Handle API errors gracefully

3. **Performance Testing**
   - Monitor CPU usage
   - Check memory consumption
   - Verify no lag in game rendering

## Contributing

To contribute to this plugin:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly in RuneLite
5. Submit a pull request

## License

This plugin is provided as-is for use with RuneLite.
