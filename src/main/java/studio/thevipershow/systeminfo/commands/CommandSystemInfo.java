package studio.thevipershow.systeminfo.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.commands.register.SystemInfoCommand;
import studio.thevipershow.systeminfo.plugin.SystemInfo;
import studio.thevipershow.systeminfo.enums.Messages;
import studio.thevipershow.systeminfo.utils.Utils;
import static org.bukkit.World.Environment.NETHER;
import static org.bukkit.World.Environment.NORMAL;
import static org.bukkit.World.Environment.THE_END;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandSystemInfo extends SystemInfoCommand {

    public CommandSystemInfo(@NotNull SystemInfo systemInfo) {
        super(systemInfo, "systeminfo",
                "main command of SystemInfo plugin",
                "/<command> [stats|version|reload|gui]",
                Collections.emptyList());
    }
    @Override
    public boolean execute(CommandSender sender, @NotNull String name, String[] args) {
        if (sender.hasPermission("systeminfo.commands.help")) {
            if (args.length == 0) {
                systemInfo1(sender);
                return true;
            } else if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "2":
                        systemInfo2(sender);
                        break;
                    case "version":
                        sender.sendMessage(Utils.color("&2» &7SystemInfo version: &a" + systemInfo.getDescription().getVersion()));
                        break;
                    case "stats":
                        stats(sender);
                        break;
                    case "reload":
                        reload(sender);
                        break;
                    case "gui":
                        if (sender instanceof Player) {
                            systemInfo.getSig().createGui((Player) sender);
                        } else {
                            sender.sendMessage(Utils.color("&4» &cYou cannot create GUIs inside a console"));
                        }
                        break;
                    default:
                        sender.sendMessage(Messages.INVALID_ARGS.value(true));
                        break;
                }
            }
        }
        return true;
    }

    private void systemInfo1(CommandSender sender) {
        sender.sendMessage(Utils.color("&7&m&l--------------------------------------"));
        sender.spigot().sendMessage(Utils.builderHover("&7»» &fSystemInfo Help &7««", "This is the help page."));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/lscpu &aget processor info! &8[&7*&8]", "this returns processor info"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/vmstat &aget memory info! &8[&7*&8]", "this gets memory usage\nof the entire host"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/sensors &aget sensors info! &8[&7*&8]", "gets various info from sensors"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/disks &aget disks info! &8[&7*&8]", "prints out a map of disks."));
        sender.spigot().sendMessage(Utils.builderClick("&7»» Click here for second page &8[&7*&8]", "/systeminfo 2"));
        sender.sendMessage(Utils.color("&7&m&l--------------------------------------"));
    }

    private void systemInfo2(CommandSender sender) {
        sender.sendMessage(Utils.color("&7&l&m--------------------------------------"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/htop &aget processes list! &8[&7*&8]", "get a list of processes"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/systeminfo [version&f|&7stats&f|&7gui] &amain command &8[&7*&8]", "available args = stats, version, gui"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/uptime &aget the machine uptime! &8[&7*&8]", "get the total uptime of machine"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/devices &aget devices list! &8[&7*&8]", "get every attached device"));
        sender.spigot().sendMessage(Utils.builderHover("&f- &7/cpuload &aget the CPU load! &8[&7&8]", "Get CPU current percentage load"));
        sender.spigot().sendMessage(Utils.builderClick("&f- &7/speedtest &aBenchmark your network! &8[&7&8]", "Get your download\\upload speeds"));
        sender.sendMessage(Utils.color("&7&l&m--------------------------------------"));
    }

    public long folderFileSize(File folder) {
        if (folder == null) return -1;

        try (Stream<Path> paths = Files.walk(folder.toPath())) {
            return paths.mapToLong(p -> p.toFile().length()).sum();
        } catch (IOException ioException) {
            systemInfo.getLogger().warning("An exception has occured while calculating folder size of " + folder.getAbsolutePath());
            systemInfo.getLogger().warning(ioException.getLocalizedMessage());
        }
        return -1L;
    }

    private void stats(CommandSender sender) {
        sender.sendMessage(Utils.color("&2» &7Server stats &2«"));
        sender.sendMessage(Utils.color("&2» &7Overworld Entities: &a" + Utils.countEntitiesInWorlds(NORMAL) + " &7Loaded Chunks: &a" + Utils.loadedChunksInWorlds(NORMAL)));
        sender.sendMessage(Utils.color("&2» &7Nether Entities: &a" + Utils.countEntitiesInWorlds(NETHER) + " &7Loaded Chunks: &a" + Utils.loadedChunksInWorlds(NETHER)));
        sender.sendMessage(Utils.color("&2» &7End Entities: &a" + Utils.countEntitiesInWorlds(THE_END) + " &7Loaded Chunks: &a" + Utils.loadedChunksInWorlds(THE_END)));
        sender.sendMessage(Utils.color("&2» &7Server File Size: &a" + Utils.formatData(folderFileSize(systemInfo.getServer().getWorldContainer()))));
    }

    private void reload(CommandSender sender) {
        sender.sendMessage(Utils.color("&8» &aSuccesfully reloaded system values!"));
    }
}
