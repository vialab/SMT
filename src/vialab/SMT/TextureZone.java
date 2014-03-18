package vialab.SMT;

/**
 * TextureZone renders a Zone like a texture, requiring a call to setModified(true); to update it's contents
 * It also uses defaults that look much better such as a higher smoothing level and vector accurate text,
 * which would normally be too slow to draw every frame. Children of this Zone will also only render inside
 * its bounds, and only when a call to setModified(true) is made on the parent TextureZone
 */
public class TextureZone extends Zone {
	
	@Override
	protected boolean updateOnlyWhenModified() {
		return true;
	}

	public TextureZone() {
		this(null);
	}

	public TextureZone(String name) {
		this(name, SMT.zone_renderer);
	}

	public TextureZone(String name, String renderer) {
		this(name, 0, 0, 1, 1, renderer);
	}

	public TextureZone(int x, int y, int width, int height) {
		this(null, x, y, width, height, SMT.zone_renderer);
	}

	public TextureZone(int x, int y, int width, int height, String renderer) {
		this(null, x, y, width, height, renderer);
	}

	public TextureZone(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, SMT.zone_renderer);
	}

	public TextureZone(String name, int x, int y, int width, int height, String renderer) {
		super(name, x, y, width, height, renderer);
		this.setDirect(false);
	}
	
	@Override
	public void beginDraw(){
		super.beginDraw();
		smooth(8);
		textMode(SHAPE);
	}
}
