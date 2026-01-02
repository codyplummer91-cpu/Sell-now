package com.sellnow;

import net.runelite.api.Client;
import net.runelite.api.ItemComposition;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Overlay to highlight items at their all-time high prices
 */
public class SellNowOverlay extends Overlay {
    
    private final Client client;
    private final SellNowConfig config;
    private final GEPriceService priceService;
    private final ItemManager itemManager;
    private final TooltipManager tooltipManager;
    private final NumberFormat numberFormat;
    
    @Inject
    public SellNowOverlay(Client client, SellNowConfig config, GEPriceService priceService,
                          ItemManager itemManager, TooltipManager tooltipManager) {
        this.client = client;
        this.config = config;
        this.priceService = priceService;
        this.itemManager = itemManager;
        this.tooltipManager = tooltipManager;
        this.numberFormat = NumberFormat.getInstance(Locale.US);
        
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }
    
    @Override
    public Dimension render(Graphics2D graphics) {
        if (!config.highlightEnabled()) {
            return null;
        }
        
        // Check inventory items
        renderInventoryHighlights(graphics);
        
        // Check bank items
        renderBankHighlights(graphics);
        
        // Check Grand Exchange interface
        renderGEHighlights(graphics);
        
        return null;
    }
    
    /**
     * Render highlights for inventory items
     */
    private void renderInventoryHighlights(Graphics2D graphics) {
        Widget inventoryWidget = client.getWidget(149, 0); // Inventory widget
        if (inventoryWidget != null && !inventoryWidget.isHidden()) {
            for (WidgetItem item : inventoryWidget.getWidgetItems()) {
                renderItemHighlight(graphics, item);
            }
        }
    }
    
    /**
     * Render highlights for bank items
     */
    private void renderBankHighlights(Graphics2D graphics) {
        Widget bankWidget = client.getWidget(12, 12); // Bank item container
        if (bankWidget != null && !bankWidget.isHidden()) {
            for (WidgetItem item : bankWidget.getWidgetItems()) {
                renderItemHighlight(graphics, item);
            }
        }
    }
    
    /**
     * Render highlights for Grand Exchange items
     */
    private void renderGEHighlights(Graphics2D graphics) {
        // GE inventory widget
        Widget geInventoryWidget = client.getWidget(465, 7);
        if (geInventoryWidget != null && !geInventoryWidget.isHidden()) {
            for (WidgetItem item : geInventoryWidget.getWidgetItems()) {
                renderItemHighlight(graphics, item);
            }
        }
    }
    
    /**
     * Render a highlight around a specific item
     */
    private void renderItemHighlight(Graphics2D graphics, WidgetItem item) {
        int itemId = item.getId();
        
        // Check if item is at all-time high
        ItemPriceData priceData = priceService.getItemPriceData(itemId);
        if (priceData == null || !priceData.isAtAllTimeHigh()) {
            return;
        }
        
        // Get the color based on price tier
        Color highlightColor = getColorForTier(priceData.getColorTier());
        
        // Draw the highlight
        Rectangle bounds = item.getCanvasBounds();
        if (bounds != null) {
            graphics.setColor(highlightColor);
            graphics.setStroke(new BasicStroke(config.highlightThickness()));
            graphics.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
            
            // Add tooltip if enabled and mouse is hovering
            if (config.showTooltip() && bounds.contains(client.getMouseCanvasPosition().getX(),
                    client.getMouseCanvasPosition().getY())) {
                showPriceTooltip(priceData);
            }
        }
    }
    
    /**
     * Get the color for a specific price tier
     */
    private Color getColorForTier(int tier) {
        switch (tier) {
            case 0:
                return config.colorGray();
            case 1:
                return config.colorWhite();
            case 2:
                return config.colorGreen();
            case 3:
                return config.colorBlue();
            case 4:
                return config.colorPurple();
            case 5:
                return config.colorOrange();
            case 6:
                return config.colorRed();
            default:
                return Color.WHITE;
        }
    }
    
    /**
     * Show a tooltip with price information
     */
    private void showPriceTooltip(ItemPriceData priceData) {
        StringBuilder tooltipText = new StringBuilder();
        tooltipText.append(priceData.getItemName())
            .append("</br>")
            .append("Current: ")
            .append(numberFormat.format(priceData.getCurrentPrice()))
            .append(" gp</br>")
            .append("All-Time High: ")
            .append(numberFormat.format(priceData.getAllTimeHigh()))
            .append(" gp</br>")
            .append("<col=00ff00>AT ALL-TIME HIGH!</col>");
        
        tooltipManager.add(new Tooltip(tooltipText.toString()));
    }
}
