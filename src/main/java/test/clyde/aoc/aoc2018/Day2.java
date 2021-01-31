package test.clyde.aoc.aoc2018;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day2 {
	private static List<String> LINES = Utils.readFile("2018/Day2.txt");
	private static List<String> TEST = Utils.readFile("2018/Day2a.txt");
	private static List<String> TEST2 = Utils.readFile("2018/Day2b.txt");

	private long problemA(List<String> lines) {
		int two =0;
		int three = 0;
		for (String line : lines) {
			Map<Character, Integer> counts = getLetterCounts(line);
			two += (counts.values().stream().filter(i -> i==2).count() > 0) ? 1 : 0;
			three += (counts.values().stream().filter(i -> i==3).count() > 0) ? 1 : 0;
		}
		return two*three;
	}

	private String problemB(List<String> lines) {
		
		for (int i=0; i<lines.size(); i++) {
			for (int j=i+1; j<lines.size(); j++) {
				if (compareLine(lines.get(i), lines.get(j))) {
					String result = "";
					for (int k=0; k<lines.get(i).length(); k++) {
						if (lines.get(i).charAt(k) == lines.get(j).charAt(k))
							result+=lines.get(i).charAt(k);
					}
					return result;
				}
			}
		}
		return null;
	}
	
	private Map<Character, Integer> getLetterCounts(String line) {
		Map<Character, Integer> result = new HashMap<>();
		for (char c : line.toCharArray()) {
			result.putIfAbsent(c,0);
			result.put(c, result.get(c)+1);
		}
		return result;
	}
	
	private boolean compareLine(String one, String two) {
		int count = 0;
		for (int i=0; i<one.length(); i++) {
			if (one.charAt(i) != two.charAt(i)) {
				count++;
				if (count > 1)
					return false;
			}
		}
		if (count==1)
			return true;
		return false;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day2().problemA(TEST));
		System.out.println("A: " + new Day2().problemA(LINES));
		System.out.println("TestB: " + new Day2().problemB(TEST2));
		System.out.println("B: " + new Day2().problemB(LINES));
	}

}
