package test.clyde.aoc.aoc2017;

import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day1 {

	private static List<String> LINES = Utils.readFile("2017/Day1.txt");
	
	private long problemA(List<String> lines) {
		String line = lines.get(0);
		line += line.charAt(0);
		char[] ca = line.toCharArray();
		
		int sum = 0;
		for (int i=0; i< ca.length-1; i++) {
			if (ca[i] == ca[i+1]) {
				sum += (ca[i] - '0');
			}
		}
		
		return sum;
	}
	
	private long problemB(List<String> lines) {
		String line = lines.get(0);
		String split = line.substring(line.length() / 2);
		split += line.substring(0, line.length() / 2);
		int sum = 0;
		for (int i=0; i<line.length(); i++) {
			if (line.charAt(i) == split.charAt(i)) {
				sum += (line.charAt(i) - '0');
			}
		}
		return sum;
	}
	
	public static void main(String[] args) {
		System.out.println("TestA: " + new Day1().problemA(LINES));
		System.out.println("TestB: " + new Day1().problemB(LINES));
	}

}
