package com.gildorymrp.unconsciousness;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {
	
	private GildorymUnconsciousness plugin;
	
	public PlayerDeathListener(GildorymUnconsciousness plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		plugin.setUnconscious(event.getEntity(), true);
		plugin.setDeathLocation(event.getEntity(), event.getEntity().getLocation());
		plugin.setDeathTime(event.getEntity());
		List<ItemStack> drops = new ArrayList<ItemStack>(event.getDrops());
		plugin.setDeathInventory(event.getEntity(), drops);
		event.getDrops().clear();
	}

}
