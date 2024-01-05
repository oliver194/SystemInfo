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
