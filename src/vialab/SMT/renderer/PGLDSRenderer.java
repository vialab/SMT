package vialab.SMT.renderer;

//standard library imports
import java.util.Stack;
import java.lang.reflect.Method;

//processing imports
import processing.core.*;
import processing.opengl.*;

/**
 * A Renderer for processing. It adds a delegate stack feature to to the PGraphicsOpenGL class.
 */
public class PGLDSRenderer extends PGraphicsOpenGLDelegate {
	//static fields
	/**
	 * Fully qualified class name of this class. Can be used for the renderer arguement in a PApplet's size function.
	 */
	public static final String CLASSNAME = PGLDSRenderer.class.getName();

	//private fields
	/**
	 * The current delegate stack
	 */
	private Stack<PGraphicsOpenGL> delegates;

	//constructor
	/**
	 * Creates a new PGraphicsOpenGL delegate stack renderer that delegates to nothing.
	 */
	public PGLDSRenderer(){
		super();
		delegates = new Stack<PGraphicsOpenGL>();
	}

	//delegate functions
	/**
	 * Gets the current desired delegate for this object.
	 * @return if the stack is not empty, the PGraphics3D object at the top of the stack, otherwise, this object.
	 */
	@Override
	public PGraphicsOpenGL getDelegate(){
		return delegates.isEmpty() ?
			this : delegates.peek();
	}
	/**
	 * Returns whether this object has objects in it's delegate stack.
	 * @return whether this object has objects in it's delegate stack.
	 */
	@Override
	public boolean hasDelegate(){
		return delegates.isEmpty();
	}
	/**
	 * Add an object to the delegate stack
	 * @param delegate the object to add to the delegate stack
	 */
	@Override
	public void setDelegate( PGraphicsOpenGL delegate){
		pushDelegate( delegate);
	}
	/**
	 * Add an object to the delegate stack
	 * @param delegate the object to add to the delegate stack
	 */
	public void pushDelegate( PGraphicsOpenGL delegate){
		if( delegate == null)
			throw new NullPointerException(
				"Cannot push a null to the delegate stack.");
		delegates.push( delegate);
	}
	/**
	 * Remove an object from the delegate stack
	 */
	public PGraphicsOpenGL popDelegate(){
		return delegates.pop();
	}
}