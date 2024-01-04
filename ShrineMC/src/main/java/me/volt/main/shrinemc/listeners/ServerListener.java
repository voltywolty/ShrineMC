package me.volt.main.shrinemc.listeners;

import me.volt.main.shrinemc.ShrineMC;
import me.volt.main.shrinemc.managers.ItemManager;

import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ServerListener implements Listener {
    private final ShrineMC plugin;
    private final ItemManager itemManager;

    private static final Inventory hatInventory = Bukkit.createInventory(null, 9, Component.text("Select a Hat"));

    public ServerListener(ShrineMC plugin, ItemManager itemManager) {
        this.plugin = plugin;
        this.itemManager = itemManager;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("smc.admin"))
            itemManager.giveItem(player.getInventory(), "hat_selector", 1, plugin.configManager.getConfig());
    }

    @EventHandler
    private void onLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (event.getItem() == null)
            return;

        ItemMeta itemMeta = event.getItem().getItemMeta();

        if (event.getItem() != null && action.isLeftClick() || action.isRightClick())
            if (itemMeta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&bOpen Hat Menu")))
                player.openInventory(hatInventory);
    }

    @EventHandler
    private void onHatMenuClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();

        itemManager.giveItem(hatInventory, "santa_hat", 1, plugin.configManager.getConfig());
        ItemStack santaHat = itemManager.createItem("santa_hat", 1, plugin.configManager.getConfig());

        if (event.getView().getTitle().equals("Select a Hat") && event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
            if (clicked == null || !clicked.hasItemMeta())
                return;

            if (clicked.getType() == Material.WHITE_WOOL) {
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.5F);

                player.getInventory().setHelmet(santaHat);
                event.setCurrentItem(santaHat);
            }
        }
    }
}
