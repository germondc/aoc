package test.clyde.aoc.aoc2015;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day19 {
	private static List<String> LINES = Utils.readFile("2015/Day19.txt");
	
	private class Replacement {
		String from;
		String to;
		public Replacement(String from, String to) {
			this.from = from;
			this.to = to;
		}
		public int getToLength() {
			return to.length();
		}
		@Override
		public String toString() {
			return String.format("Replacement [from=%s, to=%s]", from, to);
		}
	}
	
	private void loadReplacements() {
		String formula = null;
		List<Replacement> replacements = new ArrayList<>();
		List<String> from = new ArrayList<>();
		List<String> to = new ArrayList<>();
		for (String line: LINES) {
			if (line.contains("=>")) {
				String part1 = line.substring(0, line.indexOf("=>") -1).trim();
				String part2 = line.substring(line.indexOf("=>") +2).trim();
				from.add(part1);
				to.add(part2);
				replacements.add(new Replacement(part1, part2));
			} else if (line.trim().length() > 0) {
				formula = line;
			}
		}
		
		Collections.sort(replacements, Comparator.comparingInt(Replacement::getToLength).reversed());
		
		Set<String> theStrings = new HashSet<>();
		for (int fromIndex=0; fromIndex<from.size(); fromIndex++) {
			String f = from.get(fromIndex);
			String t = to.get(fromIndex);
			Matcher m = Pattern.compile(f).matcher(formula);
			while (m.find())
			{
				String s = formula.substring(0, m.start()) + t + formula.substring(m.end());
				theStrings.add(s);
			}
		}
		
		System.out.println("A: " + theStrings.size());
		
		String medicine = new String(formula);
		int count = 0;
		while (!medicine.equals("e")) {
			for (Replacement r : replacements) {
				if (medicine.contains(r.to)) {
					medicine = medicine.replaceFirst(r.to, r.from);
					count++;
				}
			}
		}
		
		System.out.println("B: " + count);
	}
	

	public static void main(String[] argv) {
		new Day19().loadReplacements();
	}
}
