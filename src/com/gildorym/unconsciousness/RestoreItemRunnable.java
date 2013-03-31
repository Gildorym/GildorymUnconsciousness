package com.gildorym.unconsciousness;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RestoreItemRunnable implements Runnable {
	
	private Player player;
	private ItemStack[] items;

	public RestoreItemRunnable(Player player, ItemStack[] items) {
		this.player = player;
		this.items = items;
	}

	@Override
	public void run() {
		player.getInventory().addItem(items);
	}

}
