package test.clyde.aoc.aoc2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day12 {

	private static List<String> LINES = Utils.readFile("2018/Day11.txt");
	private static List<String> TEST = Utils.readFile("2018/Day11a.txt");

	private long problemA(List<String> lines, int generations) {
		String state = getInitialState(lines.get(0));
		Map<String, String> rules = getRules(lines);
		return sumPots(state, rules, generations);
	}

	private long sumPots(String state, Map<String, String> rules, int generations) {
		int left = 0;
		for (int gen = 0; gen < generations; gen++) {
			StringBuilder newState = new StringBuilder();
			for (int pot = -2; pot < state.length() + 2; pot++) {
				String currentPot = rules.get(getSub(state, pot));
				newState.append(currentPot == null ? "." : currentPot);
			}
			state = newState.toString();
			left -= 2;
		}

		long result = 0;
		for (int i = 0; i < state.length(); i++) {
			if (state.charAt(i) == '#')
				result += i + left;
		}

		return result;
	}

	private long problemB(List<String> lines) {
		String state = getInitialState(lines.get(0));
		Map<String, String> rules = getRules(lines);

		List<Long> repeats = new ArrayList<>();
		int i = 0;
		long previousSum = 0;
		long currentSum = 0;
		long sumAtRepeats = 0;
		while (repeats.size() < 10) {
			currentSum = sumPots(state, rules, i);
			long diff = currentSum - previousSum;
			if (!repeats.contains(diff)) {
				repeats.clear();
				sumAtRepeats = currentSum;
			}
			repeats.add(diff);
			i++;
			previousSum = currentSum;
		}
		
		long diff = repeats.get(0);
		long result = (50000000000L - (i-10))*diff + sumAtRepeats;

		return result;
	}

	private String getSub(String state, int index) {
		StringBuilder sb = new StringBuilder();
		for (int i = index - 2; i <= index + 2; i++) {
			if (i < 0 || i > state.length() - 1)
				sb.append(".");
			else
				sb.append(state.charAt(i));
		}
		return sb.toString();
	}

	private String getInitialState(String line) {
		return line.substring(15);
	}

	private Map<String, String> getRules(List<String> lines) {
		return lines.stream().filter(l -> !l.contains("initial") && l.length() > 0)
				.collect(Collectors.toMap(l -> l.substring(0, 5), l -> Character.toString(l.charAt(9))));
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day12().problemA(TEST, 20));
		System.out.println("A: " + new Day12().problemA(LINES, 20));
		System.out.println("B: " + new Day12().problemB(LINES));
	}

}
