package vialab.SMT;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PMatrix;
import processing.core.PMatrix2D;
import processing.core.PMatrix3D;
import processing.core.PShape;
import processing.core.PStyle;
import processing.opengl.PGL;
import processing.opengl.PShader;
import processing.opengl.PGraphicsOpenGL;

/**
 * PGraphicsDelegate wraps the Processing functions for use by Zone.
 * 
 * @see <a href="http://processing.org/reference">Processing Documentation</a>
 */
public abstract class PGraphicsDelegate extends PGraphicsOpenGL{

	protected PGraphicsOpenGL pg;

	@Override
	public void beginContour() {
		pg.beginContour();
	}

	@Override
	public PGL beginPGL() {
		return pg.beginPGL();
	}

	@Override
	public void blendMode(int mode) {
		pg.blendMode(mode);
	}

	@Override
	public void endContour() {
		pg.endContour();
	}

	@Override
	public void endPGL() {
		pg.endPGL();
	}

	@Override
	public void filter(PShader shader) {
		pg.filter(shader);
	}

	@Override
	public boolean isGL() {
		return pg.isGL();
	}

	@Override
	public PShader loadShader(String fragFilename, String vertFilename) {
		return pg.loadShader(fragFilename, vertFilename);
	}

	@Override
	public PShader loadShader(String fragFilename) {
		return pg.loadShader(fragFilename);
	}

	@Override
	public PShape loadShape(String filename) {
		return pg.loadShape(filename);
	}

	@Override
	public void noClip() {
		pg.noClip();
	}

	@Override
	public void ortho(float left, float right, float bottom, float top) {
		pg.ortho(left, right, bottom, top);
	}

	@Override
	public void quadraticVertex(float cx, float cy, float cz, float x3, float y3, float z3) {
		pg.quadraticVertex(cx, cy, cz, x3, y3, z3);
	}

	@Override
	public void quadraticVertex(float cx, float cy, float x3, float y3) {
		pg.quadraticVertex(cx, cy, x3, y3);
	}

	@Override
	public void requestDraw() {
		pg.requestDraw();
	}

	@Override
	public void resetShader() {
		pg.resetShader();
	}

	@Override
	public void resetShader(int kind) {
		pg.resetShader(kind);
	}

	@Override
	public void setFrameRate(float frameRate) {
		pg.setFrameRate(frameRate);
	}

	@Override
	public void shader(PShader shader, int kind) {
		pg.shader(shader, kind);
	}

	@Override
	public void shader(PShader shader) {
		pg.shader(shader);
	}

	@Override
	public void shearX(float angle) {
		pg.shearX(angle);
	}

	@Override
	public void shearY(float angle) {
		pg.shearY(angle);
	}

	@Override
	public void smooth(int level) {
		pg.smooth(level);
	}

	@Override
	public void textureWrap(int wrap) {
		pg.textureWrap(wrap);
	}

	@Override
	public void arc(float a, float b, float c, float d, float start, float stop, int mode) {
		pg.arc(a, b, c, d, start, stop, mode);
	}

	@Override
	public void clear() {
		pg.clear();
	}

	@Override
	public void clip(float a, float b, float c, float d) {
		pg.clip(a, b, c, d);
	}

	@Override
	public PShape createShape() {
		return pg.createShape();
	}

	@Override
	public PShape createShape(int kind, float... p) {
		return pg.createShape(kind, p);
	}

	@Override
	public PShape createShape(int type) {
		return pg.createShape(type);
	}

	@Override
	public PShape createShape(PShape source) {
		return pg.createShape(source);
	}

	@Override
	public Object getCache(PImage image) {
		return pg.getCache(image);
	}

	@Override
	public PGraphics getRaw() {
		return pg.getRaw();
	}

	@Override
	public boolean haveRaw() {
		return pg.haveRaw();
	}

	@Override
	public PShape loadShape(String filename, String options) {
		return pg.loadShape(filename, options);
	}

	@Override
	public void noTexture() {
		pg.noTexture();
	}

	@Override
	public void removeCache(PImage image) {
		pg.removeCache(image);
	}

	@Override
	public void setCache(PImage image, Object storage) {
		pg.setCache(image, storage);
	}

	@Override
	public void ambient(float arg0, float arg1, float arg2) {
		pg.ambient(arg0, arg1, arg2);
	}

	@Override
	public void ambient(float arg0) {
		pg.ambient(arg0);
	}

	@Override
	public void ambient(int arg0) {
		pg.ambient(arg0);
	}

	@Override
	public void ambientLight(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.ambientLight(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void ambientLight(float arg0, float arg1, float arg2) {
		pg.ambientLight(arg0, arg1, arg2);
	}

	@Override
	public void applyMatrix(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12,
			float arg13, float arg14, float arg15) {
		pg.applyMatrix(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11,
				arg12, arg13, arg14, arg15);
	}

	@Override
	public void applyMatrix(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.applyMatrix(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void applyMatrix(PMatrix arg0) {
		pg.applyMatrix(arg0);
	}

	@Override
	public void applyMatrix(PMatrix2D arg0) {
		pg.applyMatrix(arg0);
	}

	@Override
	public void applyMatrix(PMatrix3D arg0) {
		pg.applyMatrix(arg0);
	}

	@Override
	public void arc(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.arc(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void background(float arg0, float arg1, float arg2, float arg3) {
		pg.background(arg0, arg1, arg2, arg3);
	}

	@Override
	public void background(float arg0, float arg1, float arg2) {
		pg.background(arg0, arg1, arg2);
	}

	@Override
	public void background(float arg0, float arg1) {
		pg.background(arg0, arg1);
	}

	@Override
	public void background(float arg0) {
		pg.background(arg0);
	}

	@Override
	public void background(int arg0, float arg1) {
		pg.background(arg0, arg1);
	}

	@Override
	public void background(int arg0) {
		pg.background(arg0);
	}

	@Override
	public void background(PImage arg0) {
		pg.background(arg0);
	}

	@Override
	public void beginCamera() {
		pg.beginCamera();
	}

	@Override
	public void beginDraw() {
		pg.beginDraw();
	}

	@Override
	public void beginRaw(PGraphics arg0) {
		pg.beginRaw(arg0);
	}

	@Override
	public void beginShape() {
		pg.beginShape();
	}

	@Override
	public void beginShape(int arg0) {
		pg.beginShape(arg0);
	}

	@Override
	public void bezier(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8, float arg9, float arg10, float arg11) {
		pg.bezier(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
	}

	@Override
	public void bezier(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		pg.bezier(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public void bezierDetail(int arg0) {
		pg.bezierDetail(arg0);
	}

	@Override
	public float bezierPoint(float arg0, float arg1, float arg2, float arg3, float arg4) {
		return pg.bezierPoint(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public float bezierTangent(float arg0, float arg1, float arg2, float arg3, float arg4) {
		return pg.bezierTangent(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void bezierVertex(float arg0, float arg1, float arg2, float arg3, float arg4,
			float arg5, float arg6, float arg7, float arg8) {
		pg.bezierVertex(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@Override
	public void bezierVertex(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.bezierVertex(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void blend(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6,
			int arg7, int arg8) {
		pg.blend(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@Override
	public void blend(PImage arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6,
			int arg7, int arg8, int arg9) {
		pg.blend(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
	}

	// public final float blue(int arg0) {
	// return pg.blue(arg0);
	// }

	@Override
	public void box(float arg0, float arg1, float arg2) {
		pg.box(arg0, arg1, arg2);
	}

	@Override
	public void box(float arg0) {
		pg.box(arg0);
	}

	// public final float brightness(int arg0) {
	// return pg.brightness(arg0);
	// }

	@Override
	public void camera() {
		pg.camera();
	}

	@Override
	public void camera(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8) {
		pg.camera(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@Override
	public boolean canDraw() {
		return pg.canDraw();
	}

	// public final int color(float arg0, float arg1, float arg2, float arg3) {
	// return pg.color(arg0, arg1, arg2, arg3);
	// }

	// public final int color(float arg0, float arg1, float arg2) {
	// return pg.color(arg0, arg1, arg2);
	// }

	// public final int color(float arg0, float arg1) {
	// return pg.color(arg0, arg1);
	// }

	// public final int color(float arg0) {
	// return pg.color(arg0);
	// }

	// public final int color(int arg0, float arg1) {
	// return pg.color(arg0, arg1);
	// }

	// public final int color(int arg0, int arg1, int arg2, int arg3) {
	// return pg.color(arg0, arg1, arg2, arg3);
	// }

	// public final int color(int arg0, int arg1, int arg2) {
	// return pg.color(arg0, arg1, arg2);
	// }

	// public final int color(int arg0, int arg1) {
	// return pg.color(arg0, arg1);
	// }

	// public final int color(int arg0) {
	// return pg.color(arg0);
	// }

	@Override
	public void colorMode(int arg0, float arg1, float arg2, float arg3, float arg4) {
		pg.colorMode(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void colorMode(int arg0, float arg1, float arg2, float arg3) {
		pg.colorMode(arg0, arg1, arg2, arg3);
	}

	@Override
	public void colorMode(int arg0, float arg1) {
		pg.colorMode(arg0, arg1);
	}

	@Override
	public void colorMode(int arg0) {
		pg.colorMode(arg0);
	}

	@Override
	public void copy(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7) {
		pg.copy(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public void copy(PImage arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6,
			int arg7, int arg8) {
		pg.copy(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@Override
	public void curve(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8, float arg9, float arg10, float arg11) {
		pg.curve(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
	}

	@Override
	public void curve(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		pg.curve(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public void curveDetail(int arg0) {
		pg.curveDetail(arg0);
	}

	@Override
	public float curvePoint(float arg0, float arg1, float arg2, float arg3, float arg4) {
		return pg.curvePoint(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public float curveTangent(float arg0, float arg1, float arg2, float arg3, float arg4) {
		return pg.curveTangent(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void curveTightness(float arg0) {
		pg.curveTightness(arg0);
	}

	@Override
	public void curveVertex(float arg0, float arg1, float arg2) {
		pg.curveVertex(arg0, arg1, arg2);
	}

	@Override
	public void curveVertex(float arg0, float arg1) {
		pg.curveVertex(arg0, arg1);
	}

	@Override
	public void directionalLight(float arg0, float arg1, float arg2, float arg3, float arg4,
			float arg5) {
		pg.directionalLight(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public boolean displayable() {
		return pg.displayable();
	}

	@Override
	public void dispose() {
		pg.dispose();
	}

	@Override
	public void edge(boolean arg0) {
		pg.edge(arg0);
	}

	@Override
	public void ellipse(float arg0, float arg1, float arg2, float arg3) {
		pg.ellipse(arg0, arg1, arg2, arg3);
	}

	@Override
	public void ellipseMode(int arg0) {
		pg.ellipseMode(arg0);
	}

	@Override
	public void emissive(float arg0, float arg1, float arg2) {
		pg.emissive(arg0, arg1, arg2);
	}

	@Override
	public void emissive(float arg0) {
		pg.emissive(arg0);
	}

	@Override
	public void emissive(int arg0) {
		pg.emissive(arg0);
	}

	@Override
	public void endCamera() {
		pg.endCamera();
	}

	@Override
	public void endDraw() {
		pg.endDraw();
	}

	@Override
	public void endRaw() {
		pg.endRaw();
	}

	@Override
	public void endShape() {
		pg.endShape();
	}

	@Override
	public void endShape(int arg0) {
		pg.endShape(arg0);
	}

	@Override
	public void fill(float arg0, float arg1, float arg2, float arg3) {
		pg.fill(arg0, arg1, arg2, arg3);
	}

	@Override
	public void fill(float arg0, float arg1, float arg2) {
		pg.fill(arg0, arg1, arg2);
	}

	@Override
	public void fill(float arg0, float arg1) {
		pg.fill(arg0, arg1);
	}

	@Override
	public void fill(float arg0) {
		pg.fill(arg0);
	}

	@Override
	public void fill(int arg0, float arg1) {
		pg.fill(arg0, arg1);
	}

	@Override
	public void fill(int arg0) {
		pg.fill(arg0);
	}

	@Override
	public void filter(int arg0, float arg1) {
		pg.filter(arg0, arg1);
	}

	@Override
	public void filter(int arg0) {
		pg.filter(arg0);
	}

	@Override
	public void flush() {
		pg.flush();
	}

	@Override
	public void frustum(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.frustum(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public PImage get() {
		return pg.get();
	}

	@Override
	public PImage get(int arg0, int arg1, int arg2, int arg3) {
		return pg.get(arg0, arg1, arg2, arg3);
	}

	@Override
	public int get(int arg0, int arg1) {
		return pg.get(arg0, arg1);
	}

	@Override
	public PMatrix getMatrix() {
		return pg.getMatrix();
	}

	@Override
	public PMatrix2D getMatrix(PMatrix2D arg0) {
		return pg.getMatrix(arg0);
	}

	@Override
	public PMatrix3D getMatrix(PMatrix3D arg0) {
		return pg.getMatrix(arg0);
	}

	@Override
	public PStyle getStyle() {
		return pg.getStyle();
	}

	@Override
	public PStyle getStyle(PStyle arg0) {
		return pg.getStyle(arg0);
	}

	// public final float green(int arg0) {
	// return pg.green(arg0);
	// }

	@Override
	public void hint(int arg0) {
		pg.hint(arg0);
	}

	// public final float hue(int arg0) {
	// return pg.hue(arg0);
	// }

	@Override
	public void image(PImage arg0, float arg1, float arg2, float arg3, float arg4, int arg5,
			int arg6, int arg7, int arg8) {
		pg.image(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@Override
	public void image(PImage arg0, float arg1, float arg2, float arg3, float arg4) {
		pg.image(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void image(PImage arg0, float arg1, float arg2) {
		pg.image(arg0, arg1, arg2);
	}

	@Override
	public void imageMode(int arg0) {
		pg.imageMode(arg0);
	}

	@Override
	public void init(int arg0, int arg1, int arg2) {
		pg.init(arg0, arg1, arg2);
	}

	@Override
	public boolean is2D() {
		return pg.is2D();
	}

	@Override
	public boolean is3D() {
		return pg.is3D();
	}

	@Override
	public boolean isModified() {
		return pg.isModified();
	}

	@Override
	public int lerpColor(int arg0, int arg1, float arg2) {
		return pg.lerpColor(arg0, arg1, arg2);
	}

	@Override
	public void lightFalloff(float arg0, float arg1, float arg2) {
		pg.lightFalloff(arg0, arg1, arg2);
	}

	@Override
	public void lightSpecular(float arg0, float arg1, float arg2) {
		pg.lightSpecular(arg0, arg1, arg2);
	}

	@Override
	public void lights() {
		pg.lights();
	}

	@Override
	public void line(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.line(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void line(float arg0, float arg1, float arg2, float arg3) {
		pg.line(arg0, arg1, arg2, arg3);
	}

	@Override
	public void loadPixels() {
		pg.loadPixels();
	}

	/**
	 * @deprecated
	 */
	@Override
	public void mask(int[] arg0) {
		pg.mask(arg0);
	}

	@Override
	public void mask(PImage arg0) {
		pg.mask(arg0);
	}

	@Override
	public float modelX(float arg0, float arg1, float arg2) {
		return pg.modelX(arg0, arg1, arg2);
	}

	@Override
	public float modelY(float arg0, float arg1, float arg2) {
		return pg.modelY(arg0, arg1, arg2);
	}

	@Override
	public float modelZ(float arg0, float arg1, float arg2) {
		return pg.modelZ(arg0, arg1, arg2);
	}

	@Override
	public void noFill() {
		pg.noFill();
	}

	@Override
	public void noLights() {
		pg.noLights();
	}

	@Override
	public void noSmooth() {
		pg.noSmooth();
	}

	@Override
	public void noStroke() {
		pg.noStroke();
	}

	@Override
	public void noTint() {
		pg.noTint();
	}

	@Override
	public void normal(float arg0, float arg1, float arg2) {
		pg.normal(arg0, arg1, arg2);
	}

	@Override
	public void ortho() {
		pg.ortho();
	}

	@Override
	public void ortho(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.ortho(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void perspective() {
		pg.perspective();
	}

	@Override
	public void perspective(float arg0, float arg1, float arg2, float arg3) {
		pg.perspective(arg0, arg1, arg2, arg3);
	}

	@Override
	public void point(float arg0, float arg1, float arg2) {
		pg.point(arg0, arg1, arg2);
	}

	@Override
	public void point(float arg0, float arg1) {
		pg.point(arg0, arg1);
	}

	@Override
	public void pointLight(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.pointLight(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void popMatrix() {
		pg.popMatrix();
	}

	@Override
	public void popStyle() {
		pg.popStyle();
	}

	@Override
	public void printCamera() {
		pg.printCamera();
	}

	@Override
	public void printMatrix() {
		pg.printMatrix();
	}

	@Override
	public void printProjection() {
		pg.printProjection();
	}

	@Override
	public void pushMatrix() {
		pg.pushMatrix();
	}

	@Override
	public void pushStyle() {
		pg.pushStyle();
	}

	@Override
	public void quad(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		pg.quad(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public void rect(float arg0, float arg1, float arg2, float arg3) {
		pg.rect(arg0, arg1, arg2, arg3);
	}

	@Override
	public void rect(float arg0, float arg1, float arg2, float arg3, float arg4) {
		pg.rect(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void rect(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		pg.rect(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public void rectMode(int arg0) {
		pg.rectMode(arg0);
	}

	@Override
	public void resetMatrix() {
		pg.resetMatrix();
	}

	@Override
	public void resize(int arg0, int arg1) {
		pg.resize(arg0, arg1);
	}

	@Override
	public void rotate(float arg0, float arg1, float arg2, float arg3) {
		pg.rotate(arg0, arg1, arg2, arg3);
	}

	@Override
	public void rotate(float arg0) {
		pg.rotate(arg0);
	}

	@Override
	public void rotateX(float arg0) {
		pg.rotateX(arg0);
	}

	@Override
	public void rotateY(float arg0) {
		pg.rotateY(arg0);
	}

	@Override
	public void rotateZ(float arg0) {
		pg.rotateZ(arg0);
	}

	// public final float saturation(int arg0) {
	// return pg.saturation(arg0);
	// }

	@Override
	public boolean save(String arg0) {
		return pg.save(arg0);
	}

	@Override
	public void scale(float arg0, float arg1, float arg2) {
		pg.scale(arg0, arg1, arg2);
	}

	@Override
	public void scale(float arg0, float arg1) {
		pg.scale(arg0, arg1);
	}

	@Override
	public void scale(float arg0) {
		pg.scale(arg0);
	}

	@Override
	public float screenX(float arg0, float arg1, float arg2) {
		return pg.screenX(arg0, arg1, arg2);
	}

	@Override
	public float screenX(float arg0, float arg1) {
		return pg.screenX(arg0, arg1);
	}

	@Override
	public float screenY(float arg0, float arg1, float arg2) {
		return pg.screenY(arg0, arg1, arg2);
	}

	@Override
	public float screenY(float arg0, float arg1) {
		return pg.screenY(arg0, arg1);
	}

	@Override
	public float screenZ(float arg0, float arg1, float arg2) {
		return pg.screenZ(arg0, arg1, arg2);
	}

	@Override
	public void set(int arg0, int arg1, int arg2) {
		pg.set(arg0, arg1, arg2);
	}

	@Override
	public void set(int arg0, int arg1, PImage arg2) {
		pg.set(arg0, arg1, arg2);
	}

	@Override
	public void setMatrix(PMatrix arg0) {
		pg.setMatrix(arg0);
	}

	@Override
	public void setMatrix(PMatrix2D arg0) {
		pg.setMatrix(arg0);
	}

	@Override
	public void setMatrix(PMatrix3D arg0) {
		pg.setMatrix(arg0);
	}

	@Override
	public void setModified() {
		pg.setModified();
	}

	@Override
	public void setModified(boolean arg0) {
		pg.setModified(arg0);
	}

	@Override
	public void setParent(PApplet arg0) {
		pg.setParent(arg0);
	}

	@Override
	public void setPath(String arg0) {
		pg.setPath(arg0);
	}

	@Override
	public void setPrimary(boolean arg0) {
		pg.setPrimary(arg0);
	}

	@Override
	public void setSize(int arg0, int arg1) {
		pg.setSize(arg0, arg1);
	}

	@Override
	public void shape(PShape arg0, float arg1, float arg2, float arg3, float arg4) {
		pg.shape(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void shape(PShape arg0, float arg1, float arg2) {
		pg.shape(arg0, arg1, arg2);
	}

	@Override
	public void shape(PShape arg0) {
		pg.shape(arg0);
	}

	@Override
	public void shapeMode(int arg0) {
		pg.shapeMode(arg0);
	}

	@Override
	public void shininess(float arg0) {
		pg.shininess(arg0);
	}

	@Override
	public void smooth() {
		pg.smooth();
	}

	@Override
	public void specular(float arg0, float arg1, float arg2) {
		pg.specular(arg0, arg1, arg2);
	}

	@Override
	public void specular(float arg0) {
		pg.specular(arg0);
	}

	@Override
	public void specular(int arg0) {
		pg.specular(arg0);
	}

	@Override
	public void sphere(float arg0) {
		pg.sphere(arg0);
	}

	@Override
	public void sphereDetail(int arg0, int arg1) {
		pg.sphereDetail(arg0, arg1);
	}

	@Override
	public void sphereDetail(int arg0) {
		pg.sphereDetail(arg0);
	}

	@Override
	public void spotLight(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8, float arg9, float arg10) {
		pg.spotLight(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10);
	}

	@Override
	public void stroke(float arg0, float arg1, float arg2, float arg3) {
		pg.stroke(arg0, arg1, arg2, arg3);
	}

	@Override
	public void stroke(float arg0, float arg1, float arg2) {
		pg.stroke(arg0, arg1, arg2);
	}

	@Override
	public void stroke(float arg0, float arg1) {
		pg.stroke(arg0, arg1);
	}

	@Override
	public void stroke(float arg0) {
		pg.stroke(arg0);
	}

	@Override
	public void stroke(int arg0, float arg1) {
		pg.stroke(arg0, arg1);
	}

	@Override
	public void stroke(int arg0) {
		pg.stroke(arg0);
	}

	@Override
	public void strokeCap(int arg0) {
		pg.strokeCap(arg0);
	}

	@Override
	public void strokeJoin(int arg0) {
		pg.strokeJoin(arg0);
	}

	@Override
	public void strokeWeight(float arg0) {
		pg.strokeWeight(arg0);
	}

	@Override
	public void style(PStyle arg0) {
		pg.style(arg0);
	}

	@Override
	public void text(char arg0, float arg1, float arg2, float arg3) {
		pg.text(arg0, arg1, arg2, arg3);
	}

	@Override
	public void text(char arg0, float arg1, float arg2) {
		pg.text(arg0, arg1, arg2);
	}

	@Override
	public void text(char[] arg0, int arg1, int arg2, float arg3, float arg4, float arg5) {
		pg.text(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void text(char[] arg0, int arg1, int arg2, float arg3, float arg4) {
		pg.text(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void text(float arg0, float arg1, float arg2, float arg3) {
		pg.text(arg0, arg1, arg2, arg3);
	}

	@Override
	public void text(float arg0, float arg1, float arg2) {
		pg.text(arg0, arg1, arg2);
	}

	@Override
	public void text(int arg0, float arg1, float arg2, float arg3) {
		pg.text(arg0, arg1, arg2, arg3);
	}

	@Override
	public void text(int arg0, float arg1, float arg2) {
		pg.text(arg0, arg1, arg2);
	}

	@Override
	public void text(String arg0, float arg1, float arg2, float arg3, float arg4) {
		pg.text(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void text(String arg0, float arg1, float arg2, float arg3) {
		pg.text(arg0, arg1, arg2, arg3);
	}

	@Override
	public void text(String arg0, float arg1, float arg2) {
		pg.text(arg0, arg1, arg2);
	}

	@Override
	public void textAlign(int arg0, int arg1) {
		pg.textAlign(arg0, arg1);
	}

	@Override
	public void textAlign(int arg0) {
		pg.textAlign(arg0);
	}

	@Override
	public float textAscent() {
		return pg.textAscent();
	}

	@Override
	public float textDescent() {
		return pg.textDescent();
	}

	@Override
	public void textFont(PFont arg0, float arg1) {
		pg.textFont(arg0, arg1);
	}

	@Override
	public void textFont(PFont arg0) {
		pg.textFont(arg0);
	}

	@Override
	public void textLeading(float arg0) {
		pg.textLeading(arg0);
	}

	@Override
	public void textMode(int arg0) {
		pg.textMode(arg0);
	}

	@Override
	public void textSize(float arg0) {
		pg.textSize(arg0);
	}

	@Override
	public float textWidth(char arg0) {
		return pg.textWidth(arg0);
	}

	@Override
	public float textWidth(char[] arg0, int arg1, int arg2) {
		return pg.textWidth(arg0, arg1, arg2);
	}

	@Override
	public float textWidth(String arg0) {
		return pg.textWidth(arg0);
	}

	@Override
	public void texture(PImage arg0) {
		pg.texture(arg0);
	}

	@Override
	public void textureMode(int arg0) {
		pg.textureMode(arg0);
	}

	@Override
	public void tint(float arg0, float arg1, float arg2, float arg3) {
		pg.tint(arg0, arg1, arg2, arg3);
	}

	@Override
	public void tint(float arg0, float arg1, float arg2) {
		pg.tint(arg0, arg1, arg2);
	}

	@Override
	public void tint(float arg0, float arg1) {
		pg.tint(arg0, arg1);
	}

	@Override
	public void tint(float arg0) {
		pg.tint(arg0);
	}

	@Override
	public void tint(int arg0, float arg1) {
		pg.tint(arg0, arg1);
	}

	@Override
	public void tint(int arg0) {
		pg.tint(arg0);
	}

	@Override
	public void translate(float arg0, float arg1, float arg2) {
		pg.translate(arg0, arg1, arg2);
	}

	@Override
	public void translate(float arg0, float arg1) {
		pg.translate(arg0, arg1);
	}

	@Override
	public void triangle(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.triangle(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void updatePixels() {
		pg.updatePixels();
	}

	@Override
	public void updatePixels(int arg0, int arg1, int arg2, int arg3) {
		pg.updatePixels(arg0, arg1, arg2, arg3);
	}

	@Override
	public void vertex(float arg0, float arg1, float arg2, float arg3, float arg4) {
		pg.vertex(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void vertex(float arg0, float arg1, float arg2, float arg3) {
		pg.vertex(arg0, arg1, arg2, arg3);
	}

	@Override
	public void vertex(float arg0, float arg1, float arg2) {
		pg.vertex(arg0, arg1, arg2);
	}

	@Override
	public void vertex(float arg0, float arg1) {
		pg.vertex(arg0, arg1);
	}

	@Override
	public void vertex(float[] arg0) {
		pg.vertex(arg0);
	}
}
