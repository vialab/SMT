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

#files
include dependencies.mk

#compilation definitions
$(class_files):
	javac $(cp) $(dest) $(subst bin,src,$(subst class,java,$@))

#basic commands
build: $(class_files)

#extra commands
library/SMT.jar: build
	jar cmf manifest library/SMT.jar bin/* resources/

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
test: build
	processing-shell test/SwipeKeyboard

test-tablehockey: build
	processing-shell examples/Demos/TableHockey

test-swipekeyboard: build
	processing-shell test/SwipeKeyboard