//standard library imports


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
	size( display_width, display_height, P3D);
	SMT.init( this, TouchSource.AUTOMATIC);

	//create zones
	ColourSetter touch_blue = new TouchColourSetter( 50, 100, 100, 100,
		100, 100, 150, 200);
	ColourSetter touch_pink = new TouchColourSetter( 200, 100, 100, 100,
		230, 200, 200, 200);
	ColourSetter touch_aqua = new TouchColourSetter( 350, 100, 100, 100,
		100, 150, 150, 200);
	ColourSetter touch_black = new TouchColourSetter( 500, 100, 100, 100,
		10, 10, 10, 200);
	ColourSetter touch_ghost = new TouchColourSetter( 650, 100, 100, 100,
		250, 250, 250, 50);
	ColourSetter trail_blue = new TrailColourSetter( 50, 300, 100, 100,
		100, 100, 150, 200);
	ColourSetter trail_pink = new TrailColourSetter( 200, 300, 100, 100,
		230, 200, 200, 200);
	ColourSetter trail_aqua = new TrailColourSetter( 350, 300, 100, 100,
		100, 150, 150, 200);
	ColourSetter trail_black = new TrailColourSetter( 500, 300, 100, 100,
		10, 10, 10, 200);
	ColourSetter trail_ghost = new TrailColourSetter( 650, 300, 100, 100,
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
}

void draw(){
	//draw background
	background( 50, 50, 50);
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
}

private class TouchColourSetter extends ColourSetter {
	public TouchColourSetter( int x, int y, int width, int height,
			int colour_red, int colour_green, int colour_blue, int colour_alpha){
		super( x, y, width, height,
			colour_red, colour_green, colour_blue, colour_alpha);
	}
	//touch method
	public void touchDownImpl( Touch touch){
		SMT.setTouchColour(
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
	public void touchDownImpl( Touch touch){
		SMT.setTrailColour(
			colour_red, colour_green, colour_blue, colour_alpha);
	}
}


