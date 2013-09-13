package vialab.SMT.event;

public interface SwipeKeyListener extends java.util.EventListener{
	public void swipeStarted( SwipeKeyEvent swipeKeyEvent);
	public void swipeHit( SwipeKeyEvent swipeKeyEvent);
	public void swipeEnded( SwipeKeyEvent swipeKeyEvent);
}