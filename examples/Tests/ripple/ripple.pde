//standard library imports
import java.util.Vector;

//SMT library imports
import vialab.SMT.*;

//constants
int window_width = 1200;
int window_height = 800;
int window_halfWidth;
int window_halfHeight;
int fps_limit = 60;

//other
PImage tex;
int button_width = 100;
int button_height = 80;
int ripple_red = 255;
int ripple_green = 255;
int ripple_blue = 255;
int ripple_alpha = 255;

//main functions
void setup(){
	window_halfWidth = window_width / 2;
	window_halfHeight = window_height / 2;
	//processing library setup
	frameRate( fps_limit);
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
	//SMT.setTouchDraw( TouchDraw.TEXTURED);

	//load texture and texture options
	tex = loadImage("resources/ripple_texture.png");

	//load the center thingy
	SMT.add( new Zone( "RippleTest", 100, 100, 1000, 600));

	//load the buttons
	SMT.add( new Zone( "One",
		0 * button_width, 0 * button_height, button_width, button_height));
	SMT.add( new Zone( "Two",
		1 * button_width, 0 * button_height, button_width, button_height));
	SMT.add( new Zone( "Three",
		2 * button_width, 0 * button_height, button_width, button_height));
	SMT.add( new Zone( "Four",
		3 * button_width, 0 * button_height, button_width, button_height));
	SMT.add( new Zone( "Five",
		4 * button_width, 0 * button_height, button_width, button_height));
}

void draw(){
	//draw background
	background( 30);
}

void drawRippleTest( Zone zone){
	noFill();
	stroke( 150, 220, 150);
	rect( 0, 0, zone.width, zone.height);}
void pickDrawRippleTest( Zone zone){
	rect( 0, 0, zone.width, zone.height);}
void touchDownRippleTest( Zone zone){
	Touch touch = zone.getActiveTouch(0);
	assert( touch != null);
	SMT.add( new Prototype( tex, touch.x, touch.y));
	System.out.println( touch.getTouchSource());}
void touchRippleTest( Zone zone){}

//button methods
void drawOne( Zone zone){
	fill( 200, 150, 100);
	rect( 0, 0, zone.width, zone.height);}
void touchDownOne( Zone zone){
	ripple_red = 200;
	ripple_green = 150;
	ripple_blue = 100;}

void drawTwo( Zone zone){
	fill( 60, 180, 180);
	rect( 0, 0, zone.width, zone.height);}
void touchDownTwo( Zone zone){
	ripple_red = 60;
	ripple_green = 180;
	ripple_blue = 180;}

void drawThree( Zone zone){
	fill( 255, 255, 255);
	rect( 0, 0, zone.width, zone.height);}
void touchDownThree( Zone zone){
	ripple_red = 255;
	ripple_green = 255;
	ripple_blue = 255;}

void drawFour( Zone zone){
	fill( 120, 120, 120);
	rect( 0, 0, zone.width, zone.height);}
void touchDownFour( Zone zone){
	ripple_red = 120;
	ripple_green = 120;
	ripple_blue = 120;}

void drawFive( Zone zone){
	fill( 40, 40, 40);
	rect( 0, 0, zone.width, zone.height);}
void touchDownFive( Zone zone){
	ripple_red = 40;
	ripple_green = 40;
	ripple_blue = 40;}


//subclasses
class Prototype extends Zone {
	//drawing fields
	public float radius;
	private Vector<PVector> vertices;
	private PImage tex;
	int red;
	int green;
	int blue;
	int alpha;
	//timing fields
	private long time;
	private long time_old;
	private double dtime;
	private double step;
	private final double ani_period = 1.0;

	public Prototype( PImage tex, int x, int y){
		super( x, y, 0, 0);
		this.tex = tex;
		red = ripple_red;
		green = ripple_green;
		blue = ripple_blue;
		alpha = ripple_alpha;

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
		tint( red, green, blue, alpha);
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
			this.getParent().remove( this);}
}