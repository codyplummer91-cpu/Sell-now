package com.sellnow;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * RuneLite plugin to highlight items at their all-time high Grand Exchange prices
 */
@Slf4j
@PluginDescriptor(
    name = "Sell Now",
    description = "Highlights items when they are at their all-time high on the Grand Exchange",
    tags = {"grand exchange", "ge", "prices", "trading", "highlighting"}
)
public class SellNowPlugin extends Plugin {
    
    @Inject
    private Client client;
    
    @Inject
    private SellNowConfig config;
    
    @Inject
    private OverlayManager overlayManager;
    
    @Inject
    private SellNowOverlay overlay;
    
    @Inject
    private GEPriceService priceService;
    
    private ScheduledExecutorService executorService;
    
    @Override
    protected void startUp() throws Exception {
        log.info("Sell Now plugin started!");
        
        // Create executor service
        executorService = Executors.newSingleThreadScheduledExecutor();
        
        // Add overlay
        overlayManager.add(overlay);
        
        // Load item name mapping
        executorService.submit(() -> priceService.loadItemNameMapping());
        
        // Start price updates with configured interval
        priceService.startPriceUpdates(executorService, config.updateIntervalMinutes());
        
        log.info("Price tracking started with {} minute update interval.", config.updateIntervalMinutes());
    }
    
    @Override
    protected void shutDown() throws Exception {
        log.info("Sell Now plugin stopped!");
        
        // Stop price updates
        priceService.stopPriceUpdates();
        
        // Shutdown executor service
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
        
        // Remove overlay
        overlayManager.remove(overlay);
        
        // Clear cache
        priceService.clearCache();
    }
    
    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged) {
        if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
            log.debug("Player logged in, price data available for {} items", 
                priceService.getTrackedItemCount());
        }
    }
    
    @Provides
    SellNowConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(SellNowConfig.class);
    }
}
