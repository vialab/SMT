import vialab.SMT.*;

//variables for this applet
color myZone_color;
boolean flag = false;
Zone myZone;

//Setup function for the applet
void setup(){
	//SMT and Processing setup
	size(displayWidth, displayHeight, P3D);
	SMT.init(this, TouchSource.AUTOMATIC);

	//Make a new Zone
	myZone = new Zone( "MyZone");
	myZone.translate( 200, 200);
	SMT.add( myZone);

	//Make another Zone
	Zone other = new Zone( "MyOtherZone");
	other.translate( 400, 200);
	SMT.add( other);

	myZone_color = #00dddd;
}

//Draw function for the sketch
void draw(){
	background( 51);
}

// "MyZone" functions

//Draw functions for "MyZone"
void drawMyZone( Zone zone){
	if( flag) return;
	noStroke();
	fill( myZone_color);
	rect(0, 0, 100, 100);
}

void pickDrawMyZone( Zone zone){
	if( flag) return;
	rect(0, 0, 100, 100);
}

//Touch function for "MyZone"
void touchMyZone( Zone zone){
	zone.drag();
}

// "MyOtherZone" functions

//Draw functions for "MyOtherZone"
void drawMyOtherZone( Zone zone){
	noStroke();
	fill( #88dd88);
	rect(0, 0, 100, 100);
}

void pickDrawMyOtherZone( Zone zone){
	rect(0, 0, 100, 100);
}

void touchMyOtherZone( Zone zone){}
void touchDownMyOtherZone( Zone zone){
	flag = ! flag;
}