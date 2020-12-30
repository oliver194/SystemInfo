package me.thevipershow.systeminfo;

import me.thevipershow.systeminfo.api.SysteminfoPlaceholder;
import me.thevipershow.systeminfo.commands.register.Manager;
import me.thevipershow.systeminfo.csvwriter.CSVLogger;
import me.thevipershow.systeminfo.gui.GuiClickListener;
import me.thevipershow.systeminfo.listeners.JoinNotifyListener;
import me.thevipershow.systeminfo.oshi.SystemValues;
import me.thevipershow.systeminfo.updater.DefaultConsoleUpdateHandler;
import me.thevipershow.systeminfo.updater.PluginUpdater;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import org.bukkit.plugin.PluginManager;

public final class SystemInfo extends JavaPlugin {

    private final LocalDateTime startupTime = LocalDateTime.now();
    private final PluginManager pluginManager = Bukkit.getPluginManager();
    private CSVLogger csvLogger;
    private Manager commandManager;
    private PluginUpdater pluginUpdater;
    private static SystemInfo instance;

    @Override
    public final void onLoad() {
        pluginUpdater = PluginUpdater.getInstance(this);
        pluginUpdater.addHandler(new DefaultConsoleUpdateHandler(this));
        pluginUpdater.performVersionChecks();
    }

    @Override
    public final void onEnable() {
        instance = this;

        if (pluginManager.isPluginEnabled("PlaceholderAPI")) {
            new SysteminfoPlaceholder().register();
        } else {
            getLogger().info("Could not find PlaceholderAPI, placeholders won't be available.");
        }

        try {
            commandManager = Manager.getInstance(Utils.getCommandMap());
            commandManager.registerAll();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            getLogger().warning("Could not get command map!");
            getLogger().warning("Disabling plugin . . .");
            pluginManager.disablePlugin(this);
            return;
        }
        pluginManager.registerEvents(new GuiClickListener(), this);
        csvLogger = CSVLogger.getInstance(SystemValues.getInstance(), this);
        csvLogger.startLogging();

        pluginManager.registerEvents(new JoinNotifyListener(this), this);
    }

    @Override
    public final void onDisable() {
        csvLogger.stopLogging();
    }

    public static SystemInfo getInstance() {
        return instance;
    }

    public final LocalDateTime getStartupTime() {
        return startupTime;
    }

    public final PluginUpdater getPluginUpdater() {
        return pluginUpdater;
    }
}



