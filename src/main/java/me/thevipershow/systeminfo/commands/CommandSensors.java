package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.entity.Player;

public class CommandSensors implements Command {

    private String getRpm() {
        StringBuilder rpmValues = new StringBuilder("&7Fans RPM:&a");
        final int[] rpmArray = SystemValues.getSensors().getFanSpeeds();
        if (rpmArray.length > 0) {
            for (int value : rpmArray) {
                rpmValues.append(String.format(" %d", value));
            }
        } else {
            rpmValues.append(" &cNot available");
        }
        return rpmValues.toString();
    }

    private String getVoltage() {
        String cpuVoltage = Double.toString(SystemValues.getSensors().getCpuVoltage());
        String voltageRevised = cpuVoltage.isBlank() ? "&cNot available" : cpuVoltage;
        return String.format("&7Cpu voltage: &a%s",
                voltageRevised);
    }

    private String getTemperature() {
        StringBuilder temperature = new StringBuilder("&7Cpu temperature:");
        double degrees = SystemValues.getSensors().getCpuTemperature();
        if (degrees >= 0d) {
            if (degrees <= 55.0d) {
                temperature.append(String.format(" &a%.1f &2Idle", degrees));
            } else if (degrees <= 75.0d) {
                temperature.append(String.format(" &6%.1f &6Load", degrees));
            } else {
                temperature.append(String.format(" &c%.1f &cOverload", degrees));
            }
        } else {
            temperature.append(" &cNot available");
        }
        return temperature.toString();
    }

    @Override
    public void action(Player player, String name, String[] args) {
        if (name.equals("sensors")) {
            if (player.hasPermission("systeminfo.commands.sensors")) {
                if (args.length == 0) {
                    player.sendMessage(Utils.color(getRpm()));
                    player.sendMessage(Utils.color(getVoltage()));
                    player.sendMessage(Utils.color(getTemperature()));
                }
            } else {
                player.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        }
    }
}
