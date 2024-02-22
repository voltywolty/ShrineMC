package me.volt.main.shrinemc.managers;

import me.volt.main.shrinemc.ShrineMC;
import me.volt.main.shrinemc.gamemode.GameMode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CountdownBar {
    private final ShrineMC plugin;
    private final GameMode gameMode;

    private final int seconds, minPlayers;
    private Countdown countdown;

    private class Countdown implements Runnable {
        private boolean running = true;

        public void run() {
            int remaining = seconds;

            try {
                while (remaining > 0) {
                    if (!running)
                        return;

                    CountdownBar.this.broadcastTimeRemaining(remaining);
                    Thread.sleep(1000L);
                    remaining--;
                }
            }
            catch (Exception exception) {

            }

            if (!running)
                return;

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, CountdownBar.this::countdownComplete);
        }

        public void stop() {
            this.running = false;
        }
    }

    public CountdownBar(ShrineMC plugin, GameMode gamemode, int seconds, int minPlayers) {
        this.plugin = plugin;
        this.gameMode = gamemode;
        this.seconds = seconds;
        this.minPlayers = minPlayers;

        startTimer();
    }

    public void startTimer() {
        countdown = new Countdown();

        Thread thread = new Thread(countdown);
        thread.start();
    }

    public void stopTimer() {
        countdown.stop();
    }

    private void broadcastTimeRemaining(final int s) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendTitle(ChatColor.BLUE + String.valueOf(s), "", 1, 15, 1);

                if (s == 5)
                    players.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1, false, false));
                if (s == 0)
                    players.removePotionEffect(PotionEffectType.CONFUSION);
            }
        });
    }

    private void countdownComplete() {
        if (Bukkit.getOnlinePlayers().size() >= minPlayers)
            gameMode.startGame();
        else {
            Bukkit.broadcastMessage("" + ChatColor.RED + "There are not enough players to start the game! The game will start when there are at least " + minPlayers + " players.");
            startTimer();
        }
    }
}
