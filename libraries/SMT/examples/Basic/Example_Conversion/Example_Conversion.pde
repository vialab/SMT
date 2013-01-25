//comment out these setup and draw methods to run SMT conversion of this processing code
/*
void setup() {
  size(displayWidth, displayHeight, P3D);
}

void draw() {
  background(79, 129, 189);
  fill(255);
  rect(100, 100, 100, 100);
  ellipse(100, 100, 100, 100);
}
*/

//Comment out everything past here to run just processing code

import vialab.SMT.*;

void setup() {
  size(displayWidth, displayHeight, P3D);
  
  //This provides the multi-touch functionality, and TouchSource selection
  //change MOUSE to SMART to switch from using the mouse for
  //input to Smart table multitouch input for example 
  TouchClient.init(this, TouchSource.MOUSE);
  
  //this adds a zone to the TouchClient, with the given parameters
  //new Zone(String name, int x, int y, int width, int height)
  TouchClient.add(new Zone("MyZone", 0, 0, 200, 200));
}

//same as processing, except we take out the part
//that we put in the zone
void draw() {
  background(79, 129, 189);
}

//this adds functionality that was not in the pure processing code
//allowing what was drawn can now be rotated scaled and translated
//(rst for short)
void touchMyZone(Zone zone) {
  zone.rst();
}

//this is the part of the processing code we removed from draw()
//since we want it to be multitouch capable
void drawMyZone() {
  fill(255);
  rect(100, 100, 100, 100);
  ellipse(100, 100, 100, 100);
}

