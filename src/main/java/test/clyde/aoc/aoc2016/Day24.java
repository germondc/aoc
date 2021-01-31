package test.clyde.aoc.aoc2016;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import test.clyde.aoc.utils.Utils;

public class Day24 {

	private static List<String> LINES = Utils.readFile("2016/Day24.txt");
	// private static List<String> LINES = Utils.readFile("2016/Day24a.txt");

	private enum MapItem {
		wall, passage, start, poi1, poi2, poi3, poi4, poi5, poi6, poi7, poi8, poi9;

		public static MapItem getPoi(int i) {
			return valueOf("poi" + i);
		}

		public boolean isPoi() {
			return name().startsWith("poi");
		}

		public int getPoi() {
			return Integer.valueOf(name().substring(name().length() - 1));
		}
	}

	private class Move extends Point {
		int moveCount;
		List<Integer> pois;

		public Move(int x, int y, int moveCount, List<Integer> pois) {
			super(x, y);
			this.moveCount = moveCount;
			this.pois = pois;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + pois.stream().mapToInt(i -> Integer.hashCode(i)).sum();
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (getClass() != obj.getClass())
				return false;
			Move other = (Move) obj;
			if (pois.size() != other.pois.size())
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			for (int i = 0; i < pois.size(); i++) {
				if (!other.pois.contains(pois.get(i)))
					return false;
			}
			return true;
		}
	}
	
	private long problemB(List<String> lines) {
		MapItem[][] map = getMap(lines);
		Point start = getStart(map);
		int goal = countPoi(map);
		been.add(new Move(start.x, start.y, 0, new ArrayList<>()));
		Queue<Move> moveQueue = new ArrayDeque<>();
		moveQueue.addAll(getPossibleMoves(map, start, 0, new ArrayList<>(), goal));
		while (moveQueue.size() > 0) {
			Move nextMove = moveQueue.remove();
			if (nextMove.pois.size() == goal+1 && nextMove.pois.get(goal)==0) {
				return nextMove.moveCount;
			}
			moveQueue.addAll(getPossibleMoves(map, nextMove.getLocation(), nextMove.moveCount, nextMove.pois, goal));
		}
		return -1;
	}

	private long problemA(List<String> lines) {
		MapItem[][] map = getMap(lines);
		Point start = getStart(map);
		int goal = countPoi(map);
		been.add(new Move(start.x, start.y, 0, new ArrayList<>()));
		Queue<Move> moveQueue = new ArrayDeque<>();
		moveQueue.addAll(getPossibleMoves(map, start, 0, new ArrayList<>(), goal));
		while (moveQueue.size() > 0) {
			Move nextMove = moveQueue.remove();
			if (nextMove.pois.size() == goal) {
				return nextMove.moveCount;
			}
			moveQueue.addAll(getPossibleMoves(map, nextMove.getLocation(), nextMove.moveCount, nextMove.pois, goal));
		}
		return -1;
	}

	private Set<Move> been = new HashSet<>();

	private MapItem[][] getMap(List<String> lines) {
		int maxX = lines.get(0).length();
		int maxY = lines.size();
		MapItem[][] map = new MapItem[maxY][maxX];
		int y = 0;
		for (String line : lines) {
			int x = 0;
			char[] ca = line.toCharArray();
			for (char c : ca) {
				if (c == '#') {
					map[y][x] = MapItem.wall;
				} else if (c == '.') {
					map[y][x] = MapItem.passage;
				} else if (c == '0') {
					map[y][x] = MapItem.start;
				} else if (c > '0' && c <= '9') {
					map[y][x] = MapItem.getPoi(c - '0');
				}

				x++;
			}
			y++;
		}
		return map;
	}

	private int countPoi(MapItem[][] map) {
		int i = 0;
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (map[x][y].isPoi())
					i++;
			}
		}
		return i;
	}

	private Point getStart(MapItem[][] map) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				if (map[y][x] == MapItem.start)
					return new Point(x, y);
			}
		}
		return null;
	}

	private MapItem getItemAtPoint(MapItem[][] map, Point point) {
		try {
			return map[point.y][point.x];
		} catch (Throwable t) {
			return null;
		}
	}

	private List<Move> getPossibleMoves(MapItem[][] map, Point currentPoint, int currentCount,
			List<Integer> currentPois, int goal) {
		List<Move> result = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			int dx;
			int dy;
			if (i == 0) {
				dx = -1;
				dy = 0;
			} else if (i == 1) {
				dx = 1;
				dy = 0;
			} else if (i == 2) {
				dx = 0;
				dy = -1;
			} else {
				dx = 0;
				dy = 1;
			}

			int x = currentPoint.x + dx;
			int y = currentPoint.y + dy;
			Point point = new Point(x, y);
			MapItem mapPoint = getItemAtPoint(map, point);
			if (mapPoint == null || mapPoint == MapItem.wall)
				continue;
			List<Integer> newPois = new ArrayList<>(currentPois);
			if (mapPoint.isPoi() && !newPois.contains(mapPoint.getPoi())) {
				newPois.add(mapPoint.getPoi());
			}
			if (newPois.size()==goal && mapPoint==MapItem.start) {
				newPois.add(0);
			}
			Move move = new Move(x, y, currentCount + 1, newPois);
			if (been.contains(move))
				continue;
			result.add(move);
			been.add(move);

		}
		return result;
	}

	public static void main(String[] args) {

		System.out.println("A: " + new Day24().problemA(LINES));
		System.out.println("B: " + new Day24().problemB(LINES));
	}
}
