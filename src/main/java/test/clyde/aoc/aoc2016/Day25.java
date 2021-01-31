package test.clyde.aoc.aoc2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day25 {

	private static List<String> LINES = Utils.readFile("2016/Day25.txt");

	private class Instruction {
		String name;
		String par1;
		String par2;

		public void toggle() {
			if (name.equals("inc"))
				name = "dec";
			else if (name.equals("tgl"))
				name = "inc";
			else if (par2 == null)
				name = "inc";
			else if (name.equals("jnz"))
				name = "cpy";
			else
				name = "jnz";
		}

		@Override
		public String toString() {
			return String.format("Instruction [name=%s, par1=%s, par2=%s]", name, par1, par2);
		}
	}
	
	private int[] GOAL = { 0,1,0,1,0,1,0,1,0,1 };
	
	private int problemA(List<String> lines) {		
		List<Instruction> is = getInstructions(lines);
		int index = 0;
		while (true) {
			List<Integer> numbers = first10(is, index);
			if (numbers != null) {
				return index;
			}
			index++;
		}
	}
	
	private List<Instruction> getInstructions(List<String> lines) {
		List<Instruction> is = new ArrayList<>();
		for (String line : lines) {
			String[] split = line.split(" ");
			Instruction i = new Instruction();
			i.name = split[0];
			i.par1 = split[1];
			if (split.length > 2) {
				i.par2 = split[2];
			}
			is.add(i);
		}
		return is;
	}

	private List<Integer> first10(List<Instruction> is, int start) {
		List<Integer> numbers = new ArrayList<>();
		
		Map<String, Integer> registers = new HashMap<>();
		registers.put("a", start);
		registers.put("b", 0);
		registers.put("c", 0);
		registers.put("d", 0);
		for (int i = 0; i < is.size(); i++) {
			
			Instruction ins = is.get(i);
			if (ins.name.equals("cpy")) {
				int value = getValueOrRegister(registers, ins.par1);
				if (registers.containsKey(ins.par2))
					registers.put(ins.par2, value);
			} else if (ins.name.equals("inc")) {
				registers.put(ins.par1, registers.get(ins.par1) + 1);
			} else if (ins.name.equals("dec")) {
				registers.put(ins.par1, registers.get(ins.par1) - 1);
			} else if (ins.name.equals("jnz")) {
				int value = getValueOrRegister(registers, ins.par1);
				if (value != 0) {
					i += getValueOrRegister(registers, ins.par2) - 1;
				}
			} else if (ins.name.equals("tgl")) {
				int value = getValueOrRegister(registers, ins.par1);
				if ((value+i) < is.size()) {
					is.get(i+value).toggle();
				}
			} else if (ins.name.equals("out")) {
				numbers.add(getValueOrRegister(registers, ins.par1));
				
				if (!checkNumbers(numbers))
					return null;
				if (numbers.size() == 10)
					return numbers;
			}
		}

		return null;
	}
	
	private boolean checkNumbers(List<Integer> numbers) {
		boolean allGood = true;
		for (int i=0; i<numbers.size(); i++) {
			if (numbers.get(i) != GOAL[i]) {
				allGood = false;
				break;
			}
		}
		return allGood;
	}
	
	private int getValueOrRegister(Map<String, Integer> registers, String regOrValue) {
		int value;
		if (registers.containsKey(regOrValue)) {
			value = registers.get(regOrValue);
		} else {
			value = Integer.valueOf(regOrValue);
		}
		return value;
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day25().problemA(LINES));
		//System.out.println("B: " + new Day25().problemA(LINES, 12));
	}
}
