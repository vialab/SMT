import vialab.SMT.*;
import java.awt.*;

//vars
int window_width = 1200;
int window_height = 800;

void setup(){
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//SMT.setTouchSourceBoundsActiveDisplay( TouchSource.MOUSE);
	//SMT.setTouchSourceBoundsDisplay( 0, TouchSource.MOUSE);
	//SMT.setTouchSourceBoundsDisplay( 1, TouchSource.MOUSE);
	//SMT.setTouchSourceBoundsDisplay( ":0.0", TouchSource.MOUSE);
	//SMT.setTouchSourceBoundsDisplay( ":0.1", TouchSource.MOUSE);
	//SMT.setTouchSourceBoundsRect(
	//	new Rectangle( 100, 100, 1000, 600), TouchSource.MOUSE);
	//SMT.setTouchSourceBoundsScreen( TouchSource.MOUSE);
	//SMT.setTouchSourceBoundsSketch( TouchSource.MOUSE);
	//SMT.setTouchSourceBoundsCustom( TouchBinder, TouchSource.MOUSE);
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
		PVector position = touch.getBoundPosition();
		String text = String.format(
			"id: %d\n" +
				"port: %d\n" +
				"source: %s\n" +
				"raw: %.3f, %.3f\n" +
				"fitted: %.3f, %.3f\n" +
				"bound: %.3f, %.3f",
			touch.cursorID, touch.sessionID >> 48,
			touch.getTouchSource(),
			touch.getRawX(), touch.getRawY(),
			touch.getX(), touch.getY(),
			position.x, position.y);
		
		pushStyle();
		fill( 240, 240, 240, 180);
		textAlign(LEFT, TOP);
		textSize( 30);
		textMode( SHAPE);
		text( text, touch.x + 10, touch.y);
		popStyle();
	}
}