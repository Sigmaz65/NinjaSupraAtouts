package fr.sigmaz.ninjasupraatouts;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {
    public static Main instance;
    @Override
    public void onEnable() {
        instance = this;
        String prefix = "[NinjaAtouts] ";

        getLogger().info(prefix + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        getLogger().info(prefix + "");
        getLogger().info(prefix + "     Plugin NinjaAtouts en installation");
        getLogger().info(prefix + "             Crée par Sigmaz65");
        getLogger().info(prefix + "           Version SNAPSHOT-1.0");
        getLogger().info(prefix + "     Dernière modifications : 11/11/2023");
        getLogger().info(prefix + "");
        getLogger().info(prefix + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //config.yml
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance(){
        return instance;
    }
}
