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
	public double ani_step = 0;
	public int red;
	public int green;
	public int blue;
	public BubbleZone(){
		super( "BubbleZone", 0, 0, 100, 100);
		this.red = (int)( 60 + random( 160));
		this.green = (int)( 60 + random( 160));
		this.blue = (int)( 60 + random( 160));
		this.translate(
			random( window_width - 100),
			random( window_height - 100));
	}
}

void setup(){
	size( window_width, window_height, SMT.RENDERER);
	((javax.swing.JFrame) frame).setResizable( true);
	SMT.init( this, TouchSource.AUTOMATIC);
	registerMethod( "pre", this);
}

void pre(){
	while( zone_count < 50){
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
	//fade alpha based on animation step
	fill( zone.red, zone.green, zone.blue,
		(int)( 200 * ( 1 - zone.ani_step)));
	//scale width and height based on animation step
	ellipse(
		zone.width / 2, zone.height / 2,
		(float)( zone.width * ( 1 + zone.ani_step)),
		(float)( zone.height * ( 1 + zone.ani_step)));
	if( zone.dead){
		zone.ani_step += 0.10;
		//remove if animation finished
		if( zone.ani_step > 1){
			SMT.remove( zone);
			zone_count--;
		}
	}
}
void pickDrawBubbleZone( BubbleZone zone){
	if( ! zone.dead)
		ellipse(
			zone.width / 2, zone.height / 2,
			zone.width, zone.height);
}
void touchBubbleZone( BubbleZone zone){
	zone.dead = true;
	zone.unassignAll();
}

//drawing methods
public void drawFrameRate(){
	float fps = this.frameRate;
	String fps_text = String.format( "fps: %.0f", fps);
	pushStyle();
	fill( 240, 240, 240, 180);
	textAlign( RIGHT, TOP);
	textSize( 24);
	text( fps_text, window_width - 5, 5);
	popStyle();
}