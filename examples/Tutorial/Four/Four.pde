import vialab.SMT.*;

//Setup function for the applet
void setup(){
	//SMT and Processing setup
	size( 800, 600, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//Make a new Zone
	//Single transformation zones
	Zone spinny = new Zone( "SpinnyZone", 110, 10, 100, 100);
	Zone draggy = new Zone( "DraggyZone", 220, 10, 100, 100);
	Zone scaley = new Zone( "ScaleyZone", 330, 10, 100, 100);

	//Double transformations
	Zone spinnydrag = new Zone( "SpinnyDragZone", 110, 210, 100, 100);
	Zone draggyscale = new Zone( "DraggyScaleZone", 220, 210, 100, 100);
	Zone scaleyspin = new Zone( "ScaleySpinZone", 330, 210, 100, 100);

	//Rst Zones
	Zone rst_a = new Zone( "RstZoneA", 110, 410, 100, 100);
	Zone rst_b = new Zone( "RstZoneB", 220, 410, 100, 100);
	Zone rst_c = new Zone( "RstZoneC", 330, 410, 100, 100);
	Zone rst_d = new Zone( "RstZoneD", 440, 410, 100, 100);
	Zone rst_e = new Zone( "RstZoneE", 550, 410, 100, 100);
	Zone rst_f = new Zone( "RstZoneF", 660, 410, 100, 100);

	//add all the zones
	SMT.add( spinny);
	SMT.add( draggy);
	SMT.add( scaley);
	SMT.add( spinnydrag);
	SMT.add( draggyscale);
	SMT.add( scaleyspin);
	SMT.add( rst_a);
	SMT.add( rst_b);
	SMT.add( rst_c);
	SMT.add( rst_d);
	SMT.add( rst_e);
	SMT.add( rst_f);
}

//Draw function for the sketch
void draw(){
	background( 30);
}

//touch functions for zones

void drawSpinnyZone( Zone zone){
	noStroke();
	fill( 140, 160, 200, 180);
	rect( 0, 0, zone.width, zone.height);
}
void touchSpinnyZone( Zone zone){
	//this zone only spins
	zone.rotate();
}

void drawDraggyZone( Zone zone){
	noStroke();
	fill( 140, 160, 200, 180);
	rect( 0, 0, zone.width, zone.height);
}
void touchDraggyZone( Zone zone){
	//this zone only drags
	zone.drag();
}

void drawScaleyZone( Zone zone){
	noStroke();
	fill( 140, 160, 200, 180);
	rect( 0, 0, zone.width, zone.height);
}
void touchScaleyZone( Zone zone){
	//this zone only scales
	//zone.scale();
}

void drawSpinnyDragZone( Zone zone){
	noStroke();
	fill( 140, 160, 200, 180);
	rect( 0, 0, zone.width, zone.height);
}
void touchSpinnyDragZone( Zone zone){
	//this zone spins and drags
	zone.rnt();
}

void drawDraggyScaleZone( Zone zone){
	noStroke();
	fill( 140, 160, 200, 180);
	rect( 0, 0, zone.width, zone.height);
}
void touchDraggyScaleZone( Zone zone){
	//this zone drags and scales
	zone.pinch();
}

void drawScaleySpinZone( Zone zone){
	noStroke();
	fill( 140, 160, 200, 180);
	rect( 0, 0, zone.width, zone.height);
}
void touchScaleySpinZone( Zone zone){
	//this zone spins and scales
	zone.rs();
}


//Overloads are available for rst()
void drawRstZoneA( Zone zone){
	noStroke();
	fill( 140, 160, 200, 180);
	rect( 0, 0, zone.width, zone.height);
}
void touchRstZoneA( Zone zone){
	//this zone spins
	zone.rst();
}

void drawRstZoneB( Zone zone){
	noStroke();
	fill( 140, 160, 200, 180);
	rect( 0, 0, zone.width, zone.height);
}
void touchRstZoneB( Zone zone){
	//this zone spins
	zone.rst( false, true, true);
}

void drawRstZoneC( Zone zone){
	noStroke();
	fill( 140, 160, 200, 180);
	rect( 0, 0, zone.width, zone.height);
}
void touchRstZoneC( Zone zone){
	//this zone spins
	zone.rst( true, false, true);
}

void drawRstZoneD( Zone zone){
	noStroke();
	fill( 140, 160, 200, 180);
	rect( 0, 0, zone.width, zone.height);
}
void touchRstZoneD( Zone zone){
	//this zone spins
	zone.rst( true, true, false);
}

void drawRstZoneE( Zone zone){
	noStroke();
	fill( 140, 160, 200, 180);
	rect( 0, 0, zone.width, zone.height);
}
void touchRstZoneE( Zone zone){
	//this zone spins
	zone.rst( true, true, true, false);
}

void drawRstZoneF( Zone zone){
	noStroke();
	fill( 140, 160, 200, 180);
	rect( 0, 0, zone.width, zone.height);
}
void touchRstZoneF( Zone zone){
	//this zone spins
	zone.rst( true, true, false, true);
}