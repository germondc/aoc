package test.clyde.aoc.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayUtils {
	public static int[] rotate(int[] current, int amount) {
		int[] result = new int[current.length];
		for (int i = 0; i < current.length; i++) {
			int index = i - amount;
			while (index < 0)
				index += current.length;
			while (index >= current.length) {
				index -= current.length;
			}
			result[i] += current[index];
		}
		return result;
	}
	
	public static <T> void printGrid(T[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				System.out.print(grid[y][x]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void printGrid(char[][] grid) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				System.out.print(grid[y][x]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static char[][] copyGrid(char[][] grid) {
		char[][] result = new char[grid.length][grid[0].length];
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				result[y][x] = grid[y][x];
			}
		}

		return result;
	}
	
	public static char[][] create2DFromString(String input, int width, int height) {
		int colIndex = 0;
		int rowIndex = 0;
		char[][] result = new char[height][width];
		
		for (char c : input.toCharArray()) {
			colIndex++;
			if (colIndex % width == 0) {
				colIndex = 0;
				rowIndex++;
			}
			result[rowIndex][colIndex] = c;
		}
		return result;
	}
	
	public static char[][][] create3DFromString(String input, int width, int height, int depth) {
		int colIndex = 0;
		int rowIndex = 0;
		int depthIndex = 0;
		char[][][] result = new char[depth][height][width];
		
		for (char c : input.toCharArray()) {
			colIndex++;
			if (colIndex == width) {
				colIndex = 0;
				rowIndex++;
			}
			if (rowIndex == height) {
				rowIndex=0;
				depthIndex++;
			}
			result[depthIndex][rowIndex][colIndex] = c;
		}
		return result;
	}
}
