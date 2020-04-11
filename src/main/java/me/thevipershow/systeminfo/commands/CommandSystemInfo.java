package me.thevipershow.systeminfo.commands;

import java.util.ArrayList;
import me.thevipershow.systeminfo.SystemInfo;
import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.gui.SystemInfoGui;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandSystemInfo extends Command {

    public CommandSystemInfo() {
        super("systeminfo",
                "main command of SystemInfo plugin",
                "/<command> [stats|gui|version|2]",
                new ArrayList<>());
    }

    @Override
    public boolean execute(CommandSender sender, String name, String[] args) {
        if (sender.hasPermission("systeminfo.command.help")) {
            if (args.length == 0) {
                systemInfo1(sender);
                return true;
            } else if (args.length == 1 && args[0].equals("2")) {
                systemInfo2(sender);
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("version")) {
                sender.sendMessage(String.format(Utils.color("&2» &7SystemInfo version: &a%s"), SystemInfo.getInstance().getDescription().getVersion()));
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("stats")) {
                stats(sender);
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                SystemValues.getInstance().updateValues();
                reload(sender);
            } else if (args.length == 1 && args[0].equalsIgnoreCase("gui")) {
                if (sender instanceof Player) {
                    SystemInfoGui.createGui((Player) sender);
                } else {
                    sender.sendMessage(Utils.color("&4» &cYou cannot create GUIs inside a console .-."));
                }
                return true;
            } else {
                sender.sendMessage(Messages.INVALID_ARGS.value(true));
            }
        } else {
            sender.sendMessage(Messages.NO_PERMISSIONS.value(true));
        }
        return false;
    }

    private void systemInfo1(CommandSender sender) {
        sender.sendMessage(Utils.color("&7&m&l--------------------------------------"));
        sender.spigot().sendMessage(Utils.builderHover("&7»» &fSystemInfo Help &7««", "This is the help page."));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/lscpu &aget processor info! &8[*]", "this returns processor info"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/vmstat &aget memory info! &8[*]", "this gets memory usage\nof the entire host"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/sensors &aget sensors info! &8[*]", "gets various info from sensors"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/disks &aget disks info! &8[*]", "prints out a map of disks."));
        //sender.spigot().sendMessage(Utils.builderHover("&f- &7/benchmark &aget CPU-benchmark score! &8[*]", "Makes a CPU benchmark and gives a score!")); @Deprecated
        sender.spigot().sendMessage(Utils.builderClick("&7»» Click here for second page &f[*]", "/systeminfo 2"));
        sender.sendMessage(Utils.color("&7&m&l--------------------------------------"));
    }

    private void systemInfo2(CommandSender sender) {
        sender.sendMessage(Utils.color("&7&l&m--------------------------------------"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/htop &aget processes list! &8[*]", "get a list of processes"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/systeminfo <arg> &amain command &8[*]", "args = stats, version, easteregg"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/uptime &aget the machine uptime! &8[*]", "get the total uptime of machine"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/devices &aget devices list! &8[*]", "get every attached device"));
        sender.sendMessage(Utils.color("&7&l&m--------------------------------------"));
    }

    private void stats(CommandSender sender) {
        sender.sendMessage(Utils.color("&2» &7Server stats &2«"));
        sender.sendMessage(String.format(Utils.color("&2» &7Overworld Entities: &a%s &7Loaded chunks: &a%s"), Utils.countEntitiesInWorlds(World.Environment.NORMAL), Utils.loadedChunksInWorlds(World.Environment.NORMAL)));
        sender.sendMessage(String.format(Utils.color("&2» &7Nether Entities: &a%s &7Loaded chunks: &a%s"), Utils.countEntitiesInWorlds(World.Environment.NETHER), Utils.loadedChunksInWorlds(World.Environment.NETHER)));
        sender.sendMessage(String.format(Utils.color("&2» &7End Entities: &a%s &7Loaded chunks: &a%s"), Utils.countEntitiesInWorlds(World.Environment.THE_END), Utils.loadedChunksInWorlds(World.Environment.THE_END)));
    }

    private void reload(CommandSender sender) {
        sender.sendMessage(Utils.color("&8» &aSuccesfully reloaded system values!"));
    }
}
