package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;

import java.util.ArrayList;

public final class CommandDisks extends Command {

    private final SystemValues values = SystemValues.getInstance();

    public CommandDisks() {
        super("disks",
                "get your system disks list",
                "/<command>",
                new ArrayList<>());
    }

    @Override
    public boolean execute(CommandSender sender, String name, String[] args) {
        if (sender.hasPermission("systeminfo.commands.disks")) {
            if (args.length == 0) {
                try {
                    printDisks(sender);
                } catch (IllegalArgumentException e) {
                    sender.sendMessage("&c» Could not obtain info from this system.");
                }
                return true;
            } else {
                sender.sendMessage(Messages.OUT_OF_ARGS.value(true));
            }
        } else {
            sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
        }
        return false;
    }

    private void printDisks(CommandSender sender) {
        for (HWDiskStore disk : values.getDiskStores()) {
            sender.sendMessage(Utils.builderHover(String.format("&7[%s %s", disk.getName(), disk.getModel()),
                    String.format("&7Serial: &a%s\n&7Disk Read: &a%s\n&7Disk Written: &a%s\n",
                            disk.getSerial(), Utils.formatData(disk.getReadBytes()), Utils.formatData(disk.getWriteBytes()))));
            for (HWPartition part : disk.getPartitions()) {
                sender.sendMessage(Utils.builderHover(String.format("  &7|-- &a%s &7Size: &a%s", part.getIdentification() + " " + part.getType(), Utils.formatData(part.getSize())),
                        String.format("&7Mount point: &a%s &7Uuid: &a%s", part.getMountPoint(), part.getUuid())));
            }

        }
    }
}
