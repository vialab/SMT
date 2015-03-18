/**
 * Demo of touch colours and other touch visualization customization features
 */

//SMT library imports
import vialab.SMT.*;

//constants
boolean window_fullscreen = false;
int window_width = 1200;
int window_height = 800;
int window_halfWidth;
int window_halfHeight;
int fps_limit = 60;
//other


//main functions
void setup(){
	if( window_fullscreen){
		window_width = displayWidth;
		window_height = displayHeight;
	}
	window_halfWidth = window_width / 2;
	window_halfHeight = window_height / 2;
	//processing window setup
	frameRate( fps_limit);
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setTrailEnabled( true);

	//if you want to, you can try one of the other touch drawing methods
	//SMT.setTouchDraw( TouchDraw.SMOOTH);
	//SMT.setTouchDraw( TouchDraw.DEBUG);
	//SMT.setTouchDraw( TouchDraw.NONE);

	//customize some of touch drawing options
	// make the touch a bit smaller (default: 15)
	SMT.setTouchRadius( 10);
	// set the global colours to light green
	SMT.setTouchColour( 140, 180, 140, 220);
	SMT.setTrailColour( 140, 180, 140, 220);
	// make touches fade away for twice as long (default: 250 ms)
	SMT.setTouchFadeDuration( 500);

	//create zones
	//touch colour setters
	ColourSetter touch_blue = new TouchColourSetter( 50, 40, 80, 80,
		100, 100, 150, 200);
	ColourSetter touch_pink = new TouchColourSetter( 200, 40, 80, 80,
		230, 200, 200, 200);
	ColourSetter touch_aqua = new TouchColourSetter( 350, 40, 80, 80,
		100, 150, 150, 200);
	ColourSetter touch_black = new TouchColourSetter( 500, 40, 80, 80,
		10, 10, 10, 200);
	ColourSetter touch_ghost = new TouchColourSetter( 650, 40, 80, 80,
		250, 250, 250, 50);
	//global touch colour setters
	ColourSetter touch_blue_global = new GlobalTouchColourSetter( 50, 165, 80, 80,
		100, 100, 150, 200);
	ColourSetter touch_pink_global = new GlobalTouchColourSetter( 200, 165, 80, 80,
		230, 200, 200, 200);
	ColourSetter touch_aqua_global = new GlobalTouchColourSetter( 350, 165, 80, 80,
		100, 150, 150, 200);
	ColourSetter touch_black_global = new GlobalTouchColourSetter( 500, 165, 80, 80,
		10, 10, 10, 200);
	ColourSetter touch_ghost_global = new GlobalTouchColourSetter( 650, 165, 80, 80,
		250, 250, 250, 50);
	//trail colour setters
	ColourSetter trail_blue = new TrailColourSetter( 50, 560, 80, 80,
		100, 100, 150, 200);
	ColourSetter trail_pink = new TrailColourSetter( 200, 560, 80, 80,
		230, 200, 200, 200);
	ColourSetter trail_aqua = new TrailColourSetter( 350, 560, 80, 80,
		100, 150, 150, 200);
	ColourSetter trail_black = new TrailColourSetter( 500, 560, 80, 80,
		10, 10, 10, 200);
	ColourSetter trail_ghost = new TrailColourSetter( 650, 560, 80, 80,
		250, 250, 250, 50);
	//global trail colour setters
	ColourSetter trail_blue_global = new GlobalTrailColourSetter( 50, 700, 80, 80,
		100, 100, 150, 200);
	ColourSetter trail_pink_global = new GlobalTrailColourSetter( 200, 700, 80, 80,
		230, 200, 200, 200);
	ColourSetter trail_aqua_global = new GlobalTrailColourSetter( 350, 700, 80, 80,
		100, 150, 150, 200);
	ColourSetter trail_black_global = new GlobalTrailColourSetter( 500, 700, 80, 80,
		10, 10, 10, 200);
	ColourSetter trail_ghost_global = new GlobalTrailColourSetter( 650, 700, 80, 80,
		250, 250, 250, 50);

	//add zones
	SMT.add( touch_blue);
	SMT.add( touch_pink);
	SMT.add( touch_aqua);
	SMT.add( touch_black);
	SMT.add( touch_ghost);
	SMT.add( touch_blue_global);
	SMT.add( touch_pink_global);
	SMT.add( touch_aqua_global);
	SMT.add( touch_black_global);
	SMT.add( touch_ghost_global);
	SMT.add( trail_blue);
	SMT.add( trail_pink);
	SMT.add( trail_aqua);
	SMT.add( trail_black);
	SMT.add( trail_ghost);
	SMT.add( trail_blue_global);
	SMT.add( trail_pink_global);
	SMT.add( trail_aqua_global);
	SMT.add( trail_black_global);
	SMT.add( trail_ghost_global);
}

void draw(){
	//draw background
	background( 50, 50, 50);
	//drawFrameRate();
	pushStyle();
	fill( 240, 240, 240, 180);
	textAlign( LEFT, CENTER);
	textMode( MODEL);
	textSize( 25);
	text( "Touch to set individual touch colour", 50, 15);
	text( "Touch to set global touch colour", 50, 140);
	text( "Touch to set individual trail colour", 50, 530);
	text( "Touch to set global trail colour", 50, 670);
	popStyle();
}
public void drawFrameRate(){
	float fps = this.frameRate;
	String fps_text = String.format( "fps: %.0f", fps);
	pushStyle();
	fill( 240, 240, 240, 180);
	textAlign( RIGHT, TOP);
	textMode( MODEL);
	textSize( 32);
	text( fps_text, window_width - 10, 10);
	popStyle();
}

//subclasses

//
private class ColourSetter extends Zone {
	protected int colour_red;
	protected int colour_green;
	protected int colour_blue;
	protected int colour_alpha;
	public ColourSetter( int x, int y, int width, int height,
			int colour_red, int colour_green, int colour_blue, int colour_alpha){
		super( x, y, width, height);
		this.colour_red = colour_red;
		this.colour_green = colour_green;
		this.colour_blue = colour_blue;
		this.colour_alpha = colour_alpha;
		this.setCaptureTouches( false);
	}
	//draw method
	public void draw(){
		pushStyle();
		noStroke();
		fill( colour_red, colour_green, colour_blue, colour_alpha);
		rect( 0, 0, this.getWidth(), this.getHeight(), 5);
		popStyle();
	}
	public void touch(){}
	//we define the press method so that touches will be unassigned when they 'exit' the zone.
	public void press( Touch touch){}
}

// when touched, zones of this class will set the colour of the touch that hit it to their colour
private class TouchColourSetter extends ColourSetter {
	public TouchColourSetter( int x, int y, int width, int height,
			int colour_red, int colour_green, int colour_blue, int colour_alpha){
		super( x, y, width, height,
			colour_red, colour_green, colour_blue, colour_alpha);
	}
	//touch method
	public void touch(){
		Touch touch = getActiveTouch( 0);
		touch.setTint(
			colour_red, colour_green, colour_blue, colour_alpha);
	}
}
// when touched, zones of this class will set the trail colour of the touch that hit it to their colour
private class TrailColourSetter extends ColourSetter {
	public TrailColourSetter( int x, int y, int width, int height,
			int colour_red, int colour_green, int colour_blue, int colour_alpha){
		super( x, y, width, height,
			colour_red, colour_green, colour_blue, colour_alpha);
	}
	//touch method
	public void touch(){
		Touch touch = getActiveTouch( 0);
		touch.setTrailTint(
			colour_red, colour_green, colour_blue, colour_alpha);
	}
}

// when touched, zones of this class will set the colour of all touches to their colour
private class GlobalTouchColourSetter extends ColourSetter {
	public GlobalTouchColourSetter( int x, int y, int width, int height,
			int colour_red, int colour_green, int colour_blue, int colour_alpha){
		super( x, y, width, height,
			colour_red, colour_green, colour_blue, colour_alpha);
	}
	//touch method
	public void touch(){
		SMT.setTouchColour(
			colour_red, colour_green, colour_blue, colour_alpha);
	}
}
// when touched, zones of this class will set the trail colour of all touches to their colour
private class GlobalTrailColourSetter extends ColourSetter {
	public GlobalTrailColourSetter( int x, int y, int width, int height,
			int colour_red, int colour_green, int colour_blue, int colour_alpha){
		super( x, y, width, height,
			colour_red, colour_green, colour_blue, colour_alpha);
	}
	//touch method
	public void touch(){
		SMT.setTrailColour(
			colour_red, colour_green, colour_blue, colour_alpha);
	}
}


