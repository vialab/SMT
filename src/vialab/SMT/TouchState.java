package vialab.SMT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import TUIO.TuioCursor;

class TouchState implements Iterable<Touch> {
	LinkedHashMap<Long, Touch> idToTouches = new LinkedHashMap<Long, Touch>();

	public TouchState() {
	}

	public TouchState(TouchState state) {
		if (state == null) {
			return;
		}

		for (Touch touch : state) {
			add(touch);
		}
	}

	public void add(Touch t) {
		if (t == null) {
			return;
		}

		idToTouches.put(t.getSessionID(), t);
	}

	public Touch remove(Long id) {
		if (idToTouches.containsKey(id)) {
			return idToTouches.remove(id);
		}
		return null;
	}

	public Touch getById(long id) {
		return idToTouches.get(id);
	}

	public Touch getByOrderAdded(int order) {
		int i = 0;
		for (Touch t : idToTouches.values()) {
			if (i == order) {
				return t;
			}
		}
		return null;
	}

	public int size() {
		return idToTouches.size();
	}

	public boolean contains(Long id) {
		return idToTouches.containsKey(id);
	}

	@Override
	public Iterator<Touch> iterator() {
		return idToTouches.values().iterator();
	}

	public void update(List<TuioCursor> currentTuioState) {
		ArrayList<Touch> currentCursors = new ArrayList<Touch>();
		for (TuioCursor t : currentTuioState) {
			Touch c;
			if (!this.contains(t.getSessionID())) {
				// if no touch maps to it, create touch from it, add to map
				c = new Touch(t);
				this.add(c);
				c.isDown = true;
			}
			else {
				// otherwise find corresponding touch and update the touch
				c = this.getById(t.getSessionID());
				c.updateTouch(t);
			}
			currentCursors.add(c);
		}

		// remove touches that correspond to removed cursors
		Iterator<Touch> rmv = iterator();
		while (rmv.hasNext()) {
			Touch t = rmv.next();
			if (!currentCursors.contains(t)) {
				// the cursor was not updated, assume up and remove
				rmv.remove();
				t.isDown = false;
			}
		}
	}
}
