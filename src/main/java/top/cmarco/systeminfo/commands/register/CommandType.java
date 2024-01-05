package top.cmarco.systeminfo.commands.register;

import org.jetbrains.annotations.NotNull;
import top.cmarco.systeminfo.commands.*;

/**
 * An enum that represents different types of system information commands in your Spigot plugin.
 * <p></p>
 * Each enum constant represents a specific command type along with its display name and associated command class.
 * These command types are used to categorize and identify different system information commands.
 *
 * @version 1.0
 * @since Date or Version
 */
public enum CommandType {
    CPU_LOAD("CPU Load", CommandCpuLoad.class),
    DEVICES("Devices", CommandDevices.class),
    DISKS("Disks", CommandDisks.class),
    HTOP("Htop", CommandHtop.class),
    LSCPU("LsCPU", CommandLscpu.class),
    SENSORS("Sensors", CommandSensors.class),
    SPEEDTEST("Speedtest", CommandSpeedtest.class),
    SYSTEM_INFO("System Info", CommandSystemInfo.class),
    UPTIME("Uptime", CommandUptime.class),
    VMSTAT("VmStat", CommandVmstat.class),
    JAVA("Java", CommandJava.class);

    private final String displayName;
    private final Class<? extends SystemInfoCommand> clazz;

    /**
     * Constructs a `CommandType` with the specified display name and associated command class.
     *
     * @param displayName The display name of the command type.
     * @param clazz       The class representing the associated system information command.
     */
    CommandType(@NotNull String displayName, @NotNull Class<? extends SystemInfoCommand> clazz) {
        this.displayName = displayName;
        this.clazz = clazz;
    }

    /**
     * Get the display name of the command type.
     *
     * @return The display name.
     */
    @NotNull
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get the class representing the associated system information command.
     *
     * @return The command class.
     */
    @NotNull
    public Class<? extends SystemInfoCommand> getClazz() {
        return clazz;
    }
}
