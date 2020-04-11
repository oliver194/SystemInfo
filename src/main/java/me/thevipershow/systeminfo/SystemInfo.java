package me.thevipershow.systeminfo;

import me.thevipershow.systeminfo.api.SysteminfoPlaceholder;
import me.thevipershow.systeminfo.commands.register.Manager;
import me.thevipershow.systeminfo.gui.GuiClickListener;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;

public final class SystemInfo extends JavaPlugin {

    private final LocalDateTime startupTime = LocalDateTime.now();
    private static SystemInfo instance;

    @Override
    public void onEnable() {
        instance = this;

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new SysteminfoPlaceholder().register();
        } else {
            getLogger().info("Could not find PlaceholderAPI, placeholders won't be available.");
        }

        try {
            new Manager(Utils.getCommandMap());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            getLogger().warning("Could not get command map!");
            e.printStackTrace();

            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getPluginManager().registerEvents(new GuiClickListener(), instance);
    }

    public static SystemInfo getInstance() {
        return instance;
    }

    public LocalDateTime getStartupTime() {
        return startupTime;
    }
}
