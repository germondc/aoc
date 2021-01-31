package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day13 {

	private static List<String> LINES = Utils.readFile("2017/Day13.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day13a.txt");

	private class Layer {
		int id;
		int range;
		int position = 0;
		boolean down = true;

		public Layer(int id, int range) {
			super();
			this.id = id;
			this.range = range;
		}

		@Override
		public String toString() {
			return String.format("Layer [id=%s, range=%s, position=%s]", id, range, position);
		}

		public void move() {
			if (down)
				position++;
			else
				position--;

			if (down && position == range - 1) {
				down = false;
			} else if (!down && position == 0) {
				down = true;
			}
		}
		public void reset(int delay) {
			//10 / 3
			//range*2-2
			down = true;
			position = delay % (range*2 - 2);
			if (position > range-1) {
				down = false;
				position -= (range-1);
				position = (range-1)-position;
			} else if (position == range-1) {
				down = false;
			}
		}
	}

	private long problemA(List<String> lines) {
		Map<Integer, Layer> layers = new HashMap<>();
		for (String line : lines) {
			int layerValue = Integer.valueOf(line.substring(0, line.indexOf(':')));
			int range = Integer.valueOf(line.substring(line.indexOf(':') + 2));
			layers.put(layerValue, new Layer(layerValue, range));
		}

		int max = layers.keySet().stream().mapToInt(i -> i).max().getAsInt();
		int severity = 0;
		for (int i = 0; i <= max; i++) {
			Layer layer = layers.get(i);
			if (layer != null && layer.position == 0) {
				severity += i * layer.range;
			}
			layers.values().stream().forEach(l -> l.move());
		}
		return severity;
	}

	private long problemB(List<String> lines) {

		Map<Integer, Layer> layers = new HashMap<>();
		for (String line : lines) {
			int layerValue = Integer.valueOf(line.substring(0, line.indexOf(':')));
			int range = Integer.valueOf(line.substring(line.indexOf(':') + 2));
			layers.put(layerValue, new Layer(layerValue, range));
		}

		int max = layers.keySet().stream().mapToInt(i -> i).max().getAsInt();
		int delay = 1;
		while (true) {
			final int d = delay;
			layers.values().stream().forEach(l -> l.reset(d));
			boolean good = true;
			for (int i = 0; i <= max; i++) {
				Layer layer = layers.get(i);
				if (layer != null && layer.position == 0) {
					good = false;
					break;
				}
				layers.values().stream().forEach(l -> l.move());
			}
			if (good)
				return delay;
			delay++;
		}
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day13().problemA(TEST_LINES));
		System.out.println("A: " + new Day13().problemA(LINES));
		System.out.println("TestB: " + new Day13().problemB(TEST_LINES));
		System.out.println("B: " + new Day13().problemB(LINES));
	}

}
