package com.gildorymrp.unconsciousness;

import noppes.mpm.ModelData;
import noppes.mpm.MorePlayerModels;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
	
	private GildorymUnconsciousness plugin;
	
	public PlayerMoveListener(GildorymUnconsciousness plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (plugin.isUnconscious(event.getPlayer())) {
			event.setCancelled(true);
			ModelData data = MorePlayerModels.getPlayerData(event.getPlayer().getName());
			int newState = data.moveState == 2 ? 0 : 2;
			float rotation = event.getPlayer().getLocation().getYaw();
			while (rotation < 0.0F) {
				rotation += 360.0F;
			}
			while (rotation > 360.0F) {
				rotation -= 360.0F;
			}
			data.rotation = ((int)((rotation + 45.0F) / 90.0F));
		    data.setNewState(event.getPlayer(), newState);
		}
	}

}
