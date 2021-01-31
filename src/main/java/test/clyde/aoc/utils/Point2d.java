package test.clyde.aoc.utils;

import java.util.ArrayList;
import java.util.List;

import test.clyde.aoc.utils.Directions.Direction;

public class Point2d {
	public long x;
	public long y;

	public static final Point2d ORIGIN = new Point2d(0, 0);

	public Point2d() {
		x = 0;
		y = 0;
	}

	public Point2d(Point2d point) {
		x = point.x;
		y = point.y;
	}

	public Point2d(long x, long y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) x;
		result = prime * result + (int) y;
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
		Point2d other = (Point2d) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", x, y);
	}

	public long getManhattanDistance(Point2d p) {
		return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
	}

	public Point2d move(Direction direction) {
		return new Point2d(this.x + direction.getPoint().x, this.y + direction.getPoint().y);
	}
	
	public void piecewiseAdd(Point2d anotherPoint) {
		this.x += anotherPoint.x;
		this.y += anotherPoint.y;
	}

	public Point2d move(String direction) {
		Direction d;
		if (direction.equals("N") || direction.equals("U"))
			d = Direction.up;
		else if (direction.equals("S") || direction.equals("D"))
			d = Direction.down;
		else if (direction.equals("E") || direction.equals("R"))
			d = Direction.right;
		else
			d = Direction.left;
		return move(d);
	}
	
	public List<Point2d> getNextDoor() {
		List<Point2d> result = new ArrayList<>();
		result.add(this.move(Direction.up));
		result.add(this.move(Direction.down));
		result.add(this.move(Direction.left));
		result.add(this.move(Direction.right));
		return result;
	}
	
	public Point3d addDimension(long z) {
		return new Point3d(x, y, z);
	}
}
