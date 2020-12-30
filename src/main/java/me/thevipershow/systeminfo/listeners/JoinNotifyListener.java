package me.thevipershow.systeminfo.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.thevipershow.systeminfo.SystemInfo;
import me.thevipershow.systeminfo.updater.PluginUpdater;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class JoinNotifyListener implements Listener {

    private final SystemInfo plugin;

    public JoinNotifyListener(SystemInfo plugin) {
        this.plugin = plugin;
    }

    public static void notifyPlayerToUpdate(CommandSender player, PluginUpdater.VersionCompareResult compareResult) {
        if (compareResult == null) {
            player.sendMessage(Utils.color("&7» &cSomething has went wrong when checking for updates, check your console!"));
        } else {
            boolean isOutdated = compareResult.isOutdated();
            if (!isOutdated) {
                player.sendMessage(Utils.color("&7» &aSystemInfo &7is up to date!"));
                player.sendMessage(Utils.color("&7  Current installed version&8: &a" + compareResult.getCurrentCompared().toString()));
            } else {
                player.sendMessage(Utils.color("&7» &aSystemInfo &cis not up to date!"));
                player.sendMessage(Utils.color("&7  Current installed version&8: &c" + compareResult.getCurrentCompared().toString()));
                player.sendMessage(Utils.color("&7  Latest available version&8: &a" + compareResult.getLatestCompared().toString()));
                player.sendMessage(Utils.color("&7  Please update your software to latest from the following link:"));
                player.sendMessage(Utils.color("&a  https://www.spigotmc.org/resources/system-info.70905/history"));
            }
        }
    }

    private final Map<UUID, Boolean> notifiedPlayers = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() || player.hasPermission("systeminfo.notify")) {
            UUID playerUUID = player.getUniqueId();
            boolean shouldNotify = this.notifiedPlayers.getOrDefault(playerUUID, true);
            if (shouldNotify) {
                notifyPlayerToUpdate(player, plugin.getPluginUpdater().getLastCompareResult());
                this.notifiedPlayers.put(playerUUID, false);
            }
        }
    }
}
