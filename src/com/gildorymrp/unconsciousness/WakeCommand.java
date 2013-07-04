package com.gildorymrp.unconsciousness;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WakeCommand implements CommandExecutor {

	private GildorymUnconsciousness plugin;
	
	public WakeCommand(GildorymUnconsciousness plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("gildorym.unconsciousness.command.wake")) {
			Player player = null;
			if (sender instanceof Player) {
				player = (Player) sender;
			}
			if (args.length > 0) {
				if (plugin.getServer().getPlayer(args[0]) != null) {
					player = Bukkit.getServer().getPlayer(args[0]);
				} else {
					sender.sendMessage(GildorymUnconsciousness.PREFIX + "Could not find a player by that name, defaulting to self.");
				}
			}
			if (player != null) {
				plugin.setUnconscious(player, false);
				sender.sendMessage(GildorymUnconsciousness.PREFIX + "Forcefully woke " + player.getDisplayName());
			} else {
				sender.sendMessage(GildorymUnconsciousness.PREFIX + "You must specify a player to wake");
			}
		}
		return true;
	}

}
