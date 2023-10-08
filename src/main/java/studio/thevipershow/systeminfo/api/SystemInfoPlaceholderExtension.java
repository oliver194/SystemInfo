package studio.thevipershow.systeminfo.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

import studio.thevipershow.systeminfo.plugin.SystemInfo;
import studio.thevipershow.systeminfo.oshi.SystemValues;
import org.bukkit.OfflinePlayer;

/**
 * The `SystemInfoPlaceholderExtension` class is an extension for PlaceholderAPI that provides placeholders
 * related to system information. These placeholders can be used in plugins to dynamically display system
 * information to players.
 */
public class SystemInfoPlaceholderExtension extends PlaceholderExpansion {

    private final SystemInfo systemInfo;

    /**
     * Initializes a new instance of the `SystemInfoPlaceholderExtension` class.
     *
     * @param systemInfo The `SystemInfo` instance associated with this extension.
     */
    public SystemInfoPlaceholderExtension(@NotNull SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

    /**
     * Indicates whether this extension can be registered with PlaceholderAPI.
     *
     * @return True if the extension can be registered; otherwise, false.
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * Indicates whether the placeholders provided by this extension should persist across server restarts.
     *
     * @return True if placeholders should persist; otherwise, false.
     */
    @Override
    public boolean persist() {
        return true;
    }

    /**
     * Gets the identifier for this extension, which is used to reference its placeholders.
     *
     * @return The identifier for this extension.
     */
    @Override
    public @NotNull String getIdentifier() {
        return "systeminfo";
    }

    /**
     * Gets the author of this extension.
     *
     * @return The author of this extension.
     */
    @Override
    public @NotNull String getAuthor() {
        return "TheViperShow";
    }

    /**
     * Gets the version of this extension.
     *
     * @return The version of this extension, retrieved from the associated `SystemInfo` instance.
     */
    @Override
    public @NotNull String getVersion() {
        return this.systemInfo.getDescription().getVersion();
    }

    /**
     * Handles requests for specific placeholders and returns the corresponding system information.
     *
     * @param p      The offline player for whom the placeholder is being requested (not used in this context).
     * @param params The name of the requested placeholder.
     * @return The value of the requested placeholder, or null if the placeholder name is not recognized.
     */
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
