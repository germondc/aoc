package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day8 {

	private static List<String> LINES = Utils.readFile("2017/Day8.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day8a.txt");

	private Map<String, Integer> registers = new HashMap<>();
	private int highest = 0;

	private long problemA(List<String> lines) {
		Pattern pat = Pattern.compile("([a-z]+) ([a-z]+) (-?[0-9]+) if ([a-z]+) (.+) (-?[0-9]+)");

		for (String line : lines) {
			Matcher m = pat.matcher(line);
			if (m.find()) {
				String register = m.group(1);
				String instruction = m.group(2);
				int amount = Integer.valueOf(m.group(3));
				String conditionReg = m.group(4);
				String condition = m.group(5);
				int conditionAmount = Integer.valueOf(m.group(6));
				if (doCondition(conditionReg, condition, conditionAmount)) {
					int regAmount = getRegisterValue(register);
					if (instruction.equals("inc")) {
						regAmount += amount;
					} else if (instruction.equals("dec")) {
						regAmount -= amount;
					} else {
						throw new RuntimeException("missing " + instruction);
					}
					registers.put(register, regAmount);
					recordHighest();
				}
			}
		}
		System.out.println("highest: " + highest);
		return registers.values().stream().mapToInt(i -> i).max().getAsInt();
	}
	
	private void recordHighest() {
		int value = registers.values().stream().mapToInt(i -> i).max().getAsInt();
		if (value > highest)
			highest = value;
	}

	private int getRegisterValue(String register) {
		if (!registers.containsKey(register)) {
			registers.put(register, 0);
		}
		return registers.get(register);
	}

	private boolean doCondition(String register, String condition, int amount) {
		int registerValue = getRegisterValue(register);
		if (condition.equals(">")) {
			return registerValue > amount;
		} else if (condition.equals("<")) {
			return registerValue < amount;
		} else if (condition.equals(">=")) {
			return registerValue >= amount;
		} else if (condition.equals("<=")) {
			return registerValue <= amount;
		} else if (condition.equals("==")) {
			return registerValue == amount;
		} else if (condition.equals("!=")) {
			return registerValue != amount;
		} else {
			throw new RuntimeException("missing " + condition);
		}
	}

	public static void main(String[] args) {
		System.out.println("Test: " + new Day8().problemA(TEST_LINES));
		System.out.println("A: " + new Day8().problemA(LINES));
//		System.out.println("TestB: " + new Day8().problemB(TEST_LINES));
//		System.out.println("B: " + new Day8().problemB(LINES));
	}

}
