package me.monsterman04.SimpleHomes;

import me.monsterman04.SimpleHomes.commands.Commands;
import me.monsterman04.SimpleHomes.commands.HomeTabComplete;
import me.monsterman04.SimpleHomes.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public class Main extends JavaPlugin {
    @Override
    public void onEnable(){
        Objects.requireNonNull(getCommand("sethome")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("home")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("home")).setTabCompleter(new HomeTabComplete());
        Objects.requireNonNull(getCommand("homes")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("deletehome")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("deletehome")).setTabCompleter(new HomeTabComplete());
        Objects.requireNonNull(getCommand("selecthome")).setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new Events(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SimpleHomes] Plugin started!");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SimpleHomes] Current version: " + getDescription().getVersion());

        getConfig().options().copyDefaults(true);
        saveConfig();

        for(Player player : Bukkit.getOnlinePlayers()) {
            HomeManager.joinSetup(player);
        }
    }

    @Override
    public void onDisable(){getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SimpleHomes] Plugin closed!");}
}
