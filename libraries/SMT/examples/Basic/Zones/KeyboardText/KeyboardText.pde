import vialab.SMT.*;
void setup() {
  size(800, 800, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.add(new KeyboardZone("Keyboard"));
  TouchClient.add(new TextZone("Text",0,400,200,200));
  TouchClient.getZone("Keyboard",KeyboardZone.class).addKeyListener(TouchClient.getZone("Text"));
}
void draw() {
  background(79, 129, 189);
}



