/**
 *  This examples shows the use of the KeyboardZone, which is an
 *  on-screen keyboard. The pressed keys are print()'ed
 */

import vialab.SMT.*;
void setup() {
  size(800, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.add(new KeyboardZone());
}
void draw() {
  background(79, 129, 189);
}
void keyTyped(){
  print(key);
}
