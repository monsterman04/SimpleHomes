package me.monsterman04.SimpleHomes.commands;

import me.monsterman04.SimpleHomes.themes.Theme;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class ThemeTabComplete implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){

        if(args.length == 1){
            List<String> result = new ArrayList<>();
            List<String> list = new ArrayList<>();
            list.add("get");
            list.add("set");

            for(String s : list){
                if(s.toLowerCase().startsWith(args[0].toLowerCase())){result.add(s);}
            }
            return result;
        }

        if(args.length >= 2){
            if (args[0].equals("set")) {
                List<String> result = new ArrayList<>();
                List<String> list = new ArrayList<>();
                for(Theme theme : Theme.values()){
                    list.add(theme.name());
                }

                for(String s : list){
                    if(s.toLowerCase().startsWith(args[1].toLowerCase())){result.add(s);}
                }
                if (args.length == 2) {
                    return result;
                } else {return new ArrayList<>();}
            }
            if (args[0].equals("get")) {
                return new ArrayList<>();
            }
        }

        return null;
    }
}
