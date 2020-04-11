package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public final class CommandVmstat extends Command {

    private final SystemValues values = SystemValues.getInstance();

    public CommandVmstat() {
        super("vmstat",
                "get info about the system memory",
                "/<command>",
                new ArrayList<>());
    }

    @Override
    public boolean execute(CommandSender sender, String name, String[] args) {
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
        sender.sendMessage(Utils.color("&2«« &7Memory info &2»»"));
        sender.sendMessage(String.format(Utils.color("&7Available memory: &a%s"), values.getAvailableMemory()));
        sender.sendMessage(String.format(Utils.color("&7Allocated memory: &a%s"), values.getUsedMemory()));
        sender.sendMessage(String.format(Utils.color("&7Total memory: &a%s"), values.getMaxMemory()));
        sender.sendMessage(String.format(Utils.color("&7Swap total memory: &a%s"), values.getTotalSwap()));
        sender.sendMessage(Utils.color(String.format("&7Swap used memory: &a%s", values.getUsedSwap())));
    }
}
