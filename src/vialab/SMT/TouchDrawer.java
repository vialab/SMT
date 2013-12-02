package vialab.SMT;
import processing.core.PGraphics;
public interface TouchDrawer {
	public void draw( Iterable<Touch> touches, PGraphics graphics);
}