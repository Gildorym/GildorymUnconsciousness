package com.gildorymrp.unconsciousness;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import noppes.mpm.MorePlayerModels;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.gildorymrp.api.plugin.death.GildorymDeathPlugin;

public class GildorymUnconsciousness extends JavaPlugin implements GildorymDeathPlugin {
	
	public static final String PREFIX = "" + ChatColor.DARK_BLUE + ChatColor.MAGIC + "|" + ChatColor.RESET + ChatColor.DARK_PURPLE + "GildorymUnconsciousness" + ChatColor.DARK_BLUE + ChatColor.MAGIC + "| " + ChatColor.RESET;
	private File deathLocationsFile;
	private YamlConfiguration deathLocations;
	private File deathTimesFile;
	private YamlConfiguration deathTimes;
	private Set<String> unconscious;
	private MorePlayerModels mpm;
	
	@Override
	public void onEnable() {
		this.mpm = (MorePlayerModels) this.getServer().getPluginManager().getPlugin("MorePlayerModels");
		this.deathLocationsFile = new File(this.getDataFolder().getPath() + File.separator + "death-locations.yml");
		this.deathLocations = new YamlConfiguration();
		this.deathTimesFile = new File(this.getDataFolder().getPath() + File.separator + "death-times.yml");
		this.unconscious = new HashSet<String>();
		if (!this.getDataFolder().exists()) {
			this.getDataFolder().mkdir();
		}
		if (!deathLocationsFile.exists()) {
			try {
				deathLocationsFile.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		if (!deathTimesFile.exists()) {
			try {
				deathTimesFile.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		try {
			deathLocations.load(deathLocationsFile);
			deathTimes.load(deathTimesFile);
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		} catch (InvalidConfigurationException exception) {
			exception.printStackTrace();
		}
		this.registerListeners(new PlayerDeathListener(this),
				new PlayerRespawnListener(this),
				new PlayerMoveListener(this),
				new PlayerInteractEntityListener(this),
				new EntityDamageListener(this),
				new PlayerJoinListener(this));
		this.getCommand("wake").setExecutor(new WakeCommand(this));
	}
	
	private void registerListeners(Listener... listeners) {
		for (Listener listener : listeners) {
			this.getServer().getPluginManager().registerEvents(listener, this);
		}
	}

	@Override
	public ItemStack[] getDeathInventory(Player player) {
		return player.getInventory().getContents();
	}

	@Override
	public void restoreItems(Player player) {
		setUnconscious(player, false);
	}
	
	public void setUnconscious(final Player player, boolean unconscious) {
		if (unconscious) {
			this.unconscious.add(player.getName());
			this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

				@Override
				public void run() {
					GildorymUnconsciousness.this.setUnconscious(player.getName(), false);
				}
			
			}, 18000L);
		} else {
			this.unconscious.remove(player.getName());
			removeDeathLocation(player);
			removeDeathTime(player);
		}
	}
	
	public void setUnconscious(final String playerName, boolean unconscious) {
		if (unconscious) {
			this.unconscious.add(playerName);
			this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

				@Override
				public void run() {
					GildorymUnconsciousness.this.setUnconscious(playerName, false);
				}
				
			}, 18000L);
		} else {
			this.unconscious.remove(playerName);
			removeDeathLocation(playerName);
			removeDeathTime(playerName);
		}
	}
	
	public boolean isUnconscious(Player player) {
		return unconscious.contains(player.getName());
	}
	
	public Location getDeathLocation(Player player) {
		return (Location) deathLocations.get(player.getName());
	}
	
	public void setDeathLocation(Player player, Location location) {
		deathLocations.set(player.getName(), location);
		try {
			deathLocations.save(deathLocationsFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	private void removeDeathLocation(Player player) {
		deathLocations.set(player.getName(), null);
		try {
			deathLocations.save(deathLocationsFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	private void removeDeathLocation(String playerName) {
		deathLocations.set(playerName, null);
		try {
			deathLocations.save(deathLocationsFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public long getDeathTime(Player player) {
		return deathTimes.getLong(player.getName());
	}
	
	public void setDeathTime(Player player) {
		deathTimes.set(player.getName(), System.currentTimeMillis());
		try {
			deathTimes.save(deathTimesFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	private void removeDeathTime(Player player) {
		deathTimes.set(player.getName(), null);
		try {
			deathTimes.save(deathTimesFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	private void removeDeathTime(String playerName) {
		deathTimes.set(playerName, null);
		try {
			deathTimes.save(deathTimesFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public MorePlayerModels getMorePlayerModels() {
		return mpm;
	}

}
