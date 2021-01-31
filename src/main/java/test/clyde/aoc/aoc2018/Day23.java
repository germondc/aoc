package test.clyde.aoc.aoc2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Point3d;
import test.clyde.aoc.utils.Utils;

public class Day23 {
	private static List<String> LINES = Utils.readFile("2018/Day23.txt");
	private static List<String> TEST = Utils.readFile("2018/Day23a.txt");
	private static List<String> TESTB = Utils.readFile("2018/Day23b.txt");

	private static final Point3d origin = new Point3d();

	private class Nanobot {
		Point3d location;
		long radius;
		private long distanceFromOrigin = -1;

		public long getDistanceFromOrigin() {
			if (distanceFromOrigin == -1)
				distanceFromOrigin = location.getManhattanDistance(origin);
			return distanceFromOrigin;
		}
	}

	private List<Nanobot> getBots(List<String> lines) {
		List<Nanobot> bots = new ArrayList<>();
		Pattern pat = Pattern.compile("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(-?\\d+)");
		for (String line : lines) {
			Matcher m = pat.matcher(line);
			if (m.find()) {
				int x = Integer.valueOf(m.group(1));
				int y = Integer.valueOf(m.group(2));
				int z = Integer.valueOf(m.group(3));
				int radius = Integer.valueOf(m.group(4));
				Nanobot n = new Nanobot();
				n.location = new Point3d(x, y, z);
				n.radius = radius;
				bots.add(n);
			}
		}
		return bots;
	}

	private long problemA(List<String> lines) {
		List<Nanobot> bots = getBots(lines);

		long maxRadius = bots.stream().mapToLong(b -> b.radius).max().getAsLong();
		List<Nanobot> strongest = bots.stream().filter(b -> b.radius == maxRadius).collect(Collectors.toList());

		Nanobot str = strongest.get(0);
		long count = bots.stream().filter(b -> b.location.getManhattanDistance(str.location) <= str.radius).count();
		return count;
	}

	/**
	 * Essentially this is counting the number of nanobots at each closest and farthest 
	 * distance from the origin. This is the example:
	 * {32=3, 48=2, -50=1, 35=3, 36=5, 40=4, 25=2, 44=3, 350=1}
	 * The map contains the number at each, clearly 36 is the best which is the answer
	 * While this works it makes a pretty big assumption that there is only a single
	 * position, which would have the result exactly on the vertex. If there is more than one
	 * position then the result could be on the be somewhere in the overlap between two   
	 * -50           25       30    32      36     40      44       48              350
	 * --|-----------|--------|-----|-------|------|-------|--------|----------------|---
	 *                              |---1---|
	 *                                      |---2--|
	 *                                      |-------3------|
	 *                                      |------------4----------|
	 *   |----------------------------------5----------------------------------------|
	 *               |---------6--------|
	 */
	private long problemB(List<String> lines) {
		List<Nanobot> bots = getBots(lines);
		Map<Long, Integer> countAtDistance = new HashMap<>();
		for (Nanobot n1 : bots) {

			for (long distance : new long[] { n1.getDistanceFromOrigin() - n1.radius,
					n1.getDistanceFromOrigin() + n1.radius }) {
				if (!countAtDistance.containsKey(distance)) {
					int count = 0;
					for (Nanobot n2 : bots) {
						if (n2.getDistanceFromOrigin() - n2.radius <= distance
								&& n2.getDistanceFromOrigin() + n2.radius >= distance)
							count++;
					}
					countAtDistance.put(distance, count);
				}
			}
		}

		int most = countAtDistance.values().stream().mapToInt(i -> i).max().getAsInt();
		long best = countAtDistance.entrySet().stream().filter(e -> e.getValue() == most).map(e -> e.getKey())
				.findFirst().get();

		return best;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("TestA: " + new Day23().problemA(TEST));
		System.out.println("A: " + new Day23().problemA(LINES));
		System.out.println("TestB: " + new Day23().problemB(TESTB));
		System.out.println("B: " + new Day23().problemB(LINES));
	}

}
