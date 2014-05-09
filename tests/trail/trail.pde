//standard library imports
import java.awt.Point;
import java.util.Vector;

//TUIO library imports
import TUIO.*;

//SMT library imports
import vialab.SMT.*;

//constants
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
	window_halfWidth = window_width / 2;
	window_halfHeight = window_height / 2;
	//processing window setup
	frameRate( fps_limit);
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setTouchSourceBoundsActiveDisplay( TouchSource.MOUSE);
	//SMT.setTouchDraw( TouchDraw.NONE);
	SMT.setTouchDraw( TouchDraw.TEXTURED);
	SMT.setTouchRadius( 10);
	SMT.setTouchColour( 20, 20, 20, 255);
	SMT.setTrailColour( 20, 20, 20, 180);

	//load texture
	trail_texture = loadImage("resources/trail_texture.png");
}

void draw(){
	//draw background
	background( 255, 255, 255);

	//draw trails
	if( drawrawtrail) drawRawTrails();
	if( drawrawpoints) drawRawPoints();
	if( drawnnt) drawNearestNeighbourTrail();
}

void drawNearestNeighbourTrail(){
	TuioTime sessionTime = TuioTime.getSessionTime();
	long currentTime = sessionTime.getTotalMilliseconds();
	
	Touch[] touches = SMT.getTouches();
	for( Touch touch : touches){
		//drawing parameters
		int time_threshold = 500;
		boolean timeDistance_model = true;
		float distance_threshold = 0.2;
		boolean distance_threshold_enabled = false;
		int point_threshold = 60;
		float c = 0.05;
		int t_n = 30;

		//select points that are within the time threshold
		Vector<TuioPoint> points = selectPoints(
			touch, currentTime, time_threshold, point_threshold);
		//System.out.printf("interpolation points: %d\n", points.size());
		t_n += points.size();

		//convenience variables
		int point_n = points.size();
		if( point_n < 2) continue;
		float dt = (float) 1 / t_n;
		Vector<CurvePoint> curvePoints = new Vector<CurvePoint>();
		//for t_n points on the domain [ 0, 1]
		for( int t_i = 0; t_i <= t_n; t_i++){
			float t = dt * t_i;
			//average their position
			float x = 0;
			float y = 0;
			float sum = 0;
			for( int point_i = 0; point_i < point_n; point_i++){
				TuioPoint point = points.get( point_i);
				long pointTime = point.getTuioTime().getTotalMilliseconds();

				//locate the point in the domain
				float p_t = 
					timeDistance_model ?
						(float) ( currentTime - pointTime) / time_threshold :
						(float) point_i / point_n;
				//calculate distance on the domain
				float distance = p_t - t;
				//discard if distance is too great
				if( distance_threshold_enabled)
					if( distance > distance_threshold)
						continue;
				//calculate weight with a gaussian function on distance
				float w = expweight( distance, c);
				//add weighted components
				x += w * point.getX();
				y += w * point.getY();
				sum += w;
			}
			//avoid death
			if( sum == 0) continue;
			x *= this.width / sum;
			y *= this.height / sum;
			//add point
			CurvePoint curvePoint = new CurvePoint( x, y);
			curvePoints.add( curvePoint);
		}
		int curvePoints_size = curvePoints.size();
		if( curvePoints_size < 2)
			continue;

		//calculate the tangents and normals
		//calculate first's tanget
		CurvePoint first = curvePoints.firstElement();
		CurvePoint second = curvePoints.get( 1);
		first.tx = second.x - first.x;
		first.ty = second.y - first.y;
		//calculate lasts's tanget
		CurvePoint last = curvePoints.lastElement();
		CurvePoint secondLast = curvePoints.get( curvePoints_size - 2);
		last.tx = last.x - secondLast.x;
		last.ty = last.y - secondLast.y;
		//prepare for iterations
		CurvePoint previous = first;
		CurvePoint current = second;
		CurvePoint next = null;
		//for all the rest
		for( int i = 1 ; i < curvePoints_size - 1; i++){
			next = curvePoints.get( i + 1);
			current.tx = next.x - previous.x;
			current.ty = next.y - previous.y;
			double dx = current.x - previous.x;
			double dy = current.y - previous.y;
			current.dr2 = Math.pow( dx, 2) + Math.pow( dy, 2);
			double tr = Math.sqrt(
				Math.pow( current.tx, 2) +
				Math.pow( current.ty, 2));
			if( tr != 0.0){
				current.tx /= tr;
				current.ty /= tr;
			}
			previous = current;
			current = next;
		}

		noStroke();
		fill(0);
		textureMode( NORMAL);
		beginShape( QUAD_STRIP);
		texture( trail_texture);
		tint( 40, 40, 40, 200);
		for( int i = 0 ; i < curvePoints_size; i++){
			double t = (double) ( curvePoints_size - 1 - i) / ( curvePoints_size - 1);
			CurvePoint point = curvePoints.get( i);
			double nx = - point.ty * trail_width;
			double ny = point.tx * trail_width;
			double scale = 1;
			double dr_threshold = 0.3;
			if( point.dr2 <= 0)
				scale = 0;
			/*else if( point.dr2 < dr_threshold)
				scale = dr_threshold - Math.pow( point.dr2 , 2);*/
			/*System.out.printf(
				"dr2: %f, scale: %f\n", point.dr2, scale);*/
			vertex(
				(float) ( point.x + nx * scale),
				(float) ( point.y + ny * scale),
				(float) t, 0.0);
			vertex(
				(float) ( point.x - nx * scale),
				(float) ( point.y - ny * scale),
				(float) t, 1.0);
		}
		endShape();
		noTint();
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

Vector<TuioPoint> selectPoints(
		Touch touch, long currentTime, int time_threshold, int point_threshold){
	//result points
	Vector<TuioPoint> points = new Vector<TuioPoint>();
	//for every point along the path
	TuioPoint previous = null;
	for( int i = touch.path.size() - 1; i >= 0; i--){
		//stop when we have too many points
		if( points.size() >= point_threshold) break;
		TuioPoint point = touch.path.get( i);
		//get point time
		long pointTime = point.getTuioTime().getTotalMilliseconds();
		//add points that are within the time threshold
		//don't add duplicates
		if( currentTime - pointTime > time_threshold)
			continue;
		/*if( previous != null){
			float dist_x2 = pow(
				point.getScreenX( window_width) -
				previous.getScreenX( window_width), 2);
			float dist_y2 = pow(
				point.getScreenY( window_height) -
				previous.getScreenY( window_height), 2);
			float distance = sqrt( dist_x2 + dist_y2);
			if( distance <= 5)
				continue;
		}*/
		points.add( point);
	}
	return points;
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
			drawnnt = ! drawnnt;
			break;}
		case 'f':{
			SMT.setTrailEnabled( ! SMT.getTrailEnabled());
			break;}
		default: break;
	}
}

public class CurvePoint {
	public float x = 0;
	public float y = 0;
	public double dr2 = 0;
	public double tx = 0;
	public double ty = 0;
	public CurvePoint( float x, float y){
		this.x = x;
		this.y = y;
	}
}