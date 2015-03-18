## Preparing an SMT Release
This file will guide the reader through the steps of preparing an SMT release.

### Main Steps
 - Development
 - Feature Testing
 - Platform Testing
 - Update Website
	 - Update Examples
	 - Update Tutorials
 - Update Release Notes
 - Update version variables
 - Create git tag for release
 - Create release on Github
 - Deploy website update
 - Announce on reddit, processing forums, and by email

#### Feature Testing
Run through all example and test sketches and ensure desired behavior. Don't be lazy, or the users will suffer. Be sure to test all input devices. [examples/Tests/Touch] can be useful for testing input devices.

#### Platform Testing
Be sure to at least test basic functionality on all major OSs once per minor or major release. Generally speaking, testing on all of the following should be sufficient: Windows 7 and 8, OSX, and a Linux distro. Practically speaking, there isn't significant variation between unix-like OSs. If stuff works on Mac, Linux, BSD, and other unix based OSs, it should work on the rest as well. Be sure to test both the Oracle and OpenJDK JVMs. The Oracle JVM is the more widely used, but ethically speaking the OpenJDK JVM is generally considered superior.

#### Update Website
Ensuring all the examples have been updated should be part of normal testing, but if it hasn't been done yet, do it. Also update the tutorials to make sure that new users aren't given outdated incorrect information. Update the downloads information on the home and downloads page.

#### Update Version Variables
There three variables used to track version:
 - revision :: an integer that increments by one for every release ( including pre-releases )
 - version :: a [semver](http://semver.org/) style version string
	 - pre-release versions should follow this format:
		 - `4.0 < 4.1a* < 4.1b* < 4.1`
 - git tag :: the version variable with a preceding 'v' character
	 - eg: v4.0, v4.1a2, 3.7.2b4

There are multiple places where the version variables need to be updated:
 - SMT.java
	 - `SMT.revision` should reflect the revision mentions above
	 - `SMT.version` should reflect the version mentions above
	 - `SMT.version_pretty` should be a human-readable version of `SMT.version`. eg: `"SMT 4.0"`, `"SMT 4.1 beta 3"`.
 - library.properties:
	 - version = SMT.revision
	 - prettyVersion = SMT.version
 - readme.md
	 - latest release and latest pre-release sections
 - release_notes.md
	 - the number in the title
 - makefile
	 - version variable
 - git tag
	 - The git tag should be made on the same commit used to create the distributed binaries. If a user compiled SMT themselves on the given commit, they should have identical results.
	 - Make sure to use the same git tag for the release on Github.

#### Deploy Website
Assuming the following:
 - the website content has been updated properly
 - you have smt-repo soft linked to the smt repository on your computer
 - the smt repository is at the correct git tag
 - you have data/ftp_credentials.txt

Then the following commands will cleanly generate and deploy the website
```bash
( cd smt-repo && make clean package )
make clean update deploy-vialab
```

#### Pre-Release Variations
 - No website update ( except for downloads information on home and downloads pages )
 - Create prerelease on Github ( if you feel like it )
 - Announce on reddit and by email, not processing forums 

### General Guidelines
 - Follow [semantic versioning](http://semver.org/) properly.
 - Be consistant with git tags. It's incredibly useful for backporting bug fixes.
 - See if you can get people you know who use different OSs to help you test on other platforms. It's a huge pain to install a whole new OS just for platform testing.

### Guidelines for Major Releases
Major releases are releases that either significantly break backwards compatibility ( with user's sketches ) or feature large enough API additions to warrant the major number increase. Breaking compatibility with old versions of processing does not necessarily warrant a major version increment. Users can probably be expected to read the release notes for major releases.

### Guidelines for Minor Releases
Minor releases can add features and make minor API changes, but should not break backwards compatibility if at all possible. Breaking compatibility with old versions of processing should at least warrant a minor version increment. Users can not be expected to read the relase notes for minor releases.

### Guidelines for Patch Releases
Patch releases feature only bug fixes and absolutely nothing else. If you discover major bugs during the normal development cycle, you can backport the fixes to old versions. The following command will create a new branch based of the desired old version ( 3.8 in the example ). Make sure your working directory is clean, or the checkout will fail.
```bash
# checkout v3.8 tag into new branch named v3.8-patch
git checkout v3.8 -b v3.8-patch #v3.8 is the git tag for release 3.8
```
