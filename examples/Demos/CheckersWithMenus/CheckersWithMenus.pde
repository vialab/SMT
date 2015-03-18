/**
 *  This example incorporates the "Checkers" example and adds
 *  some menus with buttons.
 *  It also has some other Zones that really should not be there
 *  but is good for testing that the toolkit works.
 */
import vialab.SMT.*;

//set some configuration constants
final int PIECES_PER_PLAYER=12;
String t="";

CheckerZone p1[]=new CheckerZone[PIECES_PER_PLAYER];
CheckerZone p2[]=new CheckerZone[PIECES_PER_PLAYER];
CheckerZone c1;
CheckerZone c2;
Zone board;
Zone title;
Zone options;
SliderZone s[]=new SliderZone[6];
PFont largeFont = createFont("Arial",100);

class CheckerZone extends Zone{
	 color c;
	 CheckerZone(String name, int x, int y, int w, int h,color c){
		 super(name, x, y, w, h); 
		 this.c=c; 
	 }
	 protected void drawImpl(){
		 fill(c);
		 ellipse(50,50,100,100);
	 }
	 protected void touchImpl(){
		 if(name=="Checker"){
			 SMT.putZoneOnTop(this);
			 rst();
		 }else{
			 unassignAll(); 
		 }
	 }
}

void setup() {
	size(displayWidth, displayHeight, SMT.RENDERER);
	SMT.init(this, TouchSource.AUTOMATIC);
	
	//call this to not display warnings for unimplemented methods
	//client.setWarnUnimplemented(false);
	
	title = new ContainerZone("Title",0,0,displayWidth,displayHeight);
	board = new ContainerZone("Board",(displayWidth-1000)/2,(displayHeight-1000)/2,1000,1000);
	options = new ContainerZone("Options",0,0,displayWidth,displayHeight);
	SMT.add(title);
	title.add(new ButtonZone("PlayButton",displayWidth/2-100,displayHeight/2-100,200,100,"Play",20));
	title.add(new ButtonZone("OptionsButton",displayWidth/2-100,displayHeight/2+100,200,100,"Options",20));
	title.add(new ButtonZone("ExitButton",displayWidth/2-100,displayHeight/2+300,200,100,"Exit",20));
	board.add(new ButtonZone("ToTitleButton",board.width+100,200,200,100,"Back to Title display",20));
	options.add(new ButtonZone("ToTitleButton",displayWidth/2-100,displayHeight/2+200,200,100,"Back to Title display",20));
	c1 = new CheckerZone("CheckerExample1",displayWidth/2-450, displayHeight/2-150, 100, 100,color(255,0,0));
	c2 = new CheckerZone("CheckerExample2",displayWidth/2+250, displayHeight/2-150, 100, 100,color(255,255,255));
	options.add(c1);
	options.add(c2);
	options.add(new ButtonZone("ChangeC1",displayWidth/2-450,displayHeight/2,200,50,"Randomize Top\n Player Color",20));
	options.add(new ButtonZone("ChangeC2",displayWidth/2+250,displayHeight/2,200,50,"Randomize Bottom\n Player Color",20));
	
	s[0]=new SliderZone("Slider",displayWidth/2-450,displayHeight/2+100,200,50,0,255);
	s[1]=new SliderZone("Slider",displayWidth/2-450,displayHeight/2+200,200,50,0,255);
	s[2]=new SliderZone("Slider",displayWidth/2-450,displayHeight/2+300,200,50,0,255);
	s[3]=new SliderZone("Slider",displayWidth/2+250,displayHeight/2+100,200,50,0,255);
	s[4]=new SliderZone("Slider",displayWidth/2+250,displayHeight/2+200,200,50,0,255);
	s[5]=new SliderZone("Slider",displayWidth/2+250,displayHeight/2+300,200,50,0,255);
	s[0].setCurrentValue(255);
	s[1].setCurrentValue(0);
	s[2].setCurrentValue(0);
	s[3].setCurrentValue(255);
	s[4].setCurrentValue(255);
	s[5].setCurrentValue(255);
	
	for(int i=0; i<6; i++){
		options.add(s[i]);
	}
	
	for(int i=0; i<PIECES_PER_PLAYER; i++){
		p1[i]=new CheckerZone("Checker",0, 0, 100, 100,color(255,0,0));
		board.add(p1[i]);
		p1[i].setDirect(true);
	}
	for(int i=0; i<PIECES_PER_PLAYER; i++){
		p2[i]=new CheckerZone("Checker",0, 0, 100, 100,color(255,255,255));
		board.add(p2[i]);
		p2[i].setDirect(true);
	}
	placePieces();
}

void draw() {
	background(79, 129, 189);
	fill(255);
	text(round(frameRate)+"fps, # of zones: "+SMT.getZones().length, width/2,10);
	text("typed text: "+t,width/10,50);
	c1.c=color(s[0].getCurrentValue(),s[1].getCurrentValue(),s[2].getCurrentValue());
	c2.c=color(s[3].getCurrentValue(),s[4].getCurrentValue(),s[5].getCurrentValue());
	for(int i=0; i<PIECES_PER_PLAYER; i++){
		p1[i].c=c1.c;
		p2[i].c=c2.c;
	}
}

void drawBoard(Zone zone){
	fill(245,245,220);
	rect(0,0,zone.width,zone.height);
	fill(150,75,0);
	for(int i=0; i<8; i++){
		for(int j=0; j<8; j++){
			if((i+j)%2==1){
				rect(i*125,j*125,125,125);
			}  
		}
	} 
}

void drawTitle(){
	background(100,200,150);
	fill(255);
	textFont(largeFont);
	textSize(100);
	text("Checkers",displayWidth/2-textWidth("Checkers")/2,displayHeight/2.8);
	textSize(16);
	text(round(frameRate)+"fps, # of zones: "+SMT.getZones().length,width/2,20);
	text("typed text: "+t,width/2,50);
}

void drawOptions(Zone zone){
	background(200,100,55);
	fill(255);
	textFont(largeFont);
	textSize(100);
	text("Options",displayWidth/2-200,displayHeight/3);
}

void keyTyped(){
	t+=key;
}

void placePieces(){
	int c1=0,c2=0;
	for(int i=0; i<8; i++){
		for(int j=0; j<8; j++){
			if((i+j)%2==1){
				if(j<3){
					p1[c1].translate(i*125+12,j*125+12);
					c1+=1;
				}
				if(j>4){
					p2[c2].translate(i*125+12,j*125+12);
					c2+=1;
				}
			}  
		}
	} 
}

void pressPlayButton(Zone zone){
	SMT.remove(title);
	SMT.add(board);
}

void pressOptionsButton(Zone zone){
	SMT.remove(title);
	SMT.add(options);
}

void pressExitButton(Zone zone){
	exit();
}

void pressToTitleButton(Zone zone){
	if(zone == board){
		SMT.remove(board);
	}
	else if(zone == options){
		SMT.remove(options);
	}
	SMT.add(title);
}

void pressChangeC1(Zone zone){
	c1.c=color(255*(int)random(2),255*(int)random(2),255*(int)random(2));
	s[0].setCurrentValue((int)red(c1.c));
	s[1].setCurrentValue((int)green(c1.c));
	s[2].setCurrentValue((int)blue(c1.c));
}

void pressChangeC2(Zone zone){
	c2.c=color(255*(int)random(2),255*(int)random(2),255*(int)random(2));
	s[3].setCurrentValue((int)red(c2.c));
	s[4].setCurrentValue((int)green(c2.c));
	s[5].setCurrentValue((int)blue(c2.c));
}
