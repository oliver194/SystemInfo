package studio.thevipershow.systeminfo.commands;

import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.commands.register.SystemInfoCommand;
import studio.thevipershow.systeminfo.enums.Messages;
import studio.thevipershow.systeminfo.oshi.SystemValues;
import studio.thevipershow.systeminfo.plugin.SystemInfo;
import studio.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public final class CommandVmstat extends SystemInfoCommand {

    public CommandVmstat(@NotNull SystemInfo systemInfo) {
        super(systemInfo,"vmstat",
                "get info about the system memory",
                "/<command>",
                Collections.emptyList());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String name, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("systeminfo.commands.vmstat")) {
                vmstat(sender);
                return true;
            } else {
                sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        } else {
            sender.sendMessage(Messages.OUT_OF_ARGS.value(true));
        }
        return false;
    }

    private void vmstat(CommandSender sender) {
        SystemValues values = systemInfo.getsV();
        sender.sendMessage(Utils.color("&2«« &7Memory info &2»»"));
        sender.sendMessage(Utils.color("&7Available memory: &a" + values.getAvailableMemory()));
        sender.sendMessage(Utils.color("&7Allocated memory: &a" + values.getUsedMemory()));
        sender.sendMessage(Utils.color("&7Total memory: &a" + values.getMaxMemory()));
        sender.sendMessage(Utils.color("&7Swap total memory: &a" + values.getTotalSwap()));
        sender.sendMessage(Utils.color("&7Swap used memory: &a" + values.getUsedSwap()));
    }
}
