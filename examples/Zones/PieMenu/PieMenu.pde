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
 *  Submenus are shown in use, created using addSubMenu(), and
 *  accessed by clicking on the parent menu item, or by moving
 *  over the edge. Going back to the parent requires moving over
 *  the centre and not releasing.
 *
 *  The Show/Hide button will toggle visibility of the PieMenu,
 *  and by doing so reset its state too.
 */

import vialab.SMT.*;

void setup(){
  size(800,800,SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  PieMenuZone menu = new PieMenuZone("PieMenu", 400, 400, 355);
  SMT.add(menu);
  menu.add("Forward",loadImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRE_lpEhrobnGhxrMyF6TFLUuAcVpGJixDzak4TxVQjjiDW5UjF", "png"));
  menu.add("Submenu");
  menu.add("Add");
  menu.add("View Source");
  menu.setDisabled("View Source",true);
  menu.add("Remove Self");
  //menu.setDisabled("Submenu",true);
  menu.addSubmenu("Submenu","Option1");
  menu.addSubmenu("Submenu","Option2");
  menu.addSubmenu("Submenu","Option3");
  ButtonZone b = new ButtonZone("ShowHide","Show / Hide Pie Menu");
  //b.deactivated = true;
  SMT.add(b);
}

void pressShowHide(){
  PieMenuZone m = SMT.get("PieMenu",PieMenuZone.class);
  m.setVisible(!m.isVisible());
}

void draw(){
  background(0);
}

void touchUpForward(){println("Forward");}
void touchUpSubmenu(){println("Submenu");}
void touchUpAdd(){println("Add");SMT.get("PieMenu",PieMenuZone.class).add("Remove Self");}
void touchUpViewSource(){println("View Source");}
void touchUpRemoveSelf(Zone z){println("Remove Self");SMT.get("PieMenu").remove(z);}
void touchUpPieMenu(PieMenuZone m){
  println("Selected: "+m.getSelectedName());
}

