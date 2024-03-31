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

    private final ItemStack loadoutSelector;

    public LoadoutListener() {
        loadoutSelector = ShrineMC.getItemManager().createItem("loadout_selector", 1, ShrineMC.getConfigManager().getConfig());
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("smc.testing") || player.hasPermission("smc.admin"))
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ShrineMC.getInstance(), () -> player.getInventory().setItem(0, loadoutSelector), 20L);
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

    @EventHandler
    private void onLoadoutMenuClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();

        if (event.getView().getTitle().equals("Select a Loadout") && event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
            if (clicked == null || !clicked.hasItemMeta())
                return;
        }
    }

    private Inventory createLoadoutInventory(Player player) {
        Inventory loadoutInventory = Bukkit.createInventory(player, 54, Component.text("Create a Loadout"));

        // Torch upgrades
        loadoutInventory.setItem(0, createLoadoutItem("torchbearer1", 1));
        loadoutInventory.setItem(9, createLoadoutItem("torchbearer2", 2));
        loadoutInventory.setItem(18, createLoadoutItem("torchbearer3", 3));

        // Mortar upgrades
        loadoutInventory.setItem(1, createLoadoutItem("bricklayer1", 1));
        loadoutInventory.setItem(10, createLoadoutItem("bricklayer2", 2));
        loadoutInventory.setItem(19, createLoadoutItem("bricklayer3", 3));

        // Stone mason upgrades
        loadoutInventory.setItem(2, createLoadoutItem("stonemason1", 1));
        loadoutInventory.setItem(11, createLoadoutItem("stonemason2", 2));
        loadoutInventory.setItem(20, createLoadoutItem("stonemason3", 3));

        // Wiggly wrench upgrade
        loadoutInventory.setItem(3, createLoadoutItem("wigglywrench1", 1));
        loadoutInventory.setItem(12, createLoadoutItem("wigglywrench2", 2));
        loadoutInventory.setItem(21, createLoadoutItem("wigglywrench3", 3));

        // Bottom slots
        loadoutInventory.setItem(45, createLoadoutItem("points_remaining", 64));
        loadoutInventory.setItem(48, createLoadoutItem("previous_page", 1));
        loadoutInventory.setItem(49, createLoadoutItem("reset_loadout", 1));
        loadoutInventory.setItem(50, createLoadoutItem("next_page", 1));

        return loadoutInventory;
    }

    private ItemStack createLoadoutItem(String itemName, int amount) {
        return ShrineMC.getItemManager().createItem(itemName, amount, ShrineMC.getConfigManager().getConfig());
    }
}
