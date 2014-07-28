/**
 *	This sketch emulates coloured bubbles being popped by touch.
 *	It shows dynamic add and remove of Zones.
 */
import vialab.SMT.*;

//vars
int window_width = 1200;
int window_height = 800;
//other
int zone_count = 0;
boolean draw_fps = true;

class BubbleZone extends Zone {
  public boolean dead = false;
  public int red;
  public int green;
  public int blue;
	public BubbleZone(){
		super( "BubbleZone", 0, 0, 100, 100);
		this.red = (int)( 60 + random( 160));
		this.green = (int)( 60 + random( 160));
		this.blue = (int)( 60 + random( 160));
		this.translate(
			50 + random( window_width - 100),
			50 + random( window_height - 100));
	}
}

void setup(){
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
  registerMethod( "pre", this);
}

void pre(){
	while( zone_count < 100){
		SMT.add( new BubbleZone());
		zone_count++;
	}
}

//draw method
void draw(){
  background( 30);
  if( draw_fps) drawFrameRate();
}

//bubble zone methods
void drawBubbleZone( BubbleZone zone){
	noStroke();
	fill( zone.red, zone.green, zone.blue, 200);
  //ellipseMode( CORNER);
  ellipse( 0, 0, zone.width, zone.height);
}
void pickDrawBubbleZone( BubbleZone zone){
  if( ! zone.dead)
    ellipse( 0, 0, zone.width, zone.height);
}
void touchDownBubbleZone( BubbleZone zone, Touch touch){
  println("asdf");
	SMT.remove( zone);
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