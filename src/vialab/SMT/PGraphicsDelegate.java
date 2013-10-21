package vialab.SMT;

//processing imports
import java.awt.Image;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PGraphicsJava2D;
import processing.core.PImage;
import processing.core.PMatrix;
import processing.core.PMatrix2D;
import processing.core.PMatrix3D;
import processing.core.PShape;
import processing.core.PStyle;
import processing.opengl.PGL;
import processing.opengl.PGraphics2D;
import processing.opengl.PGraphics3D;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PShader;

//local imports
import vialab.SMT.exception.UnsupportedRendererException;

/**
 * PGraphicsDelegate wraps the Processing functions for use by Zone.
 * 
 * @see <a href="http://processing.org/reference">Processing Documentation</a>
 */
public abstract class PGraphicsDelegate extends PGraphics{

	protected boolean initCalled = false;
	protected PGraphics pg;
	protected PGraphics defaultGraphics;

	// public methods
	public PMatrix getIdentityMatrix(){
		return ( this.getMatrix() instanceof PMatrix2D) ?
			new PMatrix2D() : new PMatrix3D();
	}

	//private methods
	private PGraphics getDelegate(){
		delegateNullCheck();
		System.out.printf(
			"Returning %s\n",
			 pg == this ? "defaultGraphics" : "pg");
		System.out.printf("init called: %b\n", initCalled);
		System.out.printf("pg is null: %b\n", pg == null);
		return pg == this ? defaultGraphics : pg;
	}
	private void delegateNullCheck(){
		if( defaultGraphics != null) return;
		defaultGraphics = SMT.parent.createGraphics(
			SMT.parent.g.width,
			SMT.parent.g.height,
			SMT.parent.g.getClass().getName());

		if( defaultGraphics == null)
			throw new UnsupportedRendererException(
				"SMT cannot detect the renderer currently being used. Try using JAVA2D, OPENGL, P2D, or P3D instead.");
	}

	//override methods

	public void ambient(int rgb) {
		getDelegate().ambient(rgb);
	}

	public void ambient(float gray) {
		getDelegate().ambient(gray);
	}

	public void ambient(float v1, float v2, float v3) {
		getDelegate().ambient(v1, v2, v3);
	}

	public void ambientLight(float v1, float v2, float v3) {
		getDelegate().ambientLight(v1, v2, v3);
	}

	public void ambientLight(float v1, float v2, float v3, float x, float y, float z) {
		getDelegate().ambientLight(v1, v2, v3, x, y, z);
	}

	public void applyMatrix(PMatrix source) {
		getDelegate().applyMatrix(source);
	}

	public void applyMatrix(PMatrix2D source) {
		getDelegate().applyMatrix(source);
	}

	public void applyMatrix(PMatrix3D source) {
		getDelegate().applyMatrix(source);
	}

	public void applyMatrix(float n00, float n01, float n02, float n10, float n11, float n12) {
		getDelegate().applyMatrix(n00, n01, n02, n10, n11, n12);
	}

	public void applyMatrix(float n00, float n01, float n02, float n03, float n10, float n11,
			float n12, float n13, float n20, float n21, float n22, float n23, float n30, float n31,
			float n32, float n33) {
		getDelegate().applyMatrix(n00, n01, n02, n03, n10, n11, n12, n13, n20, n21, n22, n23, n30, n31, n32,
				n33);
	}

	public void arc(float a, float b, float c, float d, float start, float stop) {
		getDelegate().arc(a, b, c, d, start, stop);
	}

	public void arc(float a, float b, float c, float d, float start, float stop, int mode) {
		getDelegate().arc(a, b, c, d, start, stop, mode);
	}

	public void background(int rgb) {
		getDelegate().background(rgb);
	}

	public void background(float gray) {
		getDelegate().background(gray);
	}

	public void background(PImage image) {
		getDelegate().background(image);
	}

	public void background(int rgb, float alpha) {
		getDelegate().background(rgb, alpha);
	}

	public void background(float gray, float alpha) {
		getDelegate().background(gray, alpha);
	}

	public void background(float v1, float v2, float v3) {
		getDelegate().background(v1, v2, v3);
	}

	public void background(float v1, float v2, float v3, float alpha) {
		getDelegate().background(v1, v2, v3, alpha);
	}

	public void beginCamera() {
		getDelegate().beginCamera();
	}

	public void beginContour() {
		getDelegate().beginContour();
	}

	public void beginDraw() {
		getDelegate().beginDraw();
	}

	public PGL beginPGL() {
		return getDelegate().beginPGL();
	}

	public void beginRaw(PGraphics rawGraphics) {
		getDelegate().beginRaw(rawGraphics);
	}

	public void beginShape() {
		getDelegate().beginShape();
	}

	public void beginShape(int kind) {
		getDelegate().beginShape(kind);
	}

	public void bezier(float x1, float y1, float x2, float y2, float x3, float y3, float x4,
			float y4) {
		getDelegate().bezier(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	public void bezier(float x1, float y1, float z1, float x2, float y2, float z2, float x3,
			float y3, float z3, float x4, float y4, float z4) {
		getDelegate().bezier(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
	}

	public void bezierDetail(int detail) {
		getDelegate().bezierDetail(detail);
	}

	public float bezierPoint(float a, float b, float c, float d, float t) {
		return getDelegate().bezierPoint(a, b, c, d, t);
	}

	public float bezierTangent(float a, float b, float c, float d, float t) {
		return getDelegate().bezierTangent(a, b, c, d, t);
	}

	public void bezierVertex(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		getDelegate().bezierVertex(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public void bezierVertex(float arg0, float arg1, float arg2, float arg3, float arg4,
			float arg5, float arg6, float arg7, float arg8) {
		getDelegate().bezierVertex(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	public void blend(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh, int mode) {
		getDelegate().blend(sx, sy, sw, sh, dx, dy, dw, dh, mode);
	}

	public void blend(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh,
			int mode) {
		getDelegate().blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode);
	}

	public void blendMode(int mode) {
		getDelegate().blendMode(mode);
	}

	public void box(float size) {
		getDelegate().box(size);
	}

	public void box(float w, float h, float d) {
		getDelegate().box(w, h, d);
	}

	public void camera() {
		getDelegate().camera();
	}

	public void camera(float eyeX, float eyeY, float eyeZ, float centerX, float centerY,
			float centerZ, float upX, float upY, float upZ) {
		getDelegate().camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
	}

	public boolean canDraw() {
		return getDelegate().canDraw();
	}

	public void clear() {
		getDelegate().clear();
	}

	public void clip(float arg0, float arg1, float arg2, float arg3) {
		getDelegate().clip(arg0, arg1, arg2, arg3);
	}

	public Object clone() throws CloneNotSupportedException {
		return getDelegate().clone();
	}

	public void colorMode(int mode) {
		getDelegate().colorMode(mode);
	}

	public void colorMode(int mode, float max) {
		getDelegate().colorMode(mode, max);
	}

	public void colorMode(int mode, float max1, float max2, float max3) {
		getDelegate().colorMode(mode, max1, max2, max3);
	}

	public void colorMode(int mode, float max1, float max2, float max3, float maxA) {
		getDelegate().colorMode(mode, max1, max2, max3, maxA);
	}

	public void copy(int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh) {
		getDelegate().copy(sx, sy, sw, sh, dx, dy, dw, dh);
	}

	public void copy(PImage src, int sx, int sy, int sw, int sh, int dx, int dy, int dw, int dh) {
		getDelegate().copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
	}

	public PShape createShape() {
		return getDelegate().createShape();
	}

	public PShape createShape(PShape source) {
		return getDelegate().createShape(source);
	}

	public PShape createShape(int type) {
		return getDelegate().createShape(type);
	}

	public PShape createShape(int kind, float... p) {
		return getDelegate().createShape(kind, p);
	}

	public void curve(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		getDelegate().curve(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	public void curve(float x1, float y1, float z1, float x2, float y2, float z2, float x3,
			float y3, float z3, float x4, float y4, float z4) {
		getDelegate().curve(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
	}

	public void curveDetail(int detail) {
		getDelegate().curveDetail(detail);
	}

	public float curvePoint(float a, float b, float c, float d, float t) {
		return getDelegate().curvePoint(a, b, c, d, t);
	}

	public float curveTangent(float a, float b, float c, float d, float t) {
		return getDelegate().curveTangent(a, b, c, d, t);
	}

	public void curveTightness(float tightness) {
		getDelegate().curveTightness(tightness);
	}

	public void curveVertex(float x, float y) {
		getDelegate().curveVertex(x, y);
	}

	public void curveVertex(float x, float y, float z) {
		getDelegate().curveVertex(x, y, z);
	}

	public void directionalLight(float v1, float v2, float v3, float nx, float ny, float nz) {
		getDelegate().directionalLight(v1, v2, v3, nx, ny, nz);
	}

	public boolean displayable() {
		return getDelegate().displayable();
	}

	public void dispose() {
		getDelegate().dispose();
	}

	public void edge(boolean edge) {
		getDelegate().edge(edge);
	}

	public void ellipse(float a, float b, float c, float d) {
		getDelegate().ellipse(a, b, c, d);
	}

	public void ellipseMode(int mode) {
		getDelegate().ellipseMode(mode);
	}

	public void emissive(int rgb) {
		getDelegate().emissive(rgb);
	}

	public void emissive(float gray) {
		getDelegate().emissive(gray);
	}

	public void emissive(float v1, float v2, float v3) {
		getDelegate().emissive(v1, v2, v3);
	}

	public void endCamera() {
		getDelegate().endCamera();
	}

	public void endContour() {
		getDelegate().endContour();
	}

	public void endDraw() {
		getDelegate().endDraw();
	}

	public void endPGL() {
		getDelegate().endPGL();
	}

	public void endRaw() {
		getDelegate().endRaw();
	}

	public void endShape() {
		getDelegate().endShape();
	}

	public void endShape(int mode) {
		getDelegate().endShape(mode);
	}

	public boolean equals(Object obj) {
		return getDelegate().equals(obj);
	}

	public void fill(int rgb) {
		getDelegate().fill(rgb);
	}

	public void fill(float gray) {
		getDelegate().fill(gray);
	}

	public void fill(int rgb, float alpha) {
		getDelegate().fill(rgb, alpha);
	}

	public void fill(float gray, float alpha) {
		getDelegate().fill(gray, alpha);
	}

	public void fill(float v1, float v2, float v3) {
		getDelegate().fill(v1, v2, v3);
	}

	public void fill(float v1, float v2, float v3, float alpha) {
		getDelegate().fill(v1, v2, v3, alpha);
	}

	public void filter(PShader shader) {
		getDelegate().filter(shader);
	}

	public void filter(int arg0) {
		getDelegate().filter(arg0);
	}

	public void filter(int arg0, float arg1) {
		getDelegate().filter(arg0, arg1);
	}

	public void flush() {
		getDelegate().flush();
	}

	public void frustum(float left, float right, float bottom, float top, float near, float far) {
		getDelegate().frustum(left, right, bottom, top, near, far);
	}

	public PImage get() {
		return getDelegate().get();
	}

	public int get(int x, int y) {
		return getDelegate().get(x, y);
	}

	public PImage get(int x, int y, int w, int h) {
		return getDelegate().get(x, y, w, h);
	}

	public Object getCache(PImage image) {
		return getDelegate().getCache(image);
	}

	public Image getImage() {
		return getDelegate().getImage();
	}

	public PMatrix getMatrix() {
		return getDelegate().getMatrix();
	}

	public PMatrix2D getMatrix(PMatrix2D target) {
		return getDelegate().getMatrix(target);
	}

	public PMatrix3D getMatrix(PMatrix3D target) {
		return getDelegate().getMatrix(target);
	}

	public int getModifiedX1() {
		return getDelegate().getModifiedX1();
	}

	public int getModifiedX2() {
		return getDelegate().getModifiedX2();
	}

	public int getModifiedY1() {
		return getDelegate().getModifiedY1();
	}

	public int getModifiedY2() {
		return getDelegate().getModifiedY2();
	}

	public Object getNative() {
		return getDelegate().getNative();
	}

	public PGraphics getRaw() {
		return getDelegate().getRaw();
	}

	public PStyle getStyle() {
		return getDelegate().getStyle();
	}

	public PStyle getStyle(PStyle s) {
		return getDelegate().getStyle(s);
	}

	public int hashCode() {
		return getDelegate().hashCode();
	}

	public boolean haveRaw() {
		return getDelegate().haveRaw();
	}

	public void hint(int which) {
		getDelegate().hint(which);
	}

	public void image(PImage arg0, float arg1, float arg2) {
		getDelegate().image(arg0, arg1, arg2);
	}

	public void image(PImage img, float a, float b, float c, float d) {
		getDelegate().image(img, a, b, c, d);
	}

	public void image(PImage arg0, float arg1, float arg2, float arg3, float arg4, int arg5,
			int arg6, int arg7, int arg8) {
		getDelegate().image(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	public void imageMode(int arg0) {
		getDelegate().imageMode(arg0);
	}

	public void init(int width, int height, int format) {
		getDelegate().init(width, height, format);
	}

	public boolean is2D() {
		return getDelegate().is2D();
	}

	public boolean is3D() {
		return getDelegate().is3D();
	}

	public boolean isGL() {
		return getDelegate().isGL();
	}

	public boolean isLoaded() {
		return getDelegate().isLoaded();
	}

	public boolean isModified() {
		return getDelegate().isModified();
	}

	public int lerpColor(int c1, int c2, float amt) {
		return getDelegate().lerpColor(c1, c2, amt);
	}

	public void lightFalloff(float constant, float linear, float quadratic) {
		getDelegate().lightFalloff(constant, linear, quadratic);
	}

	public void lightSpecular(float v1, float v2, float v3) {
		getDelegate().lightSpecular(v1, v2, v3);
	}

	public void lights() {
		getDelegate().lights();
	}

	public void line(float x1, float y1, float x2, float y2) {
		getDelegate().line(x1, y1, x2, y2);
	}

	public void line(float x1, float y1, float z1, float x2, float y2, float z2) {
		getDelegate().line(x1, y1, z1, x2, y2, z2);
	}

	public void loadPixels() {
		getDelegate().loadPixels();
	}

	public PShader loadShader(String fragFilename) {
		return getDelegate().loadShader(fragFilename);
	}

	public PShader loadShader(String fragFilename, String vertFilename) {
		return getDelegate().loadShader(fragFilename, vertFilename);
	}

	public PShape loadShape(String filename) {
		return getDelegate().loadShape(filename);
	}

	public PShape loadShape(String filename, String options) {
		return getDelegate().loadShape(filename, options);
	}

	public void mask(int[] arg0) {
		getDelegate().mask(arg0);
	}

	public void mask(PImage img) {
		getDelegate().mask(img);
	}

	public float modelX(float x, float y, float z) {
		return getDelegate().modelX(x, y, z);
	}

	public float modelY(float x, float y, float z) {
		return getDelegate().modelY(x, y, z);
	}

	public float modelZ(float x, float y, float z) {
		return getDelegate().modelZ(x, y, z);
	}

	public void noClip() {
		getDelegate().noClip();
	}

	public void noFill() {
		getDelegate().noFill();
	}

	public void noLights() {
		getDelegate().noLights();
	}

	public void noSmooth() {
		getDelegate().noSmooth();
	}

	public void noStroke() {
		getDelegate().noStroke();
	}

	public void noTexture() {
		getDelegate().noTexture();
	}

	public void noTint() {
		getDelegate().noTint();
	}

	public void normal(float nx, float ny, float nz) {
		getDelegate().normal(nx, ny, nz);
	}

	public void ortho() {
		getDelegate().ortho();
	}

	public void ortho(float left, float right, float bottom, float top) {
		getDelegate().ortho(left, right, bottom, top);
	}

	public void ortho(float left, float right, float bottom, float top, float near, float far) {
		getDelegate().ortho(left, right, bottom, top, near, far);
	}

	public void perspective() {
		getDelegate().perspective();
	}

	public void perspective(float fovy, float aspect, float zNear, float zFar) {
		getDelegate().perspective(fovy, aspect, zNear, zFar);
	}

	public void point(float x, float y) {
		getDelegate().point(x, y);
	}

	public void point(float x, float y, float z) {
		getDelegate().point(x, y, z);
	}

	public void pointLight(float v1, float v2, float v3, float x, float y, float z) {
		getDelegate().pointLight(v1, v2, v3, x, y, z);
	}

	public void popMatrix() {
		getDelegate().popMatrix();
	}

	public void popStyle() {
		getDelegate().popStyle();
	}

	public void printCamera() {
		getDelegate().printCamera();
	}

	public void printMatrix() {
		getDelegate().printMatrix();
	}

	public void printProjection() {
		getDelegate().printProjection();
	}

	public void pushMatrix() {
		getDelegate().pushMatrix();
	}

	public void pushStyle() {
		getDelegate().pushStyle();
	}

	public void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		getDelegate().quad(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	public void quadraticVertex(float cx, float cy, float x3, float y3) {
		getDelegate().quadraticVertex(cx, cy, x3, y3);
	}

	public void quadraticVertex(float cx, float cy, float cz, float x3, float y3, float z3) {
		getDelegate().quadraticVertex(cx, cy, cz, x3, y3, z3);
	}

	public void rect(float arg0, float arg1, float arg2, float arg3) {
		getDelegate().rect(arg0, arg1, arg2, arg3);
	}

	public void rect(float a, float b, float c, float d, float r) {
		getDelegate().rect(a, b, c, d, r);
	}

	public void rect(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		getDelegate().rect(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	public void rectMode(int mode) {
		getDelegate().rectMode(mode);
	}

	public void removeCache(PImage image) {
		getDelegate().removeCache(image);
	}

	public void requestDraw() {
		getDelegate().requestDraw();
	}

	public void requestFocus() {
		getDelegate().requestFocus();
	}

	public void resetMatrix() {
		getDelegate().resetMatrix();
	}

	public void resetShader() {
		getDelegate().resetShader();
	}

	public void resetShader(int kind) {
		getDelegate().resetShader(kind);
	}

	public void resize(int arg0, int arg1) {
		getDelegate().resize(arg0, arg1);
	}

	public void rotate(float angle) {
		getDelegate().rotate(angle);
	}

	public void rotate(float angle, float x, float y, float z) {
		getDelegate().rotate(angle, x, y, z);
	}

	public void rotateX(float angle) {
		getDelegate().rotateX(angle);
	}

	public void rotateY(float angle) {
		getDelegate().rotateY(angle);
	}

	public void rotateZ(float angle) {
		getDelegate().rotateZ(angle);
	}

	public boolean save(String arg0) {
		return getDelegate().save(arg0);
	}

	public void scale(float s) {
		getDelegate().scale(s);
	}

	public void scale(float x, float y) {
		getDelegate().scale(x, y);
	}

	public void scale(float x, float y, float z) {
		getDelegate().scale(x, y, z);
	}

	public float screenX(float x, float y) {
		return getDelegate().screenX(x, y);
	}

	public float screenX(float x, float y, float z) {
		return getDelegate().screenX(x, y, z);
	}

	public float screenY(float x, float y) {
		return getDelegate().screenY(x, y);
	}

	public float screenY(float x, float y, float z) {
		return getDelegate().screenY(x, y, z);
	}

	public float screenZ(float x, float y, float z) {
		return getDelegate().screenZ(x, y, z);
	}

	public void set(int x, int y, int c) {
		getDelegate().set(x, y, c);
	}

	public void set(int x, int y, PImage img) {
		getDelegate().set(x, y, img);
	}

	public void setCache(PImage image, Object storage) {
		getDelegate().setCache(image, storage);
	}

	public void setFrameRate(float frameRate) {
		getDelegate().setFrameRate(frameRate);
	}

	public void setLoaded() {
		getDelegate().setLoaded();
	}

	public void setLoaded(boolean l) {
		getDelegate().setLoaded(l);
	}

	public void setMatrix(PMatrix source) {
		getDelegate().setMatrix(source);
	}

	public void setMatrix(PMatrix2D source) {
		getDelegate().setMatrix(source);
	}

	public void setMatrix(PMatrix3D source) {
		getDelegate().setMatrix(source);
	}

	public void setModified() {
		getDelegate().setModified();
	}

	public void setModified(boolean m) {
		getDelegate().setModified(m);
	}

	public void setParent(PApplet parent) {
		getDelegate().setParent(parent);
	}

	public void setPath(String path) {
		getDelegate().setPath(path);
	}

	public void setPrimary(boolean primary) {
		getDelegate().setPrimary(primary);
	}

	public void setSize(int w, int h) {
		getDelegate().setSize(w, h);
	}

	public void shader(PShader shader) {
		getDelegate().shader(shader);
	}

	public void shader(PShader shader, int kind) {
		getDelegate().shader(shader, kind);
	}

	public void shape(PShape shape) {
		getDelegate().shape(shape);
	}

	public void shape(PShape shape, float x, float y) {
		getDelegate().shape(shape, x, y);
	}

	public void shape(PShape shape, float a, float b, float c, float d) {
		getDelegate().shape(shape, a, b, c, d);
	}

	public void shapeMode(int mode) {
		getDelegate().shapeMode(mode);
	}

	public void shearX(float angle) {
		getDelegate().shearX(angle);
	}

	public void shearY(float angle) {
		getDelegate().shearY(angle);
	}

	public void shininess(float shine) {
		getDelegate().shininess(shine);
	}

	public void smooth() {
		getDelegate().smooth();
	}

	public void smooth(int level) {
		getDelegate().smooth(level);
	}

	public void specular(int rgb) {
		getDelegate().specular(rgb);
	}

	public void specular(float gray) {
		getDelegate().specular(gray);
	}

	public void specular(float v1, float v2, float v3) {
		getDelegate().specular(v1, v2, v3);
	}

	public void sphere(float arg0) {
		getDelegate().sphere(arg0);
	}

	public void sphereDetail(int res) {
		getDelegate().sphereDetail(res);
	}

	public void sphereDetail(int arg0, int arg1) {
		getDelegate().sphereDetail(arg0, arg1);
	}

	public void spotLight(float v1, float v2, float v3, float x, float y, float z, float nx,
			float ny, float nz, float angle, float concentration) {
		getDelegate().spotLight(v1, v2, v3, x, y, z, nx, ny, nz, angle, concentration);
	}

	public void stroke(int rgb) {
		getDelegate().stroke(rgb);
	}

	public void stroke(float gray) {
		getDelegate().stroke(gray);
	}

	public void stroke(int rgb, float alpha) {
		getDelegate().stroke(rgb, alpha);
	}

	public void stroke(float gray, float alpha) {
		getDelegate().stroke(gray, alpha);
	}

	public void stroke(float v1, float v2, float v3) {
		getDelegate().stroke(v1, v2, v3);
	}

	public void stroke(float v1, float v2, float v3, float alpha) {
		getDelegate().stroke(v1, v2, v3, alpha);
	}

	public void strokeCap(int cap) {
		getDelegate().strokeCap(cap);
	}

	public void strokeJoin(int join) {
		getDelegate().strokeJoin(join);
	}

	public void strokeWeight(float weight) {
		getDelegate().strokeWeight(weight);
	}

	public void style(PStyle s) {
		getDelegate().style(s);
	}

	public void text(char c, float x, float y) {
		getDelegate().text(c, x, y);
	}

	public void text(String str, float x, float y) {
		getDelegate().text(str, x, y);
	}

	public void text(int num, float x, float y) {
		getDelegate().text(num, x, y);
	}

	public void text(float num, float x, float y) {
		getDelegate().text(num, x, y);
	}

	public void text(char c, float x, float y, float z) {
		getDelegate().text(c, x, y, z);
	}

	public void text(String str, float x, float y, float z) {
		getDelegate().text(str, x, y, z);
	}

	public void text(int num, float x, float y, float z) {
		getDelegate().text(num, x, y, z);
	}

	public void text(float num, float x, float y, float z) {
		getDelegate().text(num, x, y, z);
	}

	public void text(char[] arg0, int arg1, int arg2, float arg3, float arg4) {
		getDelegate().text(arg0, arg1, arg2, arg3, arg4);
	}

	public void text(String arg0, float arg1, float arg2, float arg3, float arg4) {
		getDelegate().text(arg0, arg1, arg2, arg3, arg4);
	}

	public void text(char[] chars, int start, int stop, float x, float y, float z) {
		getDelegate().text(chars, start, stop, x, y, z);
	}

	public void textAlign(int alignX) {
		getDelegate().textAlign(alignX);
	}

	public void textAlign(int alignX, int alignY) {
		getDelegate().textAlign(alignX, alignY);
	}

	public float textAscent() {
		return getDelegate().textAscent();
	}

	public float textDescent() {
		return getDelegate().textDescent();
	}

	public void textFont(PFont which) {
		getDelegate().textFont(which);
	}

	public void textFont(PFont which, float size) {
		getDelegate().textFont(which, size);
	}

	public void textLeading(float leading) {
		getDelegate().textLeading(leading);
	}

	public void textMode(int arg0) {
		getDelegate().textMode(arg0);
	}

	public void textSize(float size) {
		getDelegate().textSize(size);
	}

	public float textWidth(char c) {
		return getDelegate().textWidth(c);
	}

	public float textWidth(String str) {
		return getDelegate().textWidth(str);
	}

	public float textWidth(char[] chars, int start, int length) {
		return getDelegate().textWidth(chars, start, length);
	}

	public void texture(PImage image) {
		getDelegate().texture(image);
	}

	public void textureMode(int mode) {
		getDelegate().textureMode(mode);
	}

	public void textureWrap(int wrap) {
		getDelegate().textureWrap(wrap);
	}

	public void tint(int rgb) {
		getDelegate().tint(rgb);
	}

	public void tint(float gray) {
		getDelegate().tint(gray);
	}

	public void tint(int rgb, float alpha) {
		getDelegate().tint(rgb, alpha);
	}

	public void tint(float gray, float alpha) {
		getDelegate().tint(gray, alpha);
	}

	public void tint(float v1, float v2, float v3) {
		getDelegate().tint(v1, v2, v3);
	}

	public void tint(float v1, float v2, float v3, float alpha) {
		getDelegate().tint(v1, v2, v3, alpha);
	}

	public String toString() {
		return getDelegate().toString();
	}

	public void translate(float x, float y) {
		getDelegate().translate(x, y);
	}

	public void translate(float x, float y, float z) {
		getDelegate().translate(x, y, z);
	}

	public void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
		getDelegate().triangle(x1, y1, x2, y2, x3, y3);
	}

	public void updatePixels() {
		getDelegate().updatePixels();
	}

	public void updatePixels(int x, int y, int w, int h) {
		getDelegate().updatePixels(x, y, w, h);
	}

	public void vertex(float[] v) {
		getDelegate().vertex(v);
	}

	public void vertex(float arg0, float arg1) {
		getDelegate().vertex(arg0, arg1);
	}

	public void vertex(float arg0, float arg1, float arg2) {
		getDelegate().vertex(arg0, arg1, arg2);
	}

	public void vertex(float x, float y, float u, float v) {
		getDelegate().vertex(x, y, u, v);
	}

	public void vertex(float x, float y, float z, float u, float v) {
		getDelegate().vertex(x, y, z, u, v);
	}
}
