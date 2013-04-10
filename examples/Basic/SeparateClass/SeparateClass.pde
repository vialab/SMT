/**
 *  This example shows how methods in another object (CustomZone)
 *  can be used as if they were in this class by adding the object
 *  with a call to TouchClient.addMethodObjects().
 *
 *  This allows seperation of large projects into multiple classes
 *  without giving up the use of name based functions.
 *
 *  TouchClient.addMethodObjects() should be called with the
 *  object as a parameter before instantiation of Zones that will
 *  use these methods.
 */
import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.addMethodObjects(new CustomZone());
  TouchClient.add(new Zone("MyZone1",0,0,200,200));
  TouchClient.add(new Zone("MyZone2",200,0,200,200));
}
void draw() {
  background(79, 129, 189);
}

