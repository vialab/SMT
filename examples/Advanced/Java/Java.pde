/**
 * Sketch for Java Tutorial.
 */

import vialab.SMT.*;

//vars
PFont face_font = null;

//Setup function for the applet
void setup(){
	//SMT and Processing setup
	size( 800, 600, SMT.RENDERER);
	SMT.init( this);

	//create a happy face zone
	Zone happyzone = new HappyFaceZone();

	//create an anonymous zone
	Zone anonyzone = new Zone( "AnonyZone", 100, 10, 200, 200){
		//draw method
		@Override
		public void draw(){
			fill( 220, 140, 160, 140);
			stroke( 240, 180);
			strokeWeight( 3);
			rect( 0, 0, this.width, this.height);
		}
		//touch method
		@Override
		public void touch(){
			rst();
		}
	};

	//add our zones to the sketch
	SMT.add( anonyzone, happyzone);

	//load fonts
	face_font = createFont( "Droid Sans Bold", 80);
}

//Draw function for the sketch
void draw(){
	background( 30);
}

//touch method for anonymous zone
void touchAnonyZone( Zone zone){
	zone.pinch();
}

//happy face zone
class HappyFaceZone extends Zone {
	boolean happy = true;

	//constructor
	public HappyFaceZone(){
		super( 500, 10, 200, 200);
	}

	//draw method
	@Override
	public void draw(){
		//draw circle
		fill( 100, 150, 60, 200);
		stroke( 35, 220);
		strokeWeight( 5);
		ellipse( 100, 100, 200, 200);
		fill( 0, 220);
		//draw face
		textAlign( CENTER, CENTER);
		textFont( face_font);
		textMode( SHAPE);
		text(
			happy ? "^_^" : ">_<",
			100, 100 - 10);
	}
	//pick draw method
	@Override
	public void pickDraw(){
		ellipse( 100, 100, 200, 200);
	}
	
	//touch method
	@Override
	public void touch(){
		rst();
	}
	//touch down method
	@Override
	public void touchDown( Touch touch){
		happy = false;
	}
	//touch up method
	@Override
	public void touchUp( Touch touch){
		if( this.getNumTouches() == 0)
			happy = true;
	}
	//touch moved method
	@Override
	public void touchMoved( Touch touch){}

	//advanced overridden methods
	@Override
	public void assign( Touch... touches){
		super.assign( touches);
	}
	@Override
	public boolean add( Zone zone){
		return super.add( zone);
	}
	@Override
	public boolean remove( Zone zone){
		return super.remove( zone);
	}
}