import vialab.SMT.*;

void setup(){
	size( 800, 600, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
}
void draw(){
	background( 30);

	int count = SMT.getTouchCount();
	//System.out.printf( "Number of touches: %d\n", count);

	pushStyle();
	textAlign(CENTER, CENTER);
	textSize( 200);
	text( count, 400, 300);
	popStyle();
}
