
//standard library imports
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
PImage tex;

//main functions
void setup(){
	display_width = displayWidth;
	display_height = displayHeight;
	display_halfWidth = display_width / 2;
	display_halfHeight = display_height / 2;
	//processing library setup
	frameRate( fps_limit);
	size( display_width, display_height, P3D);
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setTouchDraw( TouchDraw.TEXTURED);

	//load texture and texture options
	PImage tex1 = loadImage("resources/touch_texture.png");
	PImage tex2 = loadImage("resources/touch_texture2.png");
	PImage tex3 = loadImage("resources/touch_texture3.png");
	textureMode( NORMAL);
	SMT.add( new Prototype( tex1, 100, 100));
	SMT.add( new Prototype( tex2, 500, 100));
	SMT.add( new Prototype( tex3, 300, 100));
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

public class Prototype extends Zone{
	public float radius;
	private Vector<PVector> vertices;
	private PImage tex;

	public Prototype( PImage tex, int x, int y){
		super( x, y, 0, 0);
		this.tex = tex;
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
		noStroke();
		beginShape( TRIANGLE_FAN);
		texture( tex);
		vertex( 0, 0, 0, 1);
		for( PVector vert : vertices)
			vertex( vert.x, vert.y, 0, 0);
		endShape();}

		/*noFill();
		stroke( 200, 50, 50, 180);
		ellipse( 0, 0, radius, radius);}*/

	public void pickDrawImpl(){
		ellipse( 0, 0, radius, radius);}

	public void touchImpl(){
		drag();}
}