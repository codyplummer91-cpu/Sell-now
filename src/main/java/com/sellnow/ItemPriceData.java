package com.sellnow;

import lombok.Data;

/**
 * Represents price data for an item in the Grand Exchange
 */
@Data
public class ItemPriceData {
    private final int itemId;
    private final String itemName;
    private long currentPrice;
    private long allTimeHigh;
    private long lastUpdated;
    
    public ItemPriceData(int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.currentPrice = 0;
        this.allTimeHigh = 0;
        this.lastUpdated = System.currentTimeMillis();
    }
    
    /**
     * Check if the item is at its all-time high
     */
    public boolean isAtAllTimeHigh() {
        return currentPrice > 0 && currentPrice >= allTimeHigh;
    }
    
    /**
     * Update the current price and check if it's a new all-time high
     */
    public void updatePrice(long newPrice) {
        this.currentPrice = newPrice;
        this.lastUpdated = System.currentTimeMillis();
        
        if (newPrice > allTimeHigh) {
            this.allTimeHigh = newPrice;
        }
    }
    
    /**
     * Get the color tier based on price ranges
     * 0: < 10k (gray)
     * 1: 10k-100k (white)
     * 2: 100k-1m (green)
     * 3: 1m-10m (blue)
     * 4: 10m-100m (purple)
     * 5: 100m-1b (orange)
     * 6: >= 1b (red)
     */
    public int getColorTier() {
        if (currentPrice < 10_000) {
            return 0; // gray
        } else if (currentPrice < 100_000) {
            return 1; // white
        } else if (currentPrice < 1_000_000) {
            return 2; // green
        } else if (currentPrice < 10_000_000) {
            return 3; // blue
        } else if (currentPrice < 100_000_000) {
            return 4; // purple
        } else if (currentPrice < 1_000_000_000) {
            return 5; // orange
        } else {
            return 6; // red
        }
    }
}
