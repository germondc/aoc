package test.clyde.aoc.aoc2015;

import java.util.Arrays;
import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day18 {
	private static List<String> LINES = Utils.readFile("2015/Day18.txt");
	
	
	
	private int theMax;
	
	private void test() {
		String test = ".#.#.#\n"
				+ "...##.\n"
				+ "#....#\n"
				+ "..#...\n"
				+ "#.#..#\n"
				+ "####..";
		LINES = Arrays.asList(test.split("\n"));
		problemA(6, 4);
	}
	
	private void test2() {
		String test = "##.#.#\n"
				+ "...##.\n"
				+ "#....#\n"
				+ "..#...\n"
				+ "#.#..#\n"
				+ "####.#";
		LINES = Arrays.asList(test.split("\n"));
		problemB(6, 5);
	}
	
	private void problemA(int theMax, int iterations) {
		this.theMax = theMax;
		boolean[][] grid = new boolean[theMax][theMax];
		int row = 0;
		for (String line : LINES) {
			int col=0;
			for (char c : line.toCharArray()) {
				grid[row][col++] = c=='#';
			}
			row++;
		}
		
		for (int i=0; i<iterations; i++) {
			boolean[][] newGrid = copyGrid(grid);
			for (row=0; row<theMax; row++) {
				for (int col=0; col<theMax; col++) {
					boolean isOn = grid[row][col];
					int neighOn = countOnNeighbours(grid, row, col);
					if (isOn) {
						if (neighOn != 2 && neighOn != 3) {
							newGrid[row][col] = false;
						}
					} else {
						if (neighOn == 3) {
							newGrid[row][col] = true;
						}
					}
				}
			}
			grid = newGrid;
		}
		
		System.out.println("A: " + countOn(grid, 0, theMax-1, 0, theMax-1));
	}
	
	private void problemB(int theMax, int iterations) {
		this.theMax = theMax;
		boolean[][] grid = new boolean[theMax][theMax];
		int row = 0;
		for (String line : LINES) {
			int col=0;
			for (char c : line.toCharArray()) {
				grid[row][col++] = c=='#';
			}
			row++;
		}
		grid[0][0] = true;
		grid[0][theMax-1] = true;
		grid[theMax-1][0] = true;
		grid[theMax-1][theMax-1] = true;
		
		for (int i=0; i<iterations; i++) {
			boolean[][] newGrid = copyGrid(grid);
			for (row=0; row<theMax; row++) {
				for (int col=0; col<theMax; col++) {
					boolean isOn = grid[row][col];
					int neighOn = countOnNeighbours(grid, row, col);
					if (isOn) {
						if (neighOn != 2 && neighOn != 3) {
							newGrid[row][col] = false;
						}
					} else {
						if (neighOn == 3) {
							newGrid[row][col] = true;
						}
					}
				}
			}
			grid = newGrid;
			grid[0][0] = true;
			grid[0][theMax-1] = true;
			grid[theMax-1][0] = true;
			grid[theMax-1][theMax-1] = true;
		}
		
		System.out.println("B: " + countOn(grid, 0, theMax-1, 0, theMax-1));
	}
	
	private boolean[][] copyGrid(boolean[][] source) {
		boolean[][] dest = new boolean[theMax][theMax];
		for (int row=0; row<theMax; row++) {
			for (int col=0; col<theMax; col++) {
				dest[row][col] = source[row][col];
			}
		}
		return dest;
	}
	
	private int countOnNeighbours(boolean[][] grid, int row, int col) {
		int row1 = Math.max(0, row-1);
		int col1 = Math.max(0, col-1);
		int row2 = Math.min(theMax-1, row+1);
		int col2 = Math.min(theMax-1, col+1);
		
		int count = countOn(grid, row1, row2, col1, col2);
		if (grid[row][col])
			count--;
		return count;
	}
	
	private int countOn(boolean[][] grid, int row1, int row2, int col1, int col2) {
		int count = 0;
		for (int x=row1; x<=row2; x++) {
			for (int y=col1; y<=col2; y++) {
				if (grid[x][y])
					count++;
			}
		}
		return count;
	}

	public static void main(String[] argv) {
//		new Day18().test();
//		new Day18().test2();
		new Day18().problemA(100, 100);
		new Day18().problemB(100, 100);
	}
}
