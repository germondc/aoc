package test.clyde.aoc.aoc2016;

import java.util.ArrayList;
import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day18 {

	private long testA() {
		List<Character> chars = Utils.readChars("2016/Day18a.txt");
		long result = chars.stream().filter(c -> c=='.').count();
		for (int i=1; i<10; i++) {
			chars = getNextRow(chars);
			result += chars.stream().filter(c -> c=='.').count();
		}
		return result;
	}
	
	private long problemA(int number) {
		List<Character> chars = Utils.readChars("2016/Day18.txt");
		List<List<Character>> all = new ArrayList<>();
		all.add(chars);
		for (int i=1; i<number; i++) {
			chars = getNextRow(chars);
			all.add(chars);
		}
		return all.stream().flatMap(l -> l.stream()).filter(c -> c=='.').count();
	}
	
	private List<Character> getNextRow(List<Character> currentRow) {
		List<Character> result = new ArrayList<>();
		List<Character> copy = new ArrayList<>(currentRow);
		copy.add(0, '.');
		copy.add('.');
		for (int i=1; i<copy.size()-1; i++) {
			if (copy.get(i-1)=='^' && copy.get(i)=='^' && copy.get(i+1)=='.') {
				result.add('^');
			} else if (copy.get(i-1)=='.' && copy.get(i)=='^' && copy.get(i+1)=='^') {
				result.add('^');
			} else if (copy.get(i-1)=='^' && copy.get(i)=='.' && copy.get(i+1)=='.') {
				result.add('^');
			} else if (copy.get(i-1)=='.' && copy.get(i)=='.' && copy.get(i+1)=='^') {
				result.add('^');
			} else {
				result.add('.');
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day18().testA());
		System.out.println("A: " + new Day18().problemA(40));
		System.out.println("B: " + new Day18().problemA(400000));
	}
}
