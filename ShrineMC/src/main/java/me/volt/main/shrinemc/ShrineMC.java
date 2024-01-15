package me.volt.main.shrinemc;

import me.volt.main.shrinemc.gamemode.GameMode;
import me.volt.main.shrinemc.gamemode.GlobalGameMode;
import me.volt.main.shrinemc.listeners.ServerListener;
import me.volt.main.shrinemc.managers.*;

import org.bukkit.Difficulty;
import org.bukkit.GameRule;
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

        ServerManager.getValues();
        ServerManager.initializeScoreboard();

        getCommand("shrinemc").setExecutor(new CommandManager(this));

        initializeWorldSettings();
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
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getPluginManager().registerEvents(new ServerListener(this, itemManager), this);
    }

    private void initializeWorldSettings() {
        getServer().getWorlds().get(0).setDifficulty(Difficulty.PEACEFUL);

        getServer().getWorlds().get(0).setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        getServer().getWorlds().get(0).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        getServer().getWorlds().get(0).setGameRule(GameRule.KEEP_INVENTORY, true);
        getServer().getWorlds().get(0).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
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
