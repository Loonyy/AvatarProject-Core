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

	public FireAbilityProvider(String id, String name, String description, boolean passive) throws AbilityRegisteredException {
		super(id, name, description, Element.FIRE, passive);
	}

}
