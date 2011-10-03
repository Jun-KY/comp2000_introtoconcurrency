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
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

/**
 * <p>The Animator class maintains and updates the current
 * state of our animation. In this case it contains a list
 * of Sprite objects, each one of which represents an image
 * on the screen (storing its current position and velocity).</p>
 * 
 * <p>This class extends JPanel, to provide a component upon 
 * which the animation is drawn. It also implements Runnable
 * in order to provide a thread which periodically updates
 * the animation and calls <code>repaint()</code> to persuade the event
 * dispatch thread to paint the next frame.</p>
 * 
 * @author Dominic Verity
 *
 */
public class Animator extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int CANVAS_WIDTH = 400;
	public static final int CANVAS_HEIGHT = 400;
	public static final int FRAME_PAUSE = 10;
	
	List<Sprite> mSprites = null;
	
	/**
	 * Constructor, creates a new list of Sprite objects
	 * which starts off empty. 
	 */
	public Animator() {
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		mSprites = new LinkedList<Sprite>(); 
	}
	
	/*
	 * The following methods are synchronized in order to 
	 * avoid situations in which:
	 * 
	 * 1) A sprite is added from, or
	 * 2) A paint of the component is initiated in
	 * 
	 * the event dispatch thread while the step() method is
	 * still halfway through updating the animation from one frame
	 * to the next.
	 */
	
	/**
	 * Creates a new Sprite object, which by default is created
	 * with a random position and velocity, and adds it to the
	 * list of currently active sprites.
	 */
	public synchronized void addSprite() {
		mSprites.add(new Sprite());
	}

	/**
	 * Updates the position of all Sprite objects by iterating
	 * through the list of Sprites
	 */
	public synchronized void step() {
		Iterator<Sprite> vSpriteIterator = mSprites.iterator();

		while (vSpriteIterator.hasNext()) {
			vSpriteIterator.next().step();
		}
	}

	/**
	 * The paintComponent() method which is called from the event
	 * dispatch thread whenever the GUI wants to repaint an
	 * Animator component.
	 */
	public synchronized void paintComponent(Graphics pGraphics) {
		Iterator<Sprite> vSpriteIterator = mSprites.iterator();

		pGraphics.clearRect(0, 0, getWidth(), getHeight());

		while (vSpriteIterator.hasNext()) {
			vSpriteIterator.next().paint(pGraphics);
		}
	}

	/**
	 * The run() method of an Animator thread simply loops
	 * calling step() to update the positions of all sprites
	 * and then calling repaint() to inform the event dispatch
	 * thread that it needs to paint the next frame.
	 * 
	 * We pause between each frame for FRAME_PAUSE milliseconds,
	 * so decreasing FRAME_PAUSE we can increase the framerate (and
	 * thus the speed) of the animation.
	 */
	public void run() {
		while (true) {
			step();
			repaint();
			
			try {
				Thread.sleep(FRAME_PAUSE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
