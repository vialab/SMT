package vialab.SMT;

/**
 * ShapeZone will only be touchable where it is drawn, for example if its draw
 * method calls ellipse(...) it will only be touchable in that ellipse, instead
 * of the default rectangle used for zone, or using pickDraw to customize this area
 */
public class ShapeZone extends Zone {

	/**
	 * @param name
	 *            - String: The name of the zone, used for the draw<ZoneName>()
	 *            and touch<ZoneName>(), etc methods
	 * @param x
	 *            - int: X-coordinate of the upper left corner of the zone
	 * @param y
	 *            - int: Y-coordinate of the upper left corner of the zone
	 * @param width
	 *            - int: Width of the zone
	 * @param height
	 *            - int: Height of the zone
	 * @param renderer
	 *            - String: The renderer used to draw this zone
	 */
	public ShapeZone() {
		// TODO Auto-generated constructor stub
	}

	public ShapeZone(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public ShapeZone(String name, String renderer) {
		super(name, renderer);
		// TODO Auto-generated constructor stub
	}

	public ShapeZone(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public ShapeZone(int x, int y, int width, int height, String renderer) {
		super(x, y, width, height, renderer);
		// TODO Auto-generated constructor stub
	}

	public ShapeZone(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public ShapeZone(String name, int x, int y, int width, int height, String renderer) {
		super(name, x, y, width, height, renderer);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void pickDrawImpl() {
		drawImpl();
		SMTUtilities.invoke(drawMethod, applet, this);
	}
}
