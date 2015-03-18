import vialab.SMT.*;

void setup(){
  // Processing and SMT setup
  size(800, 600, SMT.RENDERER);
  SMT.init(this);
  textFont(createFont(
    "Droid Sans Bold", 64));

  // add our zone to the sketch
  SMT.add(new MyZone());
}

void draw(){
  background(220);
}

class MyZone extends Zone {
  public MyZone(){
    super(300, 200, 200, 200);
  }

  public void draw(){
    fill(100, 150, 60, 200);
    ellipse(100, 100, 200, 200);
    fill(10, 220);
    textAlign(CENTER, CENTER);
    textMode(SHAPE);
    text("^_^", 100, 90);
  }
  public void pickDraw(){
    ellipse(100, 100, 200, 200);
  }

  public void touch(){
    rst();
  }
}