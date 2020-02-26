package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;

public class CommandSensors implements Command {

    SystemValues systemValues = SystemValues.getInstance();

    @Override
    public void action(CommandSender sender, String name, String[] args) {
        if (name.equals("sensors")) {
            if (sender.hasPermission("systeminfo.commands.sensors")) {
                if (args.length == 0) {
                    sender.sendMessage(Utils.color(String.format("&7Fans RPM: &a%s", systemValues.getFansRPM())));
                    sender.sendMessage(Utils.color(String.format("&7Cpu Voltage: &a%s", systemValues.getCpuVoltage())));
                    sender.sendMessage(Utils.color(String.format("&7Cpu Temperature: %s", systemValues.getCpuTemperatureStatus())));
                }
            } else {
                sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        }
    }
}
