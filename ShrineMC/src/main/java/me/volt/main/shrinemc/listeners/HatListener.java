package me.volt.main.shrinemc.listeners;

import me.volt.main.shrinemc.ShrineMC;
import me.volt.main.shrinemc.managers.ItemManager;

import net.kyori.adventure.text.Component;

import org.bukkit.*;
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
public class HatListener implements Listener {
    private final ItemStack hatSelector;
    private final ItemStack pharaohHat, gogglesHat, warriorCap, dragonsBreathHat, wolfHunterHat, jimmyCap, dwarvenBeard, santaHat;

    public HatListener() {
        hatSelector = ShrineMC.getItemManager().createItem("hat_selector", 1, ShrineMC.getConfigManager().getConfig());

        pharaohHat = ShrineMC.getItemManager().createItem("pharaoh_hat", 1, ShrineMC.getConfigManager().getConfig());
        gogglesHat = ShrineMC.getItemManager().createItem("goggles_hat", 1, ShrineMC.getConfigManager().getConfig());
        warriorCap = ShrineMC.getItemManager().createItem("warrior_hat", 1, ShrineMC.getConfigManager().getConfig());
        dragonsBreathHat = ShrineMC.getItemManager().createItem("dragons_breath_hat", 1, ShrineMC.getConfigManager().getConfig());
        wolfHunterHat = ShrineMC.getItemManager().createItem("wolf_hunter_hat", 1, ShrineMC.getConfigManager().getConfig());
        jimmyCap = ShrineMC.getItemManager().createItem("jimmy_hat", 1, ShrineMC.getConfigManager().getConfig());
        dwarvenBeard = ShrineMC.getItemManager().createItem("dwarven_beard_hat", 1, ShrineMC.getConfigManager().getConfig());
        santaHat = ShrineMC.getItemManager().createItem("santa_hat", 1, ShrineMC.getConfigManager().getConfig());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ShrineMC.getInstance(), () -> player.getInventory().setItem(0, hatSelector), 20L); // Change to 1 when done with loadout upgrades
    }

    @EventHandler
    private void onLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (event.getItem() == null)
            return;

        if (action.isRightClick()) {
            ItemStack itemInHand = player.getInventory().getItemInMainHand();

            if (itemInHand.getType() == Material.DIAMOND_HELMET)
                event.setCancelled(true);
        }
        else if (action.isLeftClick()) {
            if (player.getInventory().getItemInMainHand().isSimilar(hatSelector)) {
                Inventory hatInventory = ShrineMC.getItemManager().createHatInventory(player);
                player.openInventory(hatInventory);
            }
        }
    }

    @EventHandler
    private void onHatMenuClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ItemStack clicked = event.getCurrentItem();

        if (event.getView().getTitle().equals("Select a Hat") && event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
            if (clicked == null || !clicked.hasItemMeta())
                return;

            if (clicked.getType() == Material.PURPLE_WOOL) {
                if (clicked.hasItemMeta() && clicked.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(ShrineMC.getInstance(), "hat_equipped"), PersistentDataType.BYTE)) {
                    player.getInventory().setHelmet(null);
                    event.setCurrentItem(wolfHunterHat.clone());

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
                }
                else {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1F);

                    ItemStack item = clicked.clone();
                    hatEquipped(item);

                    player.getInventory().setHelmet(wolfHunterHat);
                    event.setCurrentItem(item);
                }
            }
            else if (clicked.getType() == Material.WHITE_WOOL) {
                if (clicked.hasItemMeta() && clicked.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(ShrineMC.getInstance(), "hat_equipped"), PersistentDataType.BYTE)) {
                    player.getInventory().setHelmet(null);
                    event.setCurrentItem(santaHat.clone());

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
                }
                else {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1F);

                    ItemStack item = clicked.clone();
                    hatEquipped(item);

                    player.getInventory().setHelmet(santaHat);
                    event.setCurrentItem(item);
                }
            }
            else if (clicked.getType() == Material.GRAY_WOOL) {
                if (clicked.hasItemMeta() && clicked.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(ShrineMC.getInstance(), "hat_equipped"), PersistentDataType.BYTE)) {
                    player.getInventory().setHelmet(null);
                    event.setCurrentItem(gogglesHat.clone());

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
                }
                else {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1F);

                    ItemStack item = clicked.clone();
                    hatEquipped(item);

                    player.getInventory().setHelmet(gogglesHat);
                    event.setCurrentItem(item);
                }
            }
            else if (clicked.getType() == Material.LIGHT_GRAY_WOOL) {
                if (clicked.hasItemMeta() && clicked.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(ShrineMC.getInstance(), "hat_equipped"), PersistentDataType.BYTE)) {
                    player.getInventory().setHelmet(null);
                    event.setCurrentItem(warriorCap.clone());

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
                }
                else {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1F);

                    ItemStack item = clicked.clone();
                    hatEquipped(item);

                    player.getInventory().setHelmet(warriorCap);
                    event.setCurrentItem(item);
                }
            }
            else if (clicked.getType() == Material.RED_WOOL) {
                if (clicked.hasItemMeta() && clicked.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(ShrineMC.getInstance(), "hat_equipped"), PersistentDataType.BYTE)) {
                    player.getInventory().setHelmet(null);
                    event.setCurrentItem(dragonsBreathHat.clone());

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
                }
                else {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1F);

                    ItemStack item = clicked.clone();
                    hatEquipped(item);

                    player.getInventory().setHelmet(dragonsBreathHat);
                    event.setCurrentItem(item);
                }
            }
            else if (clicked.getType() == Material.BROWN_WOOL) {
                if (clicked.hasItemMeta() && clicked.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(ShrineMC.getInstance(), "hat_equipped"), PersistentDataType.BYTE)) {
                    player.getInventory().setHelmet(null);
                    event.setCurrentItem(pharaohHat.clone());

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
                }
                else {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1F);

                    ItemStack item = clicked.clone();
                    hatEquipped(item);

                    player.getInventory().setHelmet(pharaohHat);
                    event.setCurrentItem(item);
                }
            }
            else if (clicked.getType() == Material.DIAMOND_HELMET) {
                if (clicked.hasItemMeta() && clicked.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(ShrineMC.getInstance(), "hat_equipped"), PersistentDataType.BYTE)) {
                    player.getInventory().setHelmet(null);
                    event.setCurrentItem(jimmyCap.clone());

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
                }
                else {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1F);

                    ItemStack item = clicked.clone();
                    hatEquipped(item);

                    player.getInventory().setHelmet(jimmyCap);
                    event.setCurrentItem(item);
                }
            }
            else if (clicked.getType() == Material.YELLOW_STAINED_GLASS) {
                if (clicked.hasItemMeta() && clicked.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(ShrineMC.getInstance(), "hat_equipped"), PersistentDataType.BYTE)) {
                    player.getInventory().setHelmet(null);
                    event.setCurrentItem(dwarvenBeard.clone());

                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.3F);
                }
                else {
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1F);

                    ItemStack item = clicked.clone();
                    hatEquipped(item);

                    player.getInventory().setHelmet(dwarvenBeard);
                    event.setCurrentItem(item);
                }
            }

            event.setCancelled(true);
        }
    }

    private void hatEquipped(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.getPersistentDataContainer().set(new NamespacedKey(ShrineMC.getInstance(), "hat_equipped"), PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
    }
}
