package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.SystemInfo;
import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CommandUptime implements Command {

    private void uptime(CommandSender sender) {
        final long uptime = ChronoUnit.MINUTES.between(SystemInfo.time, LocalDateTime.now());
        sender.sendMessage(Utils.color("&2»» &7Machine uptime &2««"));
        sender.sendMessage(String.format(Utils.color("&2» &7JVM Uptime: &a%s min."), uptime));
    }

    @Override
    public void action(CommandSender sender, String name, String[] args) {
        if (name.equals("uptime")) {
            if (args.length == 0) {
                if (sender.hasPermission("systeminfo.commands.uptime")) {
                    uptime(sender);
                } else {
                    sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
                }
            } else {
                sender.sendMessage(Messages.OUT_OF_ARGS.value(true));
            }
        }
    }
}
