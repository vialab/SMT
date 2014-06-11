//standard library imports
import java.awt.Point;
import java.util.Vector;

//TUIO library imports
import TUIO.*;

//SMT library imports
import vialab.SMT.*;

//constants
boolean window_fullscreen = false;
int window_width = 1200;
int window_height = 800;
int window_halfWidth;
int window_halfHeight;
int fps_limit = 60;
//other
PImage trail_texture;
boolean drawrawtrail = false;
boolean drawrawpoints = false;
boolean drawnnt = false;
float trail_width = 5.0;

//main functions
void setup(){
	if( window_fullscreen){
		window_width = displayWidth;
		window_height = displayHeight;
	}
	window_halfWidth = window_width / 2;
	window_halfHeight = window_height / 2;
	//processing window setup
	frameRate( fps_limit);
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//set touch drawing options
	SMT.setTouchRadius( 10);
	SMT.setTouchColour( 20, 20, 20, 255);
	SMT.setTrailColour( 20, 20, 20, 180);
	//SMT.setTrailC( 0.1f);

	//load texture
	trail_texture = loadImage("resources/trail_texture.png");
}

void draw(){
	//draw background
	background( 255, 255, 255);

	//draw trails
	if( drawrawtrail) drawRawTrails();
	if( drawrawpoints) drawRawPoints();
}

void drawRawTrails(){
	Touch[] touches = SMT.getTouches();
	for( Touch touch : touches){
		//get first touch data
		Point[] points = touch.getPathPoints();
		int point_count = min( 25, points.length);
		//create the interpolation curve
		//set up drawing parameters
		stroke( 100, 100, 100, 150);
		noFill();
		beginShape();

		for( int i = 1 ; i <= point_count; i++){
			Point point = points[ points.length - i];
			curveVertex( point.x, point.y);
		}
		endShape();
	}
}

void drawRawPoints(){
	Touch[] touches = SMT.getTouches();
	for( Touch touch : touches){
		Point[] points = touch.getPathPoints();
		int point_count = min( 25, points.length);
		//set up drawing parameters
		int radius = 15;
		stroke( 100, 100, 100, 150);
		noFill();

		for( int i = 1 ; i <= point_count; i++){
			Point point = points[ points.length - i];
			ellipse( point.x, point.y, radius, radius);
		}
	}
}

void keyPressed(){
	//println( key);
	switch( key){
		case 'a':{
			drawrawtrail = ! drawrawtrail;
			break;}
		case 's':{
			drawrawpoints = ! drawrawpoints;
			break;}
		case 'd':{
			SMT.setTrailEnabled( ! SMT.getTrailEnabled());
			break;}
		default: break;
	}
}
