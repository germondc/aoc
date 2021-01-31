package test.clyde.aoc.aoc2017;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day24 {

	private static List<String> LINES = Utils.readFile("2017/Day24.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day24a.txt");

	private long problemA(List<String> lines) {
		List<Point> ports = new ArrayList<>();
		for (String line : lines) {
			String[] split = line.split("/");
			int number1 = Integer.valueOf(split[0]);
			int number2 = Integer.valueOf(split[1]);
			ports.add(new Point(number1, number2));
		}

		findNextPoint(ports, null, 0, 0);
		return strongest;
	}
	
	private long problemB(List<String> lines) {
		List<Point> ports = new ArrayList<>();
		for (String line : lines) {
			String[] split = line.split("/");
			int number1 = Integer.valueOf(split[0]);
			int number2 = Integer.valueOf(split[1]);
			ports.add(new Point(number1, number2));
		}

		findNextPointB(ports, null, 0, 0, 0);
		return strongest;
	}

	private int strongest = 0;
	private int longest = 0;

	private void findNextPoint(List<Point> remainingPorts, Point current, int sideUsed, int currentScore) {
		int otherSide;
		if (current == null) {
			otherSide = 0;
		} else {
			if (current.x == sideUsed)
				otherSide = current.y;
			else
				otherSide = current.x;
		}
		int score = currentScore + sideUsed + otherSide;
		List<Point> possiblePorts = remainingPorts.stream().filter(p -> p.x == otherSide || p.y == otherSide)
				.collect(Collectors.toList());
		if (possiblePorts.size() == 0 && score > strongest) {
			strongest = score;
		}
		for (Point port : possiblePorts) {
			List<Point> newRemaining = new ArrayList<>(remainingPorts);
			newRemaining.remove(port);
			findNextPoint(newRemaining, port, otherSide, score);
		}
	}
	
	private void findNextPointB(List<Point> remainingPorts, Point current, int sideUsed, int currentScore, int currentLength) {
		int otherSide;
		if (current == null) {
			otherSide = 0;
		} else {
			if (current.x == sideUsed)
				otherSide = current.y;
			else
				otherSide = current.x;
		}
		int score = currentScore + sideUsed + otherSide;
		List<Point> possiblePorts = remainingPorts.stream().filter(p -> p.x == otherSide || p.y == otherSide)
				.collect(Collectors.toList());
		if (possiblePorts.size() == 0 && currentLength >= longest) {
			longest = currentLength;
			if (currentLength > longest || (currentLength == longest && score > strongest))
				strongest = score;
		}
		for (Point port : possiblePorts) {
			List<Point> newRemaining = new ArrayList<>(remainingPorts);
			newRemaining.remove(port);
			findNextPointB(newRemaining, port, otherSide, score,  currentLength+1);
		}
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day24().problemA(TEST_LINES));
		System.out.println("A: " + new Day24().problemA(LINES));
		System.out.println("TestB: " + new Day24().problemB(TEST_LINES));
		System.out.println("B: " + new Day24().problemB(LINES));
	}

}
