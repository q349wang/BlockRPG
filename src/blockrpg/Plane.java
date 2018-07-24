package blockrpg;

public class Plane {
	private Vector3D vecX;
	private Vector3D vecY;
	private Vector3D norm;
	private double d;
	private Position3D pos;

	/**
	 * Default constructor for Plane
	 */
	public Plane() {
		vecX = new Vector3D();
		vecY = new Vector3D();
		norm = new Vector3D();
		this.setD(0);
		pos = new Position3D();
	}

	/**
	 * Custom constructor for plane
	 * 
	 * @param vecX X-axis Vector3D
	 * @param vecY Y-axis Vector3D
	 * @param pos  Position Position3D
	 */
	public Plane(Vector3D vecX, Vector3D vecY, Position3D pos) {
		this.vecX = vecX.normalize();
		this.vecY = this.vecX.perp(vecY).normalize();
		norm = this.vecX.cross(this.vecY);
		this.pos = pos;
		setD(norm.dot(this.pos.toVec()));
	}

	/**
	 * Copies other Plane
	 * 
	 * @param other Other Plane to copy
	 */
	public Plane(Plane other) {
		this.pos = other.pos.clone();
		this.vecX = other.vecX.clone();
		this.vecY = other.vecY.clone();
		this.norm = other.norm.clone();
		this.d = other.d;
	}

	@Override
	public Plane clone() {
		return new Plane(this);
	}

	/**
	 * Set X-axis to given coords direction
	 * 
	 * @param coords double array of coords
	 */
	public void setvecX(double[] coords) {
		this.vecX.setCoord(coords);
		this.vecX = this.vecX.normalize();
		this.vecY = this.vecX.perp(this.vecY).normalize();
		norm = this.vecX.cross(this.vecY);
		setD(norm.dot(this.pos.toVec()));
	}

	/**
	 * Set Y-axis to given coords direction
	 * 
	 * @param coords double array of coords
	 */
	public void setvecY(double[] coords) {
		this.vecY.setCoord(coords);
		this.vecY = this.vecX.perp(vecY).normalize();
		norm = this.vecX.cross(this.vecY);
		setD(norm.dot(this.pos.toVec()));
	}

	/**
	 * Set position to given coords
	 * 
	 * @param coords double array of coords
	 */
	public void setPos(double[] coords) {
		this.pos.setCoord(coords);
	}

	/**
	 * 
	 * @return Returns x axis vector
	 */
	public Vector3D getVecX() {
		return this.vecX;
	}

	/**
	 * 
	 * @return Returns y axis vector
	 */
	public Vector3D getVecY() {
		return this.vecY;
	}

	/**
	 * 
	 * @return Returns position
	 */
	public Position3D getPos() {
		return this.pos;
	}

	/**
	 * 
	 * @return Returns normal vector
	 */
	public Vector3D getNorm() {
		return this.norm;
	}

	/**
	 * 
	 * @param x Adds inputed value to the X coordinate
	 */
	public void addX(double x) {
		this.pos.addX(x);
	}

	/**
	 * 
	 * @param y Adds inputed value to the Y coordinate
	 */
	public void addY(double y) {
		this.pos.addY(y);
	}

	/**
	 * 
	 * @param z Adds inputed value to the Z coordinate
	 */
	public void addZ(double z) {
		this.pos.addZ(z);
	}

	/**
	 * 
	 * @param x Sets X coordinate to the inputed value
	 */
	public void setX(double x) {
		this.pos.setX(x);
	}

	/**
	 * 
	 * @param y Sets Y coordinate to the inputed value
	 */
	public void setY(double y) {
		this.pos.setY(y);
	}

	/**
	 * 
	 * @param z Sets Z coordinate to the inputed value
	 */
	public void setZ(double z) {
		this.pos.setZ(z);
	}

	/**
	 * 
	 * @return d
	 */
	public double getD() {
		return d;
	}

	/**
	 * Set d
	 * 
	 * @param d double
	 */
	private void setD(double d) {
		this.d = d;
	}

	public Plane rotatePlane(double ang, Vector3D axis) {
		Plane rotatedPlane = new Plane(this);
		rotatedPlane.vecX = vecX.rotate(ang, axis);
		rotatedPlane.vecY = vecY.rotate(ang, axis);
		rotatedPlane.norm = norm.rotate(ang, axis);
		return rotatedPlane;
	}

	/**
	 * 
	 * @param point2D Point on plane as 2D space
	 * @return Returns position in 3D space that point2D corresponds to as
	 *         Position3D
	 */
	public Position3D placeOnPlane(Position2D point2D) {
		Position3D point3D = new Position3D();
		Vector2D dirOfPoint = new Vector2D(point2D.getCoord());
		Vector2D xAxis = new Vector2D(1, 0);
		Vector2D yAxis = new Vector2D(0, 1);

		double xScale = xAxis.proj(dirOfPoint).getLength();
		double yScale = yAxis.proj(dirOfPoint).getLength();

		if (xAxis.dot(dirOfPoint) < 0) {
			xScale = -xScale;
		}

		if (yAxis.dot(dirOfPoint) < 0) {
			yScale = -yScale;
		}

		point3D.setCoord(this.vecX.multiply(xScale).add(this.vecY.multiply(yScale)).getCoord());
		point3D = point3D.add(this.pos);
		return point3D;
	}

	/**
	 * 
	 * @param other Other plane to compare
	 * @return Returns whether two planes are parallel
	 */
	public boolean isParallel(Plane other) {
		return this.norm.isParallel(other.norm);
	}

	/**
	 * 
	 * @param other Other Line3D to compare
	 * @return Returns whether a line and a plane are parallel
	 */
	public boolean isParallel(Line3D other) {
		return Math.abs(this.norm.dot(other.getDir())) < Coord3D.ERROR;
	}

	/**
	 * 
	 * @param pos Position3D to check
	 * @return Returns whether pos is on plane
	 */
	public boolean onPlane(Position3D pos) {
		double result = norm.x * pos.x + norm.y * pos.y + norm.z * pos.z;
		return Math.abs(result - d) < Coord3D.ERROR;
	}

	/**
	 * Finds Position3D that a Line3D intersects a Plane on (returns null if Line3D
	 * is parallel to Plane)
	 * 
	 * @param other
	 * @return Returns Position3D that is on line and plane
	 */
	public Position3D getIntersect(Line3D other) {
		if (this.isParallel(other)) {
			return null;
		}

		double t = (this.d - this.norm.dot(other.getPos().toVec())) / this.norm.dot(other.getDir());
		return other.getLinePoint(t);
	}

	// Overriding equals() to compare two Plane objects
	@Override
	public boolean equals(Object other) {

		if (other == this) {
			return true;
		}

		if (!(other instanceof Plane)) {
			return false;
		}

		Plane p = (Plane) other;

		return this.vecX.equals(p.vecX) && this.pos.equals(p.pos) && this.vecY.equals(p.vecY);

	}

	@Override
	public String toString() {
		return "X Direction: " + this.vecX.toString() + "\nY Direction" + this.vecY.toString() + "\nPosition: "
				+ this.pos.toString();
	}
}
