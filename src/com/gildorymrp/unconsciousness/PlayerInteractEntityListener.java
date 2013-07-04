package com.gildorymrp.unconsciousness;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener {
	
	private GildorymUnconsciousness plugin;
	
	public PlayerInteractEntityListener(GildorymUnconsciousness plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof Player) {
			if (plugin.isUnconscious((Player) event.getRightClicked())) {
				plugin.setUnconscious((Player) event.getRightClicked(), false);
			}
		}
	}

}
