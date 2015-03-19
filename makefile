#globals
default: build
freshen: clean build
clean: clean-specials
	rm -rf bin/*
clean-specials:
	rm -rf library/SMT.jar SMT*.zip
	rm -rf javadoc/ SMT/

#variables
version = 4.2
cp = src:bin:lib/*:lib/processing/*
dest = -d bin
docscp = src:lib/*:lib/processing/*
docs_dir = javadoc
jar_file = library/SMT.jar
package_file = SMT-$(version).zip
java_version = -source 1.6 -target 1.6
#warnings = -Xlint:-options
warnings = -Xlint:-deprecation -Xlint:-options

#include files
include dependencies.mk
include lists.mk

#compilation definitions
$(class_files): bin/%.class : src/%.java
	javac -cp $(cp) $(dest) $(java_version) $(warnings) $<

#basic commands
build: $(class_files) $(jar_file)

$(jar_file): $(class_files)
	jar cf $(jar_file) -C bin vialab/
	jar uf $(jar_file) resources/
jar: $(jar_file)

$(docs_dir): $(source_files)
	rm -rf $(docs_dir)
	javadoc -classpath $(docscp) -d $(docs_dir) $(source_files)
docs: $(docs_dir)

$(package_file): $(class_files) $(jar_file) $(docs_dir)
	mkdir SMT
	cp -r examples library library.properties license.txt \
		$(docs_dir) readme.md release_notes.md src \
		SMT
	zip -r $(package_file) SMT
	rm -rf SMT
package: $(package_file)

#extra commands
git-prepare:
	git add -u
	git add -A

cp-to-usb: package
	rm -rf /mnt/stronghold/SMT
	mkdir /mnt/stronghold/SMT
	cp -r examples library library.properties \
		$(docs_dir) release_notes.md readme.md src \
		/mnt/stronghold/SMT
	cp $(package_file) /mnt/stronghold/

#test commands
test: test-textbox

# demos
test-demo-bubblepop: build
	pshell examples/Demos/BubblePop/
test-demo-keyboard: build
	pshell examples/Demos/Keyboard
test-demo-login: build
	pshell examples/Demos/Login
test-demo-nodes: build
	pshell examples/Demos/Nodes
test-demo-tablehockey: build
	pshell examples/Demos/TableHockey
test-demo-touchcolours: build
	pshell examples/Demos/TouchColours

# tests
test-display: build
	pshell examples/Tests/Display
test-touch: build
	pshell examples/Tests/Touch

# tutorials
test-tutorial-1: build
	pshell examples/Tutorial/One
test-tutorial-2: build
	pshell examples/Tutorial/Two
test-tutorial-3: build
	pshell examples/Tutorial/Three
test-tutorial-4: build
	pshell examples/Tutorial/Four
test-tutorial-java: build
	pshell examples/Advanced/Java
test-tutorial-tsbounds: build
	pshell examples/Advanced/TouchSourceBounds

# prototypes
test-ripple: build
	pshell tests/ripple
test-viewport: build
	pshell tests/viewport
test-textbox: build
	pshell tests/textbox

# bug tests
test-asdf: build
	pshell tests/asdf
