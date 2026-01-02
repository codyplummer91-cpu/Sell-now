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
        keyName = "colorGray",
        name = "Color: < 10k",
        description = "Color for items worth less than 10k",
        position = 10
    )
    default Color colorGray() {
        return new Color(128, 128, 128);
    }
    
    @ConfigItem(
        keyName = "colorWhite",
        name = "Color: 10k-100k",
        description = "Color for items worth 10k-100k",
        position = 11
    )
    default Color colorWhite() {
        return Color.WHITE;
    }
    
    @ConfigItem(
        keyName = "colorGreen",
        name = "Color: 100k-1m",
        description = "Color for items worth 100k-1m",
        position = 12
    )
    default Color colorGreen() {
        return Color.GREEN;
    }
    
    @ConfigItem(
        keyName = "colorBlue",
        name = "Color: 1m-10m",
        description = "Color for items worth 1m-10m",
        position = 13
    )
    default Color colorBlue() {
        return Color.CYAN;
    }
    
    @ConfigItem(
        keyName = "colorPurple",
        name = "Color: 10m-100m",
        description = "Color for items worth 10m-100m",
        position = 14
    )
    default Color colorPurple() {
        return new Color(128, 0, 128);
    }
    
    @ConfigItem(
        keyName = "colorOrange",
        name = "Color: 100m-1b",
        description = "Color for items worth 100m-1b",
        position = 15
    )
    default Color colorOrange() {
        return Color.ORANGE;
    }
    
    @ConfigItem(
        keyName = "colorRed",
        name = "Color: >= 1b",
        description = "Color for items worth 1 billion or more",
        position = 16
    )
    default Color colorRed() {
        return Color.RED;
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
