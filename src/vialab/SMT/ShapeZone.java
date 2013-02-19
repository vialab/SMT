package vialab.SMT;

public class ShapeZone extends Zone {

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
	public void pickDrawImpl(){
		drawImpl();
		SMTUtilities.invoke(drawMethod, applet, this);
	}
}
