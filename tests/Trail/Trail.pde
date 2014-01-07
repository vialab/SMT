//standard library imports
import java.awt.Point;
import java.util.Vector;

//TUIO library imports
import TUIO.TuioPoint;

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
int path_points = 30;
int trail_sections = 20;
Vector<SamplePoint> points;
Vector<SamplePoint> movingPoints;
boolean moving = false;

//main functions
void setup(){
	display_halfWidth = display_width / 2;
	display_halfHeight = display_height / 2;
	//processing window setup
	frameRate( fps_limit);
	size( display_width, display_height, P3D);
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setTouchDraw( TouchDraw.NONE);

	//load texture
	tex = loadImage("resources/trail_texture.png");

	//points
	points = new Vector<SamplePoint>();
	points.add( new SamplePoint( 100, 200));
	points.add( new SamplePoint( 200, 200));
	points.add( new SamplePoint( 300, 200));
	points.add( new SamplePoint( 400, 200));
	points.add( new SamplePoint( 500, 200));
	points.add( new SamplePoint( 600, 200));
	points.add( new SamplePoint( 700, 200));
	points.add( new SamplePoint( 800, 200));
	points.add( new SamplePoint( 900, 200));
	points.add( new SamplePoint( 1000, 200));
	for( SamplePoint point : points)
		SMT.add( point);

	//other
	movingPoints = new Vector<SamplePoint>();
}

void draw(){
	//draw background
	background( 0);

	//draw touches
	/*Touch[] touches = SMT.getTouches();
	if( touches.length > 0){
		//get first touch data
		Touch touch = touches[0];
		Point[] points = touch.getPathPoints();
		int point_count = min( path_points, points.length);
		//create the interpolation curve
		//set up drawing parameters
		stroke( 255);
		noFill();
		beginShape();

		for( int i = 1 ; i <= point_count; i++){
			Point point = points[ points.length - i];
			curveVertex( point.x, point.y);
		}
		endShape();
	}*/

	drawConnectingLine();
	//drawBernsteinPolynomial();
	//drawBspline();
	drawNearestNeighbour();
}

void drawConnectingLine(){
	int pointCount = points.size();
	stroke( 150, 230, 150, 150);
	strokeWeight( 3);
	noFill();
	for( int i = 1; i < pointCount; i++){
		SamplePoint prev = points.get( i - 1);
		SamplePoint curr = points.get( i);
		line( prev.pos.x, prev.pos.y, curr.x, curr.y);
	}
}

void drawBernsteinPolynomial(){
	for( int i = 3; i < points.size(); i++){
		PVector p0 = points.get( i - 3).pos;
		PVector p1 = points.get( i - 2).pos;
		PVector p2 = points.get( i - 1).pos;
		PVector p3 = points.get( i).pos;

		int radius = 15;
		float dt = 1.0 / 10.0;
		stroke( 150, 230, 230, 200);
		strokeWeight( 3);
		noFill();
		for( float t = 0; t < 1.00001; t += dt){
			float tinv = 1 - t;
			ellipse(
				p0.x * pow( tinv, 3) + p1.x * 3 * t * pow( tinv, 2) +
					p2.x * 3 * pow( t, 2) * tinv + p3.x * pow( t, 3),
				p0.y * pow( tinv, 3) + p1.y * 3 * t * pow( tinv, 2) +
					p2.y * 3 * pow( t, 2) * tinv + p3.y * pow( t, 3),
				radius, radius);
		}
	}
}

void drawBspline(){
	PVector p0 = points.get( 0).pos;
	PVector p1 = points.get( 1).pos;
	PVector p2 = points.get( 2).pos;
	PVector p3 = points.get( 3).pos;

	int radius = 15;
	stroke( 150, 230, 230, 200);
	strokeWeight( 3);
	noFill();

	int d = 2;
	int n = 4;
	int k = n + d + 1;
	float dta = 1.0 / ( k - 1);
	float[] ta = new float[ k];
	for( int i = 0; i < k; i++)
		ta[i] = dta * i;

	float dt = 1.0 / 20.0;
	for( float t = 0; t < 1.00001; t += dt){
		ellipse(
			(
				p0.x * bspline_n( 0, d, k, ta, t) +
				p1.x * bspline_n( 1, d, k, ta, t) +
				p2.x * bspline_n( 2, d, k, ta, t) +
				p3.x * bspline_n( 3, d, k, ta, t) ),
			(
				p0.y * bspline_n( 0, d, k, ta, t) +
				p1.y * bspline_n( 1, d, k, ta, t) +
				p2.y * bspline_n( 2, d, k, ta, t) +
				p3.y * bspline_n( 3, d, k, ta, t) ),
			radius, radius);
	}
}

float bspline_n( int i, int d, int k, float[] ta, float t){
	if( d == 0)
		if( t > ta[ i] && t < ta[ i + 1])
			return 1;
		else
			return 0;
	else {
		float parta_tc =
			( t - ta[ i]) /
			( ta[ i + d] - ta[ i]);
		float partb_tc =
			( ta[ i + d + 1] - t) /
			( ta[ i + d + 1] - ta[ i + 1]);
		float parta_n = bspline_n( i, d - 1, k, ta, t);
		float partb_n = bspline_n( i, d - 1, k, ta, t);
		return parta_tc * parta_n + partb_tc * partb_n;
	}
}

void drawNearestNeighbour(){
	int radius = 15;
	stroke( 230, 230, 150, 200);
	strokeWeight( 3);
	noFill();
}



void recalculate(){/*
	println( "Recalculating...");
	println( "Recalculation Complete");*/
}


//classes
public class SamplePoint extends Zone {
	public PVector pos;
	public static final int radius = 30;

	public SamplePoint( int x, int y){
		super( x, y, 0, 0);
		this.pos = new PVector( x, y);
	}

	public void touchDownImpl( Touch touch){
		moving = true;
		movingPoints.add( this);
	}
	public void touchImpl(){
		drag();
	}
	public void touchUpImpl( Touch touch){
		movingPoints.remove( this);
		if( movingPoints.isEmpty()){
			moving = false;
			recalculate();
		}
	}
	public void drawImpl(){
		stroke( 200, 150, 150, 120);
		strokeWeight( 5);
		noFill();
		ellipse( 0, 0, radius, radius);
	}
	public void pickDrawImpl(){
		ellipse( 0, 0, radius, radius);
	}

	@Override
	public void setLocation( float x, float y){
		super.setLocation( x, y);
		pos.set( x, y);
	}
}