package fr.sigmaz.ninjasupraatouts;

import fr.sigmaz.ninjasupraatouts.command.AtoutsCommand;
import fr.sigmaz.ninjasupraatouts.listener.ItemListeners;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {
    public static Main instance;
    private FileConfiguration dataConfig = null;
    private File dataFile = null;
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

        //data.yml
        saveDefaultDataConfig();
        getDataConfig().options().copyDefaults(true);
        reloadDataConfig();

        //commands
        getCommand("ninjaatouts").setExecutor(new AtoutsCommand());

        //listeners
        getServer().getPluginManager().registerEvents(new ItemListeners(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reloadDataConfig() {
        if (dataFile == null) {
            dataFile = new File(getDataFolder(), "data.yml");
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);

        // Charger le fichier de configuration par défaut s'il est vide
        try (Reader defaultConfigStream = new InputStreamReader(getResource("data.yml"), StandardCharsets.UTF_8)) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
            dataConfig.setDefaults(defaultConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour obtenir la configuration "data.yml"
    public FileConfiguration getDataConfig() {
        if (dataConfig == null) {
            reloadDataConfig();
        }
        return dataConfig;
    }

    // Méthode pour sauvegarder la configuration "data.yml"
    public void saveDataConfig() {
        if (dataConfig == null || dataFile == null) {
            return;
        }
        try {
            getDataConfig().save(dataFile);
        } catch (IOException e) {
            getLogger().warning("Impossible de sauvegarder le fichier data.yml !");
        }
    }

    // Méthode pour créer le fichier "data.yml" s'il n'existe pas
    public void saveDefaultDataConfig() {
        if (dataFile == null) {
            dataFile = new File(getDataFolder(), "data.yml");
        }
        if (!dataFile.exists()) {
            saveResource("data.yml", false);
        }
    }

    public static Main getInstance(){
        return instance;
    }
}
