package test.clyde.aoc.aoc2018;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day1 {
	private static List<String> LINES = Utils.readFile("2018/Day1.txt");

	private long problemA(List<String> lines) {
		long result = 0;
		for (String line : lines) {
			result += Long.valueOf(line);
		}
		return result;
	}

	private long problemB(List<String> lines) {
		Map<Long, Integer> freqs = new HashMap<>();
		long result = 0;
		while (true) {
		for (String line : lines) {
			result += Long.valueOf(line);
			if (freqs.containsKey(result))
				return result;
			freqs.putIfAbsent(result, 0);
		}
	}
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day1().problemA(LINES));
		System.out.println("B: " + new Day1().problemB(LINES));
	}

}
