package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day23 {

	private static List<String> LINES = Utils.readFile("2017/Day23.txt");

	private long problemA(List<String> lines) {
		Map<String, Long> registers = new HashMap<>();
		List<Instruction> instructions = getinstructions(lines);
		for (int i = 0; i < instructions.size(); i++) {
			Instruction instruction = instructions.get(i);
			i = instruction.work(registers, i);
		}
		return mulCounter;
	}

	private long problemB(List<String> lines) {
		Map<String, Long> registers = new HashMap<>();
		registers.put("a", 1L);
		return process(lines, registers);
	}

	private List<Instruction> getinstructions(List<String> lines) {
		List<Instruction> result = new ArrayList<>();
		for (String line : lines) {
			String[] split = line.split(" ");
			if (split[0].equals("set")) {
				result.add(new Set(split[1], split[2]));
			} else if (split[0].equals("sub")) {
				result.add(new Sub(split[1], split[2]));
			} else if (split[0].equals("mul")) {
				result.add(new Mul(split[1], split[2]));
			} else if (split[0].equals("jnz")) {
				result.add(new Jnz(split[1], split[2]));
			}

		}
		return result;
	}

	private long process(List<String> lines, Map<String, Long> registers) {
		List<Instruction> instructions = getinstructions(lines);
		for (int i = 0; i < instructions.size(); i++) {
			Instruction instruction = instructions.get(i);
			
			if (i == 10) {
				long b = registers.get("b");
				if (!Utils.isPrime(b))
					registers.put("f", 0L);
				registers.put("d", b);
				registers.put("e", b);
				registers.put("g", 0L);
				i = 23;
			}

			i = instruction.work(registers, i);
		}

		return registers.get("h");
	}

	private long getRegisterOrValue(Map<String, Long> registers, String reg) {
		try {
			return Long.valueOf(reg);
		} catch (Throwable t) {
		}
		if (!registers.containsKey(reg)) {
			registers.put(reg, 0L);
		}
		return registers.get(reg);
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day23().problemA(LINES));
		System.out.println("B: " + new Day23().problemB(LINES));
	}

	private interface Instruction {
		public int work(Map<String, Long> registers, int line);
	}

	private class Set implements Instruction {
		String x;
		String y;

		public Set(String x, String y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int work(Map<String, Long> registers, int line) {
			long value = getRegisterOrValue(registers, y);
			registers.put(x, value);
			return line;
		}
	}

	private class Sub implements Instruction {
		String x;
		String y;

		public Sub(String x, String y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int work(Map<String, Long> registers, int line) {
			long regValue = getRegisterOrValue(registers, x);
			long value = getRegisterOrValue(registers, y);
			registers.put(x, regValue - value);
			return line;
		}
	}

	private static int mulCounter = 0;

	private class Mul implements Instruction {
		String x;
		String y;

		public Mul(String x, String y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int work(Map<String, Long> registers, int line) {
			mulCounter++;
			long regValue = getRegisterOrValue(registers, x);
			long value = getRegisterOrValue(registers, y);
			registers.put(x, regValue * value);
			return line;
		}
	}

	private class Jnz implements Instruction {
		String x;
		String y;

		public Jnz(String x, String y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int work(Map<String, Long> registers, int line) {
			long regXValue = getRegisterOrValue(registers, x);
			if (regXValue != 0) {
				int jump = (int) getRegisterOrValue(registers, y);
				return line + jump - 1;
			}
			return line;
		}
	}
}
