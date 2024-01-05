package top.cmarco.systeminfo.commands;

import org.jetbrains.annotations.NotNull;
import top.cmarco.systeminfo.enums.Messages;
import top.cmarco.systeminfo.plugin.SystemInfo;
import top.cmarco.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;
import oshi.software.os.OSProcess;

import java.util.Collections;
import java.util.List;

/**
 * The `CommandHtop` class is a Spigot command that allows players with the appropriate permission to view a list of
 * processes running on the system using the "/htop" command.
 */
public final class CommandHtop extends SystemInfoCommand {

    /**
     * Initializes a new instance of the `CommandHtop` class.
     *
     * @param systemInfo The `SystemInfo` instance associated with this command.
     */
    public CommandHtop(@NotNull SystemInfo systemInfo) {
        super(systemInfo, "htop",
                "shows a list of the processes running on the system",
                "/<command>",
                Collections.emptyList());
    }

    /**
     * Executes the "/htop" command, displaying a list of processes running on the system to the sender.
     *
     * @param sender The command sender.
     * @param name   The command name.
     * @param args   The command arguments (not used in this command).
     * @return True if the command was executed successfully; otherwise, false.
     */
    @Override
    public boolean execute(CommandSender sender, @NotNull String name, String[] args) {
        if (sender.hasPermission("systeminfo.commands.htop")) {
            if (args.length == 0) {
                printHtop(sender);
                return true;
            } else {
                sender.sendMessage(Messages.OUT_OF_ARGS.value(true));
            }
        } else {
            sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
        }
        return false;
    }

    private static double getOsProcPercentage(@NotNull OSProcess osProcess) {
        return 100d * (osProcess.getKernelTime() + osProcess.getUserTime()) / osProcess.getUpTime();
    }

    /**
     * Displays a list of processes running on the system to the sender.
     *
     * @param sender The command sender.
     */
    private void printHtop(CommandSender sender) {
        sender.sendMessage(Utils.color("&2« &7Htop &2»"));
        sender.sendMessage(Utils.color("&7Processes: &a" + systemInfo.getsV().getRunningProcesses() +
                " &7Threads: &a" + systemInfo.getsV().getThreadCount()));
        sender.sendMessage(Utils.color("&7    PID  %CPU %MEM     VSZ            NAME"));
        List<OSProcess> processes = systemInfo.getsV().getOSProcesses();
        processes.sort((proc1, proc2) -> (int) (getOsProcPercentage(proc2) - getOsProcPercentage(proc1)));

        for (int i = 0; i < processes.size() && i < 8; i++) {
            OSProcess osProcess = processes.get(i);
            sender.sendMessage(Utils.color(String.format(" &8%5d &7%5.1f %s %9s %9s &a%s",
                    osProcess.getProcessID(),
                    100d * (osProcess.getKernelTime() + osProcess.getUserTime()) / osProcess.getUpTime(),
                    Utils.formatData(100 * osProcess.getResidentSetSize() / systemInfo.getsV().getMaxMemory2()),
                    Utils.formatData(osProcess.getVirtualSize()),
                    Utils.formatData(osProcess.getResidentSetSize()),
                    osProcess.getName())));
        }
    }
}
