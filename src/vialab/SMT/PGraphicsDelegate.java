package vialab.SMT;

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

	protected PGraphics pg;

	@Override
	public void beginContour() {
		if(pg == this){
			super.beginContour();
			return;
		}
		pg.beginContour();
	}

	@Override
	public PGL beginPGL() {
		if(pg == this){
			return super.beginPGL();
		}
		return pg.beginPGL();
	}

	@Override
	public void blendMode(int mode) {
		if(pg == this){
			super.blendMode(mode);
			return;
		}
		pg.blendMode(mode);
	}

	@Override
	public void endContour() {
		if(pg == this){
			super.endContour();
			return;
		}
		pg.endContour();
	}

	@Override
	public void endPGL() {
		if(pg == this){
			super.endPGL();
			return;
		}
		pg.endPGL();
	}

	@Override
	public void filter(PShader shader) {
		if(pg == this){
			super.filter(shader);
			return;
		}
		pg.filter(shader);
	}

	@Override
	public void noClip() {
		if(pg == this){
			super.noClip();
			return;
		}
		pg.noClip();
	}

	@Override
	public void ortho(float left, float right, float bottom, float top) {
		if(pg == this){
			super.ortho(left, right, bottom, top);
			return;
		}
		pg.ortho(left, right, bottom, top);
	}

	@Override
	public void quadraticVertex(float cx, float cy, float cz, float x3, float y3, float z3) {
		if(pg == this){
			super.quadraticVertex(cx, cy, cz, x3, y3, z3);
			return;
		}
		pg.quadraticVertex(cx, cy, cz, x3, y3, z3);
	}

	@Override
	public void quadraticVertex(float cx, float cy, float x3, float y3) {
		if(pg == this){
			super.quadraticVertex(cx, cy, x3, y3);
			return;
		}
		pg.quadraticVertex(cx, cy, x3, y3);
	}

	@Override
	public void requestDraw() {
		if(pg == this){
			super.requestDraw();
			return;
		}
		pg.requestDraw();
	}

	@Override
	public void resetShader() {
		if(pg == this){
			super.resetShader();
			return;
		}
		pg.resetShader();
	}

	@Override
	public void resetShader(int kind) {
		if(pg == this){
			super.resetShader(kind);
			return;
		}
		pg.resetShader(kind);
	}

	@Override
	public void setFrameRate(float frameRate) {
		if(pg == this){
			super.setFrameRate(frameRate);
			return;
		}
		pg.setFrameRate(frameRate);
	}

	@Override
	public void shader(PShader shader, int kind) {
		if(pg == this){
			super.shader(shader, kind);
			return;
		}
		pg.shader(shader, kind);
	}

	@Override
	public void shader(PShader shader) {
		if(pg == this){
			super.shader(shader);
			return;
		}
		pg.shader(shader);
	}

	@Override
	public void shearX(float angle) {
		if(pg == this){
			super.shearX(angle);
			return;
		}
		pg.shearX(angle);
	}

	@Override
	public void shearY(float angle) {
		if(pg == this){
			super.shearY(angle);
			return;
		}
		pg.shearY(angle);
	}

	@Override
	public void smooth(int level) {
		if(pg == this){
			super.smooth(level);
			return;
		}
		pg.smooth(level);
	}

	@Override
	public void textureWrap(int wrap) {
		if(pg == this){
			super.textureWrap(wrap);
			return;
		}
		pg.textureWrap(wrap);
	}

	@Override
	public void arc(float a, float b, float c, float d, float start, float stop, int mode) {
		if(pg == this){
			super.arc(a, b, c, d, start, stop, mode);
			return;
		}
		pg.arc(a, b, c, d, start, stop, mode);
	}

	@Override
	public void clear() {
		if(pg == this){
			super.clear();
			return;
		}
		pg.clear();
	}

	@Override
	public void clip(float a, float b, float c, float d) {
		if(pg == this){
			super.clip(a, b, c, d);
			return;
		}
		pg.clip(a, b, c, d);
	}

	@Override
	public void noTexture() {
		if(pg == this){
			super.noTexture();
			return;
		}
		pg.noTexture();
	}

	@Override
	public void removeCache(PImage image) {
		if(pg == this){
			super.removeCache(image);
			return;
		}
		pg.removeCache(image);
	}

	@Override
	public void setCache(PImage image, Object storage) {
		if(pg == this){
			super.setCache(image,storage);
			return;
		}
		pg.setCache(image, storage);
	}

	@Override
	public void ambient(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.ambient(arg0, arg1, arg2);
			return;
		}
		pg.ambient(arg0, arg1, arg2);
	}

	@Override
	public void ambient(float arg0) {
		if(pg == this){
			super.ambient(arg0);
			return;
		}
		pg.ambient(arg0);
	}

	@Override
	public void ambient(int arg0) {
		if(pg == this){
			super.ambient(arg0);
			return;
		}
		pg.ambient(arg0);
	}

	@Override
	public void ambientLight(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		if(pg == this){
			super.ambientLight(arg0, arg1, arg2, arg3, arg4, arg5);
			return;
		}
		pg.ambientLight(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void ambientLight(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.ambientLight(arg0, arg1, arg2);
			return;
		}
		pg.ambientLight(arg0, arg1, arg2);
	}

	@Override
	public void applyMatrix(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12,
			float arg13, float arg14, float arg15) {
		if(pg == this){
			super.applyMatrix(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11,
					arg12, arg13, arg14, arg15);
			return;
		}
		pg.applyMatrix(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11,
				arg12, arg13, arg14, arg15);
	}

	@Override
	public void applyMatrix(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		if(pg == this){
			super.applyMatrix(arg0, arg1, arg2, arg3, arg4, arg5);
			return;
		}
		pg.applyMatrix(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void applyMatrix(PMatrix arg0) {
		if(pg == this){
			super.applyMatrix(arg0);
			return;
		}
		pg.applyMatrix(arg0);
	}

	@Override
	public void applyMatrix(PMatrix2D arg0) {
		if(pg == this){
			super.applyMatrix(arg0);
			return;
		}
		pg.applyMatrix(arg0);
	}

	@Override
	public void applyMatrix(PMatrix3D arg0) {
		if(pg == this){
			super.applyMatrix(arg0);
			return;
		}
		pg.applyMatrix(arg0);
	}

	@Override
	public void arc(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		if(pg == this){
			super.arc(arg0, arg1, arg2, arg3, arg4, arg5);
			return;
		}
		pg.arc(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void background(float arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.background(arg0, arg1, arg2, arg3);
			return;
		}
		pg.background(arg0, arg1, arg2, arg3);
	}

	@Override
	public void background(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.background(arg0, arg1, arg2);
			return;
		}
		pg.background(arg0, arg1, arg2);
	}

	@Override
	public void background(float arg0, float arg1) {
		if(pg == this){
			super.background(arg0, arg1);
			return;
		}
		pg.background(arg0, arg1);
	}

	@Override
	public void background(float arg0) {
		if(pg == this){
			super.background(arg0);
			return;
		}
		pg.background(arg0);
	}

	@Override
	public void background(int arg0, float arg1) {
		if(pg == this){
			super.background(arg0, arg1);
			return;
		}
		pg.background(arg0, arg1);
	}

	@Override
	public void background(int arg0) {
		if(pg == this){
			super.background(arg0);
			return;
		}
		pg.background(arg0);
	}

	@Override
	public void background(PImage arg0) {
		if(pg == this){
			super.background(arg0);
			return;
		}
		pg.background(arg0);
	}

	@Override
	public void beginCamera() {
		if(pg == this){
			super.beginCamera();
			return;
		}
		pg.beginCamera();
	}

	@Override
	public void beginDraw() {
		if(pg == this){
			super.beginDraw();
			return;
		}
		pg.beginDraw();
	}

	@Override
	public void beginRaw(PGraphics arg0) {
		if(pg == this){
			super.beginRaw(arg0);
			return;
		}
		pg.beginRaw(arg0);
	}

	@Override
	public void beginShape() {
		if(pg == this){
			super.beginShape();
			return;
		}
		pg.beginShape();
	}

	@Override
	public void beginShape(int arg0) {
		if(pg == this){
			super.beginShape(arg0);
			return;
		}
		pg.beginShape(arg0);
	}

	@Override
	public void bezier(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8, float arg9, float arg10, float arg11) {
		if(pg == this){
			super.bezier(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
			return;
		}
		pg.bezier(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
	}

	@Override
	public void bezier(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		if(pg == this){
			super.bezier(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
			return;
		}
		pg.bezier(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public void bezierDetail(int arg0) {
		if(pg == this){
			super.bezierDetail(arg0);
			return;
		}
		pg.bezierDetail(arg0);
	}

	@Override
	public float bezierPoint(float arg0, float arg1, float arg2, float arg3, float arg4) {
		if(pg == this){
			return super.bezierPoint(arg0, arg1, arg2, arg3, arg4);
		}
		return pg.bezierPoint(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public float bezierTangent(float arg0, float arg1, float arg2, float arg3, float arg4) {
		if(pg == this){
			return super.bezierTangent(arg0, arg1, arg2, arg3, arg4);
		}
		return pg.bezierTangent(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void bezierVertex(float arg0, float arg1, float arg2, float arg3, float arg4,
			float arg5, float arg6, float arg7, float arg8) {
		if(pg == this){
			super.bezierVertex(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
			return;
		}
		pg.bezierVertex(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@Override
	public void bezierVertex(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		if(pg == this){
			super.bezierVertex(arg0, arg1, arg2, arg3, arg4, arg5);
			return;
		}
		pg.bezierVertex(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void blend(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6,
			int arg7, int arg8) {
		if(pg == this){
			super.blend(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
			return;
		}
		pg.blend(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@Override
	public void blend(PImage arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6,
			int arg7, int arg8, int arg9) {
		if(pg == this){
			super.blend(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
			return;
		}
		pg.blend(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
	}

	@Override
	public void box(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.box(arg0, arg1, arg2);
			return;
		}
		pg.box(arg0, arg1, arg2);
	}

	@Override
	public void box(float arg0) {
		if(pg == this){
			super.box(arg0);
			return;
		}
		pg.box(arg0);
	}

	@Override
	public void camera() {
		if(pg == this){
			super.camera();
			return;
		}
		pg.camera();
	}

	@Override
	public void camera(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8) {
		if(pg == this){
			super.camera(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
			return;
		}
		pg.camera(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@Override
	public void colorMode(int arg0, float arg1, float arg2, float arg3, float arg4) {
		if(pg == this){
			super.colorMode(arg0, arg1, arg2, arg3, arg4);
			return;
		}
		pg.colorMode(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void colorMode(int arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.colorMode(arg0, arg1, arg2, arg3);
			return;
		}
		pg.colorMode(arg0, arg1, arg2, arg3);
	}

	@Override
	public void colorMode(int arg0, float arg1) {
		if(pg == this){
			super.colorMode(arg0, arg1);
			return;
		}
		pg.colorMode(arg0, arg1);
	}

	@Override
	public void colorMode(int arg0) {
		if(pg == this){
			super.colorMode(arg0);
			return;
		}
		pg.colorMode(arg0);
	}

	@Override
	public void copy(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7) {
		if(pg == this){
			super.copy(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
			return;
		}
		pg.copy(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public void copy(PImage arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6,
			int arg7, int arg8) {
		if(pg == this){
			super.copy(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
			return;
		}
		pg.copy(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@Override
	public void curve(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8, float arg9, float arg10, float arg11) {
		if(pg == this){
			super.curve(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
			return;
		}
		pg.curve(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
	}

	@Override
	public void curve(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		if(pg == this){
			super.curve(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
			return;
		}
		pg.curve(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public void curveDetail(int arg0) {
		if(pg == this){
			super.curveDetail(arg0);
			return;
		}
		pg.curveDetail(arg0);
	}

	@Override
	public void curveTightness(float arg0) {
		if(pg == this){
			super.curveTightness(arg0);
			return;
		}
		pg.curveTightness(arg0);
	}

	@Override
	public void curveVertex(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.curveVertex(arg0, arg1, arg2);
			return;
		}
		pg.curveVertex(arg0, arg1, arg2);
	}

	@Override
	public void curveVertex(float arg0, float arg1) {
		if(pg == this){
			super.curveVertex(arg0, arg1);
			return;
		}
		pg.curveVertex(arg0, arg1);
	}

	@Override
	public void directionalLight(float arg0, float arg1, float arg2, float arg3, float arg4,
			float arg5) {
		if(pg == this){
			super.directionalLight(arg0, arg1, arg2, arg3, arg4, arg5);
			return;
		}
		pg.directionalLight(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void edge(boolean arg0) {
		if(pg == this){
			super.edge(arg0);
			return;
		}
		pg.edge(arg0);
	}

	@Override
	public void ellipse(float arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.ellipse(arg0, arg1, arg2, arg3);
			return;
		}
		pg.ellipse(arg0, arg1, arg2, arg3);
	}

	@Override
	public void ellipseMode(int arg0) {
		if(pg == this){
			super.ellipseMode(arg0);
			return;
		}
		pg.ellipseMode(arg0);
	}

	@Override
	public void emissive(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.emissive(arg0, arg1, arg2);
			return;
		}
		pg.emissive(arg0, arg1, arg2);
	}

	@Override
	public void emissive(float arg0) {
		if(pg == this){
			super.emissive(arg0);
			return;
		}
		pg.emissive(arg0);
	}

	@Override
	public void emissive(int arg0) {
		if(pg == this){
			super.emissive(arg0);
			return;
		}
		pg.emissive(arg0);
	}

	@Override
	public void endCamera() {
		if(pg == this){
			super.endCamera();
			return;
		}
		pg.endCamera();
	}

	@Override
	public void endDraw() {
		if(pg == this){
			super.endDraw();
			return;
		}
		pg.endDraw();
	}

	@Override
	public void endRaw() {
		if(pg == this){
			super.endRaw();
			return;
		}
		pg.endRaw();
	}

	@Override
	public void endShape() {
		if(pg == this){
			super.endShape();
			return;
		}
		pg.endShape();
	}

	@Override
	public void endShape(int arg0) {
		if(pg == this){
			super.endShape(arg0);
			return;
		}
		pg.endShape(arg0);
	}

	@Override
	public void fill(float arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.fill(arg0, arg1, arg2, arg3);
			return;
		}
		pg.fill(arg0, arg1, arg2, arg3);
	}

	@Override
	public void fill(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.fill(arg0, arg1, arg2);
			return;
		}
		pg.fill(arg0, arg1, arg2);
	}

	@Override
	public void fill(float arg0, float arg1) {
		if(pg == this){
			super.fill(arg0, arg1);
			return;
		}
		pg.fill(arg0, arg1);
	}

	@Override
	public void fill(float arg0) {
		if(pg == this){
			super.fill(arg0);
			return;
		}
		pg.fill(arg0);
	}

	@Override
	public void fill(int arg0, float arg1) {
		if(pg == this){
			super.fill(arg0, arg1);
			return;
		}
		pg.fill(arg0, arg1);
	}

	@Override
	public void fill(int arg0) {
		if(pg == this){
			super.fill(arg0);
			return;
		}
		pg.fill(arg0);
	}

	@Override
	public void filter(int arg0, float arg1) {
		if(pg == this){
			super.filter(arg0, arg1);
			return;
		}
		pg.filter(arg0, arg1);
	}

	@Override
	public void filter(int arg0) {
		if(pg == this){
			super.filter(arg0);
			return;
		}
		pg.filter(arg0);
	}

	@Override
	public void flush() {
		if(pg == this){
			super.flush();
			return;
		}
		pg.flush();
	}

	@Override
	public void frustum(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		if(pg == this){
			super.frustum(arg0, arg1, arg2, arg3, arg4, arg5);
			return;
		}
		pg.frustum(arg0, arg1, arg2, arg3, arg4, arg5);
	}
	
	@Override
	public PImage get() {
		if(pg == this){
			return super.get();
		}
		return pg.get();
	}

	@Override
	public PImage get(int arg0, int arg1, int arg2, int arg3) {
		if(pg == this){
			return super.get(arg0, arg1, arg2, arg3);
		}
		return pg.get(arg0, arg1, arg2, arg3);
	}

	@Override
	public int get(int arg0, int arg1) {
		if(pg == this){
			return super.get(arg0, arg1);
		}
		return pg.get(arg0, arg1);
	}

	@Override
	public PMatrix getMatrix() {
		if(pg == this){
			return super.getMatrix();
			
		}
		return pg.getMatrix();
	}

	@Override
	public PMatrix2D getMatrix(PMatrix2D arg0) {
		if(pg == this){
			return super.getMatrix(arg0);
		}
		return pg.getMatrix(arg0);
	}

	@Override
	public PMatrix3D getMatrix(PMatrix3D arg0) {
		if(pg == this){
			return super.getMatrix(arg0);
		}
		return pg.getMatrix(arg0);
	}

	@Override
	public PStyle getStyle() {
		if(pg == this){
			return super.getStyle();
		}
		return pg.getStyle();
	}

	@Override
	public PStyle getStyle(PStyle arg0) {
		if(pg == this){
			return super.getStyle(arg0);
		}
		return pg.getStyle(arg0);
	}

	@Override
	public void hint(int arg0) {
		if(pg == this){
			super.hint(arg0);
			return;
		}
		pg.hint(arg0);
	}

	@Override
	public void image(PImage arg0, float arg1, float arg2, float arg3, float arg4, int arg5,
			int arg6, int arg7, int arg8) {
		if(pg == this){
			super.image(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
			return;
		}
		pg.image(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@Override
	public void image(PImage arg0, float arg1, float arg2, float arg3, float arg4) {
		if(pg == this){
			super.image(arg0, arg1, arg2, arg3, arg4);
			return;
		}
		pg.image(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void image(PImage arg0, float arg1, float arg2) {
		if(pg == this){
			super.image(arg0, arg1, arg2);
			return;
		}
		pg.image(arg0, arg1, arg2);
	}

	@Override
	public void imageMode(int arg0) {
		if(pg == this){
			super.imageMode(arg0);
			return;
		}
		pg.imageMode(arg0);
	}

	@Override
	public void init(int arg0, int arg1, int arg2) {
		if(pg == this){
			super.init(arg0, arg1, arg2);
			return;
		}
		pg.init(arg0, arg1, arg2);
	}

	@Override
	public void lightFalloff(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.lightFalloff(arg0, arg1, arg2);
			return;
		}
		pg.lightFalloff(arg0, arg1, arg2);
	}

	@Override
	public void lightSpecular(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.lightSpecular(arg0, arg1, arg2);
			return;
		}
		pg.lightSpecular(arg0, arg1, arg2);
	}

	@Override
	public void lights() {
		if(pg == this){
			super.lights();
			return;
		}
		pg.lights();
	}

	@Override
	public void line(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		if(pg == this){
			super.line(arg0, arg1, arg2, arg3, arg4, arg5);
			return;
		}
		pg.line(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void line(float arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.line(arg0, arg1, arg2, arg3);
			return;
		}
		pg.line(arg0, arg1, arg2, arg3);
	}

	@Override
	public void loadPixels() {
		if(pg == this){
			super.loadPixels();
			return;
		}
		pg.loadPixels();
	}

	/**
	 * @deprecated
	 */
	@Override
	public void mask(int[] arg0) {
		if(pg == this){
			super.mask(arg0);
			return;
		}
		pg.mask(arg0);
	}

	@Override
	public void mask(PImage arg0) {
		if(pg == this){
			super.mask(arg0);
			return;
		}
		pg.mask(arg0);
	}

	@Override
	public float modelX(float arg0, float arg1, float arg2) {
		if(pg == this){
			return super.modelX(arg0, arg1, arg2);
		}
		return pg.modelX(arg0, arg1, arg2);
	}

	@Override
	public float modelY(float arg0, float arg1, float arg2) {
		if(pg == this){
			return super.modelY(arg0, arg1, arg2);
		}
		return pg.modelY(arg0, arg1, arg2);
	}

	@Override
	public float modelZ(float arg0, float arg1, float arg2) {
		if(pg == this){
			return super.modelZ(arg0, arg1, arg2);
		}
		return pg.modelZ(arg0, arg1, arg2);
	}

	@Override
	public void noFill() {
		if(pg == this){
			super.noFill();
			return;
		}
		pg.noFill();
	}

	@Override
	public void noLights() {
		if(pg == this){
			super.noLights();
			return;
		}
		pg.noLights();
	}

	@Override
	public void noSmooth() {
		if(pg == this){
			super.noSmooth();
			return;
		}
		pg.noSmooth();
	}

	@Override
	public void noStroke() {
		if(pg == this){
			super.noStroke();
			return;
		}
		pg.noStroke();
	}

	@Override
	public void noTint() {
		if(pg == this){
			super.noTint();
			return;
		}
		pg.noTint();
	}

	@Override
	public void normal(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.normal(arg0, arg1, arg2);
			return;
		}
		pg.normal(arg0, arg1, arg2);
	}

	@Override
	public void ortho() {
		if(pg == this){
			super.ortho();
			return;
		}
		pg.ortho();
	}

	@Override
	public void ortho(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		if(pg == this){
			super.ortho(arg0, arg1, arg2, arg3, arg4, arg5);
			return;
		}
		pg.ortho(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void perspective() {
		if(pg == this){
			super.perspective();
			return;
		}
		pg.perspective();
	}

	@Override
	public void perspective(float arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.perspective(arg0, arg1, arg2, arg3);
			return;
		}
		pg.perspective(arg0, arg1, arg2, arg3);
	}

	@Override
	public void point(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.point(arg0, arg1, arg2);
			return;
		}
		pg.point(arg0, arg1, arg2);
	}

	@Override
	public void point(float arg0, float arg1) {
		if(pg == this){
			super.point(arg0, arg1);
			return;
		}
		pg.point(arg0, arg1);
	}

	@Override
	public void pointLight(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		if(pg == this){
			super.pointLight(arg0, arg1, arg2, arg3, arg4, arg5);
			return;
		}
		pg.pointLight(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void popMatrix() {
		if(pg == this){
			super.popMatrix();
			return;
		}
		pg.popMatrix();
	}

	@Override
	public void popStyle() {
		if(pg == this){
			super.popStyle();
			return;
		}
		pg.popStyle();
	}

	@Override
	public void printCamera() {
		if(pg == this){
			super.printCamera();
			return;
		}
		pg.printCamera();
	}

	@Override
	public void printMatrix() {
		if(pg == this){
			super.printMatrix();
			return;
		}
		pg.printMatrix();
	}

	@Override
	public void printProjection() {
		if(pg == this){
			super.printProjection();
			return;
		}
		pg.printProjection();
	}

	@Override
	public void pushMatrix() {
		if(pg == this){
			super.pushMatrix();
			return;
		}
		pg.pushMatrix();
	}

	@Override
	public void pushStyle() {
		if(pg == this){
			super.pushStyle();
			return;
		}
		pg.pushStyle();
	}

	@Override
	public void quad(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		if(pg == this){
			super.quad(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
			return;
		}
		pg.quad(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public void rect(float arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.rect(arg0, arg1, arg2, arg3);
			return;
		}
		pg.rect(arg0, arg1, arg2, arg3);
	}

	@Override
	public void rect(float arg0, float arg1, float arg2, float arg3, float arg4) {
		if(pg == this){
			super.rect(arg0, arg1, arg2, arg3, arg4);
			return;
		}
		pg.rect(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void rect(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7) {
		if(pg == this){
			super.rect(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
			return;
		}
		pg.rect(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public void rectMode(int arg0) {
		if(pg == this){
			super.rectMode(arg0);
			return;
		}
		pg.rectMode(arg0);
	}

	@Override
	public void resetMatrix() {
		if(pg == this){
			super.resetMatrix();
			return;
		}
		pg.resetMatrix();
	}

	@Override
	public void rotate(float arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.rotate(arg0, arg1, arg2, arg3);
			return;
		}
		pg.rotate(arg0, arg1, arg2, arg3);
	}

	@Override
	public void rotate(float arg0) {
		if(pg == this){
			super.rotate(arg0);
			return;
		}
		pg.rotate(arg0);
	}

	@Override
	public void rotateX(float arg0) {
		if(pg == this){
			super.rotateX(arg0);
			return;
		}
		pg.rotateX(arg0);
	}

	@Override
	public void rotateY(float arg0) {
		if(pg == this){
			super.rotateY(arg0);
			return;
		}
		pg.rotateY(arg0);
	}

	@Override
	public void rotateZ(float arg0) {
		if(pg == this){
			super.rotateZ(arg0);
			return;
		}
		pg.rotateZ(arg0);
	}

	@Override
	public void scale(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.scale(arg0, arg1, arg2);
			return;
		}
		pg.scale(arg0, arg1, arg2);
	}

	@Override
	public void scale(float arg0, float arg1) {
		if(pg == this){
			super.scale(arg0, arg1);
			return;
		}
		pg.scale(arg0, arg1);
	}

	@Override
	public void scale(float arg0) {
		if(pg == this){
			super.scale(arg0);
			return;
		}
		pg.scale(arg0);
	}

	@Override
	public float screenX(float arg0, float arg1, float arg2) {
		if(pg == this){
			return super.screenX(arg0, arg1, arg2);
		}
		return pg.screenX(arg0, arg1, arg2);
	}

	@Override
	public float screenX(float arg0, float arg1) {
		if(pg == this){
			return super.screenX(arg0, arg1);
		}
		return pg.screenX(arg0, arg1);
	}

	@Override
	public float screenY(float arg0, float arg1, float arg2) {
		if(pg == this){
			return super.screenY(arg0, arg1, arg2);
		}
		return pg.screenY(arg0, arg1, arg2);
	}

	@Override
	public float screenY(float arg0, float arg1) {
		if(pg == this){
			return super.screenY(arg0, arg1);
		}
		return pg.screenY(arg0, arg1);
	}

	@Override
	public float screenZ(float arg0, float arg1, float arg2) {
		if(pg == this){
			return super.screenZ(arg0, arg1, arg2);
		}
		return pg.screenZ(arg0, arg1, arg2);
	}

	@Override
	public void set(int arg0, int arg1, int arg2) {
		if(pg == this){
			super.set(arg0, arg1, arg2);
			return;
		}
		pg.set(arg0, arg1, arg2);
	}

	@Override
	public void set(int arg0, int arg1, PImage arg2) {
		if(pg == this){
			super.set(arg0, arg1, arg2);
			return;
		}
		pg.set(arg0, arg1, arg2);
	}
	
	@Override
	public void setMatrix(PMatrix arg0) {
		if(pg == this){
			super.setMatrix(arg0);
			return;
		}
		pg.setMatrix(arg0);
	}

	@Override
	public void setMatrix(PMatrix2D arg0) {
		if(pg == this){
			super.setMatrix(arg0);
			return;
		}
		pg.setMatrix(arg0);
	}

	@Override
	public void setMatrix(PMatrix3D arg0) {
		if(pg == this){
			super.setMatrix(arg0);
			return;
		}
		pg.setMatrix(arg0);
	}

	@Override
	public void shape(PShape arg0, float arg1, float arg2, float arg3, float arg4) {
		if(pg == this){
			super.shape(arg0, arg1, arg2, arg3, arg4);
			return;
		}
		pg.shape(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void shape(PShape arg0, float arg1, float arg2) {
		if(pg == this){
			super.shape(arg0, arg1, arg2);
			return;
		}
		pg.shape(arg0, arg1, arg2);
	}

	@Override
	public void shape(PShape arg0) {
		if(pg == this){
			super.shape(arg0);
			return;
		}
		pg.shape(arg0);
	}

	@Override
	public void shapeMode(int arg0) {
		if(pg == this){
			super.shapeMode(arg0);
			return;
		}
		pg.shapeMode(arg0);
	}

	@Override
	public void shininess(float arg0) {
		if(pg == this){
			super.shininess(arg0);
			return;
		}
		pg.shininess(arg0);
	}

	@Override
	public void smooth() {
		if(pg == this){
			super.smooth();
			return;
		}
		pg.smooth();
	}

	@Override
	public void specular(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.specular(arg0, arg1, arg2);
			return;
		}
		pg.specular(arg0, arg1, arg2);
	}

	@Override
	public void specular(float arg0) {
		if(pg == this){
			super.specular(arg0);
			return;
		}
		pg.specular(arg0);
	}

	@Override
	public void specular(int arg0) {
		if(pg == this){
			super.specular(arg0);
			return;
		}
		pg.specular(arg0);
	}

	@Override
	public void sphere(float arg0) {
		if(pg == this){
			super.sphere(arg0);
			return;
		}
		pg.sphere(arg0);
	}

	@Override
	public void sphereDetail(int arg0, int arg1) {
		if(pg == this){
			super.sphereDetail(arg0, arg1);
			return;
		}
		pg.sphereDetail(arg0, arg1);
	}

	@Override
	public void sphereDetail(int arg0) {
		if(pg == this){
			super.sphereDetail(arg0);
			return;
		}
		pg.sphereDetail(arg0);
	}

	@Override
	public void spotLight(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5,
			float arg6, float arg7, float arg8, float arg9, float arg10) {
		if(pg == this){
			super.spotLight(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10);
			return;
		}
		pg.spotLight(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10);
	}

	@Override
	public void stroke(float arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.stroke(arg0, arg1, arg2, arg3);
			return;
		}
		pg.stroke(arg0, arg1, arg2, arg3);
	}

	@Override
	public void stroke(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.stroke(arg0, arg1, arg2);
			return;
		}
		pg.stroke(arg0, arg1, arg2);
	}

	@Override
	public void stroke(float arg0, float arg1) {
		if(pg == this){
			super.stroke(arg0, arg1);
			return;
		}
		pg.stroke(arg0, arg1);
	}

	@Override
	public void stroke(float arg0) {
		if(pg == this){
			super.stroke(arg0);
			return;
		}
		pg.stroke(arg0);
	}

	@Override
	public void stroke(int arg0, float arg1) {
		if(pg == this){
			super.stroke(arg0, arg1);
			return;
		}
		pg.stroke(arg0, arg1);
	}

	@Override
	public void stroke(int arg0) {
		if(pg == this){
			super.stroke(arg0);
			return;
		}
		pg.stroke(arg0);
	}

	@Override
	public void strokeCap(int arg0) {
		if(pg == this){
			super.strokeCap(arg0);
			return;
		}
		pg.strokeCap(arg0);
	}

	@Override
	public void strokeJoin(int arg0) {
		if(pg == this){
			super.strokeJoin(arg0);
			return;
		}
		pg.strokeJoin(arg0);
	}

	@Override
	public void strokeWeight(float arg0) {
		if(pg == this){
			super.strokeWeight(arg0);
			return;
		}
		pg.strokeWeight(arg0);
	}

	@Override
	public void style(PStyle arg0) {
		if(pg == this){
			super.style(arg0);
			return;
		}
		pg.style(arg0);
	}

	@Override
	public void text(char arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.text(arg0, arg1, arg2, arg3);
			return;
		}
		pg.text(arg0, arg1, arg2, arg3);
	}

	@Override
	public void text(char arg0, float arg1, float arg2) {
		if(pg == this){
			super.text(arg0, arg1, arg2);
			return;
		}
		pg.text(arg0, arg1, arg2);
	}

	@Override
	public void text(char[] arg0, int arg1, int arg2, float arg3, float arg4, float arg5) {
		if(pg == this){
			super.text(arg0, arg1, arg2, arg3, arg4, arg5);
			return;
		}
		pg.text(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void text(char[] arg0, int arg1, int arg2, float arg3, float arg4) {
		if(pg == this){
			super.text(arg0, arg1, arg2, arg3, arg4);
			return;
		}
		pg.text(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void text(float arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.text(arg0, arg1, arg2, arg3);
			return;
		}
		pg.text(arg0, arg1, arg2, arg3);
	}

	@Override
	public void text(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.text(arg0, arg1, arg2);
			return;
		}
		pg.text(arg0, arg1, arg2);
	}

	@Override
	public void text(int arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.text(arg0, arg1, arg2, arg3);
			return;
		}
		pg.text(arg0, arg1, arg2, arg3);
	}

	@Override
	public void text(int arg0, float arg1, float arg2) {
		if(pg == this){
			super.text(arg0, arg1, arg2);
			return;
		}
		pg.text(arg0, arg1, arg2);
	}

	@Override
	public void text(String arg0, float arg1, float arg2, float arg3, float arg4) {
		if(pg == this){
			super.text(arg0, arg1, arg2, arg3, arg4);
			return;
		}
		pg.text(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void text(String arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.text(arg0, arg1, arg2, arg3);
			return;
		}
		pg.text(arg0, arg1, arg2, arg3);
	}

	@Override
	public void text(String arg0, float arg1, float arg2) {
		if(pg == this){
			super.text(arg0, arg1, arg2);
			return;
		}
		pg.text(arg0, arg1, arg2);
	}

	@Override
	public void textAlign(int arg0, int arg1) {
		if(pg == this){
			super.textAlign(arg0, arg1);;
			return;
		}
		pg.textAlign(arg0, arg1);
	}

	@Override
	public void textAlign(int arg0) {
		if(pg == this){
			super.textAlign(arg0);
			return;
		}
		pg.textAlign(arg0);
	}

	@Override
	public float textAscent() {
		if(pg == this){
			return super.textAscent();
		}
		return pg.textAscent();
	}

	@Override
	public float textDescent() {
		if(pg == this){
			return super.textDescent();
		}
		return pg.textDescent();
	}

	@Override
	public void textFont(PFont arg0, float arg1) {
		if(pg == this){
			super.textFont(arg0, arg1);
			return;
		}
		pg.textFont(arg0, arg1);
	}

	@Override
	public void textFont(PFont arg0) {
		if(pg == this){
			super.textFont(arg0);
			return;
		}
		pg.textFont(arg0);
	}

	@Override
	public void textLeading(float arg0) {
		if(pg == this){
			super.textLeading(arg0);
			return;
		}
		pg.textLeading(arg0);
	}

	@Override
	public void textMode(int arg0) {
		if(pg == this){
			super.textMode(arg0);
			return;
		}
		pg.textMode(arg0);
	}

	@Override
	public void textSize(float arg0) {
		if(pg == this){
			super.textSize(arg0);
			return;
		}
		pg.textSize(arg0);
	}

	@Override
	public float textWidth(char arg0) {
		if(pg == this){
			return super.textWidth(arg0);
		}
		return pg.textWidth(arg0);
	}

	@Override
	public float textWidth(char[] arg0, int arg1, int arg2) {
		if(pg == this){
			return super.textWidth(arg0, arg1, arg2);
		}
		return pg.textWidth(arg0, arg1, arg2);
	}

	@Override
	public float textWidth(String arg0) {
		if(pg == this){
			return super.textWidth(arg0);
		}
		return pg.textWidth(arg0);
	}

	@Override
	public void texture(PImage arg0) {
		if(pg == this){
			super.texture(arg0);
			return;
		}
		pg.texture(arg0);
	}

	@Override
	public void textureMode(int arg0) {
		if(pg == this){
			super.textureMode(arg0);
			return;
		}
		pg.textureMode(arg0);
	}

	@Override
	public void tint(float arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.tint(arg0, arg1, arg2, arg3);
			return;
		}
		pg.tint(arg0, arg1, arg2, arg3);
	}

	@Override
	public void tint(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.tint(arg0, arg1, arg2);
			return;
		}
		pg.tint(arg0, arg1, arg2);
	}

	@Override
	public void tint(float arg0, float arg1) {
		if(pg == this){
			super.tint(arg0, arg1);
			return;
		}
		pg.tint(arg0, arg1);
	}

	@Override
	public void tint(float arg0) {
		if(pg == this){
			super.tint(arg0);
			return;
		}
		pg.tint(arg0);
	}

	@Override
	public void tint(int arg0, float arg1) {
		if(pg == this){
			super.tint(arg0, arg1);
			return;
		}
		pg.tint(arg0, arg1);
	}

	@Override
	public void tint(int arg0) {
		if(pg == this){
			super.tint(arg0);
			return;
		}
		pg.tint(arg0);
	}

	@Override
	public void translate(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.translate(arg0, arg1, arg2);
			return;
		}
		pg.translate(arg0, arg1, arg2);
	}

	@Override
	public void translate(float arg0, float arg1) {
		if(pg == this){
			super.translate(arg0, arg1);
			return;
		}
		pg.translate(arg0, arg1);
	}

	@Override
	public void triangle(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
		if(pg == this){
			super.triangle(arg0, arg1, arg2, arg3, arg4, arg5);
			return;
		}
		pg.triangle(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void updatePixels() {
		if(pg == this){
			super.updatePixels();
			return;
		}
		pg.updatePixels();
	}

	@Override
	public void updatePixels(int arg0, int arg1, int arg2, int arg3) {
		if(pg == this){
			super.updatePixels(arg0, arg1, arg2, arg3);
			return;
		}
		pg.updatePixels(arg0, arg1, arg2, arg3);
	}

	@Override
	public void vertex(float arg0, float arg1, float arg2, float arg3, float arg4) {
		if(pg == this){
			super.vertex(arg0, arg1, arg2, arg3, arg4);
			return;
		}
		pg.vertex(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void vertex(float arg0, float arg1, float arg2, float arg3) {
		if(pg == this){
			super.vertex(arg0, arg1, arg2, arg3);
			return;
		}
		pg.vertex(arg0, arg1, arg2, arg3);
	}

	@Override
	public void vertex(float arg0, float arg1, float arg2) {
		if(pg == this){
			super.vertex(arg0, arg1, arg2);
			return;
		}
		pg.vertex(arg0, arg1, arg2);
	}

	@Override
	public void vertex(float arg0, float arg1) {
		if(pg == this){
			super.vertex(arg0, arg1);
			return;
		}
		pg.vertex(arg0, arg1);
	}

	@Override
	public void vertex(float[] arg0) {
		if(pg == this){
			super.vertex(arg0);
			return;
		}
		pg.vertex(arg0);
	}
}
