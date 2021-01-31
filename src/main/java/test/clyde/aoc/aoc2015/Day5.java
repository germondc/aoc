package test.clyde.aoc.aoc2015;

import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day5 {
	private static List<String> LINES = Utils.readFile("2015/Day5.txt");
	
	public static void main(String[] args) {
		System.out.println("A: " + new Day5().getNiceStrings());
		System.out.println("BT: " + new Day5().getNiceStringsB());
	}
	
	private boolean isNice(String line) {
		int vowels = 0;
		vowels += charCount(line, "a");
		vowels += charCount(line, "e");
		vowels += charCount(line, "i");
		vowels += charCount(line, "o");
		vowels += charCount(line, "u");
		if (vowels <3)
			return false;
		
		if (!checkRepeating(line))
			return false;
		
	
		return noContain(line, "ab") && noContain(line, "cd") && noContain(line, "pq")&& noContain(line, "xy");
	}
	
	private boolean noContain(String line, String chars) {
		return !line.contains(chars);
	}
	
	private boolean checkRepeating(String line) {
		char[] chars = line.toCharArray();
		for (int i=0; i<chars.length - 1; i++) {
			if (chars[i] == chars[i+1])
				return true;
		}
		return false;
	}
	
	private int charCount(String line, String c) {
		return line.length() - line.replace(c, "").length();
	}
	
	private int getNiceStrings() {
		int result = 0;
		for (String line : LINES) {
			if (isNice(line))
				result++;
		}
		return result;
	}
	
	private int getNiceStringsB() {
		int result = 0;
		for (String line : LINES) {
			if (checkDoubleRepeat(line) && rule2(line))
				result++;
		}
		return result;
	}
	
	private boolean checkDoubleRepeat(String line) {
		
		for (int i=0; i<line.length() - 2; i++) {
			String piece = line.substring(i, i+2);
			String remaining = line.substring(i+2);
			if (remaining.contains(piece))
				return true;
		}
		return false;
	}
	
	private boolean rule2(String line) {
		char[] chars = line.toCharArray();
		for (int i=0; i<chars.length - 2; i++) {
			if (chars[i] == chars[i+2])
				return true;
			
		}
		return false;
	}
}
