package test.clyde.aoc.utils;

public class StringUtils {
	public static int charCount(String s, char c) {
		int result = 0;
		for (int i=0; i<s.length(); i++) {
			if (s.charAt(i)==c)
				result++;
		}
		return result;
	}
}
