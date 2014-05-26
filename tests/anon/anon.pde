//SMT library imports
import vialab.SMT.*;

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
	Zone asdf = new Zone( 120, 10, 100, 100){
		public void drawImpl(){
			pushStyle();
			fill( 180, 100, 100);
			rect( 0, 0, 100, 100, 5);
			popStyle();
		}
		public void pickDrawImpl(){
			rect( 0, 0, 100, 100, 5);
		}
		public void touchImpl(){
			drag();
		}
	};
	SMT.add( asdf);
	SMT.add( new Zone( 10, 10, 100, 100));
	//SMT.add( new SwipeKeyboard());
}

void draw(){
	//draw background
	background( 30);
}

void touchZone( Zone zone){
	zone.rst();
}