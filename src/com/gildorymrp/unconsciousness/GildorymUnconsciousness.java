package com.gildorymrp.unconsciousness;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.gildorymrp.api.plugin.death.GildorymDeathPlugin;

public class GildorymUnconsciousness extends JavaPlugin implements GildorymDeathPlugin {
	
	public static final String PREFIX = "" + ChatColor.DARK_BLUE + ChatColor.MAGIC + "|" + ChatColor.RESET + ChatColor.DARK_PURPLE + "GildorymUnconsciousness" + ChatColor.DARK_BLUE + ChatColor.MAGIC + "| " + ChatColor.RESET;
	private File deathLocationsFile = new File(this.getDataFolder().getPath() + File.separator + "death-locations.yml");
	private YamlConfiguration deathLocations = new YamlConfiguration();
	private Set<String> unconscious = new HashSet<String>();
	
	@Override
	public void onEnable() {
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
		try {
			deathLocations.load(this.getDataFolder() + File.separator + "death-locations.yml");
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
				new EntityDamageListener(this));
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
	
	public void setUnconscious(final Player player, Boolean unconscious) {
		if (unconscious) {
			this.unconscious.add(player.getName());
			ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
			PacketContainer packet = protocolManager.createPacket(Packets.Server.BED);
			packet.getIntegers()
			.write(0, player.getEntityId())
			.write(1, 0)
			.write(2, player.getLocation().getBlockX())
			.write(3, player.getLocation().getBlockY())
			.write(4, player.getLocation().getBlockZ());
			this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

				@Override
				public void run() {
					GildorymUnconsciousness.this.setUnconscious(player, false);
				}
			
			}, 18000L);
		} else {
			this.unconscious.remove(player.getName());
			removeDeathLocation(player);
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

}
