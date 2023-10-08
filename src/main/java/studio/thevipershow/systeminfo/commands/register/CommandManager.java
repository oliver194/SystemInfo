package studio.thevipershow.systeminfo.commands.register;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.plugin.SystemInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final CommandMap commandMap;
    private final SystemInfo systemInfo;

    private final List<Command> commands = new ArrayList<>();

    public CommandManager(@NotNull CommandMap commandMap, @NotNull SystemInfo systemInfo) {
        this.commandMap = commandMap;
        this.systemInfo = systemInfo;
    }

    public void createInstances() {
        for (CommandType value : CommandType.values()) {
            Class<? extends SystemInfoCommand> clazz = value.getClazz();
            try {
                Constructor<? extends SystemInfoCommand> constructor = clazz.getDeclaredConstructor(SystemInfo.class);
                SystemInfoCommand command = constructor.newInstance(this.systemInfo);
                commands.add(command);
            } catch (NoSuchMethodException | InvocationTargetException | RuntimeException | InstantiationException | IllegalAccessException exception) {
                systemInfo.getLogger().warning("Something went wrong while getting constructor for " + value.getDisplayName());
                systemInfo.getLogger().warning(exception.getLocalizedMessage());
            }
        }
    }
    
    public void registerAll() {
        commandMap.registerAll("systeminfo", commands);
    }
}

