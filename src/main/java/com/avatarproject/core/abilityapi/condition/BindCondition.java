package com.avatarproject.core.abilityapi.condition;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.avatarproject.core.ability.BaseAbilityProvider;
import com.avatarproject.core.player.APCPlayer;
import com.github.abilityapi.trigger.sequence.Condition;

public abstract class BindCondition<T extends Event> implements Condition<Event> {

	public boolean bind(Player player, BaseAbilityProvider baseAbilityProvider) {
		String ability = APCPlayer.get(player).getAbility(player.getInventory().getHeldItemSlot());
		return ability != null && ability.equals(baseAbilityProvider.getId());
	}
}
