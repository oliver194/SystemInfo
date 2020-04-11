package me.thevipershow.systeminfo.gui;

import me.thevipershow.systeminfo.SystemInfo;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public final class SystemInfoGui {

    private static final SystemValues values = SystemValues.getInstance();

    private SystemInfoGui() {
    }

    private static final Set<Integer> backgroundSlots = new LinkedHashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 18, 27, 26, 25, 24, 23, 22, 21, 20, 19, 10));


    /**
     * This methods creates the GUI to a Player
     *
     * @param player a valid Player
     */
    public static void createGui(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 27, "SystemInfo");
        player.openInventory(inventory);
        fillBackground(inventory);
        Bukkit.getScheduler().runTaskTimer(SystemInfo.getInstance(), r -> {
            if (player.getOpenInventory().getTitle().equals("SystemInfo")) {
                updateInventory(inventory);
            } else {
                r.cancel();
            }
        }, 2L, 20L);
    }

    /**
     * This method generates an animation that consists in taking a list of integers that represents
     * inventory slots, then generating items with material parameter for each slot creating a cool effect
     *
     * @param inventory the inventory where the items will be set.
     */
    private static void fillBackground(Inventory inventory) {
        Iterator<Integer> invSlot = SystemInfoGui.backgroundSlots.iterator();
        Bukkit.getScheduler().runTaskTimer(SystemInfo.getInstance(), r -> {
            if (invSlot.hasNext()) {
                createCustomItem(inventory, Material.BLACK_STAINED_GLASS_PANE, invSlot.next(), " ", " ");
            } else {
                r.cancel();
            }
        }, 1L, 1L);
    }

    /**
     * This methods creates and sets in an inventory a new custom ItemStack from the given parameters:
     *
     * @param inv         this is the inventory where the item should be set.
     * @param material    this is the material that the new ItemStack will have.
     * @param invSlot     this is in which slot of the inventory the item will be set.
     * @param displayName the display name of the new ItemStack (this does support color codes with &).
     * @param loreText    the lore of the new ItemStack (this does support color codes with & and multiple lines with \n).
     * @throws IllegalArgumentException if amount of items is illegal, or the slot is illegal.
     */
    private static void createCustomItem(Inventory inv, Material material, int invSlot, String displayName, String... loreText) throws IllegalArgumentException {
        if ((invSlot >= 0 && invSlot <= inv.getSize())) {
            ItemStack item;
            List<String> lore = new ArrayList<>();
            item = new ItemStack(material, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.color(displayName));
            for (String s : loreText) {
                lore.add(Utils.color(s));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(invSlot - 1, item);
        } else {
            throw new IllegalArgumentException("Illegal values chosen for creating a custom item.");
        }
    }

    /**
     * This methods updates the inventory with new items
     *
     * @param inventory the target inventory where items will be set.
     */
    private static void updateInventory(Inventory inventory) {
        createCustomItem(inventory, Material.LIME_CONCRETE, 11, "&2Processor",
                String.format("&7Vendor: &a%s", values.getCpuVendor()),
                String.format("&7Model: &a%s %s", values.getCpuModel(), values.getCpuModelName()),
                String.format("&7Clock speed: &a%s GHz", values.getCpuMaxFrequency()),
                String.format("&7Cores: &a%s", values.getCpuCores()),
                String.format("&7Threads: &a%s", values.getCpuThreads()));

        createCustomItem(inventory, Material.GREEN_CONCRETE, 13, "&2Memory",
                String.format("&7Total:&a %s", values.getMaxMemory()),
                String.format("&7Available:&a %s", values.getAvailableMemory()),
                String.format("&7Swap:&a %s/%s", values.getUsedSwap(), values.getTotalSwap()));

        createCustomItem(inventory, Material.LIGHT_BLUE_CONCRETE, 15, "&2Operating system",
                String.format("&7Name: &a%s %s %s", values.getOSFamily(), values.getOSManufacturer(), values.getOSVersion()),
                String.format("&7Processes: &a%d", values.getRunningProcesses()));

        createCustomItem(inventory, Material.BLUE_CONCRETE, 17, "&2Uptime",
                String.format("&7Jvm uptime: &a%d min", ChronoUnit.MINUTES.between(SystemInfo.getInstance().getStartupTime(), LocalDateTime.now())),
                String.format("&7Current time: &a%s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("d\\M\\u h:m:s a"))));
    }
}
