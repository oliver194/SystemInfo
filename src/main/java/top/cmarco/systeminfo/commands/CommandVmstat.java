package top.cmarco.systeminfo.commands;

import org.jetbrains.annotations.NotNull;
import top.cmarco.systeminfo.enums.Messages;
import top.cmarco.systeminfo.oshi.SystemValues;
import top.cmarco.systeminfo.plugin.SystemInfo;
import top.cmarco.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.Collections;

/**
 * The `CommandVmstat` class is a Spigot command that allows players with the appropriate permission to retrieve
 * information about system memory using the "/vmstat" command.
 */
public final class CommandVmstat extends SystemInfoCommand {

    /**
     * Initializes a new instance of the `CommandVmstat` class.
     *
     * @param systemInfo The `SystemInfo` instance associated with this command.
     */
    public CommandVmstat(@NotNull SystemInfo systemInfo) {
        super(systemInfo, "vmstat",
                "get info about the system memory",
                "/<command>",
                Collections.emptyList());
    }

    /**
     * Executes the "/vmstat" command, providing information about system memory to the sender.
     *
     * @param sender The command sender.
     * @param name   The command name.
     * @param args   The command arguments (not used in this command).
     * @return True if the command was executed successfully; otherwise, false.
     */
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

    /**
     * Sends information about system memory to the specified command sender.
     *
     * @param sender The command sender.
     */
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