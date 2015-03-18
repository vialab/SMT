/*
 * To make something happen when a zone is touched, you must 
 * create a function called "touch" followed by the zone's name. 
 * The first letter of the zone's name must be capitalized in 
 * this function. In our example, the function is called 
 * "touchMyZone" because we named our zone "myZone". This 
 * function takes a zone as a parameter, which is used to 
 * interact with the zone itself. In this example, when "myZone"
 * is touched, SMT looks to see if a RST (rotate, scale, translate) 
 * gesture has been performed on this zone. If it has, the 
 * gesture event will be executed.
 */
 
import vialab.SMT.*;

void setup() {
    size(displayWidth, displayHeight, SMT.RENDERER);  
    SMT.init(this, TouchSource.AUTOMATIC);
    
    Zone z = new Zone("myZone", 10, 10, 50, 50);
    SMT.add(z);
}
                    
void draw() {
}

void drawMyZone(Zone z){
    rect(0, 0, 50, 50);
}

// Executes when "myZone" is touched
void touchMyZone(Zone z){ 
   // Check out the gesture example for RST 
   z.rst();  
}
