//imports
import java.awt.*;
import java.awt.geom.*;


//vars
int window_width = 1280;
int window_height = 800;
Dimension screen = null;
Dimension screen2 = new Dimension();
Rectangle[] device_bounds = null;
Rectangle2D.Float vscreen = new Rectangle2D.Float();
PMatrix3D vmatrix = new PMatrix3D();
String text = "asdf";
boolean blur = false;

void setup(){
	size( window_width, window_height, P3D);
	GraphicsEnvironment environment =
		GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsDevice[] devices = environment.getScreenDevices();
	//System.out.printf( "Number of devices: %d\n", devices.length);

	int i = 0;
	device_bounds = new Rectangle[ devices.length];
	for( GraphicsDevice device : devices){
		//get device info
		GraphicsConfiguration config = device.getDefaultConfiguration();
		Rectangle bounds = config.getBounds();
		device_bounds[i] = bounds;
		//print device info
		System.out.printf( "Device %d/%d: %d, %d : %d, %d\n",
			i++, devices.length, bounds.x, bounds.y,
			bounds.width, bounds.height);
		//stretch screen2
		int device_maxx = bounds.x + bounds.width;
		int device_maxy = bounds.y + bounds.height;
		if( device_maxx > screen2.width)
			screen2.width = device_maxx;
		if( device_maxy > screen2.height)
			screen2.height = device_maxy;}

	//get screen size
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	screen = toolkit.getScreenSize();
	System.out.printf( "Screen size: %d, %d\n",
		screen.width, screen.height);
	System.out.printf( "Screen2 size: %d, %d\n",
		screen2.width, screen2.height);
	//scale screen size into 90% of window size
	double max_width = window_width * 0.9;
	double max_height = window_height * 0.9;
	//find best ratio
	double ratiox = screen.width / max_width;
	double ratioy = screen.height / max_height;
	double ratio = Math.min( ratiox, ratioy);
	//set dimensions
	vscreen.width = (float) ( screen.width / ratio);
	vscreen.height = (float) ( screen.height / ratio);
	System.out.printf( "%f, %f, %f\n", ratiox, ratioy, ratio);
	System.out.printf( "%f, %f\n",
		vscreen.width, vscreen.height);
	//set position
	vscreen.x = ( window_width - vscreen.width) / 2.0;
	vscreen.y = ( window_height - vscreen.height) / 2.0;
	//save transformations
	vmatrix.translate( vscreen.x, vscreen.y, 0);
	vmatrix.scale(
		(float) ( 1 / ratio),
		(float) ( 1 / ratio), 1);
}

void draw(){
	//update text
	text = String.format(
		"Frame: %d, %d : %d, %d\n",
		frame.getX(), frame.getY(),
		frame.getWidth(), frame.getHeight());

	//get info
	PointerInfo pinfo = MouseInfo.getPointerInfo();
	Point mouse = pinfo.getLocation();
	//update mouse text
	String mouse_text = String.format(
		"Mouse:\n%d, %d", mouse.x, mouse.y);

	pushMatrix();
	pushStyle();
	//draw background
	background( 30);

	//draw text
	fill( 140, 220, 200);
	noStroke();
	textSize( 50);
	textAlign( CENTER, CENTER);
	text( text, window_width / 2, window_height / 2);

	//apply virtual screen matrix
	applyMatrix( vmatrix);

	//draw vscreen bounds
	fill( 10, 10, 10, 100);
	noStroke();
	rect( 0, 0,
		screen.width, screen.height);

	//draw mouse
	noFill();
	stroke( 140, 220, 200, 200);
	strokeWeight( 2);
	ellipse( mouse.x, mouse.y, 35, 35);
	noStroke();
	fill( 140, 220, 200, 200);
	ellipse( mouse.x, mouse.y, 5, 5);
	textSize( 20);
	textAlign( LEFT, CENTER);
	text( mouse_text, mouse.x + 25, mouse.y);

	popStyle();
	popMatrix();
}
