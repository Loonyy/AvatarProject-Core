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
package com.avatarproject.core.element;

import java.util.HashMap;

import org.bukkit.ChatColor;

import com.avatarproject.core.exception.ElementRegisteredException;

public class Element {

	private static final HashMap<String, Element> ELEMENTS = new HashMap<>();

	/** <b>Element:</b> Air **/
	public static Element AIR;
	/** <b>Element:</b> Air **/
	public static Element FLIGHT;

	/** <b>Element:</b> Earth **/
	public static Element EARTH;
	/** <b>Element:</b> Earth **/
	public static Element LAVA;
	/** <b>Element:</b> Earth **/
	public static Element METAL;

	/** <b>Element:</b> Fire **/
	public static Element FIRE;
	/** <b>Element:</b> Fire **/
	public static Element COMBUSTION;
	/** <b>Element:</b> Fire **/
	public static Element LIGHTNING;

	/** <b>Element:</b> Water **/
	public static Element WATER;
	/** <b>Element:</b> Water **/
	public static Element BLOOD;
	/** <b>Element:</b> Water **/
	public static Element PLANT;

	/** <b>Element:</b> Chi **/
	public static Element CHI;

	private String id;
	private String name;
	private Element parent;
	private ChatColor color;

	/**
	 * Register a new element
	 * @param id Internal ID to be assigned to the element
	 * @param name Name of the element that will be displayed in messages, etc
	 * @param parent If the element is a sub-element, assign its parent element
	 * @param color ChatColor that will be used in messages, etc that should be associated with this element
	 * @throws ElementRegisteredException Thrown if the element being registered already exists
	 */
	public Element(String id, String name, Element parent, ChatColor color) throws ElementRegisteredException {
		if (ELEMENTS.containsKey(id)) {
			throw new ElementRegisteredException("Element with id " + id + " is already registered!");
		}
		setId(id);
		setName(name);
		setParent(parent);
		setColor(color);
		ELEMENTS.put(id, this);
	}
	
	/**
	 * Return the id of the element
	 * Used for internal purposes when saving
	 */
	@Override
	public String toString() {
		return id;
	}

	/**
	 * Get the ID of the element
	 * ID is usually a lower-case version of the element
	 * This is NOT the name of the Element
	 * @return String ID of the element
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the internal ID of the element
	 * @param id String ID of the element
	 */
	private void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the name of the element
	 * The name does not include the color of the element
	 * @return String name of the element
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the element
	 * This is what will be displayed in messages, e tc
	 * @param name String name of the element
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the parent of the element
	 * Use if the element is a sub-element, i.e. lava would have the parent of earth
	 * @return Element parent element of the sub element, returns null if the element is a parent
	 */
	public Element getParent() {
		return parent;
	}

	/**
	 * Sets the parent of the element
	 * Leave as null for no parent (i.e. earth would not have a parent) 
	 * @param parent Element that is the parent of this element
	 */
	private void setParent(Element parent) {
		this.parent = parent;
	}

	/**
	 * Gets the ChatColor associated with this element
	 * @return ChatColor associated with this element
	 */
	public ChatColor getColor() {
		return color;
	}

	/**
	 * Sets the ChatColor to be associated with this element
	 * @param color ChatColor to be associated with this element
	 */
	private void setColor(ChatColor color) {
		this.color = color;
	}
	
	/**
	 * Gets an Element object based on the provided id
	 * @param id ID of the element
	 * @return Element if exists
	 */
	public static Element fromString(String id) {
		if (ELEMENTS.containsKey(id)) {
			return ELEMENTS.get(id);
		}
		return null;
	}
	
	/**
	 * Gets the HashMap containing Element ID and Element object
	 * @return HashMap of IDs and elements
	 */
	public static HashMap<String, Element> getElements() {
		return ELEMENTS;
	}

	/**
	 * Reload all the stock/default elements
	 */
	public static void init() {
		ELEMENTS.clear();

		//TODO Update these to use configuration values instead of hard-coded values
		//TODO Add configuration value to disable entire element
		try {
			//Air elements
			AIR = new Element("air", "Air", null, ChatColor.GRAY);
			FLIGHT = new Element("flight", "Flight", AIR, ChatColor.DARK_GRAY);

			//Earth elements
			EARTH = new Element("earth", "Earth", null, ChatColor.GREEN);
			LAVA = new Element("lava", "Lava", EARTH, ChatColor.DARK_GREEN);
			METAL = new Element("metal", "Metal", EARTH, ChatColor.DARK_GREEN);

			//Fire elements
			FIRE = new Element("fire", "Fire", null, ChatColor.RED);
			COMBUSTION = new Element("combustion", "Combustion", FIRE, ChatColor.DARK_RED);
			LIGHTNING = new Element("lightning", "Lightning", FIRE, ChatColor.DARK_RED);

			//Water elements
			WATER = new Element("water", "Water", null, ChatColor.AQUA);
			BLOOD = new Element("blood", "Bloodbend", WATER, ChatColor.DARK_AQUA);
			PLANT = new Element("plant", "Plant", WATER, ChatColor.DARK_AQUA);

			//Chi blocking
			CHI = new Element("chi", "Chi", null, ChatColor.GOLD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
