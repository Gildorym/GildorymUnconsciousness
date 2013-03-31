package com.gildorym.unconsciousness;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlindnessRunnable implements Runnable {
	
	private Player player;
	
	public BlindnessRunnable(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 12000, 4), true);
	}

}
