package test.clyde.aoc.aoc2015;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day17 {
	private static List<String> LINES = Utils.readFile("2015/Day17.txt");
	
	List<List<Integer>> results = new ArrayList<>();
	private void test() {
		List<Integer> items = LINES.stream().map(s -> Integer.valueOf(s)).sorted().collect(Collectors.toList());
		sum(items, 150);
		
		System.out.println("A: " + results.size());

		int minCon = results.stream().map(l -> l.size()).mapToInt(i -> i).min().getAsInt();
		long count = results.stream().filter(l -> l.size()==minCon).count();
		System.out.println("B: " + count);
	}

	private void sumRecursive(List<Integer> numbers, int target, List<Integer> partial) {
		int s = partial.stream().mapToInt(i -> i).sum();
		if (s == target) {
			results.add(partial);
			return;
		} else if (s > target) {
			return;
		}
			
		for (int i = 0; i < numbers.size(); i++) {
			// move from numbers into new partial
			List<Integer> remaining = new ArrayList<Integer>(numbers.subList(i+1, numbers.size()));
		    List<Integer> newPartial = new ArrayList<Integer>(partial);
			newPartial.add(numbers.get(i));
			sumRecursive(remaining, target, newPartial);
		}
	}

	private void sum(List<Integer> numbers, int target) {
		sumRecursive(numbers, target, new ArrayList<Integer>());
	}

	public static void main(String[] argv) {
		new Day17().test();
	}
}
