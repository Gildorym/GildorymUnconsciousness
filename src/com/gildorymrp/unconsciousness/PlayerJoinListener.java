package com.gildorymrp.unconsciousness;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
	
	private GildorymUnconsciousness plugin;
	
	public PlayerJoinListener(GildorymUnconsciousness plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!plugin.isUnconscious(event.getPlayer())) {
			return;
		}
		if (System.currentTimeMillis() - plugin.getDeathTime(event.getPlayer()) >= 12000) {
			plugin.setUnconscious(event.getPlayer(), false);
		}
	}

}
