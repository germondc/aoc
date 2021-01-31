package test.clyde.aoc.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day16 {

	private static List<String> LINES = Utils.readFile("2020/Day16.txt");

	public static void main(String[] args) {
		System.out.println("A: " + new Day16().problemA(LINES));
		System.out.println("B: " + new Day16().problemB(LINES));
	}

	private long problemA(List<String> lines) {
		populateInput();

		List<Integer> nums = new ArrayList<>();
		for (List<Integer> ticket : tickets) {
			for (int field : ticket) {
				if (!isInAnyRange(field)) {
					nums.add(field);
				}

			}
		}

		return nums.stream().mapToInt(i -> i).sum();
	}

	private long problemB(List<String> lines) {
		populateInput();

		Iterator<List<Integer>> ticketsIt = tickets.iterator();
		while (ticketsIt.hasNext()) {
			List<Integer> ticket = ticketsIt.next();
			for (int field : ticket) {
				if (!isInAnyRange(field)) {
					ticketsIt.remove();
					break;
				}
			}
		}
		
		int fields = tickets.get(0).size();
		Map<Range, List<Integer>> rangePosibilities = new HashMap<>();
		for (Range r : ranges) {
			rangePosibilities.put(r, new ArrayList<>());
			for (int field = 0; field < fields; field++) {
				boolean isPossible = true;
				for (List<Integer> ticket : tickets) {
					int fieldValue = ticket.get(field);
					if (!r.isInRange(fieldValue)) {
						isPossible = false;
						break;
					}
				}
				if (isPossible) {
					rangePosibilities.get(r).add(field);
				}
			}
			
		}

		Map<Range, Integer> rangePositions = new HashMap<>();
		while (rangePositions.size() != ranges.size()) {
			for (Map.Entry<Range, List<Integer>> entry : rangePosibilities.entrySet()) {
				if (entry.getValue().size() == 1) {
					int value = entry.getValue().get(0);
					rangePositions.put(entry.getKey(), value);
					for (List<Integer> lists : rangePosibilities.values()) {
						if (lists.size() > 0)
							lists.remove(lists.indexOf(value));
					}
				}
			}
		}
		
		long result = 1;
		for (Map.Entry<Range, Integer> entry : rangePositions.entrySet()) {
			if (entry.getKey().name.startsWith("departure")) {
				result *= yourTicket.get(entry.getValue());
			}
		}

		return result;
	}

	private boolean isInAnyRange(int number) {
		for (Range r : ranges) {
			if (r.isInRange(number))
				return true;
		}
		return false;
	}

	private class Range {
		int min1;
		int min2;
		int max1;
		int max2;
		String name;

		public Range(String name, int min1, int max1, int min2, int max2) {
			this.name = name;
			this.min1 = min1;
			this.min2 = min2;
			this.max1 = max1;
			this.max2 = max2;
		}

		public boolean isInRange(int number) {
			if (number >= min1 && number <= max1)
				return true;
			if (number >= min2 && number <= max2)
				return true;
			return false;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Range other = (Range) obj;

			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	}

	private List<Range> ranges = new ArrayList<>();
	private List<Integer> yourTicket = new ArrayList<>();
	private List<List<Integer>> tickets = new ArrayList<>();

	private void populateInput() {
		Pattern range_pat = Pattern.compile("(.*): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)");

		boolean range = true;
		boolean your = false;
		boolean rest = false;
		for (String line : LINES) {

			if (range) {
				if (line.length() == 0) {
					range = false;
					your = true;
					continue;
				}
				Matcher m = range_pat.matcher(line);
				while (m.find()) {
					Range r = new Range(m.group(1), Integer.valueOf(m.group(2)), Integer.valueOf(m.group(3)),
							Integer.valueOf(m.group(4)), Integer.valueOf(m.group(5)));
					ranges.add(r);
				}
			} else if (your) {
				if (line.startsWith("your") || line.length() == 0)
					continue;
				else {
					yourTicket = Arrays.stream(line.split(",")).map(s -> Integer.valueOf(s))
							.collect(Collectors.toList());
					your = false;
					rest = true;
				}
			} else if (rest) {
				if (line.startsWith("nearby") || line.length() == 0 || line.startsWith("your"))
					continue;
				List<Integer> ticket = Arrays.stream(line.split(",")).map(s -> Integer.valueOf(s))
						.collect(Collectors.toList());
				tickets.add(ticket);
			}
		}
	}

}
