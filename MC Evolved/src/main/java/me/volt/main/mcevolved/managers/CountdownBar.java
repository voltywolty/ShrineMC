package me.volt.main.mcevolved.managers;

import me.volt.main.mcevolved.MCEvolved;
import me.volt.main.mcevolved.gamemode.GameMode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CountdownBar {
    private MCEvolved plugin;
    private GameMode gameMode;

    private int seconds, minPlayers;
    private Countdown countdown;

    private class Countdown implements Runnable {
        private boolean running = true;

        public void run() {
            int remaining = CountdownBar.this.seconds;

            try {
                while (remaining > 0) {
                    if (!this.running)
                        return;

                    CountdownBar.this.broadcastTimeRemaining(remaining);
                    Thread.sleep(1000L);
                    remaining--;
                }
            }
            catch (Exception exception) {

            }

            if (!this.running)
                return;

            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) CountdownBar.this.plugin, new Runnable() {
                @Override
                public void run() {
                    CountdownBar.this.countdownComplete();
                }
            });
        }

        public void stop() {
            this.running = false;
        }
    }

    public CountdownBar(MCEvolved plugin, GameMode gamemode, int seconds, int minPlayers) {
        this.plugin = plugin;
        this.gameMode = gamemode;
        this.seconds = seconds;
        this.minPlayers = minPlayers;

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

    private void broadcastTimeRemaining(final int s) {
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) this.plugin, new Runnable() {
            @Override
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers())
                    players.sendTitle("" + ChatColor.BLUE + ChatColor.BLUE, "", 1, 15, 1);
            }
        });
    }

    private void countdownComplete() {
        if (Bukkit.getOnlinePlayers().size() >= this.minPlayers)
            this.gameMode.startGame();
        else {
            // Not deprecated, it's just PaperMC
            Bukkit.broadcastMessage("" + ChatColor.RED + "There are not enough players to start the game! The game will start when there are at least " + minPlayers + " players.");
            startTimer();
        }
    }
}
