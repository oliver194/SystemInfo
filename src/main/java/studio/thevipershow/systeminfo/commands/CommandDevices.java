package studio.thevipershow.systeminfo.commands;

import java.util.Collections;
import studio.thevipershow.systeminfo.enums.Messages;
import studio.thevipershow.systeminfo.oshi.SystemValues;
import studio.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import oshi.hardware.UsbDevice;

public final class CommandDevices extends Command {

    private final SystemValues values = SystemValues.getInstance();

    public CommandDevices() {
        super("devices",
                "get a list of system devices",
                "/<command>",
                Collections.emptyList());
    }

    @Override
    public boolean execute(CommandSender sender, String name, String[] args) {
        if (sender.hasPermission("systeminfo.commands.devices")) {
            if (args.length == 0) {
                printDevices(sender);
                return true;
            } else {
                sender.sendMessage(Messages.OUT_OF_ARGS.value(true));
            }
        } else {
            sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
        }
        return false;
    }

    private void printDevices(CommandSender sender) {
        sender.sendMessage(Utils.color("&2» &7Attached devices &2«"));
        sender.sendMessage(Utils.color("&2» &7List:"));
        for (UsbDevice usb : values.getUsbDevices()) {
            sender.sendMessage(Utils.color("&7- &a" + usb.getVendor() + " " + usb.getSerialNumber()));
            sender.spigot().sendMessage(Utils.builderHover(" &7Serial-ID &8[&a*&8]&r", usb.getSerialNumber()));
            if (usb.getConnectedDevices().size() != 0) {
                for (UsbDevice subUsb : usb.getConnectedDevices()) {
                    sender.sendMessage(Utils.color((" &7|- &a" + subUsb.getVendor() + " " + subUsb.getName())));
                }
            }
        }
    }
}
