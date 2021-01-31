package test.clyde.aoc.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day14 {

	private static List<String> LINES = Utils.readFile("2020/Day14.txt");

	public static void main(String[] args) {
		System.out.println("A: " + new Day14().problemA(LINES));
		System.out.println("B: " + new Day14().problemB(LINES));
	}

	private abstract class Item {

	}

	private class Mask extends Item {
		String mask;

		public Mask(String mask) {
			this.mask = mask;
		}

		private long getAndMask() {
			String result = "";
			for (char c : mask.toCharArray()) {
				if (c == '0')
					result += "0";
				else
					result += "1";
			}
			return Long.parseLong(result, 2);
		}

		private long getOrMask() {
			String result = "";
			for (char c : mask.toCharArray()) {
				if (c == '1')
					result += "1";
				else
					result += "0";
			}
			return Long.parseLong(result, 2);
		}
		
		public long applyMask(long value) {
			long newValue = value;
			newValue |= getOrMask();
			newValue &= getAndMask();
			return newValue;
		}

		public List<Long> applyVersion2(long value) {
			List<Long> result = new ArrayList<>();
			long valueHolder = value | getOrMask();

			int bits=0;
			List<Integer> xPos = xPos();
			for (int i=0; i<Math.pow(2, xPos.size()); i++) {
				int bitIndex =0;
				long newValue = valueHolder;
				for (int j : xPos) {
					int bit = bits & (int)Math.pow(2,bitIndex);
					if (bit ==0)
						newValue &= ~(1L << (35-j));
					else
						newValue |= (1L << (35-j));
					bitIndex++;
				}
				result.add(newValue);
				bits++;
			}
			return result;
		}
		
		private List<Integer> xPos() {
			List<Integer> result = new ArrayList<>();
			for (int i = 0; i < mask.length(); i++) {
				if (mask.charAt(i) == 'X') {
					result.add(i);
				}
			}
			return result;
		}
	}

	private class Mem extends Item {
		int address;
		long value;

		public Mem(int address, long value) {
			this.address = address;
			this.value = value;
		}
	}
	
	private List<Item> getInput(List<String> lines) {
		Pattern mask_pat = Pattern.compile("mask = (.*)");
		Pattern mem_pat = Pattern.compile("mem\\[([0-9]+)\\] = ([0-9]+)");
		List<Item> items = new ArrayList<>();
		for (String line : lines) {
			if (line.startsWith("mask")) {
				Matcher m = mask_pat.matcher(line);
				if (m.find()) {
					String mask = m.group(1);
					items.add(new Mask(mask));
				}
			} else {
				Matcher m = mem_pat.matcher(line);
				if (m.find()) {
					int address = Integer.valueOf(m.group(1));
					long value = Integer.valueOf(m.group(2));
					items.add(new Mem(address, value));
				}
			}
		}
		return items;
	}
	
	private long problemA(List<String> lines) {
		List<Item> items = getInput(lines);
		Mask currentMask = null;
		Map<Integer, Long> values = new HashMap<>();

		for (Item item : items) {
			if (item instanceof Mask) {
				currentMask = (Mask) item;
			} else {
				Mem mem = (Mem) item;
				values.put(mem.address, currentMask.applyMask(mem.value));
			}
		}
		return values.values().stream().mapToLong(l -> l).sum();
	}

	private long problemB(List<String> lines) {
		List<Item> items = getInput(lines);
		Map<Long, Long> values = new HashMap<>();
		Mask currentMask = null;

		for (Item item : items) {
			if (item instanceof Mask) {
				currentMask = ((Mask) item);
			} else {
				Mem mem = (Mem) item;
				currentMask.applyVersion2(mem.address).stream().forEach(l -> values.put(l, mem.value));
			}
		}
		return values.values().stream().mapToLong(l -> l).sum();
	}
}
