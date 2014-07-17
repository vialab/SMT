import vialab.SMT.*;

//vars
int window_width = 1200;
int window_height = 800;
boolean draw_fps = true;

//zones
Zone frame;
ViewportZone viewport;
Zone blue, red, purple, green;

void setup(){
	//basic setup
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//create zones
	frame = new Zone( "Frame", 50, 50, 440, 440);
	viewport = new ViewportZone( 20, 20, 400, 400);
	blue = new Zone( "Blue", 10, 50, 100, 100);
	red = new Zone( "Red", 50, 10, 100, 100);
	purple = new Zone( "Purple", 150, 300, 100, 100);
	green = new Zone( "Green", 300, 150, 100, 100);
	//set up zone structure
	SMT.add( purple);
	SMT.add( green);
	SMT.add( frame);
	frame.add( viewport);
	viewport.add( blue);
	viewport.add( red);
}

void draw(){
	background( 30);
	if( draw_fps) drawFrameRate();
}
//drawing methods
public void drawFrameRate(){
	float fps = this.frameRate;
	String fps_text = String.format( "fps: %.0f", fps);
	pushStyle();
	fill( 240, 240, 240, 180);
	textAlign( RIGHT, TOP);
	textSize( 32);
	text( fps_text, window_width - 20, 20);
	popStyle();
}

//keyboard handle
void keyPressed(){
	//println( key);
	switch( key){
		case 'f':{
			draw_fps = ! draw_fps;
			break;}
		default: break;
	}
}

//zone functions

//methods for the "frame" zone
void drawFrame( Zone zone){
	pushMatrix();
	pushStyle();
	fill( 30, 30, 30, 180);
	stroke( 240, 240, 240, 220);
	strokeWeight( 5);
	rect( 0, 0, zone.width + 00, zone.height + 00, 0);
	popStyle();
	popMatrix();
}
void touchFrame( Zone zone){
	zone.rst();
}
void touchUpFrame( Zone zone, Touch touch){
	viewport.refresh();
}

//methods for viewport zone
void drawViewportZone( Zone zone){
	background( 40, 70, 70, 180);
}
void touchViewportZone( Zone zone){
	zone.pinch();
}

//methods for the "blue" zone
void drawBlue( Zone zone){
	pushStyle();
	noStroke();
	fill( 110, 140, 180, 230);
	rect( 0, 0, zone.width, zone.height, 0);
	popStyle();
}
void touchBlue( Zone zone){
	zone.rst();
}

//methods for the "red" zone
void drawRed( Zone zone){
	pushStyle();
	noStroke();
	fill( 180, 120, 110, 230);
	rect( 0, 0, zone.width, zone.height, 0);
	popStyle();
}
void touchRed( Zone zone){
	zone.rst();
}


//methods for the "purple" zone
void drawPurple( Zone zone){
	pushStyle();
	noStroke();
	fill( 150, 110, 150, 230);
	rect( 0, 0, zone.width, zone.height, 0);
	popStyle();
}
void touchPurple( Zone zone){
	zone.rst();
}


//methods for the "green" zone
void drawGreen( Zone zone){
	pushStyle();
	noStroke();
	fill( 120, 180, 110, 230);
	rect( 0, 0, zone.width, zone.height, 0);
	popStyle();
}
void touchGreen( Zone zone){
	zone.rst();
}
