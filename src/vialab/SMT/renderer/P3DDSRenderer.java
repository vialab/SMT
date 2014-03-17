package vialab.SMT.renderer;

//standard library imports
import java.util.Stack;
import java.lang.reflect.Method;

//processing imports
import processing.core.*;
import processing.opengl.*;

public class P3DDSRenderer extends PGraphics3DDelegate {
	//private fields
	private Stack<PGraphics3D> delegates;

	//constructor
	public P3DDSRenderer(){
		super();
		delegates = new Stack<PGraphics3D>();
	}

	//delegate functions
	@Override
	public PGraphics3D getDelegate(){
		return delegates.isEmpty() ?
			this : delegates.peek();
	}
	@Override
	public void setDelegate( PGraphics3D delegate){
		pushDelegate( delegate);
	}
	public void pushDelegate( PGraphics3D delegate){
		if( delegate == null)
			throw new NullPointerException(
				"Cannot push a null to the delegate stack.");
		delegates.push( delegate);
	}
	public PGraphics3D popDelegate(){
		return delegates.pop();
	}

	//static fields
	public static final String CLASSNAME = P3DDSRenderer.class.getName();
	private static PApplet applet = null;
	private static P3DDSRenderer renderer = null;

	//static functions
	public static void init( PApplet applet){
		P3DDSRenderer.applet = applet;
		renderer = (P3DDSRenderer) applet.g;
		applet.registerMethod("pre", renderer);
		applet.registerMethod("draw", renderer);
	}
}