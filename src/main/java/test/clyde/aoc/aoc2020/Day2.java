package test.clyde.aoc.aoc2020;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day2 {
	public static void main(String[] args) throws IOException {
		System.out.println("A: " + new Day2().getRightPasswords());
		System.out.println("B: " + new Day2().getRightPasswordsB());
	}
	
	private boolean testPassword(int min, int max, char letter, String password) {
		long count = password.chars().filter(ch -> ch == letter).count();
		return count >= min && count <= max;
	}
	
	private boolean testPasswordB(int posA, int posB, char letter, String password) {
		int value1 = password.charAt(posA-1) == letter ? 1 : 0;
		int value2 = password.charAt(posB-1) == letter ? 1 : 0;
		return (value1 + value2) == 1; 
	}
	
	
	
	private int getRightPasswordsB() throws IOException {
		List<String> lines = Utils.readFile("2020/day2.txt");

		String pattern = "(.*)-(.*) (.): (.*)";
		Pattern r = Pattern.compile(pattern);
		
		int count = 0;
		for (String line : lines) {
			Matcher m = r.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}
			int pos1 = Integer.valueOf(m.group(1));
			int pos2 = Integer.valueOf(m.group(2));
			char letter = m.group(3).charAt(0);
			String password = m.group(4);
			
			if (testPasswordB(pos1, pos2, letter, password))
				count++;
		}
		return count;
	}
	
	private int getRightPasswords() throws IOException {
		List<String> lines = Utils.readFile("2020/day2.txt");
		
		String pattern = "(.*)-(.*) (.): (.*)";
		Pattern r = Pattern.compile(pattern);
		
		int count = 0;
		for (String line : lines) {
			Matcher m = r.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}
			int min = Integer.valueOf(m.group(1));
			int max = Integer.valueOf(m.group(2));
			char letter = m.group(3).charAt(0);
			String password = m.group(4);
			if (testPassword(min, max, letter, password))
				count++;
		}
		return count;
	}
}
