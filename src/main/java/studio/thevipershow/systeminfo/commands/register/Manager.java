package studio.thevipershow.systeminfo.commands.register;

import com.google.common.collect.ImmutableList;
import java.util.List;
import studio.thevipershow.systeminfo.commands.CommandCpuLoad;
import studio.thevipershow.systeminfo.commands.CommandDevices;
import studio.thevipershow.systeminfo.commands.CommandDisks;
import studio.thevipershow.systeminfo.commands.CommandHtop;
import studio.thevipershow.systeminfo.commands.CommandLscpu;
import studio.thevipershow.systeminfo.commands.CommandSensors;
import studio.thevipershow.systeminfo.commands.CommandSpeedtest;
import studio.thevipershow.systeminfo.commands.CommandSystemInfo;
import studio.thevipershow.systeminfo.commands.CommandUptime;
import studio.thevipershow.systeminfo.commands.CommandVmstat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

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
    
    private final List<Command> commands = ImmutableList.of(
            new CommandCpuLoad(),
            new CommandDevices(),
            new CommandDisks(),
            new CommandHtop(),
            new CommandLscpu(),
            new CommandSensors(),
            new CommandSystemInfo(),
            new CommandUptime(),
            new CommandVmstat(),
            new CommandSpeedtest()
    );
    
    public final void registerAll() {
        commandMap.registerAll("systeminfo", commands);
    }
}

