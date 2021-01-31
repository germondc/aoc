package test.clyde.aoc.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.NumberUtils;
import test.clyde.aoc.utils.Point3d;
import test.clyde.aoc.utils.Utils;

public class Day12 {
	private static List<String> INPUT = Utils.readFile("2019/Day12.txt");
	private static List<String> TESTA = Utils.readFile("2019/Day12a.txt");
	private static List<String> TESTB = Utils.readFile("2019/Day12b.txt");

	private long getDiff(long value1, long value2) {
		long diff = value1 - value2;
		if (diff < 0)
			diff = 1;
		else if (diff > 0)
			diff = -1;
		else
			diff = 0;
		return diff;
	}

	private long problemA(List<String> input, int steps) {
		List<Moon> moons = parseInput(input);
		for (int i = 0; i < steps; i++) {
			for (Moon moon1 : moons) {
				for (Moon moon2 : moons) {
					if (moon1 == moon2)
						continue;

					moon1.velocity.x += getDiff(moon1.position.x, moon2.position.x);
					moon1.velocity.y += getDiff(moon1.position.y, moon2.position.y);
					moon1.velocity.z += getDiff(moon1.position.z, moon2.position.z);
				}
			}
			for (Moon moon : moons) {
				moon.position.piecewiseAdd(moon.velocity);
			}
		}
		return moons.stream().mapToLong(m -> m.getEnergy()).sum();
	}

	private long problemB(List<String> input) {
		List<Moon> moons = parseInput(input);
		Map<Integer, Long> repeats = new HashMap<>();
		Map<Integer, Map<Long, Long>> coordHashes = new HashMap<>();
		long[] coordHashed = getCoordHashes(moons);
		for (int coordIndex = 0; coordIndex < 3; coordIndex++) {
			coordHashes.put(coordIndex, new HashMap<>());
			coordHashes.get(coordIndex).put(coordHashed[coordIndex], 0L);
		}

		long steps = 0;
		main: while (true) {
			for (Moon moon1 : moons) {
				for (Moon moon2 : moons) {
					if (moon1 == moon2)
						continue;
					moon1.velocity.x += getDiff(moon1.position.x, moon2.position.x);
					moon1.velocity.y += getDiff(moon1.position.y, moon2.position.y);
					moon1.velocity.z += getDiff(moon1.position.z, moon2.position.z);
				}
			}
			for (Moon moon : moons) {
				moon.position.piecewiseAdd(moon.velocity);
			}
			steps++;

			coordHashed = getCoordHashes(moons);
			for (int coordIndex = 0; coordIndex < 3; coordIndex++) {
				if (coordHashes.get(coordIndex).containsKey(coordHashed[coordIndex])) {
					if (!repeats.containsKey(coordIndex)) {
						long previous = coordHashes.get(coordIndex).get(coordHashed[coordIndex]);
						repeats.put(coordIndex, steps);
						System.out.println(coordIndex + ":\t" + steps + "\tprevious:\t" + previous);
					}
					if (repeats.size() == 3) {
						break main;
					}
				}
				coordHashes.get(coordIndex).put(coordHashed[coordIndex], steps);
			}
		}
		return NumberUtils.lowestCommonMultiple(repeats.values().stream().collect(Collectors.toList()));
	}

	private long[] getCoordHashes(List<Moon> moons) {
		long[][] positionElements = new long[3][4];

		for (int moonIndex = 0; moonIndex < 4; moonIndex++) {
			Moon moon = moons.get(moonIndex);
			List<Long> elements = moon.getElements();
			for (int coordIndex = 0; coordIndex < 3; coordIndex++) {
				positionElements[coordIndex][moonIndex] = elements.get(coordIndex);
			}
		}
		long[] result = new long[3];
		for (int coordIndex = 0; coordIndex < 3; coordIndex++) {
			result[coordIndex] = 0;
			for (int moonIndex = 0; moonIndex < 4; moonIndex++) {
				result[coordIndex] += positionElements[coordIndex][moonIndex] << (16 * moonIndex);
			}
		}
		return result;
	}

	private List<Moon> parseInput(List<String> input) {
		List<Moon> result = new ArrayList<>();
		Pattern pat = Pattern.compile("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>");
		for (String line : input) {
			Matcher m = pat.matcher(line);
			if (m.find()) {
				Moon moon = new Moon();
				long x = Long.valueOf(m.group(1));
				long y = Long.valueOf(m.group(2));
				long z = Long.valueOf(m.group(3));
				moon.position = new Point3d(x, y, z);
				result.add(moon);
			}
		}
		return result;
	}

	private class Moon {
		Point3d position;
		Point3d velocity = new Point3d();

		public long getEnergy() {
			return (Math.abs(position.x) + Math.abs(position.y) + Math.abs(position.z))
					* (Math.abs(velocity.x) + Math.abs(velocity.y) + Math.abs(velocity.z));
		}

		public List<Long> getElements() {
			List<Long> result = new ArrayList<>();
			result.add(position.x * 1024 + velocity.x);
			result.add(position.y * 1024 + velocity.y);
			result.add(position.z * 1024 + velocity.z);
			return result;
		}
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day12().problemA(TESTA, 10));
		System.out.println("TestA: " + new Day12().problemA(TESTB, 100));
		System.out.println("A: " + new Day12().problemA(INPUT, 1000));
		System.out.println("TestB: " + new Day12().problemB(TESTA));
		System.out.println("TestB: " + new Day12().problemB(TESTB));
		System.out.println("B: " + new Day12().problemB(INPUT));
	}

}
