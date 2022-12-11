package me.monsterman04.SimpleHomes.commands;

import me.monsterman04.SimpleHomes.languages.LanguagesConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LanguageTabComplete implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){

        if(args.length == 1){
            List<String> result = new ArrayList<>();

            Set<String> keys = LanguagesConfig.getConfig().getKeys(false);
            keys.remove("Language");
            List<String> list = new ArrayList<>(keys);

            for(String s : list){
                if(s.toLowerCase().startsWith(args[0].toLowerCase())){result.add(s);}
            }
            return result;
        }

        return null;
    }
}
