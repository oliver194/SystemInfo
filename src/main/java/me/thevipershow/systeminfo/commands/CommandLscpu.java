package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import oshi.hardware.CentralProcessor;
import oshi.software.os.OperatingSystem;

public class CommandLscpu implements Command {

    SystemValues systemValues = SystemValues.getInstance();

    private void printLscpu(CommandSender sender) {
        sender.sendMessage(Utils.color("&2«« &7Cpu info &2»»"));
        sender.sendMessage(Utils.color(String.format(" &7OS: &a%s %s %s", systemValues.getOSFamily(), systemValues.getOSManufacturer(), systemValues.getOSVersion())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu vendor: &a%s", systemValues.getCpuVendor())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu model: &a%s %s", systemValues.getCpuModel(), systemValues.getCpuModelName())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu clock speed: &a%s GHz", systemValues.getCpuMaxFrequency())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu stepping: &a%s", systemValues.getCpuStepping())));
        sender.sendMessage(Utils.color(String.format(" &7Cpu c/t: &a%s/%s", systemValues.getCpuCores(), systemValues.getCpuThreads())));
    }

    @Override
    public void action(CommandSender sender, String name, String[] args) {
        if (name.equals("lscpu")) {
            if (sender.hasPermission("systeminfo.commands.lscpu")) {
                if (args.length == 0) {
                    printLscpu(sender);
                } else {
                    sender.sendMessage(Messages.OUT_OF_ARGS.value(true));
                }
            } else {
                sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        }
    }
}
