package me.volt.main.shrinemc;

import me.volt.main.shrinemc.gamemode.GameMode;
import me.volt.main.shrinemc.gamemode.GlobalGameMode;
import me.volt.main.shrinemc.listeners.HatListener;
import me.volt.main.shrinemc.listeners.LoadoutListener;
import me.volt.main.shrinemc.listeners.ServerListener;
import me.volt.main.shrinemc.managers.*;

import org.bukkit.plugin.java.JavaPlugin;

public final class ShrineMC extends JavaPlugin {
    private static ShrineMC plugin;
    private ConfigManager configManager;
    private ItemManager itemManager;
    private ServerManager serverManager;

    private CountdownBar countdownBar;
    private GameMode gameMode;

    private final GlobalGameMode globalGameMode = new GlobalGameMode();

    public ShrineMC getInstance() {
        return plugin;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public void onEnable() {
        plugin = this;
        plugin.getLogger().info("ShrineMC plugin is running.");

        initializeManagers();
        initializeListeners();

        getCommand("shrinemc").setExecutor(new CommandManager(this));
    }

    public void onDisable() {
        plugin.getLogger().info("ShrineMC plugin has stopped running.");
    }

    private void initializeManagers() {
        configManager = new ConfigManager(this);
        configManager.loadConfig();

        itemManager = new ItemManager(this);
        serverManager = new ServerManager(this);
    }

    private void initializeListeners() {
        getServer().getPluginManager().registerEvents(new ServerListener(this), this);
        getServer().getPluginManager().registerEvents(new HatListener(this, getItemManager()), this);
        getServer().getPluginManager().registerEvents(new LoadoutListener(this, getItemManager()), this);
    }

    public static void initializeGameMode(GameMode gameMode) {
        plugin.gameMode = gameMode;
    }

    public static void startCountdown(int seconds, int minPlayers) {
        plugin.countdownBar = new CountdownBar(plugin, plugin.gameMode, seconds, minPlayers);
    }

    public static void stopCountdown() {
        if (plugin.countdownBar != null)
            plugin.countdownBar.stopTimer();
    }
}
