package me.volt.main.shrinemc.commands.subcommands;

import me.volt.main.shrinemc.ShrineMC;
import me.volt.main.shrinemc.commands.SubCommand;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GiveItemCommand extends SubCommand {
    private final ShrineMC plugin;

    public GiveItemCommand(ShrineMC plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "giveitem";
    }

    @Override
    public String getDescription() {
        return "Gives a custom item to the player.";
    }

    @Override
    public String getSyntax() {
        return "/shrinemc giveitem [item]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 1) {
            // NOTE - Converts to lowercase for case-insensitive comparison.
            String itemName = args[1].toLowerCase();

            if (itemName != null) {
                plugin.itemManager.giveItem(player.getInventory(), itemName, 1, plugin.configManager.getConfig());
                player.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "ShrineMC" + ChatColor.GOLD + "] " + ChatColor.WHITE + "You received " + itemName + "!");
            }
            else
                player.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "ShrineMC" + ChatColor.GOLD + "] " + ChatColor.WHITE + "Did not provide a valid item. Usage: /shrinemc giveitem [item]");
        }
        else
            player.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "ShrineMC" + ChatColor.GOLD + "] " + ChatColor.WHITE + "Usage: " + getSyntax());
    }
}
