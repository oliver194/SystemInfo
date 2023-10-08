package studio.thevipershow.systeminfo.commands;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.commands.register.SystemInfoCommand;
import studio.thevipershow.systeminfo.plugin.SystemInfo;
import studio.thevipershow.systeminfo.enums.Messages;
import studio.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import oshi.util.Util;
/**
 * The `CommandCpuLoad` class is a Spigot command that allows players with the appropriate permission to retrieve
 * information about the CPU load using the "/cpuload" command.
 */
public final class CommandCpuLoad extends SystemInfoCommand {

    // Stores previous CPU tick values for load calculation
    private long[] previousTicks;
    private long[][] previousMultiTicks;

    /**
     * Initializes a new instance of the `CommandCpuLoad` class.
     *
     * @param systemInfo The `SystemInfo` instance associated with this command.
     */
    public CommandCpuLoad(@NotNull SystemInfo systemInfo) {
        super(systemInfo, "cpuload",
                "gets the load status of the CPU",
                "/<command>",
                Collections.emptyList());
    }

    /**
     * Executes the "/cpuload" command, displaying the CPU load information to the sender.
     *
     * @param sender The command sender.
     * @param s      The command name.
     * @param args   The command arguments (not used in this command).
     * @return True if the command was executed successfully; otherwise, false.
     */
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

    /**
     * Asynchronously retrieves the CPU load and calculates the system's average CPU load.
     *
     * @return A CompletableFuture that holds the CPU load percentage.
     */
    private CompletableFuture<Double> getCpuLoad() {
        final CompletableFuture<Double> future = new CompletableFuture<>();
        final BukkitScheduler scheduler = systemInfo.getServer().getScheduler();

        scheduler.runTaskAsynchronously(systemInfo, () -> {
            previousTicks = systemInfo.getsV().getSystemCpuLoadTicks();
            previousMultiTicks = systemInfo.getsV().getProcessorCpuLoadTicks();
            Util.sleep(1000);
            scheduler.runTask(systemInfo,
                    () -> future.complete(systemInfo.getsV().getSystemCpuLoadBetweenTicks(previousTicks) * 100));
        });

        return future;
    }

    /**
     * Asynchronously retrieves the average CPU load per core.
     *
     * @return A CompletableFuture that holds the string representation of average CPU loads.
     */
    private CompletableFuture<String> getAverageLoads() {
        final CompletableFuture<String> future = new CompletableFuture<>();
        final BukkitScheduler scheduler = systemInfo.getServer().getScheduler();

        scheduler.runTaskAsynchronously(systemInfo, () -> {
            StringBuilder cpuLoads = new StringBuilder("&7Load per core:&a");
            double[] load = systemInfo.getsV().getProcessorCpuLoadBetweenTicks(previousMultiTicks);
            for (final double average : load) {
                cpuLoads.append(String.format(" %.1f%%", average * 100));
            }
            scheduler.runTask(systemInfo, () -> future.complete(cpuLoads.toString()));
        });

        return future;
    }

    /**
     * Displays CPU load information to the sender.
     *
     * @param sender The command sender.
     */
    private void printCpuLoad(CommandSender sender) {
        sender.sendMessage(Utils.color("&2» &7System load: &2«"));
        getCpuLoad().thenAccept(d -> {
            sender.sendMessage(Utils.color(String.format("&7Cpu load: &a%.2f%%", d)));
            getAverageLoads().thenAccept(str -> sender.sendMessage(Utils.color(str)));
        });
    }
}