package test.clyde.aoc.aoc2018;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.ArrayUtils;
import test.clyde.aoc.utils.Utils;

public class Day18 {
	private static List<String> LINES = Utils.readFile("2018/Day18.txt");
	private static List<String> TEST = Utils.readFile("2018/Day18a.txt");

	private long problemA(List<String> lines) {
		char[][] theGrid = new char[lines.size()][lines.get(0).length()];
		int y = 0;
		for (String line : lines) {
			int x = 0;
			for (char c : line.toCharArray()) {
				theGrid[y][x] = c;
				x++;
			}
			y++;
		}

		char[][] grid = theGrid;
		for (int i = 0; i < 10; i++) {
			//ArrayUtils.printGrid(grid);
			char[][] update = ArrayUtils.copyGrid(grid);
			for (y = 0; y < grid.length; y++) {
				for (int x = 0; x < grid.length; x++) {
					List<Character> chars = getAdj(grid, x, y);
					if (grid[y][x] == '.' && chars.stream().filter(c -> c == '|').count() >= 3)
						update[y][x] = '|';
					else if (grid[y][x] == '|' && chars.stream().filter(c -> c == '#').count() >= 3)
						update[y][x] = '#';
					else if (grid[y][x] == '#') {
						if (chars.stream().filter(c -> c == '#').count() >= 1
								&& chars.stream().filter(c -> c == '|').count() >= 1)
							update[y][x] = '#';
						else
							update[y][x] = '.';
					}
				}
			}
			grid = update;
		}

		int lumber = 0;
		int tree = 0;
		for (y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				if (grid[y][x] == '|')
					tree++;
				else if (grid[y][x] == '#')
					lumber++;
			}
		}

		return lumber * tree;
	}

	private List<Character> getAdj(char[][] grid, int tx, int ty) {
		List<Character> result = new ArrayList<>();
		for (int y = ty - 1; y <= ty + 1; y++) {
			for (int x = tx - 1; x <= tx + 1; x++) {
				if (x == tx && y == ty || x<0 || y<0 || x>=grid.length || y>= grid.length)
					continue;
				try {
					result.add(grid[y][x]);
				} catch (Throwable t) {
				}
			}
		}
		return result;
	}

	private long problemB(List<String> lines) {
		char[][] theGrid = new char[lines.size()][lines.get(0).length()];
		int y = 0;
		for (String line : lines) {
			int x = 0;
			for (char c : line.toCharArray()) {
				theGrid[y][x] = c;
				x++;
			}
			y++;
		}
		
		Map<Integer, Long> repeats = new HashMap<>();

		char[][] grid = theGrid;
		for (long i = 0; i < 1000000000; i++) {
			char[][] update = ArrayUtils.copyGrid(grid);
			for (y = 0; y < grid.length; y++) {
				for (int x = 0; x < grid.length; x++) {
					List<Character> chars = getAdj(grid, x, y);
					if (grid[y][x] == '.' && chars.stream().filter(c -> c == '|').count() >= 3)
						update[y][x] = '|';
					else if (grid[y][x] == '|' && chars.stream().filter(c -> c == '#').count() >= 3)
						update[y][x] = '#';
					else if (grid[y][x] == '#') {
						if (chars.stream().filter(c -> c == '#').count() >= 1
								&& chars.stream().filter(c -> c == '|').count() >= 1)
							update[y][x] = '#';
						else
							update[y][x] = '.';
					}
				}
			}
			int hash = Arrays.deepHashCode(grid);
			if (repeats.containsKey(hash)) {
				long first = repeats.get(hash);
				long jump = ((long)((1000000000 - first) / (i - first))-1) * (i - first);
				i += jump;
			} else {
				repeats.put(hash, i);
			}
			grid = update;
		}
		
		int lumber = 0;
		int tree = 0;
		for (y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid.length; x++) {
				if (grid[y][x] == '|')
					tree++;
				else if (grid[y][x] == '#')
					lumber++;
			}
		}

		return lumber * tree;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day18().problemA(TEST));
		System.out.println("A: " + new Day18().problemA(LINES));
		System.out.println("B: " + new Day18().problemB(LINES));
	}

}
