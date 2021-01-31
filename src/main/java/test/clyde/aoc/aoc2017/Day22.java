package test.clyde.aoc.aoc2017;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day22 {

	private static List<String> LINES = Utils.readFile("2017/Day22.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day22a.txt");
	
	private Map<Point, Character> getMap(List<String> lines) {
		Map<Point, Character> result = new HashMap<>();
		int rowIndex = -lines.size() / 2;
		for (String line : lines) {
			int colIndex = -line.length() / 2;
			for (char c : line.toCharArray()) {
				if (c == '#') {
					Point p = new Point(colIndex, rowIndex);
					result.put(p, '#');
				}
				colIndex++;
			}
			rowIndex++;
		}
		return result;
	}

	private long problemA(List<String> lines, int bursts) {
		Map<Point, Character> currentMap = getMap(lines);
		Point current = new Point(0, 0);
		Point direction = new Point(0, -1);
		int count = 0;
		for (int burst = 0; burst < bursts; burst++) {
			if (currentMap.containsKey(current) && currentMap.get(current)=='#') {
				direction = changeDirection(direction, 1);
				currentMap.remove(current);
			} else {
				direction = changeDirection(direction, -1);
				currentMap.put(new Point(current.x, current.y), '#');
				count++;
			}
			current.x += direction.x;
			current.y += direction.y;
			
		}
		return count;
	}
	
	private long problemB(List<String> lines, int bursts) {
		Map<Point, Character> currentMap = getMap(lines);

		Point current = new Point(0, 0);
		Point direction = new Point(0, -1);
		long count = 0;
		for (int burst = 0; burst < bursts; burst++) {
			if (currentMap.containsKey(current)) {
				char state = currentMap.get(current);
				if (state == 'W') {
					currentMap.put(new Point(current.x, current.y), '#');
					count++;
				} else if (state == '#') {
					direction = changeDirection(direction, 1);
					currentMap.put(new Point(current.x, current.y), 'F');
				} else {
					direction = changeDirection(direction, 0);
					currentMap.remove(current);
				}
			} else {
				direction = changeDirection(direction, -1);
				currentMap.put(new Point(current.x, current.y), 'W');
			}
				
			current.x += direction.x;
			current.y += direction.y;
			
		}
		return count;
	}

	private static final Point[] DIRECTIONS = new Point[] { new Point(-1, 0), new Point(0, -1), new Point(1, 0),
			new Point(0, 1) };

	private Point changeDirection(Point currentDirection, int change) {
		int index = 2;
		if (change < 0) {
			index = -1;
		} else if (change>0) {
			index = 1;
		}
		
		for (int i=0; i<DIRECTIONS.length; i++) {
			if (DIRECTIONS[i].equals(currentDirection)) {
				int adj = i + index;
				while(adj<0)
					adj += 4;
				while(adj>=DIRECTIONS.length)
					adj -= 4;
				return DIRECTIONS[adj];
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day22().problemA(TEST_LINES, 70));
		System.out.println("TestA: " + new Day22().problemA(TEST_LINES, 10000));
		System.out.println("A: " + new Day22().problemA(LINES, 10000));
		System.out.println("TestB: " + new Day22().problemB(TEST_LINES, 100));
		System.out.println("TestB: " + new Day22().problemB(TEST_LINES, 10000000));
		System.out.println("B: " + new Day22().problemB(LINES, 10000000));
	}

}
