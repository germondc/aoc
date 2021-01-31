package test.clyde.aoc.aoc2016;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day20 {

	private static List<String> LINES = Utils.readFile("2016/Day20.txt");
	
	private class Range {
		long min;
		long max;
		public Range(long min, long max) {
			super();
			this.min = min;
			this.max = max;
		}
		
		public boolean isInRange(long number) {
			if (number>=min && number<=max)
				return true;
			return false;
		}
	}
	
	private long problemA() {
		List<Range> ranges = getInput();
		return getUnblockedNext(ranges, 0);
		
	}
	
	private long problemB() {
		List<Range> ranges = getInput();
		long lowest = getUnblockedNext(ranges, 0);
		long available = 0;
		
		long next = getBlockedNext(ranges, lowest);
		while (next != Long.MAX_VALUE) {
			available += next-lowest;
			lowest = getUnblockedNext(ranges, next);
			next = getBlockedNext(ranges, lowest);
		}
		return available;
	}
	
	private long getUnblockedNext(List<Range> ranges, long number) {
		long lowest = number;
		boolean allDone = false;
		while (!allDone) {
			allDone = true;
			for (Range r : ranges) {
				if (r.isInRange(lowest)) {
					lowest = r.max + 1;
					allDone = false;
					break;
				}
			}
		}
		return lowest;
	}
	
	private long getBlockedNext(List<Range> ranges, long number) {
		long lowest = Long.MAX_VALUE;
		for (Range r : ranges) {
			if (r.min > number && r.min < lowest) {
				lowest = r.min;
			}
		}
		return lowest;
	}
	
	private List<Range> getInput() {
		List<Range> result = new ArrayList<>();
		Pattern range_pat = Pattern.compile("([0-9]+)-([0-9]+)");
		for (String line: LINES) {
			Matcher m = range_pat.matcher(line);
			while (m.find()) {
				result.add(new Range(Long.valueOf(m.group(1)), Long.valueOf(m.group(2))));
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day20().problemA());
		System.out.println("B: " + new Day20().problemB());
		
	}
}
