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

package top.cmarco.systeminfo.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import top.cmarco.systeminfo.plugin.SystemInfo;

import java.util.Collections;

/**
 * This class represents a Spigot command to perform a CPU benchmark test.
 */
public final class CommandCPUBenchmark extends SystemInfoCommand {

    /**
     * Constructs a new `SystemInfoCommand` with the provided information.
     *
     * @param systemInfo   The `SystemInfo` instance to use for system information.
     */
    public CommandCPUBenchmark(@NotNull SystemInfo systemInfo) {
        super(systemInfo, "cpubenchmark", "Benchmark your CPU.", "/<command>", Collections.singletonList("benchmark"));
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }
}
