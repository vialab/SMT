//imports
import java.awt.Rectangle;
import vialab.SMT.*;
import vialab.SMT.util.*;

//vars
int window_width = 1200;
int window_height = 800;
SystemAdapter adapter = null;

void setup(){
	size( window_width, window_height, SMT.RENDERER);
	frame.setResizable( true);
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setTrailEnabled( true);
	SMT.setTouchSourceBoundsActiveDisplay( TouchSource.MOUSE);
	adapter = SMT.getSystemAdapter();
	adapter.update();

	int display_count = adapter.getDisplayCount();
	Rectangle screen_bounds = adapter.getScreenBounds();
	System.out.printf(
		"display count: %d\n",
		display_count);
	System.out.printf(
		"screen bounds: %d, %d, %d, %d\n",
		screen_bounds.x, screen_bounds.y,
		screen_bounds.width, screen_bounds.height);
}

void draw(){
	//update info
	adapter.update();

	//draw background
	background( 30);

	//draw test ret
	pushStyle();
	fill( 240, 240, 240, 180);
	noStroke();
	rect( 10, 10, 100, 100);
	popStyle();
}

void update(){}