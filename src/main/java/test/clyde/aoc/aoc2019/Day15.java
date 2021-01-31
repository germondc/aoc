package test.clyde.aoc.aoc2019;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import test.clyde.aoc.utils.Directions.Direction;
import test.clyde.aoc.utils.Point2d;
import test.clyde.aoc.utils.Utils;

public class Day15 {
	private static List<String> INPUT = Utils.readFile("2019/Day15.txt");

	private static Map<Integer, Direction> directions = new HashMap<>();
	static {
		directions.put(1, Direction.up);
		directions.put(2, Direction.down);
		directions.put(3, Direction.left);
		directions.put(4, Direction.right);
	}
	
	public Day15(List<String> input) {
		super();
		this.input = input;
	}

	private class Move {
		int count;
		Point2d startLocation;
		Direction desiredDirection;
		List<Move> pastMoves;
		IntCode ic;
	}

	private static long getDirection(Direction direction) {
		for (Map.Entry<Integer, Direction> e : directions.entrySet()) {
			if (e.getValue() == direction) {
				return e.getKey();
			}
		}
		return 1;
	}

	private List<String> input;
	private Point2d oxygenPoint;
	private int oxygenMoves;
	private Set<Point2d> alreadyBeen = new HashSet<>();
	private Map<Point2d, Integer> map = new HashMap<>();

	private List<Move> getPossibleMoves(Point2d location, Map<Point2d, Integer> map, int count, List<Move> pastMoves, IntCode ic) {
		List<Move> possibleMoves = new ArrayList<>();
		for (int i = 1; i <= 4; i++) {
			Point2d moveTo = location.move(directions.get(i));
			if (alreadyBeen.contains(moveTo) || (map.containsKey(moveTo) && map.get(moveTo) == 0))
				continue;
			alreadyBeen.add(moveTo);
			Move move = new Move();
			move.startLocation = location;
			move.desiredDirection = directions.get(i);
			move.count = count + 1;
			move.pastMoves = new ArrayList<>(pastMoves);
			if (ic != null)
				move.ic = ic.copy();
			possibleMoves.add(move);
		}
		return possibleMoves;
	}

	private void buildMap() {
		Point2d startLocation = new Point2d(0, 0);
		map.put(startLocation, 1);
		alreadyBeen.add(startLocation);

		Queue<Move> moves = new ArrayDeque<>();
		Move move = null;
		moves.addAll(getPossibleMoves(startLocation, map, 0, new ArrayList<>(), new IntCode(input.get(0))));
		while (moves.size() > 0) {
			move = moves.remove();
			long input = getDirection(move.desiredDirection);
			long output = move.ic.getValueForInput(input);
			if (output == 0) {
				map.put(move.startLocation.move(move.desiredDirection), 0);
			} else if (output == 1) {
				move.pastMoves.add(move);
				Point2d currentLocation = move.startLocation.move(move.desiredDirection);
				map.put(currentLocation, (int) output);
				moves.addAll(getPossibleMoves(currentLocation, map, move.count, move.pastMoves, move.ic));
			} else if (output == 2) {
				Point2d currentLocation = move.startLocation.move(move.desiredDirection);
				map.put(currentLocation, (int) output);
				moves.addAll(getPossibleMoves(currentLocation, map, move.count, move.pastMoves, move.ic));
				oxygenPoint = currentLocation;	
				oxygenMoves = move.count;
			}
		}
		
		showMap(map);
	}

	private long problemB() {
		Point2d startLocation = oxygenPoint;
		alreadyBeen.clear();
		alreadyBeen.add(startLocation);
		Queue<Move> moves = new ArrayDeque<>();
		moves.addAll(getPossibleMoves(startLocation, map, 0, new ArrayList<>(), null));
		Move move = null;
		while (moves.size() > 0) {
			move = moves.remove();
			move.pastMoves.add(move);
			Point2d currentLocation = move.startLocation.move(move.desiredDirection);
			moves.addAll(getPossibleMoves(currentLocation, map, move.count, move.pastMoves, move.ic));
		}
		return move.count;
	}

	private void showMap(Map<Point2d, Integer> map) {
		int minX = map.keySet().stream().mapToInt(p -> (int) p.x).min().getAsInt();
		int maxX = map.keySet().stream().mapToInt(p -> (int) p.x).max().getAsInt();
		int minY = map.keySet().stream().mapToInt(p -> (int) p.y).min().getAsInt();
		int maxY = map.keySet().stream().mapToInt(p -> (int) p.y).max().getAsInt();
		for (int y = minY; y <= maxY; y++) {
			for (int x = minX; x <= maxX; x++) {
				Point2d p = new Point2d(x, y);
				Integer value = map.get(p);
				if (x == 0 && y == 0) {
					System.out.print('.');
				} else if (value == null) {
					System.out.print(' ');
				} else if (value == 0) {
					System.out.print('#');
				} else if (value == 2) {
					System.out.print('x');
				} else {
					System.out.print(' ');
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Day15 d = new Day15(INPUT);
		d.buildMap();
		System.out.println("A: " + d.oxygenMoves);
		System.out.println("B: " + d.problemB());
	}
}
