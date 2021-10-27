package me.monsterman04.SimpleHomes.events;

import me.monsterman04.SimpleHomes.HomeManager;
import me.monsterman04.SimpleHomes.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Events implements Listener {

    private final Main plugin;
    private final HomeManager homeManager;
    public Events(Main main){
        plugin = main;
        homeManager = plugin.getHomeManager();

    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        homeManager.joinSetup(player);
        if(plugin.getConfig().contains("Players." + player.getName() + ".CurrentHomes")){homeManager.migrateToUUID(player);}

        new BukkitRunnable() {
            @Override
            public void run() {
                homeManager.suggestTeleportHome(player);
            }
        }.runTaskLater(plugin, 20);
    }
}