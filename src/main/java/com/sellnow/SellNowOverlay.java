package com.sellnow;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
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
    private final TooltipManager tooltipManager;
    private final NumberFormat numberFormat;
    
    @Inject
    public SellNowOverlay(Client client, SellNowConfig config, GEPriceService priceService,
                          TooltipManager tooltipManager) {
        this.client = client;
        this.config = config;
        this.priceService = priceService;
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
            Widget[] children = inventoryWidget.getChildren();
            if (children != null) {
                for (Widget child : children) {
                    if (child != null && child.getItemId() > 0) {
                        renderWidgetHighlight(graphics, child);
                    }
                }
            }
        }
    }
    
    /**
     * Render highlights for bank items
     */
    private void renderBankHighlights(Graphics2D graphics) {
        // Try multiple bank widget IDs
        Widget bankWidget = client.getWidget(15, 3); // Bank items container (newer)
        if (bankWidget == null || bankWidget.isHidden()) {
            bankWidget = client.getWidget(12, 13); // Bank items container (alternative)
        }
        
        if (bankWidget != null && !bankWidget.isHidden()) {
            Widget[] children = bankWidget.getChildren();
            if (children != null) {
                for (Widget child : children) {
                    if (child != null && child.getItemId() > 0) {
                        renderWidgetHighlight(graphics, child);
                    }
                }
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
            Widget[] children = geInventoryWidget.getChildren();
            if (children != null) {
                for (Widget child : children) {
                    if (child != null && child.getItemId() > 0) {
                        renderWidgetHighlight(graphics, child);
                    }
                }
            }
        }
    }
    
    /**
     * Render a highlight around a specific widget item
     */
    private void renderWidgetHighlight(Graphics2D graphics, Widget widget) {
        int itemId = widget.getItemId();
        
        // Check if item price is above average
        ItemPriceData priceData = priceService.getItemPriceData(itemId);
        if (priceData == null || !priceData.isAboveAverage()) {
            return;
        }
        
        // Use green highlight color
        Color highlightColor = config.highlightColor();
        
        // Draw the highlight
        Rectangle bounds = widget.getBounds();
        if (bounds != null) {
            graphics.setColor(highlightColor);
            graphics.setStroke(new BasicStroke(config.highlightThickness()));
            graphics.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
            
            // Add tooltip if enabled and mouse is hovering
            if (config.showTooltip()) {
                net.runelite.api.Point mousePos = client.getMouseCanvasPosition();
                if (mousePos != null) {
                    int mouseX = mousePos.getX();
                    int mouseY = mousePos.getY();
                    if (bounds.contains(mouseX, mouseY)) {
                        showPriceTooltip(priceData);
                    }
                }
            }
        }
    }
    
    /**
     * Show a tooltip with price information
     */
    private void showPriceTooltip(ItemPriceData priceData) {
        StringBuilder tooltipText = new StringBuilder();
        tooltipText.append(priceData.getItemName())
            .append("<br/>")
            .append("Low: ")
            .append(numberFormat.format(priceData.getLowPrice()))
            .append(" | Avg: ")
            .append(numberFormat.format(priceData.getAveragePrice()))
            .append(" | High: ")
            .append(numberFormat.format(priceData.getHighPrice()))
            .append(" gp");
        
        tooltipManager.add(new Tooltip(tooltipText.toString()));
    }
}
