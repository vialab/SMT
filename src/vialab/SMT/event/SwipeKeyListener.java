package vialab.SMT.event;

//local imports
import vialab.SMT.*;

public interface SwipeKeyListener extends java.util.EventListener{
	//public void keyPressed( SwipeKeyEvent swipeKeyEvent);
	//public void keyReleased( SwipeKeyEvent swipeKeyEvent);
	//public void keyTyped( SwipeKeyEvent swipeKeyEvent);
	public void swipeStarted( SwipeKeyEvent swipeKeyEvent);
	public void swipeHit( SwipeKeyEvent swipeKeyEvent);
	public void swipeEnded( SwipeKeyEvent swipeKeyEvent);
}