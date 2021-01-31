package test.clyde.aoc.aoc2018;

import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day5 {
	private static List<String> LINES = Utils.readFile("2018/Day5.txt");
	private static List<String> TEST = Utils.readFile("2018/Day5a.txt");

	private long problemA(List<String> lines) {
		String line = lines.get(0);
		boolean moves = true;
		while (moves) {
			moves = false;
			StringBuilder newLine = new StringBuilder();
			for (int i = 0; i < line.length(); i++) {
				if (i < line.length() - 1
						&& Character.toUpperCase(line.charAt(i)) == Character.toUpperCase(line.charAt(i + 1))
						&& line.charAt(i) != line.charAt(i + 1)) {
					moves = true;
					i++;
				} else {
					newLine.append(line.charAt(i));
				}
			}
			line = newLine.toString();
		}
		return line.length();
	}

	private long problemB(List<String> lines) {
		int diff1 = 'a' - 'A';
		int diff2 = 'A' - 'a';
		int best = Integer.MAX_VALUE;
		for (char letter = 'a'; letter <= 'z'; letter++) {
			String line = new String(lines.get(0));
			boolean moves = true;
			while (moves) {
				moves = false;
				StringBuilder newLine = new StringBuilder();
				for (int i = 0; i < line.length(); i++) {
					if (i < line.length() - 1 && (line.charAt(i) - line.charAt(i + 1) == diff1
							|| line.charAt(i) - line.charAt(i + 1) == diff2)) {
						moves = true;
						i++;
					} else if (line.charAt(i) == letter || line.charAt(i) == (letter-diff1)) {
						continue;
					} else {
						newLine.append(line.charAt(i));
					}
				}
				line = newLine.toString();
			}
			if (line.length() < best) {
				best = line.length();
			}
		}
		return best;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day5().problemA(TEST));
		System.out.println("A: " + new Day5().problemA(LINES));
		System.out.println("TestB: " + new Day5().problemB(TEST));
		System.out.println("B: " + new Day5().problemB(LINES));
	}

}
