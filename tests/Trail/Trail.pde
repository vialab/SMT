//standard library imports
import java.awt.Point;
import java.util.Vector;

//TUIO library imports
import TUIO.*;

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
int path_points = 15;
int time_cutoff = 2000;
int trail_sections = 20;
boolean drawrawtrail = true;
boolean drawrawpoints = true;
boolean drawnnt = true;

//main functions
void setup(){
	display_halfWidth = display_width / 2;
	display_halfHeight = display_height / 2;
	//processing window setup
	frameRate( fps_limit);
	size( display_width, display_height, P3D);
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setTouchDraw( TouchDraw.TEXTURED);
	SMT.setTouchRadius( 15);

	//load texture
	tex = loadImage("resources/trail_texture.png");
}

void draw(){
	//draw background
	background( 0);

	//draw trails
	if( drawrawtrail) drawRawTrails();
	if( drawrawpoints) drawRawPoints();
	if( drawnnt) drawNearestNeighbourTrail();
}

void drawNearestNeighbourTrail(){
	int radius = 15;
	stroke( 230, 230, 150, 200);
	strokeWeight( 3);
	noFill();

	TuioTime sessionTime = TuioTime.getSessionTime();
	
	long currentTime = sessionTime.getTotalMilliseconds();
	Touch[] touches = SMT.getTouches();
	for( Touch touch : touches){
		Vector<Point> points = new Vector<Point>();
		for( int i = touch.path.size() - 1; i >= 0; i--){
			long millis = touch.path.get( i).getTuioTime().getTotalMilliseconds();
			System.out.printf(
				"sess, start, sys, touch: %d, %d\n",
				currentTime, millis);
			//if( 
		}

		float distance_threshold = 0.2;
		float c = 0.2;
		int t_n = 25;
		int point_n = min( points.size(), path_points);
		float dt = (float) 1 / t_n;
		beginShape();
		for( int t_i = 0; t_i <= t_n; t_i++){
			float t = dt * t_i;
			float x = 0;
			float y = 0;
			float sum = 0;
			for( int point_i = 0; point_i < points.size(); point_i++){
				Point point = points.get( points.size() - point_i - 1);
				float p_t = (float) point_i / point_n;
				float distance = p_t - t;
				//if( distance > distance_threshold) continue;
				float w = expweight( distance, c);
				x += w * point.x;
				y += w * point.y;
				sum += w;
			}
			if( sum == 0) break;
			x /= sum;
			y /= sum;
			//ellipse( x, y, radius, radius);
			curveVertex( x, y);
		}
		endShape();
	}
}


float expweight( float distance, float c){
	return exp( - pow( distance / c, 2) / 2);
}

void drawRawTrails(){
	Touch[] touches = SMT.getTouches();
	for( Touch touch : touches){
		//get first touch data
		Point[] points = touch.getPathPoints();
		int point_count = min( path_points, points.length);
		//create the interpolation curve
		//set up drawing parameters
		stroke( 255, 255, 255, 150);
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
		int radius = 15;
		//get first touch data
		Point[] points = touch.getPathPoints();
		int point_count = min( path_points, points.length);
		//create the interpolation curve
		//set up drawing parameters
		stroke( 255, 255, 255, 150);
		noFill();

		for( int i = 1 ; i <= point_count; i++){
			Point point = points[ points.length - i];
			ellipse( point.x, point.y, radius, radius);
		}
	}
}

void keyPressed(){
	println( key);
	switch( key){
		case 'a':{
			drawrawtrail = ! drawrawtrail;
			break;}
		case 's':{
			drawrawpoints = ! drawrawpoints;
			break;}
		case 'd':{
			drawnnt = ! drawnnt;
			break;}
		default: break;
	}
}