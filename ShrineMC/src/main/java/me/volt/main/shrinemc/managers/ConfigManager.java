package me.volt.main.shrinemc.managers;

import me.volt.main.shrinemc.ShrineMC;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    // Files and File Configs
    private YamlConfiguration config;
    private File configFile;
    // ----------------------

    public void loadConfig() {
        config = loadConfig("config.yml");
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    private YamlConfiguration loadConfig(String fileName) {
        File configFile = new File(ShrineMC.getInstance().getDataFolder(), fileName);

        if (!configFile.exists()) {
            ShrineMC.getInstance().getLogger().warning("Config file not found! Creating a new one: " + fileName);

            try {
                configFile.createNewFile();
            }
            catch (IOException e) {
                ShrineMC.getInstance().getLogger().severe("Could not create " + fileName + " file!");
                e.printStackTrace();
            }
        }

        return YamlConfiguration.loadConfiguration(configFile);
    }

    public void reloadConfigs() {
        config = reloadConfig("config.yml", config);
    }

    private YamlConfiguration reloadConfig(String fileName, YamlConfiguration config) {
        File configFile = new File(ShrineMC.getInstance().getDataFolder(), fileName);

        if (!configFile.exists()) {
            ShrineMC.getInstance().getLogger().warning("Config file not found! Creating a new one: " + fileName);

            try {
                configFile.createNewFile();
            }
            catch (IOException e) {
                ShrineMC.getInstance().getLogger().severe("Could not create " + fileName + " file!");
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        return config;
    }
}
