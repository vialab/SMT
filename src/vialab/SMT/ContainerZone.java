package vialab.SMT;

/**
 * ContainerZone is used to order and hold/contain zones. Drawing within it,
 * as well as pickDraw are disabled. Therefore, ContainerZone does not effect 
 * drawing or selecting the zones within it.
 */
public class ContainerZone extends Zone {

	/**
	 * 
	 */
	public ContainerZone(){
		super();
	}

	/**
	 * @param name     - String: The name of the zone
	 */
	public ContainerZone( String name){
		super( name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name     - String: The name of the zone
	 * @param renderer - String: The renderer of the zone
	 */
	public ContainerZone( String name, String renderer){
		super( name, renderer);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param x        - int: X-coordinate of the upper left corner of the zone
	 * @param y        - int: Y-coordinate of the upper left corner of the zone
	 * @param width    - int: Width of the zone
	 * @param height   - int: Height of the zone
	 */
	public ContainerZone( int x, int y, int width, int height){
		super( x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param x        - int: X-coordinate of the upper left corner of the zone
	 * @param y        - int: Y-coordinate of the upper left corner of the zone
	 * @param width    - int: Width of the zone
	 * @param height   - int: Height of the zone
	 * @param renderer - String: The renderer of the zone
	 */
	public ContainerZone( int x, int y, int width, int height, String renderer){
		super( x, y, width, height, renderer);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name     - String: The name of the zone
	 * @param x        - int: X-coordinate of the upper left corner of the zone
	 * @param y        - int: Y-coordinate of the upper left corner of the zone
	 * @param width    - int: Width of the zone
	 * @param height   - int: Height of the zone
	 */
	public ContainerZone( String name, int x, int y, int width, int height){
		super( name, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name     - String: The name of the zone
	 * @param x        - int: X-coordinate of the upper left corner of the zone
	 * @param y        - int: Y-coordinate of the upper left corner of the zone
	 * @param width    - int: Width of the zone
	 * @param height   - int: Height of the zone
	 * @param renderer - String: The renderer of the zone
	 */
	public ContainerZone( String name, int x, int y, int width, int height, String renderer){
		super( name, x, y, width, height, renderer);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawImpl(){}

	@Override
	protected void touchImpl(){}

	@Override
	protected void pickDrawImpl(){}
}
