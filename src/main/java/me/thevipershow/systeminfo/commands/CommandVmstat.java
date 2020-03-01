package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;

public final class CommandVmstat implements Command {

    private void vmstat(CommandSender sender) {
        sender.sendMessage(Utils.color("&2«« &7Memory info &2»»"));
        sender.sendMessage(String.format(Utils.color("&7Available memory: &a%s"), SystemValues.getAvailableMemory()));
        sender.sendMessage(String.format(Utils.color("&7Allocated memory: &a%s"), SystemValues.getUsedMemory()));
        sender.sendMessage(String.format(Utils.color("&7Total memory: &a%s"), SystemValues.getMaxMemory()));
        sender.sendMessage(String.format(Utils.color("&7Swap total memory: &a%s"), SystemValues.getTotalSwap()));
        sender.sendMessage(Utils.color(String.format("&7Swap used memory: &a%s", SystemValues.getUsedSwap())));
    }

    @Override
    public boolean action(CommandSender sender, String name, String[] args) {
        if (name.equals("vmstat")) {
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
        }
        return false;
    }
}
