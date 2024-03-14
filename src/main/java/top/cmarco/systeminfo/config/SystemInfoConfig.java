/*
 *     SystemInfo - The Master of Server Hardware
 *     Copyright © 2024 CMarco
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.cmarco.systeminfo.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import top.cmarco.systeminfo.plugin.SystemInfo;

/**
 * The config class for this plugin.
 * It is associated with the config.yml.
 */
public final class SystemInfoConfig {

    private final SystemInfo plugin;
    private FileConfiguration configuration;

    public SystemInfoConfig(@NotNull SystemInfo plugin) {
        this.plugin = plugin;
        this.plugin.saveDefaultConfig();
        this.configuration = plugin.getConfig();
    }

    public void reloadValues() {
        configuration = null;
        plugin.reloadConfig();
        configuration = plugin.getConfig();
    }

    /**
     * Get the minecraft color letter used by this software.
     * @return The color letter.
     */
    public String getColorScheme() {
        return configuration.getString("messages.color-scheme");
    }

    /**
     * Get the notification update frequency for the Speedtest command.
     * @return The notification update frequency.
     */
    public int getSpeedtestUpdateFrequency() {
        return configuration.getInt("speedtest.update-frequency", 2500);
    }
}
