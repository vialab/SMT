package vialab.SMT;

import java.lang.reflect.Method;
import java.util.HashMap;
import processing.core.PApplet;

class SMTTouchManager {
	private PApplet applet;

	private SMTTuioListener tuioListener;

	private SMTZonePicker picker;

	private Method touchDown, touchMoved, touchUp;

	static TouchState currentTouchState = new TouchState();

	static TouchState previousTouchState;
	
	static HashMap<Touch,Zone> touchPrevZone = new HashMap<Touch, Zone>();

	public SMTTouchManager(SMTTuioListener tuioListener, SMTZonePicker picker) {
		this.tuioListener = tuioListener;
		this.picker = picker;
		this.applet = TouchClient.parent;

		retrieveMethods(TouchClient.parent);
	}

	/**
	 * Determines to which objects touch events should be sent, and then sends
	 * them.
	 */
	public void handleTouches() {
		picker.renderPickBuffer();

		previousTouchState = new TouchState(currentTouchState);

		currentTouchState.update(tuioListener.getCurrentTuioState());

		// forward events, each touch should go through one of these three methods, and they are mutually exclusive
		handleTouchesDown();
		handleTouchesUp();
		handleTouchesMoved();
	}

	/**
	 * Handles every touch in the current but not the previous state.
	 */
	protected void handleTouchesDown() {
		for (Touch t : currentTouchState) {
			// touchDowns only happen on new touches
			if (!previousTouchState.contains(t.sessionID)) {
				SMTUtilities.invoke(touchDown, applet);
				Zone z = picker.pick(t);
				touchPrevZone.put(t, z);
				doTouchDown(z, t);
			}
		}
	}

	/**
	 * Handles every touch in the previous but not in the current state.
	 */
	protected void handleTouchesUp() {
		for (Touch t : previousTouchState) {
			if (!currentTouchState.contains(t.sessionID)) {
				// the touch existed, but no longer exists, so it went up
				SMTUtilities.invoke(touchUp, applet);
				touchPrevZone.remove(t);
				for (Zone zone : t.getAssignedZones()) {
					doTouchUp(zone, t);
				}
			}
		}
	}

	/**
	 * Handles the movement of touches. A touch is "moved" if it is in both the
	 * previous and current state
	 */
	protected void handleTouchesMoved() {
		for (Touch t : currentTouchState) {
			if (previousTouchState.contains(t.sessionID)) {
				SMTUtilities.invoke(touchMoved, applet);
				Zone z = picker.pick(t);
				if (!t.isAssigned()) {
					// Assign the touch to the picked Zone, as long as the touch
					// is not grabbed
					if (z != null) {
						z.assign(t);
					}
				}
				for (Zone zone : t.getAssignedZones()) {
					doTouchMoved(zone, t);
				}
				touchPrevZone.put(t, z);
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
		if (zone != null) {
			zone.touchDown(touchPoint);
			zone.touchDownInvoker(touchPoint);
		}
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
			zone.touchUpInvoker(touch);
		}
	}

	private void doTouchMoved(Zone zone, Touch touch) {
		if (zone != null) {
			zone.touchMovedInvoker(touch);
		}
	}

	private void retrieveMethods(PApplet parent) {
		touchDown = SMTUtilities.getPMethod(parent, "touchDown");
		touchMoved = SMTUtilities.getPMethod(parent, "touchMoved");
		touchUp = SMTUtilities.getPMethod(parent, "touchUp");
	}
}
