package test.clyde.aoc.aoc2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day6 {

	private static List<String> LINES = Utils.readFile("2020/Day6.txt");

	public static void main(String[] args) {
		System.out.println("A: " + new Day6().problemA());
		System.out.println("B: " + new Day6().problemB());
	}

	private int problemA() {
		int group = 0;

		Map<Integer, Map<Character, Integer>> items = new HashMap<>();
		items.put(0, new HashMap<>());
		for (String line : LINES) {
			if (line.trim().length() == 0) {
				group++;
				items.put(group, new HashMap<>());
				continue;
			}
			Map<Character, Integer> chars = items.get(group);
			for (char c : line.toCharArray()) {
				if (!chars.containsKey(c))
					chars.put(c, 0);
				chars.put(c, chars.get(c) + 1);
			}
		}

		int sum = 0;
		for (int i = 0; i < items.size(); i++) {
			sum += items.get(i).size();
		}

		return sum;
	}

	private int problemB() {
		boolean[] b = gen();

		int sum = 0;
		for (String line : LINES) {
			if (line.trim().length() == 0) {
				sum += count(b);
				b = gen();
				continue;
			}
			
			for (int i=0; i<26; i++) {
				b[i] &= line.indexOf(i+97) > -1;
			}
		}
		sum += count(b);
		return sum;
	}

	private int count(boolean[] b) {
		int count = 0;
		for (int i = 0; i < 26; i++) {
			if (b[i])
				count++;
		}
		return count;
	}

	private boolean[] gen() {
		boolean[] b = new boolean[26];
		for (int i = 0; i < 26; i++)
			b[i] = true;

		return b;
	}
}
