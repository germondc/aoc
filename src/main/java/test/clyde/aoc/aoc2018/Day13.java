package test.clyde.aoc.aoc2018;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day13 {

	private static List<String> LINES = Utils.readFile("2018/Day13.txt");
	private static List<String> TEST = Utils.readFile("2018/Day13a.txt");
	private static List<String> TESTB = Utils.readFile("2018/Day13b.txt");

	private String problemA(List<String> lines) {
		List<Cart> carts = new ArrayList<>();
		char[][] grid = getGrid(lines, carts);
		Point collision = null;
		main: while (true) {
			Collections.sort(carts, (o1, o2) -> o1.compareTo(o2));
			for (Cart c : carts) {
				c.move();
				for (Cart c2 : carts) {
					if (c == c2)
						continue;
					if (c.location.equals(c2.location)) {
						collision = c.location;
						break main;
					}

				}
				char gridChar = grid[c.location.y][c.location.x];
				if (gridChar == '/' || gridChar == '\\') {
					c.direction = c.direction.turn(gridChar);
				} else if (gridChar == '+') {
					c.direction = c.direction.intersection(c.intersectionDirection);
					c.intersectionDirection = c.intersectionDirection.getNext();
				}
			}
		}

		return collision.x + "," + collision.y;
	}

	private String problemB(List<String> lines) {
		List<Cart> carts = new ArrayList<>();
		char[][] grid = getGrid(lines, carts);
		while (carts.size() != 1) {
			Collections.sort(carts, (o1, o2) -> o1.compareTo(o2));
			for (Cart c : carts) {
				c.move();
				for (Cart c2 : carts) {
					if (c == c2)
						continue;
					if (c.location.equals(c2.location)) {
						c.crashed = true;
						c2.crashed = true;
					}

				}
				char gridChar = grid[c.location.y][c.location.x];
				if (gridChar == '/' || gridChar == '\\') {
					c.direction = c.direction.turn(gridChar);
				} else if (gridChar == '+') {
					c.direction = c.direction.intersection(c.intersectionDirection);
					c.intersectionDirection = c.intersectionDirection.getNext();
				}
			}
			carts.removeIf(c -> c.crashed);
		}

		Cart lastCart = carts.get(0);
		return lastCart.location.x + "," + lastCart.location.y;
	}

	private enum Direction {
		up, down, left, right;

		public Direction turn(char c) {
			if (c == '/' && this == Direction.up)
				return Direction.right;
			else if (c == '/' && this == Direction.down)
				return Direction.left;
			else if (c == '/' && this == Direction.left)
				return Direction.down;
			else if (c == '/' && this == Direction.right)
				return Direction.up;
			else if (c == '\\' && this == Direction.up)
				return Direction.left;
			else if (c == '\\' && this == Direction.down)
				return Direction.right;
			else if (c == '\\' && this == Direction.left)
				return Direction.up;
			else if (c == '\\' && this == Direction.right)
				return Direction.down;
			return Direction.left;
		}

		public Direction intersection(IntersectionDirection id) {
			if (id == IntersectionDirection.straight) {
				return this;
			} else if (id == IntersectionDirection.left) {
				if (this == Direction.up) {
					return Direction.left;
				} else if (this == Direction.down) {
					return Direction.right;
				} else if (this == Direction.left) {
					return Direction.down;
				} else if (this == Direction.right) {
					return Direction.up;
				}
			} else {
				if (this == Direction.up) {
					return Direction.right;
				} else if (this == Direction.down) {
					return Direction.left;
				} else if (this == Direction.left) {
					return Direction.up;
				} else if (this == Direction.right) {
					return Direction.down;
				}
			}
			return Direction.left;
		}
	}

	private enum IntersectionDirection {
		left, straight, right;

		public IntersectionDirection getNext() {
			if (this == IntersectionDirection.left)
				return IntersectionDirection.straight;
			else if (this == IntersectionDirection.straight)
				return IntersectionDirection.right;
			else
				return IntersectionDirection.left;
		}
	}

	private class Cart implements Comparable<Cart> {
		Point location;
		Direction direction;
		IntersectionDirection intersectionDirection = IntersectionDirection.left;
		boolean crashed = false;

		public Cart(int x, int y, Direction direction) {
			this.location = new Point(x, y);
			this.direction = direction;
		}

		@Override
		public int compareTo(Cart o) {
			if (this.location.y < o.location.y)
				return -1;
			if (this.location.y > o.location.y)
				return 1;

			return this.location.x - o.location.x;
		}

		public void move() {
			if (direction == Direction.up) {
				this.location.y -= 1;
			} else if (direction == Direction.down) {
				this.location.y += 1;
			} else if (direction == Direction.left) {
				this.location.x -= 1;
			} else if (direction == Direction.right) {
				this.location.x += 1;
			}
		}
	}

	private char[][] getGrid(List<String> lines, List<Cart> carts) {
		char[][] result = new char[lines.size()][lines.get(0).length()];
		int y = 0;
		for (String line : lines) {
			int x = 0;
			for (char c : line.toCharArray()) {
				char actualChar = c;
				if (c == '>') {
					actualChar = '-';
					carts.add(new Cart(x, y, Direction.right));
				} else if (c == '<') {
					actualChar = '-';
					carts.add(new Cart(x, y, Direction.left));
				} else if (c == '^') {
					actualChar = '|';
					carts.add(new Cart(x, y, Direction.up));
				} else if (c == 'v') {
					actualChar = '|';
					carts.add(new Cart(x, y, Direction.down));
				}
				result[y][x] = actualChar;
				x++;
			}
			y++;
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day13().problemA(TEST));
		System.out.println("A: " + new Day13().problemA(LINES));
		System.out.println("TestB: " + new Day13().problemB(TESTB));
		System.out.println("B: " + new Day13().problemB(LINES));
	}

}
