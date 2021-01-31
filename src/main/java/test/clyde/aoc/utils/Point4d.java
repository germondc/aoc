package test.clyde.aoc.utils;

public class Point4d {
	public long w;
	public long x;
	public long y;
	public long z;

	public Point4d() {
		w = 0;
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Point4d(String[] array) {
		w = Long.valueOf(array[0]);
		x = Long.valueOf(array[1]);
		y = Long.valueOf(array[2]);
		z = Long.valueOf(array[3]);
	}

	public Point4d(long[] array) {
		w = array[0];
		x = array[1];
		y = array[2];
		z = array[3];
	}

	public Point4d(long w, long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) w;
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
		Point4d other = (Point4d) obj;
		if (w != other.w)
			return false;
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
		return String.format("(%s, %s, %s, %s)", w, x, y, z);
	}

	public long getManhattanDistance(Point4d p) {
		return Math.abs(this.w - p.w) + Math.abs(this.x - p.x) + Math.abs(this.y - p.y) + Math.abs(this.z - p.z);
	}
}
