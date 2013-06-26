package vialab.SMT;

import processing.core.PVector;

/**
 * TouchPair is a group of two touches, with some convenience methods.
 */
public class TouchPair {
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

	/**
	 * @return Whether the TouchPair has matching position
	 */
	public boolean matches() {
		return from == null || (from.x == to.x && from.y == to.y);
	}

	/**
	 * @return Whether the TouchPair is the first touch and so has no From Touch
	 */
	public boolean isFirst() {
		return from == null;
	}

	/**
	 * @return Whether the touchpair is empty, having neither a From or a To Touch
	 */
	public boolean isEmpty() {
		return to == null && from == null;
	}

	/**
	 * @return PVector containing the From Touch's position
	 */
	public PVector getFromVec() {
		return new PVector(from.x, from.y);
	}

	/**
	 * @return PVector containing the To Touch's position
	 */
	public PVector getToVec() {
		return new PVector(to.x, to.y);
	}
}
