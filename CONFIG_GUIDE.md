# Sell Now Plugin - Configuration Guide

## Overview

This guide explains all configuration options available in the Sell Now plugin. Access these settings through RuneLite's plugin configuration panel.

## Configuration Options

### General Settings

#### Enable Highlighting
- **Default**: Enabled
- **Description**: Master switch to enable/disable all item highlighting
- **Usage**: Turn this off to temporarily disable the plugin without uninstalling it

#### Show Price Tooltips
- **Default**: Enabled
- **Description**: Display detailed price information when hovering over highlighted items
- **Tooltip includes**:
  - Item name
  - Current price
  - All-time high price
  - "AT ALL-TIME HIGH!" indicator

#### Update Interval (minutes)
- **Default**: 5 minutes
- **Range**: 5-60 minutes
- **Description**: How often the plugin fetches new prices from the OSRS Wiki API
- **Note**: Setting this too low may result in rate limiting. 5 minutes is recommended.

### Color Configuration

Customize the highlight color for each price tier:

#### Color: < 10k gp (Gray)
- **Default**: RGB(128, 128, 128) - Gray
- **Items**: Low-value items like basic supplies

#### Color: 10k-100k gp (White)
- **Default**: RGB(255, 255, 255) - White
- **Items**: Mid-low value items like some herbs, ores

#### Color: 100k-1m gp (Green)
- **Default**: RGB(0, 255, 0) - Green
- **Items**: Mid-value items like some armor pieces, potions

#### Color: 1m-10m gp (Blue)
- **Default**: RGB(0, 255, 255) - Cyan
- **Items**: High-value items like some weapons, rare drops

#### Color: 10m-100m gp (Purple)
- **Default**: RGB(128, 0, 128) - Purple
- **Items**: Very high-value items like high-tier gear

#### Color: 100m-1b gp (Orange)
- **Default**: RGB(255, 200, 0) - Orange
- **Items**: Extremely valuable items like rare uniques

#### Color: >= 1b gp (Red)
- **Default**: RGB(255, 0, 0) - Red
- **Items**: Most expensive items in the game like twisted bow, scythe

#### Highlight Thickness
- **Default**: 2 pixels
- **Range**: 1-10 pixels
- **Description**: Thickness of the colored border around highlighted items
- **Note**: Thicker borders are more visible but may overlap with adjacent items

## Recommended Settings

### Conservative (Low Visibility)
```
Highlight Thickness: 1
All colors: Slightly muted versions
Use for: Minimalist UI preference
```

### Balanced (Default)
```
Highlight Thickness: 2
All colors: Default values
Use for: Most users
```

### High Visibility
```
Highlight Thickness: 3-4
Colors: Bright, saturated values
Use for: Players who want maximum visibility
```

### Color Blind Friendly
Consider these color adjustments:
- Gray → Dark Blue
- White → Light Yellow
- Green → Blue-Green
- Blue → Royal Blue
- Purple → Magenta
- Orange → Gold
- Red → Dark Red

## Tips for Configuration

1. **Test Colors**: After changing colors, check your inventory with various items to ensure visibility

2. **Performance**: If experiencing lag, increase the update interval to 10-15 minutes

3. **Visibility**: If highlights are hard to see:
   - Increase thickness to 3-4
   - Use brighter, more saturated colors
   - Ensure colors contrast with game interface

4. **Minimal Interference**: If highlights are too distracting:
   - Decrease thickness to 1
   - Use subtle, darker colors
   - Disable tooltips

## Troubleshooting

### Highlights not showing
1. Check "Enable Highlighting" is turned on
2. Verify items are actually at their all-time high
3. Wait for initial price data to load (first 5 minutes)
4. Check RuneLite console for API errors

### Wrong colors appearing
1. Verify the item's price tier matches expected tier
2. Check custom color configurations haven't been changed
3. Reset colors to default if uncertain

### Tooltips not appearing
1. Ensure "Show Price Tooltips" is enabled
2. Hover directly over the item
3. Check other overlays aren't blocking tooltips

### Performance issues
1. Increase update interval to 10-15 minutes
2. Reduce highlight thickness
3. Check other plugins aren't conflicting

## Advanced Configuration

### Plugin Integration
The Sell Now plugin works alongside other RuneLite plugins:

- **Item Identification**: Both plugins can highlight items simultaneously
- **Grand Exchange**: Compatible with GE offer tracking
- **Bank Tags**: Highlights work with tagged items
- **Inventory Tags**: Can combine with custom inventory tags

### Data Storage
- Price data is cached in memory during runtime
- All-time high values reset when plugin is restarted
- Configuration is saved to RuneLite's config files

## Examples

### Example 1: Merching Setup
```
Enable: Yes
Tooltips: Yes
Update Interval: 5 minutes
Thickness: 3
Focus on: Orange and Red tiers for high-value flips
```

### Example 2: Casual Player
```
Enable: Yes
Tooltips: Yes
Update Interval: 10 minutes
Thickness: 2
Use: Default colors for easy recognition
```

### Example 3: Minimal Distraction
```
Enable: Yes
Tooltips: No
Update Interval: 15 minutes
Thickness: 1
Colors: Subtle, darker shades
```

## Support

For issues or questions:
1. Check the main README.md for general information
2. Review TECHNICAL.md for how the plugin works
3. Report bugs via GitHub issues
4. Contribute improvements via pull requests
