import vialab.TUIOSource.*;
import vialab.SMT.*;

void setup(){
  size(800,800,P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  PieMenuZone menu = new PieMenuZone("PieMenu", 400, 100, 100);
  TouchClient.add(menu);
  menu.add("Forward");
  menu.add("Submenu");
  menu.add("Reload");
  menu.add("View Source");
  menu.add("Back");
}

void draw(){
  background(125); 
}

void pressForward(){print("Forward");}
void pressSubmenu(){print("Submenu");}
void pressReload(){print("Reload");}
void pressViewSource(){print("View Source");}
void pressBack(){print("Back");}

