package me.volt.main.shrinemc.managers;

import me.volt.main.shrinemc.ShrineMC;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemManager {
    public ItemStack createItem(String itemName, int amount, FileConfiguration config) {
        if (config.contains("custom-items." + itemName)) {
            ConfigurationSection itemConfig = config.getConfigurationSection("custom-items." + itemName);

            Material type = Material.matchMaterial(itemConfig.getString("type", "").toUpperCase());
            if (type == null) {
                ShrineMC.getInstance().getLogger().warning("Invalid material type for item: " + itemName);
                return null;
            }

            ItemStack customItem;
            ItemMeta meta;

            if (type == Material.POTION) {
                customItem = new ItemStack(type, amount);
                meta = customItem.getItemMeta();

                if (meta instanceof PotionMeta) {
                    PotionMeta potionMeta = (PotionMeta) meta;

                    // NOTE - Sets potion data
                    PotionData potionData = new PotionData(PotionType.valueOf(itemConfig.getString("potion-type", "WATER").toUpperCase()));
                    potionMeta.setBasePotionData(potionData);

                    // NOTE - Set custom color
                    if (itemConfig.contains("potion-color")) {
                        String hexColor = itemConfig.getString("potion-color", "").replace("#", "").toUpperCase(Locale.ROOT);
                        try {
                            int colorValue = Integer.parseInt(hexColor, 16);
                            Color potionColor = Color.fromRGB(colorValue);
                            potionMeta.setColor(potionColor);
                        }
                        catch (NumberFormatException e) {
                            ShrineMC.getInstance().getLogger().warning("Invalid hex color format for potion in config.");
                        }
                    }

                    meta = potionMeta;
                }
            }
            else {
                customItem = new ItemStack(type, amount);
                meta = customItem.getItemMeta();
            }

            if (meta != null) {
                String name = ChatColor.translateAlternateColorCodes('&', itemConfig.getString("name", ""));
                List<String> lore = itemConfig.getStringList("lore");
                List<String> translatedLore = lore.stream()
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                        .collect(Collectors.toList());

                boolean unbreakable = itemConfig.getBoolean("unbreakable", false);

                if (itemConfig.contains("durability")) {
                    int durability = itemConfig.getInt("durability", -1);
                    if (durability >= 0) {
                        int maxDurability = type.getMaxDurability();
                        int damage = maxDurability - durability; // Calculate damage based on remaining durability

                        if (meta instanceof Damageable) {
                            Damageable damageableMeta = (Damageable) meta;
                            damageableMeta.setDamage(damage);
                        }

                    }
                }

                meta.setDisplayName(name);
                meta.setLore(translatedLore);
                meta.setUnbreakable(unbreakable);

                applyAttributes(meta, itemConfig);
                applyEnchants(meta, itemConfig);

                if (itemConfig.getBoolean("hide-flags", false))
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ITEM_SPECIFICS, ItemFlag.HIDE_ENCHANTS);

                if (itemConfig.getBoolean("fake-glint", false))
                    meta.addEnchant(Enchantment.DURABILITY, 1, true);

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

    private void applyAttributes(ItemMeta meta, ConfigurationSection itemConfig) {
        if (itemConfig.contains("attributes")) {
            List<Map<?, ?>> attributesList = itemConfig.getMapList("attributes");

            for (Map<?, ?> attributeMap : attributesList) {
                try {
                    for (Map.Entry<?, ?> entry : attributeMap.entrySet()) {
                        String attributeName = entry.getKey().toString();
                        double amount = Double.parseDouble(entry.getValue().toString());

                        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), attributeName, amount, AttributeModifier.Operation.ADD_NUMBER);
                        meta.addAttributeModifier(Attribute.valueOf(attributeName), modifier);
                    }
                }
                catch (IllegalArgumentException | ClassCastException e) {
                    ShrineMC.getInstance().getLogger().warning("Invalid attribute configuration in the list.");
                }
            }
        }
    }

    private void applyEnchants(ItemMeta meta, ConfigurationSection itemConfig) {
        if (itemConfig.contains("enchants")) {
            List<String> enchantsList = itemConfig.getStringList("enchants");

            for (String enchantString : enchantsList) {
                String[] parts = enchantString.split(" ");

                if (parts.length == 2) {
                    String enchantName = parts[0].toLowerCase();
                    int level = Integer.parseInt(parts[1]);

                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantName));

                    if (enchantment != null) {
                        meta.addEnchant(enchantment, level, true);

                        // Check if hide-flags is enabled, and if not, show enchantments in lore
                        if (!itemConfig.getBoolean("hide-flags", false))
                            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                    else
                        ShrineMC.getInstance().getLogger().warning("Invalid enchantment key: " + enchantName);
                }
                else
                    ShrineMC.getInstance().getLogger().warning("Invalid enchantment format: " + enchantString);
            }
        }
    }
}
