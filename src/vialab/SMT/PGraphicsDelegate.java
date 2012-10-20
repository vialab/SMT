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

class PGraphicsDelegate {
	protected PGraphics pg;

	// PGraphicsDelegate(PGraphics g) {
	// this.pg = g;
	// }
	//
	public final float alpha(int arg0) {
		return pg.alpha(arg0);
	}

	public void ambient(float arg0, float arg1, float arg2) {
		pg.ambient(arg0, arg1, arg2);
	}

	public void ambient(float arg0) {
		pg.ambient(arg0);
	}

	public void ambient(int arg0) {
		pg.ambient(arg0);
	}

	public void ambientLight(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.ambientLight(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public void ambientLight(float arg0, float arg1, float arg2) {
		pg.ambientLight(arg0, arg1, arg2);
	}

	public void applyMatrix(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12,
			float arg13, float arg14, float arg15) {
		pg.applyMatrix(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11,
				arg12, arg13, arg14, arg15);
	}

	public void applyMatrix(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.applyMatrix(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public void applyMatrix(PMatrix arg0) {
		pg.applyMatrix(arg0);
	}

	public void applyMatrix(PMatrix2D arg0) {
		pg.applyMatrix(arg0);
	}

	public void applyMatrix(PMatrix3D arg0) {
		pg.applyMatrix(arg0);
	}

	public void arc(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.arc(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public void background(float arg0, float arg1, float arg2, float arg3) {
		pg.background(arg0, arg1, arg2, arg3);
	}

	public void background(float arg0, float arg1, float arg2) {
		pg.background(arg0, arg1, arg2);
	}

	public void background(float arg0, float arg1) {
		pg.background(arg0, arg1);
	}

	public void background(float arg0) {
		pg.background(arg0);
	}

	public void background(int arg0, float arg1) {
		pg.background(arg0, arg1);
	}

	public void background(int arg0) {
		pg.background(arg0);
	}

	public void background(PImage arg0) {
		pg.background(arg0);
	}

	public void beginCamera() {
		pg.beginCamera();
	}

	protected void beginDraw() {
		pg.beginDraw();
	}

	public void beginRaw(PGraphics arg0) {
		pg.beginRaw(arg0);
	}

	public void beginShape() {
		pg.beginShape();
	}

	public void beginShape(int arg0) {
		pg.beginShape(arg0);
	}

	public void bezier(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8, float arg9, float arg10, float arg11) {
		pg.bezier(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
	}

	public void bezier(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		pg.bezier(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	public void bezierDetail(int arg0) {
		pg.bezierDetail(arg0);
	}

	public float bezierPoint(float arg0, float arg1, float arg2, float arg3, float arg4) {
		return pg.bezierPoint(arg0, arg1, arg2, arg3, arg4);
	}

	public float bezierTangent(float arg0, float arg1, float arg2, float arg3, float arg4) {
		return pg.bezierTangent(arg0, arg1, arg2, arg3, arg4);
	}

	public void bezierVertex(float arg0, float arg1, float arg2, float arg3, float arg4,
			float arg5, float arg6, float arg7, float arg8) {
		pg.bezierVertex(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	public void bezierVertex(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.bezierVertex(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public void blend(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6,
			int arg7, int arg8) {
		pg.blend(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	public void blend(PImage arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6,
			int arg7, int arg8, int arg9) {
		pg.blend(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
	}

	public final float blue(int arg0) {
		return pg.blue(arg0);
	}

	public void box(float arg0, float arg1, float arg2) {
		pg.box(arg0, arg1, arg2);
	}

	public void box(float arg0) {
		pg.box(arg0);
	}

	public final float brightness(int arg0) {
		return pg.brightness(arg0);
	}

	public void camera() {
		pg.camera();
	}

	public void camera(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8) {
		pg.camera(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	public boolean canDraw() {
		return pg.canDraw();
	}

	public final int color(float arg0, float arg1, float arg2, float arg3) {
		return pg.color(arg0, arg1, arg2, arg3);
	}

	public final int color(float arg0, float arg1, float arg2) {
		return pg.color(arg0, arg1, arg2);
	}

	public final int color(float arg0, float arg1) {
		return pg.color(arg0, arg1);
	}

	public final int color(float arg0) {
		return pg.color(arg0);
	}

	public final int color(int arg0, float arg1) {
		return pg.color(arg0, arg1);
	}

	public final int color(int arg0, int arg1, int arg2, int arg3) {
		return pg.color(arg0, arg1, arg2, arg3);
	}

	public final int color(int arg0, int arg1, int arg2) {
		return pg.color(arg0, arg1, arg2);
	}

	public final int color(int arg0, int arg1) {
		return pg.color(arg0, arg1);
	}

	public final int color(int arg0) {
		return pg.color(arg0);
	}

	public void colorMode(int arg0, float arg1, float arg2, float arg3, float arg4) {
		pg.colorMode(arg0, arg1, arg2, arg3, arg4);
	}

	public void colorMode(int arg0, float arg1, float arg2, float arg3) {
		pg.colorMode(arg0, arg1, arg2, arg3);
	}

	public void colorMode(int arg0, float arg1) {
		pg.colorMode(arg0, arg1);
	}

	public void colorMode(int arg0) {
		pg.colorMode(arg0);
	}

	public void copy(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7) {
		pg.copy(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	public void copy(PImage arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6,
			int arg7, int arg8) {
		pg.copy(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	public void curve(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8, float arg9, float arg10, float arg11) {
		pg.curve(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
	}

	public void curve(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		pg.curve(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	public void curveDetail(int arg0) {
		pg.curveDetail(arg0);
	}

	public float curvePoint(float arg0, float arg1, float arg2, float arg3, float arg4) {
		return pg.curvePoint(arg0, arg1, arg2, arg3, arg4);
	}

	public float curveTangent(float arg0, float arg1, float arg2, float arg3, float arg4) {
		return pg.curveTangent(arg0, arg1, arg2, arg3, arg4);
	}

	public void curveTightness(float arg0) {
		pg.curveTightness(arg0);
	}

	public void curveVertex(float arg0, float arg1, float arg2) {
		pg.curveVertex(arg0, arg1, arg2);
	}

	public void curveVertex(float arg0, float arg1) {
		pg.curveVertex(arg0, arg1);
	}

	public void directionalLight(float arg0, float arg1, float arg2, float arg3, float arg4,
			float arg5) {
		pg.directionalLight(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public boolean displayable() {
		return pg.displayable();
	}

	public void dispose() {
		pg.dispose();
	}

	public void edge(boolean arg0) {
		pg.edge(arg0);
	}

	public void ellipse(float arg0, float arg1, float arg2, float arg3) {
		pg.ellipse(arg0, arg1, arg2, arg3);
	}

	public void ellipseMode(int arg0) {
		pg.ellipseMode(arg0);
	}

	public void emissive(float arg0, float arg1, float arg2) {
		pg.emissive(arg0, arg1, arg2);
	}

	public void emissive(float arg0) {
		pg.emissive(arg0);
	}

	public void emissive(int arg0) {
		pg.emissive(arg0);
	}

	public void endCamera() {
		pg.endCamera();
	}

	protected void endDraw() {
		pg.endDraw();
	}

	public void endRaw() {
		pg.endRaw();
	}

	public void endShape() {
		pg.endShape();
	}

	public void endShape(int arg0) {
		pg.endShape(arg0);
	}

	public void fill(float arg0, float arg1, float arg2, float arg3) {
		pg.fill(arg0, arg1, arg2, arg3);
	}

	public void fill(float arg0, float arg1, float arg2) {
		pg.fill(arg0, arg1, arg2);
	}

	public void fill(float arg0, float arg1) {
		pg.fill(arg0, arg1);
	}

	public void fill(float arg0) {
		pg.fill(arg0);
	}

	public void fill(int arg0, float arg1) {
		pg.fill(arg0, arg1);
	}

	public void fill(int arg0) {
		pg.fill(arg0);
	}

	public void filter(int arg0, float arg1) {
		pg.filter(arg0, arg1);
	}

	public void filter(int arg0) {
		pg.filter(arg0);
	}

	public void flush() {
		pg.flush();
	}

	public void frustum(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.frustum(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public PImage get() {
		return pg.get();
	}

	public PImage get(int arg0, int arg1, int arg2, int arg3) {
		return pg.get(arg0, arg1, arg2, arg3);
	}

	public int get(int arg0, int arg1) {
		return pg.get(arg0, arg1);
	}

	public Object getCache(PGraphics arg0) {
		return pg.getCache(arg0);
	}

	public PMatrix getMatrix() {
		return pg.getMatrix();
	}

	public PMatrix2D getMatrix(PMatrix2D arg0) {
		return pg.getMatrix(arg0);
	}

	public PMatrix3D getMatrix(PMatrix3D arg0) {
		return pg.getMatrix(arg0);
	}

	public PStyle getStyle() {
		return pg.getStyle();
	}

	public PStyle getStyle(PStyle arg0) {
		return pg.getStyle(arg0);
	}

	public final float green(int arg0) {
		return pg.green(arg0);
	}

	public void hint(int arg0) {
		pg.hint(arg0);
	}

	public final float hue(int arg0) {
		return pg.hue(arg0);
	}

	public void image(PImage arg0, float arg1, float arg2, float arg3, float arg4, int arg5,
			int arg6, int arg7, int arg8) {
		pg.image(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	public void image(PImage arg0, float arg1, float arg2, float arg3, float arg4) {
		pg.image(arg0, arg1, arg2, arg3, arg4);
	}

	public void image(PImage arg0, float arg1, float arg2) {
		pg.image(arg0, arg1, arg2);
	}

	public void imageMode(int arg0) {
		pg.imageMode(arg0);
	}

	public void init(int arg0, int arg1, int arg2) {
		pg.init(arg0, arg1, arg2);
	}

	public boolean is2D() {
		return pg.is2D();
	}

	public boolean is3D() {
		return pg.is3D();
	}

	public boolean isModified() {
		return pg.isModified();
	}

	public int lerpColor(int arg0, int arg1, float arg2) {
		return pg.lerpColor(arg0, arg1, arg2);
	}

	public void lightFalloff(float arg0, float arg1, float arg2) {
		pg.lightFalloff(arg0, arg1, arg2);
	}

	public void lightSpecular(float arg0, float arg1, float arg2) {
		pg.lightSpecular(arg0, arg1, arg2);
	}

	public void lights() {
		pg.lights();
	}

	public void line(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.line(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public void line(float arg0, float arg1, float arg2, float arg3) {
		pg.line(arg0, arg1, arg2, arg3);
	}

	public void loadPixels() {
		pg.loadPixels();
	}

	public void mask(int[] arg0) {
		pg.mask(arg0);
	}

	public void mask(PImage arg0) {
		pg.mask(arg0);
	}

	public float modelX(float arg0, float arg1, float arg2) {
		return pg.modelX(arg0, arg1, arg2);
	}

	public float modelY(float arg0, float arg1, float arg2) {
		return pg.modelY(arg0, arg1, arg2);
	}

	public float modelZ(float arg0, float arg1, float arg2) {
		return pg.modelZ(arg0, arg1, arg2);
	}

	public void noFill() {
		pg.noFill();
	}

	public void noLights() {
		pg.noLights();
	}

	public void noSmooth() {
		pg.noSmooth();
	}

	public void noStroke() {
		pg.noStroke();
	}

	public void noTint() {
		pg.noTint();
	}

	public void normal(float arg0, float arg1, float arg2) {
		pg.normal(arg0, arg1, arg2);
	}

	public void ortho() {
		pg.ortho();
	}

	public void ortho(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.ortho(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public void perspective() {
		pg.perspective();
	}

	public void perspective(float arg0, float arg1, float arg2, float arg3) {
		pg.perspective(arg0, arg1, arg2, arg3);
	}

	public void point(float arg0, float arg1, float arg2) {
		pg.point(arg0, arg1, arg2);
	}

	public void point(float arg0, float arg1) {
		pg.point(arg0, arg1);
	}

	public void pointLight(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.pointLight(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public void popMatrix() {
		pg.popMatrix();
	}

	public void popStyle() {
		pg.popStyle();
	}

	public void printCamera() {
		pg.printCamera();
	}

	public void printMatrix() {
		pg.printMatrix();
	}

	public void printProjection() {
		pg.printProjection();
	}

	public void pushMatrix() {
		pg.pushMatrix();
	}

	public void pushStyle() {
		pg.pushStyle();
	}

	public void quad(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		pg.quad(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	public void rect(float arg0, float arg1, float arg2, float arg3) {
		pg.rect(arg0, arg1, arg2, arg3);
	}

	public void rect(float arg0, float arg1, float arg2, float arg3, float arg4) {
		pg.rect(arg0, arg1, arg2, arg3, arg4);
	}

	public void rect(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		pg.rect(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	public void rectMode(int arg0) {
		pg.rectMode(arg0);
	}

	public final float red(int arg0) {
		return pg.red(arg0);
	}

	public void removeCache(PGraphics arg0) {
		pg.removeCache(arg0);
	}

	public void resetMatrix() {
		pg.resetMatrix();
	}

	public void resize(int arg0, int arg1) {
		pg.resize(arg0, arg1);
	}

	public void rotate(float arg0, float arg1, float arg2, float arg3) {
		pg.rotate(arg0, arg1, arg2, arg3);
	}

	public void rotate(float arg0) {
		pg.rotate(arg0);
	}

	public void rotateX(float arg0) {
		pg.rotateX(arg0);
	}

	public void rotateY(float arg0) {
		pg.rotateY(arg0);
	}

	public void rotateZ(float arg0) {
		pg.rotateZ(arg0);
	}

	public final float saturation(int arg0) {
		return pg.saturation(arg0);
	}

	public void save(String arg0) {
		pg.save(arg0);
	}

	public void scale(float arg0, float arg1, float arg2) {
		pg.scale(arg0, arg1, arg2);
	}

	public void scale(float arg0, float arg1) {
		pg.scale(arg0, arg1);
	}

	public void scale(float arg0) {
		pg.scale(arg0);
	}

	public float screenX(float arg0, float arg1, float arg2) {
		return pg.screenX(arg0, arg1, arg2);
	}

	public float screenX(float arg0, float arg1) {
		return pg.screenX(arg0, arg1);
	}

	public float screenY(float arg0, float arg1, float arg2) {
		return pg.screenY(arg0, arg1, arg2);
	}

	public float screenY(float arg0, float arg1) {
		return pg.screenY(arg0, arg1);
	}

	public float screenZ(float arg0, float arg1, float arg2) {
		return pg.screenZ(arg0, arg1, arg2);
	}

	public void set(int arg0, int arg1, int arg2) {
		pg.set(arg0, arg1, arg2);
	}

	public void set(int arg0, int arg1, PImage arg2) {
		pg.set(arg0, arg1, arg2);
	}

	public void setCache(PGraphics arg0, Object arg1) {
		pg.setCache(arg0, arg1);
	}

	public void setMatrix(PMatrix arg0) {
		pg.setMatrix(arg0);
	}

	public void setMatrix(PMatrix2D arg0) {
		pg.setMatrix(arg0);
	}

	public void setMatrix(PMatrix3D arg0) {
		pg.setMatrix(arg0);
	}

	public void setModified() {
		pg.setModified();
	}

	public void setModified(boolean arg0) {
		pg.setModified(arg0);
	}

	public void setParent(PApplet arg0) {
		pg.setParent(arg0);
	}

	public void setPath(String arg0) {
		pg.setPath(arg0);
	}

	public void setPrimary(boolean arg0) {
		pg.setPrimary(arg0);
	}

	public void setSize(int arg0, int arg1) {
		pg.setSize(arg0, arg1);
	}

	public void shape(PShape arg0, float arg1, float arg2, float arg3, float arg4) {
		pg.shape(arg0, arg1, arg2, arg3, arg4);
	}

	public void shape(PShape arg0, float arg1, float arg2) {
		pg.shape(arg0, arg1, arg2);
	}

	public void shape(PShape arg0) {
		pg.shape(arg0);
	}

	public void shapeMode(int arg0) {
		pg.shapeMode(arg0);
	}

	public void shininess(float arg0) {
		pg.shininess(arg0);
	}

	public void smooth() {
		pg.smooth();
	}

	public void specular(float arg0, float arg1, float arg2) {
		pg.specular(arg0, arg1, arg2);
	}

	public void specular(float arg0) {
		pg.specular(arg0);
	}

	public void specular(int arg0) {
		pg.specular(arg0);
	}

	public void sphere(float arg0) {
		pg.sphere(arg0);
	}

	public void sphereDetail(int arg0, int arg1) {
		pg.sphereDetail(arg0, arg1);
	}

	public void sphereDetail(int arg0) {
		pg.sphereDetail(arg0);
	}

	public void spotLight(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8, float arg9, float arg10) {
		pg.spotLight(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10);
	}

	public void stroke(float arg0, float arg1, float arg2, float arg3) {
		pg.stroke(arg0, arg1, arg2, arg3);
	}

	public void stroke(float arg0, float arg1, float arg2) {
		pg.stroke(arg0, arg1, arg2);
	}

	public void stroke(float arg0, float arg1) {
		pg.stroke(arg0, arg1);
	}

	public void stroke(float arg0) {
		pg.stroke(arg0);
	}

	public void stroke(int arg0, float arg1) {
		pg.stroke(arg0, arg1);
	}

	public void stroke(int arg0) {
		pg.stroke(arg0);
	}

	public void strokeCap(int arg0) {
		pg.strokeCap(arg0);
	}

	public void strokeJoin(int arg0) {
		pg.strokeJoin(arg0);
	}

	public void strokeWeight(float arg0) {
		pg.strokeWeight(arg0);
	}

	public void style(PStyle arg0) {
		pg.style(arg0);
	}

	public void text(char arg0, float arg1, float arg2, float arg3) {
		pg.text(arg0, arg1, arg2, arg3);
	}

	public void text(char arg0, float arg1, float arg2) {
		pg.text(arg0, arg1, arg2);
	}

	public void text(char[] arg0, int arg1, int arg2, float arg3, float arg4, float arg5) {
		pg.text(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public void text(char[] arg0, int arg1, int arg2, float arg3, float arg4) {
		pg.text(arg0, arg1, arg2, arg3, arg4);
	}

	public void text(float arg0, float arg1, float arg2, float arg3) {
		pg.text(arg0, arg1, arg2, arg3);
	}

	public void text(float arg0, float arg1, float arg2) {
		pg.text(arg0, arg1, arg2);
	}

	public void text(int arg0, float arg1, float arg2, float arg3) {
		pg.text(arg0, arg1, arg2, arg3);
	}

	public void text(int arg0, float arg1, float arg2) {
		pg.text(arg0, arg1, arg2);
	}

	public void text(String arg0, float arg1, float arg2, float arg3, float arg4) {
		pg.text(arg0, arg1, arg2, arg3, arg4);
	}

	public void text(String arg0, float arg1, float arg2, float arg3) {
		pg.text(arg0, arg1, arg2, arg3);
	}

	public void text(String arg0, float arg1, float arg2) {
		pg.text(arg0, arg1, arg2);
	}

	public void textAlign(int arg0, int arg1) {
		pg.textAlign(arg0, arg1);
	}

	public void textAlign(int arg0) {
		pg.textAlign(arg0);
	}

	public float textAscent() {
		return pg.textAscent();
	}

	public float textDescent() {
		return pg.textDescent();
	}

	public void textFont(PFont arg0, float arg1) {
		pg.textFont(arg0, arg1);
	}

	public void textFont(PFont arg0) {
		pg.textFont(arg0);
	}

	public void textLeading(float arg0) {
		pg.textLeading(arg0);
	}

	public void textMode(int arg0) {
		pg.textMode(arg0);
	}

	public void textSize(float arg0) {
		pg.textSize(arg0);
	}

	public float textWidth(char arg0) {
		return pg.textWidth(arg0);
	}

	public float textWidth(char[] arg0, int arg1, int arg2) {
		return pg.textWidth(arg0, arg1, arg2);
	}

	public float textWidth(String arg0) {
		return pg.textWidth(arg0);
	}

	public void texture(PImage arg0) {
		pg.texture(arg0);
	}

	public void textureMode(int arg0) {
		pg.textureMode(arg0);
	}

	public void tint(float arg0, float arg1, float arg2, float arg3) {
		pg.tint(arg0, arg1, arg2, arg3);
	}

	public void tint(float arg0, float arg1, float arg2) {
		pg.tint(arg0, arg1, arg2);
	}

	public void tint(float arg0, float arg1) {
		pg.tint(arg0, arg1);
	}

	public void tint(float arg0) {
		pg.tint(arg0);
	}

	public void tint(int arg0, float arg1) {
		pg.tint(arg0, arg1);
	}

	public void tint(int arg0) {
		pg.tint(arg0);
	}

	public void translate(float arg0, float arg1, float arg2) {
		pg.translate(arg0, arg1, arg2);
	}

	public void translate(float arg0, float arg1) {
		pg.translate(arg0, arg1);
	}

	public void triangle(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		pg.triangle(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public void updatePixels() {
		pg.updatePixels();
	}

	public void updatePixels(int arg0, int arg1, int arg2, int arg3) {
		pg.updatePixels(arg0, arg1, arg2, arg3);
	}

	public void vertex(float arg0, float arg1, float arg2, float arg3, float arg4) {
		pg.vertex(arg0, arg1, arg2, arg3, arg4);
	}

	public void vertex(float arg0, float arg1, float arg2, float arg3) {
		pg.vertex(arg0, arg1, arg2, arg3);
	}

	public void vertex(float arg0, float arg1, float arg2) {
		pg.vertex(arg0, arg1, arg2);
	}

	public void vertex(float arg0, float arg1) {
		pg.vertex(arg0, arg1);
	}

	public void vertex(float[] arg0) {
		pg.vertex(arg0);
	}
}
