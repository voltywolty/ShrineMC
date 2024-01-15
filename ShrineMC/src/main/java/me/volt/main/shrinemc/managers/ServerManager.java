package me.volt.main.shrinemc.managers;

import me.volt.main.shrinemc.ShrineMC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

public class ServerManager {
    private static ShrineMC plugin;

    private static ScoreboardManager manager;
    public static Scoreboard servers;

    public static String mapName;
    public static int online = 0;

    public ServerManager(ShrineMC plugin) {
        ServerManager.plugin = plugin;
    }

    public static void getValues() {
        manager = Bukkit.getScoreboardManager();
        servers = manager.getNewScoreboard();
    }

    public static void initializeScoreboard() {
        Objective objective = servers.registerNewObjective("servers", "dummy", ChatColor.AQUA + "Servers");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        final Score game = objective.getScore(ChatColor.RED + "RED");
        new BukkitRunnable() {
            public void run() {
                game.setScore(online);
            }
        }.runTaskTimer(plugin, 0L, 0L);
    }
}
