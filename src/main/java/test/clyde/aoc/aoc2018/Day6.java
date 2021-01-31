package test.clyde.aoc.aoc2018;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Distances;
import test.clyde.aoc.utils.Utils;

public class Day6 {
	private static List<String> LINES = Utils.readFile("2018/Day6.txt");
	private static List<String> TEST = Utils.readFile("2018/Day6a.txt");

	private long problemA(List<String> lines) {
		List<Point> points = new ArrayList<>();
		for (String line : lines) {
			String[] split = line.split(", ");
			points.add(new Point(Integer.valueOf(split[0]), Integer.valueOf(split[1])));
		}

		int minX = points.stream().mapToInt(p -> p.x).min().getAsInt();
		int maxX = points.stream().mapToInt(p -> p.x).max().getAsInt();
		int minY = points.stream().mapToInt(p -> p.y).min().getAsInt();
		int maxY = points.stream().mapToInt(p -> p.y).max().getAsInt();

		Map<Point, Point> closestPoints = new HashMap<>();
		List<Point> unbounded = new ArrayList<>();
		for (int y = minY - 2; y <= maxY + 2; y++) {
			for (int x = minX - 2; x <= maxX + 2; x++) {
				Point p = new Point(x, y);
				Map<Point, Integer> distances = new HashMap<>();
				for (Point point : points) {
					int distance = Distances.getManhattanDistance(p, point);
					distances.put(point, distance);
				}
				Map.Entry<Point, Integer> minEntry = distances.entrySet().stream().min(Map.Entry.comparingByValue())
						.get();
				if (distances.values().stream().filter(v -> v == minEntry.getValue()).count() == 1) {
					closestPoints.put(p, minEntry.getKey());
					if (!unbounded.contains(minEntry.getKey()) && (y == minY || y == maxY || x == minX || x == maxX)) {
						unbounded.add(minEntry.getKey());
					}
				}
			}
		}

		Map<Point, Long> boundCounts = closestPoints.values().stream().filter(p -> !unbounded.contains(p))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		return boundCounts.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
	}

	private long problemB(List<String> lines, int size) {
		List<Point> points = new ArrayList<>();
		for (String line : lines) {
			String[] split = line.split(", ");
			points.add(new Point(Integer.valueOf(split[0]), Integer.valueOf(split[1])));
		}

		int minX = points.stream().mapToInt(p -> p.x).min().getAsInt();
		int maxX = points.stream().mapToInt(p -> p.x).max().getAsInt();
		int minY = points.stream().mapToInt(p -> p.y).min().getAsInt();
		int maxY = points.stream().mapToInt(p -> p.y).max().getAsInt();

		List<Point> region = new ArrayList<>();
		for (int y = minY - 2; y <= maxY + 2; y++) {
			for (int x = minX - 2; x <= maxX + 2; x++) {
				Point p = new Point(x, y);
				Map<Point, Integer> distances = new HashMap<>();
				for (Point point : points) {
					int distance = Distances.getManhattanDistance(p, point);
					distances.put(point, distance);
				}
				int sum = distances.values().stream().mapToInt(i -> i).sum();
				if (sum < size) {
					region.add(p);
				}
			}
		}
		return region.size();
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day6().problemA(TEST));
		System.out.println("A: " + new Day6().problemA(LINES));
		System.out.println("TestB: " + new Day6().problemB(TEST, 32));
		System.out.println("B: " + new Day6().problemB(LINES, 10000));
	}

}
