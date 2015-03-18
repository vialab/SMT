/**
 * Sketch for testing display info and touch source bindings
 */

//standard library imports
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

//smt imports
import vialab.SMT.*;
import vialab.SMT.util.*;

//main vars
int window_width = 1200;
int window_height = 800;
boolean window_fullscreen = false;
SystemAdapter adapter = null;
long last_update = -1;

//info vars
String activeDisplay_id;
int activeDisplay_index;
Rectangle[] display_bounds;
int display_count = 0;
String[] display_ids;
String[] display_text;
Rectangle screen_bounds;
String screen_text;
Rectangle sketch_bounds;
String sketch_text;

//drawing vars
Rectangle2D.Float vscreen = new Rectangle2D.Float();
PMatrix2D vmatrix;

void setup(){
	if( window_fullscreen){
		window_width = displayWidth;
		window_height = displayHeight;
	}
	size( window_width, window_height, SMT.RENDERER);
	SMT.setWarnUnimplemented( false);
	SMT.init( this, TouchSource.AUTOMATIC);
	adapter = SMT.getSystemAdapter();

	//choose touch source bounds method
	//SMT.setTouchSourceBoundsActiveDisplay();
	//SMT.setTouchSourceBoundsDisplay( 0);
	//SMT.setTouchSourceBoundsDisplay( ":0.1");
	//SMT.setTouchSourceBoundsRect(
	//	new Rectangle( 100, 100, window_width - 200, window_height - 200));
	//SMT.setTouchSourceBoundsScreen();
	//SMT.setTouchSourceBoundsSketch();
}

void update(){
	//update info
	adapter.update();
	if( adapter.getLastUpdateTime() > last_update){
		last_update = adapter.getLastUpdateTime();

		//update displays
		if( display_count != adapter.getDisplayCount()){
			display_count = adapter.getDisplayCount();
			display_ids = adapter.getDisplayIDs();
			display_bounds = adapter.getDisplayBounds();
			screen_bounds = adapter.getScreenBounds();
			screen_text = String.format(
				"Screen: %d, %d :: %d, %d\n",
				screen_bounds.x, screen_bounds.y,
				screen_bounds.width, screen_bounds.height);
			updateVMatrix();
		}
		
		activeDisplay_id = adapter.getActiveDisplayID();
		activeDisplay_index = adapter.getActiveDisplayIndex();
		updateDisplayTexts();
		sketch_bounds = adapter.getSketchBounds();
		sketch_text = String.format( 
			"Sketch: %d, %d :: %d, %d",
			sketch_bounds.x, sketch_bounds.y,
			sketch_bounds.width, sketch_bounds.height);
	}
}

void updateDisplayTexts(){
	display_text = new String[ display_count];
	for( int i = 0; i < display_count; i++)
		display_text[i] = String.format(
			"Device %d :: ID %s\n" +
				"Bounds %d, %d :: %d, %d\n" +
				"Current: %s",
			i, display_ids[ i],
			display_bounds[ i].x, display_bounds[ i].y,
			display_bounds[ i].width, display_bounds[ i].height,
			String.valueOf( i == activeDisplay_index));
}

void updateVMatrix(){
	//prepare the virtual screen matrix
	double max_width = window_width * 0.95;
	double max_height = window_height * 0.95;
	//find best ratio
	double ratiox = screen_bounds.width / max_width;
	double ratioy = screen_bounds.height / max_height;
	double ratio = Math.max( ratiox, ratioy);
	//set dimensions
	vscreen.width = (float) ( screen_bounds.width / ratio);
	vscreen.height = (float) ( screen_bounds.height / ratio);
	//set position
	vscreen.x = ( window_width - vscreen.width) / 2.0;
	vscreen.y = ( window_height - vscreen.height) / 2.0;
	//save transformations
	vmatrix = new PMatrix2D();
	vmatrix.translate( vscreen.x, vscreen.y);
	vmatrix.scale(
		(float) ( 1 / ratio),
		(float) ( 1 / ratio));
	vmatrix.translate( - screen_bounds.x, - screen_bounds.y);
}

void draw(){
	update();

	//draw background
	background( 30);

	//draw setup
	pushStyle();
	ortho();

	//draw screen text
	drawScreenText();

	//apply virtual screen matrix
	pushMatrix();
	applyMatrix( vmatrix);

	//draw screen bounds
	drawScreenBounds();

	//draw displays
	for( int i = 0; i < display_count; i++)
		drawDisplay( i);

	//draw sketch
	drawSketch();

	//draw mouse
	drawMouse();

	//draw touches
	drawTouches();

	//cleanup
	popMatrix();
	popStyle();
}
void touchView( Zone zone){}

void drawDisplay( int i){
	//prep
	Rectangle bounds = display_bounds[ i];
	String id = display_ids[ i];
	boolean current = i == activeDisplay_index;
	String text = display_text[ i];

	//draw device bounds
	if( current)
		fill( 10, 10, 10, 220);
	else
		fill( 20, 20, 20, 180);
	stroke( 180, 230, 200, 240);
	strokeWeight( 1);
	rect(
		bounds.x, bounds.y,
		bounds.width, bounds.height);
	//draw device text
	fill( 180, 230, 200, 240);
	textAlign( LEFT, TOP);
	textSize( 50);
	textMode( MODEL);
	text( text, bounds.x + 50, bounds.y + 50);
}

void drawMouse(){
	PointerInfo pinfo = MouseInfo.getPointerInfo();
	Point mouse = pinfo.getLocation();
	String mouse_text = String.format(
		"Mouse:\n%d, %d", mouse.x, mouse.y);
	// draw circle
	noFill();
	stroke( 140, 220, 200, 200);
	strokeWeight( 2);
	ellipse( mouse.x, mouse.y, 35, 35);
	// draw dot
	fill( 140, 220, 200, 200);
	noStroke();
	ellipse( mouse.x, mouse.y, 5, 5);
	// draw mouse text
	textAlign( LEFT, CENTER);
	textSize( 35);
	text( mouse_text, mouse.x + 30, mouse.y + 5);
}

void drawScreenBounds(){
	//draw screen bounds
	noFill();
	stroke( 180, 230, 200, 180);
	strokeWeight( 20);
	rect(
		screen_bounds.x, screen_bounds.y,
		screen_bounds.width, screen_bounds.height);
}
void drawScreenText(){
	//draw screen text
	fill( 180, 230, 200, 240);
	noStroke();
	textAlign( CENTER, BOTTOM);
	textSize( 25);
	textMode( MODEL);
	text( screen_text, window_width / 2, window_height);
}

void drawSketch(){
	//draw sketch bounds
	fill( 10, 10, 10, 200);
	stroke( 180, 230, 200, 200);
	strokeWeight( 1);
	rect(
		sketch_bounds.x, sketch_bounds.y,
		sketch_bounds.width, sketch_bounds.height);
	//draw sketch text
	//draw window text
	fill( 180, 230, 200, 180);
	textAlign( CENTER, TOP);
	textSize( 60);
	textMode( MODEL);
	text( sketch_text,
		sketch_bounds.x + sketch_bounds.width / 2 + 20,
		sketch_bounds.y + 20);
}

void drawTouches(){
	translate( sketch_bounds.x, sketch_bounds.y);
	Touch[] touches = SMT.getTouches();
	for( Touch touch : touches){
		//get and apply the bind matrix directly to work around clamping
		PMatrix2D bind_matrix =
			touch.getTouchBinder().getBindMatrix();
		PVector touch_pos = bind_matrix.mult(
			new PVector( touch.getRawX(), touch.getRawY()), null);

		//draw touch
		String touch_text = String.format(
			"Touch:\n%.0f, %.0f", touch_pos.x, touch_pos.y);
		// draw circle
		noFill();
		stroke( 140, 220, 200, 200);
		strokeWeight( 2);
		ellipse( touch_pos.x, touch_pos.y, 35, 35);
		// draw dot
		fill( 140, 220, 200, 200);
		noStroke();
		ellipse( touch_pos.x, touch_pos.y, 5, 5);
		// draw touch getT()ext
		textAlign( LEFT, CENTER);
		textSize( 35);
		textMode( MODEL);
		text( touch_text, touch_pos.x + 30, touch_pos.y + 5);
	}
}
