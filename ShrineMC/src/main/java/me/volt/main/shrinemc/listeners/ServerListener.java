package me.volt.main.shrinemc.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.volt.main.shrinemc.ShrineMC;
import me.volt.main.shrinemc.managers.ItemManager;
import me.volt.main.shrinemc.managers.ServerManager;

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

import java.util.ArrayList;

public class ServerListener implements Listener {
    private final ShrineMC plugin;
    private final ItemManager itemManager;

    private static final Inventory hatInventory = Bukkit.createInventory(null, 9, Component.text("Select a Hat"));

    public ServerListener(ShrineMC plugin, ItemManager itemManager) {
        this.plugin = plugin;
        this.itemManager = itemManager;
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

        itemManager.giveItem(hatInventory, "santa_hat", 1, plugin.getConfigManager().getConfig());
        ItemStack santaHat = itemManager.createItem("santa_hat", 1, plugin.getConfigManager().getConfig());

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

    @EventHandler
    public void openGameList(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack gameList = plugin.getItemManager().createItem("game_list", 1, plugin.getConfigManager().getConfig());

        if (player.getInventory().getItemInMainHand().isSimilar(gameList))
            openGameListInventory(player);
    }

    // Make it more efficient?
    private void openGameListInventory(Player player) {
        Inventory gameListInv = Bukkit.createInventory(null, 9, "Game List");

        ItemStack game = new ItemStack(Material.RED_CONCRETE);
        game.setDurability((short) 9);
        ItemMeta gameMeta = game.getItemMeta();
        gameMeta.setDisplayName(ServerManager.lobbyName);

        ArrayList<String> gameLore = new ArrayList<>();
        gameLore.add(ChatColor.YELLOW + "Players: " + ChatColor.GOLD + ServerManager.online);
        gameLore.add(ChatColor.WHITE + ServerManager.serverStatus);
        gameMeta.setLore(gameLore);

        game.setItemMeta(gameMeta);

        if (plugin.getServerManager().isServerOnline())
            gameListInv.setItem(0, game);

        player.openInventory(gameListInv);
    }



    @EventHandler
    public void onGameListClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView().title().toString().contains("Game List") && event.getCurrentItem() != null && event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("dvz");

            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
            player.closeInventory();

            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }
}
