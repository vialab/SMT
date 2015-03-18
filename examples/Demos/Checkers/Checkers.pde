/**
 *  This is a simple checkers game, the pieces can be dragged around
 */
import vialab.SMT.*;

//set some configuration constants
final int PIECES_PER_PLAYER=12;

CheckerZone p1[]=new CheckerZone[PIECES_PER_PLAYER];
CheckerZone p2[]=new CheckerZone[PIECES_PER_PLAYER];

class CheckerZone extends Zone{
   color c;
   CheckerZone(String name, int x, int y, int w, int h,color c){
     super(name, x, y, w, h); 
     this.c=c; 
   }
}
void setup() {
  size(displayWidth, displayHeight, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  for(int i=0; i<PIECES_PER_PLAYER; i++){
    p1[i]=new CheckerZone("Checker",0, 0, 100, 100,color(255,0,0));
    SMT.add(p1[i]);
    p1[i].setDirect(true);
  }
  for(int i=0; i<PIECES_PER_PLAYER; i++){
    p2[i]=new CheckerZone("Checker",0, 0, 100, 100,color(255,255,255));
    SMT.add(p2[i]);
    p2[i].setDirect(true);
  }
  int c1=0,c2=0;
  for(int i=0; i<8; i++){
    for(int j=0; j<8; j++){
      if((i+j)%2==1){
        if(j<3){
          p1[c1].beginTouch();
          p1[c1].translate(((displayWidth-1000)/2)+i*125+12,((displayHeight-1000)/2)+j*125+12);
          p1[c1].endTouch();
          c1+=1;
        }
        if(j>4){
          p2[c2].beginTouch();
          p2[c2].translate(((displayWidth-1000)/2)+i*125+12,((displayHeight-1000)/2)+j*125+12);
          p2[c2].endTouch();
          c2+=1;
        }
      }  
    }
  }
}

void draw() {
  background(79, 129, 189);
  fill(245,245,220);
  rect(
    (displayWidth-1000)/2,
    (displayHeight-1000)/2,1000,1000);
  fill(150,75,0);
  for(int i=0; i<8; i++){
    for(int j=0; j<8; j++){
      if((i+j)%2==1){
        rect(
          ((displayWidth-1000)/2)+i*125,
          ((displayHeight-1000)/2)+j*125,125,125);
      }  
    }
  }
  fill(255);
  text(round(frameRate)+"fps, # of zones: "+SMT.getZones().length,width/2,10);
  SMT.drawPickBuffer(0,0,100,100);
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
