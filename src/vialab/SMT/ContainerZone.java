package vialab.SMT;

/**
 * ContainerZone should allow easy ordering of Zones like any other, but does
 * not draw or pickDraw anything, and so should not have an effect on Zone
 * selection or drawing
 */
public class ContainerZone extends Zone {

	public ContainerZone() {
		super();
	}

	public ContainerZone(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public ContainerZone(String name, String renderer) {
		super(name, renderer);
		// TODO Auto-generated constructor stub
	}

	public ContainerZone(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public ContainerZone(int x, int y, int width, int height, String renderer) {
		super(x, y, width, height, renderer);
		// TODO Auto-generated constructor stub
	}

	public ContainerZone(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public ContainerZone(String name, int x, int y, int width, int height, String renderer) {
		super(name, x, y, width, height, renderer);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawImpl() {

	}

	@Override
	protected void touchImpl() {

	}

	@Override
	protected void pickDrawImpl() {

	}
}
