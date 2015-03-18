/**
 * This shows using Zone.assign() to allow a custom implementation
 * of the mapping of Touches to Zones, in this case making all
 * Touches be assigned to the Zone, instead of the default of
 * those that are in the area of the Zone.
 */
import vialab.SMT.*;

Zone zone;

void setup() {
	//initial setup
	size( displayWidth, displayHeight, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
	zone = new Zone("Test",0,0,50,50);
	SMT.add(zone);
}

void draw() {
	background(79, 129, 189);
	text(round(frameRate)+"fps", width/2, 10);
}

void drawTest(Zone zone){
	background(255);
}

void touchTest(Zone zone){
	zone.rst(); 
}

void touchDown(){
	zone.assign(SMT.getTouches()); 
}

