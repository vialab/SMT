/**
 *  This example shows PieMenuZone functionality. It adds options
 *  to the pie menu, sometimes with an image, and uses press
 *  on both the PieMenuZone itself, and on each option.
 *  Each option when pressed will print it's name, and then be 
 *  removed, as the PieMenuZone is also pressed, and it calls
 *  remove() on the option. 
 */

import vialab.TUIOSource.*;
import vialab.SMT.*;

void setup(){
  size(800,800,P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  PieMenuZone menu = new PieMenuZone("PieMenu", 400, 400, 355);
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
  TouchClient.getZone("PieMenu",PieMenuZone.class).remove(m.getSelectedName());
}

