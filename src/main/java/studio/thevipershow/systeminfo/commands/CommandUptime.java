package studio.thevipershow.systeminfo.commands;

import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.commands.register.SystemInfoCommand;
import studio.thevipershow.systeminfo.plugin.SystemInfo;
import studio.thevipershow.systeminfo.enums.Messages;
import studio.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

public final class CommandUptime extends SystemInfoCommand {

    public CommandUptime(@NotNull SystemInfo systemInfo) {
        super(systemInfo,"uptime",
                "get the JVM uptime",
                "/<command>",
                Collections.emptyList());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String name, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("systeminfo.commands.uptime")) {
                uptime(sender);
                return true;
            } else {
                sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        } else {
            sender.sendMessage(Messages.OUT_OF_ARGS.value(true));
        }
        return false;
    }

    private void uptime(CommandSender sender) {
        final long uptime = ChronoUnit.MINUTES.between(systemInfo.getsT(), LocalDateTime.now());
        sender.sendMessage(Utils.color("&2»» &7Machine uptime &2««"));
        sender.sendMessage(Utils.color("&2» &7JVM Uptime: &a" + uptime + " min."));
    }
}

