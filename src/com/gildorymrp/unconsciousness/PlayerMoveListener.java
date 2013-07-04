package com.gildorymrp.unconsciousness;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;

public class PlayerMoveListener implements Listener {
	
	private GildorymUnconsciousness plugin;
	
	public PlayerMoveListener(GildorymUnconsciousness plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (plugin.isUnconscious(event.getPlayer())) {
			event.setCancelled(true);
			ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
			PacketContainer packet = protocolManager.createPacket(Packets.Server.BED);
			packet.getIntegers()
			.write(0, event.getPlayer().getEntityId())
			.write(1, 0)
			.write(2, event.getFrom().getBlockX())
			.write(3, event.getFrom().getBlockY())
			.write(4, event.getFrom().getBlockZ());
			for (Player player : event.getPlayer().getWorld().getPlayers()) {
				try {
					protocolManager.sendServerPacket(player, packet);
				} catch (InvocationTargetException exception) {
					exception.printStackTrace();
				}
			}
		}
	}

}
