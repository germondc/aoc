package test.clyde.aoc.aoc2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day16 {
	private static List<String> LINES = Utils.readFile("2015/Day16.txt");

	Pattern PATTERN = Pattern.compile("Sue ([0-9]*): (.*)$");

	private String[] fields = { "children", "cats", "samoyeds", "pomeranians", "akitas", "vizslas", "goldfish", "trees",
			"cars", "perfumes" };
	private int[] values = { 3, 7, 2, 3, 0, 0, 5, 3, 2, 1 };

	private int test() {
		for (String line : LINES) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}

			int sue = Integer.valueOf(m.group(1));
			String data = m.group(2);

			Map<String, Integer> map = getData(data);
			boolean match = true;
			for (int i = 0; i < fields.length; i++) {
				Integer lookup = map.get(fields[i]);
				if (lookup == null)
					continue;
				if (lookup != values[i]) {
					match = false;
					break;
				}
			}
			if (match)
				return sue;
		}

		return 0;
	}

	private Map<String, Integer> getData(String data) {
		Map<String, Integer> result = new HashMap<>();
		String[] split = data.split(", ");
		for (String s : split) {
			int index = s.indexOf(':');
			String name = s.substring(0, index);
			int value = Integer.valueOf(s.substring(index + 1).trim());
			result.put(name, value);
		}
		return result;
	}

	private int testC() {
		Map<Integer, Map<String, Integer>> sues = new HashMap<>();
		for (String line : LINES) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}

			int sue = Integer.valueOf(m.group(1));
			String data = m.group(2);

			Map<String, Integer> map = getData(data);
			sues.put(sue, map);
		}
		
		int[] defs = new int[] { 0,2,4,5,8,9};
		
		for (Map.Entry<Integer, Map<String, Integer>> entry : sues.entrySet()) {
			boolean good = true;
			for (int i = 0; i< defs.length; i++) {
				int index = defs[i];
				Integer value = entry.getValue().get(fields[index]);
				if (value == null)
					continue;
				
				if (value != values[index]) {
					good = false;
					break;
				}
			}
			if (good)
				System.out.println(entry.getKey());
		}
		return 0;
	}

	private int testB() {
		for (String line : LINES) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}

			int sue = Integer.valueOf(m.group(1));
			String data = m.group(2);

			Map<String, Integer> map = getData(data);
			boolean match = true;
			for (int i = 0; i < fields.length; i++) {
				Integer lookup = map.get(fields[i]);
				if (lookup == null)
					continue;
				
				/**
				 * children: 3
cats: 7
samoyeds: 2
pomeranians: 3
akitas: 0
vizslas: 0
goldfish: 5
trees: 3
cars: 2
perfumes: 1
				 * 
				 * 
				 * 
				 * cats trees >
				 * pomeranians goldfish <
				 */

				if (fields[i].equals("cats")) {
					if (lookup <= values[i]) {
						match = false;
						break;
					}
				} else if (fields[i].equals("trees")) {
					if (lookup <= values[i]) {
						match = false;
						break;
					}
				} else if (fields[i].equals("pomeranians")) {
					if  (lookup >= values[i]) {
						match = false;
						break;
					}
				} else if (fields[i].equals("goldfish")) {
					if (lookup >= values[i]) {
						match = false;
						break;
					}
				} else if (lookup != values[i]) {
					match = false;
					break;
				}
			}
			if (match)
				return sue;
		}

		return 0;
	}

	public static void main(String[] argv) {
		System.out.println("A: " + new Day16().test());
		System.out.println("B: " + new Day16().testB());
//		System.out.println("B: " + new Day16().testC());
	}
}
