/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockrpg;

import java.util.Arrays;

/**
 *
 * @author L
 */
public class Coord3D {
	
	protected final static double ERROR = 0.0000000000001;
	
	protected double x;
	protected double y;
	protected double z;

	/**
	 * Default Coord3D Constructor. Creates a Coord3D at (0,0,0)
	 */
	public Coord3D() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	/**
	 * Custom Coord3D Constructor. Creates a Coord3D at (x,y,z)
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 * @param z
	 *            z-Coordinate
	 */
	public Coord3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Custom Coord3D Constructor. Creates a Coord3D at (x,y,z)
	 * 
	 * @param coords
	 *            double array containing x, y, z information in that order
	 * 
	 */
	public Coord3D(double[] coords) {
		this.x = coords[0];
		this.y = coords[1];
		this.z = coords[2];
	}

	/**
	 * 
	 * @param coords
	 *            Sets coordinate to given array (in x, y, z form)
	 */
	public void setCoord(double[] coords) {
		//Rounds numbers that are very close to integers
		for(int i = 0;i<3;i++) {
			if (Math.abs(coords[i] - Math.round(coords[i])) < ERROR) {
				coords[i] = Math.round(coords[i]);
			}
		}
		this.x = coords[0];
		this.y = coords[1];
		this.z = coords[2];
	}

	/**
	 * 
	 * @param x
	 *            Adds inputed value to the X coordinate
	 */
	public void addX(double x) {
		double[] coords = { this.x+x, this.y, this.z };
		setCoord(coords);
	}

	/**
	 * 
	 * @param y
	 *            Adds inputed value to the Y coordinate
	 */
	public void addY(double y) {
		double[] coords = { this.x, this.y+ y, this.z };
		setCoord(coords);
	}

	/**
	 * 
	 * @param z
	 *            Adds inputed value to the Z coordinate
	 */
	public void addZ(double z) {
		double[] coords = { this.x, this.y, this.z+z };
		setCoord(coords);
	}
	
	/**
	 * 
	 * @param x
	 *            Sets X coordinate to the inputed value
	 */
	public void setX(double x) {
		double[] coords = {x, this.y, this.z };
		setCoord(coords);
	}

	/**
	 * 
	 * @param y
	 *            Sets Y coordinate to the inputed value
	 */
	public void setY(double y) {
		double[] coords = { this.x, y, this.z };
		setCoord(coords);
	}

	/**
	 * 
	 * @param z
	 *            Sets Z coordinate to the inputed value
	 */
	public void setZ(double z) {
		double[] coords = { this.x, this.y, z };
		setCoord(coords);
	}


	/**
	 * 
	 * @return double[3] Array of the x, y, and z values in that order
	 */
	public double[] getCoord() {
		double[] coords = new double[3];
		coords[0] = this.x;
		coords[1] = this.y;
		coords[2] = this.z;

		return coords;
	}
	
    // Overriding equals() to compare two Coord3D objects
    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Coord3D)) {
            return false;
        }

        Coord3D c = (Coord3D) o;
         
        // Compare the data members and return accordingly 
        return Arrays.equals(this.getCoord(), c.getCoord());
    }
}
