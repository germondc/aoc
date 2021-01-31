package test.clyde.aoc.aoc2020;

import java.util.List;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day9 {

	private static List<String> LINES = Utils.readFile("2020/Day9.txt");

	public static void main(String[] args) {
		System.out.println("A: " + new Day9().problemA());
		System.out.println("B: " + new Day9().problemB());
	}

	private long problemA() {
		List<Long> number = LINES.stream().map(l -> Long.valueOf(l)).collect(Collectors.toList());
		int index = 26;
		while(true) {
			long value = number.get(index);
			
			boolean match = false;
			main:
			for (int i=index-26; i<index; i++) {
				for (int j=index-25; j<index; j++) {
					if (i==j)
						continue;
					if (number.get(i) + number.get(j) == value) {
						match = true;
						break main;
					}
				}
			}
			if (!match)
				return number.get(index);
			
			index++;
		}
	}

	private long problemB() {
		long value = problemA();
		List<Long> number = LINES.stream().map(l -> Long.valueOf(l)).collect(Collectors.toList());
		
		long sum=0;
		for (int i=0; i<number.size(); i++) {
			sum = number.get(i);
			for (int j=i+1; j<number.size(); j++) {
				sum += number.get(j);	
				if (sum==value)
					return number.get(i) + number.get(j);
			}
		}
		
		return 0;
	}
}
