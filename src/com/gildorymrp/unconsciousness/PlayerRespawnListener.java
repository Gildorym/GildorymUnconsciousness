package com.gildorymrp.unconsciousness;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
	
	private GildorymUnconsciousness plugin;
	
	public PlayerRespawnListener(GildorymUnconsciousness plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		event.setRespawnLocation(plugin.getDeathLocation(event.getPlayer()));
	}

}
