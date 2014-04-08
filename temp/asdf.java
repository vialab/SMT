//backup of functions that were removed from zone temporarily
//they should all be reimplemented or updated

class asdf {
	/**
	 * @return The angle of the Zone in global coordinates
	 */
	public float getRotationAngle() {
		PMatrix3D g = getGlobalMatrix();
		float angle = PApplet.atan2(g.m10, g.m00);
		return angle >= 0 ? angle : angle + 2 * PI;
	}

	/**
	 * @return the x position of the touch in local coordinates
	 */
	public float getLocalX() {
		return
			getParent() == null ?
				getOrigin().x :
				getParent().toZoneVector( getOrigin()).x;
	}

	/**
	 * @return the y position of zone in parent coordinates
	 */
	public float getLocalY() {
		return
			getParent() == null ?
				getOrigin().y :
				getParent().toZoneVector(getOrigin()).y;
	}
	
	/**
	 * @param touch
	 * @return the x position of zone in parent coordinates
	 */
	public float getLocalX( Touch touch) {
		return toZoneVector( new PVector(
			touch.x, touch.y)).x;
	}

	/**
	 * @param touch
	 * @return the y position of the touch in local coordinates
	 */
	public float getLocalY( Touch touch) {
		return toZoneVector( new PVector(
			touch.x, touch.y)).y;
	}
	
	/**
	 * Sets the local x position
	 * @param x
	 */
	public void setX(float x) {
		if (getParent() == null)
			setLocation( x, getOrigin().y);
		else
			setLocation( x, getParent().toZoneVector(getOrigin()).y);
	}

	/**
	 * Sets the local y position
	 * @param y
	 */
	public void setY(float y) {
		if (getParent() == null)
			setLocation( getOrigin().x, y);
		else
			setLocation( getParent().toZoneVector(getOrigin()).x, y);
	}
	

	/**
	 * Reset the transformation matrix of the zone
	 */
	@Override
	public void resetMatrix() {
		matrix.reset();
		matrix.translate(x, y);
	}

	/**
	 * Changes the coordinates and size of the zone.
	 * 
	 * @param x int The zone's new x-coordinate.
	 * @param y int The zone's new y-coordinate.
	 * @param width int The zone's new width.
	 * @param height int The zone's new height.
	 */
	@Deprecated
	public void setData( int x, int y, int width, int height) {
		this.setSize( width, height);
		this.setLocation( x, y);
	}

	/**
	 * Moves the zone to a given location with a reset matrix
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(float x, float y) {
		this.x = Math.round( x);
		this.y = Math.round( y);
		if (getParent() == null)
			resetMatrix();
		else {
			this.matrix.reset();
			this.matrix.translate(x, y);
			// If we are in our own touch (which we can tell by if backupMatrix
			// is set), we need to apply our parents matrix too after the reset,
			// since it was applied before and will be inverted.
			if (backupMatrix != null)
				this.matrix.preApply(getParent().matrix);
		}
	}
	
	@Override
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
		this.dimension = new Dimension( width, height);
		this.halfDimension = new Dimension( width / 2, height / 2);
	}

	public void setWidth( int width){
		this.width = width;
		this.dimension.width = width;
		this.halfDimension.width = width / 2;
	}
	public void setHeight( int height){
		this.height = height;
		this.dimension.height = height;
		this.halfDimension.height = height / 2;
	}

	/**
	 * Get the zone x-coordinate. Upper left corner for rectangle.
	 * @return x int representing the upper left x-coordinate of the zone.
	 */
	public int getX() {
		return (int) this.getOrigin().x;
	}

	/**
	 * Get the zone y-coordinate. Upper left corner for rectangle.
	 * @return y int representing the upper left y-coordinate of the zone.
	 */
	public int getY() {
		return (int) this.getOrigin().y;
	}

	/**
	 * Get the zone's original width.
	 * @return width int representing the width of the zone.
	 */
	public int getWidth() {
		return (int) PVector.sub(
			fromZoneVector(
				new PVector(this.width, 0)),
			this.getOrigin()).mag();
	}

	/**
	 * Get the zone's original height.
	 * @return height int representing the height of the zone.
	 */
	public int getHeight() {
		return (int) PVector.sub(
			fromZoneVector(
				new PVector(0, this.height)),
			this.getOrigin()).mag();
	}

	/**
	 * @return The rotation radius of the zone, which is used by at least rnt()
	 *         for now, controlling when rotation is done
	 */
	public float getRntRadius() {
		return rntRadius;
	}

	/**
	 * @param rntRadius
	 *            The new rotation radius of the zone, which is used by at least
	 *            rnt() for now, controlling when rotation is done
	 */
	public void setRntRadius(float rntRadius) {
		this.rntRadius = rntRadius;
	}

	/**
	 * Tests to see if the x and y coordinates are in the zone.
	 * 
	 * @param x
	 *            float - X-coordinate to test
	 * @param y
	 *            float - Y-coordinate to test
	 * @return boolean True if x and y is in the zone, false otherwise.
	 */
	public boolean contains(float x, float y) {
		PVector mouse = new PVector(x, y);
		PVector world = this.toZoneVector(mouse);
		return
			(world.x > 0) && (world.x < this.width) &&
			(world.y > 0) && (world.y < this.height);
	}

	public void drag(){}
	public void drag( boolean dragX, boolean dragY){}
	public void drag( boolean dragX, boolean dragY, int leftLimit, int rightLimit, int upLimit, int downLimit){}
	public void drag( boolean dragLeft, boolean dragRight, boolean dragUp, boolean dragDown){}
	public void drag( boolean dragLeft, boolean dragRight, boolean dragUp, boolean dragDown, int leftLimit, int rightLimit, int upLimit, int downLimit){}
	public void drag( int fromX, int fromY, int toX, int toY){}
	public void drag( int fromX, int fromY, int toX, int toY, boolean dragX, boolean dragY){}

	public void drag( Touch from, Touch to){}
	public void drag( Touch from, Touch to, boolean dragX, boolean dragY){}
	public void drag( Touch from, Touch to, boolean dragLeft, boolean dragRight, boolean dragUp, boolean dragDown){}
	public void drag( TouchPair pair, boolean dragLeft, boolean dragRight, boolean dragUp, boolean dragDown){}
	public void drag( TouchPair pair, boolean dragLeft, boolean dragRight, boolean dragUp, boolean dragDown, int leftLimit, int rightLimit, int upLimit, int downLimit){}

	public void dragWithinParent(){}

	public void rst(){}
	public void rst( boolean rotate, boolean scale, boolean translate){}
	public void rst( boolean rotate, boolean scale, boolean translateX, boolean translateY){}

	public void rst( Touch from1, Touch from2, Touch to1, Touch to2){}
	public void rst( Touch from1, Touch from2, Touch to1, Touch to2, boolean rotate, boolean scale, boolean translate){}
	public void rst( Touch from1, Touch from2, Touch to1, Touch to2, boolean rotate, boolean scale, boolean translateX, boolean translateY){}
	public void rst( TouchPair first, TouchPair second, boolean rotate, boolean scale, boolean translateX, boolean translateY){}

	public void rs(){}

	public void pinch(){}
	public void pinch( Touch from1, Touch from2, Touch to1, Touch to2){}

	public void rotate(){}
	public void rotate( Touch from1, Touch from2, Touch to1, Touch to2){}

	public void rotateAbout( float angle, int x, int y){}
	public void rotateAbout( float angle, int mode){}

	public void rotateAboutCentre(){}

	public void hSwipe(){}
	public void hSwipe( int leftLimit, int rightLimit){}

	public void vSwipe(){}
	public void vSwipe( int upLimit, int downLimit){}

	public void swipeLeft(){}
	public void swipeRight(){}
	public void swipeUp(){}
	public void swipeDown(){}

	public void rnt(){}
	public void rnt( float centreRadius){}

	public void rnt( Touch from, Touch to){}
	public void rnt( Touch from, Touch to, float centreRadius){}
	public void rnt( TouchPair pair, float centreRadius){}

	public void toss() {
		// enable physics on this zone to make sure it can move from the toss
		setPhysicsEnabled( true);
		Touch t = getActiveTouch( 0);
		if( zoneBody != null && mJoint != null) {
			mJoint.setTarget(
				new Vec2(
					t.x * SMT.box2dScale,
					( applet.height - t.y) * SMT.box2dScale));
		}
	}

	public PVector getCentre() {
		return fromZoneVector(new PVector(width / 2, height / 2));
	}
	public PVector getOrigin() {
		return fromZoneVector(new PVector(0, 0));
	}

	/**
	 * This makes a PVector into one relative to the zone's matrix
	 * 
	 * @param global The PVector to put into relative coordinate space
	 * @return A PVector relative to the zone's coordinate space
	 */
	public PVector toZoneVector( PVector global) {
		PMatrix3D temp = getGlobalMatrix();
		temp.invert();
		return temp.mult(global, null);
	}

	/**
	 * This makes a PVector into a global coordinates from the given local
	 * zone's coordinate space
	 * 
	 * @param local
	 *            The PVector to put into global coordinate space
	 * @return A PVector relative to the global coordinate space
	 */
	public PVector fromZoneVector( PVector local) {
		return getGlobalMatrix().mult(local, null);
	}

	/**
	 * Returns a matrix relative to global coordinate space
	 * 
	 * @return A PMatrix3D relative to the global coordinate space
	 */
	public PMatrix3D getGlobalMatrix() {
		PMatrix3D temp = new PMatrix3D();
		// list ancestors in order from most distant to closest, in order to
		// apply their matrix's in order
		LinkedList<Zone> ancestors = new LinkedList<Zone>();
		Zone zone = this;
		while (zone.getParent() != null) {
			zone = zone.getParent();
			ancestors.addFirst(zone);
		}
		// apply ancestors matrix's in proper order to make sure image is
		// correctly oriented, but not when backupMatrix is set, as then it
		// already has its parents applied to it.
		if (backupMatrix == null) {
			for (Zone i : ancestors) {
				temp.apply(i.matrix);
			}
		}
		temp.apply(matrix);

		return temp;
	}

	public void setBodyFromMatrix() {
		// get origin position
		PVector o = fromZoneVector(new PVector(width / 2, height / 2));
		// height-y to account for difference in coordinates
		zoneBody.setTransform(new Vec2(o.x * SMT.box2dScale, (applet.height - o.y)
				* SMT.box2dScale), getRotationAngle());
	}

	public void setMatrixFromBody() {
		// set global matrix from zoneBody, then get local matrix from global
		// matrix
		PMatrix3D ng = new PMatrix3D();
		// height-y to account for difference in coordinates
		ng.translate(zoneBody.getPosition().x / SMT.box2dScale,
				(applet.height - zoneBody.getPosition().y / SMT.box2dScale));
		ng.rotate(zoneBody.getAngle());
		ng.translate(-width / 2, -height / 2);
		// ng=PM == (P-1)*ng=M
		PMatrix3D M = new PMatrix3D(matrix);
		M.invert();
		PMatrix3D P = getGlobalMatrix();
		P.apply(M);
		P.invert();
		ng.apply(P);
		matrix.set(ng);
	}

	public void addPhysicsMouseJoint() {
		if (zoneBody != null && mJoint == null && physics) {
			mJointDef = new MouseJointDef();
			mJointDef.maxForce = 1000000.0f;
			mJointDef.frequencyHz = applet.frameRate;
			mJointDef.bodyA = SMT.groundBody;
			mJointDef.bodyB = zoneBody;
			mJointDef.target.set(new Vec2(zoneBody.getPosition().x, zoneBody.getPosition().y));
			mJoint = (MouseJoint) SMT.world.createJoint(mJointDef);
			zoneBody.setAwake(true);
		}
	}
}