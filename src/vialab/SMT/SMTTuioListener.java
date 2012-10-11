package vialab.SMT;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

class SMTTuioListener implements TuioListener {
	// private Queue<TouchState> newTouchStates = new LinkedList<TouchState>();
	//
	// private TouchState currentTouchState = new TouchState();

	private List<TuioCursor> currentTouchState = Collections.emptyList();
	private List<TuioObject> currentObjectState = Collections.emptyList();

	private Map<Long, TuioCursor> removedCursors = new HashMap<Long, TuioCursor>();

	public SMTTuioListener() {
		super();
	}

	@Override
	public void addTuioCursor(TuioCursor tcur) {
		// TouchState state = new
		// TouchState(TouchClient.tuioClient.getTuioCursors());
		// publishTouchState(state);
		currentTouchState = TouchClient.tuioClient.getTuioCursors();

		// picker.pick(tcur, applet.width, applet.height);
	}

	@Override
	public void updateTuioCursor(TuioCursor tcur) {
		// if (tcur.isMoving()) {
		// TouchState state = new
		// TouchState(TouchClient.tuioClient.getTuioCursors());
		// publishTouchState(state);
		// // picker.pick(tcur, applet.width, applet.height);
		// }
		currentTouchState = TouchClient.tuioClient.getTuioCursors();
	}

	@Override
	public void removeTuioCursor(TuioCursor tcur) {
		// TouchState state = new
		// TouchState(TouchClient.tuioClient.getTuioCursors());
		// publishTouchState(state);
		// // picker.pick(tcur, applet.width, applet.height);
		removedCursors.put(tcur.getSessionID(), tcur);
		currentTouchState = TouchClient.tuioClient.getTuioCursors();
	}

	@Override
	public void addTuioObject(TuioObject tobj) {
		// SMTUtilities.invoke(addObject, applet, tobj);

		currentObjectState = TouchClient.tuioClient.getTuioObjects();
	}

	@Override
	public void updateTuioObject(TuioObject tobj) {
		// SMTUtilities.invoke(updateObject, applet, tobj);
		currentObjectState = TouchClient.tuioClient.getTuioObjects();
	}

	@Override
	public void removeTuioObject(TuioObject tobj) {
		// SMTUtilities.invoke(removeObject, applet, tobj);
		currentObjectState = TouchClient.tuioClient.getTuioObjects();
	}

	@Override
	public void refresh(TuioTime bundleTime) {
		// SMTUtilities.invoke(refresh, applet, bundleTime);
		currentTouchState = TouchClient.tuioClient.getTuioCursors();
		currentObjectState = TouchClient.tuioClient.getTuioObjects();
	}

	/**
	 * Returns the touch state that was most recently removed from the queue. If
	 * no state has been removed yet, returns the empty touch state.
	 */
	public synchronized List<TuioCursor> getCurrentTuioState() {
		return currentTouchState;
	}

	/**
	 * Returns the touch state that was most recently removed from the queue. If
	 * no state has been removed yet, returns the empty touch state.
	 */
	public synchronized List<TuioObject> getCurrentObjectState() {
		return currentObjectState;
	}

	public TuioCursor getRemovedCursor(Long id) {
		return removedCursors.get(id);
	}
}
