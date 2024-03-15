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

package top.cmarco.systeminfo.commands.neofetch;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public enum ServerLogo {

    BUKKIT("           ███████████▓▓▓▓▓▓▓▓▓▓▓             \n" +
            "           ███████████▓▓▓▓▓▓▓▓▓▓▓             \n" +
            "   ████████▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓     \n" +
            "   ████████▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓     \n" +
            "███▓▒▒▒▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓▒▒▒▒▒▒▒▓███  \n" +
            "███▓▒▒▒▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓▒▒▒▒▒▒▒▓███  \n" +
            "▓▓▓▓███▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓███████  \n" +
            "▓▓▓▓███▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓███████  \n" +
            "▓▓▓▒░░░▓██████▓▒▒▒▓▓▓▓▒▒▒▒▒▒▒▓██████▓▒▒▒▓███  \n" +
            "▓▓▓▒░░░▓██████▓▒▒▒▓▓▓▓▒▒▒▒▒▒▒▓██████▓▒▒▒▓███  \n" +
            "▓▓▓▒░░░░░░░░░░▒██████████████▒▒▒▒▓▓▓▓▓▓▓████  \n" +
            "▓▓▓▒░░░░░░░░░░▒██████████████▒▒▒▒▓▓▓▓▓▓▓████  \n" +
            "▓▓▓▒░░░░░░░░░░░▓▓▓▒░░░░░░░░░░▒▒▒▒▒▒▒▒▓▓▓████  \n" +
            "▓▓▓▒░░░░░░░░░░░▓▓▓▒░░░░░░░░░░▒▒▒▒▒▒▒▒▓▒▓████  \n" +
            "   ▓▓▓▓▒░░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▒░▓▓▒▓▓▓▓█▒▓█     \n" +
            "   ▓▓▓▓▒░░░░░░░░░░▒▒▒▒▒░▒░▒▒▒░▒▒░▓▓▓▓█▒▓█     \n" +
            "   ▓▓▓▓▒░░░░░░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓████     \n" +
            "   ▓▓▓▓▒░░░░░░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▒▓▓▓▓████     \n" +
            "   ▓▓▓▓▒░░░░░░░░░░░░░░▒▒▒▒▒▒▒▓▓▒▒▓▓▓▓█░▓█     \n" +
            "   ▓▓▓▓▒░░░░░░░░░░░░░░▒▒▒▒▒▒▒▓▓▒▒▓▓▓▓█▒▓█     \n" +
            "       ▓▓▓▓░░░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▒████         \n" +
            "       ▓▓▓▓░░░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▓████         \n" +
            "       ▓▓▓▓▒▒▒▒░░░░░░░▒▒▒▒▒▒▒░░░░████         \n" +
            "       ▓▓▓▓▒▒▒▒░░░░░░░▒▒▒▒▒▒▒░▒▒▒████         \n" +
            "           █████▓▓▓▓█████▓███▓▓▓▒             \n" +
            "           ██████████████████████             "),

    SPIGOT("                                                \n" +
            "                  █████████████████            \n" +
            "                  █▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓█            \n" +
            "                         █▓▓▓                   \n" +
            "                         █▓▓▓                   \n" +
            "                       █▓▓▓▓▓██                 \n" +
            "                      ██▓▓▓▓▓▓██                \n" +
            "                      █▓▓▓▓▓▓▓▓█                \n" +
            "                     █▓▓▓▓▓▓▓▓▓▓█               \n" +
            "  ███████       ██   █▓▓▓▓▓▓▓▓▓▓▓█              \n" +
            " █▓▓▓▓▓▓▓███████▓▓███▓▓▓▓▓▓▓▓▓▓▓▓▓█             \n" +
            " █▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓████         \n" +
            "█▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██      \n" +
            "███▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓█     \n" +
            "   █▓▓▓▓▓▓▓▓▓▓▓▓███▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓█ \n" +
            "     ██████████      █████████████ ██▓▓▓▓▓▓▓▓▓██\n" +
            "                                  █▓▓▓▓▓▓▓▓▓▓▓██\n" +
            "                                  ██▓▓▓▓▓▓▓▓██  \n" +
            "                                    ███████     \n",
            "█:&8", "▓:&e"),

    PAPERMC("                     ▒▒▒▒▓▓▓▓▒▒                 \n" +
            "                   ▒▒▒▒▓▓▓▓▓▒▒▒▒▒▓▓             \n" +
            "                  ▒▒▒▓▓▓▓▒▒▒▒▓▓▓▓▓▓▓▓            \n" +
            "                ▒▒▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓            \n" +
            "      ▓██████████████████████████████████        \n" +
            "    ▓▓▓██████████████████████████████████        \n" +
            "   ▓▓▓▓██████████████████████████████████        \n" +
            "   ▓▓▓▓███████████████████████▓▓▒#▒██████        \n" +
            " ▒▒▒▓▓▓████████████████████▓▓#####▓█████▓        \n" +
            "  ▒▒▒▓▓█████████████████▓▒########██████▒▒▒      \n" +
            "▓▓▓▓▒▓▓██████████████▓▒##########▒██████▓▒▒▒▒    \n" +
            " ▓▓▓▒▒▓██████████▓▓▒#######▒#####▓██████▓▓▓▒▒▒▒  \n" +
            "▒▒▓▓▓▒▓███████▓▓▒#######▒▓▒######███████▓▒▓▓▒▒▒▒ \n" +
            "▒▒▒▒▓▓▒█████▓▒########▓▓▒#######▒███████▓▒▒▓▓▓▒  \n" +
            "  ▒▒▒▓▓█████▓▓▒####▒▓█▓#########▓███████▓▓▒▓▓▓▓  \n" +
            "    ▒▒▒██████████▓▓█▓▒##########▓███████▓▓▓▒▓▓▓  \n" +
            "      ▒█████████████▓▒▒▒#######▒▓███████▓▓▓▒▒▒   \n" +
            "      ██████████████▓▒▒▓▓█▓▓▒##▓████████▓▓▓▓▒▒   \n" +
            "      ██████████████▓▒▓█████████████████▓▓▓▓     \n" +
            "      ██████████████████████████████████▓▓▓▓     \n" +
            "      ███████████████████████████████████        \n" +
            "      ███████████████████████████████████        \n" +
            "          ▓▓▓▓▓▓▓▓▓▓▓▓▒▒▒▓▓▓▒▒▒                  \n" +
            "           ▓▓▓▓▓▒▒▒▒▒▓▓▓▓▒▒▒▒                    \n" +
            "               ▒▒▒▓▓▓▓▓▒▒▒▒▒                     \n" +
            "                   ▓▓  ▒▒                        \n"
            , "\u2588:&8", "\u2593:&c", "\u2592:&b", "#:&r&f"),

    FOLIA("                                                   \n" +
            "                                                   \n" +
            "                                                   \n" +
            "                             ░     ░░     ░░░░░░░░▒\n" +
            "                          ░░░    ▒░░ ░░░░░▒░░░░▒▒▒▓\n" +
            "                        ░░░ ░  ░▒░░░░░░░░░░░░░░▒▓▓▓\n" +
            "                     ░░   ░░░▒▓▒░░░░░░░░░░░░▒▒▓▓▓▓▓\n" +
            "                    ░    ░░░▓█▒▒░░░░░░░░░░░▓▓▓▓▓▓▓░\n" +
            "                 ░      ░ ░░▓▓▒░░░░░░░░▒▒▓▒▒▓▓▓█▓░ \n" +
            "                 ░   ░ ░░░░▒▓▒░░░░░░░░▒▒▒▒▒▓▓▓▓▒   \n" +
            "              ░░     ░░░░░▒▓▓░░░░░░░▒▓░░░▒▓▓▓██░   \n" +
            "          ░░ ░░░░░░░░░▒░░▒▒▓▒░░░░░▓▒▒▒▒▓▓▓▓███░    \n" +
            "         ░░░░░░░░░░░░▓░░░▒▓▓░░▒▒▒▒░░▒▒▒▓▓▓▓████░   \n" +
            "        ░▒░░░░░░░░▒▒▒░░░▒▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓████▓     \n" +
            "        ░▒▒░░░░░▒▓▓▓░░░░▒▒▒▓▒░░▒▒▓▓▓▓▓▓██▓▓░░      \n" +
            "        ▒▒▓░░░▓▓▓▓▓▓░░░▒▓▓▒░▒▒▒▒▒▓▓▓▓▓▓░           \n" +
            "        ▓▓▓▒░▒▓▓▓▓▒▒░▒▓▓▓▓▓▓▓▓▓▒▓▓▓▓▓▓▓▓█▓▓        \n" +
            "        ▓▓▓▓▒▓▓▓▓▒▒▓▓▓▓▓▓▓▓▓██████████▓█▓▒         \n" +
            "        ░███▓▓▓▓▓▓▓▓▓▓▓▓▓▓██████████▓░░░           \n" +
            "         ▓████▓▓▓▓▓▓▓▓▓████████████████▓           \n" +
            "         ░██▓▓▓▓▓▓▓▓▓███████████████▒░             \n" +
            "         ░▓▓███▓▓▓█████████████░░                  \n" +
            "        ░░ ░███████████████████░                   \n" +
            "      ░░░     ▒▓███████▓▓▒░░                       \n" +
            "░░░░░                                              \n" +
            "                                                   \n" +
            "                                                   ",
            "░:&6", "█:&3", "▓:&a", "▒:&c"),

    PURPUR("                         ░▒▒░                             \n" +
            "                     ░▒▒░    ▒▒░                          \n" +
            "                  ░▒▒░          ░▒▒░                      \n" +
            "               ░▒▒░                ░▒▒░                   \n" +
            "            ░░░░▒▒░                ░▒▒░░▒░                \n" +
            "         ░▒▒░ ░▒▒░░▒▒           ░▒▒░░▒▒░ ░▒▒░             \n" +
            "      ░▒▒        ░▒▒ ░▒▒░    ░▒▒░░▒▒░        ▒▒░          \n" +
            "  ░░▒░              ░▒▒░░▒▒▒▒░░▒▒░             ░▒▒▒░      \n" +
            " ▒▒                    ▒▒░  ▒▒▒                   ░▒▒     \n" +
            "▒▒░░▒▒░             ░▒▒ ░▒▒▒▒░ ▒▒░             ░▒▒░░▒▒    \n" +
            "▒ ░▒▒░░▒▒░      ░▒▒░░░▒▒      ▒▒░ ▒▒░       ░▒▒░░▒▒░░▒    \n" +
            "▒    ░▒▒░░▒▒░░▒▒░░▒▒░            ░▒▒░░▒▒░░▒▒░░▒▒░   ░▒    \n" +
            "▒        ▒▒░ ░ ▒▒░                  ░▒▒ ░ ░▒▒       ░▒    \n" +
            "▒          ░▒░░ ▒▒░               ░▒▒▒ ░░▒░         ░▒    \n" +
            "▒          ░▒░▒░▒▒░░▒▒         ░▒▒░░▒▒░▒░▒░         ░▒    \n" +
            "▒          ░▒░▒   ░▒▒ ░▒▒    ▒▒░ ▒▒░   ▒░▒░         ░▓    \n" +
            "▒          ░▒░▒      ░▒▒░░▒▒░░▒▒░      ▒░▒░         ░▓    \n" +
            "░▒▒▒       ░▒░▒         ░▒░▒▒░         ▒░▒░       ▒▓▒     \n" +
            "▒▒▒ ░▒▒    ░▒░▒          ▒▒▒░          ▒░▒░    ▒▓░ ▒▓▒    \n" +
            "▒  ░▒▒░░▒▒░░▒░▒          ▒▒▒░          ▒░▒░░▓▒░░▒▒░ ░▓    \n" +
            "▒     ░▒▒░░▒▒░▒          ▒▒▒░          ▒░▒▒░░▒▒░    ░▓    \n" +
            "▒        ░▒▒░ ▒▒░        ▒▒▒░        ░▒▒ ░▒▓░       ░▓    \n" +
            "▒          ░▒░▒░░▒▒░     ▒▒▒░     ▒▒▒ ░▓░▒░         ░▓    \n" +
            "▒          ░▒░▒░▒▒▒░▒▒▒  ▒▒▒░ ░▒▓▒░▒▓▒ ▒░▒░         ░▓    \n" +
            "▒          ░▒░▒    ▒▒▒ ░▓▒▒▒▒▓░ ▒▓▒    ▒░▒░         ░▓    \n" +
            "▒░         ░▒░▒       ▒▒▒░  ░▒▒░       ▒░▒░         ▒▓    \n" +
            "  ░▒▒▒      ░▒░▒          ▒▒▒▒          ▒░▒░      ▒▓▒░     \n" +
            "     ▒▒▒   ░▒░▒          ▒▒▒░          ▒░▒░   ▒▓░         \n" +
            "        ░▒▒▒▒░▒          ▒▒▒░          ▒░▓░▒▓░            \n" +
            "           ░░░▒          ▒▒▒░          ▒░▒░               \n" +
            "              ░▒▒░       ▒▒▒░       ░▓▒░                  \n" +
            "                 ░▒▒░    ▒▒▒░    ░▓▒░                     \n" +
            "                    ░▒▒▒ ▒▒▓░ ▒▓▒░                        \n" +
            "                       ░▒▓▒▓▓▒                            ",
            "▒:&3", "░:&b", "▓:&d")
    ;

    private final String[] logoLines;

    ServerLogo(@NotNull final String[] logoLines) {
        this.logoLines = logoLines;
    }

    /**
     * Create a logo instance and automatically assign color to each char
     * using a color mapping feature.
     * <br>
     * A color mapping is defined this way "<char>:&<colorCode>".
     * <br>
     * Example: "%:&c", will assign red colour on all '%' characters.
     *
     * @param logo
     * @param colorMappings
     */
    ServerLogo(@NotNull final String logo, @NotNull String... colorMappings) {

        List<ColorMapping> loadedMappings = new ArrayList<>(colorMappings.length);
        for (final String string : colorMappings) {
            ColorMapping colorMapping = new ColorMapping(string);
            loadedMappings.add(colorMapping);
        }

        final String[] lines = logo.split("\\n+");
        for (int i = 0x00; i < lines.length; i++) {
            for (ColorMapping colorMapping : loadedMappings) {
                lines[i] = colorMapping.replaceWithMapping(lines[i]);
            }
        }

        this.logoLines = lines;
    }

    private static class ColorMapping {
        protected final String targetChar;
        protected final String replaceWith;

        public ColorMapping(@NotNull final String mapping) {
            String[] split = mapping.split("\\:");
            if (split.length != 0x02) {
                throw new IllegalArgumentException("Illegal mapping " + mapping);
            }
            this.targetChar = split[0x00];
            this.replaceWith = split[0x01];
        }

        public String replaceWithMapping(String input) {
            return input.replace(targetChar, replaceWith + targetChar);
        }

    }

    public String[] getLogoLines() {
        return logoLines;
    }
}
