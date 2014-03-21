package vialab.SMT;

public class MainZone extends Zone{
	MainZone( int x, int y, int w, int h){
		super(x, y, w, h);
		this.setDirect(true);
	}
	public void drawImpl(){}
	public void pickDrawImpl(){}
	public void touchImpl(){}
}