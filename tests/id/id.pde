import vialab.SMT.*;

void setup(){
	size( 1200, 800, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
	frame.setResizable( true);
}
void draw(){
	background( 30);
	drawTouchInfo();
	System.out.printf(
		"Size: %d, %d\n",
		this.width, this.height);
}

public void drawTouchInfo(){
	for( Touch touch : SMT.getTouches()){
		String text = touch == null ? "null" :
			String.format(
				"id: %d\n" +
					"port: %d\n" +
					"source: %s\n" +
					"raw: %.3f, %.3f" +
					"fitted: %.3f, %.3f",
				touch.cursorID, touch.sessionID >> 48,
				touch.getTouchSource(),
				touch.getRawX(),touch.getRawY(),
				touch.getX(),touch.getY());
		
		pushStyle();
		textAlign(LEFT, TOP);
		textSize( 30);
		text( text, touch.x + 10, touch.y);
		popStyle();
	}
}