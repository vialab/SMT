import vialab.SMT.*;

//Setup function for the applet
void setup(){
	//SMT and Processing setup
	size(displayWidth, displayHeight, P3D);
	textMode( SHAPE);
	SMT.init(this, TouchSource.MULTIPLE);

	//Make a new Zone
	Zone zone = new Zone( "MyZone");
	SMT.add( zone);
	zone.translate( 100, 100);

	//Make a child Zone
	Zone child = new Zone( "ChildZone");
	SMT.add( child);
	zone.add( child);
	child.translate( 100, 100);

	//Make a grandchild Zone
	Zone grandchild = new Zone( "GrandChildZone");
	SMT.add( grandchild);
	child.add( grandchild);
	grandchild.translate( 50, 50);
}

//Draw function for the sketch
void draw(){
	background( 51);
}


// "MyZone" functions

//Draw functions for "MyZone"
void drawMyZone( Zone zone){
	noStroke();
	fill( #00bbbb);
	rect(0, 0, 400, 400);
}
void pickDrawMyZone( Zone zone){
	rect(0, 0, 400, 400);
}


// "ChildZone" functions

//Draw functions for "MyZone"
void drawChildZone( Zone zone){
	noStroke();
	fill( #88dd88);
	rect(0, 0, 200, 200);
}
void pickDrawChildZone( Zone zone){
	rect(0, 0, 200, 200);
}

// "GrandChildZone" functions

//Draw functions for "MyZone"
void drawGrandChildZone( Zone zone){
	noStroke();
	fill( #aa66aa);
	rect(0, 0, 100, 100);
}
void pickDrawGrandChildZone( Zone zone){
	rect(0, 0, 100, 100);
}