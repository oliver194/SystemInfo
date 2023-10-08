package studio.thevipershow.systeminfo.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import studio.thevipershow.systeminfo.commands.register.SystemInfoCommand;
import studio.thevipershow.systeminfo.enums.Messages;
import studio.thevipershow.systeminfo.plugin.SystemInfo;
import studio.thevipershow.systeminfo.utils.Utils;
import java.util.Collections;

public final class CommandDisks extends SystemInfoCommand {

    public CommandDisks(@NotNull SystemInfo systemInfo) {
        super(systemInfo,"disks",
                "get your system disks list",
                "/<command>",
                Collections.emptyList());
    }

    @Override
    public boolean execute(CommandSender sender, @NotNull String name, String[] args) {
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
        for (HWDiskStore disk : systemInfo.getsV().getDiskStores()) {
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
