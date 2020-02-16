package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.SystemInfo;
import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CommandUptime implements Command {

    private void uptime(Player player) {
        final long uptime = ChronoUnit.MINUTES.between(SystemInfo.time, LocalDateTime.now());
        player.sendMessage(Utils.color("&2»» &7Machine uptime &2««"));
        player.sendMessage(String.format(Utils.color("&2» &7JVM Uptime: &a%s min."), uptime));
    }

    @Override
    public void action(Player player, String name, String[] args) {
        if (name.equals("uptime")) {
            if (args.length == 0) {
                if (player.hasPermission("systeminfo.commands.uptime")) {
                    uptime(player);
                } else {
                    player.sendMessage(Messages.NO_PERMISSIONS.value(true));
                }
            } else {
                player.sendMessage(Messages.OUT_OF_ARGS.value(true));
            }
        }
    }
}
