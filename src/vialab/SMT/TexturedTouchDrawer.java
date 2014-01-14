package vialab.SMT;

//standard library imports
import java.util.Vector;
import java.util.Vector;
import java.awt.Color;

//processing imports
import processing.core.*;

//local imports
import vialab.SMT.event.*;

public class TexturedTouchDrawer
		implements TouchDrawer, TouchListener {
	private PImage touch_texture = null;
	private Vector<PVector> vertices;
	//list of touches that are no longer in use
	private Vector<Touch> deadTouches;
	//the number of sections to use when drawing touches
	private int sections;
	//the radius to use when drawing touches
	private float radius;
	//the duration of the fade-out animation
	private static long death_duration = 250;
	//touch tinting's red element
	private float tint_red = 255f;
	//touch tinting's green element
	private float tint_green = 255f;
	//touch tinting's blue element
	private float tint_blue = 255f;
	//touch tinting's alpha element
	private float tint_alpha = 200f;

	public TexturedTouchDrawer(){
		vertices = new Vector<PVector>();
		update();
		if( touch_texture == null)
			touch_texture = SMT.parent.loadImage(
				"resources/touch_texture.png");
		deadTouches = new Vector<Touch>();
	}

	/** Implements the "Textured" touch draw method */
	public void draw( Iterable<Touch> touches, PGraphics graphics){
		//draw live touches
		for( Touch touch : touches){
			//listen for touch's death
			touch.addTouchListener( this);
			drawTouch( touch, graphics, 1f);
		}
		//draw dying touches
		Touch[] deadTouches_array = deadTouches.toArray( new Touch[0]);
		for( Touch touch : deadTouches_array){
			long deadtime_millis =
				touch.currentTime
					.getSessionTime().getTotalMilliseconds() -
				touch.deathTime.getTotalMilliseconds();
			float ani_step = (float) deadtime_millis / death_duration;
			drawTouch( touch, graphics, 1 - ani_step);
			if( ani_step > 1){
				deadTouches.remove( touch);
				touch.removeTouchListener( this);
			}
		}
	}

	/** Sets the desired tint of drawn touches.
	 * @param red The desired tint's red element
	 * @param green The desired tint's green element
	 * @param blue The desired tint's blue element
	 * @param alpha The desired tint's alpha element
	 */
	public void setTint( float red, float green, float blue, float alpha){
		//do validation?
		tint_red = red;
		tint_green = green;
		tint_blue = blue;
		tint_alpha = alpha;
	}
	public float getTintRed(){
		return tint_red;
	}
	public float getTintGreen(){
		return tint_green;
	}
	public float getTintBlue(){
		return tint_blue;
	}
	public float getTintAlpha(){
		return tint_alpha;
	}

	//private utility functions for textured touch point draw method
	private void drawTouch(
			Touch touch, PGraphics graphics, float alpha){
		//texture
		graphics.pushStyle();
		graphics.noStroke();
		graphics.fill(0);
		graphics.textureMode( PGraphics.NORMAL);
		graphics.beginShape( PGraphics.TRIANGLE_FAN);
		graphics.texture( touch_texture);
		graphics.tint( 
			tint_red,
			tint_green,
			tint_blue,
			tint_alpha * alpha);
		graphics.vertex( touch.x , touch.y, 0, 1);
		for( PVector vert : vertices)
			graphics.vertex( touch.x + vert.x, touch.y + vert.y, 0, 0);
		graphics.endShape();
		graphics.popStyle();
	}

	protected void update(){
		sections = SMT.touch_sections;
		//look at the texture used and you'll understand
		radius = SMT.touch_radius * 2;
		findVertices();
	}
	private void findVertices(){
		vertices.clear();
		float dtheta = PApplet.TWO_PI / sections;
		for( float theta = 0.0f; theta < PApplet.TWO_PI; theta += dtheta)
			addVert( theta);
		addVert( PApplet.TWO_PI);
	}
	private void addVert( float theta){
		vertices.add(
			new PVector(
				radius * PApplet.cos( theta),
				radius * PApplet.sin( theta)));
	}

	//touch listener functions
	public void handleTouchDown( TouchEvent touchEvent){}
	public void handleTouchMoved( TouchEvent touchEvent){}
	public void handleTouchUp( TouchEvent touchEvent){
		deadTouches.add( touchEvent.getTouch());
	}
}