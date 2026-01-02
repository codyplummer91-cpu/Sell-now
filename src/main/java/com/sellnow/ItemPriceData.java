package com.sellnow;

import lombok.Data;

/**
 * Represents price data for an item in the Grand Exchange
 */
@Data
public class ItemPriceData {
    // Price tier thresholds
    private static final long TIER_1_THRESHOLD = 10_000;       // Gray to White
    private static final long TIER_2_THRESHOLD = 100_000;      // White to Green
    private static final long TIER_3_THRESHOLD = 1_000_000;    // Green to Blue
    private static final long TIER_4_THRESHOLD = 10_000_000;   // Blue to Purple
    private static final long TIER_5_THRESHOLD = 100_000_000;  // Purple to Orange
    private static final long TIER_6_THRESHOLD = 1_000_000_000; // Orange to Red
    
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
     * Returns true only if price has been set and equals the all-time high
     */
    public boolean isAtAllTimeHigh() {
        // Ensure we have valid price data and that the price is actually at ATH
        return currentPrice > 0 && allTimeHigh > 0 && currentPrice >= allTimeHigh;
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
        if (currentPrice < TIER_1_THRESHOLD) {
            return 0; // gray
        } else if (currentPrice < TIER_2_THRESHOLD) {
            return 1; // white
        } else if (currentPrice < TIER_3_THRESHOLD) {
            return 2; // green
        } else if (currentPrice < TIER_4_THRESHOLD) {
            return 3; // blue
        } else if (currentPrice < TIER_5_THRESHOLD) {
            return 4; // purple
        } else if (currentPrice < TIER_6_THRESHOLD) {
            return 5; // orange
        } else {
            return 6; // red
        }
    }
}
