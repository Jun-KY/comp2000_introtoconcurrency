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

package org.macquarie.lambda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * A version of the responsive GUI application which uses
 * thread interrupts and lambdas.
 */
@SuppressWarnings("serial")
public class ResponsiveLambda extends JFrame implements ActionListener {

	private static final int CIRCLE_DIAMETER = 30;

	private static ResponsiveLambda mApplication;

	private JPanel mContentPane = null;
	private JButton mButton = null;
	private JPanel mDrawingPanel = null;
	private Thread mDrawingThread = null;

	/**
	 * Start the application by creating an application
	 * object and running its {@link ResponsiveLambda#initApp()}
	 * method in the event dispatch thread.
\	 */
	public static void main(String[] args) {
		mApplication = new ResponsiveLambda();
		
		SwingUtilities.invokeLater(()-> mApplication.initApp());
	}
	
	/**
	 * Setup the GUI by calling {@link ResponsiveLambda#setup()}
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

	/**
	 * This method listens to events from the "Start" button
	 * and starts the circle drawing animation when that button in pressed.
	 *
	 * Some implementation plot points:
	 * <ul>
	 * 	<li>We use the thread interrupt mechanism (rather than mTrucking) to
	 * stop a circle drawing thread.</li>
	 * 	<li>Rather than defining a separate thread class, we specify the run
	 * method of the circle drawing thread by passing a lambda to the
	 * {@link Thread} constructor.</li>
	 * 	<li>We are careful to only execute drawing actions in the event dispatch
	 * thread, using {@link SwingUtilities#invokeLater(Runnable r)}. Here again
	 * the {@link Runnable} passed is defined using a lambda.</li>
	 * </ul>
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (mDrawingThread != null)
			mDrawingThread.interrupt();
		mDrawingThread = new Thread(() -> {
			System.out.println("Starting a new circle drawing thread!");

			Graphics vCanvas = mDrawingPanel.getGraphics();
			int vHeight = mDrawingPanel.getHeight();
			int vWidth = mDrawingPanel.getWidth();

			Random vGenerator = new Random();

			SwingUtilities.invokeLater(() ->
					vCanvas.clearRect(0, 0, vWidth, vHeight));

			try{
				while (!Thread.currentThread().isInterrupted()) {
					int vXord = (int) (Math.abs(vGenerator.nextLong()) % (vWidth - CIRCLE_DIAMETER));
					int vYord = (int) (Math.abs(vGenerator.nextLong()) % (vHeight - CIRCLE_DIAMETER));
					SwingUtilities.invokeLater(() ->
							vCanvas.drawOval(vXord, vYord, CIRCLE_DIAMETER, CIRCLE_DIAMETER));
					Thread.sleep(200);
				}
			} catch (InterruptedException ex) {
				// Nothing do do here, just exit gracefully
			}

			System.out.println("Exiting a circle drawing thread!");
		});
		mDrawingThread.start();
	}

}
