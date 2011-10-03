/**
 * This file is part of a project entitled Wk8BouncingHead which is provided as
 * sample code for the following Macquarie University unit of study:
 * 
 * COMP229 "Object Oriented Programming Practices"
 * 
 * Copyright (c) 2011 Dominic Verity and Macquarie University.
 * 
 * Wk8BouncingHead is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Wk8BouncingHead is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Wk8BouncingHead. (See files COPYING and COPYING.LESSER.) If not,
 * see <http://www.gnu.org/licenses/>.
 */

package org.macquarie.head;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Bouncing extends JFrame implements ActionListener {

	private static Bouncing mApplication;

	private JPanel mContentPane = null;
	private Animator mSpriteAnimator = null;
	private JButton mButton = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		mApplication = new Bouncing();
			
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mApplication.initApp();
			}
		});
	}

	/**
	 * Setup the GUI by calling {@link Bouncing#setup()} and
	 * set the application running.
	 */
	private void initApp() {
		setup();
		pack();
		setVisible(true);
		new Thread(mSpriteAnimator).start();
	}

	
	/**
	 * Create the GUI components for this application
	 * and ley them out on the content pane of a top-level 
	 * window.
	 * 
	 */
	private void setup() {
		setTitle("Bouncing Head");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		mButton = new JButton();
		mButton.setText("New Head");
		mButton.addActionListener(this);
		
		mSpriteAnimator = new Animator();

		mContentPane = (JPanel)getContentPane();
		mContentPane.setLayout(new BorderLayout());
		mContentPane.add(mButton, BorderLayout.SOUTH);
		mContentPane.add(mSpriteAnimator, BorderLayout.CENTER);
	}

	/* (non-Javadoc)
	 * Responds to a button push by adding another animated sprite to
	 * the sprite animator panel.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		mSpriteAnimator.addSprite();
	}

}