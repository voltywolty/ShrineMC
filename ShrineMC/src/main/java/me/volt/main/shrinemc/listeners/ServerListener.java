package me.volt.main.shrinemc.listeners;

import me.volt.main.shrinemc.ShrineMC;
import me.volt.main.shrinemc.managers.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ServerListener implements Listener {
    private final ShrineMC plugin;
    private final ItemManager itemManager;

    public ServerListener(ShrineMC plugin, ItemManager itemManager) {
        this.plugin = plugin;
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("smc.testing"))
            itemManager.giveItem(player, "hatSelector");
    }
}
