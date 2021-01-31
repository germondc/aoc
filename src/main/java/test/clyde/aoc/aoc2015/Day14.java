package test.clyde.aoc.aoc2015;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day14 {

	private static List<String> LINES = Utils.readFile("2015/Day14.txt");
	Pattern PATTERN = Pattern
			.compile("(.*) can fly ([0-9]*) km/s for ([0-9]*) seconds, but then must rest for ([0-9]*) seconds.");

	private class Reindeer {
		String name;
		int totalDistance;
		int flyTime;
		int restTime;

		int maxFlyTime;
		int neededRestTime;
		int speed;
		boolean isResting;
		
		int points;

		public Reindeer(String name, int maxFlyTime, int neededRestTime, int speed) {
			this.name = name;
			this.maxFlyTime = maxFlyTime;
			this.neededRestTime = neededRestTime;
			this.speed = speed;
			this.totalDistance = 0;
			this.flyTime = 0;
			this.restTime = 0;
			this.isResting = false;
			this.points = 0;
		}

		public void bump() {
			if (isResting) {
				restTime++;
				if (restTime >= neededRestTime) {
					restTime = 0;
					isResting = false;
				}
			} else {
				totalDistance += speed;
				flyTime++;
				if (flyTime >= maxFlyTime) {
					flyTime = 0;
					isResting = true;
				}
			}
		}
	}

	private int furtherest(int time, boolean points) {
		List<Reindeer> reindeer = new ArrayList<>();
		for (String line : LINES) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}
			String name = m.group(1);
			int speed = Integer.valueOf(m.group(2));
			int fly = Integer.valueOf(m.group(3));
			int rest = Integer.valueOf(m.group(4));

			reindeer.add(new Reindeer(name, fly, rest, speed));
		}

		for (int i = 0; i < time; i++) {
			for (Reindeer r : reindeer) {
				r.bump();
			}
			int mostDistance = reindeer.stream().map(r -> r.totalDistance).mapToInt(d -> d).max().getAsInt();
			reindeer.stream().filter(r -> r.totalDistance==mostDistance).forEach(r -> r.points++);
		}
		
		int fur = 0;
		int mostPoints = 0;
		for (Reindeer r : reindeer) {
			if (r.totalDistance > fur)
				fur = r.totalDistance;
			if (r.points > mostPoints)
				mostPoints = r.points;
		}
		return points ? mostPoints : fur;
	}
	
	private void fur() {
		List<Reindeer> reindeer = new ArrayList<>();
		reindeer.add(new Reindeer("Comet", 10, 127, 14));
		reindeer.add(new Reindeer("Dancer", 11, 162, 16));
		for (int i = 0; i < 1000; i++) {
			for (Reindeer r : reindeer) {
				r.bump();
			}
			int mostDistance = reindeer.stream().map(r -> r.totalDistance).mapToInt(d -> d).max().getAsInt();
			reindeer.stream().filter(r -> r.totalDistance==mostDistance).forEach(r -> r.points++);
		}
		for (Reindeer r : reindeer) {
			System.out.println(r.points);
		}
	}

	public static void main(String[] argv) {
		//new Day14().fur();
		System.out.println("A: " + new Day14().furtherest(2503, false));
		System.out.println("B: " + new Day14().furtherest(2503, true));
		// System.out.println("B: " + new Day14().bestHappiness());
	}
}
