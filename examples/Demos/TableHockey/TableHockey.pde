/**
 * A Table Hockey game, made with SMT
 * by Kalev Kalda Sikes
 */

//standard library imports
import java.awt.TextArea;
import java.util.Vector;

//SMT library imports
import vialab.SMT.*;

//constants
boolean window_fullscreen = false;
int window_width = 1200;
int window_height = 800;
final int fps_limit = 60;
final int puck_count = 10;

//main variables
public Vector<Puck> pucks;
public Vector<Wall> walls;
Physics physics = new Physics();
//corners
public PVector topLeftCorner;
public PVector topRightCorner;
public PVector bottomLeftCorner;
public PVector bottomRightCorner;
//nets
public Net player;
public Net enemy;
//other
public int window_halfWidth;
public int window_halfHeight;

//main functions
void setup(){
	if( window_fullscreen){
		window_width = displayWidth;
		window_height = displayHeight;
	}
	window_halfWidth = window_width / 2;
	window_halfHeight = window_height / 2;
	//processing library setup
	frameRate( fps_limit);
	size( window_width, window_height, SMT.RENDERER);
	//smt library setup
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setTouchColour( 30, 30, 30, 150);
	SMT.setTrailColour( 30, 30, 30, 150);
	SMT.setTouchRadius( 10);

	//create pucks
	pucks = new Vector<Puck>();
	for( int i = 0; i < puck_count; i++){
		pucks.add( new Puck());
	}
	//add pucks to smt
	for( Puck puck : pucks){
		SMT.add( puck);
	}

	//create corners
	topLeftCorner = new PVector( 0, 0);
	topRightCorner = new PVector( window_width, 0);
	bottomLeftCorner = new PVector( 0, window_height);
	bottomRightCorner = new PVector( window_width, window_height);

	//create walls
	walls = new Vector<Wall>();
	walls.add( new Wall( topLeftCorner, topRightCorner));
	walls.add( new Wall( topRightCorner, bottomRightCorner));
	walls.add( new Wall( bottomRightCorner, bottomLeftCorner));
	walls.add( new Wall( bottomLeftCorner, topLeftCorner));

	//create nets
	player = new Net( "Player", 0, window_halfHeight, 50, 400);
	enemy = new Net( "Enemy", window_width, window_halfHeight, 50, 400);
	SMT.add( player);
	SMT.add( enemy);

	//start up the physics engine
	try{
		Thread.sleep( 0);
	} catch( InterruptedException e){}
	physics.start();
}

void draw(){
	fill( 255, 255, 255);
	//draw background
	rect( 0, 0, window_width, window_height);
	
	//draw scores
	textSize(32);
	fill(0, 102, 153);
	
	//draw player score
	textAlign( RIGHT);
	text( String.format( "%d", player.score), window_halfWidth - 15, 32);
	//draw separator
	textAlign( CENTER);
	text( ":", window_halfWidth, 32);
	//draw enemy score
	textAlign( LEFT);
	text( String.format( "%d", enemy.score), window_halfWidth + 15, 32);
}

void stop(){
	physics.terminate = true;
	super.stop();
}

//functions
//projection of one onto other
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

//classes
class Puck extends Zone {
	//static fields
	final static String name = "Puck";
	final static float aniStepsPerDraw = 0.15;
	final static float maxSpeed = 100.0;
	final static int defaultRadius = 30;
	final static float initialSpeedBound = 10;
	//fields
	public float radius;
	public float mass;
	public PVector velocity;
	public PVector position;
	//private fields
	private boolean scored;
	private float animation_step;

	//constructor
	public Puck(){
		super( name, 0, 0, defaultRadius*2, defaultRadius*2 );
		this.radius = defaultRadius;
		this.mass = 1;
		reset();
	}
	public Puck(int x, int y, int radius) {
		super(name, 0, 0, radius*2, radius*2 );
		this.radius = radius;
		this.mass = 1;
		this.scored = false;
		velocity = new PVector(
			random( -initialSpeedBound, initialSpeedBound),
			random( -initialSpeedBound, initialSpeedBound));
		position = new PVector( x, y);
	}

	//SMT override methods
	public void draw(){
		if( !scored){
			stroke( 255, 255, 255, 50);
			fill( 150, 50, 50, 255);
			ellipse(
				position.x, position.y,
				this.width, this.height);
		} else {
			stroke( 255, 255, 255, 50 * (1.0 - animation_step));
			fill( 150, 50, 50, 255 * (1.0 - animation_step));
			ellipse(
				position.x, position.y,
				this.width * (1.0 + animation_step),
				this.height * (1.0 + animation_step));
			animation_step += aniStepsPerDraw;
			if( animation_step > 1.0){
				this.reset();
			}
		}
	}
	public void pickDraw() {
		if( ! scored)
			ellipse(
				position.x, position.y,
				this.width, this.height);
	}
	public void touch(){
		Touch touch = getActiveTouch(0);
		assert( touch != null);

		float dx = touch.x - position.x;
		float dy = touch.y - position.y;

		velocity.x = dx * 20;
		velocity.y = dy * 20;
	}

	//utility functions
	public void reset(){
		scored = false;
		velocity = new PVector(
			random( -initialSpeedBound, initialSpeedBound),
			random( -initialSpeedBound, initialSpeedBound));
		position = new PVector(
			window_halfWidth + (int) random(-5, 5),
			window_halfHeight + (int) random(-5, 5));
		for( Touch touch : this.getTouches()){
			touch.unassignZone( this);
		}
	}
}//

public class Wall {
	public PVector a; //one end of the wall
	public PVector b; //the other end of the wall
	public PVector parallel;
	public PVector perpendicular;

	public Wall( PVector a, PVector b){
		this.a = a;
		this.b = b;
		this.parallel = new PVector(
			(float) (b.x - a.x),
			(float) (b.y - a.y));
		this.parallel.normalize();
		this.perpendicular = new PVector(
			-parallel.y, parallel.x);
		this.perpendicular.normalize();
	}

	public PVector distanceVector( PVector point){
		//vector from a to point
		PVector aToPoint = PVector.sub( point, a);
		//dot product of wall's perpendicular to 
		return projection( aToPoint, perpendicular);
	}
}

public class Net extends Zone {
	//fields
	public int score;
	public int halfWidth;
	public int halfHeight;
	public PVector position;
	//corners and walls
	public Vector<PVector> corners;
	public PVector corner_topLeft;
	public PVector corner_topRight;
	public PVector corner_bottomLeft;
	public PVector corner_bottomRight;
	public Wall wall_left;
	public Wall wall_right;
	public Wall wall_top;
	public Wall wall_bottom;

	//constructor
	public Net( String name, int x, int y, int width, int height){
		super( name, 0, 0, width, height);
		position = new PVector( x, y);
		score = 0;
		this.halfWidth = width / 2;
		this.halfHeight = height / 2;

		corners = new Vector<PVector>();
		corner_topLeft = new PVector( x - halfWidth, y - halfHeight);
		corner_topRight = new PVector( x + halfWidth, y - halfHeight);
		corner_bottomLeft = new PVector( x - halfWidth, y + halfHeight);
		corner_bottomRight = new PVector( x + halfWidth, y + halfHeight);
		corners.add( corner_topLeft);
		corners.add( corner_topRight);
		corners.add( corner_bottomLeft);
		corners.add( corner_bottomRight);
		
		wall_top = new Wall( corner_topLeft, corner_topRight);
		wall_right = new Wall( corner_topRight, corner_bottomRight);
		wall_bottom = new Wall( corner_bottomRight, corner_bottomLeft);
		wall_left = new Wall( corner_bottomLeft, corner_topLeft);
	}

	//smt functions
	public void drawImpl(){
		stroke( 255, 255, 255, 50);
		fill( 50, 150, 200, 255);
		rect( position.x - halfWidth, position.y - halfHeight, width, height);
	}
	public void pickDrawImpl() {
		rect( position.x - halfWidth, position.y - halfHeight, width, height);
	}
	public void touchImpl(){}

	//utility functions
	public boolean touches( Puck puck){
		//check corners
		for( PVector corner : corners){
			if( PVector.sub( corner, puck.position).mag() < puck.radius){
				return true;
			}
		}
		//check walls
		PVector relative = PVector.sub( puck.position, this.position);
		return (
			( abs( relative.y) < halfHeight) &&
			( abs( relative.x) < halfWidth + puck.radius));
	}
}

class Physics extends Thread {
	//constants
	public final int ticksPerSecond = 250;
	public final long nanosecondsPerSecond = 1000000000;
	public final float jiggle = 0.001;
	public final float friction = 0.998;
	public double secondsPerTick;
	//public variables
	public boolean terminate = false;
	//private variables
	private long second;
	private long time;
	private long time_old;
	private double dtime;
	private int ticks;

	//constructor
	public Physics(){
		//basic initialization
		secondsPerTick = 1.0/ticksPerSecond;
		time = System.nanoTime();
		second = time/nanosecondsPerSecond;
		updateTime();
	}

	//methods
	public void run(){
		while( !terminate){
			tick();
		}
	}

	public void tick(){
		updateTime();
		for( Puck puck : pucks){
			if( puck.scored) continue;
			/*if( puck.velocity.mag() > Puck.maxSpeed){
				puck.velocity.normalize();
				puck.velocity.mult( Puck.maxSpeed);
			}*/
			PVector step = new PVector( puck.velocity.x, puck.velocity.y);
			step.x *= dtime;
			step.y *= dtime;
			puck.position.add( step);
			puck.velocity.mult( friction);
		}
		for( int index = 0; index < pucks.size(); index++){
			Puck puck = pucks.get( index);
			handlePuckCollisions( puck, index);
		}
		for( Puck puck : pucks){
			handleWallCollisions( puck);
		}
		for( Puck puck : pucks){
			handleNetCollisions( puck);
		}
		applyTickLimit();
	}

	//collision functions
	public void handlePuckCollisions( Puck puck, int index){
		if( puck.scored) return;
		for( int i = index + 1; i < pucks.size(); i++){
			Puck other = pucks.get( i);
			if( other.scored) continue;

			PVector difference = PVector.sub(
				other.position, puck.position);
			float distance = difference.mag() - puck.radius - other.radius;
			//println( String.format("%f %f", difference.x, difference.y));
			if( distance < 0){
				difference.normalize();
				
				//push the pucks apart
				difference.mult( distance / 2 - jiggle);
				puck.position.add( difference);
				other.position.sub( difference);
				
				//bounce the pucks off each other
				//mass calculations
				double totalMass = puck.mass + other.mass;
				double puck_massTerm1 = ( puck.mass - other.mass) / totalMass;
				double puck_massTerm2 = ( other.mass * 2.0) / totalMass;
				double other_massTerm1 = ( other.mass - puck.mass) / totalMass;
				double other_massTerm2 = ( puck.mass * 2.0) / totalMass;

				//velocity calculations
				// these velocities are relative to the direction of the collision
				PVector puck_v = projection( puck.velocity, difference);
				PVector other_v = projection( other.velocity, difference);
				puck.velocity.sub( puck_v);
				other.velocity.sub( other_v);
				
				PVector puck_force = scale( puck_v, puck_massTerm1);
				PVector other_force = scale( other_v, other_massTerm1);
				
				puck_force.add( scale( other_v, puck_massTerm2));
				other_force.add( scale( puck_v, other_massTerm2));

				puck.velocity.add( puck_force);
				other.velocity.add( other_force);
			}
		}
	}

	public void handleWallCollisions( Puck puck){
		if( puck.scored) return;
		for( Wall wall : walls){
			//get puck to wall distance
			PVector distanceVector = wall.distanceVector( puck.position);
			float distance = distanceVector.mag();
			//print( String.format( "%f %f ", distanceVector.x, distanceVector.y));
			//print( String.format( "%f ", distance));
			if( distance < puck.radius){
				distanceVector.normalize();
				//push the puck away from the wall
				PVector push = PVector.mult(
					distanceVector, puck.radius - distance + jiggle);
				puck.position.add( push);
				//bounce the puck off the wall
				PVector force = projection( puck.velocity, distanceVector);
				force.mult( -2);
				puck.velocity.add( force);
			}
		}
		//println();
	}

	public boolean handleNetCollision( Puck puck, Net net){
		if( puck.scored) return false;
		if( net.touches( puck)){
			puck.scored = true;
			puck.animation_step = 0;
			return true;
		}
		return false;
	}
	public void handleNetCollisions( Puck puck){
		//check player's net
		if( handleNetCollision( puck, player)){
			enemy.score++;
		}
		//check enemy's net
		if( handleNetCollision( puck, enemy)){
			player.score++;
		}
	}

	//utility functions
	private void updateTime(){
		time_old = time;
		time = System.nanoTime();
		dtime = (time - time_old)/1e9;
		if( second != time/nanosecondsPerSecond){
			//println( String.format("tps: %d", ticks));
			ticks = 0;
			second = time/nanosecondsPerSecond;
		}
		ticks++;
	}

	private void applyTickLimit(){
		try {
			long time2 = System.nanoTime();
			double dtime2 = (time2 - time)/1e9;
			double timeLeft = secondsPerTick - dtime2;
			//println(timeLeft);
			if( timeLeft > 0){
				Thread.sleep( Math.round( timeLeft*1000));
			}
		} catch( InterruptedException e){}
	}
}//
