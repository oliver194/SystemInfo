package studio.thevipershow.systeminfo.commands;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.SystemInfo;
import studio.thevipershow.systeminfo.enums.Messages;
import studio.thevipershow.systeminfo.oshi.SystemValues;
import studio.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import oshi.util.Util;

public final class CommandCpuLoad extends Command {

    private final SystemValues values = SystemValues.getInstance();


    public CommandCpuLoad() {
        super("cpuload",
                "gets the load status of the CPU",
                "/<command>",
                Collections.emptyList());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, String[] args) {
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

    private CompletableFuture<Double> getCpuLoad() {
        final CompletableFuture<Double> future = new CompletableFuture<>();
        final Plugin plugin = SystemInfo.getInstance();
        final BukkitScheduler scheduler = plugin.getServer().getScheduler();

        scheduler.runTaskAsynchronously(plugin, () -> {
            previousTicks = values.getSystemCpuLoadTicks();
            previousMultiTicks = values.getProcessorCpuLoadTicks();
            Util.sleep(1000);
            scheduler.runTask(plugin, () -> future.complete(values.getSystemCpuLoadBetweenTicks(previousTicks) * 100));
        });

        return future;
    }

    private CompletableFuture<String> getAverageLoads() {
        final CompletableFuture<String> future = new CompletableFuture<>();
        final Plugin plugin = SystemInfo.getInstance();
        final BukkitScheduler scheduler = plugin.getServer().getScheduler();

        scheduler.runTaskAsynchronously(plugin, () -> {
            final StringBuilder cpuLoads = new StringBuilder("&7Load per core:&a");
            double[] load = values.getProcessorCpuLoadBetweenTicks(previousMultiTicks);
            for (final double average : load) {
                cpuLoads.append(String.format(" %.1f%%", average * 100));
            }
            scheduler.runTask(plugin, () -> future.complete(cpuLoads.toString()));
        });

        return future;
    }

    private void printCpuLoad(CommandSender sender) {
        sender.sendMessage(Utils.color("&2» &7System load: &2«"));
        getCpuLoad().thenAccept(d -> {
            sender.sendMessage(Utils.color(String.format("&7Cpu load: &a%.2f%%", d)));
            getAverageLoads().thenAccept(str -> sender.sendMessage(Utils.color(str)));
        });
    }
}
