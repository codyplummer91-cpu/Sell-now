package com.sellnow;

import lombok.Data;

/**
 * Represents price data for an item in the Grand Exchange
 */
@Data
public class ItemPriceData {
    private final int itemId;
    private final String itemName;
    private long lowPrice;
    private long highPrice;
    private long averagePrice;
    private long lastUpdated;
    
    public ItemPriceData(int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.lowPrice = 0;
        this.highPrice = 0;
        this.averagePrice = 0;
        this.lastUpdated = System.currentTimeMillis();
    }
    
    /**
     * Check if the item price is above average
     * Returns true if high price is greater than average
     */
    public boolean isAboveAverage() {
        return averagePrice > 0 && highPrice > averagePrice;
    }
    
    /**
     * Update prices from API data
     */
    public void updatePrices(long low, long high) {
        this.lowPrice = low;
        this.highPrice = high;
        this.averagePrice = (low + high) / 2;
        this.lastUpdated = System.currentTimeMillis();
    }
}
