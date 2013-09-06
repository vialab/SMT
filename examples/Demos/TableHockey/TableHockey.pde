
/**
 * A Table Hockey game, made with SMT
 */

//standard library imports
import java.awt.geom.Line2D;
import java.util.Vector;

//SMT library imports
import vialab.SMT.*;

//constants
final int display_width = 1200;
final int display_height = 800;
final int fps_limit = 60;

//variables
public Vector<Puck> pucks = new Vector<Puck>();
Physics physics = new Physics();

//main functions
void setup(){
	//processing library setup
	frameRate( fps_limit);
	size( display_width, display_height, P3D);
	//smt library setup
	SMT.init( this, TouchSource.MULTIPLE);

	//create pucks
	Puck temp = new Puck( 100, 100, 30);
	pucks.add( temp);
	temp = new Puck( 100, 400, 30);
	pucks.add( temp);
	temp = new Puck( 100, 400, 30);
	pucks.add( temp);
	temp = new Puck( 100, 400, 30);
	pucks.add( temp);
	temp = new Puck( 100, 400, 30);
	pucks.add( temp);
	temp = new Puck( 100, 400, 30);
	pucks.add( temp);
	temp = new Puck( 100, 400, 30);
	pucks.add( temp);

	//add pucks to smt
	for( Puck puck : pucks){
		SMT.add( puck);
	}

	//start up the physics engine
	physics.start();
}

void draw(){
	background( 0, 0, 0);
}

void stop(){
	physics.terminate = true;
	super.stop();
}

//classes
class Puck extends Zone {
	//static fields
	final static String name = "Puck";
	//fields
	public float radius;
	public float mass;
	public PVector velocity;
	public PVector position;

	//constructor
	public Puck(int x, int y, int radius) {
		super(name, 0, 0, radius*2, radius*2 );
		this.radius = radius;
		this.mass = 1;
		velocity = new PVector( 0, 0);
		position = new PVector( x, y);
	}

	//SMT override methods
	public void drawImpl(){
		fill( 150, 50, 50);
		ellipse(
			position.x, position.y,
			this.width, this.height);
	}
	public void pickDrawImpl() {
		ellipse(
			position.x, position.y,
			this.width, this.height);
	}
	public void touchImpl(){
		Touch touch = getActiveTouch(0);
		assert( touch != null);

		float dx = touch.x - position.x;
		float dy = touch.y - position.y;

		velocity.x = dx * 20;
		velocity.y = dy * 20;
	}
} //

class Physics extends Thread {
	//constants
	public final int ticksPerSecond = 60;
	public final long nanosecondsPerSecond = 1000000000;
	public double secondsPerTick;
	//public variables
	public boolean terminate = false;
	//private variables
	private long second;
	private long time;
	private long time_old;
	private double dtime;
	private int ticks;
	private Vector<Line2D.Double> walls;

	//constructor
	public Physics(){
		//basic initialization
		secondsPerTick = 1.0/ticksPerSecond;
		time = System.nanoTime();
		second = time/nanosecondsPerSecond;
		updateTime();

		//initialize walls list
		walls = new Vector<Line2D.Double>();
		walls.add( new Line2D.Double(
			0, 0,
			display_width, 0));
		walls.add( new Line2D.Double(
			display_width, 0,
			display_width, display_height));
		walls.add( new Line2D.Double(
			display_width, display_height,
			0, display_height));
		walls.add( new Line2D.Double(
			0, display_height,
			0, 0));
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
			PVector step = new PVector( puck.velocity.x, puck.velocity.y);
			step.x *= dtime;
			step.y *= dtime;
			puck.position.add( step);
		}
		for( int index = 0; index < pucks.size(); index++){
			Puck puck = pucks.get( index);
			handlePuckCollisions( puck, index);
		}
		for( Puck puck : pucks){
			handleWallCollisions( puck);
		}
		applyTickLimit();
	}

	//collision functions
	public void handlePuckCollisions( Puck puck, int index){
		for( int i = index + 1; i < pucks.size(); i++){

			Puck other = pucks.get( i);
			PVector difference = PVector.sub(
				other.position, puck.position);
			//println( String.format("%f %f", difference.x, difference.y));
		}
	}

	public void handleWallCollisions( Puck puck){
		for( Line2D.Double wall : walls){
			//get puck to wall distance
			//PVector distanceVector = pointToLineVector( wall, puck.position);
		}

	}
	/*public PVector pointToLineVector( Line2D.Double line, PVector point){
			PVector pa = new PVector(
				(float) (line.x1 - point.x),
				(float) (line.y1 - point.y));
			//store these
			PVector l = new PVector(
				(float) (line.x2 - line.x1),
				(float) (line.y2 - line.x1));
			PVector perp = new PVector( -l.y, l.x);
	}*/

	//utility functions
	void updateTime(){
		time_old = time;
		time = System.nanoTime();
		dtime = (time - time_old)/1e9;
		if( second != time/nanosecondsPerSecond){
			println( String.format("tps: %d", ticks));
			ticks = 0;
			second = time/nanosecondsPerSecond;
		}
		ticks++;
	}

	void applyTickLimit(){
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
}