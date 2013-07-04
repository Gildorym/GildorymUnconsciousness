package com.gildorymrp.unconsciousness;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
	
	private GildorymUnconsciousness plugin;
	
	public PlayerDeathListener(GildorymUnconsciousness plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		plugin.setUnconscious(event.getEntity(), true);
		plugin.setDeathLocation(event.getEntity(), event.getEntity().getLocation());
	}

}
