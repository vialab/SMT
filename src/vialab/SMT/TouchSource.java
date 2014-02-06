package vialab.SMT;

 /**
  * Used to set the input device.
  * 
  * 
  * TUIO_DEVICE - Use Pure TUIO back end, no emulation here, any TUIO packets on port 3333 will taken as multi-touch input.
  * 
  * MOUSE - Use a mouse to emulate multi-touch, left click is a touch, right click a hold, and right click the held cursor again to remove.
  * 
  * WM_TOUCH - Use Windows Touch back end, for Windows devices that support multi-touch.
  * 
  * ANDROID - Use Android back end, only available using processing for Android, which currently doesn't work.
  * 
  * SMART - Use SMART SDK back end, when running on SMART hardware, such as the SMART Table.
  * 
  * LEAP - Use Leap SDK back end, for the Leap Motion, using the finger positions.
  * 
  * MULTIPLE - Runs all back-ends that do not interfere with each other, which currently excludes SMART.
  *
  */
public enum TouchSource {
	/**
	 * Use Pure TUIO back end, no emulation here, any TUIO packets on port 3333
	 * will taken as multi-touch input
	 */
	TUIO_DEVICE,
	/**
	 * Use a mouse to emulate multi-touch, left click is a touch, right click a
	 * hold, and right click the held cursor again to remove
	 */
	MOUSE,
	/**
	 * Use Windows Touch back end, for Windows devices that support multi-touch
	 */
	WM_TOUCH,
	/**
	 * Use Android back end, only available using processing for Android, which
	 * currently doesn't work
	 */
	ANDROID,
	/**
	 * Use Smart SDK back end, when running on Smart hardware, such as the Smart
	 * Table
	 */
	SMART,
	/**
	 * Use Leap SDK back end, for the Leap Motion, using the finger positions
	 */
	LEAP,
	/**
	 * Runs all back-ends that do not interfere with each other, which currently
	 * excludes SMART
	 * @deprecated Use TouchSource.AUTOMATIC instead
	 */
	MULTIPLE,
	/**
	 * Runs all back-ends that do not interfere with each other, which currently
	 * excludes SMART
	 */
	AUTOMATIC;
}
