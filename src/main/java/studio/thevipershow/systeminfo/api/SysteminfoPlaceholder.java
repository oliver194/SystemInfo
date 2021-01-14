package studio.thevipershow.systeminfo.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import studio.thevipershow.systeminfo.SystemInfo;
import studio.thevipershow.systeminfo.oshi.SystemValues;
import org.bukkit.OfflinePlayer;

public final class SysteminfoPlaceholder extends PlaceholderExpansion {

    private final SystemValues values = SystemValues.getInstance();

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "systeminfo";
    }

    @Override
    public String getAuthor() {
        return "TheViperShow";
    }

    @Override
    public String getVersion() {
        return SystemInfo.getInstance().getDescription().getVersion();
    }

    @Override
    public String onRequest(@NotNull final OfflinePlayer p, @NotNull final String params) {
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