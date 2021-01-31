package test.clyde.aoc.aoc2020;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 {

	private class SpecialItem {
		int value;
		SpecialItem next;

		public SpecialItem(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return String.format("%s", value);
		}
	}

	public static void main(String[] args) throws IOException {
		new Day23().solve("389125467", 10);
		new Day23().solve("398254716", 100);
		new Day23().solveB("389125467", 10000000);
		new Day23().solveB("398254716", 10000000);
	}

	public void solve(String cupList, int moves) {
		Map<Integer, SpecialItem> items = new HashMap<>();
		SpecialItem first = null;
		SpecialItem previous = null;
		int size = 0;
		for (char c : cupList.toCharArray()) {
			SpecialItem s = new SpecialItem((c - '0'));
			items.put(s.value, s);
			if (first == null)
				first = s;
			if (previous != null)
				previous.next = s;
			previous = s;
			size++;
		}
		previous.next = first;

		SpecialItem si = first;
		for (int i = 0; i < moves; i++) {
			int currentCup = si.value;
			List<Integer> pickUp = pickUp(si);
			int dest = currentCup - 1;
			while (pickUp.contains(dest) || dest == 0) {
				dest--;
				if (dest <= 0) {
					dest = size;
				}
			}
			insertCups(items, si, si.next, dest);
			si = si.next;
		}

		String result = "";

		SpecialItem search = searchCup(first, 1);
		for (int j = 0; j < size - 1; j++) {
			result += search.next.value;
			search = search.next;
		}

		System.out.println("A: " + result);
	}
	
	private List<Integer> pickUp(SpecialItem currentCup) {
		List<Integer> pickUp = new ArrayList<>();
		pickUp.add(currentCup.next.value);
		pickUp.add(currentCup.next.next.value);
		pickUp.add(currentCup.next.next.next.value);
		return pickUp;
	}

	private SpecialItem searchCup(SpecialItem start, int value) {
		SpecialItem search = start;
		while (search.value != value) {
			search = search.next;
		}
		return search;
	}

	private void insertCups(Map<Integer, SpecialItem> items, SpecialItem currentCup, SpecialItem firstPickup,
			int dest) {
		SpecialItem search = items.get(dest);
		SpecialItem searchNext = search.next;
		currentCup.next = firstPickup.next.next.next;
		search.next = firstPickup;
		firstPickup.next.next.next = searchNext;
	}

	private Map<String, Long> timings = new HashMap<>();

	private void addTiming(String label, long start) {
		if (!timings.containsKey(label))
			timings.put(label, 0L);
		timings.put(label, timings.get(label) + (System.currentTimeMillis() - start));
	}

	public void solveB(String cupList, int moves) {
		Map<Integer, SpecialItem> items = new HashMap<>();

		SpecialItem first = null;
		SpecialItem previous = null;
		int size = 0;
		for (char c : cupList.toCharArray()) {
			long start = System.currentTimeMillis();
			SpecialItem s = new SpecialItem((c - '0'));
			addTiming("create", start);
			items.put(s.value, s);
			start = System.currentTimeMillis();
			addTiming("put", start);
			start = System.currentTimeMillis();
			if (first == null)
				first = s;
			if (previous != null)
				previous.next = s;
			previous = s;
			size++;
			addTiming("rest", start);
		}
		for (int i = 10; i <= 1e6; i++) {
			long start = System.currentTimeMillis();
			addTiming("create", start);
			SpecialItem s = new SpecialItem(i);
			start = System.currentTimeMillis();
			items.put(s.value, s);
			addTiming("put", start);
			start = System.currentTimeMillis();
			previous.next = s;
			previous = s;
			size++;
			addTiming("rest", start);
		}
		previous.next = first;

		SpecialItem si = first;
		for (int i = 0; i < moves; i++) {
			if (i % 500000 == 0)
				System.out.print(".");
			int currentCup = si.value;
			List<Integer> pickUp = pickUp(si);
			int dest = currentCup - 1;
			while (pickUp.contains(dest) || dest == 0) {
				dest--;
				if (dest <= 0) {
					dest = size;
				}
			}
			insertCups(items, si, si.next, dest);
			si = si.next;
		}

		System.out.println();
		SpecialItem search = items.get(1);
		BigInteger value1 = BigInteger.valueOf(search.next.value);
		BigInteger value2 = BigInteger.valueOf(search.next.next.value);
		System.out.println("B: " + value1.multiply(value2));
	}
}
