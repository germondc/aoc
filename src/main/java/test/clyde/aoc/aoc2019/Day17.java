package test.clyde.aoc.aoc2019;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import test.clyde.aoc.utils.Directions.Direction;
import test.clyde.aoc.utils.Point2d;
import test.clyde.aoc.utils.Utils;

public class Day17 {
	private static List<String> INPUT = Utils.readFile("2019/Day17.txt");

	private List<String> input;

	public Day17(List<String> input) {
		this.input = input;
	}

	private char[][] theMap;
	private Point2d currentPoint;
	private Direction currentDirection;

	private long problemA() {
		IntCode intCode = new IntCode(input.get(0));
		long output = 0;
		List<List<Character>> map = new ArrayList<>();
		List<Character> row = new ArrayList<>();
		while (output != -1) {
			output = intCode.getValueForInput(0);
			char c = (char) output;
			if (output == 10) {
				if (row.size() != 0) {
					map.add(row);
				}
				row = new ArrayList<>();
			} else {
				row.add(c);
			}
			System.out.print(c);
		}
		theMap = new char[map.size()][map.get(0).size()];

		for (int y = 0; y < map.size(); y++) {
			row = map.get(y);
			for (int x = 0; x < map.get(0).size(); x++) {
				theMap[y][x] = row.get(x);
			}
		}

		long score = 0;
		for (int y = 1; y < map.size() - 1; y++) {
			for (int x = 1; x < map.get(0).size() - 1; x++) {
				if (theMap[y][x] == '#' && theMap[y][x - 1] == '#' && theMap[y][x + 1] == '#' && theMap[y - 1][x] == '#'
						&& theMap[y + 1][x] == '#') {
					score += x * y;
				} else if (theMap[y][x] == 'v') {
					currentPoint = new Point2d(x, y);
					currentDirection = Direction.down;
				} else if (theMap[y][x] == '^') {
					currentPoint = new Point2d(x, y);
					currentDirection = Direction.up;
				} else if (theMap[y][x] == '<') {
					currentPoint = new Point2d(x, y);
					currentDirection = Direction.left;
				} else if (theMap[y][x] == '>') {
					currentPoint = new Point2d(x, y);
					currentDirection = Direction.right;
				}
			}
		}

		return score;
	}

	private long problemB() {
		IntCode intCode = new IntCode(input.get(0));
		intCode.updateProgram(0, 2);
		int maxY = theMap.length;
		int maxX = theMap[0].length;
		StringBuilder moves = new StringBuilder();
		int moveCount = 0;
		while (true) {
			Point2d moveInCurrent = currentPoint.move(currentDirection);
			if (moveInCurrent.x >= 0 && moveInCurrent.x < maxX && moveInCurrent.y >= 0 && moveInCurrent.y < maxY
					&& theMap[(int) moveInCurrent.y][(int) moveInCurrent.x] == '#') {
				moveCount++;
				currentPoint = moveInCurrent;
			} else {
				boolean anyMove = false;
				if (moveCount > 0) {
					moves.append(moveCount);
					moves.append(',');
				}
				moveCount = 0;
				for (int degrees : new int[] { 90, 270 }) {
					Direction newDirection = currentDirection.turn(degrees);
					moveInCurrent = currentPoint.move(newDirection);
					if (theMap[(int) moveInCurrent.y][(int) moveInCurrent.x] != '#')
						continue;
					currentDirection = newDirection;
					if (degrees == 90)
						moves.append("R");
					else
						moves.append("L");
					moves.append(',');
					anyMove = true;
					break;
				}
				if (!anyMove) {
					break;
				}
			}
		}
		String current = moves.toString().substring(0, moves.length() - 1);

		Map<String, String> groups = new HashMap<>();
		for (String groupName : new String[] { "A", "B", "C" }) {
			int offset = 0;
			int longest = 0;
			String[] split = current.split(",");
			while (split[offset].equals("A") || split[offset].equals("B") || split[offset].equals("C")) {
				offset++;
			}
			String group = "";
			for (int i = offset+1; i < split.length; i++) {
				int length = 0;
				group = "";
				for (int j = offset; i+j < split.length; j++) {
					if (split[j].equals("A") || split[j].equals("B") || split[j].equals("C")) {
						break;
					}
					if (split[j].equals(split[i + j])) {
						if (j > offset)
							group += ",";
						group += split[j];
						if (group.length() > 20) {
							if (!split[j].equals("R") && !split[j].equals("L"))
								length--;
							break;
						}
						length++;
					} else {
						break;
					}
				}
				if (length > longest) {
					longest = length;
				}
			}
			String theGroup = "";
			for (int i = 0; i < longest; i++) {
				if (i > 0)
					theGroup += ",";
				theGroup += split[i+offset];
			}
			current = current.replaceAll(theGroup, groupName);
			groups.put(groupName, theGroup);
		}
		
		String[] directionGroups = new String[5];
		directionGroups[0] = current;
		directionGroups[1] = groups.get("A");
		directionGroups[2] = groups.get("B");
		directionGroups[3] = groups.get("C");
		directionGroups[4] = "n";

		Queue<Long> inputQueue = new ArrayDeque<>();
		for (String directionGroup : directionGroups) {
			for (char c : directionGroup.toCharArray()) {
				inputQueue.add((long) c);
			}
			inputQueue.add(10L);
		}

		List<Long> outputs = intCode.getValuesForInputs(inputQueue);
		return outputs.get(outputs.size() - 1);
	}

	public static void main(String[] args) {
		Day17 d = new Day17(INPUT);
		System.out.println("A: " + d.problemA());
		System.out.println("B: " + d.problemB());
	}
}
