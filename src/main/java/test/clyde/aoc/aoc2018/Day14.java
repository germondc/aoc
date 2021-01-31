package test.clyde.aoc.aoc2018;

import java.util.ArrayList;
import java.util.List;

public class Day14 {
	private String problemA(int input) {
		List<Integer> recipes = new ArrayList<>();
		recipes.add(3);
		recipes.add(7);
		int elf1Index = 0;
		int elf2Index = 1;
		while (recipes.size() < input + 10) {
			int elf1Recipe = recipes.get(elf1Index);
			int elf2Recipe = recipes.get(elf2Index);
			int sum = elf1Recipe + elf2Recipe;
			if (sum >= 10) {
				int tens = sum / 10;
				int units = sum % 10;
				recipes.add(tens);
				recipes.add(units);
			} else {
				recipes.add(sum);
			}
			elf1Index += 1 + elf1Recipe;
			while (elf1Index >= recipes.size())
				elf1Index -= recipes.size();
			elf2Index += 1 + elf2Recipe;
			while (elf2Index >= recipes.size())
				elf2Index -= recipes.size();
		}

		String result = "";
		for (int i = input; i < input + 10; i++) {
			result += recipes.get(i);
		}
		return result;
	}

	private long problemB(String input) {
		// int[] input = new int[] { 1, 2, 2, 0, 6, 7 };
		// input = new int[] { 4, 1, 4, 9, 5 };
		// input = new int[] { 0, 1, 5, 2, 9 };
//		String input = "59414";
		int inputSize = input.length();

		StringBuilder sb = new StringBuilder();

//		List<Integer> recipes = new ArrayList<>();
//		recipes.add(3);
//		recipes.add(7);
		sb.append(3);
		sb.append(7);
		sb.append(1);
		sb.append(0);
		sb.append(1);
		sb.append(0);
		int elf1Index = 3;
		int elf2Index = 4;
		while (true) {
			int elf1Recipe = sb.charAt(elf1Index) - '0';
			int elf2Recipe = sb.charAt(elf2Index) - '0';
			int sum = elf1Recipe + elf2Recipe;
			if (sum >= 10) {
				int tens = sum / 10;
				int units = sum % 10;
				sb.append(tens);
				sb.append(units);
			} else {
				sb.append(sum);
			}
			if (sb.length() > 1e7) {
			}
			int currentSize = sb.length();
			String sub = sb.substring(currentSize - inputSize - 1);
			if (sub.contains(input))
				break;
			elf1Index += 1 + elf1Recipe;
			while (elf1Index >= currentSize) {
				elf1Index -= currentSize;
			}
			elf2Index += 1 + elf2Recipe;
			while (elf2Index >= currentSize) {
				elf2Index -= currentSize;
			}

		}

		return sb.indexOf(input);
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day14().problemA(9));
		System.out.println("TestA: " + new Day14().problemA(5));
		System.out.println("TestA: " + new Day14().problemA(18));
		System.out.println("TestA: " + new Day14().problemA(2018));
		System.out.println("A: " + new Day14().problemA(760221));
		System.out.println("TestB: " + new Day14().problemB("760221"));
//		System.out.println("B: " + new Day14().problemB(LINES));
	}

}
