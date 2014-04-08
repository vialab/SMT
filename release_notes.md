SMT v4.0 Release Notes
======================

#### API Additions:
 - Added ```Touch.setTint( float, float, float, float)``` and ```Touch.setTrailTint( float, float, float, float)```, to allow individual touches to override the global default colours. See [examples/Demos/TouchColours](examples/Demos/TouchColours/TouchColours.pde) for an example on how to do this. ( Issue #176 )

#### Bug Fixes:
 - SMT is now compatible with Processing 2.1.1, but not Processing 2.1 and previous. To upgrade your processing code, simply change your size call from ```size( width, height, P3D)``` to ```size( width, height, SMT.RENDERER)```. ( Issue #175 )
 - Middle-clicking a touch no longer causes a crash. ( Issue #135 )
