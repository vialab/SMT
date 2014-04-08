import vialab.SMT.*;
//Setup function for the applet
void setup(){
	//SMT and Processing setup
	size(displayWidth, displayHeight, SMT.RENDERER);
	SMT.init(this, TouchSource.AUTOMATIC);

	//Make a new Zone
	Zone zone = new Zone( "myZone");
	SMT.add( zone);
}
//Draw function for the sketch
void draw(){
	background( 51);
}

//Draw function for "myZone"
void drawMyZone(Zone zone){
	rect(0, 0, 100, 100);
}
void pickDrawMyZone(Zone zone){
	rect(0, 0, 100, 100);
}
//Touch function for "myZone"
void touchMyZone(Zone zone){
	zone.rst();
}