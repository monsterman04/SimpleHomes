package com.monsterman04.SimpleHomes.commands;

import com.monsterman04.SimpleHomes.HomeManager;
import com.monsterman04.SimpleHomes.Main;
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

@SuppressWarnings({"deprecation", "NullableProblems"})
public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }
        Main plugin = Main.getPlugin(Main.class);

        if(command.getName().equalsIgnoreCase("sethome")){
            if(args.length == 0){
                HomeManager.setHome(player, null);
            }
            else if(args.length == 1){
                HomeManager.setHome(player, args[0]);
            }
            else{player.sendMessage(ChatColor.RED + "An error occurred while setting your home!");}
        }
        if(command.getName().equalsIgnoreCase("home")){
            if(HomeManager.allNames(player).isEmpty()){
                player.sendMessage(ChatColor.RED + "You don't have any homes yet!");
            }else{
                if(args.length == 0){
                    int num = HomeManager.getHomeByName(player, "Home");
                    if(plugin.getConfig().getLocation("Players." + player.getName() + ".Home" + num +".Location") != null){
                        Location location = plugin.getConfig().getLocation("Players." + player.getName() + ".Home" + num +".Location");
                        if (location != null) {
                            HomeManager.teleport(player , location);
                        }
                    }else{
                        for (int i = 2; i <= HomeManager.checkMaxHouses(); i++){
                            if(plugin.getConfig().getLocation("Players." + player.getName() + ".Home" + i +".Location") != null){
                                Location location = plugin.getConfig().getLocation("Players." + player.getName() + ".Home" + i +".Location");
                                if (location != null) {
                                    HomeManager.teleport(player , location);
                                }
                            }
                        }
                    }
                }
                else if(args.length == 1){
                    if (HomeManager.containsName(player, args[0])) {
                        int num = HomeManager.getHomeByName(player, args[0]);
                        if(plugin.getConfig().getLocation("Players." + player.getName() + ".Home" + num +".Location") != null){
                            Location location = plugin.getConfig().getLocation("Players." + player.getName() + ".Home" + num +".Location");
                            if (location != null) {
                                HomeManager.teleport(player , location);
                            }
                        }else{player.sendMessage(ChatColor.RED + "An error occurred while locating your home!");}
                    } else {player.sendMessage(ChatColor.RED + args[0] + " not found!");}
                }
                else{player.sendMessage(ChatColor.RED + "An error occurred while locating your home!");}
            }
        }
        if(command.getName().equalsIgnoreCase("homes")){
            player.sendMessage(ChatColor.AQUA +""+ ChatColor.STRIKETHROUGH + StringUtils.repeat(" ", 80));
            player.sendMessage(ChatColor.AQUA + "Here is a list of all your homes");
            StringBuilder message = new StringBuilder();
            for (int i = 1; i <= HomeManager.checkMaxHouses(); i++){
                String formated;
                if(plugin.getConfig().contains("Players." + player.getName() + ".Home" + i +".Name")){
                    formated = ChatColor.AQUA + "" + ChatColor.BOLD + i + ")" + ChatColor.RESET + " " + ChatColor.WHITE + plugin.getConfig().getString("Players." + player.getName() + ".Home" + i +".Name") +"\n";
                }else{
                    formated = ChatColor.AQUA + "" + ChatColor.BOLD + i +")" + ChatColor.RESET + " " + ChatColor.WHITE + " \n";
                }

                message.append(formated);
            }
            player.sendMessage(message.toString());
            player.sendMessage(ChatColor.AQUA +""+ ChatColor.STRIKETHROUGH + StringUtils.repeat(" ", 80));
        }
        if(command.getName().equalsIgnoreCase("deletehome")){
            if(args.length == 1){
                HomeManager.deleteHome(player, args[0]);
            }else{player.sendMessage(ChatColor.RED + "An error occurred while deleting your home!");}
        }
        if(command.getName().equalsIgnoreCase("selecthome")){
            player.sendMessage(ChatColor.AQUA +""+ ChatColor.STRIKETHROUGH + StringUtils.repeat(" ", 80));
            player.sendMessage(ChatColor.AQUA + "Where would you like to be teleported?");
            for(String home : HomeManager.allNames(player)){
                int homeNum = HomeManager.getHomeByName(player , home);
                String distance;
                if (HomeManager.getHomeDistance(player, homeNum) == -1) {
                    distance = "In different dimension";
                } else {
                    distance = String.valueOf(HomeManager.getHomeDistance(player, homeNum));
                }
                TextComponent diff = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + "◆ " + ChatColor.RESET + home);
                diff.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/home " + home));
                diff.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Teleporting to " + home + ", Distance: " + distance)
                        .color(net.md_5.bungee.api.ChatColor.DARK_GRAY).create()));

                player.spigot().sendMessage(diff);
            }
            player.sendMessage(ChatColor.AQUA +""+ ChatColor.STRIKETHROUGH + StringUtils.repeat(" ", 80));
        }

        return true;
    }
}
