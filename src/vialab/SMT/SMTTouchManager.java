package vialab.SMT;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import processing.core.PApplet;

public class SMTTouchManager {
	private PApplet applet;

	private SMTTuioListener touchListener;

	private SMTZonePicker picker;

	/**
	 * For each touch ID, gives the object that that touch belongs to. This
	 * object may be null.
	 */
	public Map<Touch, Zone> TouchToZone = Collections.synchronizedMap(new HashMap<Touch, Zone>());

	/**
	 * The number of touches for each object. The numbers in this map are at
	 * least 1.
	 */
	private Map<Zone, Integer> touchesPerObject = Collections
			.synchronizedMap(new HashMap<Zone, Integer>());

	/**
	 * The set of currently active objects.
	 */
	private Set<Zone> activeObjects = Collections.synchronizedSet(new HashSet<Zone>());

	private Method touchDown, touchMoved, touchUp;

	// private Method addTouch, removeTouch, updateTouch;

	// private Method addObject, removeObject, updateObject;

	// private Method refresh;

	static TouchState currentTouchState = new TouchState();

	public SMTTouchManager(SMTTuioListener touchListener, SMTZonePicker picker) {
		this.touchListener = touchListener;
		this.picker = picker;
		this.applet = TouchClient.parent;

		retrieveMethods(TouchClient.parent);
	}

	/**
	 * Determines to which objects touch events should be sent, and then sends
	 * them.
	 */
	public void handleTouches() {
		// while (touchListener.hasMoreTouchStates()) {
		picker.renderPickBuffer();

		// fetch the previous and current state of all touches
		// (note that the order of fetching these matters!)
		// TouchState previousTouchState = touchListener.getCurrentTouchState();
		// TouchState currentTouchState = touchListener.dequeueTouchState();

		currentTouchState.update(touchListener.getCurrentTuioState());

		// forward events
		handleTouchesDown();
		handleTouchesUp();
		handleTouchesMoved();
		// }
	}

	public void reset() {
		this.activeObjects.clear();
		this.TouchToZone.clear();
		this.touchesPerObject.clear();
	}

	private void activate(Zone zone, Touch touch) {
		activeObjects.add(zone);
		// zone.assign(touch);
		// zone.setActive(true);
	}

	private void deactivate(Zone zone, Long id) {
		zone.unassign(id);
		if (!zone.isActive()) {
			activeObjects.remove(zone);
		}
	}

	private void deactivate(Zone zone) {
		activeObjects.remove(zone);
		zone.unassignAll();
		// zone.setActive(false);
	}

	private void resetActive() {
		// don't want ConcurrentModificationException to bite us
		ArrayList<Zone> candidates = new ArrayList<Zone>();
		for (Zone zone : activeObjects) {
			if (!touchesPerObject.containsKey(zone)) {
				candidates.add(zone);
			}
		}
		for (Zone zone : candidates) {
			deactivate(zone);
		}
	}

	public int getTouchesPerObject(Zone zone) {
		if (touchesPerObject.containsKey(zone)) {
			return touchesPerObject.get(zone);
		}
		else {
			return 0;
		}
	}

	/**
	 * Handles every touch in the current but not the previous state.
	 */
	protected void handleTouchesDown() {
		SMTUtilities.invoke(touchDown, applet);
		for (Touch touchPoint : currentTouchState) {
			// now either their is no zone it is mapped to or the zone mapped to
			// doesn't have it active, so new touchDown to find where to assign
			// it
			if (TouchToZone.get(touchPoint) == null
					|| !TouchToZone.get(touchPoint).isAssigned(touchPoint.getSessionID())) {
				// it's a new touch that just went down,
				// or an old touch that just crossed an object
				// or an old touch that was unassigned from an object
				Zone picked = picker.pick(touchPoint);
				// don't fire touchDown when re-picking to same zone (both null is
				// also caught by this), to avoid the auto-unassignAll() which
				// allows picking to different zones with one touch by default,
				// from causing touchDown being called every frame when the
				// touch is down
				if (picked != TouchToZone.get(touchPoint)) {
					doTouchDown(picked, touchPoint);
				}
			}
		}
	}

	/**
	 * Handles every touch in the previous but not in the current state.
	 */
	protected void handleTouchesUp() {
		SMTUtilities.invoke(touchUp, applet);
		ArrayList<Touch> TouchToRemove = new ArrayList<Touch>();
		for (Touch t : TouchToZone.keySet()) {
			if (!currentTouchState.contains(t.sessionID)) {
				// the touch existed, but no longer exists, so it went up
				Zone zone = TouchToZone.get(t);
				TouchToRemove.add(t);
				doTouchUp(zone, t);
			}
		}

		for (Touch t : TouchToRemove) {
			TouchToZone.remove(t);
		}
	}

	/**
	 * Handles the movement of touches.
	 */
	protected void handleTouchesMoved() {
		SMTUtilities.invoke(touchMoved, applet);

		// get the set of all objects currently touched
		Set<Zone> touchables = new HashSet<Zone>(TouchToZone.values());

		// send each of these objects a touch moved event
		for (Zone zone : touchables) {
			if (zone != null) {
				doTouchesMoved(zone, currentTouchState);
			}
		}
	}

	/**
	 * Called when a touch went down, or when an orphaned touch moves around.
	 * 
	 * @param zone
	 *            may be null
	 */
	private void doTouchDown(Zone zone, Touch touchPoint) {
		TouchToZone.put(touchPoint, zone);

		if (zone != null) {
			assignTouch(zone, touchPoint);
			zone.touchDownInvoker(touchPoint);
		}

		// upon a touch-down anywhere, we set all objects that don't currently
		// have touches to inactive
		resetActive();
	}

	public void assignTouch(Zone zone, Touch touch) {
		TouchToZone.put(touch, zone);
		if (!touchesPerObject.containsKey(zone)) {
			touchesPerObject.put(zone, 0);
		}
		touchesPerObject.put(zone, touchesPerObject.get(zone) + 1);

		activate(zone, touch);
		zone.touchDown(touch);
	}

	/**
	 * Called when a touch went up.
	 * 
	 * @param zone
	 *            may be null
	 */
	private void doTouchUp(Zone zone, Touch touch) {
		if (zone != null) {
			zone.touchUp(touch);
			deactivate(zone, touch.sessionID);

			touchesPerObject.put(zone, touchesPerObject.get(zone) - 1);
			if (touchesPerObject.get(zone) == 0) {
				touchesPerObject.remove(zone);
			}

			zone.touchUpInvoker(touch);
		}
	}

	private void doTouchesMoved(Zone zone, TouchState currentTouchState) {
		List<Touch> currentLocalState = localTouchState(currentTouchState, zone);
		zone.touchesMoved(currentLocalState);
		for (Touch t : currentLocalState){
			zone.touchMovedInvoker(t);
		}
	}

	/**
	 * Returns a new {@link TouchState} that contains only the touches relevant
	 * to the given object, with their IDs remapped to 0, 1, 2 etc.
	 * 
	 * @param zone
	 *            must not be null
	 */
	private List<Touch> localTouchState(TouchState touchState, Zone zone) {
		List<Touch> localState = new ArrayList<Touch>(touchState.size());
		for (Touch touchPoint : touchState) {
			if (TouchToZone.get(touchPoint) == zone) {
				localState.add(touchPoint);
			}
		}
		return localState;
	}

	private void retrieveMethods(PApplet parent) {
		// addTouch = SMTUtilities.getPMethod(parent, "addTouch", new Class[] {
		// Touch.class });
		// removeTouch = SMTUtilities.getPMethod(parent, "removeTouch", new
		// Class[] { Touch.class });
		// updateTouch = SMTUtilities.getPMethod(parent, "updateTouch", new
		// Class[] { Touch.class });

		/*
		 * addObject = SMTUtilities.getPMethod(parent, "addObject", new Class[]
		 * { TuioObject.class }); removeObject = SMTUtilities.getPMethod(parent,
		 * "removeObject", new Class[] { TuioObject.class }); updateObject =
		 * SMTUtilities.getPMethod(parent, "updateObject", new Class[] {
		 * TuioObject.class });
		 * 
		 * refresh = SMTUtilities.getPMethod(parent, "refresh", new Class[] {
		 * TuioTime.class });
		 */

		touchDown = SMTUtilities.getPMethod(parent, "touchDown");
		touchMoved = SMTUtilities.getPMethod(parent, "touchMoved");
		touchUp = SMTUtilities.getPMethod(parent, "touchUp");
	}
}
