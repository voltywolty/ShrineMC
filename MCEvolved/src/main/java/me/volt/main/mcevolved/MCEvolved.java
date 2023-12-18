package me.volt.main.mcevolved;

import me.volt.main.mcevolved.managers.CountdownTimer;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCEvolved extends JavaPlugin {
    private static MCEvolved plugin;

    public MCEvolved getInstance() {
        return plugin;
    }

    private String serverName = null;
    private String datacenter = "US";
    private String lobbyName = "LOBBY_1";

    private CountdownTimer countdownTimer = null;
    private GameMode gameMode;

    private String serverStatus;
    private boolean serverJoinable = true;

    private GlobalGameMode globalGameMode = new GlobalGameMode();

    public void onEnable() {
        plugin = this;

        plugin.getLogger().info("MCEvolved plugin is running!");
    }

    public void onDisable() {
        plugin.getLogger().info("MCEvolved plugin has stopped!");
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
        plugin.countdownTimer = new CountdownTimer(plugin, plugin.gameMode, seconds, minPlayers);
    }

    public static void stopCountdown() {
        if (plugin.countdownTimer != null)
            plugin.countdownTimer.stopTimer();
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
