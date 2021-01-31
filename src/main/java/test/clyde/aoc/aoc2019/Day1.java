package test.clyde.aoc.aoc2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day1 {
	private static List<String> LINES = Utils.readFile("2019/Day1.txt");

	private long problemA(List<String> lines) {
		long result = 0;
		for (String line : lines) {
			int value = Integer.valueOf(line);
			value /= 3;
			value -=2;
			result +=value;
		}
		return result;
	}

	private long problemB(List<String> lines) {
		long result = 0;
		//lines = Arrays.asList(new String[] { "100756"});
		for (String line : lines) {
			int value = Integer.valueOf(line);
			int fuel = getFuel(value);
			while (fuel >0) {
				result += fuel;
				fuel = getFuel(fuel);
			}
		}
		return result;
	}
	
	private int getFuel(int value) {
		int result = value / 3;
		result -=2;
		return result;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day1().problemA(LINES));
		System.out.println("B: " + new Day1().problemB(LINES));
	}

}
