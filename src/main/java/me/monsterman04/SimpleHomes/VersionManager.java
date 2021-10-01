package me.monsterman04.SimpleHomes;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class VersionManager {

    private final Main plugin;
    private String currentVersion;
    private final PluginDescriptionFile pluginDescriptionFile;

    public VersionManager(Main main){
        this.plugin = main;
        currentVersion = plugin.getDescription().getVersion();
        pluginDescriptionFile = plugin.getDescription();
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    public void checkVersion(){
        int pluginID = 96179;
        JSONObject json;
        try {
            json = readJsonFromUrl("https://api.spiget.org/v2/resources/" + pluginID + "/versions/latest");
            currentVersion = pluginDescriptionFile.getVersion();
            String latestVersion = json.getString("name");

            if(currentVersion.equalsIgnoreCase(latestVersion)){
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[" + pluginDescriptionFile.getName() + "] Current version: " + currentVersion + ", this is the latest version!");
            }else {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[" + pluginDescriptionFile.getName() + "] This plugin is out of date!");
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[" + pluginDescriptionFile.getName() + "] Current version: " + currentVersion + ", Latest version is: " + latestVersion);
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "You can download the latest version here: https://www.spigotmc.org/resources/simplehomes.96179/");
            }


        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[" + pluginDescriptionFile.getName() + "] Unable to find the latest version!");
        }
    }
}
