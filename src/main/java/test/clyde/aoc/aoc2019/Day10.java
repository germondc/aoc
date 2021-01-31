package test.clyde.aoc.aoc2019;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Point2d;
import test.clyde.aoc.utils.Utils;

public class Day10 {
	private static List<String> INPUT = Utils.readFile("2019/Day10.txt");
	private static List<String> TESTA = Utils.readFile("2019/Day10a.txt");
	private static List<String> TESTB = Utils.readFile("2019/Day10b.txt");
	private static List<String> TESTC = Utils.readFile("2019/Day10c.txt");

	private long problemA(List<String> input) {
		List<Point2d> asteroids = new ArrayList<>();
		Set<Point2d> lookups = new HashSet<>();
		{
			int y = 0;
			for (String line : input) {
				int x = 0;
				for (char c : line.toCharArray()) {
					if (c == '#') {
						Point2d p = new Point2d(x, y);
						asteroids.add(p);
						lookups.add(p);
					}
					x++;
				}
				y++;
			}
		}

		Map<Point2d, Integer> losCount = new HashMap<>();

		for (Point2d asteroid : asteroids) {
			losCount.put(asteroid, 0);
			for (Point2d other : asteroids) {
				if (hasLOS(asteroid, other, lookups))
					losCount.put(asteroid, losCount.get(asteroid) + 1);
			}
		}

		return losCount.entrySet().stream().mapToInt(e -> e.getValue()).max().getAsInt();
	}

	private long problemB(List<String> input) {
		List<Point2d> asteroids = new ArrayList<>();
		Set<Point2d> lookups = new HashSet<>();
		{
			int y = 0;
			for (String line : input) {
				int x = 0;
				for (char c : line.toCharArray()) {
					if (c == '#') {
						Point2d p = new Point2d(x, y);
						asteroids.add(p);
						lookups.add(p);
					}
					x++;
				}
				y++;
			}
		}

		Map<Point2d, Integer> losCount = new HashMap<>();

		for (Point2d asteroid : asteroids) {
			losCount.put(asteroid, 0);
			for (Point2d other : asteroids) {
				if (hasLOS(asteroid, other, lookups))
					losCount.put(asteroid, losCount.get(asteroid) + 1);
			}
		}

		Point2d station = losCount.entrySet().stream()
				.filter(e -> e.getValue() == losCount.values().stream().mapToInt(i -> i).max().getAsInt())
				.map(e -> e.getKey()).findFirst().get();

		int count = 0;
		int index = 0;
		while (true) {
			List<Point2d> asteroidsInQuad = getQuad(station, (index + 1) % 4, lookups);
			Iterator<Point2d> it = asteroidsInQuad.iterator();
			while (it.hasNext()) {
				Point2d p = it.next();
				if (count == 199) {
					return p.x * 100 + p.y;
					//System.out.println(p);
				}
				lookups.remove(p);
				count++;
			}
			index++;
		}
	}

	private List<Point2d> getQuad(Point2d base, int quad, Set<Point2d> asteroids) {
		long baseX = base.x;
		long baseY = base.y;
		long biggestX = asteroids.stream().mapToLong(p -> p.x).max().getAsLong();
		long biggestY = asteroids.stream().mapToLong(p -> p.y).max().getAsLong();
		long minX;
		long maxX;
		long minY;
		long maxY;
		Comparator<Double> comparator;
		if (quad == 0) {
			minX = 0;
			maxX = baseX - 1;
			minY = 0;
			maxY = baseY - 1;
			comparator = (Double d1, Double d2) -> d1.compareTo(d2);
		} else if (quad == 1) {
			minX = baseX;
			minY = 0;
			maxX = biggestX;
			maxY = baseY;
			comparator = (Double d1, Double d2) -> d1.compareTo(d2);
		} else if (quad == 2) {
			minX = baseX;
			minY = baseY + 1;
			maxX = biggestX;
			maxY = biggestY;
			comparator = (Double d1, Double d2) -> d1.compareTo(d2);
		} else {
			minX = 0;
			minY = baseY;
			maxX = baseX - 1;
			maxY = biggestY;
			comparator = (Double d1, Double d2) -> d1.compareTo(d2);

		}
		Map<Double, Point2d> gradients = new HashMap<>();
		for (long y = minY; y <= maxY; y++) {
			for (long x = minX; x <= maxX; x++) {
				if (x == baseX && y == baseY)
					continue;
				Point2d p = new Point2d(x, y);
				if (asteroids.contains(p) && hasLOS(base, p, asteroids)) {
					double m;
					if (base.x == p.x) {
						m = Double.MAX_VALUE;
						if (quad == 1 || quad == 3)
							m *= -1;
					} else {
						m = (double) (base.y - p.y) / (double) (base.x - p.x);
					}
					gradients.put(m, p);
				}
			}
		}
		return gradients.entrySet().stream().sorted(Map.Entry.<Double, Point2d>comparingByKey(comparator))
				.map(e -> e.getValue()).collect(Collectors.toList());
	}

	private boolean hasLOS(Point2d asteroid, Point2d other, Set<Point2d> lookups) {
		if (other == asteroid)
			return false;
		if (asteroid.x == other.x) {
			for (long y = Math.min(asteroid.y, other.y) + 1; y < Math.max(asteroid.y, other.y); y++) {
				Point2d lookup = new Point2d(asteroid.x, y);
				if (lookups.contains(lookup)) {
					return false;
				}
			}
		} else {
			double m = (double) (asteroid.y - other.y) / (double) (asteroid.x - other.x);
			double c = (double) asteroid.y - m * (double) asteroid.x;
			for (long x = Math.min(asteroid.x, other.x) + 1; x < Math.max(asteroid.x, other.x); x++) {
				double y = m * x + c;
				long roundedY = Math.round(y);
				if (Math.abs(y - roundedY) > 0.00000001) {
					continue;
				}
				Point2d lookup = new Point2d(x, roundedY);
				if (lookups.contains(lookup)) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day10().problemA(TESTA));
		System.out.println("TestB: " + new Day10().problemA(TESTB));
		System.out.println("TestC: " + new Day10().problemA(TESTC));
		System.out.println("A: " + new Day10().problemA(INPUT));
		System.out.println("TestB: " + new Day10().problemB(TESTC));
		System.out.println("B: " + new Day10().problemB(INPUT));
	}

}
