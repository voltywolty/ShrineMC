package me.volt.main.shrinemc.managers;

import me.volt.main.shrinemc.ShrineMC;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class ItemManager {
    private final ShrineMC plugin;

    public ItemManager(ShrineMC plugin) {
        this.plugin = plugin;
    }

    public ItemStack createItem(String itemName, int amount, FileConfiguration config) {
        if (config.contains("custom-items." + itemName)) {
            ConfigurationSection itemConfig = config.getConfigurationSection("custom-items." + itemName);

            Material type = Material.matchMaterial(itemConfig.getString("type", "").toUpperCase());
            if (type == null) {
                plugin.getLogger().warning("Invalid material type for item: " + itemName);
                return null;
            }

            String name = ChatColor.translateAlternateColorCodes('&', itemConfig.getString("name", ""));
            //int amount = itemConfig.getInt("amount", 1);
            List<String> lore = itemConfig.getStringList("lore");

            boolean unbreakable = itemConfig.getBoolean("unbreakable", false);

            ItemStack customItem = new ItemStack(type, amount);
            ItemMeta meta = customItem.getItemMeta();

            if (meta != null) {

                meta.setDisplayName(name);
                meta.setLore(lore);
                meta.setUnbreakable(unbreakable);

                applyAttributes(meta, itemConfig);

                if (itemConfig.getBoolean("hide-flags", false)) {
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
                }
                customItem.setItemMeta(meta);

                return customItem;
            }
        }
        return null;
    }

    public void giveItem(Inventory inventory, String itemName, int amount, FileConfiguration config) {
        ItemStack customItem = createItem(itemName, amount, config);

        if (customItem != null)
            inventory.addItem(customItem);
    }

    private void applyAttributes(ItemMeta meta, ConfigurationSection config) {
        if (config.contains("attributes")) {
            List<String> attributesList = config.getStringList("attributes");

            for (String attributeString : attributesList) {
                String[] parts = attributeString.split(":");
                if (parts.length == 2) {
                    String attributeName = parts[0].toUpperCase().trim();
                    double attributeValue = Double.parseDouble(parts[1].trim());

                    Attribute attribute = Attribute.valueOf(attributeName);
                    AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), attributeName, attributeValue, AttributeModifier.Operation.ADD_NUMBER);

                    meta.addAttributeModifier(attribute, modifier);
                }
                else
                    plugin.getLogger().warning("Invalid attribute format in config.");
            }
        }
    }
}
