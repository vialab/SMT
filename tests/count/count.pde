import vialab.SMT.*;

void setup(){
	size( 800, 600, P3D);
	SMT.init( this, TouchSource.TUIO_DEVICE);
}
void draw(){
	background( 30);
	System.out.printf(
		"Number of touches: %d\n",
		SMT.getTouchCount());
}
