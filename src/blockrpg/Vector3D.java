package blockrpg;

public class Vector3D extends Coord3D {

	private double length;

	/**
	 * Default Vector3D Constructor. Creates a Vector3D of (0,0,0) of length 0
	 */
	public Vector3D() {
		super();
		this.length = 0;
	}

	/**
	 * Custom Vector3D Constructor. Creates a Vector3D of (x,y,z) using separate
	 * values with calculated length
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 * @param z
	 *            z-Coordinate
	 */
	public Vector3D(double x, double y, double z) {
		super(x, y, z);
		length = Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Custom Vector3D Constructor. Creates a Vector3D of (x,y,z) using array with
	 * calculated length
	 * 
	 * @param coords
	 *            double array containing x, y, z information in that order
	 * 
	 */
	public Vector3D(double[] coords) {
		super(coords);
		length = Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Normalizes vector to length of 1
	 * 
	 * @return normalized vector as Vector3D
	 */
	public Vector3D normalize() {
		Vector3D normalized = new Vector3D(this.getCoord());
		if (Math.abs(normalized.length - 0.0) < ERROR) {
			return this;
		}
		// Don't normalize if length is already close to 1 or length is 0
		if (Math.abs(normalized.length - 1.0) > ERROR && Math.abs(normalized.length - 0.0) > ERROR) {
			normalized.setX(this.x / normalized.length + 0.0);
			normalized.setY(this.y / normalized.length + 0.0);
			normalized.setZ(this.z / normalized.length + 0.0);
		}
		normalized.length = 1.0;

		return normalized;

	}

	@Override
	public void setCoord(double coords[]) {
		super.setCoord(coords);
		length = Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * 
	 * @return length of Vector3D as double
	 */
	public double getLength() {
		return length;
	}

	/**
	 * 
	 * @param scalar
	 *            multiplies vector by double scalar
	 * 
	 * @return returns vector multiplied by scalar as Vector3D
	 */
	public Vector3D multiply(double scalar) {
		Vector3D product = new Vector3D();
		product.setX(this.x * scalar);
		product.setY(this.y * scalar);
		product.setZ(this.z * scalar);
		product.length = this.length * scalar;

		return product;
	}

	/**
	 * 
	 * @param other
	 *            other Vector3D to dot with
	 * @return returns dot product as double
	 */
	public double dot(Vector3D other) {
		double product = this.x * other.x + this.y * other.y + this.z * other.z;
		if (Math.abs(product - 0.0) < ERROR) {
			product = 0.0;
		}
		
		return product;
	}

	/**
	 * 
	 * @param other
	 *            other Vector3D to cross with
	 * @return returns cross product as Vector3D
	 */
	public Vector3D cross(Vector3D other) {
		Vector3D product = new Vector3D(this.y * other.z - this.z * other.y + 0.0,
				this.z * other.x - this.x * other.z + 0.0, this.x * other.y - this.y * other.x + 0.0);
		return product;
	}

	/**
	 * 
	 * @param other
	 *            other vector for projection
	 * @return returns projection of other vector on this vector as Vector3D
	 */
	public Vector3D proj(Vector3D other) {
		double dotProd = this.dot(other);
		double projVal;
		if (Math.abs(dotProd - 0.0) < ERROR) {
			projVal = 0.0;
		} else {
			projVal = dotProd / (x * x + y * y + z * z) + 0.0;
		}

		return this.multiply(projVal);

	}

	/**
	 * 
	 * @param other
	 *            other vector for rejection
	 * @return returns rejection of other vector on this vector as Vector3D
	 */
	public Vector3D perp(Vector3D other) {
		Vector3D rejection = this.proj(other);
		double coords[] = { other.x - rejection.x, other.y - rejection.y, other.z - rejection.z };
		rejection.setCoord(coords);

		return rejection;
	}

	/**
	 * 
	 * @param ang
	 *            angle in radians
	 * @return Returns Vector3D rotated ang radians counter clockwise about the X
	 *         axis
	 */
	public Vector3D rotateX(double ang) {
		Vector3D rotation = new Vector3D();
		double coords[] = { this.x, this.y * Math.cos(ang) - this.z * Math.sin(ang),
				this.y * Math.sin(ang) + this.z * Math.cos(ang) };
		rotation.setCoord(coords);

		return rotation;
	}

	/**
	 * 
	 * @param ang
	 *            angle in radians
	 * @return Returns Vector3D rotated ang radians counter clockwise about the Y
	 *         axis
	 */
	public Vector3D rotateY(double ang) {
		Vector3D rotation = new Vector3D();
		double coords[] = { this.x * Math.cos(ang) + this.z * Math.sin(ang), this.y,
				-this.x * Math.sin(ang) + this.z * Math.cos(ang) };
		rotation.setCoord(coords);

		return rotation;
	}

	/**
	 * 
	 * @param ang
	 *            angle in radians
	 * @return Returns Vector3D rotated ang radians counter clockwise about the Y
	 *         axis
	 */
	public Vector3D rotateZ(double ang) {
		Vector3D rotation = new Vector3D();
		double coords[] = { this.x * Math.cos(ang) - this.y * Math.sin(ang),
				this.x * Math.sin(ang) + this.y * Math.cos(ang), this.z };
		rotation.setCoord(coords);

		return rotation;
	}

	/**
	 * 
	 * @param ang
	 *            angle in radians
	 * @param axis
	 *            axis to rotate about
	 * @return Returns Vector3D rotated ang radians counter clockwise about the
	 *         specified axis
	 */
	public Vector3D rotate(double ang, Vector3D axis) {
		Vector3D rotation = new Vector3D();
		double[] oldCoords = this.getCoord();
		double[] axisCoords = axis.getCoord();
		double[] newCoords = new double[3];
		double[][] rotMatrix = new double[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i == j) {
					rotMatrix[i][j] = Math.cos(ang);
				} else {
					int index = i + j;
					switch (index) {
					case 1:
						rotMatrix[i][j] = Math.sin(ang) * axisCoords[2] * (j - i);
						break;
					case 2:
						rotMatrix[i][j] = Math.sin(ang) * axisCoords[1] * (i - j) / 2;
						break;
					case 3:
						rotMatrix[i][j] = Math.sin(ang) * axisCoords[0] * (j - i);
						break;
					default:
						break;
					}
				}

				rotMatrix[i][j] += axisCoords[i] * axisCoords[j] * (1 - Math.cos(ang));
			}

		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				newCoords[j] += rotMatrix[i][j] * oldCoords[i] + 0.0;
			}
		}
		rotation.setCoord(newCoords);

		return rotation;
	}
}
