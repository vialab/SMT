package vialab.SMT;

import processing.core.PFont;
import processing.event.KeyEvent;

/**
 * The TextBox zone displays text inside a rectangle.
 */
public class TextBox extends Zone {
	//private fields
	private String content;
	private PFont font;
	private boolean bg_enabled;
	private boolean frame_enabled;
	private boolean autowidth_enabled;
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
		this.font = null;
		this.bg_enabled = true;
		this.frame_enabled = true;
		this.autowidth_enabled = false;}

	//zone methods
	@Override
	public void draw(){
		pushStyle();
		//set background color
		if( bg_enabled)
			fill( bg_red, bg_green, bg_blue, bg_alpha);
		else
			noFill();
		//set frame color
		if( frame_enabled)
			stroke( frame_red, frame_green, frame_blue, frame_alpha);
		else
			noStroke();
		//draw background/frame rectangle
		rect( 0, 0, width, height);
		//draw text
		fill( text_red, text_green, text_blue, text_alpha);
		textSize( 16);
		text( content, 0, 0, width, height);
		popStyle();}
	@Override
	public void pickDraw(){
		rect( 0, 0, width, height);}
	@Override
	public void touch(){
		drag();}

	//key event handling
	protected void keyPressed( KeyEvent event){
		System.out.printf( "key event: %c\n", event.getKey());}
	protected void keyReleased( KeyEvent event){
		System.out.printf( "key event: %c\n", event.getKey());}
	protected void keyTyped( KeyEvent event){
		System.out.printf( "key event: %c\n", event.getKey());}

	//accessors
	/**
	 * Set the content of the textbox
	 * @param content the desired content of the textbox
	 */
	public void setContent( String content){
		this.content = content;}
	/**
	 * Append to the content of the textbox
	 * @param append content to add to the text box
	 */
	public void appendContent( String append){
		this.content += append;}
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
	/** Sets the desired color of the text box's text.
	 * @param red The desired color's red element
	 * @param green The desired color's green element
	 * @param blue The desired color's blue element
	 * @param alpha The desired color's alpha element
	 */
	public void settextColor( float red, float green, float blue, float alpha){
		this.text_red = red;
		this.text_green = green;
		this.text_blue = blue;
		this.text_alpha = alpha;}
	/**
	 * Gets the red aspect of the color of the textbox's frame.
	 * @return The desired color's red element
	 */
	public float getFrameRed(){
		return frame_red;
	}
	/**
	 * Gets the green aspect of the color of the textbox's frame.
	 * @return The frame color's green element
	 */
	public float getFrameGreen(){
		return frame_green;
	}
	/**
	 * Gets the blue aspect of the color of the textbox's frame.
	 * @return The frame color's blue element
	 */
	public float getFrameBlue(){
		return frame_blue;
	}
	/**
	 * Gets the alpha aspect of the color of the textbox's frame.
	 * @return The frame color's alpha element
	 */
	public float getFrameAlpha(){
		return frame_alpha;
	}
	/**
	 * Gets the red aspect of the color of the textbox's background.
	 * @return The frame color's red element
	 */
	public float getBgRed(){
		return bg_red;
	}
	/**
	 * Gets the green aspect of the color of the textbox's background.
	 * @return The background color's green element
	 */
	public float getBgGreen(){
		return bg_green;
	}
	/**
	 * Gets the blue aspect of the color of the textbox's background.
	 * @return The background color's blue element
	 */
	public float getBgBlue(){
		return bg_blue;
	}
	/**
	 * Gets the alpha aspect of the color of the textbox's background.
	 * @return The background color's alpha element
	 */
	public float getBgAlpha(){
		return bg_alpha;
	}
	/**
	 * Gets the red aspect of the color of the textbox's text.
	 * @return The text color's red element
	 */
	public float getTextRed(){
		return text_red;
	}
	/**
	 * Gets the green aspect of the color of the textbox's text.
	 * @return The text color's green element
	 */
	public float getTextGreen(){
		return text_green;
	}
	/**
	 * Gets the blue aspect of the color of the textbox's text.
	 * @return The text color's blue element
	 */
	public float getTextBlue(){
		return text_blue;
	}
	/**
	 * Gets the alpha aspect of the color of the textbox's text.
	 * @return The text color's alpha element
	 */
	public float getTextAlpha(){
		return text_alpha;
	}
}
