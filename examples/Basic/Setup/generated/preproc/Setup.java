import processing.core.*; 
import processing.xml.*; 

import vialab.SMT.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Setup extends PApplet {

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


public void setup() {
    size(displayWidth, displayHeight , P3D);
    
    // Initializes SMT
    SMT.init(this, TouchSource.MULTIPLE); 
}
                    
public void draw() { 
}

    static public void main(String args[]) {
        PApplet.main(new String[] { "--bgcolor=#ECE9D8", "Setup" });
    }
}
