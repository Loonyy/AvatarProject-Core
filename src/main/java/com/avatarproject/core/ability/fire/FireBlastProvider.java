package com.avatarproject.core.ability.fire;

import org.bukkit.entity.Player;

import com.avatarproject.core.ability.FireAbilityProvider;
import com.avatarproject.core.exception.AbilityRegisteredException;
import com.github.abilityapi.Ability;
import com.github.abilityapi.trigger.Trigger;

public class FireBlastProvider extends FireAbilityProvider {

	public FireBlastProvider() throws AbilityRegisteredException {
		super("fireblast", "FireBlast", "Throws a fire. Duh", false);
	}

	@Override
	public Ability createInstance(Player arg0) {
		return null;
	}

	@Override
	public Trigger getTrigger() {
		return null;
	}

}
