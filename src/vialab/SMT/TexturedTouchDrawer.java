package vialab.SMT;

//standard library imports
import java.util.Vector;

//processing imports
import processing.core.*;

public class TexturedTouchDrawer implements TouchDrawer {
	private PImage touch_texture = null;
	private Vector<PVector> vertices;
	private int sections;
	private float radius;

	public TexturedTouchDrawer(){
		vertices = new Vector<PVector>();
		update();
		if( touch_texture == null)
			touch_texture = SMT.parent.loadImage(
				"resources/touch_texture.png");
	}

	/** Implements the "Textured" touch draw method */
	public void draw( Iterable<Touch> touches, PGraphics graphics){
		for( Touch touch : touches){
			graphics.noStroke();
			graphics.beginShape( PApplet.TRIANGLE_FAN);
			graphics.texture( touch_texture);
			graphics.vertex( touch.x , touch.y, 0, 1);
			for( PVector vert : vertices)
				graphics.vertex( touch.x + vert.x, touch.y + vert.y, 0, 0);
			graphics.endShape();
		}
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

}