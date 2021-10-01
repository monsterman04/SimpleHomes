package me.monsterman04.SimpleHomes;

import me.monsterman04.SimpleHomes.commands.Commands;
import me.monsterman04.SimpleHomes.commands.HomeTabComplete;
import me.monsterman04.SimpleHomes.commands.ThemeTabComplete;
import me.monsterman04.SimpleHomes.events.Events;
import me.monsterman04.SimpleHomes.themes.ThemeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Main extends JavaPlugin {

    private final PluginDescriptionFile pluginDescriptionFile = getDescription();
    private HomeManager homeManager;
    private ThemeManager themeManager;

    @Override
    public void onEnable(){
        this.themeManager = new ThemeManager(this);
        this.homeManager = new HomeManager(this);
        Objects.requireNonNull(getCommand("sethome")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("home")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("home")).setTabCompleter(new HomeTabComplete(homeManager));
        Objects.requireNonNull(getCommand("homes")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("deletehome")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("deletehome")).setTabCompleter(new HomeTabComplete(homeManager));
        Objects.requireNonNull(getCommand("selecthome")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("simplehomestheme")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("simplehomestheme")).setTabCompleter(new ThemeTabComplete());
        getServer().getPluginManager().registerEvents(new Events(this), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[" + pluginDescriptionFile.getName() + "] Plugin started!");
        new VersionManager(this).checkVersion();

        getConfig().options().copyDefaults(true);
        saveConfig();
        createConfigHelpFile();

        for(Player player : Bukkit.getOnlinePlayers()) {
            homeManager.joinSetup(player);
        }
    }

    @Override
    public void onDisable(){getServer().getConsoleSender().sendMessage(ChatColor.RED + "[" + pluginDescriptionFile.getName() + "] Plugin closed!");}

    private void createConfigHelpFile(){
        //region Help String
        List<String> contents = new ArrayList<>();
        contents.add("MaxHomes: 5  ->  The maximum number of houses that each player can have. Maximum value is 50");
        contents.add("TeleportDelay: 5  ->  Teleportation delay in seconds. If you want no delay (Instant teleportation) set this to 0");
        contents.add("TeleportDelayBar: true  ->  If you don't want the remaining time to be displayed at the top of the screen (BossBar), set this to false");
        contents.add("SuggestHomeTeleportOnJoin: true  ->   If you don't want to suggest home teleportation whenever player joins the server, set this to false");
        contents.add("SuggestHomeTeleportDistance: 200  ->   Minimum distance for home teleportation suggestion to appear whenever player joins. Distance is measured in blocks");
        contents.add("Theme: BLUE  ->   Color theme of this plugin. Choose from: BLUE, GREEN, PINK, PURPLE, RED, WHITE, YELLOW");
        contents.add("");
        contents.add("Plugins spigot page: https://www.spigotmc.org/resources/simplehomes.96179/");
        contents.add("If you need any support, would like to report a bug, contact me here: https://discord.com/invite/jjUbVbjaCk");
        //endregion

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.getDataFolder().getPath() + "\\config.yml - help.txt"));

            for(String line : contents){
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException ignored) {}
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }

    public ThemeManager getThemeManager() {
        return themeManager;
    }
}
