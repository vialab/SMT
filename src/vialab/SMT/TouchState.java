package vialab.SMT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import TUIO.TuioCursor;

public class TouchState implements Iterable<Touch> {
	private LinkedHashMap<Long, TuioCursor> idToTouches = new LinkedHashMap<Long, TuioCursor>();

	public TouchState() {
	}

	public TouchState(TouchState state) {
		if (state == null) {
			return;
		}

		for (Touch touch : state) {
			add(new Touch(touch));
		}
	}

	public TouchState(TuioCursor... touches) {
		for (TuioCursor touch : touches) {
			add(touch);
		}
	}

	public TouchState(Collection<? extends TuioCursor> touches) {
		for (TuioCursor touch : touches) {
			add(touch);
		}
	}

	public void add(TuioCursor t) {
		if (t == null) {
			return;
		}

		idToTouches.put(t.getSessionID(), t);
	}

	public Touch remove(Long id) {
		if (idToTouches.containsKey(id)) {
			return new Touch(idToTouches.remove(id));
		}
		return null;
	}

	public Touch getById(long id) {
		return new Touch(idToTouches.get(id));
	}

	public Touch getByOrderAdded(int order) {
		int i = 0;
		for (TuioCursor t : idToTouches.values()) {
			if (i == order) {
				return new Touch(t);
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
		ArrayList<Touch> touches = new ArrayList<Touch>(idToTouches.size());
		for (TuioCursor tcur : idToTouches.values()) {
			touches.add(new Touch(tcur));
		}
		return touches.iterator();
	}
}
