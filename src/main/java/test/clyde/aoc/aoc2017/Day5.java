package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day5 {

	private static List<String> LINES = Utils.readFile("2017/Day5.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day5a.txt");

	private long problemA(List<String> lines) {
		int result = 0;
		List<Integer> jumps = lines.stream().map(s -> Integer.valueOf(s)).collect(Collectors.toList());

		for (int i = 0; i < jumps.size(); i++) {
			int jump = jumps.get(i);
			jumps.set(i, jump+1);
			i += jump-1;
			result++;
		}

		return result;
	}

	private long problemB(List<String> lines) {
		int result = 0;
		List<Integer> jumps = lines.stream().map(s -> Integer.valueOf(s)).collect(Collectors.toList());

		for (int i = 0; i < jumps.size(); i++) {
			int jump = jumps.get(i);
			if (jump >=3)
				jumps.set(i, jump-1);
			else
				jumps.set(i, jump+1);
			i += jump-1;
			result++;
		}

		return result;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day5().problemA(TEST_LINES));
		System.out.println("A: " + new Day5().problemA(LINES));
		System.out.println("TestB: " + new Day5().problemB(TEST_LINES));
		System.out.println("B: " + new Day5().problemB(LINES));
	}

}
