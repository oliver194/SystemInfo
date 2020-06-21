package me.thevipershow.systeminfo.commands.register;

import me.thevipershow.systeminfo.commands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.util.Arrays;
import java.util.List;

public final class Manager {
    
    private static Manager instance = null;
    private final CommandMap commandMap;
    
    private Manager(CommandMap map) {
        this.commandMap = map;
    }
    
    public static Manager getInstance(CommandMap map) {
        if (instance == null) {
            instance = new Manager(map);
        }
        return instance;
    }
    
    private final List<Command> commands = Arrays.asList(
            new  CommandCpuLoad(),
            new CommandDevices(),
            new CommandDisks(),
            new CommandHtop(),
            new CommandLscpu(),
            new CommandSensors(),
            new CommandSystemInfo(),
            new CommandUptime(),
            new CommandVmstat()
    );
    
    public final void registerAll() {
        commandMap.registerAll("system", commands);
    }
}

