//standard library imports
import java.awt.Point;
import java.util.Vector;

//SMT library imports
import vialab.SMT.*;

//constants
int display_width = 1200;
int display_height = 800;
int fps_limit = 60;
//other
int display_halfWidth;
int display_halfHeight;

//main functions
void setup(){
	display_halfWidth = display_width / 2;
	display_halfHeight = display_height / 2;
	//processing window setup
	frameRate( fps_limit);
	size( display_width, display_height, P3D);
	SMT.init( this, TouchSource.MOUSE);
	SMT.setTouchDraw( TouchDraw.TEXTURED);
}

void draw(){
	background( 0);
}

void touch(){
	Touch touch = SMT.getTouches()[0];
	for( Point point : touch.getPathPoints());
}
