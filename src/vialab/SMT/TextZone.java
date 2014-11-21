package vialab.SMT;

import processing.core.PFont;
import processing.event.KeyEvent;

/**
 * TextZone displays text which is selectable by touch. Each word is
 * independently highlighted when touched. The highlighting is toggled
 * whenever a TouchDown occurs on the word
 */
public class TextZone extends Zone {

	/**
	 * Zone used to render the text in the textZone
	 */
	private class WordZone extends Zone {

		public String word;
		private boolean selected = false;

		@SuppressWarnings("unused")
		public WordZone( WordZone original) {
			super( original.x, original.y, original.width, original.height);
			this.word = original.word;
			this.setDirect(true);
			this.selected = original.selected;
		}

		public WordZone(int x, int y, int width, int height) {
			super( x, y, width, height);
			this.word = "";
			TextZone.this.add(this);
			this.setDirect(true);
		}

		@Override
		public void touchMovedImpl(Touch touch) {
			if (touch.getLastPoint() != null && !word.matches("\\s*") && selectable) {
				if (touch.getLastPoint().x < touch.x) {
					// moving right, so highlight/unblur by setting selected to
					// true
					selected = true;
				}
				else if (touch.getLastPoint().x > touch.x) {
					// moving left, so unhighlight/blur by setting selected to
					// false
					selected = false;
				}
			}
		}

		@Override
		public void drawImpl() {
			textFont(font);
			textSize(fontSize);

			if (blur) {
				if (!selected) {
					textFont(sFont);
					fill(txtRed, txtBlue, txtGreen, txtAlpha);
				} else {
					fill(selTxtRed, selTxtBlue, selTxtGreen, selTxtAlpha);
				}

				// Render a background for the text
				if (width > 0 && txtBkg) {

					if (selected) {
						fill(selBkgRed, selBkgBlue, selBkgGreen, selBkgAlpha);
					} else {
						fill(bkgRed, bkgBlue, bkgGreen, bkgAlpha);
					}
					noStroke();
					rect(0, 0, width, height);
				}


				text(this.word, 0, 0, width, height);
			}
			else {
				// Render a background for the text
				if (width > 0 && txtBkg) {
					if (selected) {
						fill(selBkgRed, selBkgBlue, selBkgGreen, selBkgAlpha);
					} else {
						fill(bkgRed, bkgBlue, bkgGreen, bkgAlpha);
					}
					noStroke();
					rect(0, 0, width, height);
				}

				//Set the color of the text
				if (selected) {
					fill(selTxtRed, selTxtBlue, selTxtGreen, selTxtAlpha);
				} else {
					fill(txtRed, txtBlue, txtGreen, txtAlpha);
				}

				text(this.word, 0, 0, width, height);
			}
		}

	}

	/** Sets if the text in the TextZone is selectable/highlightable */
	private boolean selectable = false;

	/** Zone used to render the text in the textZone */
	private WordZone currentWordZone;

	/** Font used for rendering the text */
	private PFont font;

	/** Font used for rendering the blurred text*/
	private PFont sFont;

	/** Bluring Flag */
	private boolean blur = false;

	/** Set the TextZone to receive input from the PApplet (keyboard)*/
	private boolean keysFromApplet = false;

	/** Font size used to render text*/
	private float fontSize = 16;

	/** Text background color */
	int bkgRed = 255, bkgGreen = 255, bkgBlue = 255, bkgAlpha = 255;

	/** Text color */
	int txtRed = 0, txtGreen = 0, txtBlue = 0, txtAlpha = 255;

	/** Color of text background when it is selected */
	int selBkgRed = 40, selBkgGreen = 80, selBkgBlue = 180, selBkgAlpha = 127;

	/** Color of the txt when it is selected */
	int selTxtRed = 255, selTxtGreen = 255,  selTxtBlue= 255, selTxtAlpha = 255;

	/** Text background color flag */
	boolean txtBkg = false;

	/**
	 * Set the text background color
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setBkgColor(int r, int g, int b, int a){
		bkgRed = r;
		bkgGreen = g;
		bkgBlue = b;
		bkgAlpha = a;
	}

	/**
	 * Set the text color
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setTextColor(int r, int g, int b, int a){
		txtRed = r;
		txtGreen = g;
		txtBlue = b;
		txtAlpha = a;
	}

	/**
	 * Set the highlight color when the text in selected
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setSelBkgColor(int r, int g, int b, int a){
		selBkgRed = r;
		selBkgGreen = g;
		selBkgBlue = b;
		selBkgAlpha = a;
	}

	/**
	 * Set the text color when the text is selected
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setSelTextColor(int r, int g, int b, int a){
		selTxtRed = r;
		selTxtGreen = g;
		selTxtBlue = b;
		selTxtAlpha = a;
	}

	/**
	 * Gets the background color of the rectangle that the text is rendered over.
	 * This rectangle is rendered if this.setDrawTextBkg(true) is used.
	 */
	public int[] getBkgColor(){
		return new int[]{ bkgRed, bkgGreen, bkgBlue, bkgAlpha};
	}

	/**
	 * Gets the text color
	 */
	public int[] getTextColor(){
		return new int[]{ txtRed, txtGreen, txtBlue, txtAlpha};
	}

	/**
	 * Gets the highlight color when the text is selected
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public int[] getSelBkgColor(int r, int g, int b, int a){
			return new int[]{ selBkgRed, selBkgGreen, selBkgBlue, selBkgAlpha};
	}

	/**
	 * Gets the color of the text when it is selected
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public int[] getSelTextColor(int r, int g, int b, int a){
				return new int[]{selTxtRed, selTxtGreen, selTxtBlue, selTxtAlpha};
	}

	/**
	 * Sets the draw text background flag.
	 * When true, the text will be rendered on a background rectangle
	 * @param flag
	 */
	public void setDrawTextBkg(boolean flag){
		txtBkg = flag;
	}

	/**
	 * Get the draw text background flag.
	 * When true, the text will be rendered on a background rectangle
	 */
	public boolean getDrawTextBkg(){
		return txtBkg;
	}

	/**
	 * Sets the word zone used to render the text in the textZone
	 * @param wZone
	 */
	public void setWordZone(WordZone wZone){
		currentWordZone = wZone;
	}

	/**
	 * Gets the word zone used to render the text in the textZone
	 */
	public WordZone getWordZone(){
		return currentWordZone;
	}
	/**
	 * Sets if the text in the TextZone is selectable/highlightable
	 * @param flag
	 */
	public void setSelectable(boolean flag){
		selectable = flag;
	}

	/**
	 * Gets the flag that states if the text in the TextZone is selectable/highlightable
	 */
	public boolean getSelectable(){
		return selectable;
	}

	/**
	 * Set the size of the font
	 * @param fs
	 */
	public void setFontSize(float fs){
		fontSize = fs;
	}

	/**
	 * Get the font size
	 */
	public float getFontSize(){
		return fontSize;
	}

	/**
	 * Set Set the TextZone to receive input from the PApplet (keyboard)
	 * @param keysFlag
	 */
	public void setKeysFromApplet(boolean keysFlag){
		keysFromApplet = keysFlag;
	}

	/**
	 * Gets the flag that states if the TextZone is to receive input from the PApplet (keyboard)
	 */
	public boolean getKeysFromApplet(){
		return keysFromApplet;
	}

	/**
	 * Sets the blur flag. If the text should be blurred.
	 * @param blurFlag
	 */
	public void setBlur(boolean blurFlag){
		blur = blurFlag;
	}

	/**
	 * Gets the blur flag. If the text is set to blurred.
	 */
	public boolean getBlur(){
		return blur;
	}

	/**
	 * Set the small font used for blurring
	 * Blurring is achieved by stretching small font
	 * @param font
	 */
	public void setSmallFont(PFont font){
		sFont = font;
	}

	/**
	 * Set the small font used for blurring
	 * Blurring is achieved by stretching small font
	 */
	public PFont getSmallFont(){
		return sFont;
	}

	/**
	 * Set the font
	 * @param font
	 */
	public void setFont(PFont font){
		this.font = font;
	}

	/**
	 * Get the font
	 */
	public PFont getFont(){
		return font;
	}



	boolean warnDraw() {
		return false;
	}

	boolean warnTouch() {
		return false;
	}


	public TextZone(TextZone original) {
		super(original.name, original.x, original.y, original.width, original.height);
		this.currentWordZone = (WordZone) original.currentWordZone.clone();
		this.blur = original.blur;
		this.selectable = original.selectable;
		this.keysFromApplet = original.keysFromApplet;
		if (this.keysFromApplet) {
			applet.registerMethod("keyEvent", this);
		}
		this.fontSize = original.fontSize;
		if (this.fontSize != 16) {
			this.font = applet.createFont("Lucida Sans", fontSize);
		}
	}

	public TextZone(int x, int y, int width, int height, String inputText, boolean selectable,
			boolean blur, boolean keysRecievedFromApplet) {
		this(null, x, y, width, height, inputText, selectable, blur, keysRecievedFromApplet, 16);
	}

	public TextZone(String name, int x, int y, int width, int height, float fontSize) {
		this(name, x, y, width, height, null, false, false, false, fontSize);
	}

	public TextZone(String name, int x, int y, int width, int height, String inputText,
			boolean selectable, boolean blur, boolean keysRecievedFromApplet, float fontSize) {
		this(name, x, y, width, height, inputText, selectable, blur, keysRecievedFromApplet, fontSize, null);
	}

	/**
	 * @param name	- String: The name of the zone
	 * @param x	   - int: X-coordinate of the upper left corner of the zone
	 * @param y	   - int: Y-coordinate of the upper left corner of the zone
	 * @param width   - int: Width of the zone
	 * @param height  - int: Height of the zone
	 * @param inputText  - String: The text that will be displayed in the Text Zone
	 * @param selectable  - boolean:
	 * @param blur  - boolean:
	 * @param keysRecievedFromApplet  - boolean:
	 * @param fontSize  - float: Sets the size of the font used to display the text in this zone
	 * @param font  - PFont: Sets the font used to display the text in this zone
	 *
	 */
	public TextZone(String name, int x, int y, int width, int height, String inputText,
			boolean selectable, boolean blur, boolean keysRecievedFromApplet, float fontSize, PFont font) {
		super(name, x, y, width, height);
		this.currentWordZone = new WordZone(5, 5, 0, 20);
		this.keysFromApplet = keysRecievedFromApplet;
		if (keysRecievedFromApplet) {
			applet.registerMethod("keyEvent", this);
		}
		this.selectable = selectable;
		this.blur = blur;
		this.fontSize = fontSize;
		this.font = font;
		if(this.font == null){
			this.font = applet.createFont("Lucida Sans", fontSize);
		}
		sFont = applet.createFont("Lucida Sans", 3.0f);

		addText(inputText);
	}

	public TextZone(int x, int y, int width, int height) {
		this(null, x, y, width, height, false);
	}

	public TextZone(int x, int y, int width, int height, boolean keysRecievedFromApplet) {
		this(null, x, y, width, height, keysRecievedFromApplet);
	}

	public TextZone(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, false);
	}

	public TextZone(String name, int x, int y, int width, int height, boolean keysRecievedFromApplet) {
		this(name, x, y, width, height, null, false, false, keysRecievedFromApplet, 16f);
	}

	@Override
	public void drawImpl() {

	}

	/**
	 * Gets the text being rendered to the TextZone
	 */
	public String getText(){
		return currentWordZone.word;
	}

	/**
	 * Deletes all the text being rendered to the TextZone
	 */
	public void deleteAllText(){
		currentWordZone.word = "";
	}

	/**
	 * Deletes a number of characters from the end of the text
	 * that is being rendered to the TextZone
	 * @param numChars
	 */
	public void delete(int numChars){
		for(int i = 0; i < numChars; i++){
			currentWordZone.word = currentWordZone.word.substring(currentWordZone.word.length(), currentWordZone.word.length()-numChars);
		}
	}

	/**
	 * Add a String of text to the TextZone
	 * @param text
	 */
	public void addText(String text){
		if (text != null) {
			for (char c : text.toCharArray()) {
				this.addChar(c);
			}
		}
	}

	/**
	 * Add a char to the TextZone
	 * @param c
	 */
	public void addChar(char c) {
		if (c == ' ') {
			this.currentWordZone = new WordZone(currentWordZone.x + currentWordZone.width,
					currentWordZone.y, 0, (int) (fontSize * (20.0 / 16.0)));
			currentWordZone.word += " ";
		}
		else if (c == '\t') {
			this.currentWordZone = new WordZone(currentWordZone.x + currentWordZone.width,
					currentWordZone.y, 0, (int) (fontSize * (20.0 / 16.0)));
			currentWordZone.word += "	";
		}
		else if (c == '\n') {
			this.currentWordZone = new WordZone(5, currentWordZone.y
					+ (int) (fontSize * (20.0 / 16.0)), 0, (int) (fontSize * (20.0 / 16.0)));
		}
		else {
			if (currentWordZone.word.trim().equals("")) {
				currentWordZone = new WordZone(currentWordZone.x + currentWordZone.width,
						currentWordZone.y, 0, (int) (fontSize * (20.0 / 16.0)));
			}
			if (currentWordZone.x + currentWordZone.width + fontSize > width
					&& currentWordZone.x != 0) {
				this.currentWordZone.setData(0, currentWordZone.y
						+ (int) (fontSize * (20.0 / 16.0)), 0, (int) (fontSize * (20.0 / 16.0)));
			}
			if (currentWordZone.x + currentWordZone.width + fontSize <= width) {
				this.currentWordZone.word += c;
			}
		}
		pushStyle();
		textFont(font);
		textSize(fontSize);
		currentWordZone.width = (int) Math.ceil(textWidth(currentWordZone.word));
		popStyle();
	}

	@Override
	public void keyTyped( KeyEvent event) {
		this.addChar( event.getKey());
		super.keyTyped( event);
	}
}
