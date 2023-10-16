package studio.thevipershow.systeminfo.commands;

import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.commands.CommandCpuLoad;
import studio.thevipershow.systeminfo.commands.CommandDisks;
import studio.thevipershow.systeminfo.plugin.SystemInfo;

import java.util.List;


/**
 * An abstract class for system information commands in your Spigot plugin.
 * Extend this class to create specific system information commands.
 * <p>
 * This class provides a base structure for system information commands, including access to a
 * `SystemInfo` instance and common command properties.
 *
 * @author Your Name
 * @version 1.0.0
 * @since Date or Version
 */
public abstract class SystemInfoCommand extends Command {

    /**
     * The `SystemInfo` instance that provides access to system information.
     */
    protected final SystemInfo systemInfo;

    /**
     * Constructs a new `SystemInfoCommand` with the provided information.
     *
     * @param systemInfo     The `SystemInfo` instance to use for system information.
     * @param name           The name of the command.
     * @param description    A brief description of what the command does.
     * @param usageMessage   A message describing how to use the command.
     * @param aliases        A list of command aliases.
     */
    protected SystemInfoCommand(@NotNull SystemInfo systemInfo, @NotNull String name,
                                @NotNull String description, @NotNull String usageMessage,
                                @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.systemInfo = systemInfo;
    }
}