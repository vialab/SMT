package vialab.SMT.renderer;

//standard library imports
import java.util.Stack;
import java.lang.reflect.Method;

//processing imports
import processing.core.*;
import processing.opengl.*;

public class PGLDSRenderer extends PGraphicsOpenGLDelegate {
	//private fields
	private Stack<PGraphicsOpenGL> delegates;
	private static PGraphicsOpenGL temp1 = null;
	private static PGraphicsOpenGL temp2 = null;
	private static PGraphicsOpenGL temp3 = null;

	//constructor
	public PGLDSRenderer(){
		super();
		delegates = new Stack<PGraphicsOpenGL>();
	}

	//delegate functions
	@Override
	public PGraphicsOpenGL getDelegate(){
		return delegates.isEmpty() ?
			this : delegates.peek();
	}
	@Override
	public void setDelegate( PGraphicsOpenGL delegate){
		pushDelegate( delegate);
	}
	public void pushDelegate( PGraphicsOpenGL delegate){
		if( delegate == null)
			throw new NullPointerException(
				"Cannot push a null to the delegate stack.");
		delegates.push( delegate);
	}
	public PGraphicsOpenGL popDelegate(){
		return delegates.pop();
	}

	//render calls
	public void pre(){
		deltest_pre();
	}
	public void draw(){
		deltest_draw();
	}

	//test functions
	public void deltest_pre(){
		pushDelegate( temp1);
			pushDelegate( temp2);
				pushDelegate( temp3);
					beginDraw();
					deltest_reflect();
					endDraw();
				popDelegate();
				beginDraw();
				image( temp3, 100, 100, temp3.width - 200, temp3.height - 200);
				pushStyle();
				noFill();
				stroke( 250, 250, 250, 200);
				strokeWeight( 5);
				strokeCap( ROUND);
				rect( 100, 100, width - 200, height - 200);
				popStyle();
				endDraw();
			popDelegate();
			beginDraw();
			image( temp2, 100, 100, temp2.width - 200, temp2.height - 200);
			pushStyle();
			noFill();
			stroke( 250, 250, 250, 200);
			strokeWeight( 5);
			strokeCap( ROUND);
			rect( 100, 100, width - 200, height - 200);
			popStyle();
			endDraw();
		popDelegate();
	}
	public void deltest_draw(){
		image( temp1, 100, 100, width - 200, height - 200);
		pushStyle();
		noFill();
		stroke( 250, 250, 250, 200);
		strokeWeight( 5);
		strokeCap( ROUND);
		rect( 100, 100, width - 200, height - 200);
		popStyle();
	}
	public static void deltest_reflect(){
		Class applet_class = applet.getClass();
		try{
			Method drawMethod = applet_class.getMethod("drawMethod");
			drawMethod.invoke( applet);}
		catch( Exception exception){
			exception.printStackTrace();}
	}

	//static fields
	public static final String CLASSNAME = PGLDSRenderer.class.getName();
	private static PApplet applet = null;
	private static PGLDSRenderer renderer = null;

	//static functions
	public static void init( PApplet applet){
		PGLDSRenderer.applet = applet;
		//validate renderer
		if( ! PGLDSRenderer.class.isInstance( applet.g))
			throw new RuntimeException(
				String.format( "To use this library you must use PGLDSRenderer.CLASSNAME as the renderer field in size(). For example: size( 800, 600, PGLDSRenderer.CLASSNAME). You used %s.",
					applet.g.getClass().getName()));
		renderer = (PGLDSRenderer) applet.g;
		applet.registerMethod("pre", renderer);
		applet.registerMethod("draw", renderer);

		//initialize temp1		
		temp1 = (PGraphicsOpenGL) applet.createGraphics(
			renderer.width, renderer.height, PConstants.OPENGL);
		temp1.beginDraw();
		temp1.background( 0, 0, 0, 0);
		temp1.endDraw();
		//initialize temp2
		temp2 = (PGraphicsOpenGL) applet.createGraphics(
			renderer.width, renderer.height, PConstants.OPENGL);
		temp2.beginDraw();
		temp2.background( 0, 0, 0, 0);
		temp2.endDraw();
		//initialize temp3
		temp3 = (PGraphicsOpenGL) applet.createGraphics(
			renderer.width, renderer.height, PConstants.OPENGL);
		temp3.beginDraw();
		temp3.background( 0, 0, 0, 0);
		temp3.endDraw();
	}
}