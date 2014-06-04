#globals
default: build
freshen: clean build
clean: clean-specials
	rm -rf bin/*
clean-specials:
	rm -rf library/SMT.jar SMT.zip
	rm -rf javadoc/ SMT/

#variables
cp = src:bin:lib/*:lib/processing/*
dest = -d bin
docscp = src:lib/*:lib/processing/*
docs_dir = javadoc
jar_file = library/SMT.jar
package_file = pkg/game-$(version).tar.gz
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
docs-test: docs
	chromium $(docs_dir)/index.html

$(package_file): $(class_files) $(jar_file) $(docs_dir)
	mkdir SMT
	cp -r examples library library.properties \
		$(docs_dir) readme.md release_notes.md src tests\
		SMT
	zip -r SMT.zip SMT
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
		$(docs_dir) release_notes.md readme.md src tests \
		/mnt/stronghold/SMT
	cp SMT.zip /mnt/stronghold/

#test commands
test: test-window

# feature tests
test-keyboard: build
	pshell examples/Demos/Keyboard
test-touchcolours: build
	pshell examples/Demos/TouchColours

# examples
test-tablehockey: build
	pshell examples/Demos/TableHockey

# tutorials
test-tutorial1: build
	pshell examples/Tutorial/One
test-tutorial2: build
	pshell examples/Tutorial/Two
test-tutorial3: build
	pshell examples/Tutorial/Three
test-tutorial4: build
	pshell examples/Tutorial/Four

# prototypes
test-ripple: build
	pshell tests/ripple
test-trail: build
	pshell tests/trail
test-viewport: build
	pshell tests/viewport

# other tests
test-anon: build
	pshell tests/anon
test-basic: build
	pshell tests/basic
test-display: build
	pshell tests/display
test-methods: build
	pshell tests/methods
test-touch: build
	pshell tests/touch
