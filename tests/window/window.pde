//imports
import java.awt.*;


//vars
int window_width = 1280;
int window_height = 800;
Dimension screen;
String text = "asdf";
boolean blur = false;

void setup(){
	size( window_width, window_height, P3D);
	GraphicsEnvironment environment =
		GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsDevice[] devices = environment.getScreenDevices();

	System.out.printf( "Number of devices: %d\n", devices.length);

	int i = 0;
	for( GraphicsDevice device : devices){
		GraphicsConfiguration config = device.getDefaultConfiguration();
		Rectangle bounds = config.getBounds();
		System.out.printf( "Device %d: %d, %d : %d, %d\n",
			i++, bounds.x, bounds.y,
			bounds.width, bounds.height);}

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	screen = toolkit.getScreenSize();
	System.out.printf( "Screen size: %d, %d\n", screen.width, screen.height);
}

void draw(){
	//update text
	text = String.format( "Frame: %d, %d : %d, %d\n",
		frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight());
	/*Canvas canvas = g.canvas;
	text +=
		canvas == null ?
			"Canvas: null" :
			String.format( "Canvas: %d, %d : %d, %d\n",
				g.canvas.getX(), g.canvas.getY(),
				g.canvas.getWidth(), g.canvas.getHeight());*/

	//get info
	PointerInfo pinfo = MouseInfo.getPointerInfo();
	Point mouse = pinfo.getLocation();
	PVector vmouse = new PVector( mouse.x, mouse.y);
	vmouse.x *= (float) window_width / screen.width;
	vmouse.y *= (float) window_height / screen.height;
	//update mouse text
	String mouse_text = String.format(
		"Mouse:\n%d, %d", mouse.x, mouse.y);

	pushStyle();
	//draw background
	background( 30);

	//draw text
	fill( 140, 220, 200);
	textSize( 50);
	textAlign( CENTER, CENTER);
	text( text, window_width / 2, window_height / 2);

	//draw mouse
	noFill();
	stroke( 140, 220, 200, 200);
	strokeWeight( 2);
	ellipse( vmouse.x, vmouse.y, 35, 35);
	noStroke();
	fill( 140, 220, 200, 200);
	ellipse( vmouse.x, vmouse.y, 5, 5);
	textSize( 20);
	textAlign( LEFT, CENTER);
	text( mouse_text, vmouse.x + 25, vmouse.y);

	popStyle();
}
