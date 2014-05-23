//imports
import java.awt.Rectangle;
import vialab.SMT.*;
import vialab.SMT.util.*;

//vars
int window_width = 1200;
int window_height = 800;
SystemAdapter adapter = null;
long last_update = -1;

//bounds
Rectangle[] display_bounds;
Rectangle screen_bounds;
Rectangle sketch_bounds;

void setup(){
	size( window_width, window_height, SMT.RENDERER);
	frame.setResizable( true);
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setTrailEnabled( true);
	adapter = SMT.getSystemAdapter();
}	

void draw(){
	//update info
	adapter.update();
	if( adapter.getLastUpdateTime() > last_update){
		last_update = adapter.getLastUpdateTime();
		display_bounds = adapter.getDisplayBounds();
		screen_bounds = adapter.getScreenBounds();
		sketch_bounds = adapter.getSketchBounds();
	}

	//draw background
	background( 30);

	//setup
	pushStyle();
	ortho();
	
	//draw screen

	//draw displays

	//draw sketch

	//cleanup
	popStyle();
}

void update(){}