/**
 *  This examples show the use of the CheckBoxZone, and its state.
 *  It also shows use of getZone() to do a class cast to access
 *  the CheckBoxZone specific variable.
 */

import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  SMT.init(this, TouchSource.MOUSE);
  SMT.add(new CheckBoxZone("Checkbox",100,100,200,200));
}
void draw() {
  background(79, 129, 189);
  text("Checked:"+SMT.get("Checkbox",CheckBoxZone.class).checked,50,50);
}
