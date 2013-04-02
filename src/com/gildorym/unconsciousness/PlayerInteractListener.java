package com.gildorym.unconsciousness;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
	
	private Unconsciousness plugin;
	
	public PlayerInteractListener(Unconsciousness plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) event.getClickedBlock().getState();
				if (sign.getLine(0).equals(ChatColor.BLUE + "[Unconscious]")) {
					if (plugin.getServer().getPlayer(sign.getLine(2)) != null) {
						event.getPlayer().openInventory(plugin.getServer().getPlayer(sign.getLine(2)).getInventory());
					}
				}
			}
		}
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (event.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) event.getClickedBlock().getState();
				if (sign.getLine(0).equals(ChatColor.BLUE + "[Unconscious]")) {
					if (plugin.getServer().getPlayer(sign.getLine(2)) != null) {
							plugin.getServer().getPlayer(sign.getLine(2)).teleport(sign.getBlock().getLocation());
					}
					sign.getBlock().setType(Material.AIR);
					sign.getBlock().getRelative(BlockFace.DOWN).setType(Material.AIR);
				}
			}
		}
	}

}
