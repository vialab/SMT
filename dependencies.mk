#variables definitions
source_files = src/vialab/SMT/AndroidToTUIO.java\
	src/vialab/SMT/ButtonZone.java \
	src/vialab/SMT/CheckBoxZone.java \
	src/vialab/SMT/ContainerZone.java \
	src/vialab/SMT/Finger.java \
	src/vialab/SMT/ImageZone.java \
	src/vialab/SMT/KeyboardZone.java \
	src/vialab/SMT/LeftPopUpMenuZone.java \
	src/vialab/SMT/MouseToTUIO.java \
	src/vialab/SMT/PatternUnlockZone.java \
	src/vialab/SMT/PGraphicsDelegate.java \
	src/vialab/SMT/PieMenuZone.java \
	src/vialab/SMT/ShapeZone.java \
	src/vialab/SMT/Simulation.java \
	src/vialab/SMT/SlideRevealZone.java \
	src/vialab/SMT/SliderZone.java \
	src/vialab/SMT/SMT.java \
	src/vialab/SMT/SMTProxyTuioListener.java \
	src/vialab/SMT/SMTTouchManager.java \
	src/vialab/SMT/SMTTuioListener.java \
	src/vialab/SMT/SMTUtilities.java \
	src/vialab/SMT/SMTZonePicker.java \
	src/vialab/SMT/TabZone.java \
	src/vialab/SMT/TextureZone.java \
	src/vialab/SMT/TextZone.java \
	src/vialab/SMT/TouchClient.java \
	src/vialab/SMT/TouchDraw.java \
	src/vialab/SMT/Touch.java \
	src/vialab/SMT/TouchPair.java \
	src/vialab/SMT/TouchSource.java \
	src/vialab/SMT/TouchState.java \
	src/vialab/SMT/Zone.java

class_files = \
	bin/vialab/SMT/AndroidToTUIO.class \
	bin/vialab/SMT/ButtonZone.class \
	bin/vialab/SMT/CheckBoxZone.class \
	bin/vialab/SMT/ContainerZone.class \
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
	bin/vialab/SMT/TextureZone.class \
	bin/vialab/SMT/TextZone.class \
	bin/vialab/SMT/TouchClient.class \
	bin/vialab/SMT/TouchDraw.class \
	bin/vialab/SMT/Touch.class \
	bin/vialab/SMT/TouchPair.class \
	bin/vialab/SMT/TouchSource.class \
	bin/vialab/SMT/TouchState.class \
	bin/vialab/SMT/Zone.class

#dependencies
#note: weakness in every class file depends on every java file
$(class_files): $(source_files)
