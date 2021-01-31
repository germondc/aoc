package test.clyde.aoc.aoc2018;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import test.clyde.aoc.utils.Point4d;
import test.clyde.aoc.utils.Utils;

public class Day25 {
	private static List<String> LINES = Utils.readFile("2018/Day25.txt");
	private static List<String> TEST = Utils.readFile("2018/Day25a.txt");
	private static List<String> TESTB = Utils.readFile("2018/Day25b.txt");
	private static List<String> TESTC = Utils.readFile("2018/Day25c.txt");

	private long problemA(List<String> lines) {
		List<Point4d> points = new ArrayList<>();
		for (String line : lines) {
			points.add(new Point4d(line.split(",")));
		}

		int count = 0;
		Map<Integer, List<Point4d>> constellations = new HashMap<>();
		List<Point4d> remaining = new ArrayList<>(points);

		while (remaining.size() > 0) {
			Point4d current = remaining.get(0);
			remaining.remove(0);
			constellations.putIfAbsent(count, new ArrayList<>());
			constellations.get(count).add(current);

			int usedCount = 1;
			Iterator<Point4d> it;
			while (usedCount > 0) {
				usedCount = 0;
				it = remaining.iterator();
				search: while (it.hasNext()) {
					Point4d nextP = it.next();
					for (Point4d p : constellations.get(count)) {
						if (p.getManhattanDistance(nextP) <= 3) {
							constellations.get(count).add(nextP);
							it.remove();
							usedCount++;
							continue search;
						}
					}
					
					
				}
			}
			count++;
		}

		return constellations.size();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("TestA: " + new Day25().problemA(TEST));
		System.out.println("TestA: " + new Day25().problemA(TESTB));
		System.out.println("TestA: " + new Day25().problemA(TESTC));
		System.out.println("A: " + new Day25().problemA(LINES));
	}

}
