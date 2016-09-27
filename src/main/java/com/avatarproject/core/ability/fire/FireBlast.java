package com.avatarproject.core.ability.fire;

import org.bukkit.entity.Player;

import com.github.abilityapi.Ability;

public class FireBlast extends Ability {

	private Player player;
	
	public FireBlast(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public long getExpireTicks() {
		return 0;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void stop() {
		
	}

	@Override
	public void update() {
		
	}

}
