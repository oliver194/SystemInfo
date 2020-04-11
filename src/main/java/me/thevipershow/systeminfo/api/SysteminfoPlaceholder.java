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
        return SystemInfo.PLUGIN_VERSION;
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        StringBuilder toReturnString = new StringBuilder();
        switch (params) {
            case "cpu-model":
                toReturnString.append(String.format("%s %s", values.getCpuModel(), values.getCpuModelName()));
                break;
            case "cpu-frequency":
                toReturnString.append(values.getCpuMaxFrequency());
                break;
            case "cpu-temperature":
                toReturnString.append(values.getCpuTemperature());
                break;
            case "swap-max":
                toReturnString.append(values.getTotalSwap());
                break;
            case "swap-used":
                toReturnString.append(values.getUsedSwap());
                break;
            case "memory-max":
                toReturnString.append(values.getMaxMemory());
                break;
            case "memory-available":
                toReturnString.append(values.getAvailableMemory());
                break;
            case "memory-used":
                toReturnString.append(values.getUsedMemory());
                break;
            case "processes":
                toReturnString.append(values.getRunningProcesses());
                break;
            default:
                break;
        }
        return toReturnString.toString();
    }
}