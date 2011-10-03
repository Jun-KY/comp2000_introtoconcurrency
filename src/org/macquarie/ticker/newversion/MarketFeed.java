/*
 * Project: 	Wk7StockTickerNew
 * File:		MarketFeed.java
 *
 * This file is part of the sample code bundle for week 7 of the
 * unit COMP229 "Object Oriented Programming Practices".
 *
 * Copyright (C) 2007-2010 Dominic Verity, Macquarie University.
 *
 * This is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * It is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Kiama.  (See files COPYING and COPYING.LESSER.)  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.macquarie.ticker.newversion;

import java.util.Random;

/**
 * This class simulates the "market feed" which accepts a
 * stream of price updates and stores the most recent price
 * for each stock in the market.
 * 
 * In practice this would read information from an incoming 
 * data stream provided by an exchange, but in this case we
 * randomly generate stock price movements.
 * 
 * @author dom
 *
 */
public class MarketFeed extends Thread {
	
	// Static data members (constants and class variable)

	/**
	 * Array of symbolic names of the stocks in this market
	 */
	private static final String[] STOCK_NAMES = {"ANZ","TLS", "NAB", "RIO", "GIO"};

	/**
	 * Number of stocks traded in this market
	 */
	public static final int NUM_STOCKS = STOCK_NAMES.length;
	
	/**
	 * Simulation parameter - maximum uptick / downtick per update
	 */
	private static final int MAX_TICK = 20;
	
	/**
	 * Simulation parameter - maximum wait between updates
	 */
	private static final long MAX_UPDATE_WAIT = 1000;

	// Data members (instance variables)
	
	/**
	 * Array to hold the most recent traded price in each stock
	 */
	private int[] mQuotes;
	
	/**
	 * Variable to hold a random number generator
	 */
	private Random mGenerator;

	// Constructors
	
	/**
	 * Default constructor - create a quotes array and
	 * populate it with randomly generated initial values.
	 */
	MarketFeed () {
		mQuotes = new int[NUM_STOCKS];
		mGenerator = new Random();
		for (int i = 0; i < NUM_STOCKS; i++)
			mQuotes[i] = Math.abs(mGenerator.nextInt()) % 2000;
	}

	// Public methods
	
	/**
	 * The run() method for this thread, simply generates
	 * price updates at randomly chosen update intervals.
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			while (!interrupted()) {
				int vStockNum = (int)(Math.abs (mGenerator.nextLong()) % NUM_STOCKS);
				long vPriceUpdate = (int)(mGenerator.nextLong() % MAX_TICK);
				mQuotes[vStockNum] += vPriceUpdate;
				Thread.sleep(Math.abs(mGenerator.nextLong()) % MAX_UPDATE_WAIT);
			}
		} catch (InterruptedException e) { 
			// Nothing to do here, just drop through and exit.
		}
	}
	
	/**
	 * Get a stock quote for a specific numbered stock.
	 * 
	 * @param pStockNo the number of the stock to return a quote for.
	 * @return the quote if 0 <= pStockNo < NUM_STOCKS and -1 otherwise. 
	 */
	public int getQuote(int pStockNo) {
		if (0 <= pStockNo && pStockNo < NUM_STOCKS) 
			return mQuotes[pStockNo];
		else
			return -1;
	}
	
	/**
	 * Get the ticker name for a specific numbered stock
	 * 
	 * @param pStockNo the number of the stock whose ticker name is required.
	 * @return the ticker name if 0 <= pStockNo < NUM_STOCKS and empty otherwise.
	 */
	public String getTickerName(int pStockNo) {
		if (0 <= pStockNo && pStockNo < NUM_STOCKS) 
			return STOCK_NAMES[pStockNo];
		else
			return "";
	}
}
