package top.cmarco.systeminfo.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import top.cmarco.systeminfo.api.SystemInfoPlaceholderExtension;
import top.cmarco.systeminfo.commands.register.CommandManager;
import top.cmarco.systeminfo.gui.GuiClickListener;
import top.cmarco.systeminfo.gui.SystemInfoGui;
import top.cmarco.systeminfo.oshi.SystemValues;
import top.cmarco.systeminfo.utils.Utils;

import java.time.LocalDateTime;

/**
 * The main class of the SystemInfo Spigot plugin, responsible for initializing and managing the plugin's features.
 * This plugin provides information about the server's system and hardware.
 */
public final class SystemInfo extends JavaPlugin {

    private final LocalDateTime sT = LocalDateTime.now(); // Timestamp when the plugin was loaded.
    private final PluginManager pM = Bukkit.getPluginManager(); // The Spigot PluginManager instance.
    private CommandManager cM; // The custom CommandManager for handling plugin commands.
    private SystemInfoPlaceholderExtension pH; // PlaceholderAPI extension for custom placeholders.
    private SystemValues sV; // Manager for system information values.
    private SystemInfoGui sig; // Graphical User Interface for the plugin.

    /**
     * Initializes and loads the SystemInfoGui.
     */
    private void loadGui() {
        this.sig = new SystemInfoGui(this);
    }

    /**
     * Initializes and updates the SystemValues.
     */
    private void loadValues() {
        sV = new SystemValues(getLogger());
        sV.updateValues();
    }

    /**
     * Initializes and registers the PlaceholderAPI extension if PlaceholderAPI is available.
     * Otherwise, logs a warning message.
     */
    private void loadAPI() {
        if (pM.getPlugin("PlaceholderAPI") != null) {
            pH = new SystemInfoPlaceholderExtension(this);
            pH.register();
        } else {
            this.getLogger().info("Could not find PlaceholderAPI, placeholders won't be available.");
        }
    }

    /**
     * Initializes and registers custom commands using the CommandManager.
     * If there's an issue with accessing the command map, the plugin will be disabled.
     */
    private void loadCommands() {
        try {
            cM = new CommandManager(Utils.getCommandMap(), this);
            cM.createInstances();
            cM.registerAll();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            this.getLogger().warning("Could not get command map for SystemInfo!");
            this.getLogger().warning("Disabling plugin . . .");
            pM.disablePlugin(this);
        }
    }

    /**
     * Registers the GuiClickListener as a listener to handle GUI interactions.
     */
    private void registerListener() {
        pM.registerEvents(new GuiClickListener(), this);
    }

    /**
     * Called when the plugin is enabled. It initializes various components and registers listeners.
     */
    @Override
    public void onEnable() {
       // loadLibraries();
        loadValues();
        loadAPI();
        loadCommands();
        loadGui();
        registerListener();
    }

    // Getters for class members

    /**
     * @return Gets the timestamp this plugin was loaded at.
     */
    @NotNull
    public LocalDateTime getsT() {
        return sT;
    }

    /**
     * @return Gets the server plugin manager.
     */
    @NotNull
    public PluginManager getpM() {
        return pM;
    }

    /**
     * @return Gets the server command manager.
     */
    @NotNull
    public CommandManager getcM() {
        return cM;
    }

    /**
     * @return Gets the PlaceholderAPI extension.
     */
    @NotNull
    public SystemInfoPlaceholderExtension getpH() {
        return pH;
    }

    /**
     * @return Gets the system values class.
     */
    @NotNull
    public SystemValues getsV() {
        return sV;
    }

    /**
     * @return Gets the system info gui class.
     */
    @NotNull
    public SystemInfoGui getSig() {
        return sig;
    }
}
