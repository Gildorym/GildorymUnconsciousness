package com.gildorym.unconsciousness;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectsRunnable implements Runnable {
	
	private Player player;
	
	public PotionEffectsRunnable(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 12000, 0), true);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 12000, 9), true);
	}

}
