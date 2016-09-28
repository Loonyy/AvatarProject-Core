/*******************************************************************************
 * AvatarProject Core
 * Copyright (C) 2016  (AvatarProject), (jedk1)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.avatarproject.core.ability;

import java.lang.reflect.Modifier;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

import com.avatarproject.core.AvatarProjectCore;
import com.avatarproject.core.element.Element;
import com.avatarproject.core.exception.AbilityRegisteredException;
import com.github.abilityapi.Ability;
import com.github.abilityapi.AbilityAPI;
import com.github.abilityapi.AbilityProvider;
import com.google.common.reflect.ClassPath;

public abstract class BaseAbilityProvider implements IAbility, AbilityProvider<Ability> {

	private static final HashMap<String, BaseAbilityProvider> ABILITIES = new HashMap<>();

	private String id;
	private String name;
	private String description;
	private Element element;
	private boolean passive;

	/**
	 * Base constructor for all abilities
	 * @param id Internal ID to be assigned to the ability
	 * @param name Name of the ability that will be displayed in messages, etc
	 * @param description Description of the ability that will be displayed in messages, etc
	 * @param element Element of the ability, leave null for Avatar
	 * @param passive Boolean if the ability does damage or not
	 * @throws AbilityRegisteredException Thrown if the ability is already registered
	 */
	public BaseAbilityProvider(String id, String name, String description, Element element, boolean passive) throws AbilityRegisteredException {
		if (ABILITIES.containsKey(id)) {
			throw new AbilityRegisteredException("Ability with id " + id + " is already registered!");
		}
		setId(id);
		setName(name);
		setDescription(description);
		setElement(element);
		setPassive(passive);
		System.out.println("Registering new ability id: " + id + " name: " + name);
		ABILITIES.put(id, this);
	}

	@Override
	public String getId() {
		return id;
	}

	/**
	 * Sets the ID of the ability
	 * @param id String id of the ability, usually a lower-case version of the ability name
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the ability that will be displayed in messages, etc
	 * @param name String name of the ability
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the ability that will be displayed in messages, etc
	 * @param description String description of the ability
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Element getElement() {
		return element;
	}

	/**
	 * Sets the element of the ability
	 * Set as null for Avatar
	 * @param element Element of the ability
	 */
	public void setElement(Element element) {
		this.element = element;
	}

	@Override
	public boolean isPassive() {
		return passive;
	}

	/**
	 * Sets if the ability does damage or not
	 * @param passive Boolean for damage
	 */
	public void setPassive(boolean passive) {
		this.passive = passive;
	}

	/**
	 * Checks if the provided ability id is valid
	 * @param id String id of the ability
	 * @return Boolean is ability valid
	 */
	public static boolean isValidAbility(String id) {
		return getAbilities().containsKey(id);
	}

	/**
	 * Gets the HashMap containing ability IDs and Ability instances
	 * @return
	 */
	public static HashMap<String, BaseAbilityProvider> getAbilities() {
		return ABILITIES;
	}

	/**
	 * Registers all the abilities in AvatarProject-Core
	 */
	public static void registerAbilities() {
		registerAbilities(AvatarProjectCore.getInstance(), "com.avatarproject");
	}
	
	/**
	 * Register all the abilities
	 * @param plugin JavaPlugin that the abilities belong to
	 * @param packageName Package that the plugin belongs in
	 */
	public static void registerAbilities(JavaPlugin plugin, String packageName) {
		ABILITIES.clear();
		ClassLoader loader = plugin.getClass().getClassLoader();
		try {
			ClassPath.from(loader).getAllClasses().stream()
			.filter(info -> {
				if (!info.getPackageName().startsWith(packageName)) {
					return false;
				}
				Class<?> c = info.load();
				if (!AbilityProvider.class.isAssignableFrom(c) || c.isInterface() || Modifier.isAbstract(c.getModifiers())) {
					return false;
				}
				return true;
			})
			.forEach(info -> {
				Class<?> c = info.load();
				try {
					AbilityAPI.get().getRegistry().register((AbilityProvider<?>) c.newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Unregisters all AbilityProviders registered with AvatarProject-Core
	 */
	public static void unregisterAbilities() {
		for (BaseAbilityProvider bap : ABILITIES.values()) {
			AbilityAPI.get().getRegistry().getProviders().remove(bap);
		}
	}
}
