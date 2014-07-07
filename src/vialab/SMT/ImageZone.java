/*
	Simple Multi-Touch Library
	Copyright 2011
	Erik Paluka, Christopher Collins University of Ontario Institute of Technology
	Mark Hancock University of Waterloo
	
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
 **/
package vialab.SMT;

import processing.core.PImage;

/**
 * This is a rectangular zone that displays the image given to it.
 * 
 * @author Erik Paluka
 * @version 1.0
 **/
public class ImageZone extends Zone {
	/** The PImage that contains the image passed by the user **/
	private PImage image;
	/** The tint colour of the image zone **/
	private int tint = 0xFFFFFFFF;


	/**
	 * ImageZone constructor, creates a rectangular zone and draws a PImage to it.
	 * 
	 * @param name String The name of the zone
	 * @param image PImage The PImage that will be drawn to the zone's coordinates.
	 * @param x int X-coordinate of the upper left corner of the zone
	 * @param y int Y-coordinate of the upper left corner of the zone
	 * @param width int Width of the zone
	 * @param height int Height of the zone
	 **/
	public ImageZone( String name, PImage image,
			int x, int y, int width, int height) {
		super( name, x, y, width, height);
		this.image = image;
	}

	/**
	 * ImageZone constructor, creates a rectangular zone and draws a PImage to it.
	 * 
	 * @param name String The name of the zone
	 * @param image PImage The PImage that will be drawn to the zone's coordinates.
	 * @param x int X-coordinate of the upper left corner of the zone
	 * @param y int Y-coordinate of the upper left corner of the zone
	 * @param width int Width of the zone
	 * @param height int Height of the zone
	 * @param tint int The image will be tinted by this colour
	 **/
	public ImageZone( String name, PImage image,
			int x, int y, int width, int height, int tint) {
		this( name, image, x, y, width, height);
		this.tint = tint;
	}

	/**
	 * ImageZone constructor, creates a rectangular zone and draws a PImage to it.
	 * 
	 * @param image PImage The PImage that will be drawn to the zone's coordinates.
	 * @param x int X-coordinate of the upper left corner of the zone
	 * @param y int Y-coordinate of the upper left corner of the zone
	 * @param width int Width of the zone
	 * @param height int Height of the zone
	 **/
	public ImageZone( PImage image, int x, int y, int width, int height) {
		this( null, image, x, y, width, height);
	}

	/**
	 * ImageZone constructor, creates a rectangular zone and draws a PImage to it.
	 * @param image PImage The PImage that will be drawn to the zone's coordinates.
	 **/
	public ImageZone( PImage image) {
		this( image, 0, 0, image.width, image.height);
	}

	/**
	 * ImageZone constructor, creates a rectangular zone and draws a PImage to it.
	 * 
	 * @param name String The name of the zone
	 * @param image PImage The PImage that will be drawn to the zone's coordinates.
	 **/
	public ImageZone( String name, PImage image) {
		this( name, image, 0, 0, image.width, image.height);
	}

	/**
	 * ImageZone constructor. Creates a rectangular zone and draws a PImage to
	 * it. The width and height of the zone is set to the PImage's width and
	 * height.
	 * 
	 * @param image PImage The PImage that will be drawn to the zone's coordinates.
	 * @param x int X-coordinate of the upper left corner of the zone
	 * @param y int Y-coordinate of the upper left corner of the zone
	 **/
	public ImageZone( PImage image, int x, int y) {
		this( image, x, y, image.width, image.height);
	}

	/**
	 * ImageZone constructor. Creates a rectangular zone and draws a PImage to it. The width and height of the zone is set to the PImage's width and height.
	 * 
	 * @param name String The name of the zone
	 * @param image PImage The PImage that will be drawn to the zone's coordinates.
	 * @param x int X-coordinate of the upper left corner of the zone
	 * @param y int Y-coordinate of the upper left corner of the zone
	 **/
	public ImageZone( String name, PImage image, int x, int y) {
		this( name, image, x, y, image.width, image.height);
	}

	/**
	 * Pass a created ImageZone to 'clone' it.
	 * @param imageZone ImageZone The ImageZone that is copied.
	 **/
	public ImageZone( ImageZone imageZone) {
		this( imageZone.name, imageZone.image,
			imageZone.x, imageZone.y, imageZone.width, imageZone.height);
	}
	
	/**
	 * This creates an ImageZone from a URL
	 * @param url The URL of an image
	 **/
	public ImageZone( String url) {
		this( SMT.getApplet().loadImage( url));
	}

	/** Used to override what is drawn into the zone **/
	@Override
	public void drawImpl() {
		tint( tint);
		image( image, 0, 0, this.width, this.height);
	}

	//get accessor methods
	/**
	 * Get the image being drawn by this zone
	 * @return the image being drawn by this zone
	 **/
	public PImage getZoneImage(){
		return image;
	}
	/**
	 * Get the tint of the image drawn by this zone
	 * @return the tint being used by this zone
	 **/
	public int getTint(){
		return tint;
	}

	//set accessor methods
	/**
	 * Set the image being drawn by this zone
	 * @param image the image currently that should be drawn by this zone
	 **/
	public void setZoneImage( PImage image){
		this.image = image;
	}
	/**
	 * Set the tint of the image drawn by this zone
	 * @param tint the tint used by this zone
	 **/
	public void setTint( int tint){
		this.tint = tint;
	}

	//disabled warnings
	boolean warnDraw() {
		return false;
	}
}
