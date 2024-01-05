package top.cmarco.systeminfo.commands;

import org.jetbrains.annotations.NotNull;
import top.cmarco.systeminfo.enums.Messages;
import top.cmarco.systeminfo.plugin.SystemInfo;
import top.cmarco.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;
import oshi.hardware.UsbDevice;

import java.util.Collections;
/**
 * The `CommandDevices` class is a Spigot command that allows players with the appropriate permission to retrieve a list
 * of system devices using the "/devices" command.
 */
public final class CommandDevices extends SystemInfoCommand {

    /**
     * Initializes a new instance of the `CommandDevices` class.
     *
     * @param systemInfo The `SystemInfo` instance associated with this command.
     */
    public CommandDevices(@NotNull SystemInfo systemInfo) {
        super(systemInfo,"devices",
                "get a list of system devices",
                "/<command>",
                Collections.emptyList());
    }

    /**
     * Executes the "/devices" command, displaying a list of system devices to the sender.
     *
     * @param sender The command sender.
     * @param name   The command name.
     * @param args   The command arguments (not used in this command).
     * @return True if the command was executed successfully; otherwise, false.
     */
    @SuppressWarnings("NullableProblems")
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

    /**
     * Displays a list of system devices to the sender.
     *
     * @param sender The command sender.
     */
    private void printDevices(CommandSender sender) {
        sender.sendMessage(Utils.color("&2» &7Attached devices &2«"));
        sender.sendMessage(Utils.color("&2» &7List:"));
        for (UsbDevice usb : systemInfo.getsV().getUsbDevices()) {
            sender.sendMessage(Utils.color("&7- &a" + usb.getVendor() + " " + usb.getSerialNumber()));
            sender.spigot().sendMessage(Utils.builderHover(" &7Serial-ID &8[&a*&8]&r", usb.getSerialNumber()));
            if (!usb.getConnectedDevices().isEmpty()) {
                for (UsbDevice subUsb : usb.getConnectedDevices()) {
                    sender.sendMessage(Utils.color((" &7|- &a" + subUsb.getVendor() + " " + subUsb.getName())));
                }
            }
        }
    }
}