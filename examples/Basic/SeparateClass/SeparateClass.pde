/**
 *  This example shows how methods in another object (CustomZone)
 *  can be used as if they were in this class by adding the object
 *  with a call to TouchClient.addMethodClasses(), and a call to
 *  Zone.setBoundObject() for each Zone using these methods
 *
 *  This allows seperation of large projects into multiple classes
 *  without giving up the use of name based functions.
 *
 *  TouchClient.addMethodClasses() should be called with the
 *  class as a parameter before instantiation of Zones that will
 *  use these methods.
 *
 *  Zone.setBoundObject() allows specifying the object that will
 *  be referred to when using the 'this' keyword, when a method
 *  from the Zone is run.
 */
import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.addMethodClasses(CustomZone.class);
  TouchClient.add(new Zone("MyZone1",0,0,200,200));
  TouchClient.add(new Zone("MyZone2",200,0,200,200));
  CustomZone z = new CustomZone();
  CustomZone z2 = new CustomZone();
  TouchClient.get("MyZone1").setBoundObject(z);
  TouchClient.get("MyZone2").setBoundObject(z2);
}
void draw() {
  background(79, 129, 189);
}

