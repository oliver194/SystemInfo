package studio.thevipershow.systeminfo.gui;

import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.oshi.SystemValues;
import studio.thevipershow.systeminfo.plugin.SystemInfo;
import studio.thevipershow.systeminfo.utils.Utils;
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

/**
 * This class is used for the interactive GUI in the systeminfo gui command.
 * It displays basic information using Minecraft blocks and animations.
 */
public class SystemInfoGui {

    public SystemInfoGui(@NotNull SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

    private final SystemInfo systemInfo;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("d\\M\\u h:m:s a");

    // Corner slots for a 9x3 inventory.
    private static final Set<Integer> backgroundSlots = new LinkedHashSet<>(
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 18, 27, 26, 25, 24, 23, 22, 21, 20, 19, 10));

    /**
     * This method creates the GUI to a Player
     *
     * @param player a valid Player
     */
    public void createGui(@NotNull Player player) {
        Inventory inventory = Bukkit.createInventory(player, 27, "SystemInfo");
        player.openInventory(inventory);
        fillBackground(inventory);
        Bukkit.getScheduler().runTaskTimer(systemInfo, r -> {
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
    private void fillBackground(Inventory inventory) {
        Iterator<Integer> invSlot = SystemInfoGui.backgroundSlots.iterator();
        Bukkit.getScheduler().runTaskTimer(systemInfo, r -> {
            if (invSlot.hasNext()) {
                setCustomItem(inventory, Material.BLACK_STAINED_GLASS_PANE, invSlot.next(), "", "");
            } else {
                r.cancel();
            }
        }, 1L, 1L);
    }

    /**
     * This method creates and sets in an inventory a new custom ItemStack from the given parameters:
     *
     * @param inv         this is the inventory where the item should be set.
     * @param material    this is the material that the new ItemStack will have.
     * @param invSlot     this is in which slot of the inventory the item will be set.
     * @param displayName the display name of the new ItemStack (this does support color codes with &).
     * @param loreText    the lore of the new ItemStack (this does support color codes with & and multiple lines with \n).
     * @throws IllegalArgumentException if amount of items is illegal, or the slot is illegal.
     */
    public static void setCustomItem(@NotNull Inventory inv, @NotNull Material material, int invSlot, @NotNull String displayName, String... loreText) {
        if ((invSlot >= 0 && invSlot <= inv.getSize())) {
            ItemStack item;
            List<String> lore = new ArrayList<>();
            item = new ItemStack(material, 1);
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            meta.setDisplayName(Utils.color(displayName));
            for (String s : loreText) {
                lore.add(Utils.color(s));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(invSlot - 1, item);
        }
    }

    /**
     * This method updates the inventory with new items
     *
     * @param inventory the target inventory where items will be set.
     */
    public void updateInventory(Inventory inventory) {
        SystemValues values = this.systemInfo.getsV();
        setCustomItem(inventory, Material.LIME_CONCRETE, 11, "&2Processor",
                "&7Vendor: &a" + values.getCpuVendor(),
                "&7Model: &a" + values.getCpuModel() + " " + values.getCpuModelName(),
                "&7Clock Speed: &a" + values.getCpuMaxFrequency() + " GHz",
                "&7Physical Cores: &a" + values.getCpuCores(),
                "&7Logical Cores: &a" + values.getCpuThreads());

        setCustomItem(inventory, Material.GREEN_CONCRETE, 13, "&2Memory",
                "&7Total: &a" + values.getMaxMemory(),
                "&7Available: &a" + values.getAvailableMemory(),
                "&7Swap Used: &a" + values.getUsedSwap(),
                "&7Swap Allocated: &a" + values.getTotalSwap());

        setCustomItem(inventory, Material.LIGHT_BLUE_CONCRETE, 15, "&2Operating system",
                "&7Name: &a" + values.getOSFamily() + " " + values.getOSManufacturer(),
                "&7Version: &a" + values.getOSVersion(),
                "&7Active Processes: &a" + values.getRunningProcesses());

        setCustomItem(inventory, Material.BLUE_CONCRETE, 17, "&2Uptime",
                "&7Jvm uptime: &a" + ChronoUnit.MINUTES.between(systemInfo.getsT(), LocalDateTime.now()) + " min.",
                "&7Current time: &a" + LocalDateTime.now().format(TIME_FORMATTER));
    }
}
