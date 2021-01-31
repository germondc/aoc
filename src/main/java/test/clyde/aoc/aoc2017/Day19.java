package test.clyde.aoc.aoc2017;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import test.clyde.aoc.utils.Utils;

public class Day19 {

	private static List<String> LINES = Utils.readFile("2017/Day19.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day19a.txt");

	private static final Point[] DIRECTIONS = new Point[] { new Point(0, 1), new Point(0, -1), new Point(1, 0),
			new Point(-1, 0) };

	private String problemA(List<String> lines) {
		char[][] grid = new char[lines.size()][lines.get(0).length()];
		int index = 0;
		for (String line : lines) {
			grid[index] = line.toCharArray();
			index++;
		}

		int dx = 0;
		int dy = 1;
		int x = lines.get(0).indexOf('|');
		int y = 0;
		String result = "";
		int counter = 0;
		while (true) {
			if (grid[y][x] >= 'A' && grid[y][x] <= 'Z') {
				result += grid[y][x];
			}

			if (grid[y + dy][x + dx] == ' ') {
				boolean finished = true;
				for (Point p : DIRECTIONS) {
					if (p.x == dx && p.y == dy || p.x == -dx || p.y == -dy)
						continue;
					if (grid[y + p.y][x + p.x] != ' ') {
						dx = p.x;
						dy = p.y;
						finished = false;
						break;
					}
				}
				if (finished) {
					break;
				}
			}
			x += dx;
			y += dy;
			counter++;
		}

		System.out.println("Steps B: " + (counter+1));
		return result;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day19().problemA(TEST_LINES));
		System.out.println("A: " + new Day19().problemA(LINES));
	}

}
