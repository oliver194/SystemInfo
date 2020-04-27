package me.thevipershow.systeminfo.utils;

import java.lang.reflect.Field;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandMap;

public class Utils {
    /**
     * This method takes a String as input and replaces & with color codes.
     *
     * @param input a non-null String
     * @return returns the string with colors which can be displayed inside Minecraft chat/console
     */
    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    /**
     * This method takes a long value in bytes and formats it to make it human readable
     *
     * @param bytes a valid long value which represents bytes
     * @return depending from the size of the input, returns B, kB, MB, GB, TB, PB, EB.
     */
    public static String formatData(long bytes) {
        //https://programming.guide/java/formatting-byte-size-to-human-readable-format.html
        String s = bytes < 0 ? "-" : "";
        long b = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        return b < 1000L ? bytes + " B"
                : b < 999_950L ? String.format("%s%.1f kB", s, b / 1e3)
                : (b /= 1000) < 999_950L ? String.format("%s%.1f MB", s, b / 1e3)
                : (b /= 1000) < 999_950L ? String.format("%s%.1f GB", s, b / 1e3)
                : (b /= 1000) < 999_950L ? String.format("%s%.1f TB", s, b / 1e3)
                : (b /= 1000) < 999_950L ? String.format("%s%.1f PB", s, b / 1e3)
                : String.format("%s%.1f EB", s, b / 1e6);
    }

    /**
     * Creates a custom BaseComponent with an hover event using the following parameters.
     *
     * @param text      this is the text that will display in chat, color codes are allowed.
     * @param hoverText this is the text that will be shown when the player hovers over a message, color codes allowed.
     * @return returns the BaseComponent generated.
     */
    public static BaseComponent builderHover(String text, String hoverText) {
        TextComponent textComponent = new TextComponent();
        textComponent.setText(color(text));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(color(hoverText)).create()));
        return textComponent;
    }

    /**
     * Creates a custom BaseComponent with a click event using the following parameters.
     *
     * @param text    this is the text that will display in chat, color codes are allowed.
     * @param command this is the command which will be executed upon entering the message in chat.
     * @return returns the BaseComponent generated.
     */
    public static BaseComponent builderClick(String text, String command) {
        TextComponent textComponent = new TextComponent();
        textComponent.setText(color(text));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        return textComponent;
    }

    /**
     * This method counts the entities in every world with a given environment
     * if more worlds are found, multiple values will be returned in the same string
     * separated by a blank space.
     *
     * @param environment This is the environment target {@link org.bukkit.World.Environment}
     * @return returns a String with the number of total entities in every targeted world.
     * example: "255 327 455 " will be the output if three worlds with same environment are found.
     */
    public static String countEntitiesInWorlds(World.Environment environment) {
        StringBuilder entitiesInWorlds = new StringBuilder();
        Bukkit.getWorlds().stream().filter(world -> world.getEnvironment() == environment).forEach(world -> entitiesInWorlds.append(world.getEntities().size()).append(" "));
        return entitiesInWorlds.toString();
    }

    /**
     * This method counts the loaded chunks in every world with a given environment
     * if more worlds are found, multiple values will be returned in the same string
     * separated by a blank space.
     *
     * @param environment This is the environment target {@link org.bukkit.World.Environment}
     * @return returns a String with the number of loaded chunks in every targeted world.
     * example: "36 122 " will be the output if two worlds with same environment are found.
     */
    public static String loadedChunksInWorlds(World.Environment environment) {
        StringBuilder loadedChunksInWorlds = new StringBuilder();
        Bukkit.getWorlds().stream().filter(world -> world.getEnvironment() == environment).forEach(world -> loadedChunksInWorlds.append(world.getLoadedChunks().length).append(" "));
        return loadedChunksInWorlds.toString();
    }

    private static CommandMap commandMap;

    /**
     * This methods allows to use the CommandMap on different forks
     * This method has been provided by electroniccat , thanks to him!
     *
     * @return the CommandMap
     * @throws NoSuchFieldException   if field isn't found
     * @throws IllegalAccessException if access is invalid
     */
    public static CommandMap getCommandMap() throws NoSuchFieldException, IllegalAccessException {
        if (commandMap == null) {
            try {
                commandMap = Bukkit.getCommandMap();
                return commandMap;
            } catch (NoSuchMethodError ignored) {
                //exception is ignored
            }
            final Class<? extends Server> serverClass = Bukkit.getServer().getClass();
            Field commandMapField = serverClass.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        }
        return commandMap;
    }
}
