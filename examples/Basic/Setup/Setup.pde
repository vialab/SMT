/*
 * The TouchClient is the main component in applications 
 * that use Simple Multi-Touch. For zones to be drawn or 
 * interacted with, they have to be added to the TouchClient. 
 * You must set an input source for the application/sketch. 
 * Here, the input source is set to 'mouse'.
 */

// Imports SMT
import vialab.SMT.*;

void setup() {
    size(displayWidth, displayHeight , P3D);
    
    // Initializes SMT
    TouchClient.init(this, TouchSource.MOUSE); 
}
                    
void draw() { 
}
