package vialab.SMT.renderer;

import java.awt.Image;
import processing.core.*;
import processing.opengl.*;

public class PGraphicsOpenGLDelegate extends PGraphicsOpenGL {
	//static fields
	public static final String CLASSNAME = 
		PGraphicsOpenGLDelegate.class.getName();

	//fields
	// The current delegate that this object points to
	private PGraphicsOpenGL delegate;

	//construtor
	/**
	 * Creates a new PGraphicsOpenGL delegate that delegates to nothing.
	 */
	public PGraphicsOpenGLDelegate(){
		super();
		delegate = null;
	}

	//accessor methods
	/**
	 * Returns whether this object has been given a delegate or not.
	 * @return whether this object has been given a delegate or not.
	 */
	public boolean hasDelegate(){
		return delegate != null;
	}
	/**
	 * Gets the delegate of this object.
	 * @return this object's delegate
	 */
	public PGraphicsOpenGL getDelegate(){
		return delegate == null ? this : delegate;
	}
	/**
	 * Delegate all PGraphicsOpenGL calls on this object to another PGraphicsOpenGL object
	 * @param delegate the desired object to delegate calls to
	 */
	public void setDelegate( PGraphicsOpenGL delegate){
		this.delegate = delegate;
	}

	//delegate methods
	public void ambient(float v1, float v2, float v3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.ambient(v1, v2, v3);
		else
			delegate.ambient(v1, v2, v3);
	}

	public void ambient(float gray) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.ambient(gray);
		else
			delegate.ambient(gray);
	}

	public void ambient(int rgb) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.ambient(rgb);
		else
			delegate.ambient(rgb);
	}

	public void ambientLight(float r, float g, float b, float x, float y,
			float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.ambientLight(r, g, b, x, y, z);
		else
			delegate.ambientLight(r, g, b, x, y, z);
	}

	public void ambientLight(float r, float g, float b) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.ambientLight(r, g, b);
		else
			delegate.ambientLight(r, g, b);
	}

	public void applyMatrix(float n00, float n01, float n02, float n03,
			float n10, float n11, float n12, float n13, float n20, float n21,
			float n22, float n23, float n30, float n31, float n32, float n33) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.applyMatrix(n00, n01, n02, n03, n10,
				n11, n12, n13, n20, n21,
				n22, n23, n30, n31, n32, n33);
		else
			delegate.applyMatrix(n00, n01, n02, n03, n10,
				n11, n12, n13, n20, n21,
				n22, n23, n30, n31, n32, n33);
	}

	public void applyMatrix(float n00, float n01, float n02, float n10,
			float n11, float n12) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.applyMatrix(n00, n01, n02, n10, n11, n12);
		else
			delegate.applyMatrix(n00, n01, n02, n10, n11, n12);
	}

	public void applyMatrix(PMatrix source) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.applyMatrix(source);
		else
			delegate.applyMatrix(source);
	}

	public void applyMatrix(PMatrix2D source) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.applyMatrix(source);
		else
			delegate.applyMatrix(source);
	}

	public void applyMatrix(PMatrix3D source) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.applyMatrix(source);
		else
			delegate.applyMatrix(source);
	}

	public void applyProjection(float n00, float n01, float n02, float n03,
			float n10, float n11, float n12, float n13, float n20, float n21,
			float n22, float n23, float n30, float n31, float n32, float n33) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.applyProjection(n00, n01, n02, n03,
				n10, n11, n12, n13, n20,
				n21, n22, n23, n30, n31, n32, n33);
		else
			delegate.applyProjection(n00, n01, n02, n03,
				n10, n11, n12, n13, n20,
				n21, n22, n23, n30, n31, n32, n33);
	}

	public void applyProjection(PMatrix3D mat) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.applyProjection(mat);
		else
			delegate.applyProjection(mat);
	}

	public void arc(float a, float b, float c, float d, float start,
			float stop, int mode) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.arc(a, b, c, d, start, stop, mode);
		else
			delegate.arc(a, b, c, d, start, stop, mode);
	}

	public void arc(float a, float b, float c, float d, float start, float stop) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.arc(a, b, c, d, start, stop);
		else
			delegate.arc(a, b, c, d, start, stop);
	}

	public void background(float v1, float v2, float v3, float alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.background(v1, v2, v3, alpha);
		else
			delegate.background(v1, v2, v3, alpha);
	}

	public void background(float v1, float v2, float v3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.background(v1, v2, v3);
		else
			delegate.background(v1, v2, v3);
	}

	public void background(float gray, float alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.background(gray, alpha);
		else
			delegate.background(gray, alpha);
	}

	public void background(float gray) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.background(gray);
		else
			delegate.background(gray);
	}

	public void background(int rgb, float alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.background(rgb, alpha);
		else
			delegate.background(rgb, alpha);
	}

	public void background(int rgb) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.background(rgb);
		else
			delegate.background(rgb);
	}

	public void background(PImage image) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.background(image);
		else
			delegate.background(image);
	}

	public void beginCamera() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.beginCamera();
		else
			delegate.beginCamera();
	}

	public void beginContour() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.beginContour();
		else
			delegate.beginContour();
	}

	public void beginDraw() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.beginDraw();
		else
			delegate.beginDraw();
	}

	public PGL beginPGL() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.beginPGL();
		else
			return delegate.beginPGL();
	}

	public void beginRaw(PGraphics rawGraphics) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.beginRaw(rawGraphics);
		else
			delegate.beginRaw(rawGraphics);
	}

	public void beginShape() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.beginShape();
		else
			delegate.beginShape();
	}

	public void beginShape(int kind) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.beginShape(kind);
		else
			delegate.beginShape(kind);
	}

	public void bezier(float x1, float y1, float z1, float x2, float y2,
			float z2, float x3, float y3, float z3, float x4, float y4, float z4) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.bezier(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
		else
			delegate.bezier(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
	}

	public void bezier(float x1, float y1, float x2, float y2, float x3,
			float y3, float x4, float y4) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.bezier(x1, y1, x2, y2, x3, y3, x4, y4);
		else
			delegate.bezier(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	public void bezierDetail(int detail) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.bezierDetail(detail);
		else
			delegate.bezierDetail(detail);
	}

	public float bezierPoint(float a, float b, float c, float d, float t) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.bezierPoint(a, b, c, d, t);
		else
			return delegate.bezierPoint(a, b, c, d, t);
	}

	public float bezierTangent(float a, float b, float c, float d, float t) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.bezierTangent(a, b, c, d, t);
		else
			return delegate.bezierTangent(a, b, c, d, t);
	}

	public void bezierVertex(float x2, float y2, float z2, float x3, float y3,
			float z3, float x4, float y4, float z4) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.bezierVertex(x2, y2, z2, x3, y3, z3, x4, y4, z4);
		else
			delegate.bezierVertex(x2, y2, z2, x3, y3, z3, x4, y4, z4);
	}

	public void bezierVertex(float x2, float y2, float x3, float y3, float x4,
			float y4) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.bezierVertex(x2, y2, x3, y3, x4, y4);
		else
			delegate.bezierVertex(x2, y2, x3, y3, x4, y4);
	}

	public void blend(int sx, int sy, int sw, int sh, int dx, int dy, int dw,
			int dh, int mode) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.blend(sx, sy, sw, sh, dx, dy, dw, dh, mode);
		else
			delegate.blend(sx, sy, sw, sh, dx, dy, dw, dh, mode);
	}

	public void blend(PImage src, int sx, int sy, int sw, int sh, int dx,
			int dy, int dw, int dh, int mode) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode);
		else
			delegate.blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode);
	}

	public void blendMode(int mode) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.blendMode(mode);
		else
			delegate.blendMode(mode);
	}

	public void box(float w, float h, float d) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.box(w, h, d);
		else
			delegate.box(w, h, d);
	}

	public void box(float size) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.box(size);
		else
			delegate.box(size);
	}

	public void camera() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.camera();
		else
			delegate.camera();
	}

	public void camera(float eyeX, float eyeY, float eyeZ, float centerX,
			float centerY, float centerZ, float upX, float upY, float upZ) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.camera(eyeX, eyeY, eyeZ, centerX, 
				centerY, centerZ, upX, upY, upZ);
		else
			delegate.camera(eyeX, eyeY, eyeZ, centerX,
				centerY, centerZ, upX, upY, upZ);
	}

	public boolean canDraw() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.canDraw();
		else
			return delegate.canDraw();
	}

	public void clear() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.clear();
		else
			delegate.clear();
	}

	public void clip(float arg0, float arg1, float arg2, float arg3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.clip(arg0, arg1, arg2, arg3);
		else
			delegate.clip(arg0, arg1, arg2, arg3);
	}

	public Object clone() throws CloneNotSupportedException {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.clone();
		else
			return delegate.clone();
	}

	public void colorMode(int mode, float max1, float max2, float max3,
			float maxA) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.colorMode(mode, max1, max2, max3, maxA);
		else
			delegate.colorMode(mode, max1, max2, max3, maxA);
	}

	public void colorMode(int mode, float max1, float max2, float max3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.colorMode(mode, max1, max2, max3);
		else
			delegate.colorMode(mode, max1, max2, max3);
	}

	public void colorMode(int mode, float max) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.colorMode(mode, max);
		else
			delegate.colorMode(mode, max);
	}

	public void colorMode(int mode) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.colorMode(mode);
		else
			delegate.colorMode(mode);
	}

	public void copy(int sx, int sy, int sw, int sh, int dx, int dy, int dw,
			int dh) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.copy(sx, sy, sw, sh, dx, dy, dw, dh);
		else
			delegate.copy(sx, sy, sw, sh, dx, dy, dw, dh);
	}

	public void copy(PImage src, int sx, int sy, int sw, int sh, int dx,
			int dy, int dw, int dh) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
		else
			delegate.copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
	}

	public PShape createShape() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.createShape();
		else
			return delegate.createShape();
	}

	public PShape createShape(int kind, float... p) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.createShape(kind, p);
		else
			return delegate.createShape(kind, p);
	}

	public PShape createShape(int type) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.createShape(type);
		else
			return delegate.createShape(type);
	}

	public PShape createShape(PShape source) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.createShape(source);
		else
			return delegate.createShape(source);
	}

	public void curve(float x1, float y1, float z1, float x2, float y2,
			float z2, float x3, float y3, float z3, float x4, float y4, float z4) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.curve(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
		else
			delegate.curve(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
	}

	public void curve(float x1, float y1, float x2, float y2, float x3,
			float y3, float x4, float y4) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.curve(x1, y1, x2, y2, x3, y3, x4, y4);
		else
			delegate.curve(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	public void curveDetail(int detail) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.curveDetail(detail);
		else
			delegate.curveDetail(detail);
	}

	public float curvePoint(float a, float b, float c, float d, float t) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.curvePoint(a, b, c, d, t);
		else
			return delegate.curvePoint(a, b, c, d, t);
	}

	public float curveTangent(float a, float b, float c, float d, float t) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.curveTangent(a, b, c, d, t);
		else
			return delegate.curveTangent(a, b, c, d, t);
	}

	public void curveTightness(float tightness) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.curveTightness(tightness);
		else
			delegate.curveTightness(tightness);
	}

	public void curveVertex(float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.curveVertex(x, y, z);
		else
			delegate.curveVertex(x, y, z);
	}

	public void curveVertex(float x, float y) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.curveVertex(x, y);
		else
			delegate.curveVertex(x, y);
	}

	public void directionalLight(float r, float g, float b, float dx, float dy,
			float dz) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.directionalLight(r, g, b, dx, dy, dz);
		else
			delegate.directionalLight(r, g, b, dx, dy, dz);
	}

	public boolean displayable() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.displayable();
		else
			return delegate.displayable();
	}

	public void dispose() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.dispose();
		else
			delegate.dispose();
	}

	public void edge(boolean edge) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.edge(edge);
		else
			delegate.edge(edge);
	}

	public void ellipse(float a, float b, float c, float d) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.ellipse(a, b, c, d);
		else
			delegate.ellipse(a, b, c, d);
	}

	public void ellipseImpl(float a, float b, float c, float d) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.ellipseImpl(a, b, c, d);
		else
			delegate.ellipseImpl(a, b, c, d);
	}

	public void ellipseMode(int mode) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.ellipseMode(mode);
		else
			delegate.ellipseMode(mode);
	}

	public void emissive(float v1, float v2, float v3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.emissive(v1, v2, v3);
		else
			delegate.emissive(v1, v2, v3);
	}

	public void emissive(float gray) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.emissive(gray);
		else
			delegate.emissive(gray);
	}

	public void emissive(int rgb) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.emissive(rgb);
		else
			delegate.emissive(rgb);
	}

	public void endCamera() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.endCamera();
		else
			delegate.endCamera();
	}

	public void endContour() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.endContour();
		else
			delegate.endContour();
	}

	public void endDraw() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.endDraw();
		else
			delegate.endDraw();
	}

	public void endPGL() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.endPGL();
		else
			delegate.endPGL();
	}

	public void endRaw() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.endRaw();
		else
			delegate.endRaw();
	}

	public void endShape() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.endShape();
		else
			delegate.endShape();
	}

	public void endShape(int mode) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.endShape(mode);
		else
			delegate.endShape(mode);
	}

	public boolean equals(Object obj) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.equals(obj);
		else
			return delegate.equals(obj);
	}

	public void fill(float v1, float v2, float v3, float alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.fill(v1, v2, v3, alpha);
		else
			delegate.fill(v1, v2, v3, alpha);
	}

	public void fill(float v1, float v2, float v3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.fill(v1, v2, v3);
		else
			delegate.fill(v1, v2, v3);
	}

	public void fill(float gray, float alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.fill(gray, alpha);
		else
			delegate.fill(gray, alpha);
	}

	public void fill(float gray) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.fill(gray);
		else
			delegate.fill(gray);
	}

	public void fill(int rgb, float alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.fill(rgb, alpha);
		else
			delegate.fill(rgb, alpha);
	}

	public void fill(int rgb) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.fill(rgb);
		else
			delegate.fill(rgb);
	}

	public void filter(int kind, float param) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.filter(kind, param);
		else
			delegate.filter(kind, param);
	}

	public void filter(int kind) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.filter(kind);
		else
			delegate.filter(kind);
	}

	public void filter(PShader shader) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.filter(shader);
		else
			delegate.filter(shader);
	}

	public void flush() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.flush();
		else
			delegate.flush();
	}

	public void frustum(float left, float right, float bottom, float top,
			float znear, float zfar) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.frustum(left, right, bottom, top, znear, zfar);
		else
			delegate.frustum(left, right, bottom, top, znear, zfar);
	}

	public PImage get() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.get();
		else
			return delegate.get();
	}

	public PImage get(int x, int y, int w, int h) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.get(x, y, w, h);
		else
			return delegate.get(x, y, w, h);
	}

	public int get(int x, int y) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.get(x, y);
		else
			return delegate.get(x, y);
	}

	public Object getCache(PImage image) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getCache(image);
		else
			return delegate.getCache(image);
	}

	public Image getImage() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getImage();
		else
			return delegate.getImage();
	}

	public PMatrix getMatrix() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getMatrix();
		else
			return delegate.getMatrix();
	}

	public PMatrix2D getMatrix(PMatrix2D target) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getMatrix(target);
		else
			return delegate.getMatrix(target);
	}

	public PMatrix3D getMatrix(PMatrix3D target) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getMatrix(target);
		else
			return delegate.getMatrix(target);
	}

	public int getModifiedX1() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getModifiedX1();
		else
			return delegate.getModifiedX1();
	}

	public int getModifiedX2() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getModifiedX2();
		else
			return delegate.getModifiedX2();
	}

	public int getModifiedY1() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getModifiedY1();
		else
			return delegate.getModifiedY1();
	}

	public int getModifiedY2() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getModifiedY2();
		else
			return delegate.getModifiedY2();
	}

	public Object getNative() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getNative();
		else
			return delegate.getNative();
	}

	public PGraphics getRaw() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getRaw();
		else
			return delegate.getRaw();
	}

	public PStyle getStyle() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getStyle();
		else
			return delegate.getStyle();
	}

	public PStyle getStyle(PStyle s) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getStyle(s);
		else
			return delegate.getStyle(s);
	}

	public Texture getTexture() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getTexture();
		else
			return delegate.getTexture();
	}

	public Texture getTexture(PImage img) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.getTexture(img);
		else
			return delegate.getTexture(img);
	}

	public boolean haveRaw() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.haveRaw();
		else
			return delegate.haveRaw();
	}

	public void hint(int which) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.hint(which);
		else
			delegate.hint(which);
	}

	public void image(PImage arg0, float arg1, float arg2, float arg3,
			float arg4, int arg5, int arg6, int arg7, int arg8) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.image(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
		else
			delegate.image(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	public void image(PImage img, float a, float b, float c, float d) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.image(img, a, b, c, d);
		else
			delegate.image(img, a, b, c, d);
	}

	public void image(PImage arg0, float arg1, float arg2) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.image(arg0, arg1, arg2);
		else
			delegate.image(arg0, arg1, arg2);
	}

	public void imageMode(int arg0) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.imageMode(arg0);
		else
			delegate.imageMode(arg0);
	}

	public void init(int width, int height, int format) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.init(width, height, format);
		else
			delegate.init(width, height, format);
	}

	public boolean is2D() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.is2D();
		else
			return delegate.is2D();
	}

	public boolean is3D() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.is3D();
		else
			return delegate.is3D();
	}

	public boolean isGL() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.isGL();
		else
			return delegate.isGL();
	}

	public boolean isLoaded() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.isLoaded();
		else
			return delegate.isLoaded();
	}

	public boolean isModified() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.isModified();
		else
			return delegate.isModified();
	}

	public int lerpColor(int c1, int c2, float amt) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.lerpColor(c1, c2, amt);
		else
			return delegate.lerpColor(c1, c2, amt);
	}

	public void lightFalloff(float constant, float linear, float quadratic) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.lightFalloff(constant, linear, quadratic);
		else
			delegate.lightFalloff(constant, linear, quadratic);
	}

	public void lightSpecular(float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.lightSpecular(x, y, z);
		else
			delegate.lightSpecular(x, y, z);
	}

	public void lights() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.lights();
		else
			delegate.lights();
	}

	public void line(float x1, float y1, float z1, float x2, float y2, float z2) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.line(x1, y1, z1, x2, y2, z2);
		else
			delegate.line(x1, y1, z1, x2, y2, z2);
	}

	public void line(float x1, float y1, float x2, float y2) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.line(x1, y1, x2, y2);
		else
			delegate.line(x1, y1, x2, y2);
	}

	public void loadPixels() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.loadPixels();
		else
			delegate.loadPixels();
	}

	public PShader loadShader(String fragFilename, String vertFilename) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.loadShader(fragFilename, vertFilename);
		else
			return delegate.loadShader(fragFilename, vertFilename);
	}

	public PShader loadShader(String fragFilename) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.loadShader(fragFilename);
		else
			return delegate.loadShader(fragFilename);
	}

	public PShape loadShape(String filename, String options) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.loadShape(filename, options);
		else
			return delegate.loadShape(filename, options);
	}

	public PShape loadShape(String filename) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.loadShape(filename);
		else
			return delegate.loadShape(filename);
	}

	public void loadTexture() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.loadTexture();
		else
			delegate.loadTexture();
	}

	public void mask(int[] arg0) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.mask(arg0);
		else
			delegate.mask(arg0);
	}

	public void mask(PImage alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.mask(alpha);
		else
			delegate.mask(alpha);
	}

	public float modelX(float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.modelX(x, y, z);
		else
			return delegate.modelX(x, y, z);
	}

	public float modelY(float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.modelY(x, y, z);
		else
			return delegate.modelY(x, y, z);
	}

	public float modelZ(float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.modelZ(x, y, z);
		else
			return delegate.modelZ(x, y, z);
	}

	public void noClip() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.noClip();
		else
			delegate.noClip();
	}

	public void noFill() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.noFill();
		else
			delegate.noFill();
	}

	public void noLights() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.noLights();
		else
			delegate.noLights();
	}

	public void noSmooth() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.noSmooth();
		else
			delegate.noSmooth();
	}

	public void noStroke() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.noStroke();
		else
			delegate.noStroke();
	}

	public void noTexture() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.noTexture();
		else
			delegate.noTexture();
	}

	public void noTint() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.noTint();
		else
			delegate.noTint();
	}

	public void normal(float nx, float ny, float nz) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.normal(nx, ny, nz);
		else
			delegate.normal(nx, ny, nz);
	}

	public void ortho() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.ortho();
		else
			delegate.ortho();
	}

	public void ortho(float left, float right, float bottom, float top,
			float near, float far) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.ortho(left, right, bottom, top, near, far);
		else
			delegate.ortho(left, right, bottom, top, near, far);
	}

	public void ortho(float left, float right, float bottom, float top) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.ortho(left, right, bottom, top);
		else
			delegate.ortho(left, right, bottom, top);
	}

	public void perspective() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.perspective();
		else
			delegate.perspective();
	}

	public void perspective(float fov, float aspect, float zNear, float zFar) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.perspective(fov, aspect, zNear, zFar);
		else
			delegate.perspective(fov, aspect, zNear, zFar);
	}

	public void point(float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.point(x, y, z);
		else
			delegate.point(x, y, z);
	}

	public void point(float x, float y) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.point(x, y);
		else
			delegate.point(x, y);
	}

	public void pointLight(float r, float g, float b, float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.pointLight(r, g, b, x, y, z);
		else
			delegate.pointLight(r, g, b, x, y, z);
	}

	public void popMatrix() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.popMatrix();
		else
			delegate.popMatrix();
	}

	public void popProjection() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.popProjection();
		else
			delegate.popProjection();
	}

	public void popStyle() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.popStyle();
		else
			delegate.popStyle();
	}

	public void printCamera() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.printCamera();
		else
			delegate.printCamera();
	}

	public void printMatrix() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.printMatrix();
		else
			delegate.printMatrix();
	}

	public void printProjection() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.printProjection();
		else
			delegate.printProjection();
	}

	public void pushMatrix() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.pushMatrix();
		else
			delegate.pushMatrix();
	}

	public void pushProjection() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.pushProjection();
		else
			delegate.pushProjection();
	}

	public void pushStyle() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.pushStyle();
		else
			delegate.pushStyle();
	}

	public void quad(float x1, float y1, float x2, float y2, float x3,
			float y3, float x4, float y4) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.quad(x1, y1, x2, y2, x3, y3, x4, y4);
		else
			delegate.quad(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	public void quadraticVertex(float cx, float cy, float cz, float x3,
			float y3, float z3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.quadraticVertex(cx, cy, cz, x3, y3, z3);
		else
			delegate.quadraticVertex(cx, cy, cz, x3, y3, z3);
	}

	public void quadraticVertex(float cx, float cy, float x3, float y3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.quadraticVertex(cx, cy, x3, y3);
		else
			delegate.quadraticVertex(cx, cy, x3, y3);
	}

	public void rect(float arg0, float arg1, float arg2, float arg3,
			float arg4, float arg5, float arg6, float arg7) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.rect(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
		else
			delegate.rect(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	public void rect(float a, float b, float c, float d, float r) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.rect(a, b, c, d, r);
		else
			delegate.rect(a, b, c, d, r);
	}

	public void rect(float arg0, float arg1, float arg2, float arg3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.rect(arg0, arg1, arg2, arg3);
		else
			delegate.rect(arg0, arg1, arg2, arg3);
	}

	public void rectMode(int mode) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.rectMode(mode);
		else
			delegate.rectMode(mode);
	}

	public void removeCache(PImage image) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.removeCache(image);
		else
			delegate.removeCache(image);
	}

	public void requestDraw() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.requestDraw();
		else
			delegate.requestDraw();
	}

	public void requestFocus() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.requestFocus();
		else
			delegate.requestFocus();
	}

	public void resetMatrix() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.resetMatrix();
		else
			delegate.resetMatrix();
	}

	public void resetProjection() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.resetProjection();
		else
			delegate.resetProjection();
	}

	public void resetShader() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.resetShader();
		else
			delegate.resetShader();
	}

	public void resetShader(int kind) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.resetShader(kind);
		else
			delegate.resetShader(kind);
	}

	public void resize(int wide, int high) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.resize(wide, high);
		else
			delegate.resize(wide, high);
	}

	public void rotate(float angle, float v0, float v1, float v2) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.rotate(angle, v0, v1, v2);
		else
			delegate.rotate(angle, v0, v1, v2);
	}

	public void rotate(float angle) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.rotate(angle);
		else
			delegate.rotate(angle);
	}

	public void rotateX(float angle) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.rotateX(angle);
		else
			delegate.rotateX(angle);
	}

	public void rotateY(float angle) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.rotateY(angle);
		else
			delegate.rotateY(angle);
	}

	public void rotateZ(float angle) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.rotateZ(angle);
		else
			delegate.rotateZ(angle);
	}

	public boolean save(String arg0) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.save(arg0);
		else
			return delegate.save(arg0);
	}

	public void scale(float sx, float sy, float sz) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.scale(sx, sy, sz);
		else
			delegate.scale(sx, sy, sz);
	}

	public void scale(float sx, float sy) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.scale(sx, sy);
		else
			delegate.scale(sx, sy);
	}

	public void scale(float s) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.scale(s);
		else
			delegate.scale(s);
	}

	public float screenX(float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.screenX(x, y, z);
		else
			return delegate.screenX(x, y, z);
	}

	public float screenX(float x, float y) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.screenX(x, y);
		else
			return delegate.screenX(x, y);
	}

	public float screenY(float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.screenY(x, y, z);
		else
			return delegate.screenY(x, y, z);
	}

	public float screenY(float x, float y) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.screenY(x, y);
		else
			return delegate.screenY(x, y);
	}

	public float screenZ(float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.screenZ(x, y, z);
		else
			return delegate.screenZ(x, y, z);
	}

	public void set(int x, int y, int argb) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.set(x, y, argb);
		else
			delegate.set(x, y, argb);
	}

	public void set(int x, int y, PImage img) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.set(x, y, img);
		else
			delegate.set(x, y, img);
	}

	public void setCache(PImage image, Object storage) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setCache(image, storage);
		else
			delegate.setCache(image, storage);
	}

	public void setFrameRate(float frameRate) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setFrameRate(frameRate);
		else
			delegate.setFrameRate(frameRate);
	}

	public void setLoaded() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setLoaded();
		else
			delegate.setLoaded();
	}

	public void setLoaded(boolean l) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setLoaded(l);
		else
			delegate.setLoaded(l);
	}

	public void setMatrix(PMatrix source) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setMatrix(source);
		else
			delegate.setMatrix(source);
	}

	public void setMatrix(PMatrix2D source) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setMatrix(source);
		else
			delegate.setMatrix(source);
	}

	public void setMatrix(PMatrix3D source) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setMatrix(source);
		else
			delegate.setMatrix(source);
	}

	public void setModified() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setModified();
		else
			delegate.setModified();
	}

	public void setModified(boolean m) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setModified(m);
		else
			delegate.setModified(m);
	}

	public void setParent(PApplet parent) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setParent(parent);
		else
			delegate.setParent(parent);
	}

	public void setPath(String path) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setPath(path);
		else
			delegate.setPath(path);
	}

	public void setPrimary(boolean primary) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setPrimary(primary);
		else
			delegate.setPrimary(primary);
	}

	public void setProjection(PMatrix3D mat) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setProjection(mat);
		else
			delegate.setProjection(mat);
	}

	public void setSize(int iwidth, int iheight) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.setSize(iwidth, iheight);
		else
			delegate.setSize(iwidth, iheight);
	}

	public void shader(PShader shader, int kind) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.shader(shader, kind);
		else
			delegate.shader(shader, kind);
	}

	public void shader(PShader shader) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.shader(shader);
		else
			delegate.shader(shader);
	}

	public void shape(PShape shape, float a, float b, float c, float d) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.shape(shape, a, b, c, d);
		else
			delegate.shape(shape, a, b, c, d);
	}

	public void shape(PShape shape, float x, float y) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.shape(shape, x, y);
		else
			delegate.shape(shape, x, y);
	}

	public void shape(PShape shape) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.shape(shape);
		else
			delegate.shape(shape);
	}

	public void shapeMode(int mode) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.shapeMode(mode);
		else
			delegate.shapeMode(mode);
	}

	public void shearX(float angle) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.shearX(angle);
		else
			delegate.shearX(angle);
	}

	public void shearY(float angle) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.shearY(angle);
		else
			delegate.shearY(angle);
	}

	public void shininess(float shine) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.shininess(shine);
		else
			delegate.shininess(shine);
	}

	public void smooth() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.smooth();
		else
			delegate.smooth();
	}

	public void smooth(int level) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.smooth(level);
		else
			delegate.smooth(level);
	}

	public void specular(float v1, float v2, float v3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.specular(v1, v2, v3);
		else
			delegate.specular(v1, v2, v3);
	}

	public void specular(float gray) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.specular(gray);
		else
			delegate.specular(gray);
	}

	public void specular(int rgb) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.specular(rgb);
		else
			delegate.specular(rgb);
	}

	public void sphere(float r) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.sphere(r);
		else
			delegate.sphere(r);
	}

	public void sphereDetail(int arg0, int arg1) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.sphereDetail(arg0, arg1);
		else
			delegate.sphereDetail(arg0, arg1);
	}

	public void sphereDetail(int res) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.sphereDetail(res);
		else
			delegate.sphereDetail(res);
	}

	public void spotLight(float r, float g, float b, float x, float y, float z,
			float dx, float dy, float dz, float angle, float concentration) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.spotLight(r, g, b, x, y, z, dx, dy,
				dz, angle, concentration);
		else
			delegate.spotLight(r, g, b, x, y, z, dx, dy,
				dz, angle, concentration);
	}

	public void stroke(float v1, float v2, float v3, float alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.stroke(v1, v2, v3, alpha);
		else
			delegate.stroke(v1, v2, v3, alpha);
	}

	public void stroke(float v1, float v2, float v3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.stroke(v1, v2, v3);
		else
			delegate.stroke(v1, v2, v3);
	}

	public void stroke(float gray, float alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.stroke(gray, alpha);
		else
			delegate.stroke(gray, alpha);
	}

	public void stroke(float gray) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.stroke(gray);
		else
			delegate.stroke(gray);
	}

	public void stroke(int rgb, float alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.stroke(rgb, alpha);
		else
			delegate.stroke(rgb, alpha);
	}

	public void stroke(int rgb) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.stroke(rgb);
		else
			delegate.stroke(rgb);
	}

	public void strokeCap(int cap) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.strokeCap(cap);
		else
			delegate.strokeCap(cap);
	}

	public void strokeJoin(int join) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.strokeJoin(join);
		else
			delegate.strokeJoin(join);
	}

	public void strokeWeight(float weight) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.strokeWeight(weight);
		else
			delegate.strokeWeight(weight);
	}

	public void style(PStyle s) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.style(s);
		else
			delegate.style(s);
	}

	public void text(char c, float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.text(c, x, y, z);
		else
			delegate.text(c, x, y, z);
	}

	public void text(char c, float x, float y) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.text(c, x, y);
		else
			delegate.text(c, x, y);
	}

	public void text(char[] chars, int start, int stop, float x, float y,
			float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.text(chars, start, stop, x, y, z);
		else
			delegate.text(chars, start, stop, x, y, z);
	}

	public void text(char[] arg0, int arg1, int arg2, float arg3, float arg4) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.text(arg0, arg1, arg2, arg3, arg4);
		else
			delegate.text(arg0, arg1, arg2, arg3, arg4);
	}

	public void text(float num, float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.text(num, x, y, z);
		else
			delegate.text(num, x, y, z);
	}

	public void text(float num, float x, float y) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.text(num, x, y);
		else
			delegate.text(num, x, y);
	}

	public void text(int num, float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.text(num, x, y, z);
		else
			delegate.text(num, x, y, z);
	}

	public void text(int num, float x, float y) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.text(num, x, y);
		else
			delegate.text(num, x, y);
	}

	public void text(String arg0, float arg1, float arg2, float arg3, float arg4) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.text(arg0, arg1, arg2, arg3, arg4);
		else
			delegate.text(arg0, arg1, arg2, arg3, arg4);
	}

	public void text(String str, float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.text(str, x, y, z);
		else
			delegate.text(str, x, y, z);
	}

	public void text(String str, float x, float y) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.text(str, x, y);
		else
			delegate.text(str, x, y);
	}

	public void textAlign(int alignX, int alignY) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.textAlign(alignX, alignY);
		else
			delegate.textAlign(alignX, alignY);
	}

	public void textAlign(int alignX) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.textAlign(alignX);
		else
			delegate.textAlign(alignX);
	}

	public float textAscent() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.textAscent();
		else
			return delegate.textAscent();
	}

	public float textDescent() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.textDescent();
		else
			return delegate.textDescent();
	}

	public void textFont(PFont which, float size) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.textFont(which, size);
		else
			delegate.textFont(which, size);
	}

	public void textFont(PFont which) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.textFont(which);
		else
			delegate.textFont(which);
	}

	public void textLeading(float leading) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.textLeading(leading);
		else
			delegate.textLeading(leading);
	}

	public void textMode(int arg0) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.textMode(arg0);
		else
			delegate.textMode(arg0);
	}

	public void textSize(float size) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.textSize(size);
		else
			delegate.textSize(size);
	}

	public float textWidth(char c) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.textWidth(c);
		else
			return delegate.textWidth(c);
	}

	public float textWidth(char[] chars, int start, int length) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.textWidth(chars, start, length);
				else
			return delegate.textWidth(chars, start, length);
	}

	public float textWidth(String str) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			return super.textWidth(str);
		else
			return delegate.textWidth(str);
	}

	public void texture(PImage image) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.texture(image);
		else
			delegate.texture(image);
	}

	public void textureMode(int mode) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.textureMode(mode);
		else
			delegate.textureMode(mode);
	}

	public void textureSampling(int sampling) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.textureSampling(sampling);
		else
			delegate.textureSampling(sampling);
	}

	public void textureWrap(int wrap) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.textureWrap(wrap);
		else
			delegate.textureWrap(wrap);
	}

	public void tint(float v1, float v2, float v3, float alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.tint(v1, v2, v3, alpha);
		else
			delegate.tint(v1, v2, v3, alpha);
	}

	public void tint(float v1, float v2, float v3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.tint(v1, v2, v3);
		else
			delegate.tint(v1, v2, v3);
	}

	public void tint(float gray, float alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.tint(gray, alpha);
		else
			delegate.tint(gray, alpha);
	}

	public void tint(float gray) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.tint(gray);
		else
			delegate.tint(gray);
	}

	public void tint(int rgb, float alpha) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.tint(rgb, alpha);
		else
			delegate.tint(rgb, alpha);
	}

	public void tint(int rgb) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.tint(rgb);
		else
			delegate.tint(rgb);
	}

	public void translate(float tx, float ty, float tz) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.translate(tx, ty, tz);
		else
			delegate.translate(tx, ty, tz);
	}

	public void translate(float tx, float ty) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.translate(tx, ty);
		else
			delegate.translate(tx, ty);
	}

	public void triangle(float x1, float y1, float x2, float y2, float x3,
			float y3) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.triangle(x1, y1, x2, y2, x3, y3);
		else
			delegate.triangle(x1, y1, x2, y2, x3, y3);
	}

	public void updateDisplay() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.updateDisplay();
		else
			delegate.updateDisplay();
	}

	public void updatePixels() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.updatePixels();
		else
			delegate.updatePixels();
	}

	public void updatePixels(int x, int y, int w, int h) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.updatePixels(x, y, w, h);
		else
			delegate.updatePixels(x, y, w, h);
	}

	public void updateProjmodelview() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.updateProjmodelview();
		else
			delegate.updateProjmodelview();
	}

	public void updateTexture() {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.updateTexture();
		else
			delegate.updateTexture();
	}

	public void updateTexture(int x, int y, int w, int h) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.updateTexture(x, y, w, h);
		else
			delegate.updateTexture(x, y, w, h);
	}

	public void vertex(float x, float y, float z, float u, float v) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.vertex(x, y, z, u, v);
		else
			delegate.vertex(x, y, z, u, v);
	}

	public void vertex(float x, float y, float u, float v) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.vertex(x, y, u, v);
		else
			delegate.vertex(x, y, u, v);
	}

	public void vertex(float x, float y, float z) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.vertex(x, y, z);
		else
			delegate.vertex(x, y, z);
	}

	public void vertex(float x, float y) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.vertex(x, y);
		else
			delegate.vertex(x, y);
	}

	public void vertex(float[] v) {
		PGraphicsOpenGL delegate = this.getDelegate();
		if( delegate == this)
			super.vertex(v);
		else
			delegate.vertex(v);
	}
}