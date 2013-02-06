package processing.test.test_android;

import processing.core.*; 
import processing.data.*; 
import processing.opengl.*; 

import vialab.SMT.*; 
import TUIO.*; 

import android.view.MotionEvent; 
import android.view.KeyEvent; 
import android.graphics.Bitmap; 
import java.io.*; 
import java.util.*; 

public class Test_Android extends PApplet {

/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   A test sketch using simpleMultiTouch toolkit
 */



TouchClient client;

public void setup() {
  //frameRate(1000);
 
  client = new TouchClient(this, TouchSource.ANDROID);
  client.setDrawTouchPoints(true,100);
}

public void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps", width/2, 10);
}


  public int sketchWidth() { return displayWidth; }
  public int sketchHeight() { return displayHeight; }
  public String sketchRenderer() { return P3D; }
}
