package test.clyde.aoc.utils;

import java.awt.Point;

public class Directions {

	public static Point[] DIRECTIONS_ARRAY = new Point[] { new Point(-1, 0), new Point(0, -1), new Point(1, 0),
			new Point(0, 1) };

	public static Point[] DIRECTIONS_ARRAY_RO = new Point[] { new Point(0, -1), new Point(-1, 0), new Point(1, 0),
			new Point(0, 1) };

	public enum Direction {
		up(new Point(0, -1)), down(new Point(0, 1)), left(new Point(-1, 0)), right(new Point(1, 0));

		private Point coord;

		Direction(Point point) {
			this.coord = point;
		}

		public Point getPoint() {
			return coord;
		}

		public String getShortName() {
			if (this == up)
				return "U";
			else if (this == down)
				return "D";
			else if (this == left)
				return "L";
			else
				return "R";
		}

		public Direction turnLeft() {
			if (this == Direction.up)
				return Direction.left;
			else if (this == Direction.down)
				return Direction.right;
			else if (this == Direction.left)
				return Direction.down;
			else
				return Direction.up;
		}

		public Direction turnRight() {
			if (this == Direction.up)
				return Direction.right;
			else if (this == Direction.down)
				return Direction.left;
			else if (this == Direction.left)
				return Direction.up;
			else
				return Direction.down;
		}

		public Direction reverse() {
			if (this == Direction.up)
				return Direction.down;
			else if (this == Direction.down)
				return Direction.up;
			else if (this == Direction.left)
				return Direction.right;
			else
				return Direction.left;
		}

		public Direction continueDirection() {
			return this;
		}

		public Direction turn(int degrees) {
			int rounded = (degrees / 90) * 90;
			if (rounded > 360 || rounded < -360)
				rounded = rounded % 360;
			if (rounded < 0)
				rounded += 360;

			if (rounded == 0)
				return continueDirection();
			else if (rounded == 90)
				return turnRight();
			else if (rounded == 180)
				return reverse();
			else if (rounded == 270)
				return turnLeft();

			throw new RuntimeException("failed to turn: " + degrees);
		}
		
		public Direction[] getTurns() {
			Direction[] result = new Direction[2];
			result[0] = this.turnLeft();
			result[1] = this.turnRight();
			return result;
		}
	}
}
