package me.monsterman04.SimpleHomes.commands;

import me.monsterman04.SimpleHomes.HomeManager;
import me.monsterman04.SimpleHomes.Main;
import me.monsterman04.SimpleHomes.languages.LanguagesConfig;
import me.monsterman04.SimpleHomes.languages.Messages;
import me.monsterman04.SimpleHomes.themes.Theme;
import me.monsterman04.SimpleHomes.themes.ThemeManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Messages.PLAYER_ONLY_COMMAND.getText());
            return true;
        }
        Main plugin = Main.getPlugin(Main.class);
        HomeManager homeManager = plugin.getHomeManager();
        ThemeManager themeManager = plugin.getThemeManager();
        ChatColor chatColor = themeManager.getTheme().getChatColor();

        if(command.getName().equalsIgnoreCase("sethome")){
            if (player.hasPermission("simplehomes.homes")) {
                if(args.length == 0){
                    homeManager.setHome(player, null);
                }
                else if(args.length == 1){
                    homeManager.setHome(player, args[0]);
                }
                else{player.sendMessage(Messages.HOME_CREATE_ERROR.getText());}
            } else {player.sendMessage(Messages.MISSING_PERMISSION.getText());}
        }
        if(command.getName().equalsIgnoreCase("home")){
            if (player.hasPermission("simplehomes.homes")) {
                if(homeManager.allNames(player).isEmpty()){
                    player.sendMessage(Messages.NO_HOME_ERROR.getText());
                }else{
                    if(args.length == 0){
                        int num = homeManager.getHomeByName(player, "Home");
                        if(plugin.getConfig().getLocation("Players." + player.getUniqueId() + ".Home" + num +".Location") != null){
                            Location location = plugin.getConfig().getLocation("Players." + player.getUniqueId() + ".Home" + num +".Location");
                            String name = plugin.getConfig().getString("Players." + player.getUniqueId() + ".Home" + num +".Name");
                            if (location != null) {
                                homeManager.teleport(player , location, name);
                            }
                        }else{
                            for (int i = 2; i <= homeManager.checkMaxHouses(); i++){
                                if(plugin.getConfig().getLocation("Players." + player.getUniqueId() + ".Home" + i +".Location") != null){
                                    Location location = plugin.getConfig().getLocation("Players." + player.getUniqueId() + ".Home" + i +".Location");
                                    String name = plugin.getConfig().getString("Players." + player.getUniqueId() + ".Home" + num +".Name");
                                    if (location != null) {
                                        homeManager.teleport(player , location, name);
                                    }
                                }
                            }
                        }
                    }
                    else if(args.length == 1){
                        if (homeManager.containsName(player, args[0])) {
                            int num = homeManager.getHomeByName(player, args[0]);
                            if(plugin.getConfig().getLocation("Players." + player.getUniqueId() + ".Home" + num +".Location") != null){
                                Location location = plugin.getConfig().getLocation("Players." + player.getUniqueId() + ".Home" + num +".Location");
                                String name = plugin.getConfig().getString("Players." + player.getUniqueId() + ".Home" + num +".Name");
                                if (location != null) {
                                    homeManager.teleport(player , location, name);
                                }
                            }else{player.sendMessage(Messages.HOME_LOCATE_ERROR.getText());}
                        } else {player.sendMessage(Messages.HOME_NOT_FOUND.getText().replace("%home%", args[0]));}
                    }
                    else{player.sendMessage(Messages.HOME_LOCATE_ERROR.getText());}
                }
            } else {player.sendMessage(Messages.MISSING_PERMISSION.getText());}
        }
        if(command.getName().equalsIgnoreCase("homes")){
            if (player.hasPermission("simplehomes.homes")) {
                player.sendMessage(chatColor +""+ ChatColor.STRIKETHROUGH + StringUtils.repeat(" ", 80));
                player.sendMessage(chatColor + ChatColor.stripColor(Messages.HOME_LIST.getText()));
                StringBuilder message = new StringBuilder();
                for (int i = 1; i <= homeManager.checkMaxHouses(); i++){
                    String formated;
                    if(plugin.getConfig().contains("Players." + player.getUniqueId() + ".Home" + i +".Name")){
                        formated = chatColor + "" + ChatColor.BOLD + i + ")" + ChatColor.RESET + " " + ChatColor.WHITE + plugin.getConfig().getString("Players." + player.getUniqueId() + ".Home" + i +".Name") +"\n";
                    }else{
                        formated = chatColor + "" + ChatColor.BOLD + i +")" + ChatColor.RESET + " " + ChatColor.WHITE + " \n";
                    }

                    message.append(formated);
                }
                player.sendMessage(message.toString());
                player.sendMessage(chatColor +""+ ChatColor.STRIKETHROUGH + StringUtils.repeat(" ", 80));
            } else {player.sendMessage(Messages.MISSING_PERMISSION.getText());}
        }
        if(command.getName().equalsIgnoreCase("deletehome")){
            if (player.hasPermission("simplehomes.homes")) {
                if(args.length == 1){
                    homeManager.deleteHome(player, args[0]);
                }else{player.sendMessage(Messages.HOME_DELETE_ERROR.getText());}
            } else {player.sendMessage(Messages.MISSING_PERMISSION.getText());}
        }
        if(command.getName().equalsIgnoreCase("selecthome")){
            if (player.hasPermission("simplehomes.homes")) {
                player.sendMessage(chatColor +""+ ChatColor.STRIKETHROUGH + StringUtils.repeat(" ", 80));
                player.sendMessage(chatColor + ChatColor.stripColor(Messages.TELEPORT_SELECTION.getText()));
                for(String home : homeManager.allNames(player)){
                    int homeNum = homeManager.getHomeByName(player , home);
                    String distance;
                    if (homeManager.getHomeDistance(player, homeNum) == -1) {
                        distance = ChatColor.stripColor(Messages.HOME_IN_ANOTHER_DIMENSION.getText());
                    } else {
                        distance = String.valueOf(homeManager.getHomeDistance(player, homeNum));
                    }
                    TextComponent diff = new TextComponent(chatColor + "" + ChatColor.BOLD + "◆ " + ChatColor.RESET + home);
                    diff.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/home " + home));
                    String message = Messages.TELEPORT_SELECT_SUPPORT_TEXT.getText().replace("%home%", home).replace("%distance%", distance);
                    diff.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.stripColor(message))
                            .color(net.md_5.bungee.api.ChatColor.DARK_GRAY).create()));

                    player.spigot().sendMessage(diff);
                }
                player.sendMessage(chatColor +""+ ChatColor.STRIKETHROUGH + StringUtils.repeat(" ", 80));
            } else {player.sendMessage(Messages.MISSING_PERMISSION.getText());}
        }
        if(command.getName().equalsIgnoreCase("simplehomestheme")){
            if (player.hasPermission("simplehomes.theme")) {
                if(args.length == 1 || args.length == 2){
                    switch (args[0]){
                        case "get" -> player.sendMessage(chatColor + ChatColor.stripColor(Messages.CURRENT_THEME.getText().replace("%theme%", themeManager.getTheme().toString())));
                        case "set" -> {
                            List<String> list = new ArrayList<>();
                            for(Theme theme : Theme.values()){
                                list.add(theme.name());
                            }
                            if(list.contains(args[1].toUpperCase())){
                                themeManager.setTheme(Theme.valueOf(args[1].toUpperCase()));
                                if(themeManager.getTheme() == Theme.valueOf(args[1].toUpperCase())){
                                    player.sendMessage(themeManager.getTheme().getChatColor() + ChatColor.stripColor(Messages.NEW_THEME.getText()).replace("%theme%", themeManager.getTheme().toString()));
                                    homeManager.reloadTheme(themeManager.getTheme());
                                } else{player.sendMessage(Messages.SET_THEME_ERROR.getText());}
                            } else{player.sendMessage(Messages.SET_THEME_ERROR.getText());}
                        }
                    }
                }
            } else {player.sendMessage(Messages.MISSING_PERMISSION.getText());}
        }
        if(command.getName().equalsIgnoreCase("simplehomeslanguage")){
            if (player.hasPermission("simplehomes.language")) {
                if(args.length == 1){
                    String language = args[0];
                    if (LanguagesConfig.getConfig().getKeys(false).contains(language)) {
                        LanguagesConfig.getConfig().set("Language", language);
                        LanguagesConfig.save();
                        player.sendMessage(themeManager.getTheme().getChatColor() + ChatColor.stripColor(Messages.NEW_LANGUAGE.getText().replace("%language%", language)));
                    } else {player.sendMessage(Messages.SET_LANGUAGE_ERROR.getText());}
                }
            } else {player.sendMessage(Messages.MISSING_PERMISSION.getText());}
        }

        return true;
    }
}
