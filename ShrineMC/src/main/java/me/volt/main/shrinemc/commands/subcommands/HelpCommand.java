package me.volt.main.shrinemc.commands.subcommands;

import me.volt.main.shrinemc.commands.SubCommand;
import me.volt.main.shrinemc.managers.CommandManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Lists commands for DvZ.";
    }

    @Override
    public String getSyntax() {
        return "/shrinemc help";
    }

    @Override
    public void perform(Player player, String[] args) {
        for (int i = 0; i < commandManager.getSubCommands().size(); i++)
           player.sendMessage(ChatColor.GOLD + commandManager.getSubCommands().get(i).getSyntax() + " - " + ChatColor.RED + commandManager.getSubCommands().get(i).getDescription());
    }

    @Override
    public boolean isAdminCommand() {
        return false;
    }
}
