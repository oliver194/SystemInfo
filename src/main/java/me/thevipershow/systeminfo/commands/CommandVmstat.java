package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.entity.Player;

public class CommandVmstat implements Command {

    private SystemValues systemValues = SystemValues.getInstance();

    private void vmstat(Player player) {
        player.sendMessage(Utils.color("&2«« &7Memory info &2»»"));
        player.sendMessage(String.format(Utils.color("&7Available memory: &a%s"), systemValues.getAvailableMemory()));
        player.sendMessage(String.format(Utils.color("&7Allocated memory: &a%s"), systemValues.getUsedMemory()));
        player.sendMessage(String.format(Utils.color("&7Total memory: &a%s"), systemValues.getMaxMemory()));
        player.sendMessage(String.format(Utils.color("&7Swap total memory: &a%s"), systemValues.getTotalSwap()));
        player.sendMessage(Utils.color(String.format("&7Swap used memory: &a%s", systemValues.getUsedSwap())));
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
