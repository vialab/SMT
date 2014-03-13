import vialab.SMT.*;

void setup(){
	size( 800, 600, P3D);
	SMT.init( this, TouchSource.WM_TOUCH);}
void draw(){
	background( 30);
	System.out.printf( "Number of touches: %d\n", SMT.getTouchCount());}
