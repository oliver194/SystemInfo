package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.entity.Player;
import oshi.hardware.CentralProcessor;
import oshi.software.os.OperatingSystem;

public class CommandLscpu implements Command {

    SystemValues systemValues = SystemValues.getInstance();

    private void printLscpu(Player player) {
        player.sendMessage(Utils.color("&2«« &7Cpu info &2»»"));
        player.sendMessage(Utils.color(String.format(" &7OS: &a%s %s %s", systemValues.getOSFamily(), systemValues.getOSManufacturer(), systemValues.getOSVersion())));
        player.sendMessage(Utils.color(String.format(" &7Cpu vendor: &a%s", systemValues.getCpuVendor())));
        player.sendMessage(Utils.color(String.format(" &7Cpu model: &a%s %s", systemValues.getCpuModel(), systemValues.getCpuModelName())));
        player.sendMessage(Utils.color(String.format(" &7Cpu clock speed: &a%s GHz", systemValues.getCpuMaxFrequency())));
        player.sendMessage(Utils.color(String.format(" &7Cpu stepping: &a%s", systemValues.getCpuStepping())));
        player.sendMessage(Utils.color(String.format(" &7Cpu c/t: &a%s/%s", systemValues.getCpuCores(), systemValues.getCpuThreads())));
    }

    @Override
    public void action(Player player, String name, String[] args) {
        if (name.equals("lscpu")) {
            if (player.hasPermission("systeminfo.commands.lscpu")) {
                if (args.length == 0) {
                    printLscpu(player);
                } else {
                    player.sendMessage(Messages.OUT_OF_ARGS.value(true));
                }
            } else {
                player.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        }
    }
}
