package test.clyde.aoc.aoc2020;

public class Day15 {

	private static int[] INPUT = new int[] { 6, 19, 0, 5, 7, 13, 1 };
	private static int[] TEST_INPUT = new int[] { 0, 3, 6 };

	public static void main(String[] args) {
		System.out.println("Test: " + new Day15().problemB(TEST_INPUT, 10));
		System.out.println("Test: " + new Day15().problemB(TEST_INPUT, 2020));
		System.out.println("Test: " + new Day15().problemB(TEST_INPUT, 30000000));
		System.out.println("A: " + new Day15().problemB(INPUT, 2020));
		System.out.println("B: " + new Day15().problemB(INPUT, 30000000));
	}

	private long problemB(int[] input, int size) {
		int[] store = new int[size];
		for (int i = 0; i < input.length - 1; i++) {
			store[input[i]] = i + 1;
		}
		int previous = input[input.length - 1];
		for (int i = input.length; i < size; i++) {
			int turn = store[previous];
			store[previous] = i;
			if (turn == 0)
				previous = 0;
			else
				previous = i - turn;
		}
		return previous;
	}
}