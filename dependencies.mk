#dependencies
#event package
bin/vialab/SMT/event/TouchEvent.class:

bin/vialab/SMT/event/TouchListener.class: \
	bin/vialab/SMT/event/TouchEvent.class

#swipekeyboard package
bin/vialab/SMT/swipekeyboard/AnchorZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/swipekeyboard/ArrowKeysLayout.class: \
	bin/vialab/SMT/swipekeyboard/SwipeKeyboardLayout.class

bin/vialab/SMT/swipekeyboard/CondensedLayout.class: \
	bin/vialab/SMT/swipekeyboard/SwipeKeyboardLayout.class

bin/vialab/SMT/swipekeyboard/DefaultSwipeResolver.class: \
	bin/vialab/SMT/swipekeyboard/SwipeResolver.class

bin/vialab/SMT/swipekeyboard/ExtendedLayout.class: \
	bin/vialab/SMT/swipekeyboard/SwipeKeyboardLayout.class

bin/vialab/SMT/swipekeyboard/KeyZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/swipekeyboard/ModifierKeyZone.class: \
	bin/vialab/SMT/swipekeyboard/KeyZone.class

bin/vialab/SMT/swipekeyboard/SwipeKeyboardLayout.class: \

bin/vialab/SMT/swipekeyboard/SwipeKeyEvent.class:

bin/vialab/SMT/swipekeyboard/SwipeKeyListener.class: \
	bin/vialab/SMT/swipekeyboard/SwipeKeyEvent.class

bin/vialab/SMT/swipekeyboard/SwipeKeyZone.class: \
	bin/vialab/SMT/swipekeyboard/KeyZone.class

bin/vialab/SMT/swipekeyboard/SwipeResolver.class:

#util package
bin/vialab/SMT/util/ActiveDisplayTouchBinder.class: \
	bin/vialab/SMT/util/SystemAdapter.class \
	bin/vialab/SMT/util/TouchBinder.class \

bin/vialab/SMT/util/DisplayTouchBinder.class: \
	bin/vialab/SMT/util/SystemAdapter.class \
	bin/vialab/SMT/util/TouchBinder.class \

bin/vialab/SMT/util/RectTouchBinder.class: \
	bin/vialab/SMT/util/SystemAdapter.class \
	bin/vialab/SMT/util/TouchBinder.class \

bin/vialab/SMT/util/ScreenTouchBinder.class: \
	bin/vialab/SMT/util/SystemAdapter.class \
	bin/vialab/SMT/util/TouchBinder.class \

bin/vialab/SMT/util/SketchTouchBinder.class: \
	bin/vialab/SMT/util/SystemAdapter.class \
	bin/vialab/SMT/util/TouchBinder.class \

bin/vialab/SMT/util/SystemAdapter.class: \

bin/vialab/SMT/util/TouchBinder.class: \

bin/vialab/SMT/util/ZonePicker.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SMTProxyTuioListener.class: \


#top level package
bin/vialab/SMT/AndroidToTUIO.class: \
	bin/vialab/SMT/Finger.class \
	bin/vialab/SMT/Simulation.class

bin/vialab/SMT/ButtonZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/CheckBoxZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/ContainerZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/Finger.class:

bin/vialab/SMT/ImageZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/KeyboardZone.class: \
	bin/vialab/SMT/ButtonZone.class \
	bin/vialab/SMT/TextZone.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/LeftPopUpMenuZone.class: \
	bin/vialab/SMT/ButtonZone.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/MainZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/MouseToTUIO.class: \
	bin/vialab/SMT/Finger.class \
	bin/vialab/SMT/Simulation.class

bin/vialab/SMT/PatternUnlockZone.class: \
	bin/vialab/SMT/ImageZone.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/PGraphicsDelegate.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/PieMenuZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/ShapeZone.class: \
	bin/vialab/SMT/SMTUtilities.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/Simulation.class: \
	bin/vialab/SMT/Finger.class

bin/vialab/SMT/SlideRevealZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SliderZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SMT.class: \
	bin/vialab/SMT/AndroidToTUIO.class \
	bin/vialab/SMT/MainZone.class \
	bin/vialab/SMT/MouseToTUIO.class \
	bin/vialab/SMT/SMTTouchManager.class \
	bin/vialab/SMT/SMTTuioListener.class \
	bin/vialab/SMT/SMTUtilities.class \
	bin/vialab/SMT/TexturedTouchDrawer.class \
	bin/vialab/SMT/TouchDraw.class \
	bin/vialab/SMT/TouchSource.class \
	bin/vialab/SMT/TouchState.class \
	bin/vialab/SMT/util/ActiveDisplayTouchBinder.class \
	bin/vialab/SMT/util/DisplayTouchBinder.class \
	bin/vialab/SMT/util/ProxyTuioListener.class \
	bin/vialab/SMT/util/RectTouchBinder.class \
	bin/vialab/SMT/util/ScreenTouchBinder.class \
	bin/vialab/SMT/util/SketchTouchBinder.class \
	bin/vialab/SMT/util/TouchBinder.class \
	bin/vialab/SMT/util/ZonePicker.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SMTTouchManager.class: \
	bin/vialab/SMT/SMTTuioListener.class \
	bin/vialab/SMT/SMTUtilities.class \
	bin/vialab/SMT/util/ZonePicker.class \
	bin/vialab/SMT/TouchState.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SMTTuioListener.class: \
	bin/vialab/SMT/TouchState.class

bin/vialab/SMT/SMTUtilities.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SwipeKeyboard.class: \
	bin/vialab/SMT/swipekeyboard/AnchorZone.class \
	bin/vialab/SMT/swipekeyboard/ArrowKeysLayout.class \
	bin/vialab/SMT/swipekeyboard/CondensedLayout.class \
	bin/vialab/SMT/swipekeyboard/DefaultSwipeResolver.class \
	bin/vialab/SMT/swipekeyboard/ExtendedLayout.class \
	bin/vialab/SMT/swipekeyboard/KeyZone.class \
	bin/vialab/SMT/swipekeyboard/SwipeKeyboardLayout.class \
	bin/vialab/SMT/swipekeyboard/SwipeKeyEvent.class \
	bin/vialab/SMT/swipekeyboard/SwipeKeyListener.class \
	bin/vialab/SMT/swipekeyboard/SwipeKeyZone.class \
	bin/vialab/SMT/swipekeyboard/SwipeResolver.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/TabZone.class: \
	bin/vialab/SMT/ButtonZone.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/TextureZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/TexturedTouchDrawer.class: \
	bin/vialab/SMT/TouchDrawer.class

bin/vialab/SMT/TextZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/Touch.class: \
	bin/vialab/SMT/event/TouchEvent.class \
	bin/vialab/SMT/event/TouchListener.class \
	bin/vialab/SMT/renderer/PGraphics3DDelegate.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/TouchClient.class:

bin/vialab/SMT/TouchDraw.class:

bin/vialab/SMT/TouchDrawer.class:

bin/vialab/SMT/TouchPair.class:

bin/vialab/SMT/TouchSource.class:

bin/vialab/SMT/TouchState.class:

bin/vialab/SMT/ViewportZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/Zone.class: \
	bin/vialab/SMT/renderer/PGraphics3DDelegate.class \
	bin/vialab/SMT/SMTUtilities.class \
	bin/vialab/SMT/TouchPair.class
