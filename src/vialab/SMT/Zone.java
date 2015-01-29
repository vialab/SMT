/*
 * Simple Multi-Touch Library Copyright 2011 Erik Paluka, Christopher Collins -
 * University of Ontario Institute of Technology Mark Hancock - University of
 * Waterloo
 * 
 * Parts of this library are based on: TUIOZones http://jlyst.com/tz/
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License Version 3 as published by the
 * Free Software Foundation.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this library; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package vialab.SMT;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import processing.core.*;
import processing.event.KeyEvent;
import processing.opengl.PGraphics3D;
import TUIO.TuioTime;

//local imports
import vialab.SMT.renderer.*;

/**
 * This is the main zone class which all other Zones extend. It holds the zone's
 * coordinates, size, matrices, etc.. Zones are used to draw and interact with
 * the sketch/application.
 * 
 * @author Erik Paluka, Zach Cook
 * @version 1.0
 */
public class Zone extends PGraphics3DDelegate implements PConstants, KeyListener {

	//parent applet
	protected PApplet applet;
	//parent zone
	protected Zone parent = null;

	//properties
	protected String name = null;
	/** @deprecated use Zone.(get|set)(X|Y) instead */
	@Deprecated
	public int x, y;
	/** @deprecated use Zone.(get|set)(Width|Height) instead */
	@Deprecated
	public int height, width;
	/** The dimensions of the zone */
	protected Dimension dimension;
	/** The half-dimensions of the zone */
	protected Dimension halfDimension;

	//The zone's transformation matrix
	protected PMatrix3D matrix = new PMatrix3D();
	protected PMatrix3D backupMatrix = null;
	//The zone's inverse transformation matrix
	protected PMatrix3D inverse = new PMatrix3D();

	//reflection methods
	protected Method method_draw = null;
	protected Method method_pickDraw = null;
	protected Method method_touch = null;
	protected Method method_keyPressed = null;
	protected Method method_keyReleased = null;
	protected Method method_keyTyped = null;
	protected Method method_touchUp = null;
	protected Method method_touchDown = null;
	protected Method method_touchMoved = null;
	protected Method method_press = null;
	//override detection flags
	protected boolean draw_overridden = false;
	protected boolean pickDraw_overridden = false;
	protected boolean touch_overridden = false;
	protected boolean keyPressed_overridden = false;
	protected boolean keyReleased_overridden = false;
	protected boolean keyTyped_overridden = false;
	protected boolean touchUp_overridden = false;
	protected boolean touchDown_overridden = false;
	protected boolean touchMoved_overridden = false;
	protected boolean press_overridden = false;
	//impl override detection fields
	protected boolean drawImpl_overridden = false;
	protected boolean pickDrawImpl_overridden = false;
	protected boolean touchImpl_overridden = false;
	protected boolean keyPressedImpl_overridden = false;
	protected boolean keyReleasedImpl_overridden = false;
	protected boolean keyTypedImpl_overridden = false;
	protected boolean touchUpImpl_overridden = false;
	protected boolean touchDownImpl_overridden = false;
	protected boolean touchMovedImpl_overridden = false;
	protected boolean pressImpl_overridden = false;

	//method enabled flags
	private boolean drawing_enabled = true;
	private boolean picking_enabled = true;
	private boolean touching_enabled = true;

	//state variables
	private boolean drawing_on = false;
	private boolean picking_on = false;
	private boolean touching_on = false;
	protected Color pickColor = null;

	// A LinkedHashMap will allow insertion order to be maintained.  A synchronised one will prevent concurrent modification (which can happen pretty easily with the draw loop + touch event handling).
	/** All of the currently active touches for this zone */
	private Map<Long, Touch> activeTouches =
		Collections.synchronizedMap(
			new LinkedHashMap<Long, Touch>());
	protected CopyOnWriteArrayList<Zone> children = new CopyOnWriteArrayList<Zone>();
	protected TuioTime lastUpdate = TuioTime.getSessionTime();
	protected float rntRadius;
	protected String renderer_name = null;

	/** @deprecated use setDirect( boolean) instead */
	@Deprecated
	protected boolean direct;
	protected boolean captureTouches = true;

	protected boolean scalingLimit = false;
	MouseJoint mJoint;
	private MouseJointDef mJointDef;
	private Object boundObject = null;
	protected int maxWidth, maxHeight;
	protected int minWidth, minHeight;
	/** @deprecated we're working on a better alternative */
	@Deprecated
	public boolean stealChildrensTouch = false;

	//physics fields
	/** @deprecated use Zone.setPhysicsEnabled( boolean) instead */
	@Deprecated
	public boolean physics = false;
	/** A flag that describes whether physics is enabled. */
	private boolean physics_enabled = false;
	BodyDef zoneBodyDef = new BodyDef();
	Body zoneBody;
	PolygonShape zoneShape = new PolygonShape();
	FixtureDef zoneFixtureDef = new FixtureDef();
	Fixture zoneFixture;

	private List<Touch> touchDownList = new ArrayList<Touch>();
	private List<Touch> touchUpList = new ArrayList<Touch>();
	private List<Touch> touchMovedList = new ArrayList<Touch>();
	private List<Touch> pressList = new ArrayList<Touch>();
	private float offsetX;
	private float offsetY;
	private HashSet<Long> dragSeenTouch = new HashSet<Long>();
	private PGraphics3D extra_graphics;
	private PMatrix3D extra_matrix;

	/**
	 * Zone constructor, no name, position of (0,0), width and height of 100
	 */
	public Zone(){
		this( null);
	}

	/**
	 * Zone constructor, with a name, position of (0,0), width and height of 100
	 *
	 * @param name name of the zone, used in the draw, touch ,etc methods
	 */
	public Zone( String name){
		this( name, SMT.zone_renderer);
	}

	/**
	 * Zone constructor, with a name, position of (0,0), width and height of 100
	 *
	 * @param name name of the zone, used in the draw, touch ,etc methods
	 * @param renderer the PGraphics renderer that draws the Zone
	 */
	public Zone( String name, String renderer){
		this( name, 0, 0, 100, 100, renderer);
	}

	/**
	 * @param x the x position of the zone
	 * @param y the y position of the zone
	 * @param width the width of the zone
	 * @param height the height of the zone
	 */
	public Zone( int x, int y, int width, int height){
		this( x, y, width, height, SMT.zone_renderer);
	}

	/**
	 * @param x the x position of the zone
	 * @param y the y position of the zone
	 * @param width the width of the zone
	 * @param height the height of the zone
	 * @param renderer the renderer that draws the zone
	 */
	public Zone( int x, int y, int width, int height, String renderer){
		this( null, x, y, width, height, renderer);
	}

	/**
	 * Zone constructor, with a name, position of (0,0)
	 * 
	 * @param name name of the zone, used in the draw, touch ,etc methods
	 * @param width the width of the zone
	 * @param height the height of the zone
	 */
	public Zone( String name, int width, int height){
		this( name, 0, 0, width, height);
	}

	/**
	 * @param name the name of the zone, used for the reflection methods
	 *   (drawname(),touchname(),etc)
	 * @param x the x position of the zone
	 * @param y the y position of the zone
	 * @param width the width of the zone
	 * @param height the height of the zone
	 */
	public Zone( String name, int x, int y, int width, int height){
		this( name, x, y, width, height, SMT.zone_renderer);
	}

	/**
	 * @param name the name of the zone, used for the reflection methods (drawname(),touchname(),etc)
	 * @param x the x position of the zone
	 * @param y the y position of the zone
	 * @param width the width of the zone
	 * @param height the height of the zone
	 * @param renderer the renderer that draws the zone
	 */
	public Zone( String name, int x, int y, int width, int height, String renderer){
		super();
		//check if smt has been initialized
		if( SMT.applet == null)
			System.err.println(
				"Warning: Initialization of Zone before SMT.init()");

		//get parent
		this.applet = SMT.applet;

		//initialize properties
		this.direct = true;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dimension = new Dimension( width, height);
		this.halfDimension = new Dimension( width / 2, height / 2);
		this.renderer_name = renderer;
		this.rntRadius = Math.min( width, height) / 4;

		resetMatrix();
		setName( name);

		zoneBodyDef.type = BodyType.DYNAMIC;
		zoneBodyDef.linearDamping = 1.0f;
		zoneBodyDef.angularDamping = 1.0f;
		zoneShape.setAsBox( SMT.box2dScale * width / 2, SMT.box2dScale * height / 2);
		zoneFixtureDef.shape = zoneShape;
		zoneFixtureDef.density = 1.0f;
		zoneFixtureDef.friction = 0.3f;
	}

	/**
	 * Prepares and invokes the appropriate draw function. If there is a draw funtion in the processing applet for this zone, that function is called. Otherwise, if there is drawImpl override from a subclass, that function is called. Otherwise, the draw function is called.
	 */
	public void invokeDraw(){
		//is drawing enabled?
		if( ! drawing_enabled)
			return;
		//setup
		drawing_on = true;
		this.setDelegate( SMT.renderer);
		if( ! this.isDirect()){
			extraGraphicsNullCheck();
			SMT.renderer.pushDelegate( extra_graphics);
			extra_graphics.beginDraw();
			extra_graphics.clear();
			extra_graphics.ortho();
			extra_graphics.applyMatrix( extra_matrix);
		}

		//push base transformations
		if( this.isDirect()){
			pushMatrix();
			applyMatrix( matrix);
			//translate to the desired z-level
			pushMatrix();
			translate( 0, 0, SMT.getNextZone_Z());
		}

		//drawing setup
		pushStyle();
		pushMatrix();
		hint( PConstants.DISABLE_OPTIMIZED_STROKE);

		//invoke proper draw method
		invokeDrawMethod();

		//drawing cleanup
		popMatrix();
		popStyle();

		//pop z-level translation
		if( this.isDirect())
			popMatrix();

		//draw children
		for( Zone child : children)
			child.invokeDraw();

		//pop transformations
		if( this.isDirect())
			popMatrix();

		//if we're indirect, we can now draw for realz
		if( ! this.isDirect()){
			extra_graphics.endDraw();
			SMT.renderer.popDelegate();
			//push transformations
			pushMatrix();
			applyMatrix( matrix);
			//translate to the desired z-level
			translate( 0, 0, SMT.getNextZone_Z());
			//draw extra_graphics
			pushStyle();
			noTint();
			image( extra_graphics,
				0, 0, dimension.width, dimension.height);
			//cleanup
			popStyle();
			popMatrix();
		}

		//cleanup
		this.setDelegate( null);
		drawing_on = false;
	}

	/**
	 * Selects and invokes the appropriate draw method
	 */
	public void invokeDrawMethod(){
		if( method_draw != null)
			SMTUtilities.invoke( method_draw, applet, this);
		else if( drawImpl_overridden)
			drawImpl();
		else
			draw();
	}

	/**
	 * Prepares and invokes the appropriate pick draw function. If there is a pick draw funtion in the processing applet for this zone, that function is called. Otherwise, if there is pickDrawImpl override from a subclass, that function is called. Otherwise, the pickDraw function is called.
	 */
	public void invokePickDraw(){
		//is picking enabled?
		if( ! picking_enabled)
			return;
		//setup
		//all calls need to be redirected through this so that color calls can be discarded and background calls changed
		drawing_on = true;
		SMT.renderer.pushDelegate( this);
		if( this.isDirect())
			this.setDelegate(
				parent != null ?
					parent : SMT.picker.picking_context);
		else {
			extraGraphicsNullCheck();
			this.setDelegate( extra_graphics);
			extra_graphics.beginDraw();
			extra_graphics.clear();
			extra_graphics.ortho();
			extra_graphics.applyMatrix( extra_matrix);
		}
		beginPickDraw();

		//translate to the desired z-level
		//translate( 0, 0, SMT.getNextZone_Z());

		//push transformations
		if( this.isDirect()){
			pushMatrix();
			applyMatrix( matrix);
			//translate to the desired z-level
			pushMatrix();
			translate( 0, 0, SMT.getNextZone_Z());
		}

		//picking setup
		pushStyle();
		pushMatrix();
		colorMode( RGB, 255, 255, 255, 255);
		noStroke();
		if( pickColor != null)
			fill(
				pickColor.getRed(),
				pickColor.getGreen(),
				pickColor.getBlue(),
				255);
		else
			noFill();
		picking_on = true;

		//invoke proper draw method
		invokePickDrawMethod();

		//picking cleanup
		popMatrix();
		picking_on = false;
		popStyle();

		//pop z-level translation
		if( this.isDirect())
			popMatrix();

		//draw children
		for( Zone child : children){
			//translate up a bit to prevent z-fighting
			translate( 0f, 0f, 0.5f);
			child.invokePickDraw();
		}

		//pop transformations
		if( this.isDirect())
			popMatrix();

		//if we're indirect, we can now draw for realz
		if( ! this.isDirect()){
			extra_graphics.endDraw();
			this.setDelegate(
				parent != null ?
					parent : SMT.picker.picking_context);
			//push transformations
			pushMatrix();
			applyMatrix( matrix);
			//translate to the desired z-level
			translate( 0, 0, SMT.getNextZone_Z());
			//draw extra_graphics
			pushStyle();
			noTint();
			image( extra_graphics,
				0, 0, dimension.width, dimension.height);
			popStyle();
			popMatrix();
		}

		//cleanup
		endPickDraw();
		SMT.renderer.popDelegate();
		this.setDelegate( null);
		drawing_on = false;
	}

	/**
	 * Selects and invokes the appropriate pick draw method
	 */
	public void invokePickDrawMethod(){
		if( method_pickDraw != null)
			SMTUtilities.invoke( method_pickDraw, applet, this);
		else if( pickDrawImpl_overridden)
			pickDrawImpl();
		else
			pickDraw();
	}

	/**
	 * Prepares and invokes the appropriate touch function. If there is a touch funtion in the processing applet for this zone, that function is called. Otherwise, if there is touchImpl override from a subclass, that function is called. Otherwise, the touch function is called.
	 */
	protected void invokeTouch(){
		//is touching enabled?
		if( ! touching_enabled)
			return;
		//setup
		this.setDelegate( SMT.renderer);
		touching_on = true;
		pushMatrix();
		applyMatrix( matrix);
		pushStyle();
		beginTouch();

		//matrix setup
		PMatrix3D pretouch = (PMatrix3D) super.getMatrix();
		PMatrix3D pretouch_inv = new PMatrix3D( pretouch);
		pretouch_inv.invert();

		//invoke touch up
		for( Touch touch : touchUpList)
			this.invokeTouchUpMethod( touch);
		touchUpList.clear();

		//invoke touch down
		for( Touch touch : touchDownList)
			this.invokeTouchDownMethod( touch);
		touchDownList.clear();

		//invoke touch press
		for( Touch touch : pressList)
			this.invokePressMethod( touch);
		pressList.clear();

		//invoke touch moved
		for( Touch touch : touchMovedList)
			this.invokeTouchMovedMethod( touch);
		//invoke touch
		if( ! touchMovedList.isEmpty())
			this.invokeTouchMethod();
		touchMovedList.clear();

		//save it for later
		PMatrix3D posttouch = (PMatrix3D) super.getMatrix();

		//tell our children to invoke touch
		for( Zone child : children)
			if( child.isActive() || child.hasActiveChild())
				child.invokeTouch();

		//clean up
		endTouch();
		popStyle();
		popMatrix();
		this.setDelegate( null);
		touching_on = false;

		//save any transformations
		PMatrix3D change = new PMatrix3D();
		change.apply( pretouch_inv);
		change.apply( posttouch);
		matrix.apply( change);
	}

	/**
	 * Invokes the appropriate touchUp function for this zone.
	 * @param touch the touch that went up from this zone
	 */
	protected void invokeTouchUpMethod( Touch touch){
		if( method_touchUp != null)
			SMTUtilities.invoke( method_touchUp, applet, this, touch);
		else if( touchUpImpl_overridden)
			touchUpImpl( touch);
		else
			touchUp( touch);
	}
	/**
	 * Invokes the appropriate touchDown function for this zone.
	 * @param touch the touch that went down onto this zone
	 */
	protected void invokeTouchDownMethod( Touch touch){
		if( method_touchDown != null)
			SMTUtilities.invoke( method_touchDown, applet, this, touch);
		else if( touchDownImpl_overridden)
			touchDownImpl( touch);
		else
			touchDown( touch);
	}
	/**
	 * Invokes the appropriate press function for this zone.
	 * @param touch the touch that pressed this zone
	 */
	protected void invokePressMethod( Touch touch){
		if( method_press != null)
			SMTUtilities.invoke( method_press, applet, this, touch);
		else if( pressImpl_overridden)
			pressImpl( touch);
		else
			press( touch);
	}
	/**
	 * Invokes the appropriate touchMoved function for this zone.
	 * @param touch the touch moved on this zone
	 */
	protected void invokeTouchMovedMethod( Touch touch){
		if( method_touchMoved != null)
			SMTUtilities.invoke( method_touchMoved, applet, this, touch);
		else if( touchMovedImpl_overridden)
			touchMovedImpl( touch);
		else
			touchMoved( touch);
	}
	/**
	 * Invokes the appropriate touch function for this zone.
	 */
	protected void invokeTouchMethod(){
		//invoke proper touch method
		if( method_touch != null)
			SMTUtilities.invoke( method_touch, applet, this);
		else if( touchImpl_overridden)
			touchImpl();
		else
			touch();
	}

	/**
	 * Invokes the appropriate keyPressed function for this zone.
	 * @param event the key event that occured
	 */
	public void invokeKeyPressedMethod( KeyEvent event){
		if( method_keyPressed != null)
			SMTUtilities.invoke( method_keyPressed, applet, this, event);
		else if( keyPressedImpl_overridden)
			keyPressedImpl( event);
		else
			keyPressed( event);
	}

	/**
	 * Invokes the appropriate keyReleased function for this zone.
	 * @param event the key event that occured
	 */
	public void invokeKeyReleasedMethod( KeyEvent event){
		if( method_keyReleased != null)
			SMTUtilities.invoke( method_keyReleased, applet, this, event);
		else if( keyReleasedImpl_overridden)
			keyReleasedImpl( event);
		else
			keyReleased( event);
	}

	/**
	 * Invokes the appropriate keyTyped function for this zone.
	 * @param event the key event that occured
	 */
	public void invokeKeyTypedMethod( KeyEvent event){
		if( method_keyTyped != null)
			SMTUtilities.invoke( method_keyTyped, applet, this, event);
		else if( keyTypedImpl_overridden)
			keyTypedImpl( event);
		else
			keyTyped( event);
	}

	//default methods
	/** Override to specify a behavior for draw */
	public void draw(){
		fill( 50, 50, 50, 150);
		stroke( 255, 255, 255, 150);
		rect( 0, 0, width, height);
		fill( 255, 255, 255, 150);
		textSize( 16);
		text("No Draw Method", 0, 0, width, height);
	}
	/** Override to specify a behavior for pickDraw */
	public void pickDraw(){
		rect( 0, 0, width, height);
	}

	/** Override to specify a behavior for touch */
	public void touch(){
		this.drag();
	}

	//other touch methods
	/**
	 * Override to specify a behavior for touchDown 
	 * @param touch the touch that caused the touch-down event
	 */
	public void touchDown( Touch touch){
		addPhysicsMouseJoint();
	}
	/**
	 * Override to specify a behavior for touchMoved
	 * @param touch the touch that caused the touch-moved event
	 */
	public void touchMoved( Touch touch){
		addPhysicsMouseJoint();
	}
	/**
	 * Override to specify a behavior for touchUp
	 * @param touch the touch that caused the touch-up event
	 */
	public void touchUp( Touch touch){}
	/**
	 * Override to specify a behavior for press
	 * @param touch the touch that caused the touch-press event
	 */
	public void press( Touch touch){}

	//keyboard methods
	/**
	 * This method is for use by Processing, override it to change what occurs
	 * when a Processing KeyEvent is passed to the Zone
	 * @param event The Processing KeyEvent that is sent to the Zone
	 */
	public void keyEvent( KeyEvent event){
		switch ( event.getAction()){
			case KeyEvent.RELEASE:
				invokeKeyReleasedMethod( event);
				break;
			case KeyEvent.TYPE:
				invokeKeyTypedMethod( event);
				break;
			case KeyEvent.PRESS:
				invokeKeyPressedMethod( event);
				break;
		}
	}

	/**
	 * Override to specify a behavior for keyPressed
	 * @param event the details of the key-pressed event
	 */
	protected void keyPressed( KeyEvent event){}
	/**
	 * Override to specify a behavior for keyReleased
	 * @param event the details of the key-released event
	 */
	protected void keyReleased( KeyEvent event){}
	/**
	 * Override to specify a behavior for keyTyped
	 * @param event the details of the key-typed event
	 */
	protected void keyTyped( KeyEvent event){}

	//impl methods

	/**
	 * Override to specify a default behavior for draw 
	 * @deprecated override draw instead
	 */
	@Deprecated
	protected void drawImpl(){
		draw();
	}
	/**
	 * Override to specify a default behavior for pickDraw 
	 * @deprecated override pickDraw instead
	 */
	@Deprecated
	protected void pickDrawImpl(){
		pickDraw();
	}
	/**
	 * Override to specify a default behavior for touch 
	 * @deprecated override touch instead
	 */
	@Deprecated
	protected void touchImpl(){
		touch();
	}

	/**
	 * Override to specify a default behavior for touchDown 
	 * @param touch the touch thatcaused the touch-down event
	 * @deprecated override touchDown instead
	 */
	@Deprecated
	protected void touchDownImpl( Touch touch){}
	/**
	 * Override to specify a default behavior for touchUp 
	 * @param touch the touch that caused the touch-up event
	 * @deprecated override touchUp instead
	 */
	@Deprecated
	protected void touchUpImpl( Touch touch){}
	/**
	 * Override to specify a default behavior for press 
	 * @param touch the touch that caused the touch-press event
	 * @deprecated override press instead
	 */
	@Deprecated
	protected void pressImpl( Touch touch){}
	/**
	 * Override to specify a default behavior for touchMoved 
	 * @param touch the touch that caused the touch-moved event
	 * @deprecated override touchMoved instead
	 */
	@Deprecated
	protected void touchMovedImpl( Touch touch){}

	/**
	 * Override to specify a default behavior for keyPressed 
	 * @param event the details of the key-pressed event
	 * @deprecated override keyPressed instead
	 */
	@Deprecated
	protected void keyPressedImpl( KeyEvent event){}
	/**
	 * Override to specify a default behavior for keyReleased
	 * @param event the details of the key-released event
	 * @deprecated override keyReleased instead
	 */
	@Deprecated
	protected void keyReleasedImpl( KeyEvent event){}
	/**
	 * Override to specify a default behavior for keyTyped 
	 * @param event the details of the key-typed event
	 * @deprecated override keyTyped instead
	 */
	@Deprecated
	protected void keyTypedImpl( KeyEvent event){}


	//touch registration methods
	/**
	 * Add a touch to the touch down list
	 * @param touch the touch to add
	 */
	public void touchDownRegister(Touch touch){
		touchDownList.add(touch);
	}
	/**
	 * Add a touch to the touch up list
	 * @param touch the touch to add
	 */
	public void touchUpRegister(Touch touch){
		touchUpList.add(touch);
	}
	/**
	 * Add a touch to the press list
	 * @param touch the touch to add
	 */
	public void pressRegister(Touch touch){
		pressList.add(touch);
	}
	/**
	 * Add a touch to the touch moved list
	 * @param touch the touch to add
	 */
	public void touchMovedRegister(Touch touch){
		touchMovedList.add(touch);
	}

	//begin/end methods
	/**
	 * This is called before the pick draw function is called
	 * @deprecated This is no longer needed by anything.
	 */
	@Deprecated
	public void beginPickDraw(){}
	/**
	 * This is called after the pick draw function is called
	 * @deprecated This is no longer needed by anything.
	 */
	@Deprecated
	public void endPickDraw(){}
	/**
	 * This is called before the touch function is called
	 * @deprecated This is no longer needed by anything.
	 */
	@Deprecated
	public void beginTouch(){}
	/**
	 * This is called after the touch function is called
	 * @deprecated This is no longer needed by anything.
	 */
	@Deprecated
	public void endTouch(){}

	/**
	 * @param name The name of the zone to load the methods from
	 */
	protected void loadMethods( String name){
		if ( SMT.warnUnimplemented != null){
			if ( SMT.warnUnimplemented.booleanValue())
				loadMethods( name, true, true, true, true, true, true);
			else
				loadMethods( name, false, false, false, false, false, false);
		}
		else 
			loadMethods(
				name,
				warnDraw(), warnTouch(), warnKeys(),
				warnPick(), warnTouchUDM(), warnPress());
	}

	/**
	 * Prepares all of the methods for this zone.
	 * 
	 * @param name The name of the zone to load methods from, used as a suffix
	 * @param warnDraw Display a warning when the draw method doesn't exist
	 * @param warnTouch Display a warning when the touch method doesn't exist
	 * @param warnKeys Display a warning when the keyTyped/keyPressed/keyReleased methods don't exist
	 * @param warnPick Display a warning when the pickDraw method doesn't exist
	 * @param warnTouchUDM Display a warning when the touchUp/touchDown/touchMoved methods don't exist
	 * @param warnPress Display a warning when the touchPress methods don't exist
	 */
	protected void loadMethods(
			String name, boolean warnDraw, boolean warnTouch, boolean warnKeys,
			boolean warnPick, boolean warnTouchUDM, boolean warnPress){


		//check for extended class implementations
		drawImpl_overridden =
			SMTUtilities.checkImpl( Zone.class, "draw", this.getClass());
		pickDrawImpl_overridden =
			SMTUtilities.checkImpl( Zone.class, "pickDraw", this.getClass());
		touchImpl_overridden =
			SMTUtilities.checkImpl( Zone.class, "touch", this.getClass());
		touchUpImpl_overridden =
			SMTUtilities.checkImpl( Zone.class, "touchUp", this.getClass(), Touch.class);
		touchDownImpl_overridden =
			SMTUtilities.checkImpl( Zone.class, "touchDown", this.getClass(), Touch.class);
		touchMovedImpl_overridden =
			SMTUtilities.checkImpl( Zone.class, "touchMoved", this.getClass(), Touch.class);
		pressImpl_overridden =
			SMTUtilities.checkImpl( Zone.class, "press", this.getClass(), Touch.class);
		keyPressedImpl_overridden =
			SMTUtilities.checkImpl( Zone.class, "keyPressed", this.getClass(), KeyEvent.class);
		keyReleasedImpl_overridden =
			SMTUtilities.checkImpl( Zone.class, "keyReleased", this.getClass(), KeyEvent.class);
		keyTypedImpl_overridden =
			SMTUtilities.checkImpl( Zone.class, "keyTyped", this.getClass(), KeyEvent.class);

		//check normal methods
		Class<?> current_class = this.getClass();
		//check draw method overridden
		try {
			Method actual_draw = current_class.getMethod( "draw");
			Class<?> actual_draw_class = actual_draw.getDeclaringClass();
			draw_overridden = actual_draw_class != Zone.class;}
		//wtf why?
		catch( NoSuchMethodException exception){}
		//check pickDraw method overridden
		try {
			Method actual_pickDraw = current_class.getMethod( "pickDraw");
			Class<?> actual_pickDraw_class = actual_pickDraw.getDeclaringClass();
			pickDraw_overridden = actual_pickDraw_class != Zone.class;}
		//wtf why?
		catch( NoSuchMethodException exception){}
		//check touch method overridden
		try {
			Method actual_touch = current_class.getMethod( "touch");
			Class<?> actual_touch_class = actual_touch.getDeclaringClass();
			touch_overridden = actual_touch_class != Zone.class;}
		//wtf why?
		catch( NoSuchMethodException exception){}
		//check touch up method overridden
		try {
			Method actual_touchUp = current_class.getMethod( "touchUp", Touch.class);
			Class<?> actual_touchUp_class = actual_touchUp.getDeclaringClass();
			touchUp_overridden = actual_touchUp_class != Zone.class;}
		//wtf why?
		catch( NoSuchMethodException exception){}
		//check touch down method overridden
		try {
			Method actual_touchDown = current_class.getMethod( "touchDown", Touch.class);
			Class<?> actual_touchDown_class = actual_touchDown.getDeclaringClass();
			touchDown_overridden = actual_touchDown_class != Zone.class;}
		//wtf why?
		catch( NoSuchMethodException exception){}
		//check touch moved method overridden
		try {
			Method actual_touchMoved = current_class.getMethod( "touchMoved", Touch.class);
			Class<?> actual_touchMoved_class = actual_touchMoved.getDeclaringClass();
			touchMoved_overridden = actual_touchMoved_class != Zone.class;}
		//wtf why?
		catch( NoSuchMethodException exception){}
		//check press method overridden
		try {
			Method actual_press = current_class.getMethod( "press", Touch.class);
			Class<?> actual_press_class = actual_press.getDeclaringClass();
			press_overridden = actual_press_class != Zone.class;}
		//wtf why?
		catch( NoSuchMethodException exception){}
		//check keyPressed method overridden
		try {
			Method actual_keyPressed = current_class.getMethod( "keyPressed", KeyEvent.class);
			Class<?> actual_keyPressed_class = actual_keyPressed.getDeclaringClass();
			keyPressed_overridden = actual_keyPressed_class != Zone.class;}
		//wtf why?
		catch( NoSuchMethodException exception){}
		//check keyReleased method overridden
		try {
			Method actual_keyReleased = current_class.getMethod( "keyReleased", KeyEvent.class);
			Class<?> actual_keyReleased_class = actual_keyReleased.getDeclaringClass();
			keyReleased_overridden = actual_keyReleased_class != Zone.class;}
		//wtf why?
		catch( NoSuchMethodException exception){}
		//check keyTyped method overridden
		try {
			Method actual_keyTyped = current_class.getMethod( "keyTyped", KeyEvent.class);
			Class<?> actual_keyTyped_class = actual_keyTyped.getDeclaringClass();
			keyTyped_overridden = actual_keyTyped_class != Zone.class;}
		//wtf why?
		catch( NoSuchMethodException exception){}

		//debug
		/*System.out.printf( "draw_overridden: %b\n", draw_overridden);
		System.out.printf( "drawImpl_overridden: %b\n", drawImpl_overridden);
		System.out.printf( "pickDraw_overridden: %b\n", pickDraw_overridden);
		System.out.printf( "pickDrawImpl_overridden: %b\n", pickDrawImpl_overridden);
		System.out.printf( "touch_overridden: %b\n", touch_overridden);
		System.out.printf( "touchImpl_overridden: %b\n", touchImpl_overridden);
		System.out.println();*/

		if (name != null){
			//get draw method
			method_draw = SMTUtilities.getZoneMethod(
				Zone.class, applet, "draw", name,
				draw_overridden || drawImpl_overridden ? false : warnDraw,
				this.getClass());

			//get pick draw method
			method_pickDraw = SMTUtilities.getZoneMethod(
				Zone.class, applet, "pickDraw", name,
				pickDraw_overridden || pickDrawImpl_overridden ? false : warnPick,
				this.getClass());

			//get key methods
			method_keyPressed = SMTUtilities.getZoneMethod(
				Zone.class, applet, "keyPressed", name, warnKeys,
				this.getClass(), KeyEvent.class);
			method_keyReleased = SMTUtilities.getZoneMethod(
				Zone.class, applet, "keyReleased", name, warnKeys,
				this.getClass(), KeyEvent.class);
			method_keyTyped = SMTUtilities.getZoneMethod(
				Zone.class, applet, "keyTyped", name, warnKeys,
				this.getClass(), KeyEvent.class);

			//get touch methods
			method_touchUp = SMTUtilities.getZoneMethod(
				Zone.class, applet, "touchUp", name, warnTouchUDM,
				this.getClass(), Touch.class);
			method_touchDown = SMTUtilities.getZoneMethod(
				Zone.class, applet, "touchDown", name, warnTouchUDM,
				this.getClass(), Touch.class);
			method_touchMoved = SMTUtilities.getZoneMethod(
				Zone.class, applet, "touchMoved", name, warnTouchUDM,
				this.getClass(), Touch.class);

			//get press method
			method_press = SMTUtilities.getZoneMethod(
				Zone.class, applet, "press", name, warnPress,
				this.getClass(), Touch.class);

			//check if any other touch functions have been overridden
			boolean touchUDM_overridden =
				touchUpImpl_overridden ||
				touchDownImpl_overridden ||
				touchMovedImpl_overridden;
			if( method_touchUp != null || method_touchDown != null ||
					method_touchMoved != null || touchUDM_overridden ||
					pressImpl_overridden || method_press != null) 
				warnTouch = false;
			//get touch method
			method_touch = SMTUtilities.getZoneMethod(
				Zone.class, applet, "touch", name,
				touch_overridden || touchImpl_overridden ? false : warnTouch,
					this.getClass());
		}

		this.setCaptureTouches(
			! ( pressImpl_overridden || method_press != null));
	}

	/////////////////////////////////////
	// Zone 'Gesture' Transformations //
	/////////////////////////////////////

	/** 
	 * Translates the zone, its group, and its children
	 */
	public void drag(){
		drag( true, true);
	}

	/**
	 * Performs translate on this zone, using the first assigned touch's movement.
	 * 
	 * @param dragX Whether to drag along the x-axis
	 * @param dragY Whether to drag along the y-axis
	 */
	public void drag( boolean dragX, boolean dragY){
		drag( dragX, dragX, dragY, dragY);
	}

	/**
	 * Performs translate on this zone, using the first assigned touch's movement.
	 * 
	 * @param dragLeft Allow dragging left
	 * @param dragRight Allow dragging Right
	 * @param dragUp Allow dragging Up
	 * @param dragDown Allow dragging Down
	 */
	public void drag(
			boolean dragLeft, boolean dragRight,
			boolean dragUp, boolean dragDown){
		drag(
			dragLeft, dragRight, dragUp, dragDown,
			Integer.MIN_VALUE, Integer.MAX_VALUE,
			Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Performs translate on this zone, using the first assigned touch's movement.
	 * 
	 * @param dragX Allow dragging along the x-axis
	 * @param dragY Allow dragging along the y-axis
	 * @param leftLimit Limit on how far to be able to drag left
	 * @param rightLimit Limit on how far to be able to drag right
	 * @param upLimit Limit on how far to be able to drag up
	 * @param downLimit Limit on how far to be able to drag down
	 */
	public void drag(
			boolean dragX, boolean dragY,
			int leftLimit, int rightLimit, int upLimit, int downLimit){
		drag(
			dragX, dragX, dragY, dragY,
			leftLimit, rightLimit, upLimit, downLimit);
	}

	/**
	 * Performs translate on this zone, using the first assigned touch's movement.
	 * 
	 * @param drag_left Allow dragging left
	 * @param drag_right Allow dragging Right
	 * @param drag_up Allow dragging Up
	 * @param drag_down Allow dragging Down
	 * @param limit_left Limit on how far to be able to drag left
	 * @param limit_right Limit on how far to be able to drag right
	 * @param limit_up Limit on how far to be able to drag up
	 * @param limit_down Limit on how far to be able to drag down
	 */
	public void drag(
			boolean drag_left, boolean drag_right, boolean drag_up, boolean drag_down,
			int limit_left, int limit_right, int limit_up, int limit_down){
		//get touches
		Touch[] touches = this.getTouches();
		if( touches.length < 0)
			return;

		//get global inverse
		PMatrix3D global = (PMatrix3D) getGlobalMatrix();
		PMatrix3D global_inv = new PMatrix3D( global);
		global_inv.invert();

		//get first touch
		Touch first = touches[0];

		//get touch locations in current co-ordinate system
		PVector t0_g = first.getPositionVector();
		PVector t1_g = first.getPositionAtTime( lastUpdate);
		if( t1_g == null) t1_g = t0_g;
		PVector t0 = global_inv.mult( t0_g, null);
		PVector t1 = global_inv.mult( t1_g, null);
		PVector delta = PVector.sub( t0, t1);

		//clamp right delta
		if( delta.x > 0)
			if( ! drag_right || limit_right <= this.x)
				delta.x = 0;
			else
				delta.x = Math.min( delta.x, limit_right - this.x);
		//clamp left delta
		if( delta.x < 0)
			if( ! drag_left || limit_left >= this.x)
				delta.x = 0;
			else
				delta.x = Math.max( delta.x, this.x - limit_left);
		//clamp up delta
		if( delta.y > 0)
			if( ! drag_down || limit_down <= this.y)
				delta.y = 0;
			else
				delta.y = Math.min( delta.y, limit_down - this.y);
		//clamp down delta
		if( delta.y < 0)
			if( ! drag_up || limit_up >= this.y)
				delta.y = 0;
			else
				delta.y = Math.max( delta.y, this.y - limit_up);

		//apply delta
		translate( delta.x, delta.y);

		//reset last update time
		lastUpdate = maxTuioTime(
			lastUpdate, first.currentTime);
	}

	/**
	 * Performs rotate/scale/translate 'gesture' on the zone.
	 */
	public void rst(){
		rst( true, true, true, true);
	}

	/**
	 * * Performs rotate/scale/translate 'gesture' on the zone.
	 * 
	 * @param rotate Whether rotation should happen
	 * @param scale Whether scaling should happen
	 * @param translate Whether tranlation should happen
	 */
	public void rst( boolean rotate, boolean scale, boolean translate){
		rst(rotate, scale, translate, translate);
	}

	/**
	 * Performs rotate/scale/translate 'gesture' on the zone.
	 * 
	 * @param rotate Whether rotation should happen
	 * @param scale Whether scaling should happen
	 * @param translate_x Whether tranlation in the x direction should happen
	 * @param translate_y Whether tranlation in the y direction should happen
	 */
	public void rst( boolean rotate, boolean scale, boolean translate_x, boolean translate_y){

		//get touches
		Touch[] touches = this.getTouches();
		if( touches.length < 0)
			return;

		//get global inverse
		PMatrix3D global = (PMatrix3D) getGlobalMatrix();
		PMatrix3D global_inv = new PMatrix3D( global);
		global_inv.invert();

		//get first touch
		Touch first = touches[0];

		//get touch locations in current co-ordinate system
		PVector t00_g = first.getPositionVector();
		PVector t01_g = first.getPositionAtTime( lastUpdate);
		if( t01_g == null) t01_g = t00_g;
		PVector t00 = global_inv.mult( t00_g, null);
		PVector t01 = global_inv.mult( t01_g, null);

		//if we only have one
		if( touches.length == 1){
		//just do translations
			PVector dt = PVector.sub( t00, t01);
			translate(
				translate_x ? dt.x : 0,
				translate_y ? dt.y : 0, 0);

			//update time
			lastUpdate = maxTuioTime(
				lastUpdate, first.currentTime);

			//we're done
			return;
		}
		//if we have two or more touches
		else {
			//get second touch
			Touch second = touches[1];

			//get touch locations in current co-ordinate system
			PVector t10_g = second.getPositionVector();
			PVector t11_g = second.getPositionAtTime( lastUpdate);
			if( t11_g == null) t11_g = t10_g;
			PVector t10 = global_inv.mult( t10_g, null);
			PVector t11 = global_inv.mult( t11_g, null);

			//get line between first and second at both times
			PVector bar0 = PVector.sub( t10, t00);
			PVector bar1 = PVector.sub( t11, t01);
			float bar0_mag = bar0.mag();
			float bar1_mag = bar1.mag();
			PVector bar_cross = PVector.cross( bar1, bar0, null);
			bar_cross.normalize();

			//push to new location
			if( ! ( translate_x || translate_y))
				translate(
					halfDimension.width,
					halfDimension.height, 0);
			else
				translate(
					translate_x ? t00.x : 0,
					translate_y ? t00.y : 0, 0);

			if( bar0_mag > 0 && bar1_mag > 0){
				//apply rotate
				if( rotate && bar_cross.z != 0){
					float theta = PVector.angleBetween( bar1, bar0);
					rotate( theta,
						bar_cross.x, bar_cross.y, bar_cross.z);
				}

				//apply scale
				if( scale){
					float ratio = bar0_mag / bar1_mag;
					//clamp ratio ?
					scale( ratio, ratio, 1);
				}
			}

			//pull from old location
			if( ! ( translate_x || translate_y))
				translate(
					- halfDimension.width,
					- halfDimension.height, 0);
			else
				translate(
					translate_x ? - t01.x : 0,
					translate_y ? - t01.y : 0, 0);

			//update time
			lastUpdate = maxTuioTime(
				lastUpdate, first.currentTime, second.currentTime);
		}
	}

	/**
	 * Rotates and scales the zone. Uses two touches. Only works inside the
	 * zone's touch method, or between calls to beginTouch() and endTouch()
	 */
	public void rs(){
		rst( true, true, false);
	}

	/** Rotate and translate.
	 *
	 * Single finger gesture, Which only translates inside of the rotation radius, and rotates and translates outside of this radius.
	 *
	 * Using a default radius of min(width, height)/4, A quarter of the smallest side of the zone. Only works inside the zone's touch method, or between calls to beginTouch() and endTouch().
	 */
	public void rnt(){
		rst( true, false, true, true);
	}

	/**
	 * Scales the zone. Uses two touches Only works inside the zone's touch method, or between calls to beginTouch() and endTouch()
	 */
	public void pinch(){
		rst( false, true, true);
	}



	/**
	 * Rotates the zone, using two touches, about the centre Only works inside
	 * the zone's touch method, or between calls to beginTouch() and endTouch()
	 */
	public void scale(){
		rst( false, true, false);
	}
	/**
	 * Rotates the zone, using two touches, about the centre Only works inside
	 * the zone's touch method, or between calls to beginTouch() and endTouch()
	 */
	public void rotate(){
		rst( true, false, false);
	}

	/**
	 * Rotates the zone around the specified x and y coordinates
	 * 
	 * @param angle The angle to rotate specified in radians
	 * @param x the x coordinate to rotate around
	 * @param y the y coordinate to rotate around
	 */
	public void rotateAbout( float angle, int x, int y){
		translate( x, y);
		rotate( angle);
		translate( -x, -y);
	}

	/**
	 * Rotates the zone around either the centre or corner
	 * 
	 * @param angle the angle to rotate specified in radians
	 * @param mode CENTER or CORNER
	 */
	public void rotateAbout(float angle, int mode){
		if( mode == CORNER)
			rotateAbout( angle, x, y);
		else if ( mode == CENTER)
			rotateAbout( angle, x + width / 2, y + height / 2);
	}

	/**
	 * Horizontal Swipe. Uses a single touch. Only works inside the zone's touch
	 * method, or between calls to beginTouch() and endTouch()
	 */
	public void hSwipe(){
		drag(true, false);
	}

	/**
	 * Horizontal Swipe. Uses a single touch. Only works inside the zone's touch
	 * method, or between calls to beginTouch() and endTouch().
	 * 
	 * Limits are absolute and are in global coordinates.
	 * 
	 * @param leftLimit Limit on how far to be able to drag left
	 * @param rightLimit Limit on how far to be able to drag right
	 */
	public void hSwipe(int leftLimit, int rightLimit){
		drag(true, false, leftLimit, rightLimit, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Vertical Swipe. Uses a single touch. Only works inside the zone's touch
	 * method, or between calls to beginTouch() and endTouch()
	 */
	public void vSwipe(){
		drag(false, true);
	}

	/**
	 * Vertical Swipe. Uses a single touch. Only works inside the zone's touch
	 * method, or between calls to beginTouch() and endTouch().
	 * 
	 * Limits are absolute and are in global coordinates.
	 * 
	 * @param upLimit Limit on how far to be able to drag up
	 * @param downLimit Limit on how far to be able to drag down
	 */
	public void vSwipe(int upLimit, int downLimit){
		drag(false, true, Integer.MIN_VALUE, Integer.MAX_VALUE, upLimit, downLimit);
	}

	/**
	 * Swipe which only allows moving left. Uses a single touch. Only works
	 * inside the zone's touch method, or between calls to beginTouch() and
	 * endTouch()
	 */
	public void swipeLeft(){
		drag(true, false, false, false);
	}

	/**
	 * Swipe which only allows moving right. Uses a single touch. Only works
	 * inside the zone's touch method, or between calls to beginTouch() and
	 * endTouch()
	 */
	public void swipeRight(){
		drag(false, true, false, false);
	}

	/**
	 * Swipe which only allows moving up. Uses a single touch. Only works inside
	 * the zone's touch method, or between calls to beginTouch() and endTouch()
	 */
	public void swipeUp(){
		drag(false, false, true, false);
	}

	/**
	 * Swipe which only allows moving down. Uses a single touch. Only works
	 * inside the zone's touch method, or between calls to beginTouch() and
	 * endTouch()
	 */
	public void swipeDown(){
		drag(false, false, false, true);
	}

	/**
	 * Tosses a zone. Acts similarly to drag while the touch is on the zone, but when the touch is released, the zone slides for a bit, then slows to a stop.
	 */
	public void toss(){
		// enable physics on this zone to make sure it can move from the toss
		setPhysicsEnabled( true);
		Touch touch = getActiveTouch( 0);
		if( zoneBody != null && mJoint != null)
			mJoint.setTarget(
				new Vec2(
					touch.x * SMT.box2dScale,
					( applet.height - touch.y) * SMT.box2dScale));
	}

	public void dragWithinParent(){
		Zone parent = getParent();
		if (parent != null)
			drag( true, true,
				0, parent.width - this.width,
				0, parent.height - this.height);
	}

	/////////////////////
	// Touch Accesors //
	/////////////////////

	/**
	 * Assigns a touch to this zone, maintaining the order of touches.
	 * @param touches A number of Touch objects, variable number of arguments
	 */
	public void assign(Touch... touches){
		assign( Arrays.asList( touches));
	}

	/**
	 * Assigns a touch to this zone, maintaining the order of touches.
	 * @param touches A number of Touch objects, in an Iterable object that
	 * contains Touch objects
	 */
	public void assign( Iterable<? extends Touch> touches){
		for( Touch touch : touches){
			activeTouches.put( touch.sessionID, touch);
			touch.assignZone( this);
		}
	}

	/**
	 * Unassigns the given Touch from this zone, removing it from activeTouches.
	 * @param touch the touch to remove from this zone
	 */
	public void unassign( Touch touch){
		unassign( touch.sessionID);
	}

	/**
	 * Unassigns the Touch corresponding to the sessionID given from this zone,
	 * removing it from activeTouches.
	 * @param sessionID the session id of the touch to remove from this zone
	 */
	public void unassign( long sessionID){
		Touch t = activeTouches.get(sessionID);
		// only removes if it exists in the touch mapping
		if (t != null){
			activeTouches.remove(sessionID);
			t.unassignZone(this);
			// at unassign if we have a mJoint destroy it
			if (mJoint != null){
				SMT.world.destroyJoint(mJoint);
				mJoint = null;
			}
		}
	}

	/**
	 * Unassigns all Touch objects from this zone, clearing activeTouches.
	 */
	public void unassignAll(){
		long[] touchids = new long[activeTouches.keySet().size()];
		int i = 0;
		for (long id : activeTouches.keySet()){
			touchids[i] = id;
			i++;
		}

		for (long id : touchids)
			unassign(id);
	}

	/**
	 * Check whether the given touch is assigned to this zone.
	 * @param touch the touch to check for assignment
	 * @return Whether the given touch is assigned to this zone
	 */
	public boolean isAssigned( Touch touch){
		return isAssigned( touch.sessionID);
	}

	/**
	 * Check whether the given touch is assigned to this zone.
	 * @param id the id of the touch to check for assignment
	 * @return Whether the Touch corresponding to the given id is assigned to
	 * this zone
	 */
	public boolean isAssigned( long id){
		return activeTouches.containsKey( id);
	}

	/**
	 * Add one or more child zones to this zone.
	 * @param zones the list of zones to add
	 * @return true if every given zone was added sucessfully, false otherwise
	 */
	public boolean add( Zone... zones){
		boolean result = true;
		for( Zone zone : zones)
			if( ! add( zone))
				result = false;
		return result;
	}

	///////////////////////////
	// Child Zone Accessors //
	///////////////////////////

	/**
	 * Add a child zone to this zone.
	 * @param zone The zone to add to this zone
	 * @return Whether the zone was successfully added or not
	 */
	public boolean add( Zone zone){
		if( zone != null){
			if( zone != this && ! isAncestor( zone)){
				zone.removeFromParent();
				zone.parent = this;

				SMT.picker.add(zone);

				if( ! children.contains( zone))
					return children.add( zone);
			}
			else
				try { throw new RuntimeException(
					"Warning: Added a Zone to itself or an ancestor");}
				catch( RuntimeException exception){
					exception.printStackTrace();}
		}
		else throw new NullPointerException(
			"Tried to add null to a zone");
		return false;
	}

	/**
	 * This adds zones by creating them from XML specs, the XML needs "zone"
	 * tags, and currently supports the following variables: name, x, y, width,
	 * height, img
	 * 
	 * @param xmlFilename The XML file to read in for zone configuration
	 * @return The array of zones created from the XML File
	 */
	/**public Zone[] addXMLZone(String xmlFilename){
		List<Zone> zoneList = new ArrayList<Zone>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(applet.createInput(xmlFilename));

			NodeList zones = doc.getElementsByTagName("zone");
			add(zones, zoneList);
		}
		catch (ParserConfigurationException e){
			e.printStackTrace();
		}
		catch (SAXException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return zoneList.toArray(new Zone[zoneList.size()]);
	}

	private void add(NodeList zones, List<Zone> zoneList){
		for (int i = 0; i < zones.getLength(); i++){
			Node zoneNode = zones.item(i);
			if( zoneNode.getNodeType() == Node.ELEMENT_NODE
					&& zoneNode.getNodeName().equalsIgnoreCase("zone")){
				add(zoneNode, zoneList);
			}
		}
	}

	private void add(Node node, List<Zone> zoneList){
		NamedNodeMap map = node.getAttributes();
		Node nameNode = map.getNamedItem("name");
		Node xNode = map.getNamedItem("x");
		Node yNode = map.getNamedItem("y");
		Node widthNode = map.getNamedItem("width");
		Node heightNode = map.getNamedItem("height");

		Node imgNode = map.getNamedItem("img");

		Zone zone;

		String name = null;
		int x, y, width, height;
		if (nameNode != null){
			name = nameNode.getNodeValue();
		}

		if (imgNode != null){
			String imgFilename = imgNode.getNodeValue();
			PImage img = applet.loadImage(imgFilename);

			if (xNode != null && yNode != null){
				x = Integer.parseInt(xNode.getNodeValue());
				y = Integer.parseInt(yNode.getNodeValue());
				if (widthNode != null && heightNode != null){
					width = Integer.parseInt(widthNode.getNodeValue());
					height = Integer.parseInt(heightNode.getNodeValue());
					zone = new ImageZone(name, img, x, y, width, height);
				}
				else {
					zone = new ImageZone(name, img, x, y);
				}
			}
			else {
				zone = new ImageZone(name, img);
			}
		}
		else {
			if (xNode != null && yNode != null && widthNode != null && heightNode != null){
				x = Integer.parseInt(xNode.getNodeValue());
				y = Integer.parseInt(yNode.getNodeValue());
				width = Integer.parseInt(widthNode.getNodeValue());
				height = Integer.parseInt(heightNode.getNodeValue());
				zone = new Zone(name, x, y, width, height);
			}
			else {
				zone = new Zone(name);
			}
		}

		zoneList.add(zone);
		add(zone);
		add(node.getChildNodes(), zoneList);
	}*/

	/**
	 * @param child The child of this zone to remove
	 * @return Whether the zone was successfully removed or not
	 */
	public boolean remove( Zone child){
		if( child != null){
			if( children.contains( child)){
				child.cleanUp();
				child.parent = null;
				return children.remove( child);
			}
			else
				System.err.println(
					"Warning: Removed a Zone that was not a child");
		}
		else
			System.err.println(
				"Warning: Removed a null Zone");
		return false;
	}

	/**
	 * Removes this zone from its parent
	 */
	public void removeFromParent(){
		if(parent != null && parent.children.contains(this))
			parent.remove(this);
		else if( SMT.debug)
			System.err.println( "Warning: removeFromParent where parent is null or this zone is not a child of");
	}

	/**
	 * Removes multiple child zone from this zone.
	 * @param  zones the zones to remove from this zone
	 * @return whether all the zones were already children of this zone
	 */
	public boolean remove( Zone... zones){
		boolean result = true;
		for( Zone zone : zones)
			if( ! remove( zone))
				result = false;
		return result;
	}

	//////////////////////
	// Other Accessors //
	//////////////////////

	/**
	 * Indicates whether another object is equal to this one
	 * @param other the object with which to compare
	 * @return true if this object's hashcode equals the other's
	 */
	@Override
	public boolean equals( Object other){
		return this.hashCode() == other.hashCode();
	}

	/**
	 * Create a hashcode for this zone
	 * @return a hashcode for this zone
	 */
	@Override
	public int hashCode(){
		return Objects.hash(
			applet, parent, name, x, y, height, width, dimension,
			matrix, pickColor, renderer_name, direct);
	}

	/**
	 * Check whether drawing is enabled for this zone and its children.
	 *
	 * @return whether this zone will draw
	 */
	public boolean getVisible(){
		return drawing_enabled;
	}
	/**
	 * Check whether picking is enabled for this zone and its children.
	 * 
	 * @return whether this zone will pick-draw
	 */
	public boolean getPickable(){
		return picking_enabled;
	}
	/**
	 * Check whether touching is enabled for this zone and its children.
	 *
	 * @return whether this zone can be touched
	 */
	public boolean getTouchable(){
		return touching_enabled;
	}

	/**
	 * Enable or disable drawing for this zone and its children.
	 *
	 * This will not disable pick-drawing nor touching. See setPickable( boolean) for  and setTouchable( boolean), respectively.
	 * 
	 * @param enabled whether this zone should draw
	 */
	public void setVisible( boolean enabled){
		drawing_enabled = enabled;
		//picking_enabled = enabled;
	}
	/**
	 * Enable or disable picking for this zone and its children.
	 * 
	 * @param enabled whether this zone should pick-draw
	 */
	public void setPickable( boolean enabled){
		picking_enabled = enabled;
	}
	/**
	 * Enable or disable touching for this zone and its children.
	 *
	 * This will not disable picking for this zone, nor will it unassign any touches already assigned to this zone. See setPickable( false) or unassignAll(), respectively.
	 *
	 * @param enabled whether this zone can be touched
	 */
	public void setTouchable( boolean enabled){
		touching_enabled = enabled;
	}

	/**
	 * Check state of the direct flag.
	 * 
	 * The direct flag controls whether rendering directly onto parent/screen/pickBuffer (direct), or into an image (not direct) If drawing into an image we have assured size(cant draw outside of zone), and background() will work for just the zone, but we lose a large amount of performance.
	 * 
	 * @return whether zone is rendering directly onto screen/pickBuffer, or not
	 */
	public boolean isDirect(){
		return direct;
	}

	/**
	 * Change state of the direct flag.
	 * 
	 * The direct flag controls whether rendering directly onto parent/screen/pickBuffer (direct), or into an image (not direct) If drawing into an image we have assured size(cant draw outside of zone), and background() will work for just the zone, but we lose a large amount of performance.
	 *
	 * @param enabled Whether the direct flag should be enabled
	 */
	public void setDirect( boolean enabled){
		this.direct = enabled;
		if( enabled){
			this.vertices = null;
			this.tessGeo = null;
			this.texCache = null;
			this.inGeo = null;
		}
		else
			refreshResolution();
	}

	/**
	 * Ensures that the extra_graphics object is initialized if it's supposed to be.
	 */
	protected void extraGraphicsNullCheck(){
		if( ! direct)
			if( extra_graphics == null)
				refreshResolution();
	}

	/**
	 * This method refreshes this zone's offscreen graphics buffer's resolution to match the size the zone is being drawn at.
	 *
	 * To put it simply, if you scale an indirect zone, it'll get fuzzy. If you scale an indirect zone, then call this method, it won't get fuzzy.
	 *
	 * This method does nothing to normal, 'direct', zones.
	 */
	public void refreshResolution(){
		//get needed info
		Dimension screen_size = this.getScreenSize();
		setResolution( screen_size);
	}

	/**
	 * Set the resolution of the internal graphics object used by this zone (if it is indirect).
	 *
	 * This method does nothing to normal, 'direct', zones.
	 *
	 * @param desired_size the desired resolution of the internal graphics object
	 */
	public void setResolution( Dimension desired_size){
		//don't bother if we're direct
		if( direct) return;

		//create offscreen graphics context
		PGraphics extra_object = applet.createGraphics(
			desired_size.width,
			desired_size.height,
			this.renderer_name);
		//check the class
		if( ! ( extra_object instanceof PGraphics3D))
			throw new ClassCastException(
				"Must use PGraphics3D, or a class that extends PGraphics3D as the renderer 	for zones.");
		extra_graphics = (PGraphics3D) extra_object;

		//reset extra_graphics's scale matrix
		if( extra_matrix == null)
			extra_matrix = new PMatrix3D();
		else
			extra_matrix.reset();
		float scale_x = (float) desired_size.width / dimension.width;
		float scale_y = (float) desired_size.height / dimension.height;
		extra_matrix.scale( scale_x, scale_y);
		//finish up
		setModified();
	}

	/**
	 * Resizes the extra_graphics object to match the current dimensions of the Zone
	 */
	protected void initExtraGraphics(){
		setResolution( this.dimension);
	}

	/**
	 * Set whether this zone will capture touches or not.
	 *
	 * Normally, zones "capture" touches. This means that normally when touches exit a zone, they remain assigned to that zone. This "capturing" behavior, however, can be disabled. This is commonly done with buttons and other undraggable UI elements.
	 *
	 * @param enabled whether touch capturing should be enabled
	 */
	public void setCaptureTouches( boolean enabled){
		captureTouches = enabled;
	}

	/**
	 * Check whether this zone will capture touches or not.
	 *
	 * Normally, zones "capture" touches. This means that normally when touches exit a zone, they remain assigned to that zone. This "capturing" behavior, however, can be disabled. This is commonly done with buttons and UI elements.
	 *
	 * @return whether this zone will capture touches or not
	 */
	public boolean getCaptureTouches(){
		return captureTouches;
	}

	/**
	 * Get the current pick color of this zone.
	 * @return the current pick color of this zone
	 */
	public Color getPickColor(){
		return pickColor;
	}

	/**
	 * Set the desired pick colour of this zone. Do not call this.
	 * @param color the desired pick colour of this zone.
	 */
	public void setPickColor( Color color){
		this.pickColor = color;
	}

	/**
	 * Set the current pick colour to null
	 */
	public void removePickColor(){
		pickColor = null;
	}

	/**
	 * Change the name of this zone.
	 * @param name The new name of the zone
	 */
	public void setName( String name){
		if( name != null){
			if( name.length() < 1)
				throw new IllegalArgumentException(
					"Cannot give a zone an empty string for a name");
			this.name = name;
		}
		else {
			name = this.getClass().getSimpleName();
			if( name.length() < 1)
				//this is an anonymous class!
				name = this.getClass().getName();
			this.name = name;
		}
		loadMethods( this.name);
	}

	/**
	 * Get the name of this zone
	 * @return The name of the zone
	 */
	public String getName(){
		return name;
	}

	/**
	 * Get whether physics is enabled on this zone.
	 * @return whether physics is enabled on this zone
	 */
	public boolean getPhysicsEnabled(){
		return physics_enabled;
	}
	/**
	 * Set whether physics should be enabled on this zone.
	 * @param enabled whether physics should be enabled on this zone
	 */
	public void setPhysicsEnabled( boolean enabled){
		physics = enabled;
		physics_enabled = enabled;
	}

	/**
	 * Get all the touches currently assigned to this zone.
	 * @return A collection of touches containing all touches that are active on the zone
	 */
	public Collection<Touch> getTouchCollection(){
		return Collections.unmodifiableCollection( activeTouches.values());
	}

	/**
	 * Get all the touches currently assigned to this zone.
	 * @return An array of touches containing all touches that are active on the zone
	 */
	public Touch[] getTouches(){
		return activeTouches.values().toArray( new Touch[0]);
	}

	/**
	 * Get the IDs of all the touches currently assigned to this zone.
	 * @return A set of longs containing the long id's of the zone's touches
	 */
	public Set<Long> getIds(){
		return Collections.unmodifiableSet(activeTouches.keySet());
	}

	/**
	 * @return A Map&lt;Long, Touch&gt; which maps each touch id to the touch for the zones active touches
	 */
	public Map<Long, Touch> getTouchMap(){
		return Collections.unmodifiableMap(activeTouches);
	}

	/**
	 * Get the number of touches currently on the zone.
	 * @return The number of touches currently on the zone
	 */
	public int getNumTouches(){
		return activeTouches.size();
	}

	/**
	 * Whether this zone currently has any assigned touches
	 * @return Whether the zone has touches currently on it
	 */
	public boolean isActive(){
		return ! (
			activeTouches.isEmpty() && touchUpList.isEmpty() &&
			touchDownList.isEmpty() && touchMovedList.isEmpty() &&
			pressList.isEmpty());
	}

	/**
	 * Get whether the zone or one of its children has touches currently on it
	 * @return Whether the zone or one of its children has touches currently on it
	 */
	public boolean hasActiveChild(){
		for( Zone child : children)
			if( child.isActive())
				return true;
			else if( child.hasActiveChild())
				return true;
		return false;
	}

	/**
	 * Get the angle of the Zone in global coordinates
	 * @return The angle of the Zone in global coordinates
	 */
	public float getRotationAngle(){
		PMatrix3D g = getGlobalMatrix();
		float angle = PApplet.atan2(g.m10, g.m00);
		return angle >= 0 ? angle : angle + 2 * PI;
	}

	/**
	 * Get the x position of the touch in local coordinates
	 * @return the x position of the touch in local coordinates
	 */
	public float getLocalX(){
		return
			getParent() == null ?
				getOrigin().x :
				getParent().toZoneVector( getOrigin()).x;
	}

	/**
	 * Get the y position of zone in parent coordinates
	 * @return the y position of zone in parent coordinates
	 */
	public float getLocalY(){
		return
			getParent() == null ?
				getOrigin().y :
				getParent().toZoneVector(getOrigin()).y;
	}

	/**
	 * Get the x position of touch in local coordinates.
	 * @param touch the touch to get local coordinates for
	 * @return the x position of touch in local coordinates
	 */
	public float getLocalX( Touch touch){
		return toZoneVector( new PVector(
			touch.x, touch.y)).x;
	}

	/**
	 * Get the y position of the touch in local coordinates.
	 * @param touch the touch to get local coordinates for
	 * @return the y position of the touch in local coordinates
	 */
	public float getLocalY( Touch touch){
		return toZoneVector( new PVector(
			touch.x, touch.y)).y;
	}

	/**
	 * Sets the local x position
	 * @param x the desired value of the x coordinate
	 */
	public void setX(float x){
		if (getParent() == null)
			setLocation( x, getOrigin().y);
		else
			setLocation( x, getParent().toZoneVector(getOrigin()).y);
	}

	/**
	 * Sets the local y position
	 * @param y the desired value of the y coordinate
	 */
	public void setY(float y){
		if (getParent() == null)
			setLocation( getOrigin().x, y);
		else
			setLocation( getParent().toZoneVector(getOrigin()).x, y);
	}


	/**
	 * Check whether the given zone is a parent, grandparent, great-grandparent, etc. of this zone.
	 * @param zone the zone to check for ancestor status
	 * @return Whether the given zone is an ancestor of this one.
	 */
	public boolean isAncestor( Zone zone){
		for(
				Zone ancestor = zone.getParent();
				ancestor != null;
				ancestor = ancestor.getParent())
			if(ancestor == zone)
				return true;
		return false;
	}

	/**
	 * This cleans up (unassign touches, remove from picker, and remove zoneBody) the Zone and its children
	 */
	private void cleanUp(){
		for( Zone child : getChildren())
			child.cleanUp();

		SMT.picker.remove(this);

		// clear the touches from the Zone, so that it doesn't get pulled in by
		// the touches such as when putZoneOnTop() is called
		unassignAll();

		// destroy the Zones Body, so it does not collide with other Zones any
		// more
		if (zoneBody != null){
			SMT.world.destroyBody(zoneBody);
			zoneBody = null;
			zoneFixture = null;
			mJoint = null;
		}
	}

	/**
	 * This disconnects this zone from its children and vice versa
	 */
	public void clearChildren(){
		for( Zone zone : children){
			this.remove(zone);
		}
	}

	/**
	 * Get a child of this zone.
	 * @param index the index of the desired zone
	 * @return The child at the given index, or null if the index is invalid
	 */
	public Zone getChild( int index){
		try {
			return children.get(index);
		}
		catch (Exception e){
			return null;
		}
	}

	/**
	 * Get the number of children of this zone
	 * @return The number of children of this zone
	 */
	public int getChildCount(){
		return children.size();
	}

	/**
	 * Get all of this zone's children
	 * @return An array containing this zone's children
	 */
	public Zone[] getChildren(){
		return Collections.unmodifiableList(children).toArray(
			new Zone[ getChildCount()]);
	}

	/**
	 * Get this zone's parent.
	 * @return The zone that is the parent of this zone
	 */
	public Zone getParent(){
		return parent;
	}

	/**
	 * Reset the transformation matrix of the zone
	 */
	@Override
	public void resetMatrix(){
		matrix.reset();
		matrix.translate( x, y);
	}

	/**
	 * Changes the coordinates and size of the zone.
	 * 
	 * @param x int The zone's new x-coordinate.
	 * @param y int The zone's new y-coordinate.
	 * @param width int The zone's new width.
	 * @param height int The zone's new height.
	 */
	@Deprecated
	public void setData(int x, int y, int width, int height){
		this.setSize( width, height);
		this.setLocation( x, y);
	}

	/**
	 * Moves the zone to a given location with a reset matrix
	 * 
	 * @param x the desired x coordinate of this zone
	 * @param y the desired y coordinate of this zone
	 */
	public void setLocation( float x, float y){
		this.x = Math.round( x);
		this.y = Math.round( y);
		resetMatrix();
	}

	/**
	 * Set the size of this zone
	 * @param width the desired width of this zone
	 * @param height the desired height of this zone
	 */
	@Override
	public void setSize( int width, int height){
		super.setSize( width, height);
		this.width = width;
		this.height = height;
		this.dimension = new Dimension( width, height);
		this.halfDimension = new Dimension( width / 2, height / 2);
	}

	/**
	 * Set the width of this zone
	 * @param width the desired width of this zone
	 */
	public void setWidth( int width){
		this.setSize( width, dimension.height);
	}
	/**
	 * Set the height of this zone
	 * @param height the desired height of this zone
	 */
	public void setHeight( int height){
		this.setSize( dimension.width, height);
	}

	/**
	 * Get the zone x-coordinate. Upper left corner for rectangle.
	 * @return x int representing the upper left x-coordinate of the zone.
	 */
	public int getX(){
		return (int) this.getOrigin().x;
	}

	/**
	 * Get the zone y-coordinate. Upper left corner for rectangle.
	 * @return y int representing the upper left y-coordinate of the zone.
	 */
	public int getY(){
		return (int) this.getOrigin().y;
	}

	/**
	 * Get the zone's width.
	 * @return width int representing the width of the zone.
	 */
	public int getWidth(){
		return (int) PVector.sub(
			fromZoneVector(
				new PVector(this.width, 0)),
			this.getOrigin()).mag();
	}

	/**
	 * Get the zone's height.
	 * @return height int representing the height of the zone.
	 */
	public int getHeight(){
		return (int) PVector.sub(
			fromZoneVector(
				new PVector(0, this.height)),
			this.getOrigin()).mag();
	}


	/**
	 * Get the dimensions of this zone
	 * @return the dimensions of this zone
	 */
	public Dimension getSize(){
		return dimension;
	}
	/**
	 * Get the dimensions of this zone, as it appears on the screen
	 * @return the dimensions of this zone
	 */
	public Dimension getScreenSize(){
		PMatrix3D global = this.getGlobalMatrix();
		//origin vector
		PVector o0 = new PVector( 0, 0);
		//width vector
		PVector w0 = new PVector(
			dimension.width, 0);
		//height vector
		PVector h0 = new PVector(
			0, dimension.height);
		//apply matrix
		PVector o1 = global.mult( o0, null);
		PVector w1 = global.mult( w0, null);
		PVector h1 = global.mult( h0, null);
		//extract differences
		float width = o1.dist( w1);
		float height = o1.dist( h1);
		return new Dimension(
			Math.round( width),
			Math.round( height));
	}

	/**
	 * Set the half dimensions of this zone
	 * @return the half dimensions of this zone
	 */
	public Dimension getHalfSize(){
		return halfDimension;
	}

	/**
	 * @return The rotation radius of the zone, which is used by at least rnt() for now, controlling when rotation is done
	 */
	public float getRntRadius(){
		return rntRadius;
	}

	/**
	 * @param rntRadius The new rotation radius of the zone, which is used by at least rnt() for now, controlling when rotation is done
	 */
	public void setRntRadius(float rntRadius){
		this.rntRadius = rntRadius;
	}

	/**
	 * Tests to see if the x and y coordinates are in the zone.
	 * 
	 * @param x X-coordinate to test
	 * @param y Y-coordinate to test
	 * @return boolean True if x and y is in the zone, false otherwise.
	 */
	public boolean contains( float x, float y){
		PVector mouse = new PVector(x, y);
		PVector world = this.toZoneVector(mouse);
		return
			(world.x > 0) && (world.x < this.width) &&
			(world.y > 0) && (world.y < this.height);
	}

	/** 
	 * Draws the rotation radius of the zone
	 */
	public void drawRntCircle(){
		pushStyle();
		pushMatrix();
		noFill();
		strokeWeight(5);
		stroke( 255, 127, 39, 155);
		ellipseMode(RADIUS);
		ellipse(
			halfDimension.width, halfDimension.height,
			rntRadius, rntRadius);
		popMatrix();
		popStyle();
	}

	/**
	 * @return A PVector containing the centre point of the Zone
	 */
	public PVector getCentre(){
		return fromZoneVector( new PVector(
			halfDimension.width,
			halfDimension.height));
	}

	/**
	 * @return A PVector containing the origin of the Zone. The origin is defined to be at the top-left corner of the Zone
	 */
	public PVector getOrigin(){
		return fromZoneVector( new PVector(0, 0));
	}

	protected TuioTime maxTime(Iterable<Touch> touches){
		ArrayList<TuioTime> times = new ArrayList<TuioTime>();
		for (Touch t : touches){
			if (t != null){
				times.add(t.currentTime);
			}
		}
		return Collections.max(times, SMTUtilities.tuioTimeComparator);
	}

	protected TuioTime maxTime(TouchPair... pairs){
		ArrayList<Touch> touches = new ArrayList<Touch>(pairs.length * 2);
		for (TouchPair pair : pairs){
			touches.add(pair.from);
			touches.add(pair.to);
		}
		return maxTime(touches);
	}

	public static TuioTime maxTuioTime( TuioTime... times){
		return times.length != 0 ?
			Collections.max(
				Arrays.asList( times),
				SMTUtilities.tuioTimeComparator) :
			null;
	}

	private float clampScale( float scale){
		float width = this.getWidth();
		float height = this.getHeight();
		return scale;
	}

	/**
	 * Gets a touch on the zone by index, 0 is first touch, 1 is second, etc
	 * 
	 * @param n Which touch to get
	 * @return The Nth touch on the zone, or null if there is no such touch
	 */
	public Touch getActiveTouch(int n){
		int i = 0;
		for (Touch t : activeTouches.values()){
			if (i == n){
				return t;
			}
			i++;
		}
		return null;
	}

	/**
	 * This takes the touches currently assigned to the Zone and converts them
	 * into TouchPairs which are pairs of Touches of the current and previous
	 * state of each Touch
	 * 
	 * @return A List of TouchPairs, one TouchPair per Touch assigned to the
	 *         Zone, each TouchPair is the Touch at its current state, and its
	 *         previous state
	 */
	protected List<TouchPair> getTouchPairs(){
		return getTouchPairs(activeTouches.size());
	}

	/**
	 * This takes the touches currently assigned to the Zone and converts them
	 * into TouchPairs which are pairs of Touches of the current and previous
	 * state of each Touch
	 * 
	 * @param size The number of Touches to put into Touchpairs, if greater than
	 * the number of assigned Touches on the Zone, it is filled with empty
	 * TouchPairs (the Touches in each TouchPair are null)
	 * @return A List of TouchPairs, one TouchPair per Touch assigned to the
	 * Zone, each TouchPair is the Touch at its current state, and its
	 * previous state
	 */
	protected List<TouchPair> getTouchPairs(int size){
		ArrayList<TouchPair> pairs = new ArrayList<TouchPair>(size);
		for (int i = 0; i < size; i++){
			Touch touch = getActiveTouch(i);
			if (touch == null){
				pairs.add(new TouchPair());
			}
			else {
				pairs.add(
					new TouchPair(
						SMTUtilities.getLastTouchAtTime(
							touch.getTuioCursor(), lastUpdate), touch));
			}
		}
		return pairs;
	}

	/**
	 * Clones the zone and all child zones
	 * @return a clone of this zone and its children
	 */
	@Override
	public Zone clone(){
		return clone( Integer.MAX_VALUE, null);
	}

	/**
	 * Clones the zone and optionally any child zones up to the specified
	 * generation of children
	 * 
	 * @param maxChildGenerations the upper limit on how many generations
	 *  of children to clone (0 - None, 1 - First Generation children, 
	 *  ... Integer.MAX_VALUE - All generations of children)
	 * @return a clone of this zone and its children
	 */
	public Zone clone( int maxChildGenerations){
		return clone( maxChildGenerations, null);
	}

	/**
	 * Clones the zone and optionally any child zones up to the specified
	 * generation of children
	 * 
	 * @param maxChildGenerations the upper limit on how many generations
	 *  of children to clone (0 - None, 1 - First Generation children, 
	 *  ... Integer.MAX_VALUE - All generations of children)
	 * 
	 * @param enclosingClass  The enclosingClass of the Zone (needed when
	 *  cloning a Zone that is an inner class and refereneces its data,
	 *  otherwise passing null is fine)
	 * @return a clone of this zone and its children
	 */
	public Zone clone(int maxChildGenerations, Object enclosingClass){
		Zone clone;
		try {
			// if inner class, call its constructor properly by passing its
			// enclosing class too
			if (this.getClass().getEnclosingClass() != null
					&& this.getClass().getEnclosingClass() == enclosingClass.getClass()){
				// clone a Zone using a copy constructor
				clone = this.getClass()
						.getConstructor(this.getClass().getEnclosingClass(), this.getClass())
						.newInstance(enclosingClass, this);
			}
			else {
				// clone a Zone using a copy constructor
				clone = this.getClass().getConstructor(this.getClass()).newInstance(this);
			}
		}
		catch (Exception e){
			// if no copy constructor, use the old way of making a normal Zone
			clone = new Zone(this.getName(), this.x, this.y, this.width, this.height);
		}

		// use the backupMatrix if the zone being clone has its matrix modified
		// by parent, and so has backupMatrix set
		if (this.backupMatrix != null){
			clone.matrix = this.backupMatrix.get();
		}
		else {
			clone.matrix = this.matrix.get();
		}
		clone.inverse = this.inverse.get();

		if (maxChildGenerations > 0){
			for (Zone child : this.getChildren()){
				clone.add(child.clone(maxChildGenerations - 1, clone));
			}
		}
		return clone;
	}

	/**
	 * This makes a PVector into one relative to the zone's matrix
	 * 
	 * @param global The PVector to put into relative coordinate space
	 * @return A PVector relative to the zone's coordinate space
	 */
	public PVector toZoneVector(PVector global){
		PMatrix3D temp = getGlobalMatrix();
		temp.invert();
		return temp.mult(global, null);
	}

	/**
	 * This makes a PVector into a global coordinates from the given local
	 * zone's coordinate space
	 * 
	 * @param local The PVector to put into global coordinate space
	 * @return A PVector relative to the global coordinate space
	 */
	public PVector fromZoneVector( PVector local){
		return getGlobalMatrix().mult(local, null);
	}

	/**
	 * Returns a matrix relative to global coordinate space
	 * 
	 * @return A PMatrix3D relative to the global coordinate space
	 */
	public PMatrix3D getGlobalMatrix(){
		PMatrix3D temp = new PMatrix3D();
		// list ancestors in order from most distant to closest, in order to
		// apply their matrix's in order
		LinkedList<Zone> ancestors = new LinkedList<Zone>();
		Zone zone = this;
		while (zone.getParent() != null){
			zone = zone.getParent();
			ancestors.addFirst(zone);
		}
		// apply ancestors matrix's in proper order to make sure image is
		// correctly oriented, but not when backupMatrix is set, as then it
		// already has its parents applied to it.
		if (backupMatrix == null){
			for (Zone i : ancestors){
				temp.apply(i.matrix);
			}
		}
		temp.apply(matrix);

		return temp;
	}

	/**
	 * Changes the ordering of the child zones, placing the given one on top
	 * 
	 * @param zone The child to place on top of the others
	 */
	public void putChildOnTop( Zone zone){
		// only remove and add if actually in the children arraylist and not
		// already the last(top) already
		if ( this.children.contains(zone) &&
				(children.indexOf(zone) < children.size() - 1)){
			this.children.remove(zone);
			this.children.add(zone);
		}
	}

	// changing the return of this method to true will cause indirect zones only redraw if setModified(true) is called on it.
	protected boolean updateOnlyWhenModified(){ return false;}

	/**
	 * Set a limit for all scaling built-in gestures
	 *
	 * @param maxW the maximum width to scale to
	 * @param maxH the maximum height to scale to
	 * @param minW the minimum width to scale to
	 * @param minH the minimum height to scale to
	 *
	 * @deprecated this feature is currently broken with no immediate plans for repair
	 */
	@Deprecated
	public void enableScalingLimit( int maxW, int maxH, int minW, int minH){
		this.scalingLimit = true;
		this.maxWidth = maxW;
		this.maxHeight = maxH;
		this.minWidth = minW;
		this.minHeight = minH;
	}

	/**
	 * @deprecated this feature is currently broken with no immediate plans for repair
	 */
	@Deprecated
	public void disableScalingLimit(){
		this.scalingLimit = false;
	}

	boolean warnDraw(){
		return true;
	}
	boolean warnTouch(){
		return true;
	}
	boolean warnKeys(){
		return false;
	}
	boolean warnPick(){
		return false;
	}
	boolean warnTouchUDM(){
		return false;
	}
	boolean warnPress(){
		return false;
	}

	public void setBodyFromMatrix(){
		// get origin position
		PVector o = fromZoneVector(new PVector(width / 2, height / 2));
		// height-y to account for difference in coordinates
		zoneBody.setTransform(new Vec2(o.x * SMT.box2dScale, (applet.height - o.y)
				* SMT.box2dScale), getRotationAngle());
	}

	public void setMatrixFromBody(){
		// set global matrix from zoneBody, then get local matrix from global matrix
		PMatrix3D ng = new PMatrix3D();
		// height-y to account for difference in coordinates
		ng.translate(
			zoneBody.getPosition().x / SMT.box2dScale,
			( applet.height - zoneBody.getPosition().y / SMT.box2dScale));
		ng.rotate(zoneBody.getAngle());
		ng.translate( - width / 2, - height / 2);
		// ng=PM == (P-1)*ng=M
		PMatrix3D M = new PMatrix3D( matrix);
		M.invert();
		PMatrix3D P = getGlobalMatrix();
		P.apply( M);
		P.invert();
		ng.apply( P);
		matrix.set(ng);
	}

	public void addPhysicsMouseJoint(){
		if( zoneBody != null && mJoint == null && physics){
			mJointDef = new MouseJointDef();
			mJointDef.maxForce = 1000000.0f;
			mJointDef.frequencyHz = applet.frameRate;
			mJointDef.bodyA = SMT.groundBody;
			mJointDef.bodyB = zoneBody;
			mJointDef.target.set(
				new Vec2(
					zoneBody.getPosition().x,
					zoneBody.getPosition().y));
			mJoint = (MouseJoint) SMT.world.createJoint( mJointDef);
			zoneBody.setAwake(true);
		}
	}


	/**
	 * Make sure that if a hidden sub-Zone is given a name that the outer-Zone
	 * overrides this method to pass through the set to those Zones too,
	 * otherwise the methods on these sub-Zones will never be called
	 * @param obj The object to bind to the Zone
	 * @deprecated Do not use this method - See
	 * <a href="https://github.com/vialab/SMT/issues/174">this github issue</a>
	 */
	@Deprecated
	public void setBoundObject(Object obj){
		this.boundObject = obj;
	}

	/**
	 * Gets the object this zone is currently bound to.
	 * @return the object this zone is currently bound to
	 * @deprecated Do not use this method - See <a href="https://github.com/vialab/SMT/issues/174">this github issue</a>
	 */
	@Deprecated
	public Object getBoundObject(){
		return boundObject;
	}

	///////////////////
	// Event methods //
	///////////////////

	/**
	 * Converts java key pressed events into their processing counterparts,
	 * then calls them on this zone.
	 * @param event the key pressed event to be converted
	 */
	@Override
	public void keyPressed( java.awt.event.KeyEvent event){
		this.invokeKeyPressedMethod(
			new KeyEvent(
				event,
				event.getWhen(),
				KeyEvent.PRESS,
				event.getModifiers(),
				event.getKeyChar(),
				event.getKeyCode()));
	}

	/**
	 * Converts java key released events into their processing counterparts,
	 * then calls them on this zone.
	 * @param event the key released event to be converted
	 */
	@Override
	public void keyReleased( java.awt.event.KeyEvent event){
		this.invokeKeyReleasedMethod(
			new KeyEvent(
				event,
				event.getWhen(),
				KeyEvent.RELEASE,
				event.getModifiers(),
				event.getKeyChar(),
				event.getKeyCode()));
	}

	/**
	 * Converts java key typed events into their processing counterparts,
	 * then calls them on this zone.
	 * @param event the key typed event to be converted
	 */
	@Override
	public void keyTyped( java.awt.event.KeyEvent event){
		this.invokeKeyTypedMethod(
			new KeyEvent(
				event,
				event.getWhen(),
				KeyEvent.TYPE,
				event.getModifiers(),
				event.getKeyChar(),
				event.getKeyCode()));
	}

	//////////////////////////////
	// Color protection methods //
	//////////////////////////////

	//background functions
	@Override
	public void background(float arg0, float arg1, float arg2, float arg3){
		if( picking_on) return;
		if( direct){
			pushStyle();
			noStroke();
			fill(arg0, arg1, arg2, arg3);
			rect(0,0,width,height);
			popStyle();
		}
		else super.background(arg0, arg1, arg2, arg3);
	}

	@Override
	public void background(float arg0, float arg1, float arg2){
		if( picking_on) return;
		if( direct){
			pushStyle();
			noStroke();
			fill(arg0, arg1, arg2);
			rect(0,0,width,height);
			popStyle();
		}
		else super.background(arg0, arg1, arg2);
	}

	@Override
	public void background(float arg0, float arg1){
		if( picking_on) return;
		if( direct){
			pushStyle();
			noStroke();
			fill(arg0, arg1);
			rect(0,0,width,height);
			popStyle();
		}
		else super.background(arg0, arg1);
	}

	@Override
	public void background(float arg0){
		if( picking_on) return;
		if( direct){
			pushStyle();
			noStroke();
			fill(arg0);
			rect(0,0,width,height);
			popStyle();
		}
		else super.background(arg0);
	}

	@Override
	public void background(int arg0, float arg1){
		if( picking_on) return;
		if( direct){
			pushStyle();
			noStroke();
			fill(arg0, arg1);
			rect(0,0,width,height);
			popStyle();
		}
		else super.background(arg0, arg1);
	}

	@Override
	public void background(int arg0){
		if( picking_on) return;
		if( direct){
			pushStyle();
			noStroke();
			fill(arg0);
			rect(0,0,width,height);
			popStyle();
		}
		else super.background(arg0);
	}

	@Override
	public void background(PImage arg0){
		if( picking_on) return;
		if( direct){
			pushStyle();
			noStroke();
			image(arg0,0,0,width,height);
			popStyle();
		}
		else super.background(arg0);
	}

	//color functions
	@Override
	public void fill(float arg0, float arg1, float arg2, float arg3){
		if( ! picking_on)
			super.fill(arg0, arg1, arg2, arg3);
	}

	@Override
	public void fill(float arg0, float arg1, float arg2){
		if( ! picking_on)
			super.fill(arg0, arg1, arg2);
	}

	@Override
	public void fill(float arg0, float arg1){
		if( ! picking_on)
			super.fill(arg0, arg1);
	}

	@Override
	public void fill(float arg0){
		if( ! picking_on)
			super.fill(arg0);
	}

	@Override
	public void fill(int arg0, float arg1){
		if( ! picking_on)
			super.fill(arg0, arg1);
	}

	@Override
	public void fill(int arg0){
		if( ! picking_on)
			super.fill(arg0);
	}

	@Override
	public void stroke(float arg0, float arg1, float arg2, float arg3){
		if( ! picking_on)
			super.stroke(arg0, arg1, arg2, arg3);
	}

	@Override
	public void stroke(float arg0, float arg1, float arg2){
		if( ! picking_on)
			super.stroke(arg0, arg1, arg2);
	}

	@Override
	public void stroke(float arg0, float arg1){
		if( ! picking_on)
			super.stroke(arg0, arg1);
	}

	@Override
	public void stroke(float arg0){
		if( ! picking_on)
			super.stroke(arg0);
	}

	@Override
	public void stroke(int arg0, float arg1){
		if( ! picking_on)
			super.stroke(arg0, arg1);
	}

	@Override
	public void stroke(int arg0){
		if( ! picking_on)
			super.stroke(arg0);
	}

	@Override
	public void tint(float arg0, float arg1, float arg2, float arg3){
		if( ! picking_on)
			super.tint(arg0, arg1, arg2, arg3);
	}

	@Override
	public void tint(float arg0, float arg1, float arg2){
		if( ! picking_on)
			super.tint(arg0, arg1, arg2);
	}

	@Override
	public void tint(float arg0, float arg1){
		if( ! picking_on)
			super.tint(arg0, arg1);
	}

	@Override
	public void tint(float arg0){
		if( ! picking_on)
			super.tint(arg0);
	}

	@Override
	public void tint(int arg0, float arg1){
		if( ! picking_on)
			super.tint(arg0, arg1);
	}

	@Override
	public void tint(int arg0){
		if( ! picking_on)
			super.tint(arg0);
	}

	//tranformation overrides
	//when we're not drawing or not touching, we want to send tranformations to our transformation matrix instead.
	@Override
	public void rotate( float angle){
		if( drawing_on || picking_on || touching_on)
			super.rotate( angle);
		else
			matrix.rotate( angle);
	}
	@Override
	public void rotate( float angle, float x, float y, float z){
		if( drawing_on || picking_on || touching_on)
			super.rotate( angle, x, y, z);
		else
			matrix.rotate( angle, x, y, z);
	}
	@Override
	public void rotateX( float angle){
		if( drawing_on || picking_on || touching_on)
			super.rotateX( angle);
		else
			matrix.rotateX( angle);
	}
	@Override
	public void rotateY( float angle){
		if( drawing_on || picking_on || touching_on)
			super.rotateY( angle);
		else
			matrix.rotateY( angle);
	}
	@Override
	public void rotateZ( float angle){
		if( drawing_on || picking_on || touching_on)
			super.rotateZ( angle);
		else
			matrix.rotateZ( angle);
	}
	@Override
	public void scale( float s){
		if( drawing_on || picking_on || touching_on)
			super.scale( s, s);
		else
			matrix.scale( s, s);
	}
	@Override
	public void scale( float x, float y){
		if( drawing_on || picking_on || touching_on)
			super.scale( x, y);
		else
			matrix.scale( x, y);
	}
	@Override
	public void scale( float x, float y, float z){
		if( drawing_on || picking_on || touching_on)
			super.scale( x, y, z);
		else
			matrix.scale( x, y, z);
	}
	@Override
	public void translate( float x, float y){
		if( drawing_on || picking_on || touching_on)
			super.translate( x, y);
		else
			matrix.translate( x, y);
	}
	@Override
	public void translate( float x, float y, float z){
		if( drawing_on || picking_on || touching_on)
			super.translate( x, y, z);
		else
			matrix.translate( x, y, z);
	}
	@Override
	public void setMatrix( PMatrix source){
		if( drawing_on || picking_on || touching_on)
			super.setMatrix( source);
		else
			matrix.set( source);
	}
	@Override
	public void setMatrix( PMatrix2D source){
		if( drawing_on || picking_on || touching_on)
			super.setMatrix( source);
		else
			matrix.set( source);
	}
	@Override
	public void setMatrix( PMatrix3D source){
		if( drawing_on || picking_on || touching_on)
			super.setMatrix( source);
		else
			matrix.set( source);
	}
	@Override
	public void applyMatrix(
			float n00, float n01, float n02, float n10, float n11, float n12){
		if( drawing_on || picking_on || touching_on)
			super.applyMatrix( n00, n01, n02, n10, n11, n12);
		else
			matrix.apply( n00, n01, n02, n10, n11, n12);
	}
	@Override
	public void applyMatrix(
			float n00, float n01, float n02, float n03,
			float n10, float n11, float n12, float n13,
			float n20, float n21, float n22, float n23,
			float n30, float n31, float n32, float n33){
		if( drawing_on || picking_on || touching_on)
			super.applyMatrix(
				n00, n01, n02, n03, n10, n11, n12, n13,
				n20, n21, n22, n23, n30, n31, n32, n33);
		else
			matrix.apply(
				n00, n01, n02, n03, n10, n11, n12, n13,
				n20, n21, n22, n23, n30, n31, n32, n33);
	}
	@Override
	public void applyMatrix( PMatrix source){
		if( drawing_on || picking_on || touching_on)
			super.applyMatrix( source);
		else
			matrix.apply( source);
	}
	@Override
	public void applyMatrix( PMatrix2D source){
		if( drawing_on || picking_on || touching_on)
			super.applyMatrix( source);
		else
			matrix.apply( source);
	}
	@Override
	public void applyMatrix( PMatrix3D source){
		if( drawing_on || picking_on || touching_on)
			super.applyMatrix( source);
		else
			matrix.apply( source);
	}
	@Override
	public void shearX( float angle){
		if( drawing_on || picking_on || touching_on)
			super.shearX( angle);
		else
			matrix.shearX( angle);
	}
	@Override
	public void shearY( float angle){
		if( drawing_on || picking_on || touching_on)
			super.shearY( angle);
		else
			matrix.shearY( angle);
	}
}
