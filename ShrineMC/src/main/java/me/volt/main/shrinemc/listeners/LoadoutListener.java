package me.volt.main.shrinemc.listeners;

import me.volt.main.shrinemc.ShrineMC;
import me.volt.main.shrinemc.managers.ItemManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class LoadoutListener implements Listener {
    private final ShrineMC plugin;
    private final ItemManager itemManager;

    private final ItemStack loadoutSelector;

    public LoadoutListener(ShrineMC plugin, ItemManager itemManager) {
        this.plugin = plugin;
        this.itemManager = itemManager;

        loadoutSelector = plugin.getItemManager().createItem("loadout_selector", 1, plugin.getConfigManager().getConfig());
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.getInventory().setItem(0, loadoutSelector);
    }

    @EventHandler
    private void onLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (event.getItem() == null)
            return;

        if (event.getItem() != null && action.isLeftClick() || action.isRightClick()) {
            if (player.getInventory().getItemInMainHand().isSimilar(loadoutSelector)) {
                Inventory loadoutInventory = createLoadoutInventory(player);
                player.openInventory(loadoutInventory);
            }
        }
    }

    private Inventory createLoadoutInventory(Player player) {
        Inventory loadoutInventory = Bukkit.createInventory(player, 54, Component.text("Create a Loadout"));

        return loadoutInventory;
    }
}
