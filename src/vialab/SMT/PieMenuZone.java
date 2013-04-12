//Original Pie Menu Implementation by amnon.owed : https://forum.processing.org/topic/pie-menu-in-processing#25080000002077693

package vialab.SMT;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class PieMenuZone extends Zone {
	
	private class Slice extends Zone{
		private String text;
		private PImage image;
		private String name;

		Slice(String text, PImage image, String name){
			super(name);
			this.text = text;
			this.image = image;
			this.name = name;
		}
		
		boolean warnPress(){return true;}
		boolean warnDraw(){return false;}
		boolean warnTouch(){return false;}
	}
	
	private ArrayList<Slice> sliceList = new ArrayList<Slice>();
	
	private int diameter;
	
	private int selected = -1;

	public PieMenuZone(String name, int diameter , int x, int y) {
		super(name, x, y, diameter, diameter);
		this.diameter = diameter;
	}
	
	public void add(String textName){
		add(textName, (PImage) null);
	}
	
	public void add(String text, String name){
		add(text, null, name);
	}
	
	public void add(String textName, PImage image){
		add(textName, image, textName.replaceAll("\\s", ""));
	}
	
	public void add(String text, PImage image, String name){
		this.sliceList.add(new Slice(text, image, name));
	}
	
	public void remove(String textName){
		for(Slice s : sliceList){
			if(s.name.equalsIgnoreCase(textName.replaceAll("\\s", ""))){
				this.sliceList.remove(s);
			}
		}
	}
	
	public int getDiameter(){
		return (int) (2*PVector.sub(getOrigin(),getCentre()).mag());
	}
	 
	protected void drawImpl() {
		textAlign(CENTER, CENTER);
		noStroke();
		smooth();
		float textdiam = diameter/2.75f;
		
		background(255);
		
		fill(125);
		ellipse(width/2, height/2, diameter+3, diameter+3);
		
		float op = sliceList.size()/TWO_PI;
		
		for (int i=0; i<sliceList.size(); i++) {
			float s = i/op-PI*0.125f;
			float e = (i+0.98f)/op-PI*0.125f;
			if (selected == i) {
				fill(0, 0, 255);
			} else {
				fill(255);
			}
			arc(width/2, height/2, diameter, diameter, s, e);
		}
		
		fill(0);
		for (int i=0; i<sliceList.size(); i++) {
			float m = i/op;
			text(sliceList.get(i).text, width/2+PApplet.cos(m)*textdiam, height/2+PApplet.sin(m)*textdiam);
		}
		
		fill(125);
		ellipse(width/2, height/2, 50, 50);
	}
	
	@Override
	protected void touchImpl(){
		Touch t = getActiveTouch(0);
		if(t != null){
			PVector touchInZone = toZoneVector(new PVector(t.x, t.y));
			float mouseTheta = PApplet.atan2(touchInZone.y-height/2, touchInZone.x-width/2);
			float piTheta = mouseTheta>=0?mouseTheta:mouseTheta+TWO_PI;
			float op = sliceList.size()/TWO_PI;
			
			selected = -1;
			for (int i=0; i<sliceList.size(); i++) {
				float s = i/op-PI*0.125f;
				float e = (i+0.98f)/op-PI*0.125f;
				if (piTheta>= s && piTheta <= e) {
					selected = i;
				}
			}
		}
	}
	
	@Override
	protected void touchUpImpl(Touch t){
		sliceList.get(selected).pressInvoker();
		selected = -1;
	}
}
