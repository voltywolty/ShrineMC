package me.volt.main.shrinemc.commands.subcommands;

import me.volt.main.shrinemc.ShrineMC;
import me.volt.main.shrinemc.commands.SubCommand;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class HatCommand extends SubCommand {

    @Override
    public String getName() {
        return "hats";
    }

    @Override
    public String getDescription() {
        return "Equip a hat of your choice.";
    }

    @Override
    public String getSyntax() {
        return "/smc hats";
    }

    @Override
    public void perform(Player player, String[] args) {
        Inventory hatInventory = ShrineMC.getItemManager().createHatInventory(player);
        player.openInventory(hatInventory);
    }

    @Override
    public boolean isAdminCommand() {
        return false;
    }
}
