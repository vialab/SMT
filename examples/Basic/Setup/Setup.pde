/*
 * The SMT is the main component in applications 
 * that use Simple Multi-Touch. For zones to be drawn or 
 * interacted with, they have to be added to the SMT. 
 * You must set an input source for the application/sketch. 
 * Here, the input source is set to 'multiple', which
 * means that it will accept input from various
 * sources/protocols.
 */

// Imports SMT
import vialab.SMT.*;

void setup() {
    size(displayWidth, displayHeight , SMT.RENDERER);
    
    // Initializes SMT
    SMT.init(this, TouchSource.AUTOMATIC); 
}
                    
void draw() { 
}
