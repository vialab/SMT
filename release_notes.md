SMT v3.7.2 Alpha Release Notes
==============================

#### API Additions:
 - The new, very pretty ( if I do say so myself ), textured touch draw method. It is now the default touch draw method. If you want the previous touch draw method, just call ```SMT.setTouchDraw( TouchDraw.SMOOTH);```
 - Added a number of functions for adjusting the textured touch drawer's paramters, such as colour, size, trail length, etc. Look forward to a little tutorial/example on them.
 - Added a way to more easily implement your own touch drawer - ```TouchDraw.CUSTOM```. Write your own class that implements [vialab.SMT.TouchDrawer](src/vialab/SMT/TouchDrawer.java), and call ```SMT.setTouchDraw( TouchDraw.CUSTOM, TouchDrawer drawer);``` on an instance of that class to get SMT to use it! There isn't currently any tutorial or anything, but you could maybe take a look at the [TexturedTouchDrawer class](src/vialab/SMT/TexturedTouchDrawer.java) for some guidance.
 - ```SMT.init()``` now has the overloads ```SMT.init( PApplet parent, TouchSource... sources)```, and ```SMT.init( PApplet parent, int port, TouchSource... sources)```. This allows you to better identify exactly which touch sources you want. You can always use just ```TouchSource.AUTOMATIC```, if you'd like.
 - Added ```TouchSource.AUTOMATIC``` to replace ```TouchSource.MULTIPLE```.
 - Did a lot of work on the swipe keyboard - look forward to a demo/tutorial on it. In the meantime, take a look at [examples/Demos/Keyboard](examples/Demos/Keyboard/Keyboard.pde) if you're interested.


#### API Changes:
 - ```SMT.addMethodClasses()``` and ```Zone.setBoundObject()``` have been deprecated. We strongly recommend not using them. If you're using processing alone, write your methods in your PApplet. If you're using processing with java, write a new class that extends Zone, or use an anonymous Zone class. You'll notice performance improvements when doing so.
 - Removed ```SMT.init()```'s extraClasses parameter.
 - ```TouchSource.MULTIPLE``` has been deprecated. Use ```TouchSource.AUTOMATIC``` instead.
 - Deprecated ```Zone.physics```, replace with the ```Zone.setPhysicsEnabled( boolean)``` and ```Zone.getPhysicsEnabled()``` accessor methods.

#### Bug Fixes:
 - Fixed anonymous Zone classes. ( Issue #152 )
 - Unified the data source for ```SMT.getTouchCount()``` and ```SMT.getTouches()```. They should now be consistent. ( Issue #140 )

#### Other:
 - Fixed and commented [the viewport example](examples/Advanced/ViewPort/ViewPort.pde)
 - Reworked touch source connection code.
 - Did a lot of improvement on the javadocs.
