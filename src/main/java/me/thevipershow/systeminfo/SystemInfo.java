package me.thevipershow.systeminfo;

import me.thevipershow.systeminfo.api.SysteminfoPlaceholder;
import me.thevipershow.systeminfo.commands.*;
import me.thevipershow.systeminfo.gui.GuiClickListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class SystemInfo extends JavaPlugin {


    public static final String PLUGIN_VERSION = "1.0.2";
    public static SystemInfo instance;
    public static LocalDateTime time;
    public static Logger logger;
    private final List<me.thevipershow.systeminfo.interfaces.Command> commands = new ArrayList<>();

    /**
     * This fills {@link SystemInfo#commands} with all the Classes implementing
     * the Command interface.
     */
    private void completeCommandsList() {
        commands.add(new CommandCpuLoad());
        commands.add(new CommandDevices());
        commands.add(new CommandDisks());
        commands.add(new CommandHtop());
        commands.add(new CommandLscpu());
        commands.add(new CommandSystemInfo());
        commands.add(new CommandUptime());
        commands.add(new CommandVmstat());
        commands.add(new CommandSensors());
    }

    @Override
    public void onEnable() {
        instance = this;
        logger = instance.getLogger();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new SysteminfoPlaceholder().register();
        } else {
            logger.info("Could not find PlaceholderAPI, placeholders won't be available.");
        }


        completeCommandsList();
        time = LocalDateTime.now();
        Bukkit.getPluginManager().registerEvents(new GuiClickListener(), instance);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        commands.forEach(c -> c.action(sender, command.getName(), args));
        return true;
    }
}
