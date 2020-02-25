package me.thevipershow.systeminfo;

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

public final class SystemInfo extends JavaPlugin {


    public static SystemInfo instance;
    public static LocalDateTime time;
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
        completeCommandsList();
        instance = this;
        time = LocalDateTime.now();
        Bukkit.getPluginManager().registerEvents(new GuiClickListener(), instance);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            commands.forEach(command1 -> command1.action(player, command.getName(), args));
        }
        return true;
    }
}
