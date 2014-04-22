//imports
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;


//vars
int window_width = 1200;
int window_height = 800;
//screen info
Dimension screen = null;
Dimension screen2 = new Dimension();
String screen_text;
//frame info
JFrame jframe = null;
JRootPane rootpane = null;
//device info
int device_n = 0;
Rectangle[] device_bounds = null;
String[] device_ids = null;
Rectangle2D.Float vscreen = new Rectangle2D.Float();
PMatrix2D vmatrix = new PMatrix2D();

void setup(){
	size( window_width, window_height, P2D);
	GraphicsEnvironment environment =
		GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsDevice[] devices = environment.getScreenDevices();
	device_n = devices.length;
	//System.out.printf( "Number of devices: %d\n", device_n);

	//get window objects
	jframe = (JFrame) frame;
	rootpane = jframe.getRootPane();

	int i = 0;
	device_bounds = new Rectangle[ device_n];
	device_ids = new String[ device_n];
	for( GraphicsDevice device : devices){
		//get device info
		GraphicsConfiguration config = device.getDefaultConfiguration();
		Rectangle bounds = config.getBounds();
		device_bounds[i] = bounds;
		device_ids[i] = device.getIDstring();
		//print device info
		System.out.printf( "Device %d: %d, %d :: %d, %d\n",
			i++, bounds.x, bounds.y,
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
	screen = screen2
	screen2 = toolkit.getScreenSize();
	screen_text = String.format(
		"Total screen size: %d, %d",
		screen.width, screen.height);
	System.out.printf( "Screen size: %d, %d\n",
		screen.width, screen.height);
	System.out.printf( "Screen2 size: %d, %d\n",
		screen2.width, screen2.height);
	//scale screen size into 95% of window size
	double max_width = window_width * 0.95;
	double max_height = window_height * 0.95;
	//find best ratio
	double ratiox = screen.width / max_width;
	double ratioy = screen.height / max_height;
	double ratio = Math.max( ratiox, ratioy);
	//set dimensions
	vscreen.width = (float) ( screen.width / ratio);
	vscreen.height = (float) ( screen.height / ratio);
	//set position
	vscreen.x = ( window_width - vscreen.width) / 2.0;
	vscreen.y = ( window_height - vscreen.height) / 2.0;
	//save transformations
	vmatrix.translate( vscreen.x, vscreen.y);
	vmatrix.scale(
		(float) ( 1 / ratio),
		(float) ( 1 / ratio));
}

void draw(){
	//get current device
	GraphicsDevice current_device =
		frame.getGraphicsConfiguration().getDevice();
	String current_id = current_device.getIDstring();
	/*System.out.printf(
		"Current device id: %s", current_id);*/

	//draw background
	background( 30);

	//apply virtual screen matrix
	pushMatrix();
	applyMatrix( vmatrix);

	//draw graphics devices
	{ int i = 0;
	for( Rectangle device : device_bounds){
		String device_id = device_ids[i];
		boolean current = device_id.equals( current_id);
		//draw device rect
		if( current)
			fill( 10, 10, 10, 220);
		else
			fill( 20, 20, 20, 180);
		stroke( 180, 230, 200, 240);
		strokeWeight( 1);
		rect(
			device.x, device.y,
			device.width, device.height);
		//draw device text
		String device_text = String.format(
			"Device %d :: ID %s\n" +
				"Bounds %d, %d :: %d, %d\n" +
				"Current: %s",
			i, device_id,
			device.x, device.y,
			device.width, device.height,
			String.valueOf( current));
		fill( 180, 230, 200, 240);
		textAlign( LEFT, TOP);
		textSize( 50);
		text( device_text,
			device.x + 50, device.y + 50);
		//increment i
		i++;}}

	//draw screen bounds
	//fill( 10, 10, 10, 180);
	noFill();
	stroke( 180, 230, 200, 180);
	strokeWeight( 20);
	rect( 0, 0,
		screen.width, screen.height);

	//get window info
	int content_x = frame.getX() + rootpane.getX();
	int content_y = frame.getY() + rootpane.getY();
	int sketch_borderx = ( rootpane.getWidth() - this.width) / 2;
	int sketch_bordery = ( rootpane.getHeight() - this.height) / 2;
	int sketch_x = content_x + sketch_borderx;
	int sketch_y = content_y + sketch_bordery;
	String window_text = String.format(
		"Window: %d, %d :: %d, %d\n" +
			"Content: %d, %d :: %d, %d\n" +
			"Sketch: %d, %d :: %d, %d",
		frame.getX(), frame.getY(),
		frame.getWidth(), frame.getHeight(),
		content_x, content_y,
		rootpane.getWidth(),
		rootpane.getHeight(),
		sketch_x, sketch_y,
		this.width, this.height);
	//draw window bounds
	noFill();
	stroke( 180, 230, 200, 200);
	strokeWeight( 1);
	rect(
		frame.getX(), frame.getY(),
		frame.getWidth(), frame.getHeight());
	//draw content bounds
	rect(
		content_x, content_y,
		rootpane.getWidth(), rootpane.getHeight());
	//draw sketch bounds
	fill( 10, 10, 10, 200);
	rect(
		sketch_x, sketch_y,
		this.width, this.height);
	//draw window text
	fill( 180, 230, 200, 180);
	textAlign( CENTER, TOP);
	textSize( 60);
	text( window_text,
		content_x + rootpane.getWidth() / 2 + 20,
		content_y + 20);

	//get mouse info
	PointerInfo pinfo = MouseInfo.getPointerInfo();
	Point mouse = pinfo.getLocation();
	String mouse_text = String.format(
		"Mouse:\n%d, %d", mouse.x, mouse.y);
	//draw mouse
	// draw circle
	noFill();
	stroke( 140, 220, 200, 200);
	strokeWeight( 2);
	ellipse( mouse.x, mouse.y, 35, 35);
	// draw dot
	fill( 140, 220, 200, 200);
	noStroke();
	ellipse( mouse.x, mouse.y, 5, 5);
	// draw mouse text
	textAlign( LEFT, CENTER);
	textSize( 35);
	text( mouse_text, mouse.x + 30, mouse.y + 5);

	//undo transformation
	popMatrix();

	//draw screen info
	fill( 140, 220, 200);
	noStroke();
	textAlign( CENTER, BOTTOM);
	textSize( 25);
	text( screen_text, window_width / 2, window_height);
}
