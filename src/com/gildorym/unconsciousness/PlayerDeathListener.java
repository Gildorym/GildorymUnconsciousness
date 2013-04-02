package com.gildorym.unconsciousness;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {
	
	private Unconsciousness plugin;
	
	public PlayerDeathListener(Unconsciousness plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		//plugin.deathLocations.put(event.getEntity().getName().substring(0, Math.min(14, event.getEntity().getName().length())), event.getEntity().getLocation().getBlock().getLocation());
		//plugin.deathInventories.put(event.getEntity().getName().substring(0, Math.min(14, event.getEntity().getName().length())), (ItemStack[]) event.getDrops().toArray(new ItemStack[event.getDrops().size()]));
		plugin.deathLocations.put(event.getEntity().getName(), event.getEntity().getLocation().getBlock().getLocation());
		plugin.deathInventories.put(event.getEntity().getName(), (ItemStack[]) event.getDrops().toArray(new ItemStack[event.getDrops().size()]));
		event.getDrops().clear();
	}

}
