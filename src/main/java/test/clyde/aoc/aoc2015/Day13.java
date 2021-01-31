package test.clyde.aoc.aoc2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Permutations;
import test.clyde.aoc.utils.Utils;

public class Day13 {
	
	private static List<String> LINES = Utils.readFile("2015/Day13.txt");
	Pattern PATTERN = Pattern.compile("(.*) would (.*) ([0-9]*) happiness units by sitting next to (.*).");
	//Alice would lose 2 happiness units by sitting next to Bob.
	
	
	
	private int bestHappiness() {
		
		List<String> people = new ArrayList<>();
		Map<String, Map<String, Integer>> map = new HashMap<>();
		
		for (String line : LINES) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}
			String personA = m.group(1);
			if (!people.contains(personA))
				people.add(personA);
			String personB = m.group(4);
			int happiness = Integer.valueOf(m.group(3));
			boolean gain =  m.group(2).equals("gain");
			if (!gain)
				happiness *= -1;
			Map<String, Integer> happinessMap = map.get(personA);
			if (happinessMap == null) {
				happinessMap = new HashMap<>();
				map.put(personA, happinessMap);
			}
			happinessMap.put(personB, happiness);
		}
		
		int bestHappiness = 0;
		
		for (List<String> perm : new Permutations<>(people)) {
			int totalHappiness = 0;
		
			for (int i=0; i<perm.size(); i++) {
				String personA = perm.get(i);
				String personB = perm.get(i==perm.size()-1 ? 0 : i+1);
				totalHappiness += map.get(personA).get(personB);
				totalHappiness += map.get(personB).get(personA);
			}
			if (totalHappiness > bestHappiness)
				bestHappiness = totalHappiness;
		}
		return bestHappiness;
	}
	
	private int bestHappinessB() {
		
		List<String> people = new ArrayList<>();
		Map<String, Map<String, Integer>> map = new HashMap<>();
		
		for (String line : LINES) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}
			String personA = m.group(1);
			if (!people.contains(personA))
				people.add(personA);
			String personB = m.group(4);
			int happiness = Integer.valueOf(m.group(3));
			boolean gain =  m.group(2).equals("gain");
			if (!gain)
				happiness *= -1;
			Map<String, Integer> happinessMap = map.get(personA);
			if (happinessMap == null) {
				happinessMap = new HashMap<>();
				map.put(personA, happinessMap);
			}
			happinessMap.put(personB, happiness);
		}

		Map<String, Integer> happinessMapYou = new HashMap<>();
		for (String person : people) {
			happinessMapYou.put(person, 0);
			Map<String, Integer> happinessMap = map.get(person);
			happinessMap.put("YOU", 0);
		}
		map.put("YOU", happinessMapYou);
		people.add("YOU");
		
		int bestHappiness = 0;
		
		for (List<String> perm : new Permutations<>(people)) {
			int totalHappiness = 0;
		
			for (int i=0; i<perm.size(); i++) {
				String personA = perm.get(i);
				String personB = perm.get(i==perm.size()-1 ? 0 : i+1);
				totalHappiness += map.get(personA).get(personB);
				totalHappiness += map.get(personB).get(personA);
			}
			if (totalHappiness > bestHappiness)
				bestHappiness = totalHappiness;
		}
		return bestHappiness;
	}

	public static void main(String[] argv) {
		System.out.println("A: " + new Day13().bestHappiness());
		System.out.println("B: " + new Day13().bestHappinessB());
	}
}
