package vialab.SMT.util;

//libtuio imports
import TUIO.*;

/**
 * This class exists to insert the port number of connections into session id of tuio cursors and objects . This enables the unification multiple tuio connections on different ports into a single tuio listener, while still being able to differentiate the connections source of individual cursors and objects.
 */
public class ProxyTuioListener implements TuioListener {

	// fields
	private int port;
	private TuioListener actual_listener;

	// constructors
	/**
	 * Create a new proxy tuio listener.
	 * @param  port The port of the connection
	 * @param  actual_listener The tuio listener that will recieve the modified cursors and objects
	 */
	public ProxyTuioListener( int port, TuioListener actual_listener) {
		this.port = port;
		this.actual_listener = actual_listener;
	}

	// tuio listener functions
	@Override
	public synchronized void addTuioCursor( TuioCursor tcur) {
		TuioCursor proxy_cursor = new ProxyTuioCursor( tcur, port);
		actual_listener.addTuioCursor( proxy_cursor);
	}
	@Override
	public synchronized void updateTuioCursor( TuioCursor tcur) {
		TuioCursor proxy_cursor = new ProxyTuioCursor( tcur, port);
		actual_listener.updateTuioCursor( proxy_cursor);
	}
	@Override
	public synchronized void removeTuioCursor( TuioCursor tcur) {
		TuioCursor proxy_cursor = new ProxyTuioCursor( tcur, port);
		actual_listener.removeTuioCursor( proxy_cursor);
	}
	@Override
	public synchronized void addTuioObject( TuioObject tobj) {
		TuioObject proxy_object = new ProxyTuioObject( tobj, port);
		actual_listener.addTuioObject( proxy_object);
	}
	@Override
	public synchronized void updateTuioObject( TuioObject tobj) {
		TuioObject proxy_object = new ProxyTuioObject( tobj, port);
		actual_listener.updateTuioObject( proxy_object);
	}
	@Override
	public synchronized void removeTuioObject(  TuioObject tobj) {
		TuioObject proxy_object = new ProxyTuioObject( tobj, port);
		actual_listener.removeTuioObject( proxy_object);
	}
	@Override
	public void refresh( TuioTime bundleTime) {
		actual_listener.refresh( bundleTime);
	}

	/**
	 * A little function that modifies sessionIDs to include the port of the session.
	 * @param port the port of the session
	 * @param sessionID the id of the session
	 * @return the modified, more useful, but more restricted session id
	 */
	protected static long portmod( int port, long sessionID){
		//write the port into the first four bytes of the session ID
		return
			(( (long) port) << 48) +
			( 0x0000ffffffffffffl & sessionID);
	}

	//subclasses
	/**
	 * A proxy tuio cursor with the port written into the first four bytes of the session id.
	 */
	protected class ProxyTuioCursor extends TuioCursor {
		private int port;
		private TuioCursor origin;
		public ProxyTuioCursor( TuioCursor origin, int port){
			//super constructor call
			super(
				origin.getTuioTime(), portmod( port, origin.getSessionID()),
				origin.getCursorID(), origin.getX(), origin.getY());
			//steal all the fields
			super.startTime = origin.getStartTime();
			super.x_speed = origin.getXSpeed();
			super.y_speed = origin.getYSpeed();
			super.motion_speed = origin.getMotionSpeed();
			super.motion_accel = origin.getMotionAccel();
			super.path = origin.getPath();
			super.state = origin.getTuioState();
			//initialize our own fields
			this.port = port;
			this.origin = origin;
		}
	}
	/**
	 * A proxy tuio object with the port written into the first four bytes of the session id.
	 */
	protected class ProxyTuioObject extends TuioObject {
		private int port;
		private TuioObject origin;
		public ProxyTuioObject( TuioObject origin, int port){
			//super constructor call
			super(
				origin.getTuioTime(), portmod( port, origin.getSessionID()),
				origin.getSymbolID(), origin.getX(), origin.getY(), origin.getAngle());
			//steal all the fields
			super.startTime = origin.getStartTime();
			super.x_speed = origin.getXSpeed();
			super.y_speed = origin.getYSpeed();
			super.motion_speed = origin.getMotionSpeed();
			super.motion_accel = origin.getMotionAccel();
			super.path = origin.getPath();
			super.state = origin.getTuioState();
			super.rotation_accel = origin.getRotationAccel();
			super.rotation_speed = origin.getRotationSpeed();
			//initialize our own fields
			this.port = port;
			this.origin = origin;
		}
	}
}
