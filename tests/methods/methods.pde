//SMT library imports
import vialab.SMT.*;

//standard library imports
import java.util.Vector;

//constants
int window_width = 1200;
int window_height = 800;
int window_halfWidth;
int window_halfHeight;
int fps_limit = 60;
//other

//main functions
void setup(){
	window_halfWidth = window_width / 2;
	window_halfHeight = window_height / 2;
	//processing window setup
	frameRate( fps_limit);
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//stuff
	Zone asdf = new CheckerDude( 5, 5, 100, 100){
		public void touchDown( Touch touch){}
		public void touchUp( Touch touch){}
		public void touchMoved( Touch touch){}
		public void press( Touch touch){}
		public void keyPressed( KeyEvent event){}
		public void keyReleased( KeyEvent event){}
		public void keyTyped( KeyEvent event){}
	};
	Zone fdsa = new CheckerDude( 305, 5, 100, 100){
		public void touchDownImpl( Touch touch){}
		public void touchUpImpl( Touch touch){}
		public void touchMovedImpl( Touch touch){}
		public void pressImpl( Touch touch){}
		public void keyPressedImpl( KeyEvent event){}
		public void keyReleasedImpl( KeyEvent event){}
		public void keyTypedImpl( KeyEvent event){}
	};
	Zone wer = new CheckerDude( 605, 5, 100, 100){
		public void touchDown( Touch touch){}
		public void touchUp( Touch touch){}
		public void touchMoved( Touch touch){}
		public void press( Touch touch){}
		public void keyPressed( KeyEvent event){}
		public void keyReleased( KeyEvent event){}
		public void keyTyped( KeyEvent event){}
		public void touchDownImpl( Touch touch){}
		public void touchUpImpl( Touch touch){}
		public void touchMovedImpl( Touch touch){}
		public void pressImpl( Touch touch){}
		public void keyPressedImpl( KeyEvent event){}
		public void keyReleasedImpl( KeyEvent event){}
		public void keyTypedImpl( KeyEvent event){}
	};
	SMT.add( asdf);
	SMT.add( fdsa);
	SMT.add( wer);
}

void draw(){
	//draw background
	background( 30);
}

class CheckerDude extends Zone {
	public CheckerDude( int x, int y, int width, int height){
		super( x, y, width, height);
	}
	public void draw(){
		String[] strings = new String[]{
			String.format( "touchDown overridden: %b", touchDown_overridden),
			String.format( "touchUp overridden: %b", touchUp_overridden),
			String.format( "touchMoved overridden: %b", touchMoved_overridden),
			String.format( "press overridden: %b", press_overridden),
			String.format( "keyPressed overridden: %b", keyPressed_overridden),
			String.format( "keyReleased overridden: %b", keyReleased_overridden),
			String.format( "keyTyped overridden: %b", keyTyped_overridden),
			"",
			String.format( "touchDownImpl overridden: %b", touchDownImpl_overridden),
			String.format( "touchUpImpl overridden: %b", touchUpImpl_overridden),
			String.format( "touchMovedImpl overridden: %b", touchMovedImpl_overridden),
			String.format( "pressImpl overridden: %b", pressImpl_overridden),
			String.format( "keyPressedImpl overridden: %b", keyPressedImpl_overridden),
			String.format( "keyReleasedImpl overridden: %b", keyReleasedImpl_overridden),
			String.format( "keyTypedImpl overridden: %b", keyTypedImpl_overridden)};
		pushStyle();
		fill( 180, 220, 220, 180);
		textSize( 15);
		textMode( MODEL);
		textAlign( LEFT, TOP);
		int y = 0;
		for( String str : strings)
			text( str, 0, y += 30);
		popStyle();
	}
}