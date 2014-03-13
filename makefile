#globals
default: build
freshen: clean build
clean: clean-specials
	rm -rf bin/*
clean-specials:
	rm -rf library/SMT.jar SMT.zip
	rm -rf javadoc/ SMT/

#variables
cp = -cp src:bin:lib/*:lib/processing/*
dest = -d bin
docscp = -classpath src:bin:lib/*:lib/processing/*
docs_dir = javadoc
documentation = -d javadoc
jarfile = library/SMT.jar
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

$(jarfile): build
	jar cf $(jarfile) -C bin vialab/
	jar uf $(jarfile) resources/
jar: $(jarfile)

$(docs_dir): $(source_files)
	rm -rf $(docs_dir)
	$(docs_dir) $(docscp) $(documentation) $(source_files)
docs: $(docs_dir)
docs-test: docs
	chromium-browser $(docs_dir)/index.html

package: build jar docs
	mkdir SMT
	cp -r examples library library.properties \
		$(docs_dir) release_notes.md readme.md src \
		SMT
	zip -r SMT.zip SMT
	rm -rf SMT

#extra commands
git-prepare:
	git add -u
	git add -A

relink:
	rm -f lib/*
	ln -s /opt/processing/core/library/ lib/processing
	ln -s ~/p/libs/libTUIO/libTUIO.jar lib/libTUIO.jar
	ln -s ~/p/libs/android/android.jar lib/android.jar
	ln -s ~/p/libs/jbox2d/jbox2d-library-2.1.2.2-jar-with-dependencies.jar lib/jbox2d.jar

#test commands
test: test-anon

# feature tests
test-android: build jar
	pshell -a tests/android
test-gets: build
	java $(cp) vialab.SMT.test.TestGets
test-keyboard: build jar
	pshell examples/Demos/Keyboard
test-touchcolours: build jar
	pshell examples/Demos/TouchColours
test-zoneaccessors: build
	java $(cp) vialab.SMT.test.TestZoneAccessors

# examples
test-tablehockey: build jar
	pshell -p examples/Demos/TableHockey

# tutorials
test-tutorial1: build jar
	pshell -p examples/Tutorial/One
test-tutorial2: build jar
	pshell -p examples/Tutorial/Two
test-tutorial3: build jar
	pshell -p examples/Tutorial/Three

# prototypes
test-ripple: build jar
	pshell tests/Ripple
test-trail: build jar
	pshell tests/Trail

# other tests
test-anon: build jar
	pshell tests/Anon
test-createshape: build jar
	pshell tests/CreateShape
test-touchsource: build jar
	pshell tests/TouchSource
test-addremove: build jar
	pshell tests/AddRemove
test-methodclasses: build jar
	java $(cp) vialab.SMT.test.TestMethodClasses
test-basic: build jar
	pshell tests/Basic
