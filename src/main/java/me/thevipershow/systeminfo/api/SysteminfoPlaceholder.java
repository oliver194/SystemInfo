package me.thevipershow.systeminfo.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.thevipershow.systeminfo.SystemInfo;
import me.thevipershow.systeminfo.oshi.SystemValues;
import org.bukkit.OfflinePlayer;

public final class SysteminfoPlaceholder extends PlaceholderExpansion {

    private final SystemValues values = SystemValues.getInstance();

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
    public String onRequest(OfflinePlayer p, String params) {
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