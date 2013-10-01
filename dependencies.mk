#dependencies 
#top level package
bin/vialab/SMT/ButtonZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/ImageZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/KeyboardZone.class: \
	bin/vialab/SMT/ButtonZone.class \
	bin/vialab/SMT/TextZone.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/PGraphicsDelegate.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SliderZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SMTTouchManager.class: \
	bin/vialab/SMT/SMTTuioListener.class \
	bin/vialab/SMT/SMTUtilities.class \
	bin/vialab/SMT/SMTZonePicker.class \
	bin/vialab/SMT/TouchState.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SMTTuioListener.class: \
	bin/vialab/SMT/TouchState.class

bin/vialab/SMT/SMTUtilities.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/SMTZonePicker.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/TabZone.class: \
	bin/vialab/SMT/ButtonZone.class \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/TextZone.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/Touch.class: \
	bin/vialab/SMT/Zone.class

bin/vialab/SMT/TouchPair.class:

bin/vialab/SMT/TouchState.class:

bin/vialab/SMT/Zone.class: \
	bin/vialab/SMT/ImageZone.class \
	bin/vialab/SMT/PGraphicsDelegate.class \
	bin/vialab/SMT/SMTUtilities.class \
	bin/vialab/SMT/TouchPair.class

#variables definitions
class_files = \
	bin/vialab/SMT/ButtonZone.class \
	bin/vialab/SMT/ImageZone.class \
	bin/vialab/SMT/KeyboardZone.class \
	bin/vialab/SMT/PGraphicsDelegate.class \
	bin/vialab/SMT/SliderZone.class \
	bin/vialab/SMT/SMTTouchManager.class \
	bin/vialab/SMT/SMTTuioListener.class \
	bin/vialab/SMT/SMTUtilities.class \
	bin/vialab/SMT/SMTZonePicker.class \
	bin/vialab/SMT/TabZone.class \
	bin/vialab/SMT/TextZone.class \
	bin/vialab/SMT/Touch.class \
	bin/vialab/SMT/TouchClient.class \
	bin/vialab/SMT/TouchPair.class \
	bin/vialab/SMT/TouchState.class \
	bin/vialab/SMT/Zone.class 

source_files = \
	src/vialab/SMT/ButtonZone.java \
	src/vialab/SMT/ImageZone.java \
	src/vialab/SMT/KeyboardZone.java \
	src/vialab/SMT/PGraphicsDelegate.java \
	src/vialab/SMT/SliderZone.java \
	src/vialab/SMT/SMTTouchManager.java \
	src/vialab/SMT/SMTTuioListener.java \
	src/vialab/SMT/SMTUtilities.java \
	src/vialab/SMT/SMTZonePicker.java \
	src/vialab/SMT/TabZone.java \
	src/vialab/SMT/TextZone.java \
	src/vialab/SMT/Touch.java \
	src/vialab/SMT/TouchClient.java \
	src/vialab/SMT/TouchPair.java \
	src/vialab/SMT/TouchState.java \
	src/vialab/SMT/Zone.java 