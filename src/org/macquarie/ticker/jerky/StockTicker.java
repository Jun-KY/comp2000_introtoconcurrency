/**
 * This file is part of a project entitled Wk8StockTicker which is provided as
 * sample code for the following Macquarie University unit of study:
 * 
 * COMP229 "Object Oriented Programming Practices"
 * 
 * Copyright (c) 2011-2012 Dominic Verity and Macquarie University.
 * 
 * Wk8StockTicker is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Wk8StockTicker is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Wk8StockTicker. (See files COPYING and COPYING.LESSER.) If not,
 * see <http://www.gnu.org/licenses/>.
 */

package org.macquarie.ticker.jerky;
import java.util.Random;

import javax.swing.JTextField;

/**
 * <p>A simple widget class which implements a scrolling stock ticker to
 * display the stock prices coming from an equity market.</p>
 * 
 * <p>This version of the widget writes text into a {@link JTextField}. This
 * is simple and effective but results in a slightly "jerky" animation.</p>
 * 
 * @author Dominic Verity
 *
 */
public class StockTicker extends JTextField implements Runnable {

	private static final long serialVersionUID = 1L;

	/**
	 * Stock quote information.
	 */
	
	private static final int mStocks = 5;
	private static final int mMaxTick = 20;
	private static final long mMaxUpdateWait = 1000;
	private static final String[] mStockNames = {"ANZ","TLS", "NAB", "RIO", "GIO"};

	private static Thread mMarketFeed = null;
	private static int[] mQuotes = {1756, 465, 1890, 2303, 678};
	
	/**
	 * Now the members which are used to maintain the current
	 * state of the ticker display.
	 */
	private static final long UPDATE_PAUSE = 100;
	private static final int MESSAGE_LENGTH = 200;

	private int mCurrentStock = 0;
	private StringBuffer mStringBuffer = null;
	
	/**
	 * mTrucking variable allows us to signal thread termination.
	 */
	private boolean mTrucking = false;
	private Thread mThisThread = null;
	
	public void stopTicker() {
		mTrucking = false;
		mThisThread = null;
	}
	
	public boolean isTrucking() {
		return mTrucking;
	}
	
	/**
	 * Spawn a separate thread to randomly update our stock prices.
	 * Here we use the "anonymous inner class" approach.
	 * 
	 * Once started, this thread continues running until the application
	 * is terminated.
	 */
	private void startMarketFeed() {
		
		if (mMarketFeed != null) return;	
			// Do nothing if the feed is already running.

		mMarketFeed = new Thread() {

			public void run() {
				Random vGenerator = new Random();
				
				while (true) {
					int vStockNum = (int)(Math.abs (vGenerator.nextLong()) % mStocks);
					long vPriceUpdate = (int)(vGenerator.nextLong() % mMaxTick);
					mQuotes[vStockNum] += vPriceUpdate;
					try {
						Thread.sleep(Math.abs(vGenerator.nextLong()) % mMaxUpdateWait);
					} catch (InterruptedException e) { }
				}
			}
		};
		
		mMarketFeed.start();
	}
	
	/**
	 * Default constructor. Initialises the text field and
	 * an associated string buffer with stock price information.
	 */
	public StockTicker() {
		mStringBuffer = new StringBuffer(MESSAGE_LENGTH + 50);
		mCurrentStock = 0;
		while (mStringBuffer.length() < MESSAGE_LENGTH)
			appendCurrentStock();
		setText(mStringBuffer.toString());
		setEditable(false);
		setFocusable(false);
		setBackground(null);
	}

	private void appendCurrentStock() {
		mStringBuffer.append(mStockNames[mCurrentStock]+":" + 
				mQuotes[mCurrentStock]+" ");
		mCurrentStock = ++mCurrentStock % mStocks;
	}
	
	/**
	 * Here is the body of the thread, which updates the
	 * stock price ticker display.
	 */
	public void run() {
		
		mTrucking = true;
		startMarketFeed();
		
		while (mTrucking) {
			try {
				Thread.sleep(UPDATE_PAUSE);
			} catch (InterruptedException e) { }
			
			mStringBuffer.deleteCharAt(0);
			if (mStringBuffer.length() < MESSAGE_LENGTH) 
				appendCurrentStock();
			setText(mStringBuffer.toString());
			
		}
		
	}

	/**
	 * Starts the ticker, by creating a new thread from the
	 * current object and starting it.
	 */
	public void start() {
		if (mThisThread == null) {
			mThisThread = new Thread(this);
			mThisThread.start();
		}
	}

	
}
