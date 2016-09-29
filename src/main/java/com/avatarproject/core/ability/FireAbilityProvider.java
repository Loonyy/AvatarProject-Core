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

import com.avatarproject.core.element.Element;
import com.avatarproject.core.exception.AbilityRegisteredException;

public abstract class FireAbilityProvider extends BaseAbilityProvider {

	/**
	 * Registers a fire ability
	 * @param id Internal ID to be assigned to the ability
	 * @param name Name of the ability that will be displayed in messages, etc
	 * @param description Description of the ability that will be displayed in messages, etc
	 * @param passive Boolean if the ability does damage or not
	 * @throws AbilityRegisteredException Thrown if the ability is already registered
	 */
	public FireAbilityProvider(String id, String name, String description, boolean passive, boolean hidden) throws AbilityRegisteredException {
		super(id, name, description, Element.FIRE, passive, hidden);
	}

}
