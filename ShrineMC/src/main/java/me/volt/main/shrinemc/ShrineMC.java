package me.volt.main.shrinemc;

import me.volt.main.shrinemc.gamemode.GameMode;
import me.volt.main.shrinemc.gamemode.GlobalGameMode;
import me.volt.main.shrinemc.listeners.ServerListener;
import me.volt.main.shrinemc.managers.CommandManager;
import me.volt.main.shrinemc.managers.ConfigManager;
import me.volt.main.shrinemc.managers.CountdownBar;
import me.volt.main.shrinemc.managers.ItemManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShrineMC extends JavaPlugin {
    private static ShrineMC plugin;
    public ConfigManager configManager;
    public ItemManager itemManager;

    private final String serverName = null;

    private CountdownBar countdownBar;
    private GameMode gameMode;

    private String serverStatus;
    private boolean serverJoinable = true;

    private final GlobalGameMode globalGameMode = new GlobalGameMode();

    public void onEnable() {
        plugin = this;
        plugin.getLogger().info("ShrineMC plugin is running.");

        configManager = new ConfigManager(this);
        configManager.loadConfig();

        itemManager = new ItemManager(this);

        getCommand("shrinemc").setExecutor(new CommandManager(this));

        Bukkit.getPluginManager().registerEvents(new ServerListener(this, itemManager), this);
    }

    public void onDisable() {
        plugin.getLogger().info("ShrineMC plugin has stopped running.");
    }

    public static String getServerName() {
        return plugin.serverName;
    }

    public static void initializeGameMode(GameMode gameMode) {
        plugin.gameMode = gameMode;
    }

    public static GameMode getRunningGameMode() {
        return plugin.gameMode;
    }

    public static void startCountdown(int seconds, int minPlayers) {
        plugin.countdownBar = new CountdownBar(plugin, plugin.gameMode, seconds, minPlayers);
    }

    public static void stopCountdown() {
        if (plugin.countdownBar != null)
            plugin.countdownBar.stopTimer();
    }

    public GameMode getGlobalGameMode() {
        return plugin.globalGameMode;
    }

    public static void setServerStatus(String status) {
        plugin.serverStatus = status;
    }

    public static void setServerJoinable(boolean joinable) {
        plugin.serverJoinable = joinable;
    }
}
