/**
 * Sketch for testing touch input data
 */

//imports
import vialab.SMT.*;
import java.awt.*;

//vars
int window_width = 1200;
int window_height = 800;
//keyboard shortcuts
final char key_fps = 'a';
final char key_count = 's';
final char key_info = 'd';
final char key_trail = 'f';
//others
boolean draw_fps = true;
boolean draw_count = true;
boolean draw_info = true;

//main methods
void setup(){
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
	textMode( MODEL);
}

void draw(){
	background( 30);
	if( draw_fps) drawFrameRate();
	if( draw_count) drawTouchCount();
	if( draw_info) drawTouchInfo();
}

//drawing methods
public void drawFrameRate(){
	float fps = this.frameRate;
	String fps_text = String.format( "fps: %.0f", fps);
	pushStyle();
	fill( 240, 240, 240, 180);
	textAlign( LEFT, TOP);
	textSize( 32);
	text( fps_text, 10, 10);
	popStyle();
}
public void drawTouchCount(){
	int count = SMT.getTouchCount();
	String count_text = String.valueOf( count);
	pushStyle();
	fill( 240, 240, 240, 180);
	textAlign( CENTER, CENTER);
	textMode( SHAPE);
	textSize( 200);
	text( count_text, window_width / 2, window_height / 2);
	popStyle();
}
public void drawTouchInfo(){
	for( vialab.SMT.Touch touch : SMT.getTouches()){
		String touch_text = String.format(
			"id: %d\n" +
				"port: %d\n" +
				"source: %s\n" +
				"raw: %.2f, %.2f\n" +
				"fitted: %.2f, %.2f\n" +
				"rounded: %d, %d",
			touch.cursorID, touch.sessionID >> 48,
			touch.getTouchSource(),
			touch.getRawX(), touch.getRawY(),
			touch.getX(), touch.getY(),
			touch.x, touch.y);
		
		pushStyle();
		noFill();
		stroke( 240, 240, 240, 180);
		strokeWeight( 8);
		ellipse( touch.getX(), touch.getY(), 50, 50);
		fill( 240, 240, 240, 180);
		textAlign(LEFT, TOP);
		textSize( 30);
		text( touch_text, touch.x + 50, touch.y - 20);
		popStyle();
	}
}

//keyboard handle
void keyPressed(){
	//println( key);
	switch( key){
		case key_fps:{
			draw_fps = ! draw_fps;
			break;}
		case key_count:{
			draw_count = ! draw_count;
			break;}
		case key_info:{
			draw_info = ! draw_info;
			break;}
		case key_trail:{
			SMT.setTrailEnabled( ! SMT.getTrailEnabled());
			break;}
		default: break;
	}
}