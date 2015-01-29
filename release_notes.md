SMT v4.2 Release Notes
======================

#### API Additions:
 - Added the `SMT.addZone` static methods, which are shortcuts for `SMT.add( new Zone())`. There's an overload for `SMT.addZone` for each of `Zone`'s constructors.
 - Added `Zone.getVisible()`, `Zone.getPickable()`, and `Zone.getTouchable()` (Issue #210)
 - Added an experimental replacement for `TextZone` called `TextBox`. It still needs a bit of work, but should be perfectly usable in its current state.

#### API Changes: ####
 - Undeprecated the `Zone` key event functions.

#### Bug Fixes:
None

#### Other: ####
 - Added two new examples that demonstrate the difference between the object-oriented and procedural styles of using SMT.
