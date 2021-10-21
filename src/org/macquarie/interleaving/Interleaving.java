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

package org.macquarie.interleaving;

/**
 * An application which demonstrates how the output from a number of
 * threads is interleaved as it is printed to the terminal.
 * 
 * @author Dominic Verity
 *
 */
public class Interleaving {

	/**
	 * Main method, starts two counter threads and then retires.
	 */
	public static void main(String[] args) {
		new CounterThread("First Thread").start();
		new CounterThread("Second Thread").start();
		new CounterThread("Third Thread").start();
	}

}
