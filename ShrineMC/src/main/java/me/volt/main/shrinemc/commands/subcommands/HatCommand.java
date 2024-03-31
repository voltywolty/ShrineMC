package me.volt.main.shrinemc.commands.subcommands;

import me.volt.main.shrinemc.commands.SubCommand;

import me.volt.main.shrinemc.listeners.HatListener;
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
        Inventory hatInventory = HatListener.createHatInventory(player);
        player.openInventory(hatInventory);
    }

    @Override
    public boolean isAdminCommand() {
        return false;
    }
}
