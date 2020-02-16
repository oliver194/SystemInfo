package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.entity.Player;
import oshi.hardware.CentralProcessor;
import oshi.software.os.OperatingSystem;

public class CommandLscpu implements Command {

    private final OperatingSystem os = SystemValues.getOperatingSystem();
    private final CentralProcessor cpu = SystemValues.getCentralProcessor();
    private final CentralProcessor.ProcessorIdentifier cpuIdentifier = cpu.getProcessorIdentifier();

    private String cpuVendor() {
        String cpuVendorName = cpuIdentifier.getVendor().toLowerCase();
        if (cpuVendorName.contains("amd")) {
            return "Amd";
        } else if (cpuVendorName.contains("intel")) {
            return "Intel";
        } else {
            return "Unknown";
        }
    }

    private void printLscpu(Player player) {
        player.sendMessage(Utils.color("&2«« &7Cpu info &2»»"));
        player.sendMessage(Utils.color(String.format(" &7Os: &a%s %s %s", os.getFamily(), os.getManufacturer(), os.getVersionInfo().getVersion())));
        player.sendMessage(Utils.color(String.format(" &7Cpu vendor: &a%s", cpuVendor())));
        player.sendMessage(Utils.color(String.format(" &7Cpu model: &a%s %s", cpuIdentifier.getModel(), cpuIdentifier.getName())));
        player.sendMessage(Utils.color(String.format(" &7Cpu clock speed: &a%s GHz", cpu.getMaxFreq()/1e9 )));
        player.sendMessage(Utils.color(String.format(" &7Cpu stepping: &a%s", cpuIdentifier.getStepping())));
        player.sendMessage(Utils.color(String.format(" &7Cpu c/t: &a%s/%s", cpu.getPhysicalProcessorCount(), cpu.getLogicalProcessorCount())));
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
