package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;
import oshi.util.Util;

public class CommandCpuLoad implements Command {

    SystemValues systemValues = SystemValues.getInstance();

    private long[] previousTicks;
    private long[][] previousMultiTicks;

    private double getCpuLoad() {
        previousTicks = systemValues.getSystemCpuLoadTicks();
        previousMultiTicks = systemValues.getProcessorCpuLoadTicks();
        Util.sleep(1000);
        return systemValues.getSystemCpuLoadBetweenTicks(previousTicks) * 100;
    }

    private String getAverageLoads() {
        StringBuilder cpuLoads = new StringBuilder("&7Load per core:&a");
        double[] load = systemValues.getProcessorCpuLoadBetweenTicks(previousMultiTicks);
        for (double average : load) {
            cpuLoads.append(String.format(" %.1f%%", average * 100));
        }
        return cpuLoads.toString();
    }

    private void printCpuLoad(CommandSender sender) {
        sender.sendMessage(Utils.color("&2» &7System load: &2«"));
        sender.sendMessage(Utils.color(String.format("&7Cpu load: &a%.2f%%", getCpuLoad())));
        sender.sendMessage(Utils.color(getAverageLoads()));
    }

    @Override
    public void action(CommandSender sender, String name, String[] args) {
        if (name.equals("cpuload")) {
            if (args.length == 0) {
                if (sender.hasPermission("systeminfo.commands.cpuload")) {
                    printCpuLoad(sender);
                } else {
                    sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
                }
            } else {
                sender.sendMessage(Messages.OUT_OF_ARGS.value(true));
            }
        }
    }
}
