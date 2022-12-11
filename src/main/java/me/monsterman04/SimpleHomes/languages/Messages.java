package me.monsterman04.SimpleHomes.languages;

import me.monsterman04.SimpleHomes.Main;
import org.bukkit.ChatColor;

public enum Messages {
    PLAYER_ONLY_COMMAND(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".PLAYER_ONLY_COMMAND")),
    MISSING_PERMISSION(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".MISSING_PERMISSION")),
    HOME_CREATE_ERROR(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".HOME_CREATE_ERROR")),
    NO_HOME_ERROR(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".NO_HOME_ERROR")),
    HOME_LOCATE_ERROR(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".HOME_LOCATE_ERROR")),
    HOME_NOT_FOUND(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".HOME_NOT_FOUND")),
    HOME_LIST(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".HOME_LIST")),
    HOME_DELETE_ERROR(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".HOME_DELETE_ERROR")),
    TELEPORT_SELECTION(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".TELEPORT_SELECTION")),
    HOME_IN_ANOTHER_DIMENSION(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".HOME_IN_ANOTHER_DIMENSION")),
    TELEPORT_SELECT_SUPPORT_TEXT(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".TELEPORT_SELECT_SUPPORT_TEXT")),
    CURRENT_THEME(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".CURRENT_THEME")),
    NEW_THEME(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".NEW_THEME")),
    SET_THEME_ERROR(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".SET_THEME_ERROR")),
    HOME_DELETED(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".HOME_DELETED")),
    HOME_TELEPORT(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".HOME_TELEPORT")),
    HOME_CREATED(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".HOME_CREATED")),
    HOME_ALREADY_EXISTS(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".HOME_ALREADY_EXISTS")),
    HOME_LIMIT_REACHED(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".HOME_LIMIT_REACHED")),
    LOGIN_TELEPORT_SUGGESTION(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".LOGIN_TELEPORT_SUGGESTION")),
    YES_OPTION(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".YES_OPTION")),
    ELSE_OPTION(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".ELSE_OPTION")),
    NEW_LANGUAGE(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".NEW_LANGUAGE")),
    SET_LANGUAGE_ERROR(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".SET_LANGUAGE_ERROR")),
    MIGRATION_SUCCESSFUL(LanguagesConfig.getConfig().getString(Main.getSelectedLanguage() + ".MIGRATION_SUCCESSFUL"));


    private String text;

    Messages(String text){this.text = text;}

    public String getText() {return ChatColor.translateAlternateColorCodes('&', text);}
}
