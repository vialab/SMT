package vialab.SMT;

import processing.core.PVector;

/**
 * TouchPair is a group of two touches, with some convenience methods
 */
class TouchPair {
	public Touch from;

	public Touch to;

	public TouchPair() {
	}

	public TouchPair(Touch to) {
		this.from = to;
		this.to = to;
	}

	public TouchPair(Touch from, Touch to) {
		this.from = from;
		this.to = to;
	}

	public boolean matches() {
		return from == null || (from.x == to.x && from.y == to.y);
	}

	public boolean isFirst() {
		return from == null;
	}

	public boolean isEmpty() {
		return to == null && from == null;
	}

	public PVector getFromVec() {
		return new PVector(from.x, from.y);
	}

	public PVector getToVec() {
		return new PVector(to.x, to.y);
	}
}
