SMT v4.1 Release Notes
======================

#### API Additions:
 - You can now use SMT with non-fullscreen windows. This feature is enabled by default. For more info, have a look at the [Customizing Touch Source Bounds](http://vialab.science.uoit.ca/smt/tutorial/touchsourcebounds.php) tutorial.
 - There is now a new zone - `ViewportZone`. For details on how to use it, have a look at the [Working with Viewports](http://vialab.science.uoit.ca/smt/tutorial/viewports.php) tutorial.
 - Added `Zone.setResolution( Dimension)`, which allows you to adjust the resolution of the graphics context used by an indirect zone.
 - Added `Zone.refreshResolution()`, which automatically adjusts the resolution of the graphics context of an indirect zone to match the size of the zone on the screen.
 - Added `ViewportZone.resetView()`, which resets the transformation matrix of the `ViewportZone`'s container.
 - There are a number of new accessor functions in Touch.
 - Added a number of useful functions to the `SystemAdapter` class.
 - Implemented `Zone.equals( Object)` and `Zone.hashCode()`. (fixed #200)
 - Added `Zone.setVisible( boolean)`, `Zone.setPickable( boolean)`, and `Zone.setTouchable( boolean)`. Their behavior is subject to future change. For now, they toggle drawing, pick drawing, and touching for zones ( and their children ).
 - Added `SMT.revision`, `SMT.version`, and `SMT.version_pretty`, `SMT.getRevision()`, `SMT.getVersion()`, and `SMT.getPrettyVersion()`. See issue #203 for more information.

#### API Changes: ####
 - `Touch` no longer extends `TuioCursor`
 - Renamed `Zone.(get|set)CaptureTouchesEnabled( boolean)` to `Zone.(get|set)CaptureTouches( boolean)`.
 - `SMTProxyTuioListener` was reworked and renamed to `vialab.SMT.util.ProxyTuioListener`.

#### Bug Fixes:
 - `Zone.drag()` broke during this release, then was fixed.
 - Eliminated a z-fighting issue that was occurring during zone rotations/scales.
 - Fixed a null pointer exception in `SMT.setTouchSourceBoundsWhatever()`.
 - `DefaultSwipeResolver` now loads its wordlist from the SMT jar, making it properly cross platform.
 - Fixed some bugs with `SwipeKeyboard`.
 - Fixed an issue with `Zone.remove()`. (issue #200)

#### Other: ####
 - Added a [contributing guide](https://github.com/vialab/SMT/blob/master/contributing.md)
 - Set up continuous integration at [drone.io](https://drone.io/github.com/vialab/SMT)
 - A ton of javadoc comments were added, edited, and/or fixed.
 - A lot of tutorial content has been added to [our website](https://vialab.science.uoit.ca/smt/tutorial.php).
 - Added a display/touch source binding test
 - Updated some examples
 - Updated some demos
 - Added a nice node graph visualization demo
