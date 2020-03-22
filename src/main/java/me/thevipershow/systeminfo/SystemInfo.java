package me.thevipershow.systeminfo;

import me.thevipershow.systeminfo.api.SysteminfoPlaceholder;
import me.thevipershow.systeminfo.commands.register.Manager;
import me.thevipershow.systeminfo.gui.GuiClickListener;
import me.thevipershow.systeminfo.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public final class SystemInfo extends JavaPlugin {

    public static String PLUGIN_VERSION;
    public static Plugin instance;
    public static LocalDateTime time;
    public static Logger logger;

    @Override
    public void onEnable() {
        instance = this;
        logger = instance.getLogger();
        time = LocalDateTime.now();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new SysteminfoPlaceholder().register();
        } else {
            logger.info("Could not find PlaceholderAPI, placeholders won't be available.");
        }

        try {
            new Manager(Utils.getCommandMap());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Bukkit.getPluginManager().registerEvents(new GuiClickListener(), instance);
    }
}
