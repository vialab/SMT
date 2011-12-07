package vialab.simplerMultiTouch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import processing.core.PApplet;
import TUIO.TuioCursor;
import TUIO.TuioPoint;
import TUIO.TuioTime;

public final class SMTUtilities {
	private static class TuioTimeComparator implements Comparator<TuioTime> {
		@Override
		public int compare(TuioTime timeA, TuioTime timeB) {
			int temp = Long.compare(timeA.getSeconds(), timeB.getSeconds());
			if (temp != 0) {
				return temp;
			}
			return Long.compare(timeA.getMicroseconds(), timeB.getMicroseconds());
		}
	}

	public static final TuioTimeComparator tuioTimeComparator = new TuioTimeComparator();

	/**
	 * Don't let anyone instantiate this class.
	 */
	private SMTUtilities() {
	}

	static Method getPMethod(PApplet parent, String methodName, Class<?>... parameterTypes) {
		try {
			return parent.getClass().getMethod(methodName, parameterTypes);
		}
		catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

	static Method getPMethod(PApplet parent, String methodPrefix, String methodSuffix,
			Class<?>... parameterTypes) {
		String suffix = methodSuffix;
		if (!suffix.isEmpty()) {
			suffix = suffix.substring(0, 1).toUpperCase() + suffix.substring(1, suffix.length());
		}
		return getPMethod(parent, methodPrefix + suffix, parameterTypes);
	}

	static Object invoke(Method method, PApplet parent, Object... parameters) {
		if (method != null) {
			try {
				return method.invoke(parent, parameters);
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Returns the Touch(TuioCursor) associated with the session ID
	 * 
	 * @param s_id
	 *            long - Session ID of the Touch(TuioCursor)
	 * @param lastUpdate
	 * @return TuioCursor
	 */
	public static Touch getLastTouch(TuioCursor c, TuioTime lastUpdate) {
		Vector<TuioPoint> path = new Vector<>(c.getPath());

		Collections.reverse(path);
		// path.remove(0); // this should be the current touch

		// TuioPoint tuioPoint = path.firstElement();
		// return new Touch(tuioPoint.getTuioTime(), c.getSessionID(),
		// c.getCursorID(),
		// tuioPoint.getX(), tuioPoint.getY());

		for (TuioPoint tuioPoint : path) {
			if (tuioTimeComparator.compare(tuioPoint.getTuioTime(), lastUpdate) <= 0) {
				return new Touch(tuioPoint.getTuioTime(), c.getSessionID(), c.getCursorID(),
						tuioPoint.getX(), tuioPoint.getY());
			}
		}
		return null;
	}

}
