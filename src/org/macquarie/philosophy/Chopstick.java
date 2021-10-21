/**
 * This file is part of a project entitled IntroToConcurrency which is provided as
 * sample code for the following Macquarie University unit of study:
 * 
 * COMP2000 "Object Oriented Programming Practices"
 * 
 * Copyright (c) 2011-2021 Dominic Verity and Macquarie University.
 * Copyright (c) 2011 The COMP229 Class.
 * 
 * IntroToConcurrency is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * IntroToConcurrency is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with IntroToConcurrency. (See files COPYING and COPYING.LESSER.) If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.macquarie.philosophy;

/**
 * An class whose objects represent the chopsticks in the
 * dining philosophers example. A {@link Philosopher} lifts a 
 * {@link Chopstick} by taking its lock.
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
