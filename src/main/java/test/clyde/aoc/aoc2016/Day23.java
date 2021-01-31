package test.clyde.aoc.aoc2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day23 {

	private static List<String> LINES = Utils.readFile("2016/Day23.txt");
	private static List<String> TEST_LINES = Utils.readFile("2016/Day23a.txt");

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

	private int problemA(List<String> lines, int start) {
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

		Map<String, Integer> registers = new HashMap<>();
		registers.put("a", start);
		registers.put("b", 0);
		registers.put("c", 0);
		registers.put("d", 0);
		for (int i = 0; i < is.size(); i++) {
			if (i==5) {
				registers.put("a", registers.get("c") * registers.get("d"));
				registers.put("c", 0);
				registers.put("d", 0);
				i=8;
				continue;
			}
			
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
			}
		}

		return registers.get("a");
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

		System.out.println("A: " + new Day23().problemA(LINES, 7));
		System.out.println("B: " + new Day23().problemA(LINES, 12));
	}
}
