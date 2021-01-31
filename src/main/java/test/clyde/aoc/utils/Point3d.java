package test.clyde.aoc.utils;

public class Point3d {
	public long x;
	public long y;
	public long z;

	public Point3d() {
		x = 0;
		y = 0;
		z = 0;
	}

	public Point3d(long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) x;
		result = prime * result + (int) y;
		result = prime * result + (int) z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point3d other = (Point3d) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s, %s)", x, y, z);
	}

	public long getManhattanDistance(Point3d p) {
		return Math.abs(this.x - p.x) + Math.abs(this.y - p.y) + Math.abs(this.z - p.z);
	}
	
	public void piecewiseAdd(Point3d anotherPoint) {
		this.x += anotherPoint.x;
		this.y += anotherPoint.y;
		this.z += anotherPoint.z;
	}
}
