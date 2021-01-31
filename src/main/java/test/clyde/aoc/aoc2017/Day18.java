package test.clyde.aoc.aoc2017;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import test.clyde.aoc.utils.Utils;

public class Day18 {
	
	private static List<String> LINES = Utils.readFile("2017/Day18.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day18a.txt");

	private long problemA(List<String> lines) {
		Map<String, Long> registers = new HashMap<>();
		long soundFreq = 0;
		for (int i=0; i<lines.size(); i++) {
			String line = lines.get(i);
			String[] split = line.split(" ");
			if (split[0].equals("set")) {
				String reg = split[1];
				long value = getRegisterOrValue(registers, split[2]);
				registers.put(reg, value);
			} else if (split[0].equals("add")) {
				String reg = split[1];
				long regValue = getRegisterOrValue(registers, reg);
				long value = getRegisterOrValue(registers, split[2]);
				registers.put(reg, value+regValue);
			} else if (split[0].equals("mul")) {
				String reg = split[1];
				long regValue = getRegisterOrValue(registers, reg);
				long value = getRegisterOrValue(registers, split[2]);
				registers.put(reg, value*regValue);
			} else if (split[0].equals("mod")) {
				String reg = split[1];
				long regValue = getRegisterOrValue(registers, reg);
				long value = getRegisterOrValue(registers, split[2]);
				registers.put(reg, regValue%value);
			} else if (split[0].equals("snd")) {
				soundFreq = getRegisterOrValue(registers, split[1]);
			} else if (split[0].equals("rcv")) {
				long regValue = getRegisterOrValue(registers, split[1]);
				if (regValue != 0) {
					return soundFreq;
				}
			} else if (split[0].equals("jgz")) {
				long regXValue = getRegisterOrValue(registers, split[1]);
				if (regXValue > 0) {
					long jump = getRegisterOrValue(registers, split[2]);
					i += jump-1;
				}
			}
		}
		
		return 0;
	}
	
	private int counter = 0;
	private int problemB(List<String> lines) {
		
		int p0Index = 0;
		Map<String, Long> p0Registers = new HashMap<>();
		int p1Index = 0;
		Map<String, Long> p1Registers = new HashMap<>();
		p1Registers.put("p", 1L);
		Map<Integer, Queue<Long>> queues = new HashMap<>();
		queues.put(0, new ArrayDeque<>());
		queues.put(1, new ArrayDeque<>());
		
		while (true) {
			long adjust0 = 0;
			if (p0Index < lines.size())
				adjust0 = process(p0Registers, lines.get(p0Index), queues, 0);
			long adjust1 =0;
			if (p1Index < lines.size())
				adjust1 = process(p1Registers, lines.get(p1Index), queues, 1);
			
			if (adjust0==0 && adjust1==0)
				break;
			
			p0Index+=adjust0;
			p1Index+=adjust1;
		}
		
		return counter;
	}
	
	private long process(Map<String, Long> registers, String line, Map<Integer, Queue<Long>> queues, int id) {
		String[] split = line.split(" ");
		if (split[0].equals("set")) {
			String reg = split[1];
			long value = getRegisterOrValue(registers, split[2]);
			registers.put(reg, value);
		} else if (split[0].equals("add")) {
			String reg = split[1];
			long regValue = getRegisterOrValue(registers, reg);
			long value = getRegisterOrValue(registers, split[2]);
			registers.put(reg, value+regValue);
		} else if (split[0].equals("mul")) {
			String reg = split[1];
			long regValue = getRegisterOrValue(registers, reg);
			long value = getRegisterOrValue(registers, split[2]);
			registers.put(reg, value*regValue);
		} else if (split[0].equals("mod")) {
			String reg = split[1];
			long regValue = getRegisterOrValue(registers, reg);
			long value = getRegisterOrValue(registers, split[2]);
			registers.put(reg, regValue%value);
		} else if (split[0].equals("snd")) {
			long send = getRegisterOrValue(registers, split[1]);
			if (id == 1)
				counter++;
			queues.get(id==0?1:0).add(send);
		} else if (split[0].equals("rcv")) {
			if (queues.get(id).size()==0)
				return 0;
			String reg = split[1];
			long receieve = queues.get(id).remove();
			registers.put(reg, receieve);
		} else if (split[0].equals("jgz")) {
			long regXValue = getRegisterOrValue(registers, split[1]);
			if (regXValue > 0) {
				long jump = getRegisterOrValue(registers, split[2]);
				return jump;
			}
		}
		return 1;
	}

	private long getRegisterOrValue(Map<String, Long> registers, String reg) {
		try {
			return Long.valueOf(reg);
		} catch (Throwable t) {}
		if (!registers.containsKey(reg)) {
			registers.put(reg, 0L);
		}
		return registers.get(reg);
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day18().problemA(TEST_LINES));
		System.out.println("A: " + new Day18().problemA(LINES));
		System.out.println("B: " + new Day18().problemB(LINES));
	}

}
