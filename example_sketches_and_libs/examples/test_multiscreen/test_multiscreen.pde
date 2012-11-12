/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using simpleMultiTouch toolkit
 */
import vialab.SMT.*;

//set some configuration constants
final boolean DRAW_TOUCH_POINTS=true;
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
TouchClient client;
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
       client.putZoneOnTop(this);
       rst();
     }else{
       unassignAll(); 
     }
   }
}

void setup() {
  size(displayWidth, displayHeight, OPENGL);
  client = new TouchClient(this, TouchSource.MOUSE);
  
  //call this to not display warnings for unimplemented methods
  //client.setWarnUnimplemented(false);
  
  client.setDrawTouchPoints(DRAW_TOUCH_POINTS);
  title = new Zone("Title",0,0,displayWidth,displayHeight);
  board = new Zone("Board",(displayWidth-1000)/2,(displayHeight-1000)/2,1000,1000);
  options = new Zone("Options",0,0,displayWidth,displayHeight);
  client.add(title);
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
  
  KeyboardZone k = new KeyboardZone("Keyboard");
  KeyboardZone k2 = new KeyboardZone(1150,0);
  TextZone t= new TextZone(300,300,200,200);
  TextZone t2= new TextZone(1350,300,200,200);
  title.add(k);
  title.add(k2);
  title.add(t);
  title.add(t2);/*
  TabZone tabs = new TabZone(true);
  title.add(tabs);
  tabs.add(k);
  tabs.add(k2);
  tabs.add(t);
  tabs.add(t2);*/
  k.addKeyListener(t);
  k2.addKeyListener(t2);
  k.setSize(800,300);
  
  //call this to show warnings for methods that seem to be meant for use by a zone, but a zone matching the name does not exist, useful for debugging
  //client.warnUncalled();
}

void touchKeyboard(Zone zone){
  zone.rst();
}

void draw() {
  background(79, 129, 189);
  fill(255);
  text(round(frameRate)+"fps, # of zones: "+client.getZones().length,width/2,10);
  text("typed text: "+t,width/10,50);
  client.drawPickBuffer(0,0,displayWidth/5,displayHeight/5);
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
  text(round(frameRate)+"fps, # of zones: "+client.getZones().length,width/2,20);
  text("typed text: "+t,width/2,50);
}

void drawOptions(Zone zone){
  background(200,100,55);
  fill(255);
  textFont(largeFont);
  textSize(100);
  text("Options",displayWidth/2-200,displayHeight/3);
}

void drawCheckerExample1(CheckerZone zone){
  zone.c=color(s[0].getCurrentValue(),s[1].getCurrentValue(),s[2].getCurrentValue());
  fill(zone.c);
  ellipse(50,50,100,100);
}

void drawCheckerExample2(CheckerZone zone){
  zone.c=color(s[3].getCurrentValue(),s[4].getCurrentValue(),s[5].getCurrentValue());
  fill(zone.c);
  ellipse(50,50,100,100);
}

void keyTyped(){
  t+=key;
}

void placePieces(){
  for(int i=0; i<PIECES_PER_PLAYER; i++){
    p1[i].c=c1.c;
    p2[i].c=c2.c;
  }
  
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
  client.remove(title);
  client.add(board);
}

void pressOptionsButton(Zone zone){
  client.remove(title);
  client.add(options);
}

void pressExitButton(Zone zone){
  exit();
}

void pressToTitleButton(Zone zone){
  client.remove(board);
  client.remove(options);
  client.add(title);
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
