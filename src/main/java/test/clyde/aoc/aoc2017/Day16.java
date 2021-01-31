package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day16 {

	private static List<String> LINES = Utils.readFile("2017/Day16.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day16a.txt");
	
	private interface Instruction {
		int[] operate(int[] current);
	}
	
	private class Spin implements Instruction {
		int amount;
		
		public Spin(int amount) {
			super();
			this.amount = amount;
		}

		@Override
		public int[] operate(int[] current) {
			int[] result = new int[current.length];
			for (int i = 0; i < current.length; i++) {
				int index = i - amount;
				while (index < 0)
					index += current.length;
				while (index >= current.length) {
					index -= current.length;
				}
				result[i] += current[index];
			}
			return result;
		}
		
	}
	
	private class Exchange implements Instruction {
		int index1;
		int index2;
		
		public Exchange(int index1, int index2) {
			this.index1 = index1;
			this.index2 = index2;
		}
		
		@Override
		public int[] operate(int[] current) {
			int[] result = current;
			int value1 = current[index1];
			result[index1] = current[index2];
			result[index2] = value1;
			return result;
		}
		
	}
	
	private class Partner implements Instruction {
		int a;
		int b;
		public Partner(char a, char b) {
			super();
			this.a = a - 'a';
			this.b = b - 'a';
		}
		@Override
		public int[] operate(int[] current) {
			int[] result = current;
			int index1 = 0;
			int index2 = 0;
			for (int i=0; i<current.length; i++) {
				if (current[i]==a)
					index1=i;
				if (current[i]==b)
					index2=i;
			}
			
			int value1 = current[index1];
			result[index1] = current[index2];
			result[index2] = value1;
			return result;
		}
		
	}

	private String problemB(String line, int size, long repeats) {
		int[] start = new int[size];
		for (int i=0; i<size; i++) {
			start[i] = i;
		}
		
		String[] split = line.split(",");
		List<Instruction> instructions = new ArrayList<>();
		for (String s : split) {
			char in = s.charAt(0);
			if (in == 's') {
				int amount = Integer.valueOf(s.substring(1));
				instructions.add(new Spin(amount));
			} else if (in == 'x') {
				int index1 = Integer.valueOf(s.substring(1, s.indexOf('/')));
				int index2 = Integer.valueOf(s.substring(s.indexOf('/') + 1));
				instructions.add(new Exchange(index1, index2));
			} else if (in == 'p') {
				char value1 = s.charAt(s.indexOf('/') - 1);
				char value2 = s.charAt(s.indexOf('/') + 1);
				instructions.add(new Partner(value1, value2));
			}
		}

		Map<String, Long> previous = new HashMap<>();
		int[] current = start;
		for (long j = 0; j < repeats; j++) {
			for (Instruction instruction : instructions) {
				current = instruction.operate(current);
			}
			String seq = getString(current);
			if (previous.containsKey(seq)) {
				long diff = j - previous.get(seq);
				long add = (repeats / diff) * diff - diff;
				j+=add;
			}
			previous.put(seq, j);
		}
		
		return getString(current);
	}
	
	private String getString(int[] values) {
		char[] result = new char[values.length];
		for (int i=0; i<values.length; i++) {
			result[i] = (char) (values[i] + 'a');
		}
		
		return new String(result);
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day16().problemB(TEST_LINES.get(0), 5, 1));
		System.out.println("A: " + new Day16().problemB(LINES.get(0), 16, 1));
		System.out.println("TestB: " + new Day16().problemB(TEST_LINES.get(0), 5, 1000000000));
		System.out.println("B: " + new Day16().problemB(LINES.get(0), 16, 1000000000));
	}

}
