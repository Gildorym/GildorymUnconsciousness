package com.gildorymrp.unconsciousness;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class SerializableLocation implements ConfigurationSerializable, Serializable {
	
	private static final long serialVersionUID = 1721511236574072962L;
	private int x;
	private int y;
	private int z;
	private String worldName;

	private SerializableLocation() {}
	
	public SerializableLocation(Location location) {
		x = location.getBlockX();
		y = location.getBlockY();
		z = location.getBlockZ();
		worldName = location.getWorld().getName();
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> serialized = new HashMap<String, Object>();
		serialized.put("x", x);
		serialized.put("y", y);
		serialized.put("z", z);
		serialized.put("world", worldName);
		return serialized;
	}
	
	public static SerializableLocation deserialize(Map<String, Object> serialized) {
		SerializableLocation deserialized = new SerializableLocation();
		deserialized.x = (Integer) serialized.get("x");
		deserialized.y = (Integer) serialized.get("y");
		deserialized.z = (Integer) serialized.get("z");
		deserialized.worldName = (String) serialized.get("world");
		return deserialized;
	}
	
	public Location toLocation() {
		return new Location(Bukkit.getServer().getWorld(worldName), x, y, z);
	}

}
