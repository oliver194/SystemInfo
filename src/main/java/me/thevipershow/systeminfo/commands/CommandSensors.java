package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.entity.Player;

public class CommandSensors implements Command {

    SystemValues systemValues = SystemValues.getInstance();

    @Override
    public void action(Player player, String name, String[] args) {
        if (name.equals("sensors")) {
            if (player.hasPermission("systeminfo.commands.sensors")) {
                if (args.length == 0) {
                    player.sendMessage(Utils.color(String.format("&7Fans RPM: &a%s", systemValues.getFansRPM())));
                    player.sendMessage(Utils.color(String.format("&7Cpu Voltage: &a%s", systemValues.getCpuVoltage())));
                    player.sendMessage(Utils.color(String.format("&7Cpu Temperature: %s", systemValues.getCpuTemperatureStatus())));
                }
            } else {
                player.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        }
    }
}
