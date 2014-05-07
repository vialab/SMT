import vialab.SMT.*;

//vars
int window_width = 1200;
int window_height = 800;

void setup(){
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
}

void draw(){
	background( 30);
	drawTouchCount();
	drawTouchInfo();
}

public void drawTouchCount(){
	int count = SMT.getTouchCount();
	String count_text = String.valueOf( count);
	pushStyle();
	fill( 240, 240, 240, 180);
	textAlign(CENTER, CENTER);
	textSize( 200);
	text( count_text, window_width / 2, window_height / 2);
	popStyle();
}
public void drawTouchInfo(){
	for( Touch touch : SMT.getTouches()){
		String text = String.format(
			"id: %d\n" +
				"port: %d\n" +
				"source: %s\n" +
				"raw: %.3f, %.3f\n" +
				"fitted: %.3f, %.3f",
			touch.cursorID, touch.sessionID >> 48,
			touch.getTouchSource(),
			touch.getRawX(),touch.getRawY(),
			touch.getX(),touch.getY());
		
		pushStyle();
		fill( 240, 240, 240, 180);
		textAlign(LEFT, TOP);
		textSize( 30);
		text( text, touch.x + 10, touch.y);
		popStyle();
	}
}