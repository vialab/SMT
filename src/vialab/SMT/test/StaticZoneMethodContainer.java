package vialab.SMT.test;

import vialab.SMT.*;

public class StaticZoneMethodContainer {
	public String asdf = "asdf";
	public void drawMyOtherZone( Zone zone){
		zone.fill( 100, 180, 180);
		zone.rect( 0, 0, 100, 100);
		System.out.println( asdf);
	}
	public void touchMyOtherZone( Zone zone){
		zone.rst();
		asdf += "a";
	}
}