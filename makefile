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
docscp = -classpath src:bin:lib/*
documentation = -d documentation
version = -source 1.6 -target 1.6
#warnings = -Xlint:-options
warnings = -Xlint:-deprecation -Xlint:-options

#include files
include dependencies.mk

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

export: library/SMT.jar
	zip -r SMT.zip examples library library.properties \
		referense release_notes.txt resources src

docs:
	javadoc $(docscp) $(documentation) $(source_files)
docs-test: docs
	chromium-browser documentation/index.html

git-prepare:
	git add -A
	git add -u
