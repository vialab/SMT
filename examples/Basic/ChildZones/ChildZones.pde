/**
 * This shows Zones as children of other Zones by using add().
 * This makes the Zones have a child/parent relationship that
 * gives positional/rotational/translation inheritance.
 */

import vialab.SMT.*;

void setup() {
	size(400, 400, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
	Zone z = new Zone("Parent", 100, 100, 200, 200);
	z.add(new Zone("Child", 0, 0, 100, 100));
	SMT.add(z);
}

void draw() {
	background(79, 129, 189);
}

void touchParent(Zone z) {
	z.rst();
}

void drawParent(Zone z) {
	background(144, 202, 119);
	fill(0);
	text("Parent",z.width/2, z.height/2);
}

void touchChild(Zone z) {
	z.rst();
}

void drawChild(Zone z) {
	background(228, 135, 67);
	fill(0);
	text("Child",z.width/2, z.height/2);
}
