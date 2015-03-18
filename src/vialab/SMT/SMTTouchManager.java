package vialab.SMT;

import java.lang.reflect.Method;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PGraphics;

//local imports
import vialab.SMT.util.*;

class SMTTouchManager {
	private PApplet applet;
	private SMTTuioListener tuioListener;
	private ZonePicker picker;
	private Method touchDown, touchMoved, touchUp;
	static TouchState currentTouchState = new TouchState();
	static TouchState previousTouchState;
	static HashMap<Touch, Zone> touchPrevZone = new HashMap<Touch, Zone>();

	public SMTTouchManager( SMTTuioListener tuioListener, ZonePicker picker){
		this.tuioListener = tuioListener;
		this.picker = picker;
		this.applet = SMT.applet;
		retrieveMethods( SMT.applet);
	}

	/**
	 * Determines to which objects touch events should be sent, and then sends them.
	 */
	public void handleTouches(){
		//render pick buffer
		picker.render();

		//update touch state
		previousTouchState = new TouchState( currentTouchState);
		currentTouchState.update( tuioListener.getCurrentTuioState());

		//forward events, each touch should go through one of these three methods, and they are mutually exclusive
		handleTouchesDown();
		handleTouchesUp();
		handleTouchesMoved();
	}

	/**
	 * Handles every touch in the current but not the previous state.
	 */
	protected void handleTouchesDown(){
		for ( Touch touch : currentTouchState)
			// touchDowns only happen on new touches
			if ( ! previousTouchState.contains( touch.sessionID)){
				SMTUtilities.invoke( touchDown, applet, null, touch);
				Zone zone = pick( touch);
				touchPrevZone.put( touch, zone);
				doTouchDown( zone, touch);
				touch.invokeTouchDownEvent();
			}
	}

	/**
	 * Handles every touch in the previous but not in the current state.
	 */
	protected void handleTouchesUp(){
		for ( Touch touch : previousTouchState)
			if ( ! currentTouchState.contains( touch.sessionID)){
				// the touch existed, but no longer exists, so it went up
				SMTUtilities.invoke( touchUp, applet, null, touch);
				for ( Zone zone : touch.getAssignedZones()){
					doTouchUp( zone, touch);
					if ( touchPrevZone.get( touch) == zone)
						doPress( zone, touch);
				}
				touchPrevZone.remove( touch);
				touch.invokeTouchUpEvent();
			}
	}

	/**
	 * Handles the movement of touches. A touch is "moved" if it is in both the
	 * previous and current state
	 */
	protected void handleTouchesMoved(){
		for ( Touch touch : currentTouchState)
			if ( previousTouchState.contains( touch.sessionID)){
				SMTUtilities.invoke( touchMoved, applet, null, touch);
				Zone zone = null;
				if( ! touch.isAssigned()){
					zone = pick( touch);
					// Assign the touch to the picked Zone, as long as the touch is not grabbed
					if ( zone != null)
						zone.assign( touch);
				}
				else {
					boolean first = true;
					for( Zone assigned_zone : touch.getAssignedZones())
						if( ! assigned_zone.getCaptureTouches()){
							//if the zone defines a press method, make sure to unassign when we no longer pick to the Zone, meaning that the touchUp can rely on the previous pick of the Touch to determine if the zone was pressed
							if ( first){
								zone = pick( touch);
								first = false;
							}
							if ( zone != assigned_zone){
								assigned_zone.unassign( touch);
								// Assign the touch to the picked Zone
								if ( zone != null)
									zone.assign( touch);
							}
						}
				}
				for( Zone assigned_zone : touch.getAssignedZones())
					doTouchMoved( assigned_zone, touch);
				touchPrevZone.put( touch, zone);
				touch.invokeTouchMovedEvent();
			}
	}

	private void doPress( Zone zone, Touch touch){
		if ( zone != null)
			zone.pressRegister( touch);
	}

	/**
	 * Called when a touch went down, or when an orphaned touch moves around.
	 * @param zone may be null
	 */
	private void doTouchDown( Zone zone, Touch touchPoint){
		if ( zone != null){
			zone.assign( touchPoint);
			zone.touchDownRegister( touchPoint);
		}
	}

	/**
	 * Called when a touch went up.
	 * @param zone may be null
	 */
	private void doTouchUp( Zone zone, Touch touch){
		if ( zone != null){
			zone.unassign( touch);
			zone.touchUpRegister( touch);
		}
	}

	private void doTouchMoved( Zone zone, Touch touch){
		if ( zone != null)
			zone.touchMovedRegister( touch);
	}

	private Zone pick( Touch touch){
		return picker.pick( touch.x, touch.y);
	}

	private void retrieveMethods( PApplet applet){
		touchDown = SMTUtilities.getAnyPMethod(
			applet, "touch", "Down", true, Touch.class);
		touchMoved = SMTUtilities.getAnyPMethod(
			applet, "touch", "Moved", true, Touch.class);
		touchUp = SMTUtilities.getAnyPMethod(
			applet, "touch", "Up", true, Touch.class);
		SMTUtilities.methodSet.add( "touchDown");
		SMTUtilities.methodSet.add( "touchMoved");
		SMTUtilities.methodSet.add( "touchUp");
	}
}
