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
import java.util.concurrent.TimeUnit;

/**
 * Service to fetch and manage Grand Exchange prices from various sources
 */
@Slf4j
@Singleton
public class GEPriceService {
    
    private static final String OSRS_WIKI_API = "https://prices.runescape.wiki/api/v1/osrs/latest";
    private static final String OSRS_WIKI_MAPPING = "https://prices.runescape.wiki/api/v1/osrs/mapping";
    private static final long UPDATE_INTERVAL_MINUTES = 5;
    
    private final OkHttpClient httpClient;
    private final Map<Integer, ItemPriceData> priceCache;
    private final Map<Integer, String> itemNameCache;
    
    @Inject
    public GEPriceService(OkHttpClient httpClient) {
        this.httpClient = httpClient;
        this.priceCache = new ConcurrentHashMap<>();
        this.itemNameCache = new ConcurrentHashMap<>();
    }
    
    /**
     * Start periodic price updates
     */
    public void startPriceUpdates(ScheduledExecutorService executor) {
        // Initial update
        updateAllPrices();
        
        // Schedule periodic updates
        executor.scheduleAtFixedRate(
            this::updateAllPrices,
            UPDATE_INTERVAL_MINUTES,
            UPDATE_INTERVAL_MINUTES,
            TimeUnit.MINUTES
        );
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
                    JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
                    JsonObject data = json.getAsJsonObject("data");
                    
                    if (data != null) {
                        data.entrySet().forEach(entry -> {
                            try {
                                int itemId = Integer.parseInt(entry.getKey());
                                JsonObject priceData = entry.getValue().getAsJsonObject();
                                
                                // Use high price if available, otherwise use low price
                                long price = 0;
                                if (priceData.has("high")) {
                                    price = priceData.get("high").getAsLong();
                                } else if (priceData.has("low")) {
                                    price = priceData.get("low").getAsLong();
                                }
                                
                                if (price > 0) {
                                    ItemPriceData itemData = priceCache.computeIfAbsent(
                                        itemId,
                                        id -> new ItemPriceData(id, itemNameCache.getOrDefault(id, "Unknown Item"))
                                    );
                                    itemData.updatePrice(price);
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
                    
                    // The response is a JSON array directly
                    if (responseBody.trim().startsWith("[")) {
                        JsonParser.parseString(responseBody).getAsJsonArray().forEach(element -> {
                            JsonObject item = element.getAsJsonObject();
                            int id = item.get("id").getAsInt();
                            String name = item.get("name").getAsString();
                            itemNameCache.put(id, name);
                        });
                        
                        log.debug("Loaded {} item names", itemNameCache.size());
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
     * Check if an item is at its all-time high
     */
    public boolean isItemAtAllTimeHigh(int itemId) {
        ItemPriceData data = priceCache.get(itemId);
        return data != null && data.isAtAllTimeHigh();
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
