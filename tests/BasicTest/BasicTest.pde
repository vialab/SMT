//standard library imports
import java.util.Set;
import java.util.HashSet;

//tuio imports
import TUIO.TuioObject;

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
	println("SMT.init finished");
}

void draw(){
	//draw background
	background( 80, 80, 80);
	/*pushStyle();
	rectMode(CENTER);
	for( TuioObject object : SMT.getTuioObjects()) {
		float x = object.getX();
		float y = object.getY();
		float theta = object.getAngle();
		pushMatrix();
		translate( x * display_width, y * display_height);
		rotate( theta);
		rect( 0, 0, 40, 40);
		popMatrix();
	}
	popStyle();*/
}
