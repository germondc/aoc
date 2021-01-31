package test.clyde.aoc.aoc2020;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day18 {

	private static List<String> LINES = Utils.readFile("2020/Day18.txt");
	private static List<String> TEST_LINES = Utils.readFile("2020/Day18a.txt");

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day18().solve(TEST_LINES, false));
		System.out.println("A: " + new Day18().solve(LINES, false));
		System.out.println("TestB: " + new Day18().solve(TEST_LINES, true));
		System.out.println("B: " + new Day18().solve(LINES, true));
	}
	
	private long solve(List<String> lines, boolean order) {
		List<Long> numbers = new ArrayList<>();
		for(String line : lines) {
			String lineProcess = line;
			while (lineProcess.contains("(")) {
				Pattern pat = Pattern.compile("\\(([^\\(^\\)]*)\\)");
				Matcher m = pat.matcher(lineProcess);
				while (m.find()) {
					String bracket = m.group(1);
					long bracketValue = calcInner(bracket, order);
					lineProcess = lineProcess.substring(0, m.start()) + bracketValue + lineProcess.substring(m.end());
					break;
				}
			}	
			numbers.add(calcInner(lineProcess, order));
		}
		return numbers.stream().mapToLong(l -> l).sum();
	}
	
	private long calcInner(String inner, boolean order) {
		if (order)
			return calcInnerB(inner);
		String processInner = inner;
		while (processInner.contains("+") || processInner.contains("*")) {
			Pattern pat = Pattern.compile("([0-9]+) ([\\+\\*]) ([0-9]+)");
			Matcher m = pat.matcher(processInner);
			while (m.find()) {
				long value1 = Long.valueOf(m.group(1));
				long value2 = Long.valueOf(m.group(3));
				String operator = m.group(2);
				long result;
				if (operator.equals("+"))
					result = value1 + value2;
				else
					result = value1 * value2;
				processInner = processInner.substring(0, m.start()) + result + processInner.substring(m.end());
				break;
			}
		}
		return Long.valueOf(processInner);
	}
	
	private long calcInnerB(String inner) {
		String processInner = inner;
		while (processInner.contains("+")) {
			Pattern pat = Pattern.compile("([0-9]+) (\\+) ([0-9]+)");
			Matcher m = pat.matcher(processInner);
			while (m.find()) {
				long value1 = Long.valueOf(m.group(1));
				long value2 = Long.valueOf(m.group(3));
				long result = value1 + value2;
				processInner = processInner.substring(0, m.start()) + result + processInner.substring(m.end());
				break;
			}
		}
		return calcInner(processInner, false);
	}
}
