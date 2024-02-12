package top.cmarco.systeminfo.gui;

import org.bukkit.inventory.InventoryView;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import top.cmarco.systeminfo.oshi.SystemValues;
import top.cmarco.systeminfo.plugin.SystemInfo;
import top.cmarco.systeminfo.utils.Utils;
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
public final class SystemInfoGui {

    public SystemInfoGui(@NotNull SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

    private final SystemInfo systemInfo;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("d\\M\\u h:m:s a");

    // Corner slots for a 9x3 inventory.
    private static final Set<Integer> backgroundSlots = new LinkedHashSet<>(
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 18, 27, 26, 25, 24, 23, 22, 21, 20, 19, 10));

    public final static Inventory GUI = Bukkit.createInventory(null, 27, "SystemInfo");

    private final HashMap<UUID, BukkitTask> tasks = new HashMap<>();
    private BukkitTask fillTask = null;

    /**
     * This method creates the GUI to a Player
     *
     * @param player a valid Player
     */
    public void createGui(@NotNull Player player) {
        this.cleanBackground();
        player.openInventory(GUI);
        this.fillBackground();


        final BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(systemInfo, () -> {
            final InventoryView inventoryView = player.getOpenInventory();
            if (inventoryView.getTopInventory().equals(GUI)) {
                this.updateInventory();
            } else {
                BukkitTask bt = this.tasks.get(player.getUniqueId());
                if (bt != null) {
                    bt.cancel();
                    this.tasks.remove(player.getUniqueId());
                }
            }
        }, 2L, 20L);

        this.tasks.put(player.getUniqueId(), bukkitTask);

    }

    /**
     * This method generates an animation that consists in taking a list of integers that represents
     * inventory slots, then generating items with material parameter for each slot creating a cool effect
     */
    private void fillBackground() {
        final Iterator<Integer> invSlotIterator = SystemInfoGui.backgroundSlots.iterator();
        this.fillTask = Bukkit.getScheduler().runTaskTimer(systemInfo, () -> {
            if (invSlotIterator.hasNext()) {
                setCustomItem(SystemInfoGui.GUI, Material.STAINED_GLASS_PANE, invSlotIterator.next(), "", "");
            } else {
                if (this.fillTask != null) {
                    this.fillTask.cancel();
                    this.fillTask = null;
                }
            }
        }, 1L, 1L);
    }

    private void cleanBackground() {
        for (int backgroundSlot : SystemInfoGui.backgroundSlots) {
            final int slot = backgroundSlot - 1;
            SystemInfoGui.GUI.setItem(slot, null);
        }
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
     */
    public void updateInventory() {
        SystemValues values = this.systemInfo.getsV();
        setCustomItem(GUI, Material.GREEN_RECORD, 11, "&2Processor",
                "&7Vendor: &a" + values.getCpuVendor(),
                "&7Model: &a" + values.getCpuModel() + " " + values.getCpuModelName(),
                "&7Clock Speed: &a" + values.getCpuMaxFrequency() + " GHz",
                "&7Physical Cores: &a" + values.getCpuCores(),
                "&7Logical Cores: &a" + values.getCpuThreads());

        setCustomItem(GUI, Material.RECORD_4, 13, "&2Memory",
                "&7Total: &a" + values.getMaxMemory(),
                "&7Available: &a" + values.getAvailableMemory(),
                "&7Swap Used: &a" + values.getUsedSwap(),
                "&7Swap Allocated: &a" + values.getTotalSwap());

        setCustomItem(GUI, Material.GOLD_RECORD, 15, "&2Operating system",
                "&7Name: &a" + values.getOSFamily() + " " + values.getOSManufacturer(),
                "&7Version: &a" + values.getOSVersion(),
                "&7Active Processes: &a" + values.getRunningProcesses());

        setCustomItem(GUI, Material.RECORD_9, 17, "&2Uptime",
                "&7Jvm uptime: &a" + ChronoUnit.MINUTES.between(systemInfo.getsT(), LocalDateTime.now()) + " min.",
                "&7Current time: &a" + LocalDateTime.now().format(TIME_FORMATTER));
    }
}
