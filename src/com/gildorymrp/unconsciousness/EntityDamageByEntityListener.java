package com.gildorymrp.unconsciousness;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
	
	private GildorymUnconsciousness plugin;
	
	public EntityDamageByEntityListener(GildorymUnconsciousness plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (plugin.isUnconscious(player)) {
				event.setCancelled(true);
			}
		}
	}

}
