SMT v4.1 Release Notes
======================

#### API Additions:
 - You can now use SMT with non-fullscreen windows. This feature is enabled by default. For more info, have a look at the [Customizing Touch Source Bounds](http://vialab.science.uoit.ca/smt/tutorial/touchsourcebounds.php) tutorial.
 - There is now a new zone - the ViewportZone. For details on how to use it, have a look at the [Working with Viewports](http://vialab.science.uoit.ca/smt/tutorial/viewports.php) tutorial.
 - There are a number of new accessor functions in Touch.

#### API Changes: ####
 - Touch no longer extends TuioCursor

#### Bug Fixes:
 - ```Zone.drag()``` broke during this release, then was fixed.

#### Other: ####
 - Added a [contributing guide](https://github.com/vialab/SMT/blob/master/contributing.md)
 - Switched from make to make + ant
 - Set up continuous integration at [drone.io](https://drone.io/github.com/vialab/SMT)
 - SMTProxyTuioListener was reworked and renamed to util.ProxyTuioListener
 - A ton of javadoc comments were added, edited, and/or fixed.
 - A lot of tutorial content has been added to [our website](https://vialab.science.uoit.ca/smt/tutorial.php).
