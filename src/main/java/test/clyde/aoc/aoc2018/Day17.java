package test.clyde.aoc.aoc2018;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day17 {
	private static List<String> LINES = Utils.readFile("2018/Day17.txt");
	private static List<String> TEST = Utils.readFile("2018/Day17a.txt");

	private List<Point> getClay(List<String> lines) {
		List<Point> result = new ArrayList<>();
		Pattern pat = Pattern.compile("(.)=(.+), (.)=(.+)");
		for (String line : lines) {
			Matcher m = pat.matcher(line);
			if (m.find()) {
				Map<String, String> parts = new HashMap<>();
				parts.put(m.group(1), m.group(2));
				parts.put(m.group(3), m.group(4));
				List<Integer> xParts = getParts(parts.get("x"));
				List<Integer> yParts = getParts(parts.get("y"));
				for (int x = xParts.get(0); x <= xParts.get(1); x++) {
					for (int y = yParts.get(0); y <= yParts.get(1); y++) {
						Point p = new Point(x, y);
						if (!result.contains(p))
							result.add(p);
					}
				}
			}
		}
		return result;
	}

	private List<Integer> getParts(String part) {
		List<Integer> result = new ArrayList<>();
		String[] split = part.split("\\.\\.");
		for (String s : split) {
			result.add(Integer.valueOf(s));
		}
		if (result.size() == 1)
			result.add(result.get(0));
		return result;
	}

	private void print(List<Point> points) {
		int minX = points.stream().mapToInt(p -> p.x).min().getAsInt();
		int maxX = points.stream().mapToInt(p -> p.x).max().getAsInt();
		int minY = points.stream().mapToInt(p -> p.y).min().getAsInt();
		int maxY = points.stream().mapToInt(p -> p.y).max().getAsInt();
		print(points, minX, maxX, minY, maxY);
	}
	
	private void print(List<Point> points, int minX, int maxX, int minY, int maxY) {
		System.out.println(minX + "," + maxX + " -> " + minY + "," + maxY);
		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x <= maxX; x++) {
				Point p = new Point(x, y);
				if (points.contains(p))
					System.out.print('#');
				else
					System.out.print('.');
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private long problemA(List<String> lines) {
		List<Point> clay = getClay(lines);
		Queue<Point> queue = new ArrayDeque<>();
		queue.add(new Point(500,0));
		Map<Point, Boolean> waterBeen = new HashMap<>();
		int maxY = clay.stream().mapToInt(p -> p.y).max().getAsInt();
		int minY = clay.stream().mapToInt(p -> p.y).min().getAsInt();
		
		Set<Point> previous = new HashSet<>();
		while (queue.size() > 0) {
			Point p = queue.remove();
			processStream(p, waterBeen, clay, queue, maxY, previous);
		}
		return waterBeen.keySet().stream().filter(p -> p.y>=minY && p.y<=maxY).count();
	}
	
	private Set<Point> waterReserve = new HashSet<>();
	
	private void processStream(Point start, Map<Point, Boolean> waterBeen, List<Point> clay, Queue<Point> currentStreams, int maxY, Set<Point> previous) {
		int index = start.y;
		while (true) {
			if (index > maxY)
				return;
			Point currentPoint = new Point(start.x, index);
			if (clay.contains(currentPoint)) {				
				currentPoint = new Point(start.x, --index);
				Point left = processLeftRight(currentPoint, waterBeen, clay, true, currentStreams, previous);
				Point right = processLeftRight(currentPoint, waterBeen, clay, false, currentStreams, previous);
				if (left != null && right != null) {
					for (int x=left.x; x<=right.x; x++) {
						clay.add(new Point(x, left.y));
						waterReserve.add(new Point(x, left.y));
					}
					continue;
				} else {
					return;
				}
			} else {
				waterBeen.put(currentPoint, true);
			}
			index++;
		}
	}
	
	private Point processLeftRight(Point start, Map<Point, Boolean> waterBeen, List<Point> clay, boolean isLeft, Queue<Point> streams, Set<Point> previous) {
		int index = start.x;
		int adj = isLeft ? -1 : 1;
		Point previousPoint = start;
		while (true) {
			Point currentPoint = new Point(index, start.y);
			if (clay.contains(currentPoint)) {
				return previousPoint;
			} else {
				waterBeen.put(currentPoint, true);
			}
			Point belowPoint = new Point(index, start.y+1);
			if (!clay.contains(belowPoint)) {
				if (!previous.contains(currentPoint)) {
					streams.add(currentPoint);
					previous.add(currentPoint);
				}
				return null;
			}
			previousPoint = currentPoint;
			index += adj;
		}
	}

	private long problemB(List<String> lines) {
		return waterReserve.size();
	}

	public static void main(String[] args) {
		Day17 test = new Day17();
		System.out.println("TestA: " + test.problemA(TEST));
		System.out.println("TestB: " + test.problemB(TEST));
		Day17 d = new Day17();
		System.out.println("A: " + d.problemA(LINES));
		System.out.println("B: " + d.problemB(LINES));
	}

}
