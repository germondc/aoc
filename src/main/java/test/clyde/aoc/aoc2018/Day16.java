package test.clyde.aoc.aoc2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day16 {
	private static List<String> LINES = Utils.readFile("2018/Day16.txt");
	private static List<String> TEST = Utils.readFile("2018/Day16a.txt");

	private List<Instruction> ALL_INSTRUCTIONS = Arrays.asList(new Instruction[] { new Addr(), new Addi(), new Mulr(),
			new Muli(), new Banr(), new Bani(), new Borr(), new Bori(), new Seti(), new Setr(), new Gtir(), new Gtri(),
			new Gtrr(), new Eqir(), new Eqri(), new Eqrr() });

	private interface Instruction {
		List<Integer> operate(List<Integer> registers, int A, int B, int C);
	}

	private class Addr implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			int value2 = registers.get(B);
			result.set(C, value1 + value2);
			return result;
		}

	}

	private class Addi implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			int value2 = B;
			result.set(C, value1 + value2);
			return result;
		}
	}

	private class Mulr implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			int value2 = registers.get(B);
			result.set(C, value1 * value2);
			return result;
		}
	}

	private class Muli implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			int value2 = B;
			result.set(C, value1 * value2);
			return result;
		}
	}

	private class Banr implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			int value2 = registers.get(B);
			result.set(C, value1 & value2);
			return result;
		}
	}

	private class Bani implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			int value2 = B;
			result.set(C, value1 & value2);
			return result;
		}
	}

	private class Borr implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			int value2 = registers.get(B);
			result.set(C, value1 | value2);
			return result;
		}
	}

	private class Bori implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			int value2 = B;
			result.set(C, value1 | value2);
			return result;
		}
	}

	private class Setr implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			result.set(C, value1);
			return result;
		}
	}

	private class Seti implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = A;
			result.set(C, value1);
			return result;
		}
	}

	private class Gtir implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = A;
			int value2 = registers.get(B);
			result.set(C, value1 > value2 ? 1 : 0);
			return result;
		}
	}

	private class Gtri implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			int value2 = B;
			result.set(C, value1 > value2 ? 1 : 0);
			return result;
		}
	}

	private class Gtrr implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			int value2 = registers.get(B);
			result.set(C, value1 > value2 ? 1 : 0);
			return result;
		}
	}

	private class Eqir implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = A;
			int value2 = registers.get(B);
			result.set(C, value1 == value2 ? 1 : 0);
			return result;
		}
	}

	private class Eqri implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			int value2 = B;
			result.set(C, value1 == value2 ? 1 : 0);
			return result;
		}
	}

	private class Eqrr implements Instruction {
		@Override
		public List<Integer> operate(List<Integer> registers, int A, int B, int C) {
			List<Integer> result = new ArrayList<>(registers);
			int value1 = registers.get(A);
			int value2 = registers.get(B);
			result.set(C, value1 == value2 ? 1 : 0);
			return result;
		}
	}

	private class InstructionItem {
		List<Integer> before;
		List<Integer> after;
		List<Integer> instruction;

		public InstructionItem(List<Integer> before, List<Integer> after, List<Integer> instruction) {
			super();
			this.before = before;
			this.after = after;
			this.instruction = instruction;
		}
	}
	
	private int endOfFirst = 0;
	
	private List<InstructionItem> getItems(List<String> lines) {
		List<InstructionItem> items = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++) {
			String line1 = lines.get(i++);

			if (line1.length() == 0) {
				endOfFirst = i+2;
				break;
			}

			String line2 = lines.get(i++);
			String line3 = lines.get(i++);

			Pattern pat = Pattern.compile(" \\[(\\d+), (\\d+), (\\d+), (\\d+)\\]");
			List<Integer> before = new ArrayList<>();
			Matcher m = pat.matcher(line1);
			if (m.find()) {
				before.add(Integer.valueOf(m.group(1)));
				before.add(Integer.valueOf(m.group(2)));
				before.add(Integer.valueOf(m.group(3)));
				before.add(Integer.valueOf(m.group(4)));
			}

			List<Integer> after = new ArrayList<>();
			m = pat.matcher(line3);
			if (m.find()) {
				after.add(Integer.valueOf(m.group(1)));
				after.add(Integer.valueOf(m.group(2)));
				after.add(Integer.valueOf(m.group(3)));
				after.add(Integer.valueOf(m.group(4)));
			}
			List<Integer> instruction = Arrays.stream(line2.split(" ")).map(s -> Integer.valueOf(s))
					.collect(Collectors.toList());
			items.add(new InstructionItem(before, after, instruction));
		}
		return items;
	}
	
	private List<List<Integer>> getRest(List<String> lines) {
		List<List<Integer>> result = new ArrayList<>();
		for (int i = endOfFirst; i < lines.size(); i++) {
			String line = lines.get(i);
			List<Integer> instruction = Arrays.stream(line.split(" ")).map(s -> Integer.valueOf(s))
					.collect(Collectors.toList());
			result.add(instruction);
		}
		return result;
	}

	private long problemA(List<String> lines) {
		long result = 0;

		for (InstructionItem item : getItems(lines)) {
			int A = item.instruction.get(1);
			int B = item.instruction.get(2);
			int C = item.instruction.get(3);
			int count = 0;
			for (Instruction i : ALL_INSTRUCTIONS) {
				List<Integer> output = i.operate(item.before, A, B, C);
				if (output.equals(item.after))
						count++;
			}
			if (count >= 3)
				result++;
		}

		return result;
	}

	private long problemB(List<String> lines) {
		Map<Integer, Instruction> ops = new HashMap<>();
		Map<Integer, Set<Instruction>> possibles = new HashMap<>();
		for (InstructionItem item : getItems(lines)) {
			int A = item.instruction.get(1);
			int B = item.instruction.get(2);
			int C = item.instruction.get(3);
			int opcode = item.instruction.get(0);
			possibles.putIfAbsent(opcode, new HashSet<>());
			
			for (Instruction i : ALL_INSTRUCTIONS) {
				List<Integer> output = i.operate(item.before, A, B, C);
				if (output.equals(item.after))
					possibles.get(opcode).add(i);
			}
		}
		
		while (true) {
			if (possibles.values().stream().filter(l -> l.size() > 0).count() == 0)
				break;
			for (Map.Entry<Integer, Set<Instruction>> entry : possibles.entrySet()) {
				if (entry.getValue().size() == 1) {
					Instruction i = entry.getValue().iterator().next();
					ops.put(entry.getKey(), i);
					possibles.values().forEach(l -> l.remove(i));
				}
			}
		}
		
		List<Integer> registers = new ArrayList<>();
		registers.add(0);
		registers.add(0);
		registers.add(0);
		registers.add(0);
		List<List<Integer>> rest = getRest(lines);
		for (List<Integer> item : rest) {
			int A = item.get(1);
			int B = item.get(2);
			int C = item.get(3);
			int opcode = item.get(0);
			Instruction i = ops.get(opcode);
			registers = i.operate(registers, A, B, C);
		}
		
		return registers.get(0);
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day16().problemA(TEST));
		System.out.println("A: " + new Day16().problemA(LINES));
		System.out.println("B: " + new Day16().problemB(LINES));
	}

}
