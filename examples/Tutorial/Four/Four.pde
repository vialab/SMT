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
}

//Draw function for the sketch
void draw(){
	background( 30);
}
