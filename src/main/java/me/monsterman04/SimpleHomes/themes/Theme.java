package me.monsterman04.SimpleHomes.themes;

import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;

public enum Theme {
    BLUE(ChatColor.AQUA, BarColor.BLUE),
    GREEN(ChatColor.GREEN, BarColor.GREEN),
    PINK(ChatColor.LIGHT_PURPLE, BarColor.PINK),
    PURPLE(ChatColor.DARK_PURPLE, BarColor.PURPLE),
    RED(ChatColor.RED, BarColor.RED),
    WHITE(ChatColor.WHITE, BarColor.WHITE),
    YELLOW(ChatColor.YELLOW, BarColor.YELLOW);

    private ChatColor chatColor;
    private BarColor barColor;

    Theme(ChatColor chatColor, BarColor barColor){
        this.chatColor = chatColor;
        this.barColor = barColor;
    }


    public ChatColor getChatColor() {
        return chatColor;
    }
    public BarColor getBarColor() {
        return barColor;
    }
}
