package me.thevipershow.systeminfo.commands;

import me.thevipershow.systeminfo.SystemInfo;
import me.thevipershow.systeminfo.enums.Messages;
import me.thevipershow.systeminfo.gui.SystemInfoGui;
import me.thevipershow.systeminfo.interfaces.Command;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CommandSystemInfo implements Command {

    private void systemInfo1(Player player) {
        player.sendMessage(Utils.color("&7&m&l--------------------------------------"));
        player.spigot().sendMessage(Utils.builderHover("&7»» &fSystemInfo Help &7««", "This is the help page."));
        player.spigot().sendMessage(Utils.builderHover("&f- &7/lscpu &aget processor info! &8[*]", "this returns processor info"));
        player.spigot().sendMessage(Utils.builderHover("&f- &7/vmstat &aget memory info! &8[*]", "this gets memory usage\nof the entire host"));
        player.spigot().sendMessage(Utils.builderHover("&f- &7/cputemp &aget sensors info! &8[*]", "gets various info from sensors"));
        player.spigot().sendMessage(Utils.builderHover("&f- &7/disks &aget disks info! &8[*]", "prints out a map of disks."));
        player.spigot().sendMessage(Utils.builderHover("&f- &7/benchmark &aget CPU-benchmark score! &8[*]", "Makes a CPU benchmark and gives a score!"));
        player.spigot().sendMessage(Utils.builderClick("&7»» Click here for second page &f[*]", "/systeminfo 2"));
        player.sendMessage(Utils.color("&7&m&l--------------------------------------"));
    }

    private void systemInfo2(Player player) {
        player.sendMessage(Utils.color("&7&l&m---------------------------------"));
        player.spigot().sendMessage(Utils.builderHover("&f- &7/htop &aget processes list! &8[*]", "get a list of processes"));
        player.spigot().sendMessage(Utils.builderHover("&f- &7/systeminfo <arg> &amain command &8[*]", "args = stats, version, easteregg"));
        player.spigot().sendMessage(Utils.builderHover("&f- &7/uptime &aget the machine uptime! &8[*]", "get the total uptime of machine"));
        player.spigot().sendMessage(Utils.builderHover("&f- &7/devices &aget devices list! &8[*]", "get every attached device"));
        player.sendMessage(Utils.color("&7&l&m---------------------------------"));
    }

    private void stats(Player player) {
        player.sendMessage(Utils.color("&2» &7Server stats &2«"));
        player.sendMessage(String.format(Utils.color("&2» &7Overworld Entities: &a%s &7Loaded chunks: &a%s"), Utils.countEntitiesInWorlds(World.Environment.NORMAL), Utils.loadedChunksInWorlds(World.Environment.NORMAL)));
        player.sendMessage(String.format(Utils.color("&2» &7Nether Entities: &a%s &7Loaded chunks: &a%s"), Utils.countEntitiesInWorlds(World.Environment.NETHER), Utils.loadedChunksInWorlds(World.Environment.NETHER)));
        player.sendMessage(String.format(Utils.color("&2» &7End Entities: &a%s &7Loaded chunks: &a%s"), Utils.countEntitiesInWorlds(World.Environment.THE_END), Utils.loadedChunksInWorlds(World.Environment.THE_END)));
    }

    @Override
    public void action(Player player, String name, String[] args) {
        if (name.equals("systeminfo")) {
            if (player.hasPermission("systeminfo.command.help")) {
                if (args.length == 0) {
                    systemInfo1(player);
                } else if (args.length == 1 && args[0].equals("2")) {
                    systemInfo2(player);
                } else if (args.length == 1 && args[0].equals("version")) {
                    player.sendMessage(String.format(Utils.color("&2» &7SystemInfo version: &a%s"), SystemInfo.instance.getDescription().getVersion()));
                } else if (args.length == 1 && args[0].equals("stats")) {
                    stats(player);
                } else if (args.length == 1 && args[0].equals("gui")) {
                    SystemInfoGui gui = new SystemInfoGui(player);
                } else {
                    player.sendMessage(Messages.INVALID_ARGS.value(true));
                }
            } else {
                player.sendMessage(Messages.NO_PERMISSIONS.value(true));
            }
        }
    }
}
