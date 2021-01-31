package test.clyde.aoc.aoc2016;

import java.util.ArrayList;
import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day1 {
	private static List<String> LINES = Utils.readFile("2016/Day1.txt");

	private int problemA() {
		String line = LINES.get(0);
		String[] directions = line.split(", ");

		int x = 0;
		int y = 0;
		int heading = 0;

		for (String direction : directions) {
			char turn = direction.charAt(0);
			int blocks = Integer.valueOf(direction.substring(1));
			if (turn == 'L')
				heading -= 1;
			else
				heading += 1;

			if (heading == -1)
				heading = 3;
			else if (heading == 4)
				heading = 0;

			if (heading == 0) {
				y += blocks;
			} else if (heading == 1) {
				x += blocks;
			} else if (heading == 2) {
				y -= blocks;
			} else if (heading == 3) {
				x -= blocks;
			}
		}
		return Math.abs(x)+Math.abs(y);
	}
	
	private class Coord {
		int x;
		int y;
		
		public Coord(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
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
			Coord other = (Coord) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}
	
	private int problemB() throws Exception {
		String line = LINES.get(0);
		String[] directions = line.split(", ");

		int x = 0;
		int y = 0;
		int heading = 0;
		
		List<Coord> been = new ArrayList<>();

		for (String direction : directions) {
			char turn = direction.charAt(0);
			int blocks = Integer.valueOf(direction.substring(1));
			if (turn == 'L')
				heading -= 1;
			else
				heading += 1;

			if (heading == -1)
				heading = 3;
			else if (heading == 4)
				heading = 0;

			if (heading == 0) {
				makeMoves(been, x, x, y+1, y+blocks);
				y += blocks;
			} else if (heading == 1) {
				makeMoves(been, x+1, x+blocks, y, y);
				x += blocks;
			} else if (heading == 2) {
				makeMoves(been, x, x, y-blocks, y-1);
				y -= blocks;
			} else if (heading == 3) {
				makeMoves(been, x-blocks, x-1, y, y);
				x -= blocks;
			}
		}
		return 0;
	}

	private int makeMoves(List<Coord> been, int startX, int endX, int startY, int endY) throws Exception {
		for (int x=startX; x<=endX; x++) {
			for (int y=startY; y<=endY; y++) {
				Coord c = new Coord(x, y);
				if (been.contains(c)) {
					throw new Exception("" + (Math.abs(x)+Math.abs(y)));
				}
				been.add(c);
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day1().problemA());
		try {
			System.out.println("B: " + new Day1().problemB());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
