package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day15 {

	private long problemA(int a, int b) {
		int genAfactor = 16807;
		int genBfactor = 48271;

		long previousA = a;
		long previousB = b;

		long mask = 0xFFFF;

		int count = 0;
		for (int i = 0; i < 40e6; i++) {
			long nextA = (previousA * genAfactor) % 2147483647;
			long nextB = (previousB * genBfactor) % 2147483647;
			if ((nextA & mask) == (nextB & mask)) {
				count++;
			}

			previousA = nextA;
			previousB = nextB;
		}

		return count;
	}
	
	private long problemB(int a, int b) {
		int genAfactor = 16807;
		int genBfactor = 48271;

		long previousA = a;
		long previousB = b;

		long mask = 0xFFFF;

		int count = 0;
		for (int i = 0; i < 5e6; i++) {
			long nextA = (previousA * genAfactor) % 2147483647;
			while (nextA%4 != 0) {
				previousA = nextA;
				nextA = (previousA * genAfactor) % 2147483647;
			}
			long nextB = (previousB * genBfactor) % 2147483647;
			while (nextB%8 != 0) {
				previousB = nextB;
				nextB = (previousB * genBfactor) % 2147483647;
			}
			if ((nextA & mask) == (nextB & mask)) {
				count++;
			}

			previousA = nextA;
			previousB = nextB;
		}

		return count;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day15().problemA(65, 8921));
		System.out.println("A: " + new Day15().problemA(512, 191));
		System.out.println("TestB: " + new Day15().problemB(65, 8921));
		System.out.println("B: " + new Day15().problemB(512, 191));
	}

}
