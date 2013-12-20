package vialab.SMT.swipekeyboard;

public interface SwipeKeyboardListener{
	public void swipeCompleted( SwipeKeyboardEvent event);
	public void swipeStarted( SwipeKeyboardEvent event);
	public void swipeProgressed( SwipeKeyboardEvent event);
}