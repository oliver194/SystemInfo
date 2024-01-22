package top.cmarco.systeminfo.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Listener used to prevent players from stealing or adding item into the plugin inventory.
 */
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
        final Inventory inventory = inventoryClickEvent.getView().getTopInventory();

        if (inventory.equals(SystemInfoGui.GUI)) {
            inventoryClickEvent.setCancelled(true);
        }

    }
}
