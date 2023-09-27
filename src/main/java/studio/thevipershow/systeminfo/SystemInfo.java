package studio.thevipershow.systeminfo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import studio.thevipershow.systeminfo.api.SystemInfoPlaceholderExtension;
import studio.thevipershow.systeminfo.commands.register.Manager;
import studio.thevipershow.systeminfo.gui.GuiClickListener;
import studio.thevipershow.systeminfo.utils.Utils;

import java.time.LocalDateTime;

public final class SystemInfo extends JavaPlugin {

    private final LocalDateTime startupTime = LocalDateTime.now();
    private final PluginManager pluginManager = Bukkit.getPluginManager();
    private Manager commandManager;
    private static SystemInfo instance;
    private SystemInfoPlaceholderExtension systeminfoPlaceholder;


    @Override
    public void onEnable() {
        instance = this;

        if (pluginManager.getPlugin("PlaceholderAPI") != null) {
            systeminfoPlaceholder = new SystemInfoPlaceholderExtension();
            systeminfoPlaceholder.register();
        } else {
            this.getLogger().info("Could not find PlaceholderAPI, placeholders won't be available.");
        }

        try {
            commandManager = Manager.getInstance(Utils.getCommandMap());
            commandManager.registerAll();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            this.getLogger().warning("Could not get command map!");
            this.getLogger().warning("Disabling plugin . . .");
            pluginManager.disablePlugin(this);
            return;
        }
        pluginManager.registerEvents(new GuiClickListener(), this);
    }

    public static SystemInfo getInstance() {
        return instance;
    }

    public LocalDateTime getStartupTime() {
        return startupTime;
    }
}