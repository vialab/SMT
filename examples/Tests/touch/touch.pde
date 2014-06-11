import vialab.SMT.*;
import java.awt.*;

//vars
int window_width = 1200;
int window_height = 800;
//others
boolean draw_fps = true;
boolean draw_count = true;
boolean draw_info = false;


//main methods

void setup(){
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
	textMode( SHAPE);

	//select touch drawer
	SMT.setTouchDraw( TouchDraw.TEXTURED);
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
		text( text, touch.x + 10, touch.y);
		popStyle();
	}
}

//keyboard handle
void keyPressed(){
	//println( key);
	switch( key){
		case 'a':{
			draw_fps = ! draw_fps;
			break;}
		case 's':{
			draw_count = ! draw_count;
			break;}
		case 'd':{
			draw_info = ! draw_info;
			break;}
		case 'f':{
			SMT.setTrailEnabled( ! SMT.getTrailEnabled());
			break;}
		default: break;
	}
}