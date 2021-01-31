package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day7 {

	private static List<String> LINES = Utils.readFile("2017/Day7.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day7a.txt");

	private class Program {
		Program parent;
		String name;
		int weight;
		int goodWeight;
		int oddWeight;
		List<Program> children = new ArrayList<>();
		
		public Program(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return String.format("%s", name);
		}
	}
	
	private int problemA(List<String> lines) {
		Map<String, Program> programs = new HashMap<>();
		Pattern top_pat = Pattern.compile("([a-z]*) \\((\\d+)\\)");
		Pattern children = Pattern.compile("([a-z]+)");
		for (String line : lines) {
			Matcher m = top_pat.matcher(line);
			Program parent = null;
			if (m.find()) {
				String name = m.group(1);
				int weight = Integer.valueOf(m.group(2));
				if (!programs.containsKey(name)) {
					programs.put(name, new Program(name));
				}
				parent = programs.get(name);
				parent.weight = weight;
			}
			if (line.contains("->")) {
				m = children.matcher(line.substring(line.indexOf("->")));
				while (m.find()) {
					String child = m.group(1);
					if (!programs.containsKey(child)) {
						programs.put(child, new Program(child));
					}
					Program program = programs.get(child);
					program.parent = parent;
				}
			}
		}
		
		String rootName = programs.values().stream().filter(p -> p.parent==null).map(p -> p.name).findFirst().get();
		System.out.println("A: " + rootName);
		
		programs.values().stream().filter(p -> p.parent!= null).forEach(p -> p.parent.children.add(p));
		
		Program program = programs.get(rootName);
		Program odd = getOddOne(program);
		Program previousOdd = odd;
		while (true) {
			previousOdd = odd;
			odd = getOddOne(previousOdd);
			if (odd == null)
				break;
		}
		return previousOdd.weight-(previousOdd.oddWeight-previousOdd.goodWeight);
	}
	
	private Program getOddOne(Program start) {
		Map<Integer, Integer> sums = new HashMap<>();
		Map<Integer, Program> sumPrograms = new HashMap<>();
		for (Program child : start.children) {
			int weight = getSumWeights(child);
			if (!sums.containsKey(weight))
				sums.put(weight, 0);
			sums.put(weight, sums.get(weight)+1);
			sumPrograms.put(weight, child);
		}
		if (sums.size() == 1)
			return null;
		
		int oddWeight = sums.entrySet().stream().filter(e -> e.getValue()==1).map(e -> e.getKey()).mapToInt(i -> i).findFirst().getAsInt();
		int goodWeight = sums.entrySet().stream().filter(e -> e.getKey()!=oddWeight).map(e -> e.getKey()).mapToInt(i -> i).findFirst().getAsInt();
		Program oddProgram = sumPrograms.get(oddWeight);
		oddProgram.goodWeight = goodWeight;
		oddProgram.oddWeight = oddWeight;
		return oddProgram;
	}
	
	private int getSumWeights(Program program) {
		int result = program.weight;
		for (Program p : program.children) {
			result += getSumWeights(p);
		}
		return result;
	}

	private long problemB(List<String> lines) {
		int result = 0;
		
		return result;
	}

	public static void main(String[] args) {
		System.out.println("Test: " + new Day7().problemA(TEST_LINES));
		System.out.println("B: " + new Day7().problemA(LINES));
//		System.out.println("TestB: " + new Day7().problemB(TEST_LINES));
//		System.out.println("B: " + new Day7().problemB(LINES));
	}

}
