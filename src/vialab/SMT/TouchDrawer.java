package vialab.SMT;

import processing.core.PGraphics;

/**
 * A interface for objects that visualize touches
 */
public interface TouchDrawer {
	/**
	 * Draws the given touches on the given PGraphics object
	 * @param touches A collection containing all the touches on the system
	 * @param graphics The graphics object upon which to draw
	 */
	public void draw( Iterable<Touch> touches, PGraphics graphics);
}