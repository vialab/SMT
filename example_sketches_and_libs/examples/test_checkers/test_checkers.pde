/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using simpleMultiTouch toolkit
 */
import vialab.SMT.*;
import vialab.mouseToTUIO.*;
import TUIO.*;
import processing.opengl.PGraphicsOpenGL;
import codeanticode.glgraphics.*;

//set some configuration constants
final boolean USE_MOUSE_TO_TUIO=true;
final boolean DRAW_TOUCH_POINTS=true;
final int PIECES_PER_PLAYER=12;

CheckerZone p1[]=new CheckerZone[PIECES_PER_PLAYER];
CheckerZone p2[]=new CheckerZone[PIECES_PER_PLAYER];

TouchClient client;

class CheckerZone extends Zone{
   color c;
   CheckerZone(String name, int x, int y, int w, int h,color c){
     super(name, x, y, w, h); 
     this.c=c; 
   }
}
void setup() {
  frameRate(1000);
  size(screenWidth, screenHeight, GLConstants.GLGRAPHICS);
  client = new TouchClient(this, USE_MOUSE_TO_TUIO, true);
  client.setDrawTouchPoints(DRAW_TOUCH_POINTS);
  for(int i=0; i<PIECES_PER_PLAYER; i++){
    p1[i]=new CheckerZone("Checker",0, 0, 100, 100,color(255,0,0));
    client.add(p1[i]);
    p1[i].setDirect(true);
  }
  for(int i=0; i<PIECES_PER_PLAYER; i++){
    p2[i]=new CheckerZone("Checker",0, 0, 100, 100,color(255,255,255));
    client.add(p2[i]);
    p2[i].setDirect(true);
  }
  int c1=0,c2=0;
  for(int i=0; i<8; i++){
    for(int j=0; j<8; j++){
      if((i+j)%2==1){
        if(j<3){
          p1[c1].translate(((screenWidth-1000)/2)+i*125+12,((screenHeight-1000)/2)+j*125+12);
          c1+=1;
        }
        if(j>4){
          p2[c2].translate(((screenWidth-1000)/2)+i*125+12,((screenHeight-1000)/2)+j*125+12);
          c2+=1;
        }
      }  
    }
  }
}

void draw() {
  background(79, 129, 189);
  fill(245,245,220);
  rect((screenWidth-1000)/2,(screenHeight-1000)/2,1000,1000);
  fill(150,75,0);
  for(int i=0; i<8; i++){
    for(int j=0; j<8; j++){
      if((i+j)%2==1){
        rect(((screenWidth-1000)/2)+i*125,((screenHeight-1000)/2)+j*125,125,125);
      }  
    }
  }
  fill(255);
  text(round(frameRate)+"fps, # of zones: "+client.getZones().length,width/2,10);
  client.drawPickBuffer(0,0,100,100);
}

void drawChecker(CheckerZone zone){
  fill(zone.c);
  ellipse(50,50,100,100);
}

void pickDrawChecker(CheckerZone zone){
  ellipse(50,50,100,100);
}

void touchChecker(CheckerZone zone){
  zone.drag();
}
