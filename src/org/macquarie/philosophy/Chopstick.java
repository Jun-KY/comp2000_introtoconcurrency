/**
 * This file is part of a project entitled Wk8Philosophers which is provided as
 * sample code for the following Macquarie University unit of study:
 * 
 * COMP229 "Object Oriented Programming Practices"
 * 
 * Copyright (c) 2011 Dominic Verity and Macquarie University.
 * 
 * Wk8Philosophers is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Wk8Philosophers is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Wk8Philosophers. (See files COPYING and COPYING.LESSER.) If not,
 * see <http://www.gnu.org/licenses/>.
 */

package org.macquarie.philosophy;

/**
 * An class whose objects represent the chopsticks in the
 * dining philosophers example. A philosopher lifts a chopstick
 * by taking its lock.
 * 
 * @author Dominic Verity
 *
 */

public class Chopstick {
	private static int mNumChopsticks = 0;
	
	private int mNum;
	
	public Chopstick() {
		mNum = mNumChopsticks++;
	}
	
	public String toString() {
		return "Chopstick " + String.valueOf(mNum);
	}
}
