package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.entity.Player;
import oshi.hardware.UsbDevice;

public class CommandDevices implements Command {

    SystemValues systemValues = SystemValues.getInstance();

    private void printDevices(Player player) {
        player.sendMessage(Utils.color("&2» &7Attached devices &2«"));
        player.sendMessage(Utils.color("&2» &7List:"));
        for (UsbDevice usb : systemValues.getUsbDevices()) {
            player.sendMessage(Utils.color(String.format("&7- &a%s %s", usb.getVendor(), usb.getSerialNumber())));
            player.sendMessage(Utils.builderHover(" &7Serial-ID &8[&a*&8]&r", usb.getSerialNumber()));
            if (usb.getConnectedDevices().length != 0) {
                for (UsbDevice subUsb : usb.getConnectedDevices()) {
                    player.sendMessage(Utils.color(String.format(" &7|- &a%s %s", subUsb.getVendor(), subUsb.getName())));
                }
            }
        }
    }


    @Override
    public void action(Player player, String name, String[] args) {
        if (name.equals("devices")) {
            if (player.hasPermission("systeminfo.commands.devices")) {
                if (args.length == 0) {
                    printDevices(player);
                } else {
                    player.sendMessage(Messages.OUT_OF_ARGS.value(true));
                }
            } else {
                player.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        }
    }
}
