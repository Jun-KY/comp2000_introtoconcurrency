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

package org.macquarie.head;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;


/**
 * <p>This class is used to represent simple "sprites", that is
 * the animatable images that make up an animated image.</p>
 * 
 * <p>In this case, all sprites are drawn as instances of the same
 * image. A sprite knows its current location and its velocity.
 * This latter information tells the sprite the direction it should
 * move in and the speed at which it will move. This is expressed
 * pixel increments or decrements in the x and y directions.</p>
 * 
 * <p>Each sprite knows how to update it location and velocity from
 * one frame to the next (the <code>step()</code> method) and how to 
 * draw itself on a Graphics object (the <code>paint()</code> method).</p>
 * 
 * @author Dominic Verity
 *
 */

public class Sprite {

	private static Random mGenerator = new Random();
	private int mXPos, mYPos, mXVel, mYVel;
	
	private static Image mHead = null;
	
	/**
	 * All sprites share the same image object. This is managed as
	 * a lazily loaded singleton via this method.
	 * 
	 * @return the BufferedImage object which contains our sprite image.
	 */
	public static Image getHead() {
		if (mHead == null) {
			mHead = new ImageIcon(Sprite.class.getResource("images/dijkstra.jpg")).getImage();
		}
		return mHead;
	}
	
	public static int getHeadWidth() {
		return getHead().getWidth(null);
	}
	
	public static int getHeadHeight() {
		return getHead().getHeight(null);
	}

	/**
	 * The default constructor, gives the new sprite a randomly
	 * generated location and velocity.
	 */
	public Sprite() {
		mXPos = Math.abs(mGenerator.nextInt()) % 
			(Animator.CANVAS_WIDTH - getHeadWidth());
		mYPos = Math.abs(mGenerator.nextInt()) % 
			(Animator.CANVAS_HEIGHT - getHeadHeight());
		mXVel = mGenerator.nextInt() % 3;
		mYVel = mGenerator.nextInt() % 3;
	}

	/**
	 * This method can be called to update the location and
	 * velocity of our sprite from frame to frame. It handles
	 * collision with the boundary of the animation space by
	 * reflecting the sprites velocity at the boundary it has
	 * hit. 
	 * 
	 * That is if it hits a 
	 * . vertical boundary it negates the sprite's horizontal velocity,
	 * . horizontal bondary it negates the sprite's vertical velocity.
	 */
	public void step() {
		mXPos += mXVel;
		if (mXPos <= 0 || 
				mXPos >= Animator.CANVAS_WIDTH - getHeadWidth())
			mXVel = -mXVel;

		mYPos += mYVel;
		if (mYPos <= 0 ||
				mYPos >= Animator.CANVAS_HEIGHT - getHeadHeight())
			mYVel = -mYVel;
	}
	
	/**
	 * A very simple paint() method - simply draws the sprite image
	 * at the sprites current location on the canvas.
	 * 
	 * @param pGraphics the Graphics object on which the sprite image
	 *                  is to be drawn.
	 */
	public void paint(Graphics pGraphics) {
		pGraphics.drawImage(getHead(), mXPos, mYPos, null);
	}
}
