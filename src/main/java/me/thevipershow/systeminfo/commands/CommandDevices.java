package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;
import oshi.hardware.UsbDevice;

public final class CommandDevices implements Command {

    private void printDevices(CommandSender sender) {
        sender.sendMessage(Utils.color("&2» &7Attached devices &2«"));
        sender.sendMessage(Utils.color("&2» &7List:"));
        for (UsbDevice usb : SystemValues.getUsbDevices()) {
            sender.sendMessage(Utils.color(String.format("&7- &a%s %s", usb.getVendor(), usb.getSerialNumber())));
            sender.sendMessage(Utils.builderHover(" &7Serial-ID &8[&a*&8]&r", usb.getSerialNumber()));
            if (usb.getConnectedDevices().length != 0) {
                for (UsbDevice subUsb : usb.getConnectedDevices()) {
                    sender.sendMessage(Utils.color(String.format(" &7|- &a%s %s", subUsb.getVendor(), subUsb.getName())));
                }
            }
        }
    }


    @Override
    public void action(CommandSender sender, String name, String[] args) {
        if (name.equals("devices")) {
            if (sender.hasPermission("systeminfo.commands.devices")) {
                if (args.length == 0) {
                    printDevices(sender);
                } else {
                    sender.sendMessage(Messages.OUT_OF_ARGS.value(true));
                }
            } else {
                sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        }
    }
}
