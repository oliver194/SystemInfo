package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public final class CommandLscpu extends Command {

    public CommandLscpu() {
        super("lscpu",
                "get information about the system processor(s)",
                "/<command>",
                new ArrayList<>());
    }

    @Override
    public boolean execute(CommandSender sender, String name, String[] args) {
        if (sender.hasPermission("systeminfo.commands.lscpu")) {
            if (args.length == 0) {
                printLscpu(sender);
                return true;
            } else {
                sender.sendMessage(Messages.OUT_OF_ARGS.value(true));
            }
        } else {
            sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
        }
        return false;
    }

    private void printLscpu(CommandSender sender) {
        sender.sendMessage(Utils.color("&2«« &7Cpu info &2»»"));
        sender.sendMessage(Utils.color(String.format(" &7OS: &a%s %s %s", SystemValues.getOSFamily(), SystemValues.getOSManufacturer(), SystemValues.getOSVersion())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu vendor: &a%s", SystemValues.getCpuVendor())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu model: &a%s %s", SystemValues.getCpuModel(), SystemValues.getCpuModelName())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu clock speed: &a%s GHz", SystemValues.getCpuMaxFrequency())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu stepping: &a%s", SystemValues.getCpuStepping())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu c/t: &a%s/%s", SystemValues.getCpuCores(), SystemValues.getCpuThreads())));
    }
}
