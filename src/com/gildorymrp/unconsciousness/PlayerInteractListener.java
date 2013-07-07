package com.gildorymrp.unconsciousness;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
	
	private GildorymUnconsciousness plugin;
	
	public PlayerInteractListener(GildorymUnconsciousness plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (plugin.isUnconscious(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

}
