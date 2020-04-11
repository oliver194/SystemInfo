package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import oshi.util.Util;

import java.util.ArrayList;

public final class CommandCpuLoad extends Command {

    private final SystemValues values = SystemValues.getInstance();

    public CommandCpuLoad() {
        super("cpuload",
                "gets the load status of the CPU",
                "/<command>",
                new ArrayList<>());
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("systeminfo.commands.cpuload")) {
                printCpuLoad(sender);
                return true;
            } else {
                sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        } else {
            sender.sendMessage(Messages.OUT_OF_ARGS.value(true));
        }
        return false;
    }

    private long[] previousTicks;
    private long[][] previousMultiTicks;

    private double getCpuLoad() {
        previousTicks = values.getSystemCpuLoadTicks();
        previousMultiTicks = values.getProcessorCpuLoadTicks();
        Util.sleep(1000);
        return values.getSystemCpuLoadBetweenTicks(previousTicks) * 100;
    }

    private String getAverageLoads() {
        StringBuilder cpuLoads = new StringBuilder("&7Load per core:&a");
        double[] load = values.getProcessorCpuLoadBetweenTicks(previousMultiTicks);
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
}
