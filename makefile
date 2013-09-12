#globals
default: build
freshen: clean build
clean: clean-specials
	rm -rf bin/*
clean-specials:
	rm -rf library/SMT.jar SMT.zip
freshen: clean build

#variables
cp = -cp src:bin:lib/*
dest = -d bin
version = -source 1.6 -target 1.6
warnings = -nowarn

#files
include dependencies.mk

#compilation definitions
$(class_files):
	javac $(cp) $(dest) $(version) $(warnings)\
		$(subst bin,src,$(subst class,java,$@))
$(class_files2):
	javac $(cp) $(dest) $(version) $(warnings)\
		$(subst bin,src,$(subst class,java,$@))

#basic commands
build: $(class_files) $(class_files2)

#extra commands
library/SMT.jar: build
	jar cmf manifest library/SMT.jar -C bin vialab/
	jar uf library/SMT.jar resources/

jar: library/SMT.jar

export: library/SMT.jar
	zip -r SMT.zip examples library library.properties \
		referense release_notes.txt resources src
docs:
	javadoc -d documentation $(source_files)

git-prepare:
	git add -A
	git add -u

#test commands
test: test-swipekeyboard

test-tablehockey: build
	processing-shell examples/Demos/TableHockey

test-swipekeyboard: build \
		bin/vialab/SMT/test/TestSwipeKeyboard.class
	java $(cp) vialab.SMT.test.TestSwipeKeyboard