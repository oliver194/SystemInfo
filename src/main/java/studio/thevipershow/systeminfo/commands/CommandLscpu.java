package studio.thevipershow.systeminfo.commands;

import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.commands.register.SystemInfoCommand;
import studio.thevipershow.systeminfo.enums.Messages;
import studio.thevipershow.systeminfo.oshi.SystemValues;
import studio.thevipershow.systeminfo.plugin.SystemInfo;
import studio.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public final class CommandLscpu extends SystemInfoCommand {

    public CommandLscpu(@NotNull SystemInfo systemInfo) {
        super(systemInfo, "lscpu",
                "get information about the system processor(s)",
                "/<command>",
                Collections.emptyList());
    }
    @Override
    public boolean execute(CommandSender sender, String name, String[] args) {
        if (sender.hasPermission("systeminfo.commands.lscpu")) {
            if (args.length == 0) {
                printLscpu(sender);
                return true;
            } else {
                sender.sendMessage(Messages.OUT_OF_ARGS.value(true));
            }
        } else {
            sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
        }
        return false;
    }

    private void printLscpu(CommandSender sender) {
        SystemValues values = systemInfo.getsV();
        sender.sendMessage(Utils.color("&2«« &7Cpu info &2»»"));
        sender.sendMessage(Utils.color("&7Operating System: &a" + values.getOSFamily() + " " + values.getOSManufacturer() + " " + values.getOSVersion()));
        sender.sendMessage(Utils.color("&7Cpu Vendor: &a" + values.getCpuVendor()));
        sender.sendMessage(Utils.color("&7Cpu Model: &a" + values.getCpuModel() + " " + values.getCpuModelName()));
        sender.sendMessage(Utils.color("&7Cpu Clock Rate: &a" + values.getCpuMaxFrequency()));
        sender.sendMessage(Utils.color("&7Cpu Stepping: &a" + values.getCpuStepping()));
        sender.sendMessage(Utils.color("&7Physical Cores: &a" + values.getCpuCores()));
        sender.sendMessage(Utils.color("&7Logical Cores: &a" + values.getCpuThreads()));
    }

}
