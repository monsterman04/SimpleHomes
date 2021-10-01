package me.monsterman04.SimpleHomes;

import me.monsterman04.SimpleHomes.themes.Theme;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.*;

@SuppressWarnings({"deprecation"})
public class HomeManager {

    private static Main plugin;
    private Theme theme;

    public HomeManager(Main main){
        plugin = main;
        theme = plugin.getThemeManager().getTheme();
    }

    public void joinSetup(Player player) {
        int maxHomes = checkMaxHouses();

        if (!plugin.getConfig().contains("Players." + player.getName() + ".CurrentHomes")) {
            plugin.getConfig().set("Players." + player.getName() + ".CurrentHomes", 0);
            plugin.saveConfig();
        }
        for (int i = 1; i <= maxHomes; i++) {
            if (!plugin.getConfig().contains("Players." + player.getName() + ".Home" + i)) {
                plugin.getConfig().set("Players." + player.getName() + ".Home" + i + ".Empty", true);
                plugin.saveConfig();
            }
        }

        deleteInvalidHouses(maxHomes, player);
        checkCurrentHouses(player);
    }
    public int checkMaxHouses() {
        if (plugin.getConfig().getInt("MaxHomes") == 0 || plugin.getConfig().getInt("MaxHomes") > 50) {
            plugin.getConfig().set("MaxHomes", 5);
            plugin.saveConfig();
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SimpleHomes] Output value of MaxHomes in config.yml didn't meet our requirements so it was reset to default of 5!");
            return 5;
        } else {
            return plugin.getConfig().getInt("MaxHomes");
        }
    }
    public void checkCurrentHouses(Player player) {
        int total = 0;
        for (int i = 1; i <= checkMaxHouses(); i++) {
            if (plugin.getConfig().get("Players." + player.getName() + ".Home" + i + ".Empty") == Boolean.FALSE) {
                total++;
            }
        }

        plugin.getConfig().set("Players." + player.getName() + ".CurrentHomes", total);
        plugin.saveConfig();
    }
    public boolean containsName(Player player, String name) {
        boolean contains = false;
        for (int i = 1; i <= checkMaxHouses(); i++) {
            if (plugin.getConfig().contains("Players." + player.getName() + ".Home" + i + ".Name") && Objects.requireNonNull(plugin.getConfig().getString("Players." + player.getName() + ".Home" + i + ".Name")).equalsIgnoreCase(name)) {
                contains = true;
            }
        }
        return contains;
    }
    public List<String> allNames(Player player) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= checkMaxHouses(); i++) {
            if (plugin.getConfig().contains("Players." + player.getName() + ".Home" + i + ".Name")) {
                list.add(Objects.requireNonNull(plugin.getConfig().get("Players." + player.getName() + ".Home" + i + ".Name")).toString());
            }
        }
        return list;
    }
    public void deleteInvalidHouses(int maxHomes, Player player) {
        for (int i = maxHomes + 1; i <= 60; i++) {
            if (plugin.getConfig().contains("Players." + player.getName() + ".Home" + i)) {
                plugin.getConfig().set("Players." + player.getName() + ".Home" + i, null);
                plugin.saveConfig();
            }
        }
    }
    public int getHomeByName(Player player, String name) {
        int num = 1;
        for (int i = 1; i <= checkMaxHouses(); i++) {
            if (plugin.getConfig().contains("Players." + player.getName() + ".Home" + i + ".Name")) {
                if (Objects.requireNonNull(plugin.getConfig().getString("Players." + player.getName() + ".Home" + i + ".Name")).equalsIgnoreCase(name)) {
                    num = i;
                }
            }
        }
        return num;
    }
    public int getHomeDistance(Player player, int number) {
        if (plugin.getConfig().contains("Players." + player.getName() + ".Home" + number + ".Location")) {
            Location homeLocation = plugin.getConfig().getLocation("Players." + player.getName() + ".Home" + number + ".Location");
            Location playerLocation = player.getLocation();

            try {
                int distance = -1;
                if (homeLocation != null) {
                    distance = (int) playerLocation.distance(homeLocation);
                }
                return distance;
            } catch (IllegalArgumentException exception) {
                return -1;
            }
        }
        return -1;
    }
    public void suggestTeleportHome(Player player) {
        if(!plugin.getConfig().getBoolean("SuggestHomeTeleportOnJoin")){
            return;
        }
        HashMap<String, Integer> d = new HashMap<>();
        for (String home : allNames(player)) {
            int i = getHomeByName(player, home);
            int distance = getHomeDistance(player, i);
            if (distance >= plugin.getConfig().getInt("SuggestHomeTeleportDistance")) {
                d.put(home, distance);
            }
        }
        HashMap<String, Integer> distances = sortByValue(d);
        Optional<String> firstKey = distances.keySet().stream().findFirst();

        if (firstKey.isPresent()) {
            String home = firstKey.get();
            int homeNum = getHomeByName(player , home);
            player.sendMessage(ChatColor.AQUA + "It looks like you're far from home. Would you like to be teleported to " + home +  "?");

            //region [YES]
            TextComponent yes = new TextComponent("[YES]");
            yes.setColor(net.md_5.bungee.api.ChatColor.of(new java.awt.Color(0, 196, 12)));
            yes.setBold(true);
            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/home " + home));
            yes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Teleporting to " + home + ", Distance: " + getHomeDistance(player, homeNum))
                    .color(net.md_5.bungee.api.ChatColor.DARK_GRAY).create()));
            //endregion
            //region [TAKE ME SOMEWHERE ELSE]
            StringBuilder message = new StringBuilder();
            for (String home2 : allNames(player)) {
                String formated;
                int i = getHomeByName(player, home2);
                int distance = getHomeDistance(player, i);
                if (distance != -1) {
                    formated = home2 + ": " + distance + " \n";
                } else {
                    formated = home2 + ": Not in this dimension" + " \n";
                }
                message.append(formated);
            }

            TextComponent diff = new TextComponent("[TAKE ME SOMEWHERE ELSE]");
            diff.setColor(net.md_5.bungee.api.ChatColor.of(new java.awt.Color(196, 196, 196)));
            diff.setBold(true);
            diff.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/selecthome"));
            diff.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(message.toString()).color(net.md_5.bungee.api.ChatColor.DARK_GRAY).create()));

            //endregion
            TextComponent space = new TextComponent("   ");

            player.spigot().sendMessage(diff, space, yes);
        }

    }
    public HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(hm.entrySet());

        list.sort(Map.Entry.comparingByValue());


        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    public void setHome(Player player, @Nullable String name_){
        int houseNumber = 6;
        for (int i = 1; i <= checkMaxHouses(); i++){
            if(plugin.getConfig().getBoolean("Players." + player.getName() + ".Home" + i +".Empty")){
                houseNumber = i;
                break;
            }
        }

        if(houseNumber > plugin.getConfig().getInt("MaxHomes")){
            player.sendMessage(ChatColor.RED + "You have reached the maximum amount of homes you can have!");
            return;
        }
        String name = name_;
        if(name == null){name = "Home";}
        if(containsName(player, name)){ player.sendMessage(ChatColor.RED + "Please name your home differently, a home with this name already exists!");return;}

        plugin.getConfig().set("Players." + player.getName() + ".Home" + houseNumber +".Empty", false);
        plugin.getConfig().set("Players." + player.getName() + ".Home" + houseNumber +".Name", name);
        plugin.getConfig().set("Players." + player.getName() + ".Home" + houseNumber +".Location", player.getLocation());

        plugin.getConfig().set("Players." + player.getName() + ".CurrentHomes", houseNumber);
        plugin.saveConfig();

        player.sendMessage(ChatColor.GREEN + name + " successfully created!");
    }
    public void teleport(Player player, Location location, String name){
        if (plugin.getConfig().getBoolean("TeleportDelayBar")) {
            BossBar bar = Bukkit.createBossBar("Teleporting to " + name + " in: ", theme.getBarColor(), BarStyle.SOLID);
            bar.setVisible(true);
            bar.addPlayer(player);

            int delay = plugin.getConfig().getInt("TeleportDelay");
            long time = System.currentTimeMillis() + (delay * 1000L);

            new BukkitRunnable() {


                @Override
                public void run() {
                    if(time - System.currentTimeMillis() <= 0.1){
                        bar.removeAll();
                        cancel();
                        player.teleport(location, PlayerTeleportEvent.TeleportCause.COMMAND);
                    }

                    double d = time - System.currentTimeMillis();
                    double timeLeft = d / 1000.d;
                    double time = d  / (delay * 1000);
                    DecimalFormat df = new DecimalFormat("0.0");

                    bar.setTitle("Teleporting to " + name + " in: " + df.format(timeLeft));
                    try {
                        bar.setProgress(time);
                    } catch (IllegalArgumentException exception) {
                        player.sendMessage();
                    }

                }
            }.runTaskTimer(plugin, 0, 0);
        } else {
            int delay = plugin.getConfig().getInt("TeleportDelay");
            long time = System.currentTimeMillis() + (delay * 1000L);

            new BukkitRunnable() {


                @Override
                public void run() {
                    if(time - System.currentTimeMillis() <= 0.1){
                        cancel();
                        player.teleport(location, PlayerTeleportEvent.TeleportCause.COMMAND);
                    }
                }
            }.runTaskTimer(plugin, 0, 0);
        }
    }
    public void deleteHome(Player player, String name){
        int i = getHomeByName(player, name);
        if(plugin.getConfig().contains("Players." + player.getName() + ".Home" + i +".Name") && Objects.requireNonNull(plugin.getConfig().getString("Players." + player.getName() + ".Home" + i + ".Name")).equalsIgnoreCase(name)){
            plugin.getConfig().set("Players." + player.getName() + ".Home" + i +".Empty", true);
            plugin.getConfig().set("Players." + player.getName() + ".Home" + i +".Name", null);
            plugin.getConfig().set("Players." + player.getName() + ".Home" + i +".Location", null);
            plugin.getConfig().set("Players." + player.getName() + ".CurrentHomes", plugin.getConfig().getInt("Players." + player.getName() + ".CurrentHomes") - 1);
            plugin.saveConfig();
            player.sendMessage(ChatColor.GREEN + name + " successfully deleted!");


        }else{player.sendMessage(ChatColor.RED + name + " not found!");}
    }
    public void reloadTheme(Theme theme){this.theme =theme;}
}
