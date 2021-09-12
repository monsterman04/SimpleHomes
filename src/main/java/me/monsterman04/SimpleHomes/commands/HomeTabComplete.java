package me.monsterman04.SimpleHomes.commands;

import me.monsterman04.SimpleHomes.HomeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class HomeTabComplete implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){

        if(args.length == 1){
            List<String> result = new ArrayList<>();
            List<String> list = new ArrayList<>(HomeManager.allNames((Player) sender));

            for(String s : list){
                if(s.toLowerCase().startsWith(args[0].toLowerCase())){result.add(s);}
            }
            return result;
        }

        return null;
    }
}
