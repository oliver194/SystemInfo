package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.entity.Player;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;

public class CommandDisks implements Command {

    private void printDisks(Player player) {
        for (HWDiskStore disk : SystemValues.getDiskStores()) {
            player.sendMessage(Utils.builderHover(String.format("&7[%s %s", disk.getName(), disk.getModel()),
                    String.format("&7Serial: &a%s\n&7Disk Read: &a%s\n&7Disk Written: &a%s\n",
                            disk.getSerial(), Utils.formatData(disk.getReadBytes()), Utils.formatData(disk.getWriteBytes()))));
            for (HWPartition part : disk.getPartitions()) {
                player.sendMessage(Utils.builderHover(String.format("  &7|-- &a%s &7Size: &a%s", part.getIdentification() + " " + part.getType(), Utils.formatData(part.getSize())),
                        String.format("&7Mount point: &a%s &7Uuid: &a%s", part.getMountPoint(), part.getUuid())));
            }

        }
    }

    @Override
    public void action(Player player, String name, String[] args) {
        if (name.equals("disks")) {
            if (player.hasPermission("systeminfo.commands.disks")) {
                if (args.length == 0) {
                    printDisks(player);
                } else {
                    player.sendMessage(Messages.OUT_OF_ARGS.value(true));
                }
            } else {
                player.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        }
    }
}
