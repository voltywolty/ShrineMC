package me.volt.main.shrinemc;

import me.volt.main.shrinemc.gamemode.GameMode;
import me.volt.main.shrinemc.gamemode.GlobalGameMode;
import me.volt.main.shrinemc.listeners.HatListener;
import me.volt.main.shrinemc.listeners.ServerListener;
import me.volt.main.shrinemc.managers.*;

import org.bukkit.plugin.java.JavaPlugin;

public final class ShrineMC extends JavaPlugin {
    private static ShrineMC instance;
    private static ConfigManager configManager;
    private static ItemManager itemManager;
    private static ServerManager serverManager;

    private CountdownBar countdownBar;
    private GameMode gameMode;

    private final GlobalGameMode globalGameMode = new GlobalGameMode();

    public static ShrineMC getInstance() {
        return instance;
    }

    public static ItemManager getItemManager() {
        return itemManager;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static ServerManager getServerManager() {
        return serverManager;
    }

    public void onEnable() {
        instance = this;
        instance.getLogger().info("ShrineMC plugin is running.");

        initializeManagers();
        initializeListeners();

        getCommand("shrinemc").setExecutor(new CommandManager());
    }

    public void onDisable() {
        instance.getLogger().info("ShrineMC plugin has stopped running.");
    }

    private void initializeManagers() {
        configManager = new ConfigManager();
        configManager.loadConfig();

        itemManager = new ItemManager();
        serverManager = new ServerManager();
    }

    private void initializeListeners() {
        getServer().getPluginManager().registerEvents(new ServerListener(), this);
        getServer().getPluginManager().registerEvents(new HatListener(), this);
    }

    public static void initializeGameMode(GameMode gameMode) {
        instance.gameMode = gameMode;
    }

    public static void startCountdown(int seconds, int minPlayers) {
        instance.countdownBar = new CountdownBar(instance.gameMode, seconds, minPlayers);
    }

    public static void stopCountdown() {
        if (instance.countdownBar != null)
            instance.countdownBar.stopTimer();
    }
}
