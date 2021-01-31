package test.clyde.aoc.aoc2018;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day3 {
	private static List<String> LINES = Utils.readFile("2018/Day3.txt");
	private static List<String> TEST = Utils.readFile("2018/Day3a.txt");

	private class Claim {
		int id;
		int left;
		int top;
		int width;
		int height;

		public Claim(int id, int left, int top, int width, int height) {
			super();
			this.id = id;
			this.left = left;
			this.top = top;
			this.width = width;
			this.height = height;
		}
	}

	private long problemA(List<String> lines) {
		List<Claim> claims = new ArrayList<>();
		Pattern pat = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");
		for (String line : lines) {

			Matcher m = pat.matcher(line);
			if (m.find()) {
				int id = Integer.valueOf(m.group(1));
				int left = Integer.valueOf(m.group(2));
				int top = Integer.valueOf(m.group(3));
				int width = Integer.valueOf(m.group(4));
				int height = Integer.valueOf(m.group(5));
				Claim claim = new Claim(id, left, top, width, height);
				claims.add(claim);
			}
		}

		Map<Point, Integer> pointCounts = new HashMap<>();
		for (Claim claim : claims) {
			for (int row = claim.top; row < claim.top + claim.height; row++) {
				for (int col = claim.left; col < claim.left + claim.width; col++) {
					Point p = new Point(col, row);
					pointCounts.putIfAbsent(p, 0);
					pointCounts.put(p, pointCounts.get(p) + 1);
				}
			}
		}

		return pointCounts.values().stream().filter(i -> i > 1).count();
	}

	private long problemB(List<String> lines) {
		List<Claim> claims = new ArrayList<>();
		Pattern pat = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");
		for (String line : lines) {

			Matcher m = pat.matcher(line);
			if (m.find()) {
				int id = Integer.valueOf(m.group(1));
				int left = Integer.valueOf(m.group(2));
				int top = Integer.valueOf(m.group(3));
				int width = Integer.valueOf(m.group(4));
				int height = Integer.valueOf(m.group(5));
				Claim claim = new Claim(id, left, top, width, height);
				claims.add(claim);
			}
		}

		Map<Point, List<Claim>> pointCounts = new HashMap<>();
		for (Claim claim : claims) {
			for (int row = claim.top; row < claim.top + claim.height; row++) {
				for (int col = claim.left; col < claim.left + claim.width; col++) {
					Point p = new Point(col, row);
					pointCounts.putIfAbsent(p, new ArrayList<>());
					pointCounts.get(p).add(claim);
				}
			}
		}

		Set<Claim> overlapping = pointCounts.values().stream().filter(l -> l.size() > 1).flatMap(l -> l.stream())
				.collect(Collectors.toSet());

		claims.removeAll(overlapping);
		
		return claims.get(0).id;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day3().problemA(TEST));
		System.out.println("A: " + new Day3().problemA(LINES));
		System.out.println("TestB: " + new Day3().problemB(TEST));
		System.out.println("B: " + new Day3().problemB(LINES));
	}

}
