package test.clyde.aoc.aoc2016;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day11a {

	private static List<String> LINES = Utils.readFile("2016/Day11.txt");

	private static String[] ELEMENTS = { "thulium", "ruthenium", "cobalt", "polonium", "promethium" };
//	private static String[] ELEMENTS = { "hydrogen", "lithium" };
	private static int LENGTH = ELEMENTS.length;

	private class Pair {
		short one;
		short two;

		public Pair(short one, short two) {
			this.one = one;
			this.two = two;
		}

		@Override
		public String toString() {
			return String.format("(%s, %s)", one, two);
		}
	}

	private int problemA() {
		long l = getInitialState();
		Map<Long, Integer> list = new HashMap<>();
		list.put(generateKeyForFloors(l), 0);
		doMove(list, l, 0);
		return bestMoves;
	}

	private int bestMoves = Integer.MAX_VALUE;

	private void doMove(Map<Long, Integer> currentMoves, long floors, int currentCount) {
		if (currentCount > bestMoves) {
			return;
		}
		if (isDone(floors) && currentCount < bestMoves) {
			bestMoves = currentCount;
			return;
		}
		short[] sa = longToShorts(floors);
		int currentFloorNum = 0;
		for (short s : sa) {
			if ((s & 1) != 0)
				break;
			currentFloorNum++;
		}

		List<Integer> possibleFloors = new ArrayList<>();
		short thisFloor = sa[currentFloorNum];

		if (currentFloorNum < 3) {
			possibleFloors.add(1);
		}
		if (currentFloorNum > 0) { // down
			possibleFloors.add(-1);
		}

		for (int floorAdj : possibleFloors) {
			for (Pair p : getPossiblePairs(thisFloor, floorAdj)) {
				short toFloor = sa[currentFloorNum + floorAdj];
//				if (floorAdj == -1 && toFloor == (short) 0)
//					continue;
//				if (toFloor == (short) 0 && p.one==0)
//					continue;
				short newThisFloor = thisFloor;
				short newToFloor = toFloor;
				newToFloor += p.one;
				newToFloor += p.two;
				newToFloor += 1;
				newThisFloor -= p.one;
				newThisFloor -= p.two;
				newThisFloor -= 1;

				if (!isFloorValid(newToFloor) || !isFloorValid(newThisFloor)) {
					continue;
				}

				short[] newShorts = new short[4];
				for (int i = 0; i < 4; i++) {
					newShorts[i] = sa[i];
				}
				newShorts[currentFloorNum] = newThisFloor;
				newShorts[currentFloorNum + floorAdj] = newToFloor;

				long key = generateKeyForFloors(newShorts);
				if (currentMoves.containsKey(key)) {
					int previousMoves = currentMoves.get(key);
					if (currentCount > previousMoves) {
						continue;
					}
				}
				currentMoves.put(key, currentCount + 1);
				doMove(currentMoves, shortsToLong(newShorts), currentCount + 1);
			}
		}
	}

	private long generateKeyForFloors(long floors) {
		return generateKeyForFloors(longToShorts(floors));
	}
	private long generateKeyForFloors(short[] floors) {
		/*
		 * long result = 0; for (int i = 0; i < 4; i++) { result <<= 16; result |= (b[i]
		 * & 0xFFFF); } return result;
		 */
		short[] key = new short[4];
		for (int i = 0; i < 4; i++) {
			key[i] |= (floors[i] & 1);
			int gen = 0;
			int chip = 0;
			for (int j = 0; j < LENGTH; j++) {
				if ((floors[i] & (1 << (2 * j + 1))) != 0) {
					gen++;
				}
				if ((floors[i] & (1 << (2 * j + 2))) != 0) {
					chip++;
				}
			}
			gen <<= 1;
			key[i] |= gen;
			chip <<= 5;
			key[i] |= chip;
		}

		return shortsToLong(key);
	}

	private boolean isDone(long floors) {
		short[] sa = longToShorts(floors);
		for (int i = 0; i < LENGTH * 2 + 1; i++) {
			if ((sa[3] & (1 << i)) == 0)
				return false;
		}
		return true;
	}

	private List<Pair> getPossiblePairs(short floor, int dir) {
		List<Pair> result = new ArrayList<>();
		List<Short> possibles = new ArrayList<>();
		for (int i = 1; i < LENGTH * 2 + 1; i++) {
			if (dir == -1 && i % 2 == 1)
				continue;
			short s = (short) (floor & (1 << i));
			if (s != 0) {
				possibles.add(s);
				result.add(new Pair((short) 0, s));
			}
		}
		for (int i = 0; i < possibles.size() - 1; i++) {
			for (int j = i + 1; j < possibles.size(); j++) {
				result.add(new Pair(possibles.get(i), possibles.get(j)));
			}
		}

		return result;
	}

	private int problemB() {
		return 0;
	}

	private long getInitialState() {
		List<String> elements = Arrays.asList(ELEMENTS);

		int floor = 0;
		Pattern gen_pat = Pattern.compile(" ([^ ]+) generator");
		Pattern chip_pat = Pattern.compile(" ([^ ]+)-compatible microchip");
		short[] floors = new short[4];
		for (String line : LINES) {
			Matcher m = gen_pat.matcher(line);
			while (m.find()) {
				int index = elements.indexOf(m.group(1));
				floors[floor] |= 1L << (2 * index + 1);
			}
			m = chip_pat.matcher(line);
			while (m.find()) {
				int index = elements.indexOf(m.group(1));
				floors[floor] |= 1L << (2 * index + 2);
			}
			if (floor == 0)
				floors[floor] += 1;
			floor++;
		}

		long floorCode = shortsToLong(floors);

		return floorCode;
	}

	private boolean isFloorValid(short floor) {
		boolean haveGen = false;
		for (int i = 0; i < LENGTH; i++) {
			if ((floor & (1 << (2 * i + 1))) != 0) {
				haveGen = true;
				break;
			}
		}
		if (haveGen) {
			for (int i = 0; i < LENGTH; i++) {
				if ((floor & (1 << (2 * i + 1))) == 0 && (floor & (1 << (2 * i + 2))) != 0) {
					return false;
				}
			}
		}
		return true;
	}

	public static short[] longToShorts(long l) {
		short[] result = new short[4];
		for (int i = 3; i >= 0; i--) {
			result[i] = (short) (l & 0xFFFF);
			l >>= 16;
		}
		return result;
	}

	public static long shortsToLong(final short[] b) {
		long result = 0;
		for (int i = 0; i < 4; i++) {
			result <<= 16;
			result |= (b[i] & 0xFFFF);
		}
		return result;
	}

	private String getBinaryString(long l) {
		return Long.toBinaryString(l).replace(' ', '0');
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println("A: " + new Day11a().problemA());
		System.out.println("B: " + new Day11a().problemB());
	}

}
