package studio.thevipershow.systeminfo.commands;

import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.commands.register.SystemInfoCommand;
import studio.thevipershow.systeminfo.enums.Messages;
import studio.thevipershow.systeminfo.plugin.SystemInfo;
import studio.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public final class CommandSensors extends SystemInfoCommand {

    public CommandSensors(@NotNull SystemInfo systemInfo) {
        super(systemInfo,"sensors",
                "get information from the system sensors",
                "/<command>",
                Collections.emptyList());
    }
    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender.hasPermission("systeminfo.commands.sensors")) {
            if (args.length == 0) {
                sender.sendMessage(Utils.color("&7Fans RPM: &a" + systemInfo.getsV().getFansRPM()));
                sender.sendMessage(Utils.color("&7Cpu Voltage: &a" + systemInfo.getsV().getCpuVoltage()));
                sender.sendMessage(Utils.color("&7Cpu Temperature: " + systemInfo.getsV().getCpuTemperatureStatus()));
                 return true;
            }
        } else {
            sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
        }
        return false;
    }
}
