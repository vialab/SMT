import vialab.SMT.*;

void setup(){
	size( 1200, 800, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
}
void draw(){
	background( 30);

	for( Touch touch : SMT.getTouches()){
		String text = touch == null ? "null" :
			String.format( "cursor id: %d\nport: %d",
				touch.cursorID, touch.sessionID >> 48);
		
		pushStyle();
		textAlign(LEFT, TOP);
		textSize( 30);
		text( text, touch.x+10, touch.y);
		popStyle();
	}
}