package studio.thevipershow.systeminfo.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

import studio.thevipershow.systeminfo.plugin.SystemInfo;
import studio.thevipershow.systeminfo.oshi.SystemValues;
import org.bukkit.OfflinePlayer;

public class SystemInfoPlaceholderExtension extends PlaceholderExpansion {

    private final SystemInfo systemInfo;

    public SystemInfoPlaceholderExtension(@NotNull SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "systeminfo";
    }

    @Override
    public @NotNull String getAuthor() {
        return "TheViperShow";
    }

    @Override
    public @NotNull String getVersion() {
        return this.systemInfo.getDescription().getVersion();
    }

    @Override
    public String onRequest(@NotNull final OfflinePlayer p, @NotNull final String params) {
        SystemValues values = systemInfo.getsV();
        switch (params) {
            case "cpu-model":
                return String.format("%s %s", values.getCpuModel(), values.getCpuModelName());
            case "cpu-frequency":
                return values.getCpuMaxFrequency();
            case "cpu-temperature":
                return values.getCpuTemperature();
            case "swap-max":
                return values.getTotalSwap();
            case "swap-used":
                return values.getUsedSwap();
            case "memory-max":
                return values.getMaxMemory();
            case "memory-available":
                return values.getAvailableMemory();
            case "memory-used":
                return values.getUsedMemory();
            case "processes":
                return Integer.toString(values.getRunningProcesses());
            default:
                return null;
        }
    }
}