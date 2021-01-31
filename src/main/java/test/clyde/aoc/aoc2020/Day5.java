package test.clyde.aoc.aoc2020;

import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day5 {
	
	private static List<String> LINES = Utils.readFile("2020/Day5.txt");
	
	public static void main(String[] args) {
		System.out.println(new Day5().getId("BBFFBBFRLL"));
		System.out.println("A: " + new Day5().problemA());
		System.out.println(new Day5().problemB());
	}
	
	private int problemA() {
		int currentMax = 0;
		for (String line : LINES) {
			int value = getId(line);
			if (value > currentMax)
				currentMax = value;
		}
		return currentMax;
	}
	
	private int problemB() {
		boolean[][] seatsTaken = new boolean[128][8];
		for (String line : LINES) {
			int row = getRow(line);
			int col = getCol(line);
			seatsTaken[row][col] = true;
			if (row ==67) {
				System.out.println(line);
			}
		}
		
		int firstRow = 0;
		int lastRow = 0;
		main:
		for (int i=0; i<128; i++) {
			for (int j=0; j<8; j++) {
				if (seatsTaken[i][j]) {
					firstRow = i;
					break main;
				}
			}
		}
		
		main:
			for (int i=0; i<128; i++) {
				for (int j=0; j<8; j++) {
					if (seatsTaken[127-i][j]) {
						lastRow = 127-i;
						break main;
					}
				}
			}
		
		for (int i=firstRow+1; i<lastRow-1; i++) {
			for (int j=0; j<8; j++) {
				if (!seatsTaken[i][j]) {
					System.out.println(i + " " + j);
					return i * 8 + j;
				}
			}
		}
		
		return 0;
	}
	
	private int getRow(String line) {
		char[] c = line.toCharArray();
		int min = 0;
		int max = 128;
		int row = -1;
		for (int i=0; i<7; i++) {
			int mid = (max - min) / 2;
			if (c[i] == 'F') {
				max -= mid;
				row = max - 1;
			} else {
				min += mid;
				row = min;
			}
		}
		return row;
	}
	
	private int getCol(String line) {
		char[] c = line.toCharArray();
		int minA=0;
		int maxA=8;
		int col = -1;
		for (int i=7; i<10; i++) {
			int mid = (maxA - minA) / 2;
			if (c[i] == 'L') {
				maxA -= mid;
				col = maxA - 1;
			} else {
				minA += mid;
				col = minA;
			}
		}
		return col;
	}
	
	private int getId(String line) {
		char[] c = line.toCharArray();
		int min = 0;
		int max = 128;
		int row = -1;
		for (int i=0; i<7; i++) {
			int mid = (max - min) / 2;
			if (c[i] == 'F') {
				max -= mid;
				row = max - 1;
			} else {
				min += mid;
				row = min;
			}
		}
		
		int minA=0;
		int maxA=8;
		int col = -1;
		for (int i=7; i<10; i++) {
			int mid = (maxA - minA) / 2;
			if (c[i] == 'L') {
				maxA -= mid;
				col = maxA - 1;
			} else {
				minA += mid;
				col = minA;
			}
		}
		return row * 8 + col;
	}
}
