package test.clyde.aoc.aoc2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day4 {
	private static List<String> LINES = Utils.readFile("2018/Day4.txt");
	private static List<String> TEST = Utils.readFile("2018/Day4a.txt");

	private class Record {
		String datestamp;
		int hour;
		int minute;
		String action;
		int guard;

		public Record(String datestamp, int hour, int minute, String action) {
			super();
			this.datestamp = datestamp;
			this.hour = hour;
			this.minute = minute;
			this.action = action;
		}
	}

	private long problemA(List<String> lines) {
		List<Record> records = new ArrayList<>();
		Pattern pat = Pattern.compile("\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)\\] (.*)$");
		for (String line : lines) {
			Matcher m = pat.matcher(line);
			if (m.find()) {
				String date = m.group(1) + m.group(2) + m.group(3) + m.group(4) + m.group(5);
				int hour = Integer.valueOf(m.group(4));
				int minute = Integer.valueOf(m.group(5));
				String action = m.group(6);
				records.add(new Record(date, hour, minute, action));
			}
		}

		Collections.sort(records, new Comparator<Record>() {

			@Override
			public int compare(Record o1, Record o2) {

				return o1.datestamp.compareTo(o2.datestamp);
			}
		});

		int currentGuard = 0;
		Map<Integer, Map<Integer, Integer>> sleepingMinutes = new HashMap<>();
		int sleepTime = 0;
		for (Record record : records) {
			if (record.action.contains("begins shift")) {
				currentGuard = Integer.valueOf(record.action.substring(record.action.indexOf('#') + 1,
						record.action.indexOf("begins shift") - 1));
				sleepingMinutes.putIfAbsent(currentGuard, new HashMap<>());
			} else if (record.action.contains("falls asleep")) {
				sleepTime = record.hour * 60 + record.minute;
			} else if (record.action.contains("wakes up")) {
				Map<Integer, Integer> minutes = sleepingMinutes.get(currentGuard);
				for (int t = sleepTime; t < (record.hour * 60 + record.minute); t++) {
					minutes.putIfAbsent(t, 0);
					minutes.put(t, minutes.get(t) + 1);
				}
			}
			record.guard = currentGuard;
		}

		Map<Integer, Integer> mapTo = sleepingMinutes.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().values().stream().mapToInt(i -> i).sum()));
		int guardAsleepMost = mapTo.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
		int minute = sleepingMinutes.get(guardAsleepMost).entrySet().stream().max(Map.Entry.comparingByValue()).get()
				.getKey();

		return guardAsleepMost * minute;
	}

	private long problemB(List<String> lines) {
		List<Record> records = new ArrayList<>();
		Pattern pat = Pattern.compile("\\[(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+)\\] (.*)$");
		for (String line : lines) {
			Matcher m = pat.matcher(line);
			if (m.find()) {
				String date = m.group(1) + m.group(2) + m.group(3) + m.group(4) + m.group(5);
				int hour = Integer.valueOf(m.group(4));
				int minute = Integer.valueOf(m.group(5));
				String action = m.group(6);
				records.add(new Record(date, hour, minute, action));
			}
		}

		Collections.sort(records, new Comparator<Record>() {

			@Override
			public int compare(Record o1, Record o2) {

				return o1.datestamp.compareTo(o2.datestamp);
			}
		});

		int currentGuard = 0;
		Map<Integer, Map<Integer, Integer>> sleepingMinutes = new HashMap<>();
		int sleepTime = 0;
		for (Record record : records) {
			if (record.action.contains("begins shift")) {
				currentGuard = Integer.valueOf(record.action.substring(record.action.indexOf('#') + 1,
						record.action.indexOf("begins shift") - 1));
				sleepingMinutes.putIfAbsent(currentGuard, new HashMap<>());
			} else if (record.action.contains("falls asleep")) {
				sleepTime = record.hour * 60 + record.minute;
			} else if (record.action.contains("wakes up")) {
				Map<Integer, Integer> minutes = sleepingMinutes.get(currentGuard);
				for (int t = sleepTime; t < (record.hour * 60 + record.minute); t++) {
					minutes.putIfAbsent(t, 0);
					minutes.put(t, minutes.get(t) + 1);
				}
			}
			record.guard = currentGuard;
		}
		
		int maxMinute = 0;
		int maxAmount = 0;
		int maxGuard = 0;
		for (Map.Entry<Integer, Map<Integer, Integer>> entry : sleepingMinutes.entrySet()) {
			if (entry.getValue().size() == 0)
				continue;
			Map.Entry<Integer, Integer> maxEntry = entry.getValue().entrySet().stream().max(Map.Entry.comparingByValue()).get();
			if (maxEntry.getValue() > maxAmount) {
				maxAmount = maxEntry.getValue();
				maxMinute = maxEntry.getKey();
				maxGuard = entry.getKey();
			}
				
		}
		
		return maxGuard*maxMinute;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day4().problemA(TEST));
		System.out.println("A: " + new Day4().problemA(LINES));
		System.out.println("TestB: " + new Day4().problemB(TEST));
		System.out.println("B: " + new Day4().problemB(LINES));
	}

}
