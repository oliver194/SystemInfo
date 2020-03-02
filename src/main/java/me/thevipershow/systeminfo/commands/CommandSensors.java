package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public final class CommandSensors extends Command {

    public CommandSensors() {
        super("sensors",
                "get information from the system sensors",
                "/<command>",
                new ArrayList<>());
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender.hasPermission("systeminfo.commands.sensors")) {
            if (args.length == 0) {
                sender.sendMessage(Utils.color(String.format("&7Fans RPM: &a%s", SystemValues.getFansRPM())));
                sender.sendMessage(Utils.color(String.format("&7Cpu Voltage: &a%s", SystemValues.getCpuVoltage())));
                sender.sendMessage(Utils.color(String.format("&7Cpu Temperature: %s", SystemValues.getCpuTemperatureStatus())));
                return true;
            }
        } else {
            sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
        }
        return false;
    }
}
