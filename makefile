#globals
default: build
freshen: clean build
clean: clean-specials
	rm -rf bin/*
clean-specials:
	rm -rf library/SMT.jar SMT.zip
	rm -rf javadoc/ SMT/
freshen: clean build

#variables
cp = -cp src:bin:lib/*:lib/processing/*
dest = -d bin
docscp = -classpath src:bin:lib/*:lib/processing/*
documentation = -d javadoc
version = -source 1.6 -target 1.6
#warnings = -Xlint:-options
warnings = -Xlint:-deprecation -Xlint:-options

#include files
include dependencies.mk
include lists.mk

#compilation definitions
$(class_files): bin/%.class : src/%.java
	javac $(cp) $(dest) $(version) $(warnings) $<

#basic commands
build: $(class_files)

#extra commands
library/SMT.jar: build
	jar cmf manifest library/SMT.jar -C bin vialab/
	jar uf library/SMT.jar resources/

jar: library/SMT.jar

export: build jar docs
	mkdir SMT
	cp -r examples library library.properties \
		javadoc release_notes.md readme.md src \
		SMT
	zip -r SMT.zip SMT
	rm -rf SMT

javadoc: $(source_files)
	rm -rf javadoc
	javadoc $(docscp) $(documentation) $(source_files)
docs: javadoc
docs-test: docs
	chromium-browser javadoc/index.html

git-prepare:
	git add -A
	git add -u

relink:
	rm -f lib/*
	ln -s /opt/processing/core/library/ lib/processing
	ln -s ~/p/libs/libTUIO/libTUIO.jar lib/libTUIO.jar
	ln -s ~/p/libs/android/android.jar lib/android.jar
	ln -s ~/p/libs/jbox2d/jbox2d-library-2.1.2.2-jar-with-dependencies.jar lib/jbox2d.jar

#test commands
test: test-trail

# feature tests
test-swipekeyboard: build jar
	processing-shell tests/TestSwipeKeyboard --present
test-zoneaccessors: build
	java $(cp) vialab.SMT.test.TestZoneAccessors
test-gets: build
	java $(cp) vialab.SMT.test.TestGets

# examples
test-tablehockey: build jar
	processing-shell examples/Demos/TableHockey --present

# tutorials
test-tutorial1: build jar
	processing-shell examples/Tutorial/One --present
test-tutorial2: build jar
	processing-shell examples/Tutorial/Two --present
test-tutorial3: build jar
	processing-shell examples/Tutorial/Three --present

# prototypes
test-touch: build jar
	processing-shell tests/Touch --present
test-ripple: build jar
	processing-shell tests/Ripple
test-trail: build jar
	processing-shell tests/Trail

# other tests
test-createshape: build jar
	processing-shell tests/CreateShape
test-touchsource: build jar
	processing-shell tests/TouchSource
