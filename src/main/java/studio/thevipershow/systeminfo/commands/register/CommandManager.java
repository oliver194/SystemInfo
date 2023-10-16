package studio.thevipershow.systeminfo.commands.register;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.commands.SystemInfoCommand;
import studio.thevipershow.systeminfo.plugin.SystemInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages and registers system information commands in your Spigot plugin.
 * <p>
 * This class is responsible for creating instances of system information commands based on the provided
 * `CommandType` enum, and registering those commands with the Spigot command map for use within the plugin.
 *
 * @version 1.0
 * @since Date or Version
 */
public final class CommandManager {
    private final CommandMap commandMap;
    private final SystemInfo systemInfo;

    private final List<Command> commands = new ArrayList<>();

    /**
     * Constructs a new `CommandManager` with the given `CommandMap` and `SystemInfo`.
     *
     * @param commandMap The Spigot command map used for command registration.
     * @param systemInfo The `SystemInfo` instance for accessing system information.
     */
    public CommandManager(@NotNull CommandMap commandMap, @NotNull SystemInfo systemInfo) {
        this.commandMap = commandMap;
        this.systemInfo = systemInfo;
    }

    /**
     * Creates instances of system information commands based on the `CommandType` enum
     * and adds them to the list of registered commands.
     */
    public void createInstances() {
        for (CommandType value : CommandType.values()) {
            Class<? extends SystemInfoCommand> clazz = value.getClazz();
            try {
                Constructor<? extends SystemInfoCommand> constructor = clazz.getDeclaredConstructor(SystemInfo.class);
                SystemInfoCommand command = constructor.newInstance(this.systemInfo);
                commands.add(command);
            } catch (NoSuchMethodException | InvocationTargetException | RuntimeException | InstantiationException | IllegalAccessException exception) {
                systemInfo.getLogger().warning("Something went wrong while getting a constructor for " + value.getDisplayName());
                systemInfo.getLogger().warning(exception.getLocalizedMessage());
            }
        }
    }

    /**
     * Registers all created system information commands with the Spigot command map.
     */
    public void registerAll() {
        commandMap.registerAll("systeminfo", commands);
    }

}
