package test.clyde.aoc.aoc2015;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day24 {
	
	private static List<String> LINES = Utils.readFile("2015/Day24.txt");
	
	List<Integer> results;
	long bestQE = Long.MAX_VALUE;
	int result = Integer.MAX_VALUE;
	
	private void sumRecursive(List<Integer> numbers, int target, List<Integer> partial) {
		int s = partial.stream().mapToInt(i -> i).sum();
		if (s == target) {
			if (partial.size() <= result) {
				result = partial.size();
				try {
					long qe= getQE(partial);
					if (qe < bestQE)
						bestQE = qe;
				} catch (Exception e) {}
			}
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
	
	private long getQE(List<Integer> items) {
		return items.stream().mapToLong(i -> i).reduce(1, Math::multiplyExact);
	}
	
	private long test() {
		List<Integer> items = LINES.stream().mapToInt(s -> Integer.valueOf(s)).boxed().collect(Collectors.toList());
		int target = items.stream().mapToInt(i -> i).sum() / 3;
		sum(items, target);
		
		//return result; //results.stream().mapToInt(l -> l.size()).min().getAsInt();
		return bestQE;
	}
	
	private long testB() {
		List<Integer> items = LINES.stream().mapToInt(s -> Integer.valueOf(s)).boxed().collect(Collectors.toList());
		int target = items.stream().mapToInt(i -> i).sum() / 4;
		sum(items, target);
		
		//return result; //results.stream().mapToInt(l -> l.size()).min().getAsInt();
		return bestQE;
	}
	
	public static void main(String[] args) {
		//System.out.println("A: " + new Day24().test());
		System.out.println("B: " + new Day24().testB());
	}
}
