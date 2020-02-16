package me.thevipershow.systeminfo.utils;

import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

public class Utils {

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String formatData(long bytes) {
        //Thanks to this website for the formatting method
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

    public static BaseComponent builderHover(String text, String hoverText) {
        TextComponent textComponent = new TextComponent();
        textComponent.setText(color(text));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(color(hoverText)).create()));
        return textComponent;
    }

    public static BaseComponent builderClick(String text, String command) {
        TextComponent textComponent = new TextComponent();
        textComponent.setText(color(text));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        return textComponent;
    }

    public static String countEntitiesInWorlds(@Nonnull World.Environment environment) {
        StringBuilder entitiesInWorlds = new StringBuilder();
        Bukkit.getWorlds().stream().filter(world -> world.getEnvironment().equals(environment)).collect(Collectors.toUnmodifiableList()).forEach(world -> entitiesInWorlds.append(world.getEntities().size() + " "));
        return entitiesInWorlds.toString();
    }

    public static String loadedChunksInWorlds(@Nonnull World.Environment environment) {
        StringBuilder loadedChunksInWorlds = new StringBuilder();
        Bukkit.getWorlds().stream().filter(world -> world.getEnvironment().equals(environment)).collect(Collectors.toUnmodifiableList()).forEach(world -> loadedChunksInWorlds.append(world.getLoadedChunks().length + " "));
        return loadedChunksInWorlds.toString();
    }

}
