package test.clyde.aoc.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day4 {

	private static List<String> LINES = Utils.readFile("2020/Day4.txt");
	private static List<String> validField = Arrays
			.asList(new String[] { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" });
	private static List<String> validColours = Arrays
			.asList(new String[] { "amb", "blu", "brn", "gry", "grn", "hzl", "oth" });

	public static void main(String[] args) {
		System.out.println("A: " + new Day4().problemA());
		System.out.println("B: " + new Day4().problemB());
	}

	private List<Map<String, String>> getValues() {
		Pattern p = Pattern.compile("([^: ]*):([^: ]*)");
		List<Map<String, String>> result = new ArrayList<>();
		Map<String, String> passport = new HashMap<>();
		result.add(passport);
		for (String line : LINES) {
			if (line.length() == 0) {
				passport = new HashMap<>();
				result.add(passport);
				continue;
			}
			Matcher m = p.matcher(line);
			while (m.find()) {
				passport.put(m.group(1), m.group(2));
			}
		}
		return result;
	}

	private long problemA() {
		List<Map<String, String>> passports = getValues();
		int result = 0;
		for (Map<String, String> passport : passports) {
			if (passport.keySet().stream().filter(n -> validField.contains(n)).count() == 7)
				result++;
		}
		return result;
	}

	private boolean validField(String name, String value) {
		if (name.equals("byr")) {
			return testValue(value, 1920, 2002);
		} else if (name.equals("iyr")) {
			return testValue(value, 2010, 2020);
		} else if (name.equals("eyr")) {
			return testValue(value, 2020, 2030);
		} else if (name.equals("hgt")) {
			String units = value.substring(value.length() - 2);
			String v = value.substring(0, value.length() - 2);
			if (units.equals("cm")) {
				return testValue(v, 150, 193);
			} else if (units.equals("in")) {
				return testValue(v, 59, 76);
			} else {
				return false;
			}
		} else if (name.equals("hcl")) {
			Pattern hclPat = Pattern.compile("^#[0-9a-f]{6}$");
			Matcher m = hclPat.matcher(value);
			return m.find();
		} else if (name.equals("ecl")) {
			return validColours.contains(value);
		} else if (name.equals("pid")) {
			Pattern pidPat = Pattern.compile("^\\d{9}$");
			Matcher m = pidPat.matcher(value);
			return m.find();
		} else if (name.equals("cid")) {
			return true;
		}
		return false;
	}

	private boolean testValue(String value, int min, int max) {
		int i = Integer.valueOf(value);
		return (i >= min && i <= max);
	}

	private long problemB() {
		List<Map<String, String>> passports = getValues();
		int result = 0;
		main: for (Map<String, String> passport : passports) {
			if (passport.keySet().stream().filter(n -> validField.contains(n)).count() != 7)
				continue;
			for (Map.Entry<String, String> entry : passport.entrySet()) {
				if (!validField(entry.getKey(), entry.getValue()))
					continue main;
			}
			result++;
		}
		return result;
	}
}
