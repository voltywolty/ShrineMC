package me.volt.main.mcevolved.managers;

import me.volt.main.mcevolved.GameMode;
import me.volt.main.mcevolved.MCEvolved;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CountdownTimer {
    MCEvolved plugin;

    GameMode gameMode;

    int seconds;

    int minPlayers;

    Countdown countdown;

    class Countdown implements Runnable {
        boolean running = true;

        public void run() {
            int remaining = CountdownTimer.this.seconds;

            try {
                while (remaining > 0) {
                    if (!this.running)
                        return;
                    CountdownTimer.this.broadcastTimeRemaining(remaining);
                    Thread.sleep(1000L);
                    remaining--;
                }
            }
            catch (Exception exception) {}

            if (!this.running)
                return;

            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CountdownTimer.this.plugin, new Runnable() {
                public void run() {
                    CountdownTimer.this.countdownDone();
                }
            });
        }

        public void stop() {
            this.running = false;
        }
    }

    public CountdownTimer(MCEvolved plugin, GameMode gameMode, int seconds, int minPlayers) {
        this.plugin = plugin;
        this.seconds = seconds;
        this.minPlayers = minPlayers;
        this.gameMode = gameMode;

        startTimer();
    }

    public void startTimer() {
        this.countdown = new Countdown();

        Thread thread = new Thread(this.countdown);
        thread.start();
    }

    public void stopTimer() {
        this.countdown.stop();
    }

    void broadcastTimeRemaining(final int s) {
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable() {
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers())
                    players.sendTitle("" + ChatColor.BLUE + ChatColor.BLUE, "", 1, 15, 1);
            }
        });
    }

    void countdownDone() {
        if (Bukkit.getOnlinePlayers().size() >= this.minPlayers)
            this.gameMode.startGame();
        else {
            // Not deprecated, it's just PaperMC
            Bukkit.broadcastMessage("" + ChatColor.RED + "There are not enough players to start the game! The game will start when there is at least 5 players.");
            startTimer();
        }
    }
}