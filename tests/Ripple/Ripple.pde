
//standard library imports
import java.util.Vector;

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
PImage tex;

//main functions
void setup(){
	display_halfWidth = display_width / 2;
	display_halfHeight = display_height / 2;
	//processing library setup
	frameRate( fps_limit);
	size( display_width, display_height, P3D);
	SMT.init( this, TouchSource.MOUSE);

	//load texture and texture options
	tex = loadImage("resources/ripple_texture.png");

	//load zones
	SMT.add( new Zone( "RippleTest", 100, 100, 1000, 600));
}

void draw(){
	//draw background
	fill( 0, 0, 0);
	noStroke();
	rect( 0, 0, display_width, display_height);

	//draw a bunch of random shit in random colours
	fill( 200, 150, 100);
	rect( 0, 0, 200, 150);
	fill( 60, 180, 180);
	rect( 200, 0, 200, 150);
	fill( 255, 255, 255);
	rect( 0, 150, 200, 150);
	fill( 120, 120, 120);
	rect( 200, 150, 200, 150);
	fill( 40, 40, 40);
	rect( 400, 0, 200, 150);
}

void drawRippleTest( Zone zone){
	noFill();
	stroke( 150, 220, 150);
	rect( 0, 0, zone.width, zone.height);}
void pickDrawRippleTest( Zone zone){
	rect( 100, 100, zone.width, zone.height);}
void touchDownRippleTest( Zone zone){
	Touch touch = zone.getActiveTouch(0);
	assert( touch != null);
	SMT.add( new Prototype( tex, touch.x, touch.y));}

//subclasses
class Prototype extends Zone {
	//drawing fields
	public float radius;
	private Vector<PVector> vertices;
	private PImage tex;
	//timing fields
	private long time;
	private long time_old;
	private double dtime;
	private double step;
	private final double ani_period = 10.0;

	public Prototype( PImage tex, int x, int y){
		super( x, y, 0, 0);
		this.tex = tex;

		//load timing vars
		time = System.nanoTime();
		updateTime();
		step = 0.0;

		//load vertices
		vertices = new Vector<PVector>();
		int sections = 32;
		float dtheta = TWO_PI / sections;
		radius = 200.0;
		for( float theta = 0.0; theta < TWO_PI; theta += dtheta)
			addVert( theta, radius);
		addVert( TWO_PI, radius);}

	private void addVert( float theta, float radius){
		vertices.add(
			new PVector(
				radius * cos( theta),
				radius * sin( theta)));}

	public void drawImpl(){
		updateTime();

		noStroke();
		textureMode( NORMAL);
		beginShape( TRIANGLE_FAN);
		texture( tex);
		vertex( 0, 0, 1, 0);
		for( PVector vert : vertices)
			vertex( (float) step * vert.x, (float) step * vert.y, 0, 0);
		endShape();}

	public void pickDrawImpl(){}
	public void touchImpl(){}

	private void updateTime(){
		time_old = time;
		time = System.nanoTime();
		dtime = (time - time_old) / 1e9;
		step += dtime / ani_period;
		if( step >= 1)
			SMT.remove( this);}
}