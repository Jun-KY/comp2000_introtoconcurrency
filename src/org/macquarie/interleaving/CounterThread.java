/**
 * This file is part of a project entitled Week8Samples which is provided as
 * sample code for the following Macquarie University unit of study:
 * 
 * COMP229 "Object Oriented Programming Practices"
 * 
 * Copyright (c) 2011-2012 Dominic Verity and Macquarie University.
 * 
 * Week8Samples is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Week8Samples is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Week8Samples. (See files COPYING and COPYING.LESSER.) If not,
 * see <http://www.gnu.org/licenses/>.
 */

package org.macquarie.interleaving;
import java.util.Random;


/**
 * A simple counter class. Here a counter is continually
 * incremented and its value is printed to the terminal at
 * each step. The delay between increments is determined
 * randomly and the whole thing is implemented as a Thread
 * class, so that we can run more than one counter at a time.
 * 
 * @author Dominic Verity
 *
 */
public class CounterThread extends Thread {

	private int mCount;
	private String mName;
	
	public CounterThread(String pName) {
		mName = pName;
		mCount = 0;
	}
	
	public void run() {
		doCounting();
	}

	/**
	 * Our counter method, called from the run() method.
	 */
	private void doCounting() {
		Random vGenerator = new Random();
		
		while (true) {
			System.out.print(mName);
			System.out.print(": ");
			System.out.println(mCount++);
			
			try {
				Thread.sleep(Math.abs(vGenerator.nextLong()) % 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
