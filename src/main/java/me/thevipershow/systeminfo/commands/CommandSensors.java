package me.thevipershow.systeminfo.commands;

import java.util.Collections;
import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public final class CommandSensors extends Command {

    private final SystemValues values = SystemValues.getInstance();

    public CommandSensors() {
        super("sensors",
                "get information from the system sensors",
                "/<command>",
                Collections.emptyList());
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender.hasPermission("systeminfo.commands.sensors")) {
            if (args.length == 0) {
                sender.sendMessage(Utils.color("&7Fans RPM: &a" + values.getFansRPM()));
                sender.sendMessage(Utils.color("&7Cpu Voltage: &a" + values.getCpuVoltage()));
                sender.sendMessage(Utils.color("&7Cpu Temperature: " + values.getCpuTemperatureStatus()));
                return true;
            }
        } else {
            sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
        }
        return false;
    }
}
