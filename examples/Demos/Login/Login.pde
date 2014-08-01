//imports
import vialab.SMT.*;

//variables
String username = "Enter username";
boolean username_selected = false;
SwipeKeyboard keyboard;

//main methods
void setup(){
	//create the window
	size( 1200, 800, SMT.RENDERER);
	//initialize SMT
	SMT.init( this, TouchSource.AUTOMATIC);

	//create our zones
	Zone usernameZone = new Zone( "UsernameZone");
	usernameZone.setSize( 800, 120);
	usernameZone.setLocation( 200, 150);

	//keyboard
	keyboard = new SwipeKeyboard();
	keyboard.setLocation( 45, 300);
	keyboard.addKeyListener( this);
	keyboard.setVisible( false);
	keyboard.setPickable( false);

	//add our zones to the sketch
	SMT.add( usernameZone, keyboard);
}

//draw the background
void draw(){
	background( 255);
}

//draw function for username zone
void drawUsernameZone( Zone zone){
	//draw text box
	noFill();
	stroke( 15, 220);
	rect( 0, 0, zone.width, zone.height);
	//draw text
	fill( 15, 220);
	textAlign( CENTER, CENTER);
	textSize( 64);
	text( username, zone.width / 2, zone.height / 2);
}
void touchDownUsernameZone( Zone zone){
	//clear the username field and show the keyboard
	username = "";
	username_selected = true;
	keyboard.setVisible( true);
	keyboard.setPickable( true);
}

//keyboard handle
void keyPressed(){
	if( username_selected)
		switch( key){
			case '\b': //backspace
				if( ! username.isEmpty())
					username = username.substring(
						0, username.length() - 1);
				break;
			case '\n': //enter
				if( username.isEmpty()) break;
				keyboard.setVisible( false);
				keyboard.setPickable( false);
				username_selected = false;
				username = ":: Access Granted ::";
				break;
			case (char) 65535: //unknown keys
				break;
			default: //any other keys
				username += key;
		}
}

