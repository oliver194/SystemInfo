package me.thevipershow.systeminfo.enums;

import me.thevipershow.systeminfo.utils.Utils;

public enum Messages {

    INVALID_ARGS("&4» &cInvalid arguments."),
    OUT_OF_ARGS("&4» &cArguments number is incorrect."),
    INVALID_ARG_TYPE("&4» &cArgument type is invalid."),
    NO_PERMISSIONS("&4» &cInsufficient permissions.");

    private final String s;

    Messages(String value) {
        this.s = value;
    }

    public String value() {
        return this.s;
    }

    public String value(boolean color) {
        return color ? Utils.color(this.s) : this.s;
    }

}
