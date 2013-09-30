import vialab.SMT.*;
//Setup function for the applet
void setup(){
	//SMT and Processing setup
	size(displayWidth, displayHeight, P3D);
	SMT.init(this, TouchSource.MULTIPLE);

	//Make a new Zone
	Zone zone = new Zone( "MyZone");
	SMT.add( zone);
}

//Draw function for the sketch
void draw(){
	background( 51);
}

//Draw function for "MyZone"
void drawMyZone(Zone zone){
	rect(0, 0, 100, 100);
}

void pickDrawMyZone(Zone zone){
	rect(0, 0, 100, 100);
}
//Touch function for "MyZone"
void touchMyZone(Zone zone){
	zone.drag();
}