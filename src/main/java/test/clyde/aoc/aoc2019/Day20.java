package test.clyde.aoc.aoc2019;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import test.clyde.aoc.utils.Point2d;
import test.clyde.aoc.utils.Point3d;
import test.clyde.aoc.utils.Utils;

public class Day20 {
	private static List<String> INPUT = Utils.readFile("2019/Day20.txt");
	private static List<String> TESTA = Utils.readFile("2019/Day20a.txt");
	private static List<String> TESTB = Utils.readFile("2019/Day20b.txt");
	private static List<String> TESTC = Utils.readFile("2019/Day20c.txt");
	private List<String> input;

	private class Maze {
		Point2d start;
		Point2d end;
		Map<Point2d, Point2d> teleports = new HashMap<>();
		Set<Point2d> path = new HashSet<>();
		int minX = 2;
		int minY = 2;
		int maxX;
		int maxY;

		public Maze() {
			loadMap();
		}

		private void loadMap() {
			Map<String, List<Point2d>> teleportsByLabel = new HashMap<>();
			maxY = input.size() - 3;
			maxX = input.get(2).length() - 3;
			for (int y = 1; y < input.size() - 1; y++) {
				String row = input.get(y);
				for (int x = 1; x < row.length() - 1; x++) {
					char c = row.charAt(x);
					if (c == '#' || c == ' ')
						continue;
					Point2d point = new Point2d(x, y);
					if (c == '.')
						path.add(point);
					else if (isCharLetter(c)) {
						char first;
						char second;
						char up = input.get(y - 1).charAt(x);
						char down = input.get(y + 1).charAt(x);
						char left = input.get(y).charAt(x - 1);
						char right = input.get(y).charAt(x + 1);
						if ((up == ' ' && down == ' ' && (left == ' ' || right == ' '))
								|| (left == ' ' && right == ' ' && (up == ' ' || down == ' '))) {
							continue;
						}
						if (isCharLetter(up)) {
							first = up;
							second = c;
						} else if (isCharLetter(down)) {
							first = c;
							second = down;
						} else if (isCharLetter(left)) {
							first = left;
							second = c;
						} else {
							first = c;
							second = right;
						}
						Point2d teleportEntrance;
						if (up == '.')
							teleportEntrance = new Point2d(x, y - 1);
						else if (down == '.')
							teleportEntrance = new Point2d(x, y + 1);
						else if (left == '.')
							teleportEntrance = new Point2d(x - 1, y);
						else
							teleportEntrance = new Point2d(x + 1, y);
						String teleportLabel = Character.toString(first) + Character.toString(second);
						teleportsByLabel.putIfAbsent(teleportLabel, new ArrayList<>());
						teleportsByLabel.get(teleportLabel).add(teleportEntrance);
					}
				}
			}
			teleportsByLabel.values().stream().filter(l -> l.size() == 2).forEach(l -> {
				teleports.put(l.get(0), l.get(1));
				teleports.put(l.get(1), l.get(0));
			});
			start = teleportsByLabel.get("AA").get(0);
			end = teleportsByLabel.get("ZZ").get(0);
		}

		private boolean isCharLetter(char c) {
			return c >= 'A' && c <= 'Z';
		}
	}

	public Day20(List<String> input) {
		this.input = input;
	}

	public long problemA() {
		Maze maze = new Maze();
		Map<Point2d, Integer> been = new HashMap<>();
		Queue<Point2d> moves = new ArrayDeque<>();
		moves.add(maze.start);
		been.put(maze.start, 0);
		while (moves.size() > 0) {
			Point2d currentPoint = moves.remove();
			int moveCount = been.get(currentPoint);
			if (currentPoint.equals(maze.end))
				return moveCount;
			if (maze.teleports.containsKey(currentPoint)) {
				Point2d next = maze.teleports.get(currentPoint);
				if (!been.containsKey(next)) {
					been.put(next, moveCount + 1);
					moves.add(next);
					continue;
				}
			}

			for (Point2d next : currentPoint.getNextDoor()) {
				if (maze.path.contains(next) && !been.containsKey(next)) {
					been.put(next, moveCount + 1);
					moves.add(next);
				}
			}
		}

		return 0;
	}

	public long problemB() {
		Maze maze = new Maze();
		Map<Point3d, Integer> been = new HashMap<>();
		Queue<Point3d> moves = new ArrayDeque<>();
		Point3d start = maze.start.addDimension(0);
		Point3d end = maze.end.addDimension(0);
		moves.add(start);
		been.put(start, 0);
		while (moves.size() > 0) {
			Point3d currentPoint = moves.remove();
			Point2d currentPoint2d = new Point2d(currentPoint.x, currentPoint.y);
			int moveCount = been.get(currentPoint);
			if (currentPoint.equals(end))
				return moveCount;
			if (maze.teleports.containsKey(currentPoint2d)) {
				Point2d telEnd = maze.teleports.get(currentPoint2d);
				int dir = -1;
				if (telEnd.x == maze.minX || telEnd.y == maze.minY || telEnd.x == maze.maxX || telEnd.y == maze.maxY) {
					dir = 1;
				}
				if (currentPoint.z + dir < 0)
					continue;
				Point3d next = telEnd.addDimension(currentPoint.z + dir);
				if (!been.containsKey(next)) {
					been.put(next, moveCount + 1);
					moves.add(next);
					continue;
				}
			}

			for (Point2d next2d : currentPoint2d.getNextDoor()) {
				Point3d next = next2d.addDimension(currentPoint.z);
				if (maze.path.contains(next2d) && !been.containsKey(next)) {
					been.put(next, moveCount + 1);
					moves.add(next);
				}
			}
		}

		return 0;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day20(TESTA).problemA());
		System.out.println("TestB: " + new Day20(TESTB).problemA());
		System.out.println("A: " + new Day20(INPUT).problemA());
		System.out.println("TestA: " + new Day20(TESTA).problemB());
		System.out.println("TestC: " + new Day20(TESTC).problemB());
		System.out.println("B: " + new Day20(INPUT).problemB());
	}
}