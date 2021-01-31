package test.clyde.aoc.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day19 {

	private static List<String> LINES = Utils.readFile("2020/Day19.txt");
	private static List<String> TEST_LINES = Utils.readFile("2020/Day19a.txt");
	private static List<String> TEST_LINESB = Utils.readFile("2020/Day19b.txt");

	private class Rule {
		String line;

		public List<String> getPossibles(Map<Integer, Rule> rules) {
			List<String> result = new ArrayList<>();
			if (line.contains("\"")) {
				result.add(line.replaceAll("\"", ""));
			} else if (line.contains("|")) {
				String part1 = line.substring(0, line.indexOf('|')).trim();
				result.addAll(getPartPossibles(rules, part1));
				String part2 = line.substring(line.indexOf('|') + 2).trim();
				result.addAll(getPartPossibles(rules, part2));
			} else {
				result.addAll(getPartPossibles(rules, line));
			}
			return result;
		}

		private List<String> getPartPossibles(Map<Integer, Rule> rules, String part) {
			List<String> result = new ArrayList<>();
			Pattern p = Pattern.compile("(\\d+)");
			Matcher m = p.matcher(part);
			List<List<String>> parts = new ArrayList<>();
			while (m.find()) {
				int i = Integer.valueOf(m.group(1));
				List<String> thisResults = rules.get(i).getPossibles(rules);
				parts.add(thisResults);
			}

			if (parts.size() == 1)
				return parts.get(0);

			if (parts.size() > 3) {
				throw new RuntimeException("failed");
			}

			if (parts.size() == 2) {

				for (String part1 : parts.get(0)) {
					for (String part2 : parts.get(1)) {
						result.add(part1 + part2);
					}
				}
			} else {
				for (String part1 : parts.get(0)) {
					for (String part2 : parts.get(1)) {
						for (String part3 : parts.get(2)) {
							result.add(part1 + part2 + part3);
						}
					}
				}
			}

			return result;
		}
	}

	public static void main(String[] args) {
//		System.out.println("TestA: " + new Day19().problemB(TEST_LINES, false));
		System.out.println("A: " + new Day19().problemA(LINES));
//		System.out.println("TestB: " + new Day19().problemB(TEST_LINESB, true));
		System.out.println("B: " + new Day19().problemB(LINES));
	}

	private long problemA(List<String> lines) {
		List<String> messages = new ArrayList<>();
		Map<Integer, Rule> rules = new HashMap<>();
		boolean isRules = true;
		for (String line : lines) {
			if (line.length() == 0) {
				isRules = false;
				continue;
			}
			if (isRules) {
				int index = line.indexOf(':');
				int ruleNo = Integer.valueOf(line.substring(0, index));
				String ruleLine = line.substring(index + 2);
				Rule rule = new Rule();
				rule.line = ruleLine;
				rules.put(ruleNo, rule);
			} else {
				messages.add(line);
			}
		}

		long result = 0;
		List<String> poss = rules.get(0).getPossibles(rules);
		for (String mess : messages) {
			if (poss.contains(mess)) {
				result++;
			}
		}

		return result;
	}

	private long problemB(List<String> lines) {
		List<String> messages = new ArrayList<>();
		Map<Integer, Rule> rules = new HashMap<>();
		boolean isRules = true;
		for (String line : lines) {
			if (line.length() == 0) {
				isRules = false;
				continue;
			}
			if (isRules) {
				int index = line.indexOf(':');
				int ruleNo = Integer.valueOf(line.substring(0, index));
				String ruleLine = line.substring(index + 2);
				Rule rule = new Rule();
				rule.line = ruleLine;
				rules.put(ruleNo, rule);
			} else {
				messages.add(line);
			}
		}

		// rule 0: 8 11

		long result = 0;
		List<String> poss42 = rules.get(42).getPossibles(rules);
		List<String> poss31 = rules.get(31).getPossibles(rules);

		for (String mess : messages) {
			if (mess.length() % poss42.get(0).length() != 0)
				continue;
			List<String> pieces = splitSize(mess, poss42.get(0).length());

			boolean allGood = true;
			boolean test42 = true;
			boolean test31 = false;

			if (!poss42.contains(pieces.get(0)) || !poss42.contains(pieces.get(1))
					|| !poss31.contains(pieces.get(pieces.size() - 1))) {
				allGood = false;
			} else {
				int count42 = 0;
				int count31 = 0;
				for (int i = 2; i < pieces.size() - 1; i++) {
					String piece = pieces.get(i);
					
					if (test42) {
						if (!poss42.contains(piece)) {
							test31 = true;
							test42 = false;
						} else {
							count42++;
						}
					}
					if (test31) {
						
						if (!poss31.contains(piece)) {
							allGood = false;
							break;
						} else {
							count31++;
						}
					}
				}
				if (count31 > count42) {
					allGood = false;
				}
			}

			if (allGood) {
				result++;
			}
		}

		return result;
	}

	private List<String> splitSize(String s, int size) {
		List<String> result = new ArrayList<>();
		String piece = "";
		for (int i = 0; i < s.length(); i++) {
			piece += s.charAt(i);
			if (i % size == size - 1) {
				result.add(piece);
				piece = "";
			}
		}
		return result;
	}
}
