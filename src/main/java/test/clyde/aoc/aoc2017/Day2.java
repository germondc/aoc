package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day2 {

	private static List<String> LINES = Utils.readFile("2017/Day2.txt");
	
	private long problemA(List<String> lines) {
		Pattern pat = Pattern.compile("(\\d+)");
		List<List<Integer>> numbers = new ArrayList<>();
		for (String line : lines) {
			List<Integer> row = new ArrayList<>();
			numbers.add(row);
			Matcher m = pat.matcher(line);
			while(m.find()) {
				int number = Integer.valueOf(m.group(1));
				row.add(number);
			}
		}
		
		int sum = 0;
		for (List<Integer> li : numbers) {
			int min = li.stream().mapToInt(i -> i).min().getAsInt();
			int max = li.stream().mapToInt(i -> i).max().getAsInt();
			sum += (max - min);
		}
		return sum;
	}
	
	private long problemB(List<String> lines) {
		Pattern pat = Pattern.compile("(\\d+)");
		List<List<Integer>> numbers = new ArrayList<>();
		for (String line : lines) {
			List<Integer> row = new ArrayList<>();
			numbers.add(row);
			Matcher m = pat.matcher(line);
			while(m.find()) {
				int number = Integer.valueOf(m.group(1));
				row.add(number);
			}
		}
		
		int sum = 0;
		for (List<Integer> li : numbers) {
			main:
			for (int i=0; i<li.size(); i++) {
				for (int j=i+1; j<li.size(); j++) {
					if (li.get(i) % li.get(j) == 0) {
						sum += (li.get(i)/li.get(j));
						break main;
					}
					if (li.get(j) % li.get(i) == 0) {
						sum += (li.get(j)/li.get(i));
						break main;
					}
				}
			}
		}
		return sum;
	}
	
	public static void main(String[] args) {
		System.out.println("TestA: " + new Day2().problemA(LINES));
		System.out.println("TestB: " + new Day2().problemB(LINES));
	}

}
