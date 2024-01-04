package me.volt.main.shrinemc.managers;

import me.volt.main.shrinemc.ShrineMC;
import me.volt.main.shrinemc.commands.SubCommand;
import me.volt.main.shrinemc.commands.subcommands.GiveItemCommand;
import me.volt.main.shrinemc.commands.subcommands.HelpCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();
    private final ShrineMC plugin;

    public CommandManager(ShrineMC plugin) {
        this.plugin = plugin;

        subCommands.add(new HelpCommand(this));
        subCommands.add(new GiveItemCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName()))
                        getSubCommands().get(i).perform(player, args);
                }
            }
            else
                player.sendMessage(ChatColor.RED + "Invalid command. Please type " + ChatColor.GOLD + "/shrinemc help" + ChatColor.RED + " to see all commands!");
        }

        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
