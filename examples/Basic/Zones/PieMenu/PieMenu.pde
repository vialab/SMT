/**
 *  This example shows PieMenuZone functionality. It adds options
 *  to the pie menu, sometimes with an image, and uses press
 *  on both the PieMenuZone itself, and on each option.
 *
 *  Each option when pressed will print it's name, and as the
 *  PieMenuZone is also pressed, it will print the name too.
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
  menu.add("Remove Self");
  menu.changeRoot("Submenu");
  menu.add("Option1");
  menu.add("Option2");
  menu.add("Option3");
  menu.changeRoot(null);
}

void draw(){
  background(0);
}

void pressForward(){println("Forward");}
void pressSubmenu(){println("Submenu");}
void pressReload(){println("Reload");}
void pressViewSource(){println("View Source");}
void pressRemoveSelf(){println("Remove Self");TouchClient.getZone("PieMenu",PieMenuZone.class).remove("RemoveSelf");}
void pressPieMenu(PieMenuZone m){
  println("Selected: "+m.getSelectedName());
}

