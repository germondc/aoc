package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day12 {

	private static List<String> LINES = Utils.readFile("2017/Day12.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day12a.txt");

	private class Program {
		List<Program> refs = new ArrayList<>();
		int id;

		public Program(int id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return String.format("Program [id=%s]", id);
		}
	}

	private long problemA(List<String> lines) {
		Map<Integer, Program> programs = new HashMap<>();
		Pattern pat = Pattern.compile("(\\d+)");
		int index = 0;
		for (String line : lines) {
			if (!programs.containsKey(index))
				programs.put(index, new Program(index));
			Matcher m = pat.matcher(line.substring(line.indexOf("<->")));
			while (m.find()) {
				int ref = Integer.valueOf(m.group(1));
				if (!programs.containsKey(ref)) {
					programs.put(ref, new Program(ref));
				}
				programs.get(index).refs.add(programs.get(ref));
			}
			index++;
		}

		return programs.values().stream().filter(p -> hasRefTo(p, new ArrayList<>(), 0)).count();
	}

	private boolean hasRefTo(Program program, List<Integer> checked, int refId) {
		if (program.id == refId)
			return true;

		List<Integer> newChecked = new ArrayList<>(checked);
		newChecked.add(program.id);

		for (Program ref : program.refs) {
			if (newChecked.contains(ref.id))
				continue;
			if (hasRefTo(ref, newChecked, refId))
				return true;
		}
		return false;
	}

	private long problemB(List<String> lines) {
		Map<Integer, Program> programs = new HashMap<>();
		Pattern pat = Pattern.compile("(\\d+)");
		int index = 0;
		for (String line : lines) {
			if (!programs.containsKey(index))
				programs.put(index, new Program(index));
			Matcher m = pat.matcher(line.substring(line.indexOf("<->")));
			while (m.find()) {
				int ref = Integer.valueOf(m.group(1));
				if (!programs.containsKey(ref)) {
					programs.put(ref, new Program(ref));
				}
				programs.get(index).refs.add(programs.get(ref));
			}
			index++;
		}

		List<Integer> covered = new ArrayList<>();
		int groups = 0;
		for (int i = 0; i < programs.size(); i++) {
			Program program = programs.get(i);
			if (!covered.contains(program.id)) {
				covered.addAll(programs.values().stream().filter(p -> hasRefTo(p, new ArrayList<>(), program.id))
						.map(p -> p.id).collect(Collectors.toList()));
				groups++;
			}
		}

		return groups;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day12().problemA(TEST_LINES));
		System.out.println("A: " + new Day12().problemA(LINES));
		System.out.println("B: " + new Day12().problemB(LINES));
	}

}
