/*
 * To draw in a zone, you must create a function called "draw" 
 * followed by the zone's name. The first letter of the zone's 
 * name must be capitalized in this function. In our example, 
 * the function is called "drawMyZone" because we named our 
 * zone "myZone". Use Processing graphics function calls to 
 * draw in the zone. The coordinates given to these function 
 * calls are relative to the zone itself, i.e. drawing something 
 * at (0,0) will draw it at the left corner of the zone. This 
 * draw function takes a zone as a parameter, which is used to 
 * interact with the zone itself from within the function.
 */
 
import vialab.SMT.*;

void setup() {
	size(displayWidth, displayHeight, P3D);  
	SMT.init(this, TouchSource.MULTIPLE);
	
	Zone z = new Zone("myZone", 10, 10, 50, 50);
	SMT.add(z);
}
void draw() {
}

// Draw function for "myZone"
void drawMyZone(Zone z){ 
	rect(0, 0, 50, 50);
}
