package me.thevipershow.systeminfo.commands;

import java.util.Collections;
import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CommandHtop extends Command {

    private final SystemValues values = SystemValues.getInstance();

    public CommandHtop() {
        super("htop",
                "shows a list of the processes running on the system",
                "/<command>",
                Collections.emptyList());
    }

    @Override
    public boolean execute(CommandSender sender, String name, String[] args) {

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

    private void printHtop(CommandSender sender) {
        sender.sendMessage(Utils.color("&2« &7Htop &2»"));
        sender.sendMessage(Utils.color("&7Processes: &a" + values.getRunningProcesses() + " &7Threads: &a" + values.getThreadCount()));
        sender.sendMessage(Utils.color("&7    PID  %CPU %MEM     VSZ            NAME"));
        List<OSProcess> processes = Arrays.asList(values.getOSProcesses(9, OperatingSystem.ProcessSort.CPU));
        for (int i = 0; i < processes.size() && i < 8; i++) {
            OSProcess osProcess = processes.get(i);
            sender.sendMessage(Utils.color(String.format(" &8%5d &7%5.1f %s %9s %9s &a%s",
                    osProcess.getProcessID(),
                    100d * (osProcess.getKernelTime() + osProcess.getUserTime()) / osProcess.getUpTime(),
                    Utils.formatData(100 * osProcess.getResidentSetSize() / values.getMaxMemory2()),
                    Utils.formatData(osProcess.getVirtualSize()),
                    Utils.formatData(osProcess.getResidentSetSize()),
                    osProcess.getName())));
        }
    }
}
