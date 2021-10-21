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

package org.macquarie.responsive;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Responsive extends JFrame implements ActionListener {

	private static final int CIRCLE_DIAMETER = 30;

	private static Responsive mApplication;

	private JPanel mContentPane = null;
	private JButton mButton = null;
	private JPanel mDrawingPanel = null;
	private CircleDrawingThread mDrawingThread = null;

	/**
	 * Start the application by creating an application
	 * object and running its {@link Responsive#initApp()}
	 * method in the event dispatch thread.
\	 */
	public static void main(String[] args) {
		mApplication = new Responsive();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mApplication.initApp();
			}
		});
	}
	
	/**
	 * Setup the GUI by calling {@link Responsive#setup()}
	 * and start it running by making it visible.
	 */
	private void initApp() {
		setup();
		pack();
		setVisible(true);
	}

	/**
	 * Creates the components of the GUI
	 * of this application and lays them out on the 
	 * content pane of the it's top-level window.
	 * 
	 */
	private void setup() {
		setTitle("ResponsiveTest");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		mButton = new JButton("Start");
		mButton.addActionListener(this);
		
		mDrawingPanel = new JPanel();
		mDrawingPanel.setPreferredSize(new Dimension(200,300));
		
		mContentPane = (JPanel)getContentPane();
		mContentPane.setLayout(new BorderLayout());
		mContentPane.add(mButton, BorderLayout.SOUTH);
		mContentPane.add(mDrawingPanel, BorderLayout.CENTER);
	}

	/* (non-Javadoc)
	 * This method listens to events from the "Start" button
	 * and starts the circle drawing animation when the button in pressed.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (mDrawingThread != null)
			mDrawingThread.stopTrucking();
		mDrawingThread = new CircleDrawingThread();
		mDrawingThread.start();
	}

	private class CircleDrawingThread extends Thread {
		
		/**
		 * The thread continues running for a long as thus variable
		 * remains true.
		 */
		private boolean mTrucking = true;
		
		/**
		 * This method notifies the thread code that it should clean
		 * itself up and terminate gracefully.
		 */
		protected void stopTrucking() {
			mTrucking = false;
		}
		
		/**
		 * This is the "body of the thread" - that is, the code executed
		 * in this thread of execution once it has been initialised.
		 * 
		 * Our thread remains "alive" until this method returns - at which 
		 * point it switches to the "dead" state.
		 */
		public void run () {
			drawCircles();
		}
		
		/**
		 * Draws randomly positioned circles on the jDrawingPanel component 
		 * of the GUI. Circles are drawn at 0.2 second intervals
		 */
		private void drawCircles() {
			System.out.println("Starting a new circle drawing thread!");
			
			Graphics vCanvas = mDrawingPanel.getGraphics();
			int vHeight = mDrawingPanel.getHeight();
			int vWidth = mDrawingPanel.getWidth();
			
			Random vGenerator = new Random();
			
			vCanvas.clearRect(0, 0, vWidth, vHeight);
			
			while (mTrucking) {
				int vXord = (int) (Math.abs(vGenerator.nextLong()) % (vWidth - CIRCLE_DIAMETER));
				int vYord = (int) (Math.abs(vGenerator.nextLong()) % (vHeight - CIRCLE_DIAMETER));
				vCanvas.drawOval(vXord, vYord, CIRCLE_DIAMETER, CIRCLE_DIAMETER);	
				doPause(200);
			}
			
			System.out.println("Exiting a circle drawing thread!");
		}
	}
	/**
	 * Simple helper method, which pauses execution of our program
	 * for a specified period of time.
	 * 
	 * @param pMilliSeconds length of pause (in milliseconds).
	 */
	private void doPause(int pMilliSeconds) {
		try {
			Thread.sleep(pMilliSeconds);
		} catch (InterruptedException e) { }
	}

}
