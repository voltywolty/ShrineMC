package me.volt.main.shrinemc.commands.subcommands;

import me.volt.main.shrinemc.commands.SubCommand;
import me.volt.main.shrinemc.managers.ItemManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GiveItemCommand extends SubCommand {
    private final ItemManager itemManager;

    public GiveItemCommand(ItemManager itemManager) {
        this.itemManager = itemManager;
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
        if (player.hasPermission("smc.testing")) {
            if (args.length > 1) {
                // NOTE - Converts to lowercase for case-insensitive comparison.
                String itemName = args[1].toLowerCase();

                if (itemName.equals("santahat")) {
                    itemManager.giveItem(player, "santaHat");
                    player.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "ShrineMC" + ChatColor.GOLD + "] " + ChatColor.WHITE + "You received the Santa Hat!");
                }
                else
                    player.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "ShrineMC" + ChatColor.GOLD + "] " + ChatColor.WHITE + "Did not provide a valid item. Usage: /shrinemc giveitem [item]");
            }
            else
                player.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "ShrineMC" + ChatColor.GOLD + "] " + ChatColor.WHITE + "Usage: " + getSyntax());
        }
        else
            player.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "ShrineMC" + ChatColor.GOLD + "] " + ChatColor.WHITE + "You do not have permission to use that command.");
    }
}
