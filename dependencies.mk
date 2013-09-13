#dependencies
#event package
bin/vialab/SMT/event/SwipeKeyListener.class: \
		src/vialab/SMT/event/SwipeKeyListener.java \
	bin/vialab/SMT/event/SwipeKeyEvent.class

bin/vialab/SMT/event/SwipeKeyEvent.class: \
		src/vialab/SMT/event/SwipeKeyEvent.java

bin/vialab/SMT/event/TouchEvent.class: \
		src/vialab/SMT/event/TouchEvent.java

bin/vialab/SMT/event/TouchListener.class: \
		src/vialab/SMT/event/TouchListener.java \
	bin/vialab/SMT/event/TouchEvent.class

#test package
bin/vialab/SMT/test/TestSwipeKeyboard.class: \
		src/vialab/SMT/test/TestSwipeKeyboard.java \
	bin/vialab/SMT/zone/SwipeKeyboard.class \
	bin/vialab/SMT/TouchSource.class

#zone package
bin/vialab/SMT/zone/SwipeKeyZone.class: \
		src/vialab/SMT/zone/SwipeKeyZone.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/zone/SwipeKeyboard.class: \
		src/vialab/SMT/zone/SwipeKeyboard.java \
	bin/vialab/SMT/Zone.class \
	bin/vialab/SMT/event/SwipeKeyEvent.class \
	bin/vialab/SMT/event/SwipeKeyListener.class \
	bin/vialab/SMT/zone/SwipeKeyZone.class

#top level package

bin/vialab/SMT/AndroidToTUIO.class: \
		src/vialab/SMT/AndroidToTUIO.java \
	bin/vialab/SMT/Finger.class \
	bin/vialab/SMT/Simulation.class

bin/vialab/SMT/ButtonZone.class: \
		src/vialab/SMT/ButtonZone.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/CheckBoxZone.class: \
		src/vialab/SMT/CheckBoxZone.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/ContainerZone.class: \
		src/vialab/SMT/ContainerZone.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/Finger.class: \
		src/vialab/SMT/Finger.java

bin/vialab/SMT/ImageZone.class: \
		src/vialab/SMT/ImageZone.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/KeyboardZone.class: \
		src/vialab/SMT/KeyboardZone.java \
	bin/vialab/SMT/ButtonZone.class \
	bin/vialab/SMT/TextZone.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/LeftPopUpMenuZone.class: \
		src/vialab/SMT/LeftPopUpMenuZone.java \
	bin/vialab/SMT/ButtonZone.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/MouseToTUIO.class: \
		src/vialab/SMT/MouseToTUIO.java \
	bin/vialab/SMT/Finger.class \
	bin/vialab/SMT/Simulation.class

bin/vialab/SMT/PatternUnlockZone.class: \
		src/vialab/SMT/PatternUnlockZone.java \
	bin/vialab/SMT/ImageZone.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/PGraphicsDelegate.class: \
		src/vialab/SMT/PGraphicsDelegate.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/PieMenuZone.class: \
		src/vialab/SMT/PieMenuZone.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/ShapeZone.class: \
		src/vialab/SMT/ShapeZone.java \
	bin/vialab/SMT/SMTUtilities.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/Simulation.class: \
		src/vialab/SMT/Simulation.java \
	bin/vialab/SMT/Finger.class \
	bin/vialab/SMT/MouseToTUIO.class

bin/vialab/SMT/SlideRevealZone.class: \
		src/vialab/SMT/SlideRevealZone.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SliderZone.class: \
		src/vialab/SMT/SliderZone.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SMT.class: \
		src/vialab/SMT/SMT.java \
	bin/vialab/SMT/AndroidToTUIO.class \
	bin/vialab/SMT/MouseToTUIO.class \
	bin/vialab/SMT/SMTProxyTuioListener.class \
	bin/vialab/SMT/SMTTouchManager.class \
	bin/vialab/SMT/SMTTuioListener.class \
	bin/vialab/SMT/SMTUtilities.class \
	bin/vialab/SMT/SMTZonePicker.class \
	bin/vialab/SMT/TouchDraw.class \
	bin/vialab/SMT/TouchSource.class \
	bin/vialab/SMT/TouchState.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SMTProxyTuioListener.class: \
		src/vialab/SMT/SMTProxyTuioListener.java

bin/vialab/SMT/SMTTouchManager.class: \
		src/vialab/SMT/SMTTouchManager.java \
	bin/vialab/SMT/SMTTuioListener.class \
	bin/vialab/SMT/SMTUtilities.class \
	bin/vialab/SMT/SMTZonePicker.class \
	bin/vialab/SMT/TouchState.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SMTTuioListener.class: \
		src/vialab/SMT/SMTTuioListener.java \
	bin/vialab/SMT/TouchState.class

bin/vialab/SMT/SMTUtilities.class: \
		src/vialab/SMT/SMTUtilities.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SMTZonePicker.class: \
		src/vialab/SMT/SMTZonePicker.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/TabZone.class: \
		src/vialab/SMT/TabZone.java \
	bin/vialab/SMT/ButtonZone.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/TextureZone.class: \
		src/vialab/SMT/TextureZone.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/TextZone.class: \
		src/vialab/SMT/TextZone.java \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/Touch.class: \
		src/vialab/SMT/Touch.java \
	bin/vialab/SMT/event/TouchEvent.class \
	bin/vialab/SMT/event/TouchListener.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/TouchClient.class: \
		src/vialab/SMT/TouchClient.java

bin/vialab/SMT/TouchDraw.class: \
		src/vialab/SMT/TouchDraw.java

bin/vialab/SMT/TouchPair.class: \
		src/vialab/SMT/TouchPair.java

bin/vialab/SMT/TouchSource.class: \
		src/vialab/SMT/TouchSource.java

bin/vialab/SMT/TouchState.class: \
		src/vialab/SMT/TouchState.java

bin/vialab/SMT/Zone.class: \
		src/vialab/SMT/Zone.java \
	bin/vialab/SMT/ImageZone.class \
	bin/vialab/SMT/PGraphicsDelegate.class \
	bin/vialab/SMT/SMTUtilities.class \
	bin/vialab/SMT/TouchPair.class

#variables definitions
class_files = \
	bin/vialab/SMT/AndroidToTUIO.class \
	bin/vialab/SMT/ButtonZone.class \
	bin/vialab/SMT/CheckBoxZone.class \
	bin/vialab/SMT/ContainerZone.class \
	bin/vialab/SMT/event/SwipeKeyEvent.class \
	bin/vialab/SMT/event/SwipeKeyListener.class \
	bin/vialab/SMT/event/TouchEvent.class \
	bin/vialab/SMT/event/TouchListener.class \
	bin/vialab/SMT/Finger.class \
	bin/vialab/SMT/ImageZone.class \
	bin/vialab/SMT/KeyboardZone.class \
	bin/vialab/SMT/LeftPopUpMenuZone.class \
	bin/vialab/SMT/MouseToTUIO.class \
	bin/vialab/SMT/PatternUnlockZone.class \
	bin/vialab/SMT/PGraphicsDelegate.class \
	bin/vialab/SMT/PieMenuZone.class \
	bin/vialab/SMT/ShapeZone.class \
	bin/vialab/SMT/Simulation.class \
	bin/vialab/SMT/SlideRevealZone.class \
	bin/vialab/SMT/SliderZone.class \
	bin/vialab/SMT/SMT.class \
	bin/vialab/SMT/SMTProxyTuioListener.class \
	bin/vialab/SMT/SMTTouchManager.class \
	bin/vialab/SMT/SMTTuioListener.class \
	bin/vialab/SMT/SMTUtilities.class \
	bin/vialab/SMT/SMTZonePicker.class \
	bin/vialab/SMT/TabZone.class \
	bin/vialab/SMT/test/TestSwipeKeyboard.class \
	bin/vialab/SMT/TextureZone.class \
	bin/vialab/SMT/TextZone.class \
	bin/vialab/SMT/TouchClient.class \
	bin/vialab/SMT/TouchDraw.class \
	bin/vialab/SMT/TouchPair.class \
	bin/vialab/SMT/TouchSource.class \
	bin/vialab/SMT/TouchState.class \
	bin/vialab/SMT/Zone.class \
	bin/vialab/SMT/zone/SwipeKeyboard.class \
	bin/vialab/SMT/zone/SwipeKeyZone.class