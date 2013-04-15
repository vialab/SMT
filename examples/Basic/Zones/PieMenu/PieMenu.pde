import vialab.TUIOSource.*;
import vialab.SMT.*;

void setup(){
  size(800,800,P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  PieMenuZone menu = new PieMenuZone("PieMenu", 400, 200, 200);
  TouchClient.add(menu);
  menu.add("Forward",loadImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRE_lpEhrobnGhxrMyF6TFLUuAcVpGJixDzak4TxVQjjiDW5UjF", "png"));
  menu.add("Submenu");
  menu.add("Reload");
  menu.add("View Source");
  menu.add("Back");
}

void draw(){
  background(125); 
}

void pressForward(){println("Forward");}
void pressSubmenu(){println("Submenu");}
void pressReload(){println("Reload");}
void pressViewSource(){println("View Source");}
void pressBack(){println("Back");}
void pressPieMenu(PieMenuZone m){
  println("Pie Menu selected: "+m.getSelectedName());
}

