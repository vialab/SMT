package vialab.SMT;

//standard library imports
import java.util.Vector;

//processing imports
import processing.core.*;

//local imports
import vialab.SMT.event.*;

public class TexturedTouchDrawer
		implements TouchDrawer, TouchListener {
	private PImage touch_texture = null;
	private Vector<PVector> vertices;
	private Vector<Touch> deadTouches;
	private int sections;
	private float radius;
	private static long death_duration = 250;

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
		for( Touch touch : touches){
			touch.addTouchListener( this);
			drawTouch( touch, graphics, 1f);
			System.out.printf(
				"%d, %d\n",
				touch.currentTime.getTotalMilliseconds(),
				touch.currentTime.getSessionTime().getTotalMilliseconds());
		}
		Touch[] deadTouches_array = deadTouches.toArray( new Touch[0]);
		for( Touch touch : deadTouches_array){
			long deadtime_micros =
				touch.currentTime
					.getSessionTime().getTotalMilliseconds() -
				touch.deathTime.getTotalMilliseconds();
			float ani_step = (float) deadtime_micros / death_duration;
			drawTouch( touch, graphics, 1 - ani_step);
			System.out.printf( "%f", ani_step);
			System.out.printf( " %d\n",
				touch.deathTime != null ?
					touch.deathTime.getTotalMilliseconds() : -1);
			if( ani_step > 1){
				deadTouches.remove( touch);
				touch.removeTouchListener( this);
			}
		}
	}
	private void drawTouch(
			Touch touch, PGraphics graphics, float alpha){
		graphics.noStroke();
		graphics.beginShape( PApplet.TRIANGLE_FAN);
		graphics.texture( touch_texture);
		graphics.tint( 255, 255f * alpha);
		graphics.vertex( touch.x , touch.y, 0, 1);
		for( PVector vert : vertices)
			graphics.vertex( touch.x + vert.x, touch.y + vert.y, 0, 0);
		graphics.endShape();
		graphics.noTint();
	}

	// utility functions for textured touch point draw method
	public void update(){
		sections = SMT.touch_sections;
		radius = SMT.touch_radius;
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