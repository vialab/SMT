package vialab.SMT;

import processing.core.PFont;
import processing.event.KeyEvent;

/**
 * The TextBox zone displays text inside a rectangle.
 */
public class TextBox extends Zone {
	/// private fields
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
	//padding's left element
	private float padding_left = 10f;
	//padding's right element
	private float padding_right = 10f;
	//padding's top element
	private float padding_top = 10f;
	//padding's bottom element
	private float padding_bottom = 10f;

	/// constructor
	public TextBox(){
		this( new String());
	}
	public TextBox( String content){
		this( content, null);
	}
	public TextBox( String content, int width, int height){
		this( content, null, width, height);
	}
	public TextBox( String content,
			int x, int y, int width, int height){
		this( content, null, x, y, width, height);
	}
	public TextBox( String content, String name){
		this( content, name, 300, 100);
	}
	public TextBox( String content, String name, int width, int height){
		this( content, name, 0, 0, width, height);
	}
	public TextBox( String content, String name,
			int x, int y, int width, int height){
		super( name, x, y, width, height);
		this.content = content;
		this.font = null;
		this.bg_enabled = true;
		this.frame_enabled = true;
		this.autowidth_enabled = false;
	}

	/// zone methods
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
		popStyle();
	}
	@Override
	public void pickDraw(){
		rect( 0, 0, width, height);
	}
	@Override
	public void touch(){
		drag();
	}

	/// utility methods
	/**
	 * Automatically adjust width to accommodate all curent text
	 */
	public void adjustWidth(){}

	/// key event handling
	@Override
	public void keyPressed( KeyEvent event){
		System.out.printf( "key pressed: %c\n", event.getKey());
	}
	@Override
	public void keyReleased( KeyEvent event){
		System.out.printf( "key released: %c\n", event.getKey());
	}
	@Override
	public void keyTyped( KeyEvent event){
		System.out.printf( "key typed: %c\n", event.getKey());
		this.appendContent( String.valueOf( event.getKey()));
	}

	/// accessors
	/**
	 * Set whether automatic width adjustment should be enabled
	 * @param enabled whether automatic width adjustment should be enabled
	 */
	public void setAutoWidthEnabled( boolean enabled){
		this.autowidth_enabled = enabled;
	}
	/**
	 * Get whether automatic width adjustment is enabled
	 * @return whether automatic width adjustment is enabled
	 */
	public boolean getAutoWidthEnabled(){
		return this.autowidth_enabled;
	}
	/**
	 * Set the content of the textbox
	 * @param content the desired content of the textbox
	 */
	public void setContent( String content){
		this.content = content;
		if( autowidth_enabled)
			this.adjustWidth();
	}
	/**
	 * Append to the content of the textbox
	 * @param append content to add to the text box
	 */
	public void appendContent( String append){
		this.content = content.concat( append);
		if( autowidth_enabled)
			this.adjustWidth();
	}
	/**
	 * Get the content of the textbox
	 * @return the current content of the textbox
	 */
	public String getContent(){
		return this.content;
	}

	/**
	 * Sets the desired color of the text box's frame.
	 * @param red The desired color's red element
	 * @param green The desired color's green element
	 * @param blue The desired color's blue element
	 * @param alpha The desired color's alpha element
	 */
	public void setFrameColor( float red, float green, float blue, float alpha){
		this.frame_red = red;
		this.frame_green = green;
		this.frame_blue = blue;
		this.frame_alpha = alpha;
	}
	/**
	 * Sets the desired color of the text box's background.
	 * @param red The desired color's red element
	 * @param green The desired color's green element
	 * @param blue The desired color's blue element
	 * @param alpha The desired color's alpha element
	 */
	public void setBgColor( float red, float green, float blue, float alpha){
		this.bg_red = red;
		this.bg_green = green;
		this.bg_blue = blue;
		this.bg_alpha = alpha;
	}
	/**
	 * Sets the desired color of the text box's text.
	 * @param red The desired color's red element
	 * @param green The desired color's green element
	 * @param blue The desired color's blue element
	 * @param alpha The desired color's alpha element
	 */
	public void setTextColor( float red, float green, float blue, float alpha){
		this.text_red = red;
		this.text_green = green;
		this.text_blue = blue;
		this.text_alpha = alpha;
	}
	/**
	 * Sets the text padding of the text box
	 * @param left The desired padding's left component
	 * @param right The desired padding's right component
	 * @param top The desired padding's down component
	 * @param bottom The desired padding's up component
	 **/
	public void setPadding( float left, float right, float top, float bottom){
		this.padding_left = left;
		this.padding_right = right;
		this.padding_top = top;
		this.padding_bottom = bottom;
	}
	/**
	 * Sets the text padding of the text box
	 * @param left The desired padding's left component
	 **/
	public void setPaddingLeft( float padding){
		this.padding_left = padding;
	}
	/**
	 * Sets the text padding of the text box
	 * @param right The desired padding's right component
	 **/
	public void setPaddingRight( float padding){
		this.padding_right = padding;
	}
	/**
	 * Sets the text padding of the text box
	 * @param top The desired padding's top component
	 **/
	public void setPaddingTop( float padding){
		this.padding_top = padding;
	}
	/**
	 * Sets the text padding of the text box
	 * @param bottom The desired padding's bottom component
	 **/
	public void setPaddingBottom( float padding){
		this.padding_bottom = padding;
	}
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
