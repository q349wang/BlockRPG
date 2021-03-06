package blockrpg;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Coord2D {

	protected final static double ERROR = Coord3D.ERROR;
	protected final static DecimalFormat df = Coord3D.df;

	protected double x;
	protected double y;

	/**
	 * Default Coord2D Constructor. Creates a Coord3D at (0,0)
	 */
	public Coord2D() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Custom Coord2D Constructor. Creates a Coord3D at (x,y)
	 * 
	 * @param x x-Coordinate
	 * @param y y-Coordinate
	 */
	public Coord2D(double x, double y) {
		this.setCoord(new double[] { x, y });
	}

	/**
	 * Custom Coord2D Constructor. Creates a Coord2D at (x,y)
	 * 
	 * @param coords double array containing x, y information in that order
	 * 
	 */
	public Coord2D(double[] coords) {
		this.setCoord(coords);
	}

	/**
	 * Copys another Coord2D
	 * 
	 * @param other Other Coord2D to copy
	 */
	public Coord2D(Coord2D other) {
		this.setCoord(other.getCoord());
	}

	/**
	 * 
	 * @param coords Sets coordinate to given array (in x, y form)
	 */
	public void setCoord(double[] coords) {
		// Rounds numbers that are very close to nearest billionth
		for (int i = 0; i < 2; i++) {
			coords[i] = Double.parseDouble(df.format(coords[i])) + 0.0;
		}
		this.x = coords[0];
		this.y = coords[1];
	}

	/**
	 * 
	 * @return Returns X
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * 
	 * @return Returns Y
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * 
	 * @param x Adds inputed value to the X coordinate
	 */
	public void addX(double x) {
		double[] coords = { this.x + x, this.y };
		setCoord(coords);
	}

	/**
	 * 
	 * @param y Adds inputed value to the Y coordinate
	 */
	public void addY(double y) {
		double[] coords = { this.x, this.y + y };
		setCoord(coords);
	}

	/**
	 * 
	 * @param x Sets X coordinate to the inputed value
	 */
	public void setX(double x) {
		double[] coords = { x, this.y };
		setCoord(coords);
	}

	/**
	 * 
	 * @param y Sets Y coordinate to the inputed value
	 */
	public void setY(double y) {
		double[] coords = { this.x, y };
		setCoord(coords);
	}

	/**
	 * 
	 * @return double[2] Array of the x, and y values in that order
	 */
	public double[] getCoord() {
		double[] coords = new double[2];
		coords[0] = this.x;
		coords[1] = this.y;

		return coords;
	}

	/**
	 * 
	 * @param other Other Coord2D to add
	 * @return returns Coord2D with other added to it
	 */
	public Coord2D add(Coord2D other) {
		Coord2D sum = new Coord2D();
		double coords[] = { this.x + other.x, this.y + other.y };
		sum.setCoord(coords);
		return sum;
	}

	/**
	 * 
	 * @param other Other Coord2D to subtract
	 * @return returns Coord2D with other subtracted from it
	 */
	public Coord2D subtract(Coord2D other) {
		Coord2D diff = new Coord2D();
		double coords[] = { this.x - other.x, this.y - other.y };
		diff.setCoord(coords);
		return diff;
	}

	/**
	 * Rotates Coord2D ang radians counter clockwise
	 * @param ang Angle in radians to turn Coord2D counter clockwise
	 * 
	 */
	public void rotate(double ang) {
		double coords[] = { this.x * Math.cos(ang) - this.y * Math.sin(ang),
				this.x * Math.sin(ang) + this.y * Math.cos(ang) };
		this.setCoord(coords);

	}

	/**
	 * @return Returns copy of this object
	 */
	@Override
	public Coord2D clone() {
		Coord2D clone = new Coord2D(this.getCoord());
		return clone;
	}

	// Overriding equals() to compare two Coord2D objects
	@Override
	public boolean equals(Object other) {

		if (other == this) {
			return true;
		}

		if (!(other instanceof Coord2D)) {
			return false;
		}

		Coord2D coord = (Coord2D) other;
		boolean equals = true;
		for (int i = 0; i < 2; i++) {
			if (Math.abs(this.getCoord()[i] - coord.getCoord()[i]) > ERROR) {
				equals = false;
				break;
			}
		}
		// Compare the data members and return accordingly
		return equals;
	}

	/**
	 * 
	 * @return Returns true if origin
	 */
	public boolean isOrigin() {
		double origin[] = { 0.0, 0.0 };
		return Arrays.equals(this.getCoord(), origin);
	}
	
	@Override
	public String toString() {
		return "[ " + this.x + ", " + this.y + " ]";
	}
}
