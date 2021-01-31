package test.clyde.aoc.aoc2020;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day25 {

	private static List<String> LINES = Utils.readFile("2020/day25.txt");
	private static List<String> TEST_LINES = Utils.readFile("2020/day25a.txt");

	public static void main(String[] args) throws IOException {
		new Day25().solve(TEST_LINES);
		new Day25().solve(LINES);
	}

	private void solve(List<String> lines) {
		long card = Long.valueOf(lines.get(0)); //9789649;//5764801;
		long door = Long.valueOf(lines.get(1)); //3647239;//17807724;
		int cardLoop = getLoopSize(card);
		int doorLoop = getLoopSize(door);
		
		long key = transform(card, doorLoop);
		long key1 = transform(door, cardLoop);
		
		System.out.println("A: " + key + " (" + key1 + ")");
	}
	
	private long transform(long key, int loopSize) {
		long value = 1;
		for(int i=0; i<loopSize; i++) {
			value *= key;
			value = value % 20201227;
		}
		return value;
	}
	
	private int getLoopSize(long publicKey) {
		int subject = 7;
		int loopSize = 0;
		long value = 1;
		while (value != publicKey) {
			value *= subject;
			value = value % 20201227;
			loopSize++;
		}
		return loopSize;
	}
}
