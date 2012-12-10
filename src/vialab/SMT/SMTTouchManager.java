package vialab.SMT;

import java.lang.reflect.Method;
import processing.core.PApplet;

class SMTTouchManager {
	private PApplet applet;

	private SMTTuioListener tuioListener;

	private SMTZonePicker picker;

	private Method touchDown, touchMoved, touchUp;

	static TouchState currentTouchState = new TouchState();

	static TouchState previousTouchState;

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
		// while (touchListener.hasMoreTouchStates()) {
		picker.renderPickBuffer();

		// fetch the previous and current state of all touches
		// (note that the order of fetching these matters!)
		// TouchState previousTouchState = touchListener.getCurrentTouchState();
		// TouchState currentTouchState = touchListener.dequeueTouchState();

		previousTouchState = new TouchState(currentTouchState);

		currentTouchState.update(tuioListener.getCurrentTuioState());

		// forward events
		handleTouchesDown();
		handleTouchesUp();
		handleTouchesMoved();
		// }
	}

	/**
	 * Handles every touch in the current but not the previous state.
	 */
	protected void handleTouchesDown() {
		for (Touch touchPoint : currentTouchState) {
			// touchDowns only happen on new touches
			if (!previousTouchState.contains(touchPoint.sessionID)) {
				SMTUtilities.invoke(touchDown, applet);
				doTouchDown(picker.pick(touchPoint), touchPoint);
			}
		}
	}

	/**
	 * Handles every touch in the previous but not in the current state.
	 */
	protected void handleTouchesUp() {
		for (Touch t : previousTouchState) {
			if (!currentTouchState.contains(t.sessionID)) {
				SMTUtilities.invoke(touchUp, applet);
				// the touch existed, but no longer exists, so it went up
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
				if (!t.isGrabbed()) {
					// Assign the touch to the picked Zone, as long as the touch
					// is not grabbed
					Zone z = picker.pick(t);
					if (z != null) {
						z.assign(t);
					}
				}
				for (Zone zone : t.getAssignedZones()) {
					doTouchMoved(zone, t);
				}
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
