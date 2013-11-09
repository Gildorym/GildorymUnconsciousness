package com.gildorymrp.unconsciousness;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gildorymrp.api.plugin.death.GildorymDeathPlugin;

public class GildorymUnconsciousness extends JavaPlugin implements GildorymDeathPlugin {
	
	private File dataFile;
	private YamlConfiguration data;
	
	@Override
	public void onEnable() {
		ConfigurationSerialization.registerClass(SerializableLocation.class);
		this.dataFile = new File(this.getDataFolder().getPath() + File.separator + "data.yml");
		this.data = new YamlConfiguration();
		if (!this.getDataFolder().exists()) {
			this.getDataFolder().mkdir();
		}
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		try {
			data.load(dataFile);

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
			
			if (Bukkit.getServer().getOnlinePlayers().toString().contains(player.getName())){
				this.removeDeathLocation(player);
				this.removeDeathTime(player);
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 0, 0), true);
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15000, 0), true);
				player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 15000, 4), true);
			}else{
				
			}
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
		return data.getStringList("unconscious.").contains(player.getName());
	}
	
	public Location getDeathLocation(Player player) {
		if (data.getStringList("unconscious.").contains(player.getName())) {
			return ((SerializableLocation) data.get(player.getName())).toLocation();
		}
		return null;
	}
	
	public void setDeathLocation(Player player, Location location) {
		data.set("unconscious." + player.getName() + ".location", new SerializableLocation(location));
		try {
			data.save(dataFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	private void removeDeathLocation(Player player) {
		removeDeathLocation(player.getName());
	}
	
	private void removeDeathLocation(String playerName) {
		data.set("unconscious." + playerName + ".location", null);
		try {
			data.save(dataFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public long getDeathTime(Player player) {
		return data.getLong("unconscious." + player.getName() + ".time");
	}
	
	public void setDeathTime(Player player) {
		setDeathTime(player.getName());
	}
	
	public void setDeathTime(String playerName) {
		data.set("unconscious." + playerName + ".time", System.currentTimeMillis());
		try {
			data.save(dataFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	private void removeDeathTime(Player player) {
		removeDeathTime(player.getName());
	}
	
	private void removeDeathTime(String playerName) {
		data.set("unconscious." + playerName + ".time", null);
		try {
			data.save(dataFile);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public ItemStack[] getDeathInventory(Player arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restoreItems(Player arg0) {
		// TODO Auto-generated method stub
		
	}

}
