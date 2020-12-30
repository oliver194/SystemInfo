package studio.thevipershow.systeminfo.commands;

import java.util.Collections;
import studio.thevipershow.systeminfo.enums.Messages;
import studio.thevipershow.systeminfo.oshi.SystemValues;
import studio.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;

public final class CommandDisks extends Command {

    private final SystemValues values = SystemValues.getInstance();

    public CommandDisks() {
        super("disks",
                "get your system disks list",
                "/<command>",
                Collections.emptyList());
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
            sender.spigot().sendMessage(Utils.builderHover("&7[" + disk.getName() + " " + disk.getModel(),
                    "&7Serial: &a" + disk.getSerial()
                            + "\n&7Disk Read: &a" + Utils.formatData(disk.getReadBytes())
                            + "\n&7Disk Written: &a" + Utils.formatData(disk.getWriteBytes())));
            for (HWPartition part : disk.getPartitions()) {
                sender.spigot().sendMessage(Utils.builderHover("  &7|-- &a" + part.getIdentification() + " " + part.getType() + " &7Size: &a" + Utils.formatData(part.getSize()),
                        "&7Mount Point: &a" + part.getMountPoint() + " &7Uuid: &a" + part.getUuid()));
            }
        }
    }
}
