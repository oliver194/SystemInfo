package me.thevipershow.systeminfo.interfaces;

import org.bukkit.command.CommandSender;

public interface Command {

    void action(CommandSender sender, String name, String[] args);

}
