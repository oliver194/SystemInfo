package studio.thevipershow.systeminfo.commands.register;

import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.plugin.SystemInfo;

import java.util.List;


public abstract class SystemInfoCommand extends Command {

    protected final SystemInfo systemInfo;

    protected SystemInfoCommand(@NotNull SystemInfo systemInfo, @NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.systemInfo = systemInfo;
    }
}
