//Based on Pie Menu Implementation by amnon.owed : https://forum.processing.org/topic/pie-menu-in-processing#25080000002077693

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
		
		boolean warnDraw(){return false;}
		boolean warnTouch(){return false;}
	}
	
	private ArrayList<Slice> sliceList = new ArrayList<Slice>();
	
	private int outerDiameter;
	
	private int innerDiameter;
	
	private int selected = -1;

	private boolean visible = true;

	public PieMenuZone(String name, int outerdiameter , int x, int y) {
		this(name, outerdiameter , 50, x, y);
	}
	
	public PieMenuZone(String name, int outerDiameter , int innerDiameter, int x, int y) {
		super(name, x-outerDiameter/2, y-outerDiameter/2, outerDiameter, outerDiameter);
		this.outerDiameter = outerDiameter;
		this.innerDiameter = innerDiameter;
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
		if(isVisible()){
			textAlign(CENTER, CENTER);
			imageMode(CENTER);
			noStroke();
			
			float textdiam = (outerDiameter + innerDiameter)/3.65f;
			
			fill(125);
			ellipse(width/2, height/2, outerDiameter+3, outerDiameter+3);
			
			float op = sliceList.size()/TWO_PI;
			for (int i=0; i<sliceList.size(); i++) {
				float s = (i-0.49f)/op;
				float e = (i+0.49f)/op;
				if (selected == i) {
					fill(0, 0, 255);
				} else {
					fill(255);
				}
				arc(width/2, height/2, outerDiameter, outerDiameter, s, e);
			}
			
			fill(0);
			for (int i=0; i<sliceList.size(); i++) {
				float m = i/op;
				int imageSize = (int) (PI*textdiam/(sliceList.size()+1));
				if(sliceList.get(i).image != null){
					image(sliceList.get(i).image, width/2+PApplet.cos(m)*textdiam, height/2+PApplet.sin(m)*textdiam, imageSize, imageSize);
				}else{
					imageSize = 0;
				}
				if(sliceList.get(i).text !=null){
					textSize(textdiam/(sliceList.size()+1+imageSize/40));
					text(sliceList.get(i).text, width/2+PApplet.cos(m)*textdiam, height/2+PApplet.sin(m)*textdiam + imageSize/2 + textAscent());
				}
			}
			
			fill(125);
			ellipse(width/2, height/2, innerDiameter, innerDiameter);
		}
	}
	
	@Override
	protected void touchImpl(){
		Touch t = getActiveTouch(0);
		if(t != null && isVisible()){
			PVector touchInZone = toZoneVector(new PVector(t.x, t.y));
			float mouseTheta = PApplet.atan2(touchInZone.y-height/2, touchInZone.x-width/2);
			float piTheta = mouseTheta>=0?mouseTheta:mouseTheta+TWO_PI;
			float op = sliceList.size()/TWO_PI;
			
			selected = -1;
			for (int i=0; i<sliceList.size(); i++) {
				float s = (i-0.5f)/op;
				float e = (i+0.5f)/op;
				if (piTheta>= s && (piTheta <= e || i == 0)) {
					selected = i;
				}
			}
		}
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}

	@Override
	protected void touchUpImpl(Touch t){
		if(selected != -1){
			sliceList.get(selected).pressInvoker();
			selected = -1;
		}
	}
	
	/**
	 * @return The name of the selected slice of the PieMenuZone, or null if none are selected
	 */
	public String getSelectedName(){
		try{
			return sliceList.get(selected).name;
		}catch(Exception e){}
		return null;
	}
}
