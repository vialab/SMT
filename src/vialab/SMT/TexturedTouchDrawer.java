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
import vialab.SMT.util.*;

/**
 * This class uses some nice textures to render touches and their paths.
 * @author Kalev Kalda Sikes
 */
public class TexturedTouchDrawer
		implements TouchDrawer, TouchListener {
	//the texture used for touches
	private PImage touch_texture = null;
	//the texture used for trails
	private PImage trail_texture = null;
	//the calculated list of vertices for drawing the circle for touches
	private Vector<PVector> vertices;
	//list of touches that are no longer in use
	private Vector<Touch> deadTouches;
	//the current (TUIO) time
	private long currentTime;

	// Touch drawing options
	//whether to draw the trail
	private boolean touch_enabled = true;
	//the number of sections to use when drawing touches
	private int touch_sections;
	//the radius to use when drawing touches
	private float touch_radius;
	//the duration of the fade-out animation in seconds
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
	//the time window within which to include points
	private int trail_time_threshold = 300;
	//the maximum number of points on to include
	private int trail_point_threshold = 50;
	//the smoothing constant of the curve
	private float trail_c = 0.10f;
	//the base number of points on the curve
	private int trail_t_n = 40;
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
		//initialize properties
		vertices = new Vector<PVector>();
		deadTouches = new Vector<Touch>();
		//update the circle's points
		update();
		//load touch texture
		touch_texture = SMT.getApplet().loadImage(
			"resources/touch_texture.png");
		//load trail texture
		trail_texture = SMT.getApplet().loadImage(
			"resources/trail_texture.png");
	}

	/** 
	 * Implements the "Textured" touch draw method
	 * @param touches  The touches to draw
	 * @param graphics the graphics object on which to draw
	 */
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
			Vector<CurvePoint> curvePoints = interpolatePoints(
				touch.getTouchBinder(), pathPoints);
			//draw the points
			drawCurvePoints( graphics, touch, curvePoints, alpha);
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
		Color touch_override = touch.getTint();
		if( touch_override != null)
			graphics.tint( 
				(float) touch_override.getRed(),
				(float) touch_override.getGreen(),
				(float) touch_override.getBlue(),
				(float) touch_override.getAlpha() * alpha);
		else
			graphics.tint( 
				touch_tint_red,
				touch_tint_green,
				touch_tint_blue,
				touch_tint_alpha * alpha);
		graphics.translate( touch.x, touch.y);
		graphics.vertex( 0, 0, 100, 0, 1);
		for( PVector vert : vertices)
			graphics.vertex( vert.x, vert.y, 100, 0, 0);
		graphics.endShape();
		graphics.popMatrix();
		graphics.popStyle();

	}

	/** Pull the static SMT touch drawing parameters, and update accordingly. */
	protected void update(){
		touch_sections = SMT.touch_sections;
		//the texture i'm using is actually twice the radius
		touch_radius = SMT.touch_radius * 2;
		findVertices();
	}

	/** Recalculate the points of the circle */
	private void findVertices(){
		vertices.clear();
		float dtheta = PApplet.TWO_PI / touch_sections;
		for( float theta = 0.0f; theta < PApplet.TWO_PI; theta += dtheta)
			addVert( theta);
		addVert( PApplet.TWO_PI);
	}
	/** Recalculate the points of the circle */
	private void addVert( float theta){
		vertices.add(
			new PVector(
				touch_radius * PApplet.cos( theta),
				touch_radius * PApplet.sin( theta)));
	}

	/**
	 * Select the points from the touch's path that we wanna interpolate from.
	 * @return the selected points
	 **/
	private Vector<TuioPoint> selectPoints( Touch touch, long currentTime){
		//result points
		Vector<TuioPoint> points = new Vector<TuioPoint>();
		Vector<TuioPoint> touch_path = touch.getTuioPath();
		//for every point along the path
		TuioPoint previous = null;
		for( int i = touch_path.size() - 1; i >= 0; i--){
			//stop when we have too many points
			if( points.size() >= trail_point_threshold) break;
			TuioPoint point = touch_path.get( i);
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

	/**
	 * Interpolate the points of the trail from the touch's path
	 * @return the interpolated points
	 **/
	private Vector<CurvePoint> interpolatePoints(
			TouchBinder binder, Vector<TuioPoint> points){
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
			PVector result = binder.bind(
				x / sum, y / sum);
			//add point
			CurvePoint curvePoint = new CurvePoint(
				result.x, result.y);
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
	/**
	 * Calculate the weight of a point based the distance.
	 * Uses a gaussian function.
	 * @param distance the distance on the domain
	 * @param c the c parameter of the gaussian function
	 * @return the calculated weight of the point
	 */
	private float expweight( float distance, float c){
		return PApplet.exp( - PApplet.pow( distance / c, 2) / 2);
	}

	/**
	 * Draws the given points on the given graphics context.
	 * Uses the given alpha for tranparency.
	 * @param graphics the graphics context to draw on
	 * @param curvePoints the points of the curve to draw
	 * @param alpha the desired transparency of the curve
	 */
	private void drawCurvePoints(
			PGraphics graphics, Touch touch, Vector<CurvePoint> curvePoints, float alpha){
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
		Color trail_override = touch.getTrailTint();
		if( trail_override != null)
			graphics.tint( 
				(float) trail_override.getRed(),
				(float) trail_override.getGreen(),
				(float) trail_override.getBlue(),
				(float) trail_override.getAlpha() * alpha);
		else
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
				(float) ( point.y + ny * scale), 100f,
				(float) t, 0.0f);
			graphics.vertex(
				(float) ( point.x - nx * scale),
				(float) ( point.y - ny * scale), 100f,
				(float) t, 1.0f);
		}
		//clean up
		graphics.endShape();
		graphics.popStyle();
	}

	//Accessor methods

	/**
	 * Sets whether touch drawing is enabled
	 * @param enabled whether touch drawing should be enabled 
	 **/
	public void setTouchEnabled( boolean enabled){
		this.touch_enabled = enabled;
	}
	/**
	 * Sets how long it takes for a touch to got from 100% visible to 0% visible after it "dies".
	 * @param duration desired length of the death animation
	 **/
	public void setDeathDuration( long duration){
		this.death_duration = duration;
	}
	/** Sets the desired tint of drawn touches.
	 * @param red The desired tint's red element
	 * @param green The desired tint's green element
	 * @param blue The desired tint's blue element
	 * @param alpha The desired tint's alpha element
	 */
	public void setTouchTint( float red, float green, float blue, float alpha){
		//do validation?
		this.touch_tint_red = red;
		this.touch_tint_green = green;
		this.touch_tint_blue = blue;
		this.touch_tint_alpha = alpha;
	}
	/**
	 * Gets whether touch drawing is enabled
	 * @return whether touch drawing is enabled 
	 **/
	public boolean getTouchEnabled(){
		return touch_enabled;
	}
	/**
	 * Gets how long it takes for a touch to got from 100% visible to 0% visible after it "dies".
	 * @return the current length of the death animation 
	 **/
	public long getDeathDuration(){
		return death_duration;
	}
	/** Gets the red aspect of tint of drawn touches.
	 * @return The desired tint's red element
	 */
	public float getTouchTintRed(){
		return touch_tint_red;
	}
	/** Gets the green aspect of tint of drawn touches.
	 * @return The desired tint's green element
	 */
	public float getTouchTintGreen(){
		return touch_tint_green;
	}
	/** Gets the blue aspect of tint of drawn touches.
	 * @return The desired tint's blue element
	 */
	public float getTouchTintBlue(){
		return touch_tint_blue;
	}
	/** Gets the alpha aspect of tint of drawn touches.
	 * @return The desired tint's alpha element
	 */
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
		this.trail_tint_red = red;
		this.trail_tint_green = green;
		this.trail_tint_blue = blue;
		this.trail_tint_alpha = alpha;
	}
	/** Gets the red aspect of tint of the drawn trail.
	 * @return The desired tint's red element
	 */
	public float getTrailTintRed(){
		return trail_tint_red;
	}
	/** Gets the green aspect of tint of the drawn trail.
	 * @return The desired tint's green element
	 */
	public float getTrailTintGreen(){
		return trail_tint_green;
	}
	/** Gets the blue aspect of tint of the drawn trail.
	 * @return The desired tint's blue element
	 */
	public float getTrailTintBlue(){
		return trail_tint_blue;
	}
	/** Gets the alpha aspect of tint of the drawn trail.
	 * @return The desired tint's alpha element
	 */
	public float getTrailTintAlpha(){
		return trail_tint_alpha;
	}

	/**
	 * Set whether trail drawing is enabled 
	 * @param enabled whether trail drawing should be enabled 
	 **/
	public void setTrailEnabled( boolean enabled){
		this.trail_enabled = enabled;
	}
	/**
	 * Set the time threshold for touch path point selection 
	 * @param threshold the desired time threshold for touch path point selection
	 **/
	public void setTrailTimeThreshold( int threshold){
		this.trail_time_threshold = threshold;
	}
	/**
	 * Set the point count threshold for touch path point selection 
	 * @param threshold the desired point count threshold for touch path point selection
	 **/
	public void setTrailPointThreshold( int threshold){
		this.trail_point_threshold = threshold;
	}
	/**
	 * Set the C parameter of the smoothing function 
	 * @param c the desired C parameter of the smoothing function
	 **/
	public void setTrailC( float c){
		this.trail_c = c;
	}
	/**
	 * Set the base number of points on the curve 
	 * @param t_n the desired base number of points on the curve
	 **/
	public void setTrailT_N( int t_n){
		this.trail_t_n = t_n;
	}
	/**
	 * Set the desired width of the trail.
	 * Making this any greater than the default will probably look bad.
	 * @param width the desired width of the trail
	 **/
	public void setTrailWidth( float width){
		this.trail_width = width;
	}

	/**
	 * Get whether the trail is enabled
	 * @return whether the trail is enabled
	 **/
	public boolean getTrailEnabled(){
		return trail_enabled;
	}
	/**
	 * Get the time threshold for touch path point selection
	 * @return the time threshold for touch path point selection
	 **/
	public int getTrailTimeThreshold(){
		return trail_time_threshold;
	}
	/**
	 * Get the point count threshold for touch path point selection
	 * @return the point count threshold for touch path point selection
	 **/
	public int getTrailPointThreshold(){
		return trail_point_threshold;
	}
	/**
	 * Get the C parameter of the smoothing function
	 * @return the C parameter of the smoothing function
	 **/
	public float getTrailC(){
		return trail_c;
	}
	/**
	 * Get the base number of points on the curve
	 * @return the base number of points on the curve
	 **/
	public int getTrailT_N(){
		return trail_t_n;
	}
	/**
	 * Get Set the desired width of the trail
	 * @return Set the desired width of the trail
	 **/
	public float getTrailWidth(){
		return trail_width;
	}

	//touch listener functions
	/** 
	 * Do nothing on touch down
	 * @param touchEvent the touch event to process
	 */
	public void handleTouchDown( TouchEvent touchEvent){}
	/** 
	 * Do nothing on touch moved
	 * @param touchEvent the touch event to process
	 **/
	public void handleTouchMoved( TouchEvent touchEvent){}
	/** 
	 * Add to our list of dead touches and stop listening
	 * @param touchEvent the touch event to process
	 */
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