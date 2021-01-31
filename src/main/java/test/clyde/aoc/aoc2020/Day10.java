package test.clyde.aoc.aoc2020;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import test.clyde.aoc.utils.Utils;

public class Day10 {

	private static List<String> LINES = Utils.readFile("2020/Day10.txt");
	private static List<String> LINESA = Utils.readFile("2020/Day10a.txt");
	private static List<String> LINESB = Utils.readFile("2020/Day10b.txt");
	
	public static void main(String[] args) {
		System.out.println("A: " + new Day10().problemA());
		System.out.println("B: " + new Day10().problemB());
	}

	private long problemA() {
		List<Integer> numbers = LINES.stream().map(l -> Integer.valueOf(l)).sorted().collect(Collectors.toList());
		Map<Integer, Integer> diffs = new HashMap<>();
		
		int current = 0;
		for (int number : numbers) {
			int diff = number - current;
			
			if (!diffs.containsKey(diff))
				diffs.put(diff, 0);
			diffs.put(diff, diffs.get(diff)+1);
			
			current = number;
			
		}
			
		return diffs.get(1) * (diffs.get(3) + 1);
	}

	private String problemB() {
		List<Integer> numbers = LINES.stream().map(l -> Integer.valueOf(l)).sorted().collect(Collectors.toList());
		numbers.add(0,0);
		Map<Integer, Integer> items = new HashMap<>();
		IntStream.range(0, 5).forEach(i -> items.put(i, 0));
		int count = 0;
		
		for (int i=0; i<numbers.size()-1; i++) {
			int diff;
			diff = numbers.get(i+1) - numbers.get(i);
			if (diff==1)
				count++;
			else {
				items.put(count, items.get(count)+1);
				count=0;
			}
		}
		
		BigInteger bi = BigInteger.valueOf(2).pow(items.get(2)).multiply(BigInteger.valueOf(4).pow(items.get(3))).multiply(BigInteger.valueOf(7).pow(items.get(4)));
		return bi.toString();
	}
}
 