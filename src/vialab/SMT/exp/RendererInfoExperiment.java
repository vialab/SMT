package vialab.SMT.exp;

//processing imports
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

//standard library imports
import java.util.Vector;

public class RendererInfoExperiment extends PApplet {
	//fields
	Vector<PGraphics> list = new Vector<PGraphics>();
	boolean asdf = false;
	
	// main functions
	public void setup(){
		if( asdf){
			size( 800, 600, JAVA2D);
			list.add( this.createGraphics( 10, 10, JAVA2D));
		}
		else {
			size( 800, 600, P2D);
			list.add( this.createGraphics( 10, 10, P2D));
			list.add( this.createGraphics( 10, 10, P3D));
			list.add( this.createGraphics( 10, 10, "processing.opengl.PGraphicsOpenGL"));
		}
		for( PGraphics pg : list){
			System.out.printf( "Name: %s\n", pg.getClass().getName());
			System.out.printf( "\tis2D, is3d: %b, %b\n", pg.is2D(), pg.is3D());
			PMatrix pm = pg.getMatrix();
			System.out.printf( "\tmatrix is 2d, is 3d: %b, %b\n",
				pm instanceof PMatrix2D, pm instanceof PMatrix3D);
		}
	}

	// program entry point
	static public void main( String[] args) {
		String[] appletArgs = new String[] { RendererInfoExperiment.class.getName()};
		if (args != null)
			PApplet.main(concat(appletArgs, args));
		else
			PApplet.main(appletArgs);
	}
}
