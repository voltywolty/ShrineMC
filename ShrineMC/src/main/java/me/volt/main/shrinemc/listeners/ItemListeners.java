package me.volt.main.shrinemc.listeners;

import me.volt.main.shrinemc.managers.ItemManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ItemListeners implements Listener {

    private final ItemManager itemManager;

    public ItemListeners(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        itemManager.giveItem(event.getPlayer(), "hatSelector");
    }
}
