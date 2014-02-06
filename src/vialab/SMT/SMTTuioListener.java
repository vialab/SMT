package vialab.SMT;

import java.util.ArrayList;
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

	private List<TuioCursor> currentTouchState =
		new ArrayList<TuioCursor>();
	private List<TuioObject> currentObjectState =
		new ArrayList<TuioObject>();

	private Map<Long, TuioCursor> removedCursors =
		new HashMap<Long, TuioCursor>();

	public SMTTuioListener(){
		super();
	}

	@Override
	public synchronized void addTuioCursor(TuioCursor cursor) {
		currentTouchState.add( cursor);
	}

	@Override
	public synchronized void updateTuioCursor(TuioCursor cursor) {
		TuioCursor tr = null;
		for (TuioCursor t : currentTouchState)
			if (t.getSessionID() == cursor.getSessionID())
				tr = t;
		currentTouchState.remove(tr);
		currentTouchState.add(cursor);
	}

	@Override
	public synchronized void removeTuioCursor(TuioCursor cursor) {
		TuioCursor tr = null;
		for (TuioCursor t : currentTouchState)
			if (t.getSessionID() == cursor.getSessionID())
				tr = t;
		currentTouchState.remove(tr);
	}

	@Override
	public synchronized void addTuioObject(TuioObject tobj) {
		currentObjectState.add(tobj);
	}

	@Override
	public synchronized void updateTuioObject(TuioObject tobj) {
		TuioObject tr = null;
		for (TuioObject t : currentObjectState)
			if (t.getSessionID() == tobj.getSessionID())
				tr = t;
		currentObjectState.remove(tr);
		currentObjectState.add(tobj);
	}

	@Override
	public synchronized void removeTuioObject(TuioObject tobj) {
		TuioObject tr = null;
		for (TuioObject t : currentObjectState)
			if (t.getSessionID() == tobj.getSessionID())
				tr = t;
		currentObjectState.remove(tr);
	}

	@Override
	public void refresh(TuioTime bundleTime) {}

	/**
	 * Returns the touch state that was most recently removed from the queue. If
	 * no state has been removed yet, returns the empty touch state.
	 */
	public synchronized List<TuioCursor> getCurrentTuioState() {
		List<TuioCursor> listCopy = new ArrayList<TuioCursor>();
		listCopy.addAll( currentTouchState);
		return listCopy;
	}

	/**
	 * Returns the touch state that was most recently removed from the queue. If
	 * no state has been removed yet, returns the empty touch state.
	 */
	public synchronized List<TuioObject> getCurrentObjectState() {
		List<TuioObject> listCopy = new ArrayList<TuioObject>();
		listCopy.addAll( currentObjectState);
		return listCopy;
	}

	public synchronized TuioCursor getRemovedCursor( Long id) {
		return removedCursors.get(id);
	}

	public synchronized List<TuioObject> getTuioObjects() {
		return currentObjectState;
	}

	public synchronized TuioObject getTuioObject( long s_id) {
		for (TuioObject t : currentObjectState)
			if (t.getSessionID() == s_id)
				return t;
		return null;
	}

	public synchronized TuioCursor getTuioCursor( long s_id) {
		for (TuioCursor t : currentTouchState)
			if (t.getSessionID() == s_id)
				return t;
		return null;
	}

	public synchronized List<TuioCursor> getTuioCursors() {
		return currentTouchState;
	}
}
