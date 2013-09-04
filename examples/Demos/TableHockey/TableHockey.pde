
/**
 * A Table Hockey game, made with SMT
 */

//standard library imports
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
	pucks.add( new Puck( 0, 0, 100));
	pucks.add( new Puck( 0, 0, 100));

	//add pucks to smt
	for( Puck puck : pucks){
		SMT.add( puck);
	}

	//start up the physics engine
	//physics.start();
}

void draw(){
	print("tick\n");
	background( 0, 0, 0);
	physics.tick();
}

void stop(){
	physics.terminate = true;
	super.stop();
}

//utility functions
private float getTime(){
	return (float) System.currentTimeMillis()/1000;
}

//classes
class Puck extends Zone {
	//static fields
	final static String name = "Puck";
	//fields
	float radius;
	PVector velocity;

	//constructor
	Puck(int x, int y, int radius) {
		super(name, x, y, radius*2, radius*2 );
		this.radius = radius;
		velocity = new PVector( 10, 10);
	}

	//methods
	void drawImpl(){
		fill( 150, 50, 50);
		ellipse( this.radius, this.radius, this.width, this.height);
	}
	void pickDrawImpl() {
		ellipse( this.radius, this.radius, this.width, this.height);
	}
	void touchImpl(){
		Touch touch = getActiveTouch(0);
		assert( touch != null);

		float dx = touch.x - this.x;
		float dy = touch.y - this.y;

		drag();
	}
} //

class Physics extends Thread {
	//constants
	public final int ticksPerSecond = 50;
	public final float secondsPerTick = 1/ticksPerSecond;
	//public variables
	public boolean terminate = false;
	//private variables
	private float time;
	private float time_old;
	private float dtime;

	public Physics(){
		time = getTime();
		time_old = time;
		dtime = 0;
	}

	public void run(){
		while( !terminate){
			tick();
		}
	}

	public void tick(){
		updateTime();
		for( Puck puck : pucks){
			puck.translate(
				puck.velocity.x * dtime,
				puck.velocity.y * dtime);
		}
	}

	void applyTickLimit(){
		updateTime();
		try {
			float timeLeft = secondsPerTick - dtime;
			if( timeLeft > 0){
				Thread.sleep( round( timeLeft));
			}
		} catch( InterruptedException e){}
	}
	void updateTime(){
			time_old = time;
			time = getTime();
			dtime = time - time_old;
	}
}