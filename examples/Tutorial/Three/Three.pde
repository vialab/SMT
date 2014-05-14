import vialab.SMT.*;

//Setup function for the applet
void setup(){
	//SMT and Processing setup
	size( displayWidth, displayHeight, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//Make a new Zone
	Zone zone = new Zone( "MyZone");
	SMT.add( zone);
	zone.translate( 100, 100);

	//Make a child Zone
	Zone child = new Zone( "ChildZone");
	zone.add( child);
	child.translate( 100, 100);

	//Make a grandchild Zone
	Zone grandchild = new Zone( "GrandChildZone");
	child.add( grandchild);
	grandchild.translate( 50, 50);
}

//Draw function for the sketch
void draw(){
	background( 30);
}


// "MyZone" functions

//Functions for "MyZone"
void drawMyZone( Zone zone){
	noStroke();
	fill( #00bbbb);
	rect(0, 0, 400, 400);
}
void touchMyZone( Zone zone){
	zone.rst();
}

// "ChildZone" functions

//Functions for "ChildZone"
void drawChildZone( Zone zone){
	noStroke();
	fill( #88dd88);
	rect(0, 0, 200, 200);
}
void touchChildZone( Zone zone){
	zone.rst();
}

// "GrandChildZone" functions

//Functions for "GrandChildZone"
void drawGrandChildZone( Zone zone){
	noStroke();
	fill( #aa66aa);
	rect(0, 0, 100, 100);
}
void touchGrandChildZone( Zone zone){
	zone.rst();
}
