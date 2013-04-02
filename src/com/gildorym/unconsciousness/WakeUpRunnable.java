package com.gildorym.unconsciousness;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class WakeUpRunnable implements Runnable {
	
	private String player;
	private Location location;
	
	public WakeUpRunnable(Player player, Location location) {
		this.player = player.getName();
		this.location = location;
	}

	@Override
	public void run() {
		if (location.getBlock().getType() == Material.BEDROCK) {
			if (Bukkit.getServer().getPlayer(player) != null) {
				Bukkit.getServer().getPlayer(player).teleport(location.getBlock().getLocation());
			}
			location.getBlock().setType(Material.AIR);
			location.getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
		}
	}

}
