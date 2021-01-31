package test.clyde.aoc.aoc2016;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day9 {

	private int problemA() {
		int count = 0;

		// List<Character> chars = Utils.readChars("2016/Day9.txt");

		List<String> lines = Utils.readFile("2016/Day9.txt");
		String line = lines.get(0);
		Pattern pattern = Pattern.compile("\\(([0-9]+)x([0-9]+)\\)");
		Matcher m = pattern.matcher(line);
		int index = 0;
		while (m.find(index)) {
			int length = Integer.valueOf(m.group(1));
			int repeat = Integer.valueOf(m.group(2));
			
			count += m.start() - index;
			count += length * repeat;
			index = m.end() + length;
		}
		count += line.length() - index;

		return count;
	}

	private long problemB() {
		List<String> lines = Utils.readFile("2016/Day9.txt");
		String line = lines.get(0);
		return getLengthForSub(line);
	}
	
	private long getLengthForSub(String theSub) {
		Pattern pattern = Pattern.compile("\\(([0-9]+)x([0-9]+)\\)");
		Matcher m = pattern.matcher(theSub);
		int index = 0;
		long count = 0;
		while (m.find(index)) {
			int length = Integer.valueOf(m.group(1));
			int repeat = Integer.valueOf(m.group(2));
			
			String sub = theSub.substring(m.end(), m.end() + length);
			
			count += m.start() - index;
			count += repeat * getLengthForSub(sub);
			index = m.end() + length;
		}
		count += theSub.length() - index;
		return count;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {

		System.out.println("A: " + new Day9().problemA());
		System.out.println("B: " + new Day9().problemB());
	}
}
