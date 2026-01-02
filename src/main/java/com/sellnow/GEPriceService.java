package com.sellnow;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Service to fetch and manage Grand Exchange prices from various sources
 */
@Slf4j
@Singleton
public class GEPriceService {
    
    private static final String OSRS_WIKI_API = "https://prices.runescape.wiki/api/v1/osrs/latest";
    private static final String OSRS_WIKI_MAPPING = "https://prices.runescape.wiki/api/v1/osrs/mapping";
    private static final long DEFAULT_UPDATE_INTERVAL_MINUTES = 5;
    
    private final OkHttpClient httpClient;
    private final Map<Integer, ItemPriceData> priceCache;
    private final Map<Integer, String> itemNameCache;
    private ScheduledFuture<?> priceUpdateTask;
    
    @Inject
    public GEPriceService(OkHttpClient httpClient) {
        this.httpClient = httpClient;
        this.priceCache = new ConcurrentHashMap<>();
        this.itemNameCache = new ConcurrentHashMap<>();
    }
    
    /**
     * Start periodic price updates
     * @param executor The executor service for scheduling updates
     * @param updateIntervalMinutes The interval between updates in minutes (minimum 5)
     */
    public void startPriceUpdates(ScheduledExecutorService executor, int updateIntervalMinutes) {
        // Ensure minimum interval of 5 minutes to respect API rate limits
        long intervalMinutes = Math.max(updateIntervalMinutes, DEFAULT_UPDATE_INTERVAL_MINUTES);
        
        // Initial update (must be on background thread)
        executor.submit(this::updateAllPrices);
        
        // Schedule periodic updates and store the task
        priceUpdateTask = executor.scheduleAtFixedRate(
            this::updateAllPrices,
            intervalMinutes,
            intervalMinutes,
            TimeUnit.MINUTES
        );
    }
    
    /**
     * Stop periodic price updates
     */
    public void stopPriceUpdates() {
        if (priceUpdateTask != null && !priceUpdateTask.isCancelled()) {
            priceUpdateTask.cancel(false);
            log.debug("Price update task cancelled");
        }
    }
    
    /**
     * Update all prices from OSRS Wiki API
     */
    private void updateAllPrices() {
        try {
            log.debug("Updating GE prices from OSRS Wiki...");
            
            // Fetch latest prices
            Request request = new Request.Builder()
                .url(OSRS_WIKI_API)
                .header("User-Agent", "RuneLite Sell-Now Plugin")
                .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    JsonObject json = new JsonParser().parse(responseBody).getAsJsonObject();
                    JsonObject data = json.getAsJsonObject("data");
                    
                    if (data != null) {
                        data.entrySet().forEach(entry -> {
                            try {
                                int itemId = Integer.parseInt(entry.getKey());
                                JsonObject priceData = entry.getValue().getAsJsonObject();
                                
                                // Get both high and low prices
                                long highPrice = 0;
                                long lowPrice = 0;
                                
                                if (priceData.has("high")) {
                                    highPrice = priceData.get("high").getAsLong();
                                }
                                if (priceData.has("low")) {
                                    lowPrice = priceData.get("low").getAsLong();
                                }
                                
                                // Only store if we have at least one valid price
                                if (highPrice > 0 || lowPrice > 0) {
                                    // If one is missing, use the other
                                    if (highPrice == 0) highPrice = lowPrice;
                                    if (lowPrice == 0) lowPrice = highPrice;
                                    
                                    ItemPriceData itemData = priceCache.computeIfAbsent(
                                        itemId,
                                        id -> new ItemPriceData(id, itemNameCache.getOrDefault(id, "Unknown Item"))
                                    );
                                    itemData.updatePrices(lowPrice, highPrice);
                                }
                            } catch (Exception e) {
                                log.debug("Error parsing price for item: " + entry.getKey(), e);
                            }
                        });
                        
                        log.debug("Updated {} items from OSRS Wiki", data.size());
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error fetching prices from OSRS Wiki", e);
        }
    }
    
    /**
     * Fetch item names mapping from OSRS Wiki
     */
    public void loadItemNameMapping() {
        try {
            log.debug("Loading item name mapping from OSRS Wiki...");
            
            Request request = new Request.Builder()
                .url(OSRS_WIKI_MAPPING)
                .header("User-Agent", "RuneLite Sell-Now Plugin")
                .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    
                    try {
                        // Parse as JSON array and process each item
                        new JsonParser().parse(responseBody).getAsJsonArray().forEach(element -> {
                            JsonObject item = element.getAsJsonObject();
                            int id = item.get("id").getAsInt();
                            String name = item.get("name").getAsString();
                            itemNameCache.put(id, name);
                        });
                        
                        log.debug("Loaded {} item names", itemNameCache.size());
                    } catch (Exception e) {
                        log.error("Error parsing item mapping JSON", e);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error loading item name mapping", e);
        }
    }
    
    /**
     * Get price data for an item
     */
    public ItemPriceData getItemPriceData(int itemId) {
        return priceCache.get(itemId);
    }
    
    /**
     * Check if an item is above average price
     */
    public boolean isItemAboveAverage(int itemId) {
        ItemPriceData data = priceCache.get(itemId);
        return data != null && data.isAboveAverage();
    }
    
    /**
     * Get the number of items being tracked
     */
    public int getTrackedItemCount() {
        return priceCache.size();
    }
    
    /**
     * Clear all cached data
     */
    public void clearCache() {
        priceCache.clear();
    }
}
