package studio.thevipershow.systeminfo.commands.register;

import org.jetbrains.annotations.NotNull;
import studio.thevipershow.systeminfo.commands.*;

public enum CommandType {
    CPU_LOAD("CPU Load",CommandCpuLoad.class),
    DEVICES("Devices",CommandDevices.class),
    DISKS("Disks",CommandDisks.class),
    HTOP("Htop",CommandHtop.class),
    LSCPU("LsCPU",CommandLscpu.class),
    SENSORS("Sensors",CommandSensors.class),
    SPEEDTEST("Speedtest",CommandSpeedtest.class),
    SYSTEM_INFO("System Info",CommandSystemInfo.class),
    UPTIME("Uptime",CommandUptime.class),
    VMSTAT("VmStat",CommandVmstat.class);

    private final String displayName;
    private final Class<? extends SystemInfoCommand> clazz;

    CommandType(@NotNull String displayName, @NotNull Class<? extends SystemInfoCommand> clazz) {
        this.displayName = displayName;
        this.clazz = clazz;
    }

    @NotNull
    public String getDisplayName() {
        return displayName;
    }

    @NotNull
    public Class<? extends SystemInfoCommand> getClazz() {
        return clazz;
    }
}
