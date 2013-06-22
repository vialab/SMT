/*
  Simple Multitouch Library
  Copyright 2011
  Erik Paluka, Christopher Collins - University of Ontario Institute of Technology
  Mark Hancock - University of Waterloo
  
  Parts of this library are based on:
  TUIOZones http://jlyst.com/tz/
         
  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public
  License Version 3 as published by the Free Software Foundation.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  General Public License for more details.

  You should have received a copy of the GNU General
  Public License along with this library; if not, write to the
  Free Software Foundation, Inc., 59 Temple Place, Suite 330,
  Boston, MA  02111-1307  USA
 */
package vialab.SMT;

import processing.core.PImage;

/**
 * This is a rectangular zone that displays the image given to it.
 * 
 * @author Erik Paluka
 * @version 1.0
 */
public class ImageZone extends Zone {
	/** The PImage that contains the image passed by the user */
	public PImage img;

	/**
	 * Default tint colour of image is opaque white, as to not change appearance
	 */
	public int tintColour = 0xFFFFFFFF;

	boolean warnDraw() {
		return false;
	}

	/**
	 * ImageZone constructor, creates a rectangular zone and draws a PImage to
	 * it.
	 * 
	 * @param name
	 *            The name of the zone, used for the drawZoneName() and
	 *            touchZoneName(), etc methods
	 * 
	 * @param img
	 *            PImage - The PImage that will be drawn to the zone's
	 *            coordinates.
	 * @param x
	 *            int - X-coordinate of the upper left corner of the zone
	 * @param y
	 *            int - Y-coordinate of the upper left corner of the zone
	 * @param width
	 *            int - Width of the zone
	 * @param height
	 *            int - Height of the zone
	 */
	public ImageZone(String name, PImage img, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		this.img = img;

		// beginDraw();
		// image(img, 0, 0, this.width, this.height);
		// endDraw();
	}

	/**
	 * ImageZone constructor, creates a rectangular zone and draws a PImage to
	 * it.
	 * 
	 * @param name
	 *            The name of the zone, used for the drawZoneName() and
	 *            touchZoneName(), etc methods
	 * 
	 * @param img
	 *            PImage - The PImage that will be drawn to the zone's
	 *            coordinates.
	 * @param x
	 *            int - X-coordinate of the upper left corner of the zone
	 * @param y
	 *            int - Y-coordinate of the upper left corner of the zone
	 * @param width
	 *            int - Width of the zone
	 * @param height
	 *            int - Height of the zone
	 * @param tintColour
	 *            int - colour the image will be tinted by
	 */
	public ImageZone(String name, PImage img, int x, int y, int width, int height, int tintColour) {
		this(name, img, x, y, width, height);
		this.tintColour = tintColour;
	}

	public ImageZone(PImage img, int x, int y, int width, int height) {
		this(null, img, x, y, width, height);
	}

	public ImageZone(PImage img) {
		this(img, 0, 0, img.width, img.height);
	}

	public ImageZone(String name, PImage img) {
		this(name, img, 0, 0, img.width, img.height);
	}

	/**
	 * ImageZone constructor. Creates a rectangular zone and draws a PImage to
	 * it. The width and height of the zone is set to the PImage's width and
	 * height.
	 * 
	 * @param img
	 *            PImage - The PImage that will be drawn to the zone's
	 *            coordinates.
	 * @param x
	 *            int - X-coordinate of the upper left corner of the zone
	 * @param y
	 *            int - Y-coordinate of the upper left corner of the zone
	 */
	public ImageZone(PImage img, int x, int y) {
		this(img, x, y, img.width, img.height);
	}

	public ImageZone(String name, PImage img, int x, int y) {
		this(name, img, x, y, img.width, img.height);
	}

	public ImageZone(ImageZone imgZone) {
		this(imgZone.name, imgZone.img, imgZone.x, imgZone.y, imgZone.width, imgZone.height);
	}

	@Override
	public void drawImpl() {
		tint(tintColour);
		image(img, 0, 0, this.width, this.height);
	}

}
