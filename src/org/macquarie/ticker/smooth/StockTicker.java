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

package org.macquarie.ticker.smooth;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

/**
 * <p>A simple widget class which implements a scrolling stock ticker to
 * display the stock prices coming from an equity market.</p>
 * 
 * <p>This version of the widget draws onto the graphics surface of a
 * {@link JPanel} in order to achieve a smooth scrolling effect.</p>
 * 
 * @author Dominic Verity
 *
 */
public class StockTicker extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	/**
	 * Stock quote information.
	 */
	
	private static final int NUMBER_OF_STOCKS = 5;
	private static final int MAX_TICK = 10;
	private static final long MAX_UPDATE_WAIT = 1000;
	private static final String[] STOCK_NAMES = {"ANZ","TLS", "NAB", "RIO", "GIO"};

	private static Thread mMarketFeed = null;
	private static int[] mQuotes = {1756, 465, 1890, 2303, 678};
	
	/**
	 * Now the members which are used to maintain the current
	 * state of the ticker display.
	 */
	private static final long UPDATE_PAUSE = 30;
	private static final int MESSAGE_LENGTH = 60;
	private static final int PIXEL_OFFSET_DELTA = 1;
	
	private int mCurrentStock = 0;
	private StringBuffer mStringBuffer = null;
	
	/**
	 * mTrucking variable allows us to signal thread termination.
	 */
	private boolean mTrucking = false;
	private Thread mThisThread = null;

	private int mPixelOffset;
	
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
					int vStockNum = (int)(Math.abs (vGenerator.nextLong()) % NUMBER_OF_STOCKS);
					long vPriceUpdate = (int)(vGenerator.nextLong() % MAX_TICK);
					mQuotes[vStockNum] += vPriceUpdate;
					try {
						Thread.sleep(Math.abs(vGenerator.nextLong()) % MAX_UPDATE_WAIT);
					} catch (InterruptedException e) { }
				}
			}
		};
		
		mMarketFeed.start();
	}
	
	/**
	 * Default constructor. Initialises the panel and
	 * an associated string buffer with stock price information.
	 */
	public StockTicker() {
		mStringBuffer = new StringBuffer(MESSAGE_LENGTH + 50);
		mCurrentStock = 0;

		mPixelOffset = 0;
		
		Font vFont = getFont().deriveFont(30f).deriveFont(Font.BOLD);
		setFont(vFont);
		
		FontMetrics vMetrics = getFontMetrics(vFont);
		setPreferredSize(new Dimension(100,vMetrics.getHeight()));
		
		while (vMetrics.charsWidth(mStringBuffer.toString().toCharArray(), 
				0, mStringBuffer.length()) < 100)
			appendCurrentStock();

		setFocusable(false);
		setBackground(Color.white);
	}

	private void appendCurrentStock() {
		mStringBuffer.append(STOCK_NAMES[mCurrentStock]+":" + 
				mQuotes[mCurrentStock]+" ");
		mCurrentStock = ++mCurrentStock % NUMBER_OF_STOCKS;
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

			synchronized (this) {
				mPixelOffset += PIXEL_OFFSET_DELTA;
			
				FontMetrics vMetrics = getFontMetrics(getFont());
				int vFirstCharWidth = vMetrics.charWidth(mStringBuffer.charAt(0));
			
				if (mPixelOffset >= vFirstCharWidth) {
					mStringBuffer.deleteCharAt(0);
					if (mStringBuffer.length() < MESSAGE_LENGTH) 
					appendCurrentStock();
					mPixelOffset -= vFirstCharWidth;
				}

				while (vMetrics.charsWidth(mStringBuffer.toString().toCharArray(), 
						0, mStringBuffer.length()) < getWidth())
					appendCurrentStock();
			}
			
			// setText(mStringBuffer.toString());		deleted code
			repaint();
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

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected synchronized void paintComponent(Graphics pGraphics) {
		super.paintComponent(pGraphics);
		
		Font vFont = getFont();
		FontMetrics vMetrics = getFontMetrics(vFont);
		
		int vBaseline = getHeight() - vMetrics.getDescent();
		
		pGraphics.drawString(mStringBuffer.toString(), -mPixelOffset, vBaseline);
	}

	
}
