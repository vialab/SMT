## Contributing to SMT
This file will attempt to aid the reader in getting started contributing to SMT.

### Submitting Issues
For the most part, just try to be as consise and specific as possible. Please also include the following ( when relevant ):
 - the version of processing that you're using
 - the version of smt that you're using
 - your current operating system
 - the code of the sketch that caused the issue
 - the console output
 - maybe a screenshot?

Being polite, clear, and having proper spelling and grammer would be greatly appreciated by anybody who takes a look at your issue. We'd also appreciate it if you check back every few days in case we have questions for you.

### Checking out the Project
```bash
git clone git@github.com/vialab/SMT
```

### Compilation Dependencies
 - make
 - oracle jdk or openjdk

### Compiling
Navigate to the SMT's folder in a terminal and run the following:
```bash
make build
```
This will compile SMT and create `library/SMT.jar`. Some IDEs have shorcuts you can enable for running make commands. It might be worth the reseach to check.

### Getting setup for rapid development
Symbolically linking your SMT project directory to the Processing's libary folder for it will allow you to skip copying the jar file every time you recompile. You can do this with the following command ( when run from smt's project directory ):
```bash
ln -snf $(readlink -e .) ~/sketchbook/libraries/SMT
```
On windows, you can do the same using the [MKLINK](http://ss64.com/nt/mklink.html) command.

### Packaging
Navigate to the SMT's folder in a terminal and run
```bash
make package
```
This will build SMT and create a zip file of all important files.

### Tests
A number of basic tests are located in the tests folder. Some of these are feature tests, some are just basic functionality tests. Some other tests are located in [examples/Tests] folder.

### Code Style
Generally we follow normal Java code style. See the following code snippet for examples:
```java
/**
 * Short description of functionA
 * @return description of return value
 */
public Asdf functionA(){
	//comment about what we're doing
	this.field = new Asdf();
	// comment that is related to above comment
	return this.field;
}
/**
 * Short description of functionB
 * More long text about functionB. More long text about functionB. More long text about functionB.
 *
 * @param variable description of variable
 */
public void functionB( Type variable){
	//long comment that wraps around multiple lines, but isn't manually broken cause jeez man that's such a pain to maintain
	this.other_variable =
		variable != null ?
			variable : new Type( 1, "asdf");
}
```
One thing that may not be clear from the above is that we use tabs for indentation, not spaces. In our text editor, we set the tab width to 2 characters. We use tabs so that other developers can set their editor's tab width to whatever they prefer to see.

### Commit Guidelines
 - At least get your code to compile before you commit.
 - Partition changes into multiple commits, based on logical changes.
 - Write descriptive commit messages.
 - The first line of a commit messages should be super-concise, but comprehensive, and no longer than 80 characters. You can write more on the following lines. As a general rule, if your first line is long, the commit should have been split into multiple separate commits.
 - Set the user.name and user.email fields in your git config to properly reflect your username and email on github.
 - If possible, sign your commits with a secure pgp key.

### Other
Learn how to use git, it's so worth the effort.

Sometimes you may need to regenerate `src/vialab/SMT/renderer/PGraphics3DDelegate.java`. Start by using eclipse to make a delegate for `processing.opengl.PGraphics3D`, then edit away. Using an editor that supports multiple cursors, such as Sublime Text, can make the process so much easier.

We have continuous integration set up on [drone.io](https://drone.io/github.com/vialab/SMT). We get automatic emails when commits in SMT's main branches fail to compile