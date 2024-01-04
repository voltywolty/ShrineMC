package me.volt.main.shrinemc.managers;

import me.volt.main.shrinemc.ShrineMC;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private final ShrineMC plugin;

    // Files and File Configs
    private YamlConfiguration config;
    private File configFile;
    // ----------------------

    public ConfigManager(ShrineMC plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        config = loadConfig("config.yml");
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    private YamlConfiguration loadConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);

        if (!configFile.exists()) {
            plugin.getLogger().warning("Config file not found! Creating a new one: " + fileName);

            try {
                configFile.createNewFile();
            }
            catch (IOException e) {
                plugin.getLogger().severe("Could not create " + fileName + " file!");
                e.printStackTrace();
            }
        }

        return YamlConfiguration.loadConfiguration(configFile);
    }

    public void reloadConfigs() {
        config = reloadConfig("config.yml", config);
    }

    private YamlConfiguration reloadConfig(String fileName, YamlConfiguration config) {
        File configFile = new File(plugin.getDataFolder(), fileName);

        if (!configFile.exists()) {
            plugin.getLogger().warning("Config file not found! Creating a new one: " + fileName);

            try {
                configFile.createNewFile();
            }
            catch (IOException e) {
                plugin.getLogger().severe("Could not create " + fileName + " file!");
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        return config;
    }
}
