/**
 *  A photo album sketch!
 *  This sketch loads a few images, and
 *  puts them in randomly sized ImageZones. The ImageZones are
 *  configured to rotated, scaled, and translated, and to also
 *  be brought to the top layer when touched
 */
import vialab.SMT.*;

void setup() {
  //make a fullscreen sketch and start the touchclient with the
  //multiple backend, to use any device detected
  size(displayWidth, displayHeight, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);

  //load 5 image zones with random size + position
  for (int i=0; i<5; i++) {  
    SMT.add(new ImageZone("ImageZone", loadImage(i + ".jpg"), 
    (int)random(0, displayWidth-200), (int)random(0, displayHeight-200), 
    (int)random(100, 200), (int)random(100, 200)));
  }
}

void touchImageZone(Zone z) {
  //tell image zones to be put on the top when touched, and to
  //rotate scale and tranlate
  SMT.putZoneOnTop(z);
  z.rst();
}

void draw() {
  //draw the background and fps
  background(79, 129, 189);
  text(round(frameRate)+" fps", width/2, 10);
}

