package me.thevipershow.systeminfo.interfaces;

import org.bukkit.entity.Player;

public interface Command {

    void action(Player player, String name, String[] args);

}
