package test.clyde.aoc.aoc2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day4 {
	private static List<String> LINES = Utils.readFile("2016/Day4.txt");

	Pattern PATTERN = Pattern.compile("(.*)-([0-9]*)\\[([a-z]*)\\]");

	private int problemA() {

		int count = 0;
		for (String line : LINES) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}
			String room = m.group(1);
			int sectorId = Integer.valueOf(m.group(2));
			String checksum = m.group(3);

			int[] counts = new int[26];

			for (char c : room.replace("-", "").toCharArray()) {
				counts[c - 'a']++;
			}

			Map<Character, Integer> map = new LinkedHashMap<>();
			for (int i=0; i<26; i++) {
				if (counts[i] != 0) {
					map.put((char) (i + 'a'), counts[i]);
				}
			}
			String topFive = map.entrySet().stream()
					.sorted(Map.Entry.<Character, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
					.map(e -> String.valueOf(e.getKey())).limit(5).collect(Collectors.joining());
			if (checksum.equals(topFive)) {
				count += sectorId;
				String decrypted = decrypt(room, sectorId);
				if (decrypted.startsWith("north"))
					System.out.println(decrypted + " - " + sectorId);
			}

		}
		return count;
	}

	private boolean isValid(List<Integer> items) {
		Collections.sort(items);
		return (items.get(0) + items.get(1)) > items.get(2);
	}

	private boolean isValid(int[] items) {
		List<Integer> li = new ArrayList<>();
		for (int i : items) {
			li.add(i);
		}
		return isValid(li);
	}

	private boolean isValide(int value1, int value2, int value3) {
		List<Integer> items = new ArrayList<>();
		items.add(value1);
		items.add(value2);
		items.add(value3);
		return isValid(items);
	}

	private String decrypt(String room, int sectorId) {
		char[] ca = new char[room.length()];
		int index = 0;
		for (char c : room.toCharArray()) {
			ca[index] = rotate(c, sectorId);
			index++;
		}
		return (new String(ca));
	}

	private int problemB() {
		int count = 0;

		String s = "qzmt-zixmtkozy-ivhz";

		char[] ca = new char[s.length()];
		int index = 0;
		for (char c : s.toCharArray()) {
			ca[index] = rotate(c, 343);
			index++;
		}
		System.out.println(new String(ca));

		return count;
	}

	private char rotate(char current, int amount) {
		if (current == '-')
			return ' ';
		int shift = amount % 26;
		char result = (char) (current + shift);
		if (result > 'z')
			result -= 26;
		return result;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day4().problemA());
		System.out.println("B: " + new Day4().problemB() + " see above");
	}
}
