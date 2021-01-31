package test.clyde.aoc.aoc2015;

import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day8 {

	private static List<String> LINES = Utils.readFile("2015/Day8.txt");

	public static void main(String[] args) {
		System.out.println("A: " + new Day8().getCount());
		System.out.println("B: " + new Day8().getCountB());
	}
	
	private long getCount() {
		long result = 0;
		for (String line : LINES) {
			int lineLen = line.length();
			int act = 0;
			for (int i=0; i<line.length(); i++) {
				char c = line.charAt(i);
				if (c == '"') {
					
				} else if (c == '\\') {
					i++;
					c = line.charAt(i);
					if (c == '"' || c == '\\') {
						act++;
					} else if (c == 'x') {
						act++;
						i=i+2;
					}
				} else {
					act = act + 1;
				}
			}
			result += (lineLen - act);
		}
		return result;
	}
	
	private long getCountB() {
		long result = 0;
		for (String line : LINES) {
			int lineLen = line.length();
			int act = 0;
			for (int i=0; i<line.length(); i++) {
				char c = line.charAt(i);
				if (c == '"') {
					act += 2;
				} else if (c == '\\') {
					act += 2;
				} else {
					act = act + 1;
				}
			}
			result += (act + 2 - lineLen);
		}
		return result;
	}
}
