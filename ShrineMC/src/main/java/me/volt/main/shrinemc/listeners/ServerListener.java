package me.volt.main.shrinemc.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.volt.main.shrinemc.ShrineMC;
import me.volt.main.shrinemc.managers.ServerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ServerListener implements Listener {
    @EventHandler
    public void openGameList(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack gameList = ShrineMC.getItemManager().createItem("game_list", 1, ShrineMC.getConfigManager().getConfig());

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

        if (ShrineMC.getServerManager().isServerOnline())
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

            player.sendPluginMessage(ShrineMC.getInstance(), "BungeeCord", out.toByteArray());
            player.closeInventory();

            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }
}
