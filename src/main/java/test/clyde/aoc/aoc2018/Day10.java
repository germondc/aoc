package test.clyde.aoc.aoc2018;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day10 {

	private static List<String> LINES = Utils.readFile("2018/Day10.txt");
	private static List<String> TEST = Utils.readFile("2018/Day10a.txt");

	private class APoint {
		Point position;
		Point velocity;

		public APoint(Point position, Point velocity) {
			super();
			this.position = position;
			this.velocity = velocity;
		}
	}

	private long problemA(List<String> lines) {
		List<APoint> points = getThePoints(lines);
		long count=1;
		while (true) {
			for (APoint apoint : points) {
				apoint.position.x += apoint.velocity.x;
				apoint.position.y += apoint.velocity.y;
			}
			
			if (drawPoints(points))
				break;
			count++;
		}
		return count;
	}

	private long problemB(List<String> lines) {
		return 0;
	}
	
	private boolean drawPoints (List<APoint> points) {
		Set<Point> list = points.stream().map(a -> a.position).collect(Collectors.toCollection(HashSet::new));
		int minX = list.stream().mapToInt(p -> p.x).min().getAsInt();
		int maxX = list.stream().mapToInt(p -> p.x).max().getAsInt();
		int minY = list.stream().mapToInt(p -> p.y).min().getAsInt();
		int maxY = list.stream().mapToInt(p -> p.y).max().getAsInt();
		if (!(maxX-minX < 100 && maxY-minY<15))
			return false;
		for (int y = minY; y<=maxY; y++) {
			for (int x = minX; x<=maxX; x++) {
				Point p = new Point(x,y);
				if (list.contains(p)) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
		System.out.println();
		return true;
	}

	private List<APoint> getThePoints(List<String> lines) {
		List<APoint> result = new ArrayList<>();
		Pattern pat = Pattern.compile("position=< *(-?\\d+), *(-?\\d+)> velocity=< *(-?\\d+), *(-?\\d+)>");
		for (String line : lines) {
			Matcher m = pat.matcher(line);
			if (m.find()) {
				result.add(new APoint(new Point(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2))),
						new Point(Integer.valueOf(m.group(3)), Integer.valueOf(m.group(4)))));
			}
		}
		return result;
	}

	private void pressAnyKeyToContinue() {
		System.out.println("Press Enter key to continue...");
		try {
			System.in.read();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
//		System.out.println("TestA: " + new Day10().problemA(TEST));
		System.out.println("B: " + new Day10().problemA(LINES));
	}

}
