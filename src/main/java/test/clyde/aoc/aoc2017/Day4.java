package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day4 {
	
	private static List<String> LINES = Utils.readFile("2017/Day4.txt");

	private long problemA(List<String> lines) {
		int result = 0;
		Pattern p = Pattern.compile("([a-z]+)");
		for (String line : lines) {
			List<String> words = new ArrayList<>();
			Matcher m = p.matcher(line);
			while (m.find()) {
				words.add(m.group(1));
			}
			boolean allGood = true;
			for (String word : words) {
				long count = words.stream().filter(w -> w.equals(word)).count();
				if (count > 1) {
					allGood = false;
					break;
				}
			}
			if (allGood)
				result++;
		}
		return result;
	}
	
	private long problemB(List<String> lines) {
		int result = 0;
		Pattern p = Pattern.compile("([a-z]+)");
		for (String line : lines) {
			List<String> words = new ArrayList<>();
			Matcher m = p.matcher(line);
			while (m.find()) {
				words.add(m.group(1));
			}
			boolean allGood = true;
			main:
			for (String word : words) {
				for (String word2 : words) {
					if (word == word2)
						continue;
					if (compareWords(word, word2)) {
						allGood = false;
						break main;
					}
				}
			}
			if (allGood)
				result++;
		}
		return result;
	}
	
	private boolean compareWords(String word1, String word2) {
		if (word1.length() != word2.length())
			return false;
		Map<Character, Integer> letters1Map = getLetters(word1);
		Map<Character, Integer> letters2Map = getLetters(word2);
		for (Map.Entry<Character, Integer> entry : letters1Map.entrySet()) {
			if (!letters2Map.containsKey(entry.getKey()))
				return false;
			if (entry.getValue() != letters2Map.get(entry.getKey()))
				return false;
		}
		return true;
	}
	
	private Map<Character, Integer> getLetters(String word) {
		Map<Character, Integer> letters = new HashMap<>();
		for (char c : word.toCharArray()) {
			if (!letters.containsKey(c))
				letters.put(c, 0);
			letters.put(c, letters.get(c)+1);
		}
		return letters;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day4().problemA(LINES));
		System.out.println("B: " + new Day4().problemB(LINES));
	}

}
