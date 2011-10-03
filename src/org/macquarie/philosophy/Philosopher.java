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
import java.util.Random;

/**
 * Simulate a single philosopher in the dinning philosophers example.
 * 
 * @author Dominic Verity
 *
 */
public class Philosopher extends Thread {

	private static Random mGenerator;
	private static int mNumPhilosophers = 0;
	private static int mLeftChopsticksHeld = 0;
	
	public static int getLeftChopsticksHeld() {
		return mLeftChopsticksHeld;
	}
	
	public static void randomPause(long pMaxPause) {
		if (mGenerator == null)
			mGenerator = new Random();
		try {
			Thread.sleep(Math.abs(mGenerator.nextLong()) % pMaxPause);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Chopstick mLeft, mRight;
	private int mNum;
	
	public Philosopher(Chopstick pLeft, Chopstick pRight) {
		mLeft = pLeft;
		mRight = pRight;
		mNum = mNumPhilosophers++;
	}
	
	public void run() {
		while (true) {
			randomPause(500);
			synchronized (mLeft) {
				mLeftChopsticksHeld++;
				System.out.println(toString() + " has lifted " + mLeft.toString() +".");
				randomPause(150);
				synchronized (mRight) {
					System.out.println(toString() + " has lifted " + mRight.toString() +".");
					System.out.println(toString() + " has eaten");
				}
				mLeftChopsticksHeld--;
			}
		}
	}
	
	public String toString() {
		return "Philosopher " + String.valueOf(mNum);
	}
}
