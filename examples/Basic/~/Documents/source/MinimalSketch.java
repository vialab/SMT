import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import vialab.SMT.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "MinimalSketch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
