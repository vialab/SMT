#globals
default: build
freshen: clean build
clean: clean-specials
	rm -rf bin/*
clean-specials:
freshen: clean build

#variables
cp = -cp src:bin:lib/*
dest = -d bin

#commands

build:
	javac $(cp) $(dest) src/vialab/SMT/*

export: jar
	zip -r SMT.zip examples library library.properties \
		referense release_notes.txt resources src

test: build
	processing-java --run --force --output=/tmp/asdf\
	 --sketch=examples/Demos/CheckersWithMenus

jar: library/SMT.jar

library/SMT.jar: build
	jar cmf manifest library/SMT.jar bin/* resources/