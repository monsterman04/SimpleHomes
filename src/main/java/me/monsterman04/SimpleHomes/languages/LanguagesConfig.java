package me.monsterman04.SimpleHomes.languages;

import me.monsterman04.SimpleHomes.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LanguagesConfig {
    private static File file;
    private static FileConfiguration config;

    public static void setup(Main main){
        file = new File(main.getDataFolder(), "languages.yml");

        if(!file.exists()){
            try {
                boolean created = file.createNewFile();
            } catch (IOException e) {e.printStackTrace();}
        }

        config = YamlConfiguration.loadConfiguration(file);
        createConfigData(config);
    }

    public static FileConfiguration getConfig() {return config;}

    public static void save(){
        try {
            config.save(file);
        } catch (IOException e) {
            Main.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.RED + "[" + Main.getDescriptionFile().getName() + "] languages.yml wasn't able to save!");
        }
    }

    public static void reloadConfig(){
        config = YamlConfiguration.loadConfiguration(file);
        Main.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[" + Main.getDescriptionFile().getName() + "] languages.yml reloaded!");
    }

    public static void createConfigData(FileConfiguration configuration){
        if(configuration.getString("Language") == null){
            configuration.set("Language", "en");
        }

        if (configuration.getConfigurationSection("en") == null) {
            ConfigurationSection section = configuration.createSection("en");
            section.set("PLAYER_ONLY_COMMAND", "&cOnly players can use this command!");
            section.set("MISSING_PERMISSION", "&cYou don't have the required permissions to do this!");
            section.set("HOME_CREATE_ERROR", "&cAn error occurred while setting your home!");
            section.set("NO_HOME_ERROR", "&cYou don't have any homes yet!");
            section.set("HOME_LOCATE_ERROR", "&cAn error occurred while locating your home!");
            section.set("HOME_NOT_FOUND", "&c%home% not found!");
            section.set("HOME_LIST", "Here is a list of all your homes:");
            section.set("TELEPORT_SELECTION", "Where would you like to be teleported?");
            section.set("TELEPORT_SELECT_SUPPORT_TEXT", "Teleporting to %home%, Distance: %distance%");
            section.set("HOME_DELETED", "&a%home% successfully deleted!");
            section.set("HOME_DELETE_ERROR", "&cAn error occurred while deleting your home!");
            section.set("HOME_TELEPORT", "&fTeleporting to %home% in: %time%");
            section.set("HOME_CREATED", "&a%home% successfully created!");
            section.set("HOME_ALREADY_EXISTS", "&cPlease name your home differently, a home with this name already exists!");
            section.set("HOME_LIMIT_REACHED", "&cYou have reached the maximum amount of homes you can have!");
            section.set("LOGIN_TELEPORT_SUGGESTION", "It looks like you're far from home. Would you like to be teleported to %home%?");
            section.set("YES_OPTION", "[YES]");
            section.set("ELSE_OPTION", "[TAKE ME SOMEWHERE ELSE]");
            section.set("HOME_IN_ANOTHER_DIMENSION", "In different dimension");
            section.set("CURRENT_THEME", "Your current Theme is %theme%");
            section.set("NEW_THEME", "Theme successfully was set to %theme%");
            section.set("SET_THEME_ERROR", "&cThere was an error while setting new Theme!");
            section.set("NEW_LANGUAGE", "Language successfully was set to %language%. Please restart the server in order to make this change!");
            section.set("SET_LANGUAGE_ERROR", "&cThere was an error while setting new Language!");
            section.set("MIGRATION_SUCCESSFUL", "&aThere was a change in the way how your homes were stored. If you had any saved homes, they were migrated to a new way successfully");

        }

        save();
    }
}
