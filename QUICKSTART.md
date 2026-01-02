# Sell Now Plugin - Quick Start Guide

## What Does This Plugin Do?

The Sell Now plugin helps you identify the perfect time to sell your items! It automatically:

1. **Tracks Grand Exchange prices** in real-time from OSRS Wiki
2. **Identifies all-time high prices** for every item
3. **Highlights items** when they reach their peak value
4. **Shows you** which items are most valuable with color coding

## Quick Start

### Installation

1. **Download**: Get the latest release from GitHub
2. **Install**: Place the JAR file in your RuneLite plugins folder
3. **Enable**: Start RuneLite and enable "Sell Now" in the plugin list
4. **Wait**: Give it 5 minutes to load all item prices
5. **Profit**: Check your inventory and bank - highlighted items are at peak prices!

### First Time Setup

1. **Open RuneLite**: Start the game client
2. **Enable Plugin**: Go to Configuration → Sell Now
3. **Wait for Data**: The plugin needs to download price data (about 1-2 minutes)
4. **Check Items**: Open your inventory or bank

### What You'll See

When an item is at its all-time high price, you'll see:

- **Colored Border**: Around the item in your inventory/bank/GE
- **Tooltip** (on hover): Shows current price and ATH confirmation

## Understanding the Colors

The border color tells you how valuable the item is:

| Color | Price Range | Example Items |
|-------|-------------|---------------|
| 🔘 Gray | Under 10k | Basic supplies, common items |
| ⚪ White | 10k - 100k | Mid-tier supplies, herbs |
| 🟢 Green | 100k - 1m | Good armor, popular items |
| 🔵 Blue | 1m - 10m | High-tier weapons, rare items |
| 🟣 Purple | 10m - 100m | Premium gear, boss uniques |
| 🟠 Orange | 100m - 1b | End-game items, rare drops |
| 🔴 Red | 1b+ | Twisted Bow, Scythe, etc. |

## Common Use Cases

### Scenario 1: Checking Your Bank
```
You open your bank and see several items highlighted:
- Dragon Bones (Green border) - Time to sell those prayer supplies!
- Abyssal Whip (Purple border) - Great time to sell that extra whip!
- Ranarr Seeds (White border) - Your farming supplies are worth good money!
```

### Scenario 2: After a Boss Trip
```
You complete a boss run and check your loot:
- Armadyl Crossbow (Orange border) - This is at ATH! Sell now!
- Dragon Boots (Green border) - Also at peak value!
```

### Scenario 3: Merching
```
You're flipping items and want to know when to sell:
- Check your GE offers
- Highlighted items = sell orders should be placed now
- Non-highlighted = wait for price to rise
```

### Scenario 4: Cleaning Out Bank
```
Deciding what to sell from bank tabs:
- Orange/Red highlights = Sell these for maximum profit
- Green/Blue = Good time to sell
- Gray/White = These are at ATH but low value, up to you
```

## Tips for Maximum Profit

### 1. Patience Pays Off
- Not every item will be highlighted immediately
- Wait for market movements
- Check daily for new highlights

### 2. Sell High-Value Items First
- Focus on Red and Orange highlights (100m+)
- These fluctuate more and timing matters most
- Green/Blue items are less volatile

### 3. Check After Game Updates
- New content affects prices dramatically
- Updates often create new all-time highs
- Check your bank after every major update

### 4. Use With Grand Exchange
- Place items in GE interface
- Highlighted items should be listed above market price
- Adjust sell price based on color tier

### 5. Combine With Other Tools
- Use with price checking websites
- Cross-reference with GE Tracker
- Check market trends on OSRS Wiki

## Common Questions

### "Why isn't my item highlighted?"
- Item is not at its all-time high yet
- Wait for price to rise
- Plugin needs 5 minutes to load initial data

### "How often are prices updated?"
- Default: Every 5 minutes
- Configurable: 5-60 minutes
- Changes take effect after next update cycle

### "Can I change the colors?"
- Yes! Go to Configuration → Sell Now
- Each price tier has its own color setting
- Useful for color blind players or personal preference

### "Does this work in all game interfaces?"
- Inventory: ✓ Yes
- Bank: ✓ Yes
- Grand Exchange: ✓ Yes
- Trade Window: ✗ Not yet
- Looting: ✗ Not yet

### "Will this get me banned?"
- No, RuneLite is approved by Jagex
- This plugin only displays information
- No automation or rule breaking

### "How accurate are the prices?"
- Very accurate - uses OSRS Wiki API
- Same data as Wiki price page
- Updated from real GE trades
- Typically within 1% of actual value

## Advanced Usage

### Configuration Tips

**For Merchers:**
```
- Enable tooltips: Yes (see exact prices)
- Update interval: 5 minutes (fastest updates)
- Highlight thickness: 3 (clearly visible)
- Focus on: Orange/Red tiers
```

**For Bank Cleaning:**
```
- Enable tooltips: Yes (helps decide what to keep)
- Update interval: 10 minutes (less frequent)
- Highlight all tiers
- Sell highlighted items systematically
```

**For PvM/Bossing:**
```
- Enable tooltips: Yes (see drop values)
- Update interval: 5 minutes
- Focus on: Blue/Purple/Orange tiers
- Quick decisions on valuable drops
```

### Hotkeys and Workflow

1. **Check Inventory**: Look for highlights after activities
2. **Visit Bank**: Scan all tabs for highlights
3. **Head to GE**: List highlighted items
4. **Set Prices**: Use tooltip prices as reference
5. **Collect**: Come back when sold

### Integration With Other Plugins

**Works well with:**
- Item Identification (both can highlight)
- Grand Exchange plugin (track offers)
- Bank Tags (organize by value)
- Loot Tracker (see valuable drops)

**Potential conflicts:**
- Multiple highlighting plugins may overlap
- Adjust highlight thickness if issues occur

## Troubleshooting

### Problem: No highlights appearing
**Solutions:**
1. Check plugin is enabled
2. Wait 5 minutes for initial load
3. Check RuneLite console for errors
4. Verify internet connection

### Problem: Wrong colors
**Solutions:**
1. Check item actual price
2. Verify color tier in settings
3. Reset colors to default
4. Restart plugin

### Problem: Performance lag
**Solutions:**
1. Increase update interval to 15 minutes
2. Reduce highlight thickness to 1
3. Close other plugins temporarily
4. Update RuneLite to latest version

### Problem: Tooltips not showing
**Solutions:**
1. Enable "Show Price Tooltips" in config
2. Hover directly over item
3. Check no other tooltips are blocking
4. Restart RuneLite

## Examples in Action

### Example 1: Dragon Bones
```
Current Price: 2,450 gp
All-Time High: 2,450 gp
Status: AT ALL-TIME HIGH!
Color: Gray/White (depending on tier)
Action: Good time to sell prayer supplies
```

### Example 2: Twisted Bow
```
Current Price: 1,234,567,890 gp
All-Time High: 1,234,567,890 gp
Status: AT ALL-TIME HIGH!
Color: Red (1b+)
Action: Optimal time to sell if planning to
```

### Example 3: Abyssal Whip
```
Current Price: 2,500,000 gp
All-Time High: 2,500,000 gp
Status: AT ALL-TIME HIGH!
Color: Blue (1m-10m)
Action: List on GE now for maximum profit
```

## Best Practices

1. **Check Daily**: Prices change constantly
2. **Be Patient**: Wait for highlights before selling
3. **Research**: Cross-check with Wiki if uncertain
4. **Don't Panic**: Not every item will be highlighted
5. **Keep Records**: Track your sales and profits
6. **Stay Updated**: Watch for plugin updates
7. **Community**: Share tips with other users

## Getting Help

- **Documentation**: Read README.md and TECHNICAL.md
- **Configuration**: See CONFIG_GUIDE.md for settings
- **Issues**: Report bugs on GitHub
- **Suggestions**: Submit feature requests
- **Community**: Discuss on RuneLite Discord

## Remember

> "The best time to sell is when an item is highlighted. The second best time is... well, waiting for it to be highlighted again!"

Happy trading, and may your items always be at their all-time high! 💰
