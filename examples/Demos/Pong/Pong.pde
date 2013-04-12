/**
 *   A pong game using SMT, the paddles are Zones that can be moved
 */
import vialab.SMT.*;

//set some configuration constants
final boolean DRAW_TOUCH_POINTS=true;
final int NUM_ZONES=2;
final float ADD_BALL_PER_SECOND_CHANCE=0.1;

int NUM_BALLS=3;

reflectZone[] zone = new reflectZone[NUM_ZONES];
ButtonZone score;
ArrayList<Ball> balls = new ArrayList<Ball>();
int p1score=0, p2score=0;

class Ball {
  float speed;
  float x;
  float y;
  float r;
  PVector Dir;
  Ball() {
    setDefaults();
  }
  void setDefaults() {
    x=displayWidth/2+random(-100, 100);
    y=displayHeight/2+random(-100, 100);
    r=5.0;
    Dir=new PVector(random(-1, 1), 0.3*random(-1, 1));
    Dir.normalize();
    speed=10.0;
  }
  void moveBall() {
    x+=Dir.x*speed;
    y+=Dir.y*speed;
  }
  void drawBall() {
    fill(255, 0, 0);
    stroke(0);
    strokeWeight(1);
    ellipseMode(CENTER);
    ellipse(x, y, r*2, r*2);
  }
}

void setup() {
  size(displayWidth, displayHeight, P3D);
  for (int i=0; i<NUM_BALLS; i++) {
    balls.add(new Ball());
  }
  TouchClient.init(this, TouchSource.MOUSE);
  for (int i = 0; i < NUM_ZONES; i++) {
    zone[i] = new reflectZone("Paddle",(i%2)*(displayWidth-450)+200, 400, 50, 300, (i%2==0)? new PVector(1.0, 0):new PVector(-1.0, 0.0));
    TouchClient.add(zone[i]);
  }
  score= new ButtonZone("Score",displayWidth/2-50, 100, 100, 50,""+p1score+"|"+p2score,createFont("Arial-Black", 16));
  TouchClient.add(score);
}

void touchPaddle(Zone z){
  z.rst();
}

void draw() {
  background(79, 129, 189);
  for (int i=0; i<NUM_BALLS; i++) {
    Ball b=balls.get(i);
    checkBallOffdisplay(b);
    //check for zone intesection
    for (int j=0; j<NUM_ZONES; j++) {
      if (zone[j].contains(b.x, b.y)) {
        PVector oldDir=b.Dir;
        b.Dir=zone[j].reflectionDirection(b);
        b.Dir.normalize();
        //only speedup if direction changed
        if (!b.Dir.equals(oldDir)) {
          //asymptotically aproach speed of 50
          b.speed+=5.0*(1-b.speed/50);
        }
      }
    }
    b.moveBall();
    b.drawBall();
  }
  score.setText("\t"+p1score+"\t|\t"+p2score+"\t");
  //generate a new ball every once in a while, depending on the per
  //second chance, dividing by framerate to get the chance per frame
  if (random(1)<ADD_BALL_PER_SECOND_CHANCE/frameRate) {
    balls.add(new Ball());
    NUM_BALLS=balls.size();
  }
  fill(255);
  text(frameRate+" fps".getWidth()/2,10);
}

void checkBallOffdisplay(Ball b) {
  if (b.y<0||b.y>displayHeight) {
    if (b.y<0) {
      b.y=0;
      if (b.Dir.y<0) {
        b.Dir.y*=-1.0;
      }
    }
    else {
      b.y=displayHeight-1;
      if (b.Dir.y>0) {
        b.Dir.y*=-1.0;
      }
    }
  }
  if (b.x<0||b.x>displayWidth) {
    if (b.x<0) {
      p2score++;
    }
    else {
      p1score++;
    }
    //ball went past player and scored, reset the ball to default
    b.setDefaults();
  }
}

void drawPaddle(reflectZone z){
  fill(0,255,0);
  rect(0,0,z.getWidth(),z.getHeight());
  stroke(0);
  strokeWeight(5);
  if (z.normalDir.x>0) {
    line(0+z.getWidth(), 0, 0+z.getWidth(), 0+z.getHeight());
  }
  else if (z.normalDir.x<0) {
    line(0, 0,0, 0+z.getHeight());
  }
}

class reflectZone extends Zone {
  PVector normalDir=new PVector();
  reflectZone(String name, int x, int y, int.getWidth(), int.getHeight(), PVector normalDir) {
    super(name,x, y,.getWidth(),.getHeight());
    this.normalDir=normalDir;
  }
  PVector reflectionDirection(Ball b) {
    PVector newNormalDir= new PVector();
    PMatrix3D inv=this.matrix.get();
    inv.invert();
    inv.transpose();
    inv.mult(normalDir, newNormalDir);
    newNormalDir.normalize();
    b.Dir.normalize();
    //reflection equation, but transparent when normal is facing away
    PVector reflectionDir=PVector.sub(b.Dir, PVector.mult(newNormalDir, (min(0.0, 2.0*(PVector.dot(newNormalDir, b.Dir))))));
    reflectionDir.normalize();
    return reflectionDir;
  }
}

