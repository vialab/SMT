PGraphics context;

void setup(){
	size( 800, 600, P2D);
	context = createGraphics( 400, 400, P2D);
}

void draw(){
	context.beginDraw();
	context.clear();
	//draw rect 1
	context.stroke( 0, 0, 0, 255);
	context.fill( 220, 140, 140, 255);
	context.rect( 10, 10, 100, 100);
	//draw rect 2
	context.stroke( 0, 0, 0, 255);
	context.fill( 220, 140, 220, 255);
	context.rect( 50, 20, 100, 100);
	//draw rect 3
	context.stroke( 0, 0, 0, 255);
	context.fill( 140, 220, 140, 255);
	context.rect( 20, 80, 100, 100);
	context.endDraw();

	background( 30, 30, 30);
	image( context, 200, 100, 400, 400);
	noFill();
	stroke( 255, 255, 255, 220);
	rect( 200, 100, 400, 400);

}
