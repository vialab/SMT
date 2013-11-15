
//SMT library imports
import vialab.SMT.*;

//constants
int display_width = 1200;
int display_height = 800;
int fps_limit = 60;
int puck_count = 10;
//other
int display_halfWidth;
int display_halfHeight;

//main functions
void setup(){
	display_halfWidth = display_width / 2;
	display_halfHeight = display_height / 2;
	//processing library setup
	frameRate( fps_limit);
	size( display_width, display_height, P3D);
	//smt library setup
	SMT.init( this, TouchSource.MULTIPLE);
	SMT.add( enemy);
}

void draw(){
	fill( 0, 0, 0);
	//draw background
	rect( 0, 0, display_width, display_height);
}
