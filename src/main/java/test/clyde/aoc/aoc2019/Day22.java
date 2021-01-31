package test.clyde.aoc.aoc2019;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day22 {
	private static List<String> INPUT = Utils.readFile("2019/Day22.txt");
	private static List<String> TESTA = Utils.readFile("2019/Day22a.txt");
	private static List<String> TESTAA = Utils.readFile("2019/Day22aa.txt");
	private static List<String> TESTB = Utils.readFile("2019/Day22b.txt");
	private static List<String> TESTC = Utils.readFile("2019/Day22c.txt");

	private List<String> input;
	public Day22(List<String> input) {
		this.input = input;
	}

	public long problemA(int size) {
		BigInteger numberOfCards = BigInteger.valueOf(size);

		BigInteger minusOne = BigInteger.valueOf(-1);
		BigInteger offsetDiff = BigInteger.ZERO;
		BigInteger incMul = BigInteger.ONE;
		for (String line : input) {
			if (line.startsWith("deal into new stack")) {
				incMul = incMul.multiply(minusOne).mod(numberOfCards);
				offsetDiff = offsetDiff.add(incMul).mod(numberOfCards);
			} else if (line.startsWith("cut ")) {
				int value = Integer.valueOf(line.replace("cut ", ""));
				offsetDiff = offsetDiff.add(incMul.multiply(BigInteger.valueOf(value))).mod(numberOfCards);
			} else if (line.startsWith("deal with increment ")) {
				int value = Integer.valueOf(line.replace("deal with increment ", ""));
				incMul = incMul.multiply(BigInteger.valueOf(value).modInverse(numberOfCards)).mod(numberOfCards);
			} else {
				throw new RuntimeException("missing");
			}
		}

		return BigInteger.valueOf(2019).subtract(offsetDiff).multiply(incMul.modInverse(numberOfCards))
				.mod(numberOfCards).longValue();
	}

	public long problemB() {

		BigInteger numberOfCards = new BigInteger("119315717514047");
		BigInteger shuffles = new BigInteger("101741582076661");

		BigInteger minusOne = BigInteger.valueOf(-1);
		BigInteger offsetDiff = BigInteger.ZERO;
		BigInteger incMul = BigInteger.ONE;
		for (String line : input) {
			if (line.startsWith("deal into new stack")) {
				incMul = incMul.multiply(minusOne).mod(numberOfCards);
				offsetDiff = offsetDiff.add(incMul).mod(numberOfCards);
			} else if (line.startsWith("cut ")) {
				int value = Integer.valueOf(line.replace("cut ", ""));
				offsetDiff = offsetDiff.add(incMul.multiply(BigInteger.valueOf(value))).mod(numberOfCards);
			} else if (line.startsWith("deal with increment ")) {
				int value = Integer.valueOf(line.replace("deal with increment ", ""));
				incMul = incMul.multiply(BigInteger.valueOf(value).modInverse(numberOfCards)).mod(numberOfCards);
			} else {
				throw new RuntimeException("missing");
			}
		}

		BigInteger incrementN = incMul.modPow(shuffles, numberOfCards);
		BigInteger b = BigInteger.ONE.subtract(incMul);
		BigInteger offsetN = offsetDiff.multiply(BigInteger.ONE.subtract(incrementN))
				.multiply(b.modInverse(numberOfCards)).mod(numberOfCards);

		return incrementN.multiply(BigInteger.valueOf(2020)).add(offsetN).mod(numberOfCards).longValue();
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day22(INPUT).problemA(10007));
		System.out.println("B: " + new Day22(INPUT).problemB());
	}
}