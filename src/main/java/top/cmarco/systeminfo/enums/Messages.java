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

package top.cmarco.systeminfo.enums;

import org.jetbrains.annotations.NotNull;
import top.cmarco.systeminfo.utils.Utils;

/**
 * Some basic and frequent messages used by this plugin.
 */
public enum Messages {
    INVALID_ARGS("&4» &cInvalid arguments."),
    OUT_OF_ARGS("&4» &cArguments number is incorrect."),
    NO_PERMISSIONS("&4» &cInsufficient permissions.");

    private final String s;

    Messages(@NotNull String value) {
        this.s = value;
    }

    /**
     * Colored message enabler.
     * @param color whether to color or not.
     * @return a colored text if true, default text otherwise.
     */
    public String value(boolean color) {
        return color ? Utils.color(this.s) : this.s;
    }
}
