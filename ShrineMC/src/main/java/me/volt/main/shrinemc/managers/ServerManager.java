package me.volt.main.shrinemc.managers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.volt.main.shrinemc.ShrineMC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.net.Socket;

public class ServerManager {
    private static ShrineMC plugin;

    private static ScoreboardManager manager;
    public static Scoreboard servers;

    public static String lobbyName = ChatColor.RED + "RED";
    public static String serverStatus;
    public static int online = 0;

    public ServerManager(ShrineMC plugin) {
        ServerManager.plugin = plugin;
    }

    public void getValues() {
        manager = Bukkit.getScoreboardManager();
        servers = manager.getNewScoreboard();
    }

    public void initializeScoreboard() {
        Objective objective = servers.registerNewObjective("servers", "dummy", ChatColor.AQUA + "Servers");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        new BukkitRunnable() {
            public void run() {
                if (isServerOnline()) {
                    Score game = objective.getScore(lobbyName);
                    game.setScore(online);
                }
            }
        }.runTaskTimer(plugin, 0L, 0L);
    }

    public static void setServerStatus(String status) {
        serverStatus = status;
    }

    public String getServerStatus() {
        return serverStatus;
    }

    public static void setLobbyName(String name) {
        lobbyName = name;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public static void sendPlayerToLobby(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("lobby");

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    public boolean isServerOnline() {
        try {
            Socket s = new Socket("localhost", 25567);

            if (s.isConnected())
                return true;
        }
        catch (Exception e) {
            return false;
        }

        return false;
    }
}
