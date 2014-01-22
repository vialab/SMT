package vialab.SMT.test;

import vialab.SMT.*;

public class StaticZoneMethodContainer {
	public static void drawMyOtherZone( Zone zone){
		zone.fill( 100, 180, 180);
		zone.rect( 0, 0, 100, 100);
	}
	public static void touchMyOtherZone( Zone zone){
		zone.rst();
	}
}