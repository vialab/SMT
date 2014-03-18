//standard library imports
import java.util.Vector;

//SMT library imports
import vialab.SMT.*;

//constants
int window_width = 1200;
int window_height = 800;
int window_halfWidth;
int window_halfHeight;
int fps_limit = 60;

//main functions
void setup(){
	window_halfWidth = window_width / 2;
	window_halfHeight = window_height / 2;
	//processing library setup
	frameRate( fps_limit);
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.MULTIPLE);
}

void draw(){
	//draw background
	noStroke();
	fill( 0, 0, 0);
	rect( 0, 0, window_width, window_height);
}


void touch(){
	for( Touch touch : SMT.getTouches())
		System.out.printf(
			"Touch Moved, ID: %d X: %d Y: %d Source: %s\n",
			touch.cursorID, touch.x, touch.y, touch.getTouchSource());
}

void touchDown( Touch touch){
	System.out.printf(
		"Touch Down, ID: %d X: %d Y: %d Source: %s\n",
		touch.cursorID, touch.x, touch.y, touch.getTouchSource());
}

void touchUp( Touch touch){
	System.out.printf(
		"Touch Up, ID: %d X: %d Y: %d Source: %s\n",
		touch.cursorID, touch.x, touch.y, touch.getTouchSource());
}
