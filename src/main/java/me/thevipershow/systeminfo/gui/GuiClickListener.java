package me.thevipershow.systeminfo.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiClickListener implements Listener {

    @EventHandler
    public void clickEvent(InventoryClickEvent inventoryClickEvent) {
        String invTitle = inventoryClickEvent.getView().getTitle();
        if (invTitle.equals("SystemInfo")) {
            inventoryClickEvent.setCancelled(true);
        }
    }

}
