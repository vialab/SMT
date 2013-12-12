//standard library imports
import java.util.Vector;

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
	//processing library setup
	frameRate( fps_limit);
	size( display_width, display_height, P3D);
	SMT.init( this, vialab.SMT.TouchSource.MULTIPLE);
}

void draw(){
	//draw background
	noStroke();
	fill( 0, 0, 0);
	rect( 0, 0, display_width, display_height);
}

void touchDown( Touch touch){
	System.out.printf(
		"Touch Down, ID: %d X: %d Y: %d Source: %s\n",
		touch.cursorID, touch.x, touch.y, touch.getTouchSource());
}

void touch(){
	for( Touch touch : SMT.getTouches())
		System.out.printf(
			"Touch Moved, ID: %d X: %d Y: %d Source: %s\n",
			touch.cursorID, touch.x, touch.y, touch.getTouchSource());
}

void touchUp( Touch touch){
	System.out.printf(
		"Touch Up, ID: %d X: %d Y: %d Source: %s\n",
		touch.cursorID, touch.x, touch.y, touch.getTouchSource());
}
