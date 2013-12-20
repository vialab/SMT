import vialab.SMT.*;
import vialab.SMT.swipekeyboard.*;

// constants
int window_width = 1200;
int window_height = 800;
final int fps_limit = 60;

// objects
SwipeKeyboard keyboard;

// other
int window_halfWidth;
int window_halfHeight;

// main functions
public void setup(){
	window_width = displayWidth;
	window_height = displayHeight;
	window_halfWidth = window_width / 2;
	window_halfHeight = window_height / 2;
	//processing library setup
	frameRate( fps_limit);
	size( window_width, window_height, P3D);
	textMode( SHAPE);
	frame.setTitle("Swipe Keyboard Test");
	//smt library setup
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.setTouchDraw( TouchDraw.TEXTURED);

	//add keyboard test
	//keyboard = new SwipeKeyboard();
	keyboard = new SwipeKeyboard( SwipeKeyboard.condensedLayout);
	keyboard.addSwipeKeyboardListener( new DebugSwipeKeyboardListener());
	keyboard.translate( 40, 300);
	SMT.add( keyboard);
	SMT.add( new SwipeKeyboard( SwipeKeyboard.arrowKeysLayout));

	//add text zone
	TextZone texty = new TextZone( "Texty", 500, 0, 500, 200);
	texty.addText( "Texty");
	SMT.add( texty);
}

public void draw(){
	fill( 255, 255, 255);
	//draw background
	rect( 0, 0, window_width, window_height);
}

public void stop(){
	super.stop();
}
