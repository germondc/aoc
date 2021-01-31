package test.clyde.aoc.aoc2020;

import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day3 {
	public static void main(String[] args) {
		System.out.println(new Day3().problemA(3, 1));
		System.out.println(new Day3().problemB());
	}
	
	private int problemA(int colShift, int rowShift) {
		List<String> lines = Utils.readFile("2020/Day3.txt");
		char[][] chars = new char[lines.size()][];
		for (int i=0; i<lines.size(); i++) {
			chars[i] = lines.get(i).toCharArray();
		}
		
		int colSize = chars[0].length;
		
		int rowCount = 0;
		int colCount = 0;
		
		int count = 0;
		
		while(rowCount < lines.size()) {
			if (isTree(chars, rowCount, colCount))
				count++;
			rowCount += rowShift;
			colCount += colShift;
			if (colCount >= colSize)
				colCount -= colSize;
		}
		return count;
	}
	
	private long problemB() {
		long result = 1;
		result *= problemA(1,1);
		result *= problemA(3,1);
		result *= problemA(5,1);
		result *= problemA(7,1);
		result *= problemA(1,2);
		return result;
	}
	
	private boolean isTree(char[][] grid, int row, int col) {
		return grid[row][col] == '#';
	}
}
