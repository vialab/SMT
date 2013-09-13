#!/bin/bash

keywords="\
	AndroidToTUIO \
	ButtonZone \
	CheckBoxZone \
	ContainerZone \
	SwipeKeyEvent \
	SwipeKeyListener \
	Finger \
	ImageZone \
	KeyboardZone \
	LeftPopUpMenuZone \
	MouseToTUIO \
	PatternUnlockZone \
	PGraphicsDelegate \
	PieMenuZone \
	ShapeZone \
	Simulation \
	SlideRevealZone \
	SliderZone \
	SMTProxyTuioListener \
	SMTTouchManager \
	SMTTuioListener \
	SMTUtilities \
	SMTZonePicker \
	TabZone \
	TestSwipeKeyboard \
	TextureZone \
	TextZone \
	TouchClient \
	TouchDraw \
	TouchPair \
	TouchSource \
	TouchState \
	Zone \
	SwipeKeyboard \
	SwipeKeyZone"

for file in src/vialab/SMT/*.java src/vialab/SMT/*/*.java
do
	echo $file:
	for term in $keywords
	do
		if grep -q $term $file; then
			echo "	$term"
		fi
	done
done
