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
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ShrineMC.getInstance(), () -> player.getInventory().setItem(9, loadoutSelector), 20L); // Change slot back to 0 when done
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
