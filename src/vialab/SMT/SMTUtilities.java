package vialab.SMT;

import java.lang.reflect.Method;
import java.util.ArrayList;
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

/**
 * SMTUtilities has some methods that are useful throughout SMT, and in
 * extending classes
 */
public final class SMTUtilities {

	private static boolean warned = false;

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
	static Set<String> methodSet = new HashSet<String>();
	static Set<String> prefixSet = new HashSet<String>();

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
			Method m = null;
			if (!SMT.extraClassList.isEmpty()) {
				for (Class<?> c : SMT.extraClassList) {
					try {
						m = c.getMethod(methodName, parameterTypes);
					}
					catch (NoSuchMethodException e1) {
						continue;
					}
					catch (SecurityException e1) {
						continue;
					}
					if (m != null) {
						if (SMT.debug) {
							System.out.println(c.toString() + m.toString());
						}
						return m;
					}
				}
			}
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
		
		Method m = getPMethod(parent, methodPrefix + suffix, parameterTypes);
		if(m == null){
			String suffix_no_num_suffix = suffix;
			while(suffix_no_num_suffix.length()>0 && suffix_no_num_suffix.substring(suffix_no_num_suffix.length()-1, suffix_no_num_suffix.length()).matches("\\d")){
					suffix_no_num_suffix = suffix_no_num_suffix.substring(0,suffix_no_num_suffix.length()-1);
			}
			if(suffix_no_num_suffix.length()>0){
				m = getPMethod(parent, methodPrefix + suffix_no_num_suffix, parameterTypes);
			}
		}
		return m;
	}

	static Method getAnyPMethod(PApplet parent, String methodPrefix, String methodSuffix,
			boolean removeFirst, Class<?>... parameter) {
		if (parameter.length == 0) {
			return getPMethod(parent, methodPrefix, methodSuffix, parameter);
		}

		if (parameter[0] == null && removeFirst) {
			// try to get a method with the first class removed from the
			// parameter list since it is null
			Class<?>[] firstRemoved = new Class<?>[parameter.length - 1];
			System.arraycopy(parameter, 1, firstRemoved, 0, parameter.length - 1);
			return getAnyPMethod(parent, methodPrefix, methodSuffix, removeFirst, firstRemoved);
		}

		if (parameter[parameter.length - 1] == null && !removeFirst) {
			// try to get a method with the last class removed from the
			// parameter list since it is null
			Class<?>[] lastRemoved = new Class<?>[parameter.length - 1];
			System.arraycopy(parameter, 0, lastRemoved, 0, parameter.length - 1);
			return getAnyPMethod(parent, methodPrefix, methodSuffix, removeFirst, lastRemoved);
		}

		Method method = getPMethod(parent, methodPrefix, methodSuffix, parameter);
		if (method == null) {
			if (removeFirst) {
				// recurse only on first class
				Class<?> superClass = parameter[0].getSuperclass();
				Class<?>[] firstSupered = new Class<?>[parameter.length];
				System.arraycopy(parameter, 0, firstSupered, 0, parameter.length);
				firstSupered[0] = superClass;
				method = getAnyPMethod(parent, methodPrefix, methodSuffix, removeFirst,
						firstSupered);
			}
			else {
				// recurse only on last class
				Class<?> superClass = parameter[parameter.length - 1].getSuperclass();
				Class<?>[] lastSupered = new Class<?>[parameter.length];
				System.arraycopy(parameter, 0, lastSupered, 0, parameter.length);
				lastSupered[parameter.length - 1] = superClass;
				method = getAnyPMethod(parent, methodPrefix, methodSuffix, removeFirst, lastSupered);
			}
		}
		return method;
	}

	/**
	 * Use this method when creating Classes extending Zone, to acquires a
	 * Method which can be called by invoke(Method, Zone)
	 * 
     * @parm callingClass    - Class<?>: The class
	 * @param methodPrefix   - String: The prefix to look for on a method
	 * @param zone       - Zone: The Zone whose name is used as the method suffix
	 * @param warnMissing - boolean: If True, shows a warning when the method does not exist for the
	 *            given Zone
	 * @return A Method, which can be called by invoke(Method, Zone)
	 */
	public static Method getZoneMethod(Class<?> callingClass, String methodPrefix, Zone zone,
			boolean warnMissing) {
		return SMTUtilities.getZoneMethod(callingClass, Zone.applet, methodPrefix, zone.name,
				warnMissing, zone.getClass());
	}

	/**
	 * Use this method when creating Classes extending Zone, to acquires a
	 * Method which can be called by invoke(Method, Zone)
	 * 
     * @parm callingClass    - Class<?>: The class
	 * @param methodPrefix   - String: The prefix to look for on a method
	 * @param zone       - Zone: The Zone whose name is used as the method suffix
	 * @param warnMissing - boolean: If True, shows a warning when the method does not exist for the given Zone
	 * @param parameters  - Class<?>: The parameters that the method will have (use getClass() on an
	 *            object to get its class to use here)
	 * 
	 * @return A Method, which can be called by invoke(Method, parameters... )
	 */
	public static Method getZoneMethod(Class<?> callingClass, String methodPrefix, Zone zone,
			boolean warnMissing, Class<?>... parameters) {
		return SMTUtilities.getZoneMethod(callingClass, Zone.applet, methodPrefix, zone.name,
				warnMissing, parameters);
	}

	static Method getZoneMethod(Class<?> callingClass, PApplet parent, String methodPrefix,
			String name, boolean warnMissing, Class<?>... parameters) {
		// uppercase the first letter of the name so that we have consistent
		// naming warnings
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		Method method = getAnyPMethod(parent, methodPrefix, name, true, parameters);
		if (method == null) {
			method = getAnyPMethod(parent, methodPrefix, name, false, parameters);
		}
		if (method == null) {

			// warn only if the flag is set, the methodSet doesn't contain it(to
			// only warn once per method)
			// and the methodPrefix+Impl method is not provided by the class
			// itself
			if (warnMissing && !methodSet.contains(methodPrefix + name)
					&& !checkImpl(callingClass, methodPrefix, parameters)) {
				if (!warned) {
					System.err
							.println("\nCall SMT.setWarnUnimplemented(false) before zone creation to disable No such method warnings");
					warned = true;
				}
				System.err.print("No such method: " + methodPrefix + name+"(");
				boolean first = true;
				for(Class<?> c : parameters){
					if(first){
						first = false;
					}
					else{
						System.err.print(", ");
					}
					System.err.print(c.getName());
				}
				System.err.print(")");
				System.err.print(", using default "+methodPrefix+" method");
				ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
				classes.add(parent.getClass());
				classes.addAll(SMT.extraClassList);
				for(Class<?> c : classes){
					for(Method m : c.getDeclaredMethods()){
						if(m.getName().equalsIgnoreCase(methodPrefix+name)){
							System.err.print(", method found named "+m.getName()+"(");
							boolean first2 = true;
							for(Class<?> c2 : m.getParameterTypes()){
								if(first2){
									first2 = false;
								}
								else{
									System.err.print(", ");
								}
								System.err.print(c2.getName());
							}
							System.err.print(")");
							System.err.print(" in the class "+c.getName()+", which did not have correct capitalization or parameters");
							break;
						}
					}
				}
				System.err.println();
			}
			// set method to methodPrefix+Default if defined
			method = getAnyPMethod(parent, methodPrefix, "Default", true, parameters);
			if (method == null) {
				method = getAnyPMethod(parent, methodPrefix, "Default", false, parameters);
			}
		}
		methodSet.add(methodPrefix + name);
		prefixSet.add(methodPrefix);
		return method;
	}

	/**
	 * This checks whether a method that has the given methodPrefix exists in
	 * the parameter given with the assumed suffix of 'Impl'. For example the
	 * prefix 'draw' would mean this method checks for the method drawImpl()
	 * existing in the given class. The first parameter should be the calling
	 * object that by definition this method will return false for. This means
	 * we can have the method declared so that its subclasses can override it,
	 * but it will not count as having our own implementation, so that
	 * checkImpl() tells us when we have a real implementation of the method.
	 * 
	 * @param callingClass
	 *            The calling object, which will by definition return false
	 * 
	 * @param methodPrefix
	 *            The prefix to check for on methods in the given Class
	 * @param parameters
	 *            The Class to check for having a method with the given
	 *            methodPrefix, the first of this array should be the Zone class
	 * @return Whether the given class has a method with the given Prefix
	 */
	public static boolean checkImpl(Class<?> callingClass, String methodPrefix,
			Class<?>... parameters) {
		if (parameters[0] == null) {
			System.err.println("Error: CheckImpl() first class parameter was null.");
			return false;
		}

		if (parameters[0] == callingClass) {
			return false;
		}

		if (!(Zone.class.isAssignableFrom(parameters[0]))) {
			System.err
					.println("Error: CheckImpl() first class parameter ("
							+ parameters[0]
							+ ") was not Zone or a subclass, please give the current Zone class (using this.getClass()) as the first class parameter.");
			return false;
		}

		Method impl = null;
		Class<?>[] firstRemoved = new Class<?>[parameters.length - 1];
		System.arraycopy(parameters, 1, firstRemoved, 0, parameters.length - 1);
		try {
			// get the method if the class declared the prefix+Impl
			// method,
			// otherwise null
			impl = parameters[0].getDeclaredMethod(methodPrefix + "Impl", firstRemoved);
		}
		catch (Exception e) {}
		if (impl == null) {
			try {
				// check if we find the method with the parameter Zone,
				// and
				// give warning
				impl = parameters[0].getDeclaredMethod(methodPrefix + "Impl", parameters);
				if (impl != null) {
					System.err.println(methodPrefix + "Impl() in the class "
							+ parameters[0].getName()
							+ " should not have Zone as a parameter, please remove it to override "
							+ methodPrefix + "Impl() correctly.");
					// make sure we don't set impl as to return the
					// wrong
					// result when this method is called, as we just
					// want to
					// add the error
					impl = null;
				}
			}
			catch (Exception e) {}
		}
		// check if a super-class implements it, and the superclass is not Zone,
		// and also that this class is not Zone, as we shouldn't check the
		// superclass in that case either
		if (impl == null && parameters[0].getSuperclass() != null
				&& parameters[0].getSuperclass() != Zone.class && parameters[0] != Zone.class) {
			Class<?> superClass = parameters[0].getSuperclass();
			Class<?>[] firstSupered = new Class<?>[parameters.length];
			System.arraycopy(parameters, 0, firstSupered, 0, parameters.length);
			firstSupered[0] = superClass;
			if (checkImpl(callingClass, methodPrefix, firstSupered)) {
				return true;
			}
		}

		if (impl == null) {
			return false;
		}
		else {
			return true;
		}
	}

	static void warnUncalledMethods(PApplet parent) {
		Method methods[] = parent.getClass().getMethods();
		// add inherited methods to the set that should not be checked for being
		// uncalled
		Method inherited[] = parent.getClass().getSuperclass().getMethods();
		for (Method method : inherited) {
			methodSet.add(method.getName());
		}
		for (Method method : methods) {
			// find all methods that do not correspond to one used by a zone
			if (!methodSet.contains(method.getName())) {
				for (String prefix : prefixSet) {
					// check all unaccounted for methods to see if they match
					// any reserved prefixes
					if (method.getName().startsWith(prefix)) {
						System.err
								.println("The method '"
										+ method.getName()
										+ "' corresponds a zone named '"
										+ method.getName().replaceFirst(prefix, "")
										+ "' which did not exist during this run.\nIf this method is not meant to be used by a Zone, do not use the reserved method prefix '"
										+ prefix + "'.");
					}
				}
			}
		}
	}

	/**
	 * Invokes the given method of the given Zone
	 * 
	 * @param method
	 *            The method to invoke, usually from getZoneMethod(String, Zone,
	 *            boolean)
	 * @param zone
	 *            The zone to invoke the method of
	 * @return The return of the method that was invoked
	 */
	public static Object invoke(Method method, Zone zone) {
		return SMTUtilities.invoke(method, Zone.applet, zone);
	}

	/**
	 * This tries every different way we know to invoke the method, although we
	 * should just store the needed info when we get the method
	 * 
	 * @param method
	 * @param parent
	 * @param parameters
	 * @return The return of the invoked method, if no method was invoked it
	 *         will be null, the opposite is not true, as the return of an
	 *         invoked method can be null also
	 */
	static Object invoke(Method method, PApplet parent, Object... parameters) {
		if (method != null) {
			if (SMT.debug) {
				System.out.println("Invoking Method:" + method.toString());
			}
			Object[] removeFromFront = parameters.clone();
			Object[] removeFromBack = parameters.clone();
			// try to invoke the method many times, with parameters removed from
			// the front and back separately
			// one of these should return
			for (int i = 0; i < parameters.length + 1; i++) {
				// invoke with parameters removed from the front
				try {
					if (SMT.debug) {
						System.out.print("In Papplet with params: ");
						for (Object o : removeFromFront) {
							System.out.print(o.toString());
						}
						System.out.println();
					}
					return method.invoke(parent, removeFromFront);
				}
				catch (Exception e) {}
				if (parameters.length > 0 && Zone.class.isAssignableFrom(parameters[0].getClass())) {
					if (((Zone) parameters[0]).getBoundObject() != null) {
						Object o = ((Zone) parameters[0]).getBoundObject();
						try {
							if (SMT.debug) {
								System.out.print(o.toString() + " with params: ");
								for (Object o2 : removeFromFront) {
									System.out.print(o2.toString());
								}
								System.out.println();
							}
							return method.invoke(o, removeFromFront);
						}
						catch (Exception e) {}
					}
					else if (SMT.debug) {
						System.out.println("There was no object bound to the Zone: "
								+ parameters[0].toString());
					}
				}

				// invoke with parameters removed from the back
				try {
					if (SMT.debug) {
						System.out.print("In Papplet with params: ");
						for (Object o : removeFromFront) {
							System.out.print(o.toString());
						}
						System.out.println();
					}
					return method.invoke(parent, removeFromBack);
				}
				catch (Exception e) {}
				if (parameters.length > 0 && Zone.class.isAssignableFrom(parameters[0].getClass())) {
					if (((Zone) parameters[0]).getBoundObject() != null) {
						Object o = ((Zone) parameters[0]).getBoundObject();
						try {
							if (SMT.debug) {
								System.out.print(o.toString() + " with params: ");
								for (Object o2 : removeFromBack) {
									System.out.print(o2.toString());
								}
								System.out.println();
							}
							return method.invoke(o, removeFromBack);
						}
						catch (Exception e) {}
					}
					else if (SMT.debug) {
						System.out.println("There was no object bound to the Zone: "
								+ parameters[0].toString());
					}
				}

				if (removeFromFront.length > 0 && removeFromBack.length > 0) {
					removeFromFront = new Object[removeFromFront.length - 1];
					System.arraycopy(parameters, 1 + i, removeFromFront, 0, removeFromFront.length);
					removeFromBack = new Object[removeFromBack.length - 1];
					System.arraycopy(parameters, 0, removeFromBack, 0, removeFromBack.length);
				}
			}
		}
		return null;
	}

	/**
	 * Returns the Touch(TuioCursor) associated with the session ID
	 * 
	 * @param cursor
	 *            The Touch(TuioCursor) to get the last touch of befoer the
	 *            specified lastUpdate time
	 * @param lastUpdate
	 *            TuioTime of the last update
	 * @return TuioCursor
	 */
	public static Touch getLastTouchAtTime(TuioCursor cursor, TuioTime lastUpdate) {
		Vector<TuioPoint> path = new Vector<TuioPoint>(cursor.getPath());

		Collections.reverse(path);
		// path.remove(0); // this should be the current touch

		// TuioPoint tuioPoint = path.firstElement();
		// return new Touch(tuioPoint.getTuioTime(), c.getSessionID(),
		// c.getCursorID(),
		// tuioPoint.getX(), tuioPoint.getY());

		for (TuioPoint tuioPoint : path) {
			if (tuioTimeComparator.compare(tuioPoint.getTuioTime(), lastUpdate) <= 0) {
				return new Touch(tuioPoint.getTuioTime(), cursor.getSessionID(),
						cursor.getCursorID(), tuioPoint.getX(), tuioPoint.getY());
			}
		}
		return null;
	}

	/**
	 * This calls the main method in the PApplet
	 * 
	 * @param args
	 *            The args to call the main method with
	 */
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

	/**
	 * This method draws the image at (x,y) within the specified width/height
	 * parameters, but maintaining the aspect ratio of the image.
	 * 
	 * @param applet
	 *            the PApplet to draw the image in, specifically the PApplet's
	 *            graphics context g
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
	public static void aspectImage(PApplet applet, PImage image, int x, int y, int width, int height) {
		aspectImage(applet.g, image, x, y, width, height);
	}
}
