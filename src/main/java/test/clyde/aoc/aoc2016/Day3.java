package test.clyde.aoc.aoc2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day3 {
	private static List<String> LINES = Utils.readFile("2016/Day3.txt");

	private int problemA() {

		int count = 0;
		for (String line : LINES) {

			String[] elements = line.split("\\s+");

			int value1 = Integer.valueOf(elements[1]);
			int value2 = Integer.valueOf(elements[2]);
			int value3 = Integer.valueOf(elements[3]);
			if (isValide(value1, value2, value3))
				count++;
		}
		return count;
	}

	private boolean isValid(List<Integer> items) {
		Collections.sort(items);
		return (items.get(0) + items.get(1)) > items.get(2);
	}

	private boolean isValid(int[] items) {
		List<Integer> li = new ArrayList<>();
		for (int i : items) {
			li.add(i);
		}
		return isValid(li);
	}

	private boolean isValide(int value1, int value2, int value3) {
		List<Integer> items = new ArrayList<>();
		items.add(value1);
		items.add(value2);
		items.add(value3);
		return isValid(items);
	}

	private int problemB() {
		int count = 0;
		int index = 0;
		int[][] items = new int[3][3];
		for (String line : LINES) {

			String[] elements = line.split("\\s+");

			items[0][index] = Integer.valueOf(elements[1]);
			items[1][index] = Integer.valueOf(elements[2]);
			items[2][index] = Integer.valueOf(elements[3]);
			index++;
			if (index == 3) {
				index = 0;
				for (int i = 0; i < 3; i++) {
					if (isValid(items[i]))
						count++;
				}
			}
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day3().problemA());
		System.out.println("B: " + new Day3().problemB());
	}
}
