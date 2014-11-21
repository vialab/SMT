package vialab.SMT;

import processing.core.PFont;
import processing.event.KeyEvent;

/**
 * TextBox displays text which is selectable by touch. Each word is
 * independently highlighted when touched. The highlighting is toggled 
 * whenever a TouchDown occurs on the word
 */
public class TextBox extends Zone {
	//private fields
	private String content;
	private PFont font;
	private boolean bg_enabled;
	private boolean frame_enabled;
	//background's red element
	private float bg_red = 030f;
	//background's green element
	private float bg_green = 030f;
	//background's blue element
	private float bg_blue = 030f;
	//background's alpha element
	private float bg_alpha = 200f;
	//frame's red element
	private float frame_red = 255f;
	//frame's green element
	private float frame_green = 255f;
	//frame's blue element
	private float frame_blue = 255f;
	//text's alpha element
	private float frame_alpha = 200f;
	//text's red element
	private float text_red = 255f;
	//text's green element
	private float text_green = 255f;
	//text's blue element
	private float text_blue = 255f;
	//text's alpha element
	private float text_alpha = 200f;

	//constructor
	public TextBox( String content){
		super();
		this.content = content;
		this.font = null;}

	//accessors
	/**
	 * Set the content of the text box
	 * @param content [description]
	 */
	public void setContent( String content){
		this.content = content;}
	public String getContent(){
		return this.content;}

	/** Sets the desired color of the text box's frame.
	 * @param red The desired color's red element
	 * @param green The desired color's green element
	 * @param blue The desired color's blue element
	 * @param alpha The desired color's alpha element
	 */
	public void setFrameColor( float red, float green, float blue, float alpha){
		this.frame_red = red;
		this.frame_green = green;
		this.frame_blue = blue;
		this.frame_alpha = alpha;}
	/** Sets the desired color of the text box's background.
	 * @param red The desired color's red element
	 * @param green The desired color's green element
	 * @param blue The desired color's blue element
	 * @param alpha The desired color's alpha element
	 */
	public void setBgColor( float red, float green, float blue, float alpha){
		this.bg_red = red;
		this.bg_green = green;
		this.bg_blue = blue;
		this.bg_alpha = alpha;}
	/** Gets the red aspect of the color of the textbox's frame.
	 * @return The desired tint's red element
	 */
	public float getFrameRed(){
		return frame_red;
	}
	/** Gets the green aspect of the color of the textbox's frame.
	 * @return The desired tint's green element
	 */
	public float getFrameGreen(){
		return frame_green;
	}
	/** Gets the blue aspect of the color of the textbox's frame.
	 * @return The desired tint's blue element
	 */
	public float getFrameBlue(){
		return frame_blue;
	}
	/** Gets the alpha aspect of the color of the textbox's frame.
	 * @return The desired tint's alpha element
	 */
	public float getFrameAlpha(){
		return frame_alpha;
	}
	/** Gets the red aspect of the color of the textbox's background.
	 * @return The desired tint's red element
	 */
	public float getBgRed(){
		return bg_red;
	}
	/** Gets the green aspect of the color of the textbox's background.
	 * @return The desired tint's green element
	 */
	public float getBgGreen(){
		return bg_green;
	}
	/** Gets the blue aspect of the color of the textbox's background.
	 * @return The desired tint's blue element
	 */
	public float getBgBlue(){
		return bg_blue;
	}
	/** Gets the alpha aspect of the color of the textbox's background.
	 * @return The desired tint's alpha element
	 */
	public float getBgAlpha(){
		return bg_alpha;
	}

	@Override
	public void draw(){
		//set background
		if( bg_enabled)
			fill( bg_red, bg_green, bg_blue, bg_alpha);
		else
			noFill();
		//set frame
		if( frame_enabled)
			stroke( frame_red, frame_green, frame_blue, frame_alpha);
		else
			noStroke();
		rect( 0, 0, width, height);
		fill( text_red, text_green, text_blue, text_alpha);
		textSize( 16);
		text( content, 0, 0, width, height);}
	@Override
	public void pickDraw(){
		rect( 0, 0, width, height);}
	@Override
	public void touch(){}

	//key event handling
	protected void keyPressed( KeyEvent event){}
	protected void keyReleased( KeyEvent event){}
	protected void keyTyped( KeyEvent event){}
}
