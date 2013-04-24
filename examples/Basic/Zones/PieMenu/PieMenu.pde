/**
 *  This example shows PieMenuZone functionality. It adds options
 *  to the pie menu, sometimes with an image, and uses touchUp
 *  on both the PieMenuZone itself, and on each option.
 *
 *  Each option when touchUped will print it's name, and as the
 *  PieMenuZone is also touchUped, it will print the name too.
 *
 *  Remove Self is as advertised, removing itself from the menu.
 *
 *  Submenus are shown in use, created using changeRoot() to set
 *  the root for options that are add()'ed after it. Then we
 *  changeRoot(null) to get back to the top root menu.
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
  menu.setDisabled("View Source",true);
  menu.add("Remove Self");
  //menu.setDisabled("Submenu",true);
  menu.addSubmenu("Submenu","Option1");
  menu.addSubmenu("Submenu","Option2");
  menu.addSubmenu("Submenu","Option3");
}

void draw(){
  background(0);
}

void touchUpForward(){println("Forward");}
void touchUpSubmenu(){println("Submenu");}
void touchUpReload(){println("Reload");}
void touchUpViewSource(){println("View Source");}
void touchUpRemoveSelf(){println("Remove Self");TouchClient.getZone("PieMenu",PieMenuZone.class).remove("RemoveSelf");}
void touchUpPieMenu(PieMenuZone m){
  println("Selected: "+m.getSelectedName());
}

