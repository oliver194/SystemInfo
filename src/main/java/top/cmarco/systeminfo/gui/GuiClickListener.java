/*
 *     SystemInfo - The Master of Server Hardware
 *     Copyright © 2024 CMarco
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
