package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day6 {

	private static List<String> LINES = Utils.readFile("2017/Day6.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day6a.txt");

	private long problemA(List<String> lines) {
		int result = 0;
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(lines.get(0));
		
		List<Integer> banks = new ArrayList<>();
		while (m.find()) {
			int number = Integer.valueOf(m.group(1));
			banks.add(number);
		}
		
		List<List<Integer>> seen = new ArrayList<>();
		List<Integer> newBanks = banks;
		seen.add(banks);
		while (true) {
			result++;
			newBanks = new ArrayList<>(newBanks);
			int indexLargest = indexOfLargest(newBanks);
			int largest = newBanks.get(indexLargest);
			newBanks.set(indexLargest, 0);
			while (largest > 0) {
				largest--;
				indexLargest++;
				if (indexLargest >= newBanks.size())
					indexLargest = 0;
				newBanks.set(indexLargest, newBanks.get(indexLargest)+1);
			}
			if (seen.contains(newBanks)) {
				break;
			}
			seen.add(newBanks);
		}
		
		return result;
	}
	
	private int indexOfLargest(List<Integer> numbers) {
		int index = 0;
		int largest = 0;
		for (int i=0; i<numbers.size(); i++) {
			if (numbers.get(i) > largest) {
				largest = numbers.get(i);
				index = i;
			}
		}
		return index;
	}

	private long problemB(List<String> lines) {
		int result = 0;
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(lines.get(0));
		
		List<Integer> banks = new ArrayList<>();
		while (m.find()) {
			int number = Integer.valueOf(m.group(1));
			banks.add(number);
		}
		
		List<List<Integer>> seen = new ArrayList<>();
		List<Integer> newBanks = banks;
		seen.add(banks);
		while (true) {
			result++;
			newBanks = new ArrayList<>(newBanks);
			int indexLargest = indexOfLargest(newBanks);
			int largest = newBanks.get(indexLargest);
			newBanks.set(indexLargest, 0);
			while (largest > 0) {
				largest--;
				indexLargest++;
				if (indexLargest >= newBanks.size())
					indexLargest = 0;
				newBanks.set(indexLargest, newBanks.get(indexLargest)+1);
			}
			if (seen.contains(newBanks)) {
				break;
			}
			seen.add(newBanks);
		}
		
		return result - seen.indexOf(newBanks);
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day6().problemA(TEST_LINES));
		System.out.println("A: " + new Day6().problemA(LINES));
		System.out.println("TestB: " + new Day6().problemB(TEST_LINES));
		System.out.println("B: " + new Day6().problemB(LINES));
	}

}
