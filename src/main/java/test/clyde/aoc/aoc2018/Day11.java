package test.clyde.aoc.aoc2018;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day11 {

	private String problemA(int input) {
		int[][] grid = new int[300][300];
		for (int y = 0; y < 300; y++) {
			for (int x = 0; x < 300; x++) {

				grid[y][x] = getPower(input, x, y);
			}
		}

		int valueX = -1;
		int valueY = -1;
		long largest = 0;
		for (int y = 0; y < 298; y++) {
			for (int x = 0; x < 298; x++) {
				long value = 0;
				for (int dy = 0; dy < 3; dy++) {
					for (int dx = 0; dx < 3; dx++) {
						value += grid[y + dy][x + dx];
					}
				}
				if (value > largest) {
					largest = value;
					valueX = x;
					valueY = y;
				}
			}
		}

		return valueX + "," + valueY;
	}

	private int getPower(int input, int x, int y) {
		int rack = x + 10;
		int power = rack * y + input;
		power *= rack;
		String stringPower = Integer.toString(power);
		int hundreds;
		if (stringPower.length() < 3)
			hundreds = 0;
		else
			hundreds = stringPower.charAt(stringPower.length() - 3) - '0';
		return hundreds - 5;
	}

	private String problemB(int input) {
		int[][] grid = new int[300][300];
		for (int y = 0; y < 300; y++) {
			for (int x = 0; x < 300; x++) {

				grid[y][x] = getPower(input, x, y);
			}
		}

		int valueX = -1;
		int valueY = -1;
		int valueDelta = -1;
		long largest = 0;

		for (int y = 0; y < 300; y++) {
			for (int x = 0; x < 300; x++) {
				int square = 1;
				int value = 0;
				while (true) {
					for (int row = y; row < (y + square); row++) {
						value += grid[row][x + square - 1];
					}
					for (int col = x; col < (x + square) - 1; col++) {
						value += grid[y + square - 1][col];
					}
					if (value > largest) {
						largest = value;
						valueX = x;
						valueY = y;
						valueDelta = square;
					}
					square++;
					if (x + square > 299 || y + square > 299)
						break;
				}
			}
		}

		return valueX + "," + valueY + "," + valueDelta;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day11().getPower(8, 3, 5));
		System.out.println("TestA: " + new Day11().getPower(57, 122, 79));
		System.out.println("TestA: " + new Day11().getPower(39, 217, 196));
		System.out.println("TestA: " + new Day11().getPower(71, 101, 153));
		System.out.println("TestA: " + new Day11().problemA(18));
		System.out.println("TestA: " + new Day11().problemA(42));
		System.out.println("A: " + new Day11().problemA(3214));

		System.out.println("TestB: " + new Day11().problemB(18));
		System.out.println("TestB: " + new Day11().problemB(42));
		System.out.println("B: " + new Day11().problemB(3214));
	}

}
