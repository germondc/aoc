package test.clyde.aoc.aoc2016;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day6 {

	private static List<String> LINES = Utils.readFile("2016/Day6.txt");
	// private static List<String> LINES = Utils.readFile("2016/Day6a.txt");

	private String problemA() {

		int[][] letterCounts = new int[8][26];

		for (String line : LINES) {
			int col = 0;
			for (char c : line.toCharArray()) {
				letterCounts[col][c - 'a']++;
				col++;
			}

		}

		String code = "";
		for (int i = 0; i < 8; i++) {
			int maxAt = 0;

			for (int j = 0; j < letterCounts[i].length; j++) {
				maxAt = letterCounts[i][j] > letterCounts[i][maxAt] ? j : maxAt;
			}
			code += (char) (maxAt + 'a');
		}

		return code;
	}

	private String problemB() {

		int[][] letterCounts = new int[8][26];

		for (String line : LINES) {
			int col = 0;
			for (char c : line.toCharArray()) {
				letterCounts[col][c - 'a']++;
				col++;
			}

		}

		String code = "";
		for (int i = 0; i < 8; i++) {
			int minAt = 0;

			for (int j = 0; j < letterCounts[i].length; j++) {
				minAt = letterCounts[i][j] < letterCounts[i][minAt] ? j : minAt;
			}
			code += (char) (minAt + 'a');
		}
		return code;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {

		System.out.println("A: " + new Day6().problemA());
		System.out.println("B: " + new Day6().problemB());
	}
}
