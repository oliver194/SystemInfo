package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.entity.Player;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.util.Arrays;
import java.util.List;

public class CommandHtop implements Command {

    private final SystemValues systemValues = SystemValues.getInstance();
    private OSProcess osProcess;

    private void printHtop(Player player) {
        player.sendMessage(Utils.color("&2« &7Htop &2»"));
        player.sendMessage(Utils.color(String.format("&7Processes: &a%d &7Threads: &a%d", systemValues.getRunningProcesses(), systemValues.getThreadCount())));
        player.sendMessage(Utils.color("&7    PID  %CPU %MEM     VSZ            NAME"));
        List<OSProcess> processes = Arrays.asList(systemValues.getOSProcesses(9, OperatingSystem.ProcessSort.CPU));
        for (int i = 0; i < processes.size() && i < 8; i++) {
            osProcess = processes.get(i);
            player.sendMessage(Utils.color(String.format(" &8%5d &7%5.1f %s %9s %9s &a%s",
                    osProcess.getProcessID(),
                    100d * (osProcess.getKernelTime() + osProcess.getUserTime()) / osProcess.getUpTime(),
                    Utils.formatData(100 * osProcess.getResidentSetSize() / systemValues.getMaxMemory2()),
                    Utils.formatData(osProcess.getVirtualSize()),
                    Utils.formatData(osProcess.getResidentSetSize()),
                    osProcess.getName())));
        }
    }

    @Override
    public void action(Player player, String name, String[] args) {
        if (name.equals("htop")) {
            if (player.hasPermission("systeminfo.commands.htop")) {
                if (args.length == 0) {
                    printHtop(player);
                } else {
                    player.sendMessage(Messages.OUT_OF_ARGS.value(true));
                }
            } else {
                player.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        }
    }
}
