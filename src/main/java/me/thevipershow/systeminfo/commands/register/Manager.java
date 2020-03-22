package me.thevipershow.systeminfo.commands.register;

import me.thevipershow.systeminfo.commands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.util.Arrays;
import java.util.List;

public class Manager {
    private static final List<Command> commands = Arrays.asList(
         //   new CommandBenchmark(), REMOVED , UNSTABLE ON MANY SERVERS
            new CommandCpuLoad(),
            new CommandDevices(),
            new CommandDisks(),
            new CommandHtop(),
            new CommandLscpu(),
            new CommandSensors(),
            new CommandSystemInfo(),
            new CommandUptime(),
            new CommandVmstat()
    );

    public Manager(CommandMap map) {
        map.registerAll("system", commands);
    }


}
