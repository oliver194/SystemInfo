package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.entity.Player;

public class CommandVmstat implements Command {

    private void vmstat(Player player) {
        final long totalMemory = SystemValues.getMemory().getTotal();
        final long availableMemory = SystemValues.getMemory().getAvailable();
        final String available = Utils.formatData(availableMemory);
        final String allocated = Utils.formatData(totalMemory - availableMemory);
        final String total = Utils.formatData(totalMemory);
        final String swapTotal = Utils.formatData(SystemValues.getMemory().getVirtualMemory().getSwapTotal());
        final String swapUsed = Utils.formatData(SystemValues.getMemory().getVirtualMemory().getSwapUsed());

        player.sendMessage(Utils.color("&2«« &7Memory info &2»»"));
        player.sendMessage(String.format(Utils.color("&7Available memory: &a%s"), available));
        player.sendMessage(String.format(Utils.color("&7Allocated memory: &a%s"), allocated));
        player.sendMessage(String.format(Utils.color("&7Total memory: &a%s"), total));
        player.sendMessage(String.format(Utils.color("&7Swap total memory: &a%s"), swapTotal));
        player.sendMessage(Utils.color(String.format("&7Swap used memory: &a%s", swapUsed)));
    }

    @Override
    public void action(Player player, String name, String[] args) {
        if (name.equals("vmstat")) {
            if (args.length == 0) {
                if (player.hasPermission("systeminfo.commands.vmstat")) {
                    vmstat(player);
                } else {
                    player.sendMessage(Messages.NO_PERMISSIONS.value(true));
                }
            } else {
                player.sendMessage(Messages.OUT_OF_ARGS.value(true));
            }
        }
    }
}
