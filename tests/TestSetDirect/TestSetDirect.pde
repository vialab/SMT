import vialab.SMT.*;
Zone parentZ;
Zone z;
Zone z2;
void setup() {
    size( 1280, 1024, P3D);  
    SMT.init(this, TouchSource.MOUSE);

    // Creates a zone
    //black
    z = new Zone("myZone", 50, 50, 100, 100);
    //grey
    z2 = new Zone("myZone2", 10, 10, 20, 20);
    parentZ = new Zone("parentZone", 0, 0, 200, 200);
    // Need to add the zone to the SMT
    SMT.add(parentZ);
    parentZ.add(z);
    z.add(z2);
}

void draw() { 
  background(79, 129, 189);
  int currX = 0;
    strokeWeight(5);
    stroke(255);
    while(currX < displayWidth){
      line(currX, 0, currX, displayHeight);
      currX += 200;
    }
    int currY = 0;
    while(currY < displayHeight){
      line(0, currY, displayWidth, currY);
      currY += 200;
    }
    stroke(0);
    strokeWeight(1);
}

void drawMyZone(){
  fill(0);
  rect(0,0,z.width, z.height);
  fill(255);
  text("1", 0, 0); 
}

void drawMyZone2(){
  fill(125);
  rect(0,0,z2.width, z2.height);
  fill(255);
  text("2", 0, 0); 
}

void drawParentZone(){
  noFill();
  strokeWeight(5);
  rect(0,0,parentZ.width, parentZ.height);
  fill(255);
  text("parent", 0, 0); 
}

void touchMyZone(){
  System.out.println("parent x: " + parentZ.getX() + " y: " + parentZ.getY());
  System.out.println("black zone x: " + z.getX() + " y: " + z.getY());
  System.out.println("grey zone x: " + z2.getX() + " y: " + z2.getY() + "<-- the unexpected line");
}

void touchMyZone2(){
  System.out.println("parent x: " + parentZ.getX() + " y: " + parentZ.getY());
  System.out.println("black zone x: " + z.getX() + " y: " + z.getY());
  System.out.println("grey zone x: " + z2.getX() + " y: " + z2.getY());
}

void touchParentZone(){
  parentZ.drag();
}