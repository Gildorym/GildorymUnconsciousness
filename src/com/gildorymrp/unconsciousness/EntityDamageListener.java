package com.gildorymrp.unconsciousness;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
	
	private GildorymUnconsciousness plugin;
	
	public EntityDamageListener(GildorymUnconsciousness plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (plugin.isUnconscious(player)) {
				event.setCancelled(true);
			}
		}
	}

}
