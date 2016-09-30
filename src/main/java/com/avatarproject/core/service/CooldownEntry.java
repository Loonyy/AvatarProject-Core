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
package com.avatarproject.core.service;

import org.bukkit.boss.BossBar;

public class CooldownEntry {
	
	private String ability;
	private Long start;
	private Long duration;
	private BossBar bar;

	/**
	 * CooldownEntry information about a cool-down
	 * @param ability String id of the ability
	 * @param start Long start time of the cool-down
	 * @param duration Long duration of the cool-down
	 */
	public CooldownEntry(String ability, Long start, Long duration) {
		setAbility(ability);
		setStart(start);
		setDuration(duration);
	}

	/**
	 * Gets the id of the ability on cool-down
	 * @return String id of the ability
	 */
	public String getAbility() {
		return ability;
	}

	/**
	 * Sets the id of the ability on cool-down
	 * @param ability String id of the ability
	 */
	public void setAbility(String ability) {
		this.ability = ability;
	}

	/**
	 * Gets the start time of the cool-down
	 * @return Long time of when the cool-down started
	 */
	public Long getStart() {
		return start;
	}

	/**
	 * Set the start time of the cool-down
	 * @param start Long time of the start of the cool-down
	 */
	public void setStart(Long start) {
		this.start = start;
	}

	/**
	 * Gets the duration of the cool-down
	 * @return Long duration of the cool-down
	 */
	public Long getDuration() {
		return duration;
	}

	/**
	 * Sets the duration of the cool-down
	 * @param duration Long duration of the cool-down
	 */
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	/**
	 * Sets the BossBar of the cool-down
	 * @return BossBar instance
	 */
	public BossBar getBar() {
		return bar;
	}

	/**
	 * Sets the BossBar of the cool-down
	 * @param bar BossBar instance
	 */
	public void setBar(BossBar bar) {
		this.bar = bar;
	}

	/**
	 * Gets the time remaining on the cool-down
	 * @return Long remaining time
	 */
	public long getRemainder() {
		return (getStart() + getDuration()) - System.currentTimeMillis();
	}
	
}
