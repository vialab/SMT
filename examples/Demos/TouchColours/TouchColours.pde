//SMT library imports
import vialab.SMT.*;

//constants
int display_width = 1200;
int display_height = 800;
int display_halfWidth;
int display_halfHeight;
int fps_limit = 60;
//other


//main functions
void setup(){
	display_halfWidth = display_width / 2;
	display_halfHeight = display_height / 2;
	//processing window setup
	frameRate( fps_limit);
	size( display_width, display_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setTrailEnabled( true);

	//customize some of touch drawing options
	// make the touch a bit smaller
	SMT.setTouchRadius( 15);
	// set the default colours to black
	SMT.setTouchColour( 30, 30, 30, 220);
	SMT.setTrailColour( 30, 30, 30, 220);
	// make the touch a bit smaller
	SMT.setTrailT_N( 20);
	// make the touch a bit smaller
	SMT.setTrailPointThreshold( 10);

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
	SMT.add( trail_blue);
	SMT.add( trail_pink);
	SMT.add( trail_aqua);
	SMT.add( trail_black);
	SMT.add( trail_ghost);
	SMT.add( touch_blue_global);
	SMT.add( touch_pink_global);
	SMT.add( touch_aqua_global);
	SMT.add( touch_black_global);
	SMT.add( touch_ghost_global);
	SMT.add( trail_blue_global);
	SMT.add( trail_pink_global);
	SMT.add( trail_aqua_global);
	SMT.add( trail_black_global);
	SMT.add( trail_ghost_global);
}

void draw(){
	//draw background
	background( 50, 50, 50);
	pushStyle();
	fill( 240, 240, 240, 180);
	textSize( 25);
	textMode( SHAPE);
	textAlign( LEFT, CENTER);
	text( "Touch to set individual touch colour", 50, 15);
	text( "Touch to set global touch colour", 50, 140);
	text( "Touch to set individual trail colour", 50, 530);
	text( "Touch to set global trail colour", 50, 670);
	popStyle();
}

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
	}
	//draw method
	public void drawImpl(){
		pushStyle();
		noStroke();
		fill( colour_red, colour_green, colour_blue, colour_alpha);
		rect( 0, 0, this.getWidth(), this.getHeight(), 5);
		popStyle();
	}
	public void touchImpl(){}
	public void pressImpl( Touch touch){}
}

private class TouchColourSetter extends ColourSetter {
	public TouchColourSetter( int x, int y, int width, int height,
			int colour_red, int colour_green, int colour_blue, int colour_alpha){
		super( x, y, width, height,
			colour_red, colour_green, colour_blue, colour_alpha);
	}
	//touch method
	public void touchImpl(){
		 Touch touch = getActiveTouch( 0);
		touch.setTint(
			colour_red, colour_green, colour_blue, colour_alpha);
	}
}
private class TrailColourSetter extends ColourSetter {
	public TrailColourSetter( int x, int y, int width, int height,
			int colour_red, int colour_green, int colour_blue, int colour_alpha){
		super( x, y, width, height,
			colour_red, colour_green, colour_blue, colour_alpha);
	}
	//touch method
	public void touchImpl(){
		 Touch touch = getActiveTouch( 0);
		touch.setTrailTint(
			colour_red, colour_green, colour_blue, colour_alpha);
	}
}

private class GlobalTouchColourSetter extends ColourSetter {
	public GlobalTouchColourSetter( int x, int y, int width, int height,
			int colour_red, int colour_green, int colour_blue, int colour_alpha){
		super( x, y, width, height,
			colour_red, colour_green, colour_blue, colour_alpha);
	}
	//touch method
	public void touchImpl(){
		 Touch touch = getActiveTouch( 0);
		SMT.setTouchColour(
			colour_red, colour_green, colour_blue, colour_alpha);
	}
}
private class GlobalTrailColourSetter extends ColourSetter {
	public GlobalTrailColourSetter( int x, int y, int width, int height,
			int colour_red, int colour_green, int colour_blue, int colour_alpha){
		super( x, y, width, height,
			colour_red, colour_green, colour_blue, colour_alpha);
	}
	//touch method
	public void touchImpl(){
		 Touch touch = getActiveTouch( 0);
		SMT.setTrailColour(
			colour_red, colour_green, colour_blue, colour_alpha);
	}
}


