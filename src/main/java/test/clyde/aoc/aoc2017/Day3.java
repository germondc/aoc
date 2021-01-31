package test.clyde.aoc.aoc2017;

import java.awt.Point;
import java.util.List;

public class Day3 {

	private Point[] directions = new Point[] { new Point(1,0), new Point(0,-1), new Point(-1,0), new Point(0,1) };
	
	private long problemA(int input) {
		int goal = input;
		int size = (int) Math.ceil(Math.sqrt(goal));
		if (size % 2 != 1)
			size++;
		int[][] grid = buildGrid(size, size);
		int dirIndex = 3;
		int mid = (size-1)/2;
		int x = mid;
		int y = mid;
		int number = 0;
		int counter = 0;
		while (true) {
			number++;
			grid[y][x] = number;
			if (number == goal)
				break;
			if ((directions[dirIndex].y==0 && Math.abs(x-mid) >= counter) || (directions[dirIndex].x==0 && Math.abs(y-mid) >= counter)) {
				dirIndex++;
				if (dirIndex >= directions.length) {
					dirIndex = 0;
					counter++;
				}
			}
			x += directions[dirIndex].x;
			y += directions[dirIndex].y;
		}
		
		int result = Math.abs(x-mid) + Math.abs(y-mid);
		return result;
	}
	
	private long problemB(int input) {
		int goal = input;
		int size = (int) Math.ceil(Math.sqrt(goal));
		if (size % 2 != 1)
			size++;
		int[][] grid = buildGrid(size, size);
		int dirIndex = 3;
		int mid = (size-1)/2;
		int x = mid;
		int y = mid;
		int counter = 0;
		while (true) {
			if (x==mid && y==mid)
				grid[y][x] = 1;
			else
				grid[y][x] = getSurrounds(grid, x,y);
			if (grid[y][x] > goal)
				break;
			if ((directions[dirIndex].y==0 && Math.abs(x-mid) >= counter) || (directions[dirIndex].x==0 && Math.abs(y-mid) >= counter)) {
				dirIndex++;
				if (dirIndex >= directions.length) {
					dirIndex = 0;
					counter++;
				}
			}
			x += directions[dirIndex].x;
			y += directions[dirIndex].y;
		}
		
		return grid[y][x];
	}
	
	private int getSurrounds(int[][] grid, int x, int y) {
		int result = 0;
		for (int yy=-1; yy<2; yy++) {
			for (int xx=-1; xx<2; xx++) {
				try {
					result += grid[y+yy][x+xx];
				} catch (Throwable t) {}
			}
		}
		return result;
	}
	
	private int[][] buildGrid(int x, int y) {
		int[][] grid = new int[y][x];
		return grid;
	}

	private long problemB(List<String> lines) {

		return 0;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day3().problemA(1024));
		System.out.println("A: " + new Day3().problemA(277678));
		System.out.println("TestB: " + new Day3().problemB(805));
		System.out.println("B: " + new Day3().problemB(277678));
	}

}
