package test.clyde.aoc.aoc2019;

import java.util.Arrays;
import java.util.List;

import test.clyde.aoc.utils.Utils;

public class Day16 {
	private static List<String> INPUT = Utils.readFile("2019/Day16.txt");
	private String input;

	public Day16(String input) {
		super();
		this.input = input;
	}

	private static int[] pattern = { 0, 1, 0, -1 };

	private String problemA(int phases) {
		int length = input.length();
		int[] currentSignalArray = new int[length];
		for (int i = 0; i < length; i++) {
			currentSignalArray[i] = input.charAt(i) - '0';
		}
		for (int phase = 0; phase < phases; phase++) {
			int[] signalArray = new int[length];
			for (int j = 0; j < length; j++) {
				int value = 0;
				for (int i = 0; i < length; i++) {
					int current = currentSignalArray[i];
					int arrayIndex = (i + 1) / (j + 1);
					value += current * pattern[arrayIndex % 4];
				}
				value = Math.abs(value) % 10;
				signalArray[j] = value;
			}

			currentSignalArray = signalArray;
		}

		String result = "";
		for (int i = 0; i < 8; i++) {
			result += currentSignalArray[i];
		}
		return result;
	}

	private String problemB(int phases) {
		int length = input.length();
		int[] currentSignalArray = new int[length];
		for (int i = 0; i < length; i++) {
			currentSignalArray[i] = input.charAt(i) - '0';
		}
		int offset = Integer.valueOf(input.substring(0, 7));
		int[] msg = new int[length * 10000 - offset];
		for (int i = 0; i < msg.length; i++) {
			msg[i] = currentSignalArray[(offset + i) % (currentSignalArray.length)];
		}

		for (int phase = 0; phase < phases; phase++) {
			int sum = 0;
			for (int i = msg.length - 1; i >= 0; i--) {
				sum += msg[i];
				msg[i] = Math.abs(sum) % 10;
			}
		}

		String result = "";
		for (int i : Arrays.copyOfRange(msg, 0, 8)) {
			result += i;
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day16("12345678").problemA(4));
		System.out.println("A: " + new Day16("80871224585914546619083218645595").problemA(100));
		System.out.println("A: " + new Day16("19617804207202209144916044189917").problemA(100));
		System.out.println("A: " + new Day16("69317163492948606335995924319873").problemA(100));
		System.out.println("A: " + new Day16(INPUT.get(0)).problemA(100));
		System.out.println("B: " + new Day16(INPUT.get(0)).problemB(100));
	}
}
