//standard library imports
import java.awt.Point;
import java.util.Vector;

//SMT library imports
import vialab.SMT.*;

//constants
int display_width = 1200;
int display_height = 800;
int display_halfWidth;
int display_halfHeight;
int fps_limit = 60;

//main functions
void setup(){
	//variable setup
	display_halfWidth = display_width / 2;
	display_halfHeight = display_height / 2;
	
	//processing window setup
	frameRate( fps_limit);
	size( display_width, display_height, P3D);
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setTouchDraw( TouchDraw.TEXTURED);

	//load zones
	SMT.add( new MyZone( this));
}

void draw(){
	background( 0);
}

//custom classes
class MyZone extends Zone {
	public MyZone( PApplet parent){
		super( "MyZone", 0, 0, 100, 100);
		PShape s = parent.createShape();
	}
}