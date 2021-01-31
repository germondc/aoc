package test.clyde.aoc.aoc2020;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day1 {
	private static List<String> LINES = Utils.readFile("2020/Day1.txt");

	public static void main(String[] args) throws IOException {
		System.out.println("A: " + new Day1().problemA());
		System.out.println("B: " + new Day1().problemB());
	}

	public long problemA() {
		List<Integer> values = LINES.stream().map(s -> Integer.valueOf(s)).collect(Collectors.toList());
		for (int i = 0; i < values.size(); i++) {
			int value1 = values.get(i);
			for (int j = 0; j < values.size(); j++) {
				int value2 = values.get(j);
				if (value1 + value2 == 2020)
					return value1 * value2;
			}
		}
		return 0;
	}

	public long problemB() {
		List<Integer> values = LINES.stream().map(s -> Integer.valueOf(s)).collect(Collectors.toList());
		for (int i = 0; i < values.size(); i++) {
			int value1 = values.get(i);
			for (int j = 0; j < values.size(); j++) {
				int value2 = values.get(j);
				for (int k = 0; k < values.size(); k++) {
					int value3 = values.get(k);
					if (value1 + value2 + value3 == 2020)
						return value1 * value2 * value3;
				}
			}
		}
		return 0;
	}
}
