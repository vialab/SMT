
/**
 * A Table Hockey game, made with SMT
 */
import vialab.SMT.*;

//variables
final int display_width = 1200;
final int display_height = 800;

//main functions
void setup() {
	size( 1200, 800, P3D);
	SMT.init( this, TouchSource.MULTIPLE);
	SMT.add( new Puck( 0, 0, 100));
}

void draw() {
	background( 0, 0, 0);
}

//utility functions

//classes
class Puck extends Zone {
	final static String name = "Puck";
	float r;
	Puck(int x, int y, int r) {
		super(name, x, y, r*2, r*2 );
		this.r = r;
	}
}
void drawPuck( Puck puck){
	print("draw\n");
  fill( 150, 50, 50);
  ellipse( 0, 0, puck.width, puck.height);
}
void pickPuck(Puck puck) {
	print("pick\n");
  ellipse( 0, 0, puck.width, puck.height);
}
void touchPuck( Puck puck){
	print("touch\n");
}

