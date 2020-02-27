package me.thevipershow.systeminfo.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.thevipershow.systeminfo.SystemInfo;
import me.thevipershow.systeminfo.oshi.SystemValues;
import org.bukkit.OfflinePlayer;

public class SysteminfoPlaceholder extends PlaceholderExpansion {

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
                toReturnString.append(String.format("%s %s", SystemValues.getCpuModel(), SystemValues.getCpuModelName()));
                break;
            case "cpu-frequency":
                toReturnString.append(SystemValues.getCpuMaxFrequency());
                break;
            case "cpu-temperature":
                toReturnString.append(SystemValues.getCpuTemperature());
                break;
            case "swap-max":
                toReturnString.append(SystemValues.getTotalSwap());
                break;
            case "swap-used":
                toReturnString.append(SystemValues.getUsedSwap());
                break;
            case "memory-max":
                toReturnString.append(SystemValues.getMaxMemory());
                break;
            case "memory-available":
                toReturnString.append(SystemValues.getAvailableMemory());
                break;
            case "memory-used":
                toReturnString.append(SystemValues.getUsedMemory());
                break;
            case "processes":
                toReturnString.append(SystemValues.getRunningProcesses());
                break;
            default:
                break;
        }
        return toReturnString.toString();
    }
}