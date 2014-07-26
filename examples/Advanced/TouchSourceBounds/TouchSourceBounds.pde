/**
 * Sketch for Touch-source-bounds Tutorial
 */

import java.awt.Rectangle;
import vialab.SMT.*;
import vialab.SMT.util.*;

//vars
String keys_text;
TouchBinder binder_custom;

//Setup function for the applet
void setup(){
	//SMT and Processing setup
	size( 1200, 800, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//setup
	keys_text =  "key :: touch binding method\n";
	keys_text += "  q :: active display\n";
	keys_text += "  w :: display index\n";
	keys_text += "  e :: display id\n";
	keys_text += "  r :: rectangle\n";
	keys_text += "  s :: screen\n";
	keys_text += "  d :: sketch\n";
	keys_text += "  f :: custom";

	//create a custom touch binder
	binder_custom = new TouchBinder();
	//binder_custom.setDebug( true);
	PMatrix2D bind_matrix = new PMatrix2D();
	bind_matrix.scale( 1200, 800);
	bind_matrix.rotate( HALF_PI);
	bind_matrix.translate( 0, - 1);
	binder_custom.setBindMatrix( bind_matrix);
	binder_custom.setClampMin( new PVector( 0, 0));
	binder_custom.setClampMax( new PVector( 1200, 800));

	//choose default touch binding method
	//SMT.setTouchSourceBoundsActiveDisplay();
	//SMT.setTouchSourceBoundsDisplay( 0);
	//SMT.setTouchSourceBoundsDisplay( ":0.1");
	//SMT.setTouchSourceBoundsRect(
	//	new Rectangle( 100, 100, 1000, 600));
	//SMT.setTouchSourceBoundsScreen();
	//SMT.setTouchSourceBoundsSketch();
	//SMT.setTouchSourceBoundsCustom( binder_custom);
}

//Draw function for the sketch
void draw(){
	background( 30);
	fill( 240, 200);
	textAlign( CENTER, CENTER);
	textSize( 20);
	text( keys_text, 600, 400);
}

//keyboard handle
void keyPressed(){
	println( key);
	switch( key){
		case 'q':{
			SMT.setTouchSourceBoundsActiveDisplay();
			break;}
		case 'w':{
			SMT.setTouchSourceBoundsDisplay( 0);
			break;}
		case 'e':{
			SMT.setTouchSourceBoundsDisplay( ":0.1");
			break;}
		case 'r':{
			SMT.setTouchSourceBoundsRect(
				new Rectangle( 100, 100, 1000, 600));
			break;}
		case 's':{
			SMT.setTouchSourceBoundsScreen();
			break;}
		case 'd':{
			SMT.setTouchSourceBoundsSketch();
			break;}
		case 'f':{
			SMT.setTouchSourceBoundsCustom( binder_custom);
			break;}
		default: break;
	}
}