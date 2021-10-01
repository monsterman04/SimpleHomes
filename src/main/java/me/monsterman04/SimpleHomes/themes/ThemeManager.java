package me.monsterman04.SimpleHomes.themes;

import me.monsterman04.SimpleHomes.Main;
import org.bukkit.ChatColor;

public class ThemeManager {

    private Theme theme;
    private final Main main;

    public ThemeManager(Main main){
        this.main = main;
        String themeString = main.getConfig().getString("Theme");

        if(themeString !=null){
            try {
                this.setTheme(Theme.valueOf(themeString.toUpperCase()));
            } catch (IllegalArgumentException exception) {
                invalidTheme();
            }
        }else{invalidTheme();}
    }

    private void invalidTheme(){
        this.theme = Theme.BLUE;
        main.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[" + main.getDescription().getName() + "] Wasn't able to set a valid theme! Theme was set to default Blue");
    }

    public Theme getTheme() {
        return theme;
    }
    public void setTheme(Theme theme) {
        try {
            this.theme = theme;
            main.getConfig().set("Theme", theme.toString());
            main.saveConfig();
        } catch (Exception e) {
            invalidTheme();
        }
    }
}
