package test.clyde.aoc.aoc2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day7 {

	private static List<String> LINES = Utils.readFile("2020/Day7.txt");
	private static List<String> TESTA = Utils.readFile("2020/Day7a.txt");
	private static List<String> TESTB = Utils.readFile("2020/Day7b.txt");

	Pattern PATTERN = Pattern.compile("(.*) bags contain (.*)\\.");
	Pattern PATTERN2 = Pattern.compile("([0-9]+) ([^,]+) bag");
	String search = "shiny gold";

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day7().problemA(TESTA));
		System.out.println("TestB: " + new Day7().problemB(TESTB));
		System.out.println("A: " + new Day7().problemA(LINES));
		System.out.println("B: " + new Day7().problemB(LINES));
	}

	private Map<String, Map<String, Integer>> getBagContents(List<String> lines) {
		Map<String, Map<String, Integer>> bagContents = new HashMap<>();
		for (String line : lines) {

			Matcher m = PATTERN.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}
			String bag = m.group(1);

			Map<String, Integer> contents = new HashMap<>();
			Matcher mm = PATTERN2.matcher(m.group(2));
			while (mm.find()) {
				contents.put(mm.group(2), Integer.valueOf(mm.group(1)));
			}

			bagContents.put(bag, contents);
		}
		return bagContents;
	}

	private int problemA(List<String> lines) {
		Map<String, Map<String, Integer>> bagContents = getBagContents(lines);

		int count = 0;
		for (String bag : bagContents.keySet()) {
			if (containsGold(bagContents, bag))
				count++;
		}
		return count;
	}

	private boolean containsGold(Map<String, Map<String, Integer>> bagContents, String bag) {
		if (!bagContents.containsKey(bag))
			return false;
		for (String innerBag : bagContents.get(bag).keySet()) {
			if (innerBag.equals(search)) {
				return true;
			}
			if (containsGold(bagContents, innerBag)) {
				return true;
			}
		}
		return false;
	}

	private int problemB(List<String> lines) {
		Map<String, Map<String, Integer>> bagContents = getBagContents(lines);

		int count = countInBag(bagContents, search);
		return count;
	}

	private int countInBag(Map<String, Map<String, Integer>> bagContents, String bag) {
		int count = 0;
		for (String innerBag : bagContents.get(bag).keySet()) {
			int thisBag = bagContents.get(bag).get(innerBag);
			count += thisBag + (thisBag * countInBag(bagContents, innerBag));
		}
		return count;
	}
}
