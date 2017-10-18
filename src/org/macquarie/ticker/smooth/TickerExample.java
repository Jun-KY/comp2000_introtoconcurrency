/**
 * This file is part of a project entitled IntroToConcurrency which is
 * provided as sample code for the following Macquarie University unit of study:
 * 
 * COMP229 "Object Oriented Programming Practices"
 * 
 * Copyright (c) 2011-2017 Dominic Verity and Macquarie University.
 * 
 * IntroToConcurrency is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * IntroToConcurrency is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with IntroToConcurrency. (See files COPYING and COPYING.LESSER.) If
 * not, see <http://www.gnu.org/licenses/>.
 */

package org.macquarie.ticker.smooth;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 * A simple application to demonstrate a widget of the {@link StockTicker}
 * class.
 * 
 * @author Dominic Verity
 *
 */
@SuppressWarnings("serial")
public class TickerExample extends JFrame {

	private static TickerExample mApplication;

	private JPanel jContentPane = null;
	private StockTicker mStockTicker = null;

	public static void main(String[] args) {
		mApplication = new TickerExample();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mApplication.initApp();
			}
		});
	}

	private void initApp() {
		setup();
		pack();
		setVisible(true);
	}
	
	/**
	 * Create the components for the GUI and
	 * lay them out in the application window.
	 */
	private void setup() {
		setTitle("Stock Ticker Example");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mStockTicker = new StockTicker();
		mStockTicker.start();

		jContentPane = (JPanel)getContentPane();
		
		jContentPane.setLayout(new BorderLayout());
		jContentPane.add(mStockTicker, BorderLayout.SOUTH);
		jContentPane.setPreferredSize(new Dimension(300,200));
	}
}
