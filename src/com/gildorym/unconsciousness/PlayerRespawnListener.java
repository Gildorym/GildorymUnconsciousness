package com.gildorym.unconsciousness;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
	
	private Unconsciousness plugin;
	
	public PlayerRespawnListener(Unconsciousness plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Location location = plugin.deathLocations.get(event.getPlayer().getName().substring(0, Math.min(14, event.getPlayer().getName().length())));
		if (location != null) {
			event.setRespawnLocation(new Location(plugin.getServer().getWorlds().get(0), -146, 63, 839));
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new RestoreItemRunnable(event.getPlayer(), plugin.deathInventories.get(event.getPlayer().getName().substring(0, Math.min(14, event.getPlayer().getName().length())))), 20L);
			location.getBlock().getRelative(BlockFace.UP).setType(Material.SIGN_POST);
			location.getBlock().setType(Material.BEDROCK);
			Sign sign = (Sign) location.getBlock().getRelative(BlockFace.UP).getState();
			sign.setLine(0, ChatColor.BLUE + "[Unconscious]");
			sign.setLine(1, event.getPlayer().getDisplayName().substring(0, Math.min(14, event.getPlayer().getDisplayName().length())));
			sign.setLine(2, event.getPlayer().getName().substring(0, Math.min(14, event.getPlayer().getName().length())));
			sign.update();
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new WakeUpRunnable(event.getPlayer(), location), 12000L);
		}
	}

}
