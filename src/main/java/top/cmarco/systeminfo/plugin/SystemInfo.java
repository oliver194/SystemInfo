/*
 *     SystemInfo - The Master of Server Hardware
 *     Copyright © 2024 CMarco
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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

    private final LocalDateTime startupTime = LocalDateTime.now(); // Timestamp when the plugin was loaded.
    private final PluginManager pluginManager = Bukkit.getPluginManager(); // The Spigot PluginManager instance.
    private CommandManager commandManager; // The custom CommandManager for handling plugin commands.
    private SystemInfoPlaceholderExtension systemInfoExtension; // PlaceholderAPI extension for custom placeholders.
    private SystemValues systemValues; // Manager for system information values.
    private SystemInfoGui systemInfoGui; // Graphical User Interface for the plugin.

    /**
     * Initializes and loads the SystemInfoGui.
     */
    private void loadGui() {
        this.systemInfoGui = new SystemInfoGui(this);
    }

    /**
     * Initializes and updates the SystemValues.
     */
    private void loadValues() {
        systemValues = new SystemValues(getLogger());
        systemValues.updateValues();
    }

    /**
     * Initializes and registers the PlaceholderAPI extension if PlaceholderAPI is available.
     * Otherwise, logs a warning message.
     */
    private void loadAPI() {
        if (pluginManager.isPluginEnabled("PlaceholderAPI")) {
            systemInfoExtension = new SystemInfoPlaceholderExtension(this);
            systemInfoExtension.register();
            this.getLogger().info("Successfully Registered SystemInfo with PlaceholderAPI.");
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
            commandManager = new CommandManager(Utils.getCommandMap(), this);
            commandManager.createInstances();
            commandManager.registerAll();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            this.getLogger().warning("Could not get command map for SystemInfo!");
            this.getLogger().warning("Disabling plugin . . .");
            pluginManager.disablePlugin(this);
        }
    }

    /**
     * Registers the GuiClickListener as a listener to handle GUI interactions.
     */
    private void registerListener() {
        pluginManager.registerEvents(new GuiClickListener(), this);
    }

    /**
     * Called when the plugin is enabled. It initializes various components and registers listeners.
     */
    @Override
    public void onEnable() {
        loadValues();
        loadCommands();
        loadGui();
        loadAPI();
        registerListener();
    }

    // Getters for class members

    /**
     * @return Gets the timestamp this plugin was loaded at.
     */
    @NotNull
    public LocalDateTime getStartupTime() {
        return startupTime;
    }

    /**
     * @return Gets the server plugin manager.
     */
    @NotNull
    public PluginManager getPluginManager() {
        return pluginManager;
    }

    /**
     * @return Gets the server command manager.
     */
    @NotNull
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * @return Gets the PlaceholderAPI extension.
     */
    @NotNull
    public SystemInfoPlaceholderExtension getSystemInfoExtension() {
        return systemInfoExtension;
    }

    /**
     * @return Gets the system values class.
     */
    @NotNull
    public SystemValues getSystemValues() {
        return systemValues;
    }

    /**
     * @return Gets the system info gui class.
     */
    @NotNull
    public SystemInfoGui getSystemInfoGui() {
        return systemInfoGui;
    }
}
