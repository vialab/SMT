package vialab.SMT.event;

public interface TouchListener extends java.util.EventListener{
	public void handleTouchDown( TouchEvent touchEvent);
	public void handleTouchUp( TouchEvent touchEvent);
	public void handleTouchMoved( TouchEvent touchEvent);
}