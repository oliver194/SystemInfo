package me.thevipershow.systeminfo.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class GuiClickListener implements Listener {

    /**
     * In this event we are listening for the player clicking in his inventory, if the inventory
     * title equals to "SystemInfo" we are cancelling every action in order to prevent stealing
     * or moving items inside the inventory.
     *
     * @param inventoryClickEvent triggered whenever the player clicks inside an inventory.
     */
    @EventHandler
    public void clickEvent(InventoryClickEvent inventoryClickEvent) {
        String invTitle = inventoryClickEvent.getView().getTitle();
        if (invTitle.equals("SystemInfo")) {
            inventoryClickEvent.setCancelled(true);
        }
    }

}
