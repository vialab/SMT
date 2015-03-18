/**
 * Sketch for ViewportZone Tutorial
 */

//imports
import java.util.*;
import vialab.SMT.*;

//vars
boolean window_fullscreen = false;
int window_width = 1200;
int window_height = 800;
//draw toggles
boolean draw_edges = true;
boolean draw_fps = true;
//zones
ViewportZone viewport;
Zone edge_drawer, frame;
Button reset_view, reset_nodes;
//zone toggles
boolean buttons_enabled = true;
//other vars
Physics physics;
Vector<Node> nodes;
Vector<Edge> edges;
int node_count = 30;
int edge_count = 80;
PFont text_font;

//Setup function for the applet
void setup(){
	if( window_fullscreen){
		window_width = displayWidth;
		window_height = displayHeight;
	}
	//SMT and Processing setup
	size( window_width, window_height, SMT.RENDERER);
	this.registerMethod( "pre", this);
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setWarnUnimplemented( false);

	//create font
	text_font = createFont( "Consolas", 24);

	//create and add viewport zone
	viewport = new ViewportZone(
		"Viewport", 50, 50, window_width - 100, window_height - 100);
	frame = new Zone( "Frame",
		viewport.x, viewport.y, viewport.width, viewport.height);
	SMT.add( viewport, frame);

	//create and add reset buttons
	reset_view = new Button( "Reset View"){
		@Override
		public void touchDown( Touch touch){
			resetView();}};
	reset_nodes = new Button( "Reset Nodes"){
		@Override
		public void touchDown( Touch touch){
			resetNodes();}};
	reset_view.setLocation( 0, - 45);
	reset_nodes.setLocation( 10 + reset_view.width, - 45);
	reset_view.setVisible( buttons_enabled);
	reset_view.setPickable( buttons_enabled);
	reset_nodes.setVisible( buttons_enabled);
	reset_nodes.setPickable( buttons_enabled);
	frame.add( reset_view, reset_nodes);

	//create and add edge drawer
	edge_drawer = new Zone( "EdgeDrawer");
	edge_drawer.setVisible( draw_edges);
	edge_drawer.setPickable( draw_edges);
	edge_drawer.setPickable( false);
	viewport.add( edge_drawer);

	//setup nodes and edges
	edges = new Vector<Edge>();
	nodes = new Vector<Node>();
	nodeGen();
	edgeGen();

	//create phyics manager
	physics = new Physics();
}

void resetView(){
	viewport.resetView();
}
void resetNodes(){
	//clean up old data
	edges.clear();
	for( Node node : nodes)
		viewport.remove( node);
	nodes.clear();
	//generate new data
	nodeGen();
	edgeGen();
}
void nodeGen(){
	float extra_space = 300.0f;
	float min_x = - extra_space;
	float min_y = - extra_space;
	float max_x = viewport.width + extra_space;
	float max_y = viewport.height + extra_space;
	//create nodes with randomized positions
	for( int i = 0; i < node_count; i++)
		nodes.add( new Node(
			random( min_x, max_x),
			random( min_y, max_y)));
	//add nodes to viewport
	for( Node node : nodes)
		viewport.add( node);
}
void edgeGen(){
	int i = 0;
	Random dice = new Random( System.nanoTime());
	//while we still need edges
	while( i < edge_count){
		//pick two random nodes
		Node node_a = nodes.get(
			dice.nextInt( nodes.size()));
		Node node_b = nodes.get(
			dice.nextInt( nodes.size()));
		//checks
		if( node_a == node_b) continue;
		if( node_a.connectsTo( node_b)) continue;
		//add edge
		node_a.connect( node_b);
		i++;
	}
}

//applet methods
void pre(){
	physics.tick();
}

//drawing methods
void draw(){
	background( 20);
	if( draw_fps) drawFrameRate();
}
void drawFrameRate(){
	float fps = this.frameRate;
	String fps_text = String.format( "fps: %2.0f", fps);
	pushStyle();
	fill( 240, 240, 240, 180);
	textAlign( RIGHT, TOP);
	textSize( 24);
	text( fps_text, window_width - 5, 5);
	popStyle();
}

//keyboard handle
void keyPressed(){
	//println( key);
	switch( key){
		case 'q':{
			resetNodes();
			break;}
		case 'w':{
			resetView();
			break;}
		case 'e':{
			draw_edges = ! draw_edges;
			edge_drawer.setVisible( draw_edges);
			edge_drawer.setPickable( draw_edges);
			break;}
		case 'a':{
			buttons_enabled = ! buttons_enabled;
			reset_view.setVisible( buttons_enabled);
			reset_view.setPickable( buttons_enabled);
			reset_nodes.setVisible( buttons_enabled);
			reset_nodes.setPickable( buttons_enabled);
			break;}
		case 'f':{
			draw_fps = ! draw_fps;
			break;}
		default: break;
	}
}

//zone methods
void drawViewport( Zone zone){
	fill( 10, 140);
	noStroke();
	rect( 0, 0, zone.width, zone.height);
}
void touchViewport( Zone zone){
	zone.pinch(); //aka. rst( false, true, true)
}

void drawEdgeDrawer(){
	for( Edge edge : edges)
		edge.draw();
}
void pickDrawEdgeDrawer( Zone zone){}

void drawFrame( Zone zone){
	noFill();
	stroke( 240, 200);
	strokeWeight( 5);
	rect( 0, 0, zone.width, zone.height);
}
void pickDrawFrame( Zone zone){}

//static functions
// projection of one onto other
public PVector projection( PVector one, PVector other){
	PVector result = new PVector( other.x, other.y);
	result.normalize();
	result.mult( one.dot( result));
	return result;
}

public PVector scale( PVector vector, double scalar){
	PVector result = new PVector( vector.x, vector.y);
	result.x *= scalar;
	result.y *= scalar;
	return result;
}

//subclasses
class Edge {
	//fields
	public Node a, b;
	//constructors
	public Edge( Node a, Node b){
		this.a = a;
		this.b = b;
	}
	public void draw(){
		//draw base line
		strokeWeight( 2);
		stroke( 140, 200);
		line(
			a.position.x, a.position.y,
			b.position.x, b.position.y);
		//draw highlight line
		stroke( 240, getAlpha());
		strokeWeight( 4);
		line(
			a.position.x, a.position.y,
			b.position.x, b.position.y);
	}
	public float getAlpha(){
		return Math.max(
			a.getAlpha(),
			b.getAlpha());
	}
	public boolean contains( Node node){
		return a == node || b == node;
	}
};

class Node extends Zone {
	//constants
	public static final float mass = 1.0f;
	private static final float aniStepsPerDraw = 0.15;
	//data fields
	public Vector<Edge> edges;
	public PVector velocity;
	public PVector position;
	public float inset = 5.0f;
	public float radius = 25.0f;
	//drawing fields
	public boolean selected;
	public boolean animating;
	public float ani_step;
	//constructors
	public Node( float x, float y){
		super();
		this.edges = new Vector<Edge>();
		this.position = new PVector( x, y);
		this.velocity = new PVector();
		this.selected = false;}
	//public functions
	public boolean connectsTo( Node other){
		for( Edge edge : edges)
			if( edge.contains( other))
				return true;
		return false;
	}
	public void connect( Node other){
		Edge edge = new Edge( this, other);
		this.edges.add( edge);
		other.edges.add( edge);
		Nodes.this.edges.add( edge);
	}
	//zone overrides
	@Override
	public void draw(){
		//draw ellipse
		stroke( 240, this.getAlpha());
		strokeWeight( 5);
		fill( 150, 50, 50, 240);
		ellipseMode( RADIUS);
		ellipse(
			position.x, position.y,
			this.radius, this.radius);
		//increment animation step
		if( animating){
			ani_step += aniStepsPerDraw;
			if( ani_step > 1.0)
				animating = false;
		}
	}
	public float getAlpha(){
		return animating ?
			( 180 * ( selected ? ani_step : ( 1.0 - ani_step))) :
			( selected ? 180 : 0);
	}
	public void pickDraw(){
		ellipseMode( RADIUS);
		ellipse(
			position.x, position.y,
			this.radius, this.radius);
	}
	public void touch(){
		Touch touch = getActiveTouch(0);
		PVector touch_rel = toZoneVector(
			touch.getPositionVector());
		float dx = touch_rel.x - position.x;
		float dy = touch_rel.y - position.y;
		velocity.x = dx * 20;
		velocity.y = dy * 20;
	}
	public void touchDown( Touch touch){}
	public void touchMoved( Touch touch){}
	public void assign( Touch... touches){
		super.assign( touches);
		selected = true;
		ani_step = animating ? ( 1.0 - ani_step) : 0.0;
		animating = true;
	}
	public void unassign( Touch touch){
		super.unassign( touch);
		if( this.getNumTouches() == 0){
			selected = false;
			ani_step = animating ? ( 1.0 - ani_step) : 0.0;
			animating = true;
		}
	}
};

class Button extends Zone {
	//constants
	private static final float aniStepsPerDraw = 0.25;
	//private fields
	private String text;
	private PGraphics text_graphics = null;
	private boolean selected;
	public boolean animating;
	public float ani_step;
	//constructors
	public Button( String text){
		super( 0, 0, 160, 40);
		this.text = text;
		this.setCaptureTouches( false);
	}
	//zone overrides
	public void draw(){
		//draw rect
		stroke( 220, getAlpha());
		strokeWeight( 3);
		fill( 15, 220);
		rect( 0, 0, this.width, this.height, 4);
		//draw text
		textAlign( CENTER, CENTER);
		textFont( text_font);
		fill( 220, 220);
		text( this.text, this.width / 2, this.height / 2);
		if( animating){
			ani_step += aniStepsPerDraw;
			if( ani_step > 1.0)
				animating = false;
		}
	}
	public float getAlpha(){
		return animating ?
			( 180 * ( selected ? ani_step : ( 1.0 - ani_step))) :
			( selected ? 180 : 0);
	}
	public void pickDraw(){
		rect( 0, 0, this.width, this.height, 5);
	}
	public void touch(){}
	public void assign( Touch... touches){
		super.assign( touches);
		selected = true;
		ani_step = animating ? ( 1.0 - ani_step) : 0.0;
		animating = true;
	}
	public void unassign( Touch touch){
		super.unassign( touch);
		if( this.getNumTouches() == 0){
			selected = false;
			ani_step = animating ? ( 1.0 - ani_step) : 0.0;
			animating = true;
		}
	}
};

// we're probably not gonna use this as a thread... but we could.
class Physics extends Thread {
	//constants
	public final int ticksPerSecond = 250;
	public final long nanosecondsPerSecond = 1000000000;
	public final float jiggle = 0.01;
	public final float friction = 0.8;
	public double secondsPerTick;
	public final float node_mass = 1;
	//public variables
	public boolean terminate = false;
	//private variables
	private long second;
	private long time;
	private long time_old;
	private double dtime;
	private int ticks;
	//other
	private boolean print_tps = false;

	//constructor
	public Physics(){
		//basic initialization
		secondsPerTick = 1.0 / ticksPerSecond;
		time = System.nanoTime();
		second = time / nanosecondsPerSecond;
		updateTime();
	}

	//methods
	@Override
	public void run(){
		while( ! terminate){
			tick();
			applyTickLimit();
		}
	}

	public void tick(){
		updateTime();
		for( Node node : nodes){
			PVector step = new PVector( node.velocity.x, node.velocity.y);
			step.x *= dtime;
			step.y *= dtime;
			node.position.add( step);
			node.velocity.mult( friction);}
		for( int index = 0; index < nodes.size(); index++){
			Node node = nodes.get( index);
			handleNodeCollisions( node, index);
		}
	}

	//collision functions
	public void handleNodeCollisions( Node node, int index){
		for( int i = index + 1; i < nodes.size(); i++){
			Node other = nodes.get( i);

			PVector difference = PVector.sub(
				other.position, node.position);
			float distance = difference.mag()
				- node.inset - node.inset
				- node.radius - other.radius;
			if( distance < 0){
				difference.normalize();
				
				//push the nodes apart
				difference.mult( distance / 2 - jiggle);
				node.position.add( difference);
				other.position.sub( difference);
				
				//bounce the nodes off each other
				//mass calculations
				double totalMass = Node.mass + Node.mass;
				double node_massTerm1 = ( Node.mass - Node.mass) / totalMass;
				double node_massTerm2 = ( Node.mass * 2.0) / totalMass;
				double other_massTerm1 = ( Node.mass - Node.mass) / totalMass;
				double other_massTerm2 = ( Node.mass * 2.0) / totalMass;

				//velocity calculations
				// these velocities are relative to the direction of the collision
				PVector node_v = projection( node.velocity, difference);
				PVector other_v = projection( other.velocity, difference);
				node.velocity.sub( node_v);
				other.velocity.sub( other_v);
				
				PVector node_force = scale( node_v, node_massTerm1);
				PVector other_force = scale( other_v, other_massTerm1);
				
				node_force.add( scale( other_v, node_massTerm2));
				other_force.add( scale( node_v, other_massTerm2));

				node.velocity.add( node_force);
				other.velocity.add( other_force);
			}
		}
	}

	//utility functions
	private void updateTime(){
		time_old = time;
		time = System.nanoTime();
		dtime = (time - time_old)/1e9;
		long new_second = time / nanosecondsPerSecond;
		if( second != new_second){
			if( print_tps)
				System.out.printf( "tps: %d", ticks);
			ticks = 0;
			second = new_second;
		}
		ticks++;
	}

	private void applyTickLimit(){
		try {
			long time2 = System.nanoTime();
			double dtime2 = (time2 - time) / 1e9;
			double timeLeft = secondsPerTick - dtime2;
			//println(timeLeft);
			if( timeLeft > 0)
				Thread.sleep( Math.round( timeLeft * 1000));}
		catch( InterruptedException exception){}
	}
};
