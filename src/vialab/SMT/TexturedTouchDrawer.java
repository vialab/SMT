package vialab.SMT;

//standard library imports
import java.util.Vector;
import java.util.Vector;
import java.awt.Color;

//processing library imports
import processing.core.*;

//TUIO library imports
import TUIO.*;

//local imports
import vialab.SMT.event.*;

public class TexturedTouchDrawer
		implements TouchDrawer, TouchListener {
	private PImage touch_texture = null;
	private PImage trail_texture = null;
	private Vector<PVector> vertices;
	//list of touches that are no longer in use
	private Vector<Touch> deadTouches;
	long currentTime;

	// Touch drawing options
	//the number of sections to use when drawing touches
	private int touch_sections;
	//the radius to use when drawing touches
	private float touch_radius;
	//the duration of the fade-out animation
	private static long death_duration = 250;
	//touch tinting's red element
	private float touch_tint_red = 255f;
	//touch tinting's green element
	private float touch_tint_green = 255f;
	//touch tinting's blue element
	private float touch_tint_blue = 255f;
	//touch tinting's alpha element
	private float touch_tint_alpha = 200f;

	// Trail drawing options
	//whether to draw the trail
	private boolean trail_enabled = true;
	private int trail_time_threshold = 300;
	private int trail_point_threshold = 50;
	private float trail_c = 0.05f;
	private int trail_t_n = 30;
	//width of the trail's texture
	private float trail_width = 5.0f;
	//trail tinting's red element
	private float trail_tint_red = 255f;
	//trail tinting's green element
	private float trail_tint_green = 255f;
	//trail tinting's blue element
	private float trail_tint_blue = 255f;
	//trail tinting's alpha element
	private float trail_tint_alpha = 200f;

	public TexturedTouchDrawer(){
		vertices = new Vector<PVector>();
		update();
		if( touch_texture == null)
			touch_texture = SMT.parent.loadImage(
				"resources/touch_texture.png");
		if( trail_texture == null)
			trail_texture = SMT.parent.loadImage(
				"resources/trail_texture.png");
		deadTouches = new Vector<Touch>();
	}

	/** Implements the "Textured" touch draw method */
	public void draw( Iterable<Touch> touches, PGraphics graphics){
		//useful variables
		TuioTime sessionTime = TuioTime.getSessionTime();
		currentTime = sessionTime.getTotalMilliseconds();
		//draw live touches
		for( Touch touch : touches){
			//listen for touch's death
			touch.addTouchListener( this);
			//draw the touch
			drawTouch( touch, graphics, 1f);
		}
		//draw dying touches
		Touch[] deadTouches_array = deadTouches.toArray( new Touch[0]);
		for( Touch touch : deadTouches_array){
			//get how long this touch has been dead
			long deadtime_millis = currentTime -
				touch.deathTime.getTotalMilliseconds();
			//interpolate onto the animation domain
			float ani_step = (float) deadtime_millis / death_duration;
			//draw the touch
			drawTouch( touch, graphics, 1 - ani_step);
			//remove if the death animation has finished
			if( ani_step > 1){
				deadTouches.remove( touch);
				touch.removeTouchListener( this);
			}
		}
	}

	//drawing functions
	private void drawTouch(
			Touch touch, PGraphics graphics, float alpha){
		//draw the trail
		if( trail_enabled){
			//select points that are within the time threshold
			Vector<TuioPoint> pathPoints = selectPoints( touch, currentTime);
			//interpolate the points
			Vector<CurvePoint> curvePoints = interpolatePoints( graphics, pathPoints);
			//draw the points
			drawCurvePoints( graphics, curvePoints, alpha);
		}
		
		//setup
		graphics.pushStyle();
		graphics.noStroke();
		graphics.fill(0);
		graphics.textureMode( PGraphics.NORMAL);
		//draw the touch
		graphics.pushMatrix();
		graphics.beginShape( PGraphics.TRIANGLE_FAN);
		graphics.texture( touch_texture);
		graphics.tint( 
			touch_tint_red,
			touch_tint_green,
			touch_tint_blue,
			touch_tint_alpha * alpha);
		graphics.translate( touch.x, touch.y);
		graphics.vertex( 0, 0, 0, 1);
		for( PVector vert : vertices)
			graphics.vertex( vert.x, vert.y, 0, 0);
		graphics.endShape();
		graphics.popMatrix();
		graphics.popStyle();

	}

	protected void update(){
		touch_sections = SMT.touch_sections;
		//look at the texture i used and you'll understand
		touch_radius = SMT.touch_radius * 2;
		findVertices();
	}
	private void findVertices(){
		vertices.clear();
		float dtheta = PApplet.TWO_PI / touch_sections;
		for( float theta = 0.0f; theta < PApplet.TWO_PI; theta += dtheta)
			addVert( theta);
		addVert( PApplet.TWO_PI);
	}
	private void addVert( float theta){
		vertices.add(
			new PVector(
				touch_radius * PApplet.cos( theta),
				touch_radius * PApplet.sin( theta)));
	}

	private Vector<TuioPoint> selectPoints( Touch touch, long currentTime){
		//result points
		Vector<TuioPoint> points = new Vector<TuioPoint>();
		//for every point along the path
		TuioPoint previous = null;
		for( int i = touch.path.size() - 1; i >= 0; i--){
			//stop when we have too many points
			if( points.size() >= trail_point_threshold) break;
			TuioPoint point = touch.path.get( i);
			//get point time
			long pointTime = point.getTuioTime().getTotalMilliseconds();
			//add points that are within the time threshold
			//don't add duplicates
			if( currentTime - pointTime > trail_time_threshold)
				continue;
			/*if( previous != null){
				float dist_x2 = pow(
					point.getScreenX( display_width) -
					previous.getScreenX( display_width), 2);
				float dist_y2 = pow(
					point.getScreenY( display_height) -
					previous.getScreenY( display_height), 2);
				float distance = sqrt( dist_x2 + dist_y2);
				if( distance <= 5)
					continue;
			}*/
			points.add( point);
		}
		return points;
	}

	private Vector<CurvePoint> interpolatePoints(
			PGraphics graphics, Vector<TuioPoint> points){
		//convenience variables
		int point_n = points.size();
		if( point_n < 2) return null;
		Vector<CurvePoint> curvePoints = new Vector<CurvePoint>();
		int t_n = trail_t_n + points.size();
		float dt = (float) 1 / t_n;
		//for t_n points on the domain [ 0, 1]
		for( int t_i = 0; t_i <= t_n; t_i++){
			float t = dt * t_i;
			//average their position
			float x = 0;
			float y = 0;
			float sum = 0;
			for( int point_i = 0; point_i < point_n; point_i++){
				TuioPoint point = points.get( point_i);
				long pointTime = point.getTuioTime().getTotalMilliseconds();

				//locate the point in the domain
				float p_t =
					(float) ( currentTime - pointTime) / trail_time_threshold;
				//calculate distance on the domain
				float distance = p_t - t;
				//calculate weight with a gaussian function on distance
				float w = expweight( distance, trail_c);
				//add weighted components
				x += w * point.getX();
				y += w * point.getY();
				sum += w;
			}
			//avoid death
			if( sum == 0) continue;
			x *= graphics.width / sum;
			y *= graphics.height / sum;
			//add point
			CurvePoint curvePoint = new CurvePoint( x, y);
			curvePoints.add( curvePoint);
		}
		int curvePoints_size = curvePoints.size();
		//just a safety check
		if( curvePoints_size < 2)
			return null;

		//calculate the tangents and normals
		//calculate first's tanget
		CurvePoint first = curvePoints.firstElement();
		CurvePoint second = curvePoints.get( 1);
		first.tx = second.x - first.x;
		first.ty = second.y - first.y;
		//calculate lasts's tanget
		CurvePoint last = curvePoints.lastElement();
		CurvePoint secondLast = curvePoints.get( curvePoints_size - 2);
		last.tx = last.x - secondLast.x;
		last.ty = last.y - secondLast.y;
		//prepare for iterations
		CurvePoint previous = first;
		CurvePoint current = second;
		CurvePoint next = null;
		//for all the rest
		for( int i = 1 ; i < curvePoints_size - 1; i++){
			next = curvePoints.get( i + 1);
			current.tx = next.x - previous.x;
			current.ty = next.y - previous.y;
			double dx = current.x - previous.x;
			double dy = current.y - previous.y;
			current.dr2 = Math.pow( dx, 2) + Math.pow( dy, 2);
			double tr = Math.sqrt(
				Math.pow( current.tx, 2) +
				Math.pow( current.ty, 2));
			if( tr != 0.0){
				current.tx /= tr;
				current.ty /= tr;
			}
			previous = current;
			current = next;
		}

		//finish up
		return curvePoints;
	}
	private float expweight( float distance, float c){
		return PApplet.exp( - PApplet.pow( distance / c, 2) / 2);
	}

	private void drawCurvePoints(
			PGraphics graphics, Vector<CurvePoint> curvePoints, float alpha){
		if( curvePoints == null) return;
		int curvePoints_size = curvePoints.size();
		//just a safety check
		if( curvePoints_size < 2) return;

		//drawing options
		graphics.pushStyle();
		graphics.noStroke();
		graphics.fill( 0);
		graphics.textureMode( PConstants.NORMAL);
		graphics.beginShape( PConstants.QUAD_STRIP);
		graphics.texture( trail_texture);
		graphics.tint(
			trail_tint_red,
			trail_tint_green,
			trail_tint_blue,
			trail_tint_alpha * alpha);
		//for each curve point
		for( int i = 0 ; i < curvePoints_size; i++){
			CurvePoint point = curvePoints.get( i);
			//place the point on the curve's domain (to get texture co-ordinates)
			double t = (double) ( curvePoints_size - 1 - i) / ( curvePoints_size - 1);
			//calculate the normals
			double nx = - point.ty * trail_width;
			double ny = point.tx * trail_width;
			//adjust the width of this section
			double scale = 1;
			if( point.dr2 <= 0)
				scale = 0;
			//place the next two bits of the quad strip
			graphics.vertex(
				(float) ( point.x + nx * scale),
				(float) ( point.y + ny * scale),
				(float) t, 0.0f);
			graphics.vertex(
				(float) ( point.x - nx * scale),
				(float) ( point.y - ny * scale),
				(float) t, 1.0f);
		}
		//clean up
		graphics.endShape();
		graphics.popStyle();
	}

	//Accessor methods

	/** Sets the desired tint of drawn touches.
	 * @param red The desired tint's red element
	 * @param green The desired tint's green element
	 * @param blue The desired tint's blue element
	 * @param alpha The desired tint's alpha element
	 */
	public void setTouchTint( float red, float green, float blue, float alpha){
		//do validation?
		touch_tint_red = red;
		touch_tint_green = green;
		touch_tint_blue = blue;
		touch_tint_alpha = alpha;
	}
	public float getTouchTintRed(){
		return touch_tint_red;
	}
	public float getTouchTintGreen(){
		return touch_tint_green;
	}
	public float getTouchTintBlue(){
		return touch_tint_blue;
	}
	public float getTouchTintAlpha(){
		return touch_tint_alpha;
	}

	// Trail drawing options

	/** Sets the desired tint of drawn trails.
	 * @param red The desired tint's red element
	 * @param green The desired tint's green element
	 * @param blue The desired tint's blue element
	 * @param alpha The desired tint's alpha element
	 */
	public void setTrailTint( float red, float green, float blue, float alpha){
		//do validation?
		trail_tint_red = red;
		trail_tint_green = green;
		trail_tint_blue = blue;
		trail_tint_alpha = alpha;
	}
	public float getTrailTintRed(){
		return trail_tint_red;
	}
	public float getTrailTintGreen(){
		return trail_tint_green;
	}
	public float getTrailTintBlue(){
		return trail_tint_blue;
	}
	public float getTrailTintAlpha(){
		return trail_tint_alpha;
	}

	public void setTrailEnabled( boolean enabled){
		trail_enabled = enabled;
	}
	public void setTrailTimeThreshold( int threshold){
		trail_time_threshold = threshold;
	}
	public void setTrailPointThreshold( int threshold){
		trail_point_threshold = threshold;
	}
	public void setTrailC( float c){
		trail_c = c;
	}
	public void setTrailT_N( int t_n){
		trail_t_n = t_n;
	}
	public void setTrailWidth( float width){
		trail_width = width;
	}

	public boolean getTrailEnabled(){
		return trail_enabled;
	}
	public int getTrailTimeThreshold(){
		return trail_time_threshold;
	}
	public int getTrailPointThreshold(){
		return trail_point_threshold;
	}
	public float getTrailC(){
		return trail_c;
	}
	public int getTrailT_N(){
		return trail_t_n;
	}
	public float getTrailWidth(){
		return trail_width;
	}

	//touch listener functions
	public void handleTouchDown( TouchEvent touchEvent){}
	public void handleTouchMoved( TouchEvent touchEvent){}
	public void handleTouchUp( TouchEvent touchEvent){
		deadTouches.add( touchEvent.getTouch());
	}

	//sub-classes
	private class CurvePoint {
		public float x = 0;
		public float y = 0;
		public double dr2 = 0;
		public double tx = 0;
		public double ty = 0;
		public CurvePoint( float x, float y){
			this.x = x;
			this.y = y;
		}
	}
}