package me.volt.main.shrinemc.managers;

import me.volt.main.shrinemc.ShrineMC;
import me.volt.main.shrinemc.commands.SubCommand;
import me.volt.main.shrinemc.commands.subcommands.GiveItemCommand;
import me.volt.main.shrinemc.commands.subcommands.HatCommand;
import me.volt.main.shrinemc.commands.subcommands.HelpCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new HelpCommand(this));
        subCommands.add(new GiveItemCommand());
        subCommands.add(new HatCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    SubCommand subCommand = getSubCommands().get(i);
                    if (args[0].equalsIgnoreCase(subCommand.getName())) {
                        // Check if the subcommand is an admin command
                        if (subCommand.isAdminCommand() && !player.hasPermission("smc.admin")) {
                            player.sendMessage(ChatColor.RED + "You do not have permission to use that command.");
                            return true;
                        }

                        subCommand.perform(player, args);
                        return true;
                    }
                }
            }

            // If no matching subcommand, display invalid command message
            player.sendMessage(ChatColor.RED + "Invalid command. Please type " + ChatColor.GOLD + "/smc help" + ChatColor.RED + " to see all commands!");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            return subCommands.stream()
                    .map(SubCommand::getName)
                    .filter(name -> name.toLowerCase().startsWith(input))
                    .collect(Collectors.toList());
        }
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give")) {
                return Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give")) {
                List<String> items = new ArrayList<>();
                items.addAll(ShrineMC.getConfigManager().getConfig().getConfigurationSection("custom-items").getKeys(false).stream()
                        .collect(Collectors.toList()));
                return items.stream()
                        .filter(item -> item.toLowerCase().startsWith(args[2].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        else if (args.length == 4 && args[0].equalsIgnoreCase("give"))
            return Collections.singletonList("<quantity>");

        return Collections.emptyList();
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
