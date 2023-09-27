package studio.thevipershow.systeminfo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import studio.thevipershow.systeminfo.api.SysteminfoPlaceholder;
import studio.thevipershow.systeminfo.commands.register.Manager;
import studio.thevipershow.systeminfo.gui.GuiClickListener;
import studio.thevipershow.systeminfo.listeners.JoinNotifyListener;
import studio.thevipershow.systeminfo.utils.Utils;

import java.time.LocalDateTime;

public final class SystemInfo extends JavaPlugin {

    private final LocalDateTime startupTime = LocalDateTime.now();
    private final PluginManager pluginManager = Bukkit.getPluginManager();
    private Manager commandManager;
    private JoinNotifyListener joinNotifyListener;
    private static SystemInfo instance;
    private SysteminfoPlaceholder systeminfoPlaceholder;
    private final static long EIGHT_HOURS_TICKS = 20L * 60L * 60L * 8L;

    @Override
    public void onEnable() {
        instance = this;

        if (pluginManager.getPlugin("PlaceholderAPI") != null) {
            systeminfoPlaceholder = new SysteminfoPlaceholder();
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
        // csvLogger = CSVLogger.getInstance(SystemValues.getInstance(), this);
        // csvLogger.startLogging();

        // joinNotifyListener = new JoinNotifyListener(this);
        //pluginManager.registerEvents(joinNotifyListener, this);
    }

    @Override
    public void onDisable() {
        // csvLogger.stopLogging();
    }

    public static SystemInfo getInstance() {
        return instance;
    }

    public LocalDateTime getStartupTime() {
        return startupTime;
    }

    public JoinNotifyListener getJoinNotifyListener() {
        return joinNotifyListener;
    }
}



