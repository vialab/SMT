package vialab.SMT;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import TUIO.TuioCursor;
import TUIO.TuioPoint;
import TUIO.TuioTime;

public final class SMTUtilities {
	private static class TuioTimeComparator implements Comparator<TuioTime> {
		@Override
		public int compare(TuioTime timeA, TuioTime timeB) {
			Long timeALong = new Long(timeA.getSeconds());
			int temp = timeALong.compareTo(timeB.getSeconds());
			// int temp = Long.compare(timeA.getSeconds(), timeB.getSeconds());
			if (temp != 0) {
				return temp;
			}
			timeALong = new Long(timeA.getMicroseconds());
			// return Long.compare(timeA.getMicroseconds(),
			// timeB.getMicroseconds());
			return timeALong.compareTo(timeB.getMicroseconds());
		}
	}

	public static final TuioTimeComparator tuioTimeComparator = new TuioTimeComparator();
	private static Set<String> methodSet = new HashSet<String>();

	/**
	 * Don't let anyone instantiate this class.
	 */
	private SMTUtilities() {
	}

	static Method getPMethod(PApplet parent, String methodName, Class<?>... parameterTypes) {
		try {
			return parent.getClass().getMethod(methodName, parameterTypes);
		}
		catch (NoSuchMethodException e) {
			// try to call with no parameter too, to allow optional zone
			// parameter
			try {
				return parent.getClass().getMethod(methodName);
			}
			catch (NoSuchMethodException e2) {}
			catch (SecurityException e2) {}
		}
		catch (SecurityException e) {}
		return null;
	}

	static Method getPMethod(PApplet parent, String methodPrefix, String methodSuffix,
			Class<?>... parameterTypes) {
		String suffix = methodSuffix;
		if (!suffix.isEmpty()) {
			suffix = suffix.substring(0, 1).toUpperCase() + suffix.substring(1, suffix.length());
		}
		return getPMethod(parent, methodPrefix + suffix, parameterTypes);
	}

	static Method getAnyPMethod(PApplet parent, String methodPrefix, String methodSuffix,
			Class<?> parameter) {
		if (parameter == null) {
			return null;
		}

		Method method = getPMethod(parent, methodPrefix, methodSuffix, parameter);
		if (method == null) {
			Class<?> superClass = parameter.getSuperclass();
			method = getAnyPMethod(parent, methodPrefix, methodSuffix, superClass);
		}
		return method;
	}

	static Method getZoneMethod(PApplet parent, String methodPrefix, String name, Class<?> parameter, boolean warnMissing) {
		Method method = getAnyPMethod(parent, methodPrefix, name, parameter);
		if (method == null) {
			if(warnMissing){
				checkMethod(methodPrefix+name);
			}
			method = getAnyPMethod(parent, methodPrefix, "Default", parameter);
		}
		return method;
	}

	private static void checkMethod(String methodName) {
		if(!methodSet.contains(methodName)){
			System.out.println("Method: "+methodName+" does not exist");
		}
		methodSet.add(methodName);
	}

	static Object invoke(Method method, PApplet parent, Object... parameters) {
		if (method != null) {
			try {
				return method.invoke(parent, parameters);
			}
			catch (IllegalAccessException e) {}
			catch (IllegalArgumentException e) {
				// try calling the method with no parameters, to allow optional
				// zone parameter
				try {
					return method.invoke(parent);
				}
				catch (IllegalAccessException e2) {}
				catch (IllegalArgumentException e2) {}
				catch (InvocationTargetException e2) {}
			}
			catch (InvocationTargetException e) {}
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
		Vector<TuioPoint> path = new Vector<TuioPoint>(c.getPath());

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

	public static void start(String... args) {
		PApplet.main(args);
	}

	/**
	 * This method draws the image at (x,y) within the specified width/height
	 * parameters, but maintaining the aspect ratio of the image.
	 * 
	 * @param g
	 *            the graphics context to draw the image in
	 * @param image
	 *            the image to draw
	 * @param x
	 *            the x-coordinate of the position to draw it at
	 * @param y
	 *            the y-coordinate of the position to draw it at
	 * @param width
	 *            the width to draw the image within
	 * @param height
	 *            the height to draw the image within
	 */
	public static void aspectImage(PGraphics g, PImage image, int x, int y, int width, int height) {
		float bgX = 0;
		float bgY = 0;
		float bgWidth = width;
		float bgHeight = height;

		if (height == 0 || image.height == 0) {
			return;
		}

		float aspectBoard = (float) width / height;
		float aspectImage = (float) image.width / image.height;
		if (aspectBoard < aspectImage && aspectImage != 0) {
			bgHeight = bgWidth / aspectImage;
			bgY += (height - bgHeight) / 2;
		}
		else if (aspectBoard > aspectImage) {
			bgWidth = bgHeight * aspectImage;
			bgX += (width - bgWidth) / 2;
		}

		g.image(image, bgX, bgY, bgWidth, bgHeight);
	}

	public static void aspectImage(PApplet applet, PImage image, int x, int y, int width, int height) {
		aspectImage(applet.g, image, x, y, width, height);
	}

	// public static Rectangle fit(Rectangle outer, Rectangle inner) {
	//
	// }
}
