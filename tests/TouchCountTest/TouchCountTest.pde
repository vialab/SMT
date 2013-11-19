import vialab.SMT.*;

void setup(){
	size( 800, 600, P3D);
	SMT.init( this, TouchSource.MULTIPLE);}
void draw(){
	background( 0);
	System.out.printf( "Number of touches: %d\n", SMT.getTouchCount());}