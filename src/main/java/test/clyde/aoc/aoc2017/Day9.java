package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day9 {

	private static List<String> LINES = Utils.readFile("2017/Day9.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day9a.txt");

	private long problemA(List<String> lines) {
		for (String line : lines) {
			String cleaned = "";
			char[] ca = line.toCharArray();
			for (int i = 0; i < ca.length; i++) {
				if (ca[i] == '!')
					i += 1;
				else
					cleaned += ca[i];
			}
			
			ca = cleaned.toCharArray();
			cleaned = "";
			boolean stopped = false;
			for (int i = 0; i < ca.length; i++) {
				if (ca[i] == '<')
					stopped = true;
				if (!stopped) {
					cleaned += ca[i];
				}
				if (ca[i] == '>')
					stopped = false;
			}
			
			ca = cleaned.toCharArray();
			int result = 0;
			int counter = 0;
			for (int i = 0; i < ca.length; i++) {
				if (ca[i] == '{') {
					counter++;
					result += counter;
				} else if (ca[i] == '}') {
					counter--;
				}
			}
			
			return result;
		}
		return 0;
	}

	private long problemB(List<String> lines) {
		for (String line : lines) {
			String cleaned = "";
			char[] ca = line.toCharArray();
			for (int i = 0; i < ca.length; i++) {
				if (ca[i] == '!')
					i += 1;
				else
					cleaned += ca[i];
			}
			
			ca = cleaned.toCharArray();
			cleaned = "";
			int result = 0;
			boolean stopped = false;
			for (int i = 0; i < ca.length; i++) {
				if (ca[i] == '<') {
					if (!stopped)
						result--;
					stopped = true;					
				}
				if (stopped) {
					result++;
				} else {
					cleaned += ca[i];
				}
				if (ca[i] == '>') {
					stopped = false;
					result--;
				}
			}
			
			return result;
//			System.out.println(result);
		}
		return 0;
	}

	public static void main(String[] args) {
//		System.out.println("Test: " + new Day9().problemA(TEST_LINES));
		System.out.println("A: " + new Day9().problemA(LINES));
//		System.out.println("TestB: " + new Day9().problemB(TEST_LINES));
		System.out.println("B: " + new Day9().problemB(LINES));
	}

}
