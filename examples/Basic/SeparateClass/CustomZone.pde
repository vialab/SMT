public class CustomZone{
  void drawMyZone1(){
    fill(0);
    rect(0,0,200,200);
    fill(255);
    ellipse(100,100,100,100); 
  }
  void touchMyZone1(Zone z){
    println("MyZone1 Bound object:"+this);
    z.rst();
  }
  void drawMyZone2(){
    fill(255);
    rect(0,0,200,200);
    fill(0);
    ellipse(100,100,100,100); 
  }
  void touchMyZone2(Zone z){
    println("MyZone2 Bound object:"+this);
    z.drag();
  }
}
