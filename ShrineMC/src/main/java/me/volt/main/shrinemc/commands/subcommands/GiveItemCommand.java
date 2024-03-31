package me.volt.main.shrinemc.commands.subcommands;

import me.volt.main.shrinemc.ShrineMC;
import me.volt.main.shrinemc.commands.SubCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GiveItemCommand extends SubCommand {
    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getDescription() {
        return "Gives a custom item to a player.";
    }

    @Override
    public String getSyntax() {
        return "/shrinemc give [player] [item] [quantity]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 3) {
            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);

            if (target == null || !target.isOnline()) {
                player.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "ShrineMC" + ChatColor.GOLD + "] " + ChatColor.WHITE + "Invalid player name.");
                return;
            }

            // Converts to lowercase for case-insensitive comparison.
            String itemName = args[2].toLowerCase();
            int quantity = 1;  // Default quantity to 1 if not provided

            if (args.length > 4) {
                try {
                    quantity = Integer.parseInt(args[3]);
                }
                catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid quantity. Please provide a valid number.");
                    return;
                }
            }

            ShrineMC.getItemManager().giveItem(target.getInventory(), itemName, quantity, ShrineMC.getConfigManager().getConfig());
            player.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "ShrineMC" + ChatColor.GOLD + "] " + ChatColor.WHITE + "Gave " + quantity + " " + itemName + " to " + target.getName());
        }
        else
            player.sendMessage(ChatColor.RED + "Usage: " + getSyntax());
    }


    @Override
    public boolean isAdminCommand() {
        return true;
    }
}
