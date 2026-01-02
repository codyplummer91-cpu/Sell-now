package com.sellnow;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.Color;

@ConfigGroup("sellnow")
public interface SellNowConfig extends Config {
    
    @ConfigItem(
        keyName = "highlightEnabled",
        name = "Enable Highlighting",
        description = "Enable highlighting of items at all-time high prices",
        position = 1
    )
    default boolean highlightEnabled() {
        return true;
    }
    
    @ConfigItem(
        keyName = "showTooltip",
        name = "Show Price Tooltips",
        description = "Show price information in tooltips when hovering over items",
        position = 2
    )
    default boolean showTooltip() {
        return true;
    }
    
    @ConfigItem(
        keyName = "updateIntervalMinutes",
        name = "Update Interval (minutes)",
        description = "How often to update prices from the API (minimum 5 minutes)",
        position = 3
    )
    default int updateIntervalMinutes() {
        return 5;
    }
    
    @ConfigItem(
        keyName = "highlightColor",
        name = "Highlight Color",
        description = "Color for items above average price",
        position = 10
    )
    default Color highlightColor() {
        return Color.GREEN;
    }
    
    @ConfigItem(
        keyName = "highlightThickness",
        name = "Highlight Thickness",
        description = "Thickness of the highlight border",
        position = 20
    )
    default int highlightThickness() {
        return 2;
    }
}
