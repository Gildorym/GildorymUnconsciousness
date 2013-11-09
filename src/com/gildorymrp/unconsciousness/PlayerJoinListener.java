package com.gildorymrp.unconsciousness;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoinListener implements Listener {
	
	private GildorymUnconsciousness plugin;
	
	public PlayerJoinListener(GildorymUnconsciousness plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (plugin.isUnconscious(event.getPlayer())) {
			if (System.currentTimeMillis() - plugin.getDeathTime(event.getPlayer()) >= 600000L) { //600000ms = 10min
				plugin.setUnconscious(event.getPlayer(), false);
			}else{
				
			}
	}

}
