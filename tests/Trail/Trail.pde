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
//other
PImage tex;
int path_points = 10;
int trail_sections = 20;

//main functions
void setup(){
	display_halfWidth = display_width / 2;
	display_halfHeight = display_height / 2;
	//processing window setup
	frameRate( fps_limit);
	size( display_width, display_height, P3D);
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setTouchDraw( TouchDraw.TEXTURED);

	//load texture and texture options
	tex = loadImage("resources/ripple_texture.png");

	//other

}

void draw(){
	background( 0);
	Touch[] touches = SMT.getTouches();
	if( touches.length > 0){
		Touch touch = touches[0];
		Point[] points = touch.getPathPoints();
		int point_count = min( path_points, points.length);
		//create the interpolation curve
		stroke( 255);
		noFill();
		beginShape();
		for( int i = 1 ; i <= point_count; i++){
			Point point = points[ points.length - i];
			curveVertex( point.x, point.y);
		}
		endShape();
	}
}
void touch(){
	System.out.printf(
		"%d\n",
		SMT.getTouches()[0].currentTime
			.getSessionTime().getTotalMilliseconds());
}