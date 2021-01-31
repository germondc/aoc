package test.clyde.aoc.aoc2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day23 {
	
	private static List<String> LINES = Utils.readFile("2015/Day23.txt");
	
	private class Instruction {
		int jump;
		String operation;
		String register;
		
		public Instruction(String operation) {
			super();
			this.operation = operation;
		}
	}
	
	private int problem(int aStart) {
		
		List<Instruction> instructions = new ArrayList<>();
		for (String line :LINES) {
			String[] split = line.split(" ");
			
			Instruction i = new Instruction(split[0]);
			i.register = split[1].replace(",", "");
			if (split.length == 3)
				i.jump = Integer.valueOf(split[2]);
			else if (i.operation.equals("jmp"))
				i.jump = Integer.valueOf(i.register);				
			instructions.add(i);
		}
				
		Map<String, Integer> registers = new HashMap<>();
		registers.put("a", aStart);
		registers.put("b", 0);

		for (int pos = 0; pos < instructions.size(); pos++) {
			Instruction inst = instructions.get(pos);
			if (inst.operation.equals("hlf")) {
				registers.put(inst.register, registers.get(inst.register) / 2);
			} else if (inst.operation.equals("tpl")) {
				registers.put(inst.register, registers.get(inst.register) * 3);
			} else if (inst.operation.equals("inc")) {
				registers.put(inst.register, registers.get(inst.register) + 1);
			} else if (inst.operation.equals("jmp")) {
				pos += inst.jump -1;
			} else if (inst.operation.equals("jie")) {
				if (registers.get(inst.register) % 2 == 0)
					pos += inst.jump -1;
			} else if (inst.operation.equals("jio")) {
				if (registers.get(inst.register) == 1)
					pos += inst.jump -1;
			}
		}
		
		return registers.get("b");
	}
	
	public static void main(String[] args) {		
		System.out.println("A: " + new Day23().problem(0));
		System.out.println("B: " + new Day23().problem(1));
	}

}
