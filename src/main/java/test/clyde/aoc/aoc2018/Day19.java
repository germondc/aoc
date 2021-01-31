package test.clyde.aoc.aoc2018;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day19 {
	private static List<String> LINES = Utils.readFile("2018/Day19.txt");
	private static List<String> TEST = Utils.readFile("2018/Day19a.txt");

	private abstract class Instruction {
		int A;
		int B;
		int C;

		abstract void operate(Map<Integer, Integer> registers);

		@Override
		public String toString() {
			return String.format("%s [A=%s, B=%s, C=%s]", this.getClass().getSimpleName(), A, B, C);
		}
	}

	private class Addr extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			int value2 = registers.get(B);
			registers.put(C, value1 + value2);
			return;
		}

	}

	private class Addi extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			int value2 = B;
			registers.put(C, value1 + value2);
			return;
		}
	}

	private class Mulr extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			int value2 = registers.get(B);
			registers.put(C, value1 * value2);
			return;
		}
	}

	private class Muli extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			int value2 = B;
			registers.put(C, value1 * value2);
			return;
		}
	}

	private class Banr extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			int value2 = registers.get(B);
			registers.put(C, value1 & value2);
			return;
		}
	}

	private class Bani extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			int value2 = B;
			registers.put(C, value1 & value2);
			return;
		}
	}

	private class Borr extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			int value2 = registers.get(B);
			registers.put(C, value1 | value2);
			return;
		}
	}

	private class Bori extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			int value2 = B;
			registers.put(C, value1 | value2);
			return;
		}
	}

	private class Setr extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			registers.put(C, value1);
			return;
		}
	}

	private class Seti extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = A;
			registers.put(C, value1);
			return;
		}
	}

	private class Gtir extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = A;
			int value2 = registers.get(B);
			registers.put(C, value1 > value2 ? 1 : 0);
			return;
		}
	}

	private class Gtri extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			int value2 = B;
			registers.put(C, value1 > value2 ? 1 : 0);
			return;
		}
	}

	private class Gtrr extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			int value2 = registers.get(B);
			registers.put(C, value1 > value2 ? 1 : 0);
			return;
		}
	}

	private class Eqir extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = A;
			int value2 = registers.get(B);
			registers.put(C, value1 == value2 ? 1 : 0);
			return;
		}
	}

	private class Eqri extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			int value2 = B;
			registers.put(C, value1 == value2 ? 1 : 0);
			return;
		}
	}

	private class Eqrr extends Instruction {
		@Override
		public void operate(Map<Integer, Integer> registers) {

			int value1 = registers.get(A);
			int value2 = registers.get(B);
			registers.put(C, value1 == value2 ? 1 : 0);
			return;
		}
	}

	private long problemA(List<String> lines) throws Exception {
		List<Instruction> instructions = new ArrayList<>();
		int ipRegister = 0;
		for (String line : lines) {
			if (line.startsWith("#")) {
				ipRegister = Integer.valueOf(line.replace("#ip ", ""));
				continue;
			}
			Pattern pat = Pattern.compile("(.+) (\\d+) (\\d+) (\\d+)");
			Matcher m = pat.matcher(line);
			if (m.find()) {
				String instruction = m.group(1);
				instruction = instruction.substring(0, 1).toUpperCase() + instruction.substring(1);
				int value1 = Integer.valueOf(m.group(2));
				int value2 = Integer.valueOf(m.group(3));
				int value3 = Integer.valueOf(m.group(4));
				Class<?> clazz = Class.forName("test.clyde.aoc.aoc2018.Day19$" + instruction);
				Constructor<?> cons = clazz.getDeclaredConstructors()[0];
				cons.setAccessible(true);
				Instruction ins = (Instruction) cons.newInstance(this);
				ins.A = value1;
				ins.B = value2;
				ins.C = value3;
				instructions.add(ins);
			}
		}

		Map<Integer, Integer> registers = new HashMap<>();
		for (int i=0; i<6; i++) {
			registers.put(i,  0);
		}
		
		while (true) {
			int ip = registers.get(ipRegister);
			if (ip>= instructions.size())
				break;
			Instruction ins = instructions.get(ip);
			ins.operate(registers);
			registers.put(ipRegister, registers.get(ipRegister)+1);
		}

		return registers.get(0);
	}

	private long problemB(List<String> lines) throws Exception {
		List<Instruction> instructions = new ArrayList<>();
		int ipRegister = 0;
		for (String line : lines) {
			if (line.startsWith("#")) {
				ipRegister = Integer.valueOf(line.replace("#ip ", ""));
				continue;
			}
			Pattern pat = Pattern.compile("(.+) (\\d+) (\\d+) (\\d+)");
			Matcher m = pat.matcher(line);
			if (m.find()) {
				String instruction = m.group(1);
				instruction = instruction.substring(0, 1).toUpperCase() + instruction.substring(1);
				int value1 = Integer.valueOf(m.group(2));
				int value2 = Integer.valueOf(m.group(3));
				int value3 = Integer.valueOf(m.group(4));
				Class<?> clazz = Class.forName("test.clyde.aoc.aoc2018.Day19$" + instruction);
				Constructor<?> cons = clazz.getDeclaredConstructors()[0];
				cons.setAccessible(true);
				Instruction ins = (Instruction) cons.newInstance(this);
				ins.A = value1;
				ins.B = value2;
				ins.C = value3;
				instructions.add(ins);
			}
		}

		Map<Integer, Integer> registers = new HashMap<>();
		for (int i=0; i<6; i++) {
			registers.put(i,  0);
		}
		registers.put(0, 1);
		
		while (true) {
			int ip = (int) registers.get(ipRegister);
			if (ip>= instructions.size())
				break;
			if (ip==3) {
				int reg2 = registers.get(2);
				int reg0 = Utils.getFactors(registers.get(2)).stream().mapToInt(i -> i).sum();
				registers.put(1, reg2+1);
				registers.put(4, reg2+1);
				registers.put(0, reg0);

				registers.put(ipRegister, 13);
				ip = 13;
			}

			Instruction ins = instructions.get(ip);
			ins.operate(registers);
			registers.put(ipRegister, registers.get(ipRegister)+1);
		}

		return registers.get(0);
	}

	public static void main(String[] args) throws Exception {
		System.out.println("TestA: " + new Day19().problemA(TEST));
		System.out.println("A: " + new Day19().problemA(LINES));
		System.out.println("B: " + new Day19().problemB(LINES));
	}

}
