package vialab.SMT.zone;

//standard library imports
import java.awt.event.*;
import java.util.Vector;

public class Key extends Zone {
	//Private fields
	private Vector<KeyListener> listeners;

	//Constructors
	public Key(){
		listeners = new Vector<KeyListener>();
	}

	//SMT overrides
	@Override
	public void drawImpl() {
		fill( 0, 0, 0, 255);
		rect( 0, 0, 10, 10);
	}
	@Override
	public void pickDrawImpl() {}
	@Override
	public void touchDownImpl() {}
	@Override
	public void touchUpImpl() {}
	@Override
	public void touchImpl() {}

	//event creation functions
	public void handleKeyPressed(){
		for( KeyListener listener : listeners){
			lisener.keyPressed( keyEvent);
		}
	}
	public void handleKeyReleased(){
		for( KeyListener listener : listeners){
			lisener.keyReleased( keyEvent);
		}
	}
	public void handleKeyTyped(){
		for( KeyListener listener : listeners){
			lisener.keyTyped( keyEvent);
		}
	}
}