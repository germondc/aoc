package test.clyde.aoc.aoc2018;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day21 {
	private static List<String> LINES = Utils.readFile("2018/Day21.txt");

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
				Class<?> clazz = Class.forName("test.clyde.aoc.aoc2018.Day21$" + instruction);
				Constructor<?> cons = clazz.getDeclaredConstructors()[0];
				cons.setAccessible(true);
				Instruction ins = (Instruction) cons.newInstance(this);
				ins.A = value1;
				ins.B = value2;
				ins.C = value3;
				instructions.add(ins);
			}
		}

		int value = 0;
		Map<Integer, Integer> registers = new HashMap<>();
		for (int i = 0; i < 6; i++) {
			registers.put(i, 0);
		}

		while (true) {
			int ip = registers.get(ipRegister);

			if (ip == 28) {
				value = registers.get(3);
				break;
			}

			Instruction ins = instructions.get(ip);
			ins.operate(registers);
			registers.put(ipRegister, registers.get(ipRegister) + 1);
		}

		return value;
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
				Class<?> clazz = Class.forName("test.clyde.aoc.aoc2018.Day21$" + instruction);
				Constructor<?> cons = clazz.getDeclaredConstructors()[0];
				cons.setAccessible(true);
				Instruction ins = (Instruction) cons.newInstance(this);
				ins.A = value1;
				ins.B = value2;
				ins.C = value3;
				instructions.add(ins);
			}
		}

		int value = 0;
		Map<Integer, Integer> registers = new HashMap<>();
		for (int i = 0; i < 6; i++) {
			registers.put(i, 0);
		}

		List<Integer> regCValues = new ArrayList<>();

		while (true) {
			int ip = registers.get(ipRegister);

			if (ip == 28) {
				int regC = registers.get(3);
				if (regCValues.contains(regC)) {
					value = regCValues.get(regCValues.size()-1);
					System.out.println(regC);
					break;
				}
					
				regCValues.add(regC);
			}

			Instruction ins = instructions.get(ip);
			ins.operate(registers);
			registers.put(ipRegister, registers.get(ipRegister) + 1);
		}

		return value;
	}

	private int hashCode(Collection<Integer> registerValues, int lineNumber) {
		Set<Integer> set = new HashSet<>(registerValues);
		set.add(lineNumber);
		return set.hashCode();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("A: " + new Day21().problemA(LINES));
		System.out.println("B: " + new Day21().problemB(LINES));
	}

}
