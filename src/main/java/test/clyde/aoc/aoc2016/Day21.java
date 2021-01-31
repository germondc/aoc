package test.clyde.aoc.aoc2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day21 {

	private static List<String> LINES = Utils.readFile("2016/Day21.txt");
	private static List<String> TEST_LINES = Utils.readFile("2016/Day21a.txt");

	private String problemA(List<String> lines, String password) {

		String result = new String(password);
		Pattern rule1_pat = Pattern.compile("swap position ([0-9]+) with position ([0-9]+)");
		Pattern rule2_pat = Pattern.compile("swap letter (.) with letter (.)");
		Pattern rule3_pat = Pattern.compile("rotate (.*) ([0-9]+) step");
		Pattern rule4_pat = Pattern.compile("rotate based on position of letter (.)");
		Pattern rule5_pat = Pattern.compile("reverse positions ([0-9]+) through ([0-9]+)");
		Pattern rule6_pat = Pattern.compile("move position ([0-9]+) to position ([0-9]+)");

		for (String line : lines) {
			if (line.startsWith("swap position")) {
				Matcher m = rule1_pat.matcher(line);
				while (m.find()) {
					int x = Integer.valueOf(m.group(1));
					int y = Integer.valueOf(m.group(2));

					char[] chars = result.toCharArray();
					chars[x] = result.charAt(y);
					chars[y] = result.charAt(x);
					result = new String(chars);
				}
			} else if (line.startsWith("swap letter")) {
				Matcher m = rule2_pat.matcher(line);
				while (m.find()) {
					String x = m.group(1);
					String y = m.group(2);
					int xVal = result.indexOf(x);
					int yVal = result.indexOf(y);
					char[] chars = result.toCharArray();
					chars[xVal] = result.charAt(yVal);
					chars[yVal] = result.charAt(xVal);
					result = new String(chars);
				}
			} else if (line.startsWith("rotate based")) {
				Matcher m = rule4_pat.matcher(line);
				while (m.find()) {
					String x = m.group(1);
					int xVal = result.indexOf(x);
					int shift = 1;
					if (xVal >= 4)
						shift++;
					result = rotate("right", result, shift + xVal);
				}
			} else if (line.startsWith("rotate")) {
				Matcher m = rule3_pat.matcher(line);
				while (m.find()) {
					String leftRight = m.group(1);
					int x = Integer.valueOf(m.group(2));
					result = rotate(leftRight, result, x);
				}
			} else if (line.startsWith("reverse")) {
				Matcher m = rule5_pat.matcher(line);
				while (m.find()) {
					int x = Integer.valueOf(m.group(1));
					int y = Integer.valueOf(m.group(2));
					char[] chars = new char[result.length()];
					for (int i = 0; i < x; i++) {
						chars[i] = result.charAt(i);
					}
					for (int i = x; i <= y; i++) {
						chars[i] = result.charAt(y - i + x);
					}
					for (int i = y + 1; i < result.length(); i++) {
						chars[i] = result.charAt(i);
					}
					result = new String(chars);
				}
			} else if (line.startsWith("move")) {
				Matcher m = rule6_pat.matcher(line);
				while (m.find()) {
					int x = Integer.valueOf(m.group(1));
					int y = Integer.valueOf(m.group(2));
					char[] chars = new char[result.length()];
					if (x < y) {
						for (int i = 0; i < x; i++) {
							chars[i] = result.charAt(i);
						}
						for (int i = x + 1; i <= y; i++) {
							chars[i - 1] = result.charAt(i);
						}
						chars[y] = result.charAt(x);
						for (int i = y + 1; i < result.length(); i++) {
							chars[i] = result.charAt(i);
						}
					} else {
						for (int i = 0; i < y; i++) {
							chars[i] = result.charAt(i);
						}
						chars[y] = result.charAt(x);
						for (int i = y + 1; i <= x; i++) {
							chars[i] = result.charAt(i - 1);
						}
						for (int i = x + 1; i < result.length(); i++) {
							chars[i] = result.charAt(i);
						}
					}
					result = new String(chars);
				}
			}

		}
		return result;
	}

	private String rotate(String leftRight, String result, int x) {
		char[] chars = new char[result.length()];

		while (x < 0) {
			x += result.length();
		}

		while (x >= result.length()) {
			x -= result.length();
		}

		if (leftRight.equals("left")) {
			for (int i = 0; i < result.length() - x; i++) {
				chars[i] = result.charAt(i + x);
			}
			for (int i = 0; i < x; i++) {
				chars[i + result.length() - x] = result.charAt(i);
			}
		} else {
			for (int i = x; i < result.length(); i++) {
				chars[i] = result.charAt(i - x);
			}
			for (int i = 0; i < x; i++) {
				chars[i] = result.charAt(result.length() - x + i);
			}
		}
		result = new String(chars);
		return result;
	}

	private String problemB(List<String> lines, String password) {
		List<String> reservedLines = new ArrayList<>(lines);
		Collections.reverse(reservedLines);

		String result = new String(password);
		Pattern rule1_pat = Pattern.compile("swap position ([0-9]+) with position ([0-9]+)");
		Pattern rule2_pat = Pattern.compile("swap letter (.) with letter (.)");
		Pattern rule3_pat = Pattern.compile("rotate (.*) ([0-9]+) step");
		Pattern rule4_pat = Pattern.compile("rotate based on position of letter (.)");
		Pattern rule5_pat = Pattern.compile("reverse positions ([0-9]+) through ([0-9]+)");
		Pattern rule6_pat = Pattern.compile("move position ([0-9]+) to position ([0-9]+)");

		for (String line : reservedLines) {
			if (line.startsWith("swap position")) {
				Matcher m = rule1_pat.matcher(line);
				while (m.find()) {
					int x = Integer.valueOf(m.group(1));
					int y = Integer.valueOf(m.group(2));

					char[] chars = result.toCharArray();
					chars[x] = result.charAt(y);
					chars[y] = result.charAt(x);
					result = new String(chars);
				}
			} else if (line.startsWith("swap letter")) {
				Matcher m = rule2_pat.matcher(line);
				while (m.find()) {
					String x = m.group(1);
					String y = m.group(2);
					int xVal = result.indexOf(x);
					int yVal = result.indexOf(y);
					char[] chars = result.toCharArray();
					chars[xVal] = result.charAt(yVal);
					chars[yVal] = result.charAt(xVal);
					result = new String(chars);
				}
			} else if (line.startsWith("rotate based")) {
				Matcher m = rule4_pat.matcher(line);
				while (m.find()) {
					String x = m.group(1);
					int xVal = result.indexOf(x);
					boolean done = false;
					while (!done) {
						result = rotate("left", result, 1);
						int xValTemp = result.indexOf(x);
						int newPos = (1 + ((xValTemp >= 4) ? 1 : 0) + xValTemp) + xValTemp;
						while (newPos >= result.length())
							newPos -= result.length();
						if (newPos == xVal)
							done = true;
					}
				}
			} else if (line.startsWith("rotate")) {
				Matcher m = rule3_pat.matcher(line);
				while (m.find()) {
					String leftRight = m.group(1);
					if (leftRight.equals("left"))
						leftRight = "right";
					else
						leftRight = "left";
					int x = Integer.valueOf(m.group(2));
					result = rotate(leftRight, result, x);
				}
			} else if (line.startsWith("reverse")) {
				Matcher m = rule5_pat.matcher(line);
				while (m.find()) {
					int x = Integer.valueOf(m.group(1));
					int y = Integer.valueOf(m.group(2));
					char[] chars = new char[result.length()];
					for (int i = 0; i < x; i++) {
						chars[i] = result.charAt(i);
					}
					for (int i = x; i <= y; i++) {
						chars[i] = result.charAt(y - i + x);
					}
					for (int i = y + 1; i < result.length(); i++) {
						chars[i] = result.charAt(i);
					}
					result = new String(chars);
				}
			} else if (line.startsWith("move")) {
				Matcher m = rule6_pat.matcher(line);
				while (m.find()) {
					int x = Integer.valueOf(m.group(2));
					int y = Integer.valueOf(m.group(1));
					char[] chars = new char[result.length()];
					if (x < y) {
						for (int i = 0; i < x; i++) {
							chars[i] = result.charAt(i);
						}
						for (int i = x + 1; i <= y; i++) {
							chars[i - 1] = result.charAt(i);
						}
						chars[y] = result.charAt(x);
						for (int i = y + 1; i < result.length(); i++) {
							chars[i] = result.charAt(i);
						}
					} else {
						for (int i = 0; i < y; i++) {
							chars[i] = result.charAt(i);
						}
						chars[y] = result.charAt(x);
						for (int i = y + 1; i <= x; i++) {
							chars[i] = result.charAt(i - 1);
						}
						for (int i = x + 1; i < result.length(); i++) {
							chars[i] = result.charAt(i);
						}
					}
					result = new String(chars);
				}
			}

		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day21().problemA(TEST_LINES, "abcde"));
		System.out.println("A: " + new Day21().problemA(LINES, "abcdefgh"));
		System.out.println("TestB: " + new Day21().problemB(TEST_LINES, "decab"));
		System.out.println("B: " + new Day21().problemB(LINES, "fbgdceah"));

	}
}
