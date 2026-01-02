# Sell Now - RuneLite Plugin

A RuneLite plugin that highlights items when they are at their all-time high on the Grand Exchange.

## Features

- **Real-time Price Tracking**: Fetches live GE prices from OSRS Wiki API
- **All-Time High Detection**: Automatically tracks and identifies when items reach their all-time high prices
- **Color-Coded Highlights**: Items are highlighted with different colors based on their price ranges:
  - **Gray**: < 10k gp
  - **White**: 10k - 100k gp
  - **Green**: 100k - 1m gp
  - **Blue**: 1m - 10m gp
  - **Purple**: 10m - 100m gp
  - **Orange**: 100m - 1b gp
  - **Red**: >= 1b gp
- **Multiple Interface Support**: Works in inventory, bank, and Grand Exchange interfaces
- **Customizable**: Configure colors, highlight thickness, and update intervals
- **Price Tooltips**: Hover over highlighted items to see detailed price information

## Installation

1. Clone this repository
2. Build the plugin using Gradle:
   ```bash
   ./gradlew build
   ```
3. Copy the built JAR to your RuneLite plugins folder
4. Enable the plugin in RuneLite's plugin hub

## Configuration

Access the plugin settings in RuneLite to customize:
- Enable/disable highlighting
- Toggle price tooltips
- Adjust update interval (minimum 5 minutes)
- Customize colors for each price tier
- Adjust highlight border thickness

## Data Sources

This plugin uses the OSRS Wiki Price API:
- Latest prices: https://prices.runescape.wiki/api/v1/osrs/latest
- Item mapping: https://prices.runescape.wiki/api/v1/osrs/mapping

## Usage

Once enabled, the plugin will automatically:
1. Fetch the latest GE prices every 5 minutes (configurable)
2. Track all-time high prices for all items
3. Highlight items in your inventory, bank, or GE interface when they're at their ATH
4. Display price information when you hover over highlighted items

## Development

### Building

```bash
./gradlew build
```

### Project Structure

- `SellNowPlugin.java` - Main plugin class
- `SellNowConfig.java` - Configuration interface
- `SellNowOverlay.java` - Rendering overlay for highlights
- `GEPriceService.java` - Service for fetching and managing prices
- `ItemPriceData.java` - Model for item price data

## License

This plugin is provided as-is for use with RuneLite.
