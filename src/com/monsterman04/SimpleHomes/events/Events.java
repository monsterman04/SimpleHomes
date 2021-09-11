package com.monsterman04.SimpleHomes.events;

import com.monsterman04.SimpleHomes.HomeManager;
import com.monsterman04.SimpleHomes.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Events implements Listener {

    @EventHandler
    public static void playerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final Main plugin = Main.getPlugin(Main.class);

        HomeManager.joinSetup(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                HomeManager.suggestTeleportHome(player);
            }
        }.runTaskLater(plugin, 20);
    }
}