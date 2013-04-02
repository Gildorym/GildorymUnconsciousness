package com.gildorym.unconsciousness;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Unconsciousness extends JavaPlugin {
	
	public Map<String, Location> deathLocations = new HashMap<String, Location>();
	public Map<String, ItemStack[]> deathInventories = new HashMap<String, ItemStack[]>();
	
	public void onEnable() {
		this.registerListeners(new PlayerRespawnListener(this), new PlayerDeathListener(this));
	}
	
	private void registerListeners(Listener... listeners) {
		for (Listener listener : listeners) {
			this.getServer().getPluginManager().registerEvents(listener, this);
		}
	}

}
