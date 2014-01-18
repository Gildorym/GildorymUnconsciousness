package com.gildorymrp.unconsciousness;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GildorymUnconsciousness extends JavaPlugin {
	
	public static final String PREFIX = "" + ChatColor.DARK_BLUE + ChatColor.MAGIC + "|" + ChatColor.RESET + ChatColor.DARK_PURPLE + "GildorymUnconsciousness" + ChatColor.DARK_BLUE + ChatColor.MAGIC + "| " + ChatColor.RESET;
	private File deathLocationsFile;
	private YamlConfiguration deathLocations;
	private File deathTimesFile;
	private YamlConfiguration deathTimes;
	public Map<String, Integer[]> locationMap;
	public Map<String, String> messageMap;
	
	@Override
	public void onEnable() {
		ConfigurationSerialization.registerClass(SerializableLocation.class);
		this.deathLocationsFile = new File(this.getDataFolder().getPath() + File.separator + "death-locations.yml");
		this.deathLocations = new YamlConfiguration();
		this.deathTimesFile = new File(this.getDataFolder().getPath() + File.separator + "death-times.yml");
		this.deathTimes = new YamlConfiguration();
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
				new PlayerJoinListener(this),
				new PlayerInteractListener(this),
				new EntityDamageByEntityListener(this));
		this.getCommand("wake").setExecutor(new WakeCommand(this));
	}
	
	private void registerListeners(Listener... listeners) {
		for (Listener listener : listeners) {
			this.getServer().getPluginManager().registerEvents(listener, this);
		}
	}
	
	public void setUnconscious(final Player player, boolean unconscious) {
		if (unconscious) {
			this.setDeathTime(player);
			this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

				@Override
				public void run() {
					GildorymUnconsciousness.this.setUnconscious(player.getName(), false);
				}
			
			}, 18000L);
		} else {
			this.removeDeathLocation(player);
			this.removeDeathTime(player);
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 0, 0), true);
		}
	}
	
	public void setUnconscious(final String playerName, boolean unconscious) {
		if (unconscious) {
			this.setDeathTime(playerName);
			this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

				@Override
				public void run() {
					GildorymUnconsciousness.this.setUnconscious(playerName, false);
				}
				
			}, 18000L);
		} else {
			removeDeathLocation(playerName);
			removeDeathTime(playerName);
		}
	}
	
	public boolean isUnconscious(Player player) {
		return deathTimes.contains(player.getName());
	}
	
	public Location getDeathLocation(Player player) {
		return ((SerializableLocation) deathLocations.get(player.getName())).toLocation();
	}
	
	public void setDeathLocation(Player player, Location location) {
		deathLocations.set(player.getName(), new SerializableLocation(location));
		try {
			deathLocations.save(deathLocationsFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	private void removeDeathLocation(Player player) {
		removeDeathLocation(player.getName());
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
		setDeathTime(player.getName());
	}
	
	public void setDeathTime(String playerName) {
		deathTimes.set(playerName, System.currentTimeMillis());
		try {
			deathTimes.save(deathTimesFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	private void removeDeathTime(Player player) {
		removeDeathTime(player.getName());
	}
	
	private void removeDeathTime(String playerName) {
		deathTimes.set(playerName, null);
		try {
			deathTimes.save(deathTimesFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	static Integer[] getClosestLocation(Location playerLocation, Map<String,Integer[]> locationMap) {
		Integer[] closestLocation = null;
		double minDistance = Double.MAX_VALUE;
		for (Integer[] coords : locationMap.values()) {
			try {
				if (calculateDistance(playerLocation, coords) < minDistance) {
					minDistance = calculateDistance(playerLocation, coords);
					closestLocation = coords;
				}
			} catch (IllegalArgumentException ex) {
				
			} catch (NullPointerException ex) {
				
			}
		}
		return closestLocation;
	}
	
	static double calculateDistance(Location playerLocation, Integer[] coords) {
		return Math.sqrt( Math.pow((coords[0] - playerLocation.getX()), 2) +
				Math.pow((coords[1] - playerLocation.getY()), 2) +
				Math.pow((coords[2] - playerLocation.getZ()), 2));
	}

}
