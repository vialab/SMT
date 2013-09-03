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

public class MinimalSketch extends PApplet {

/**
 *  This sketch shows the minimum amount of SMT needed to run,
 *  and so is a good starting point for a new sketch.
 */


public void setup(){
  size(400,400,P3D);
  SMT.init(this, TouchSource.MULTIPLE);
}
public void draw(){
  background(125);
}

    static public void main(String args[]) {
        PApplet.main(new String[] { "--bgcolor=#ECE9D8", "MinimalSketch" });
    }
}
