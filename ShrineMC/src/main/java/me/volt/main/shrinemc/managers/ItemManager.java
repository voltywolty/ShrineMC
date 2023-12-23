package me.volt.main.shrinemc.managers;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemManager {
    private final FileConfiguration config;

    public ItemManager(FileConfiguration config) {
        this.config = config;
    }

    public void giveItem(Player player, String itemName) {
        if (config.contains("customItems." + itemName)){
            String name = config.getString("customItems." + itemName + ".name");
            Material type = Material.valueOf(config.getString("customItems." + itemName + ".type"));

            int amount = config.getInt("customItems." + itemName + ".amount", 1);
            List<String> lore = config.getStringList("customItems." + itemName + ".lore");

            ItemStack customItem = new ItemStack(type, amount);
            ItemMeta meta = customItem.getItemMeta();

            meta.setDisplayName(name);
            meta.setLore(lore);

            customItem.setItemMeta(meta);

            player.getInventory().addItem(customItem);
        }
    }
}
