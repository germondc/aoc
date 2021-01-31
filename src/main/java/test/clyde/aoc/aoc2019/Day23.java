package test.clyde.aoc.aoc2019;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import test.clyde.aoc.utils.Utils;

public class Day23 {
	private static List<String> INPUT = Utils.readFile("2019/Day23.txt");

	private List<String> input;

	public Day23(List<String> input) {
		this.input = input;
	}

	public long problemA() {
		Map<Integer, IntCode23> computers = new HashMap<>();
		for (int i=0; i<50; i++) {
			computers.put(i, new IntCode23(input.get(0)));
			computers.get(i).addToInput(i);
		}
		
		while (true) {
			for (int i=0; i<50; i++) {
				computers.get(i).process();
				List<Long> output = computers.get(i).getCurrentOutput(3);
				if (output != null) {
					long address = output.get(0);
					long x = output.get(1);
					long y = output.get(2);
					if (address==255)
						return y;
					computers.get((int)address).addToInput(x);
					computers.get((int)address).addToInput(y);
				}
			}
		}
	}

	public long problemB() {
		Map<Integer, IntCode23> computers = new HashMap<>();
		for (int i=0; i<50; i++) {
			computers.put(i, new IntCode23(input.get(0)));
			computers.get(i).addToInput(i);
		}
		
		long natX = -1;
		long natY = -1;
		Set<Long> yCache = new HashSet<>();
		
		while (true) {
			for (int i=0; i<50; i++) {
				computers.get(i).process();
				List<Long> output = computers.get(i).getCurrentOutput(3);
				if (output != null) {
					long address = output.get(0);
					long x = output.get(1);
					long y = output.get(2);
					if (address==255) {
						natX = x;
						natY = y;
					} else {
						computers.get((int)address).addToInput(x);
						computers.get((int)address).addToInput(y);
					}
				}
				boolean allEmpty = true;
				for (int j=0; j<50; j++) {
					if (!computers.get(j).isOnlyReceiving()) {
						allEmpty = false;
						break;
					}
				}
				if (allEmpty && natX != -1) {
					if (yCache.contains(natY)) {
						return natY;
					}
					computers.get(0).addToInput(natX);
					computers.get(0).addToInput(natY);
					yCache.add(natY);
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("A: " + new Day23(INPUT).problemA());
		System.out.println("B: " + new Day23(INPUT).problemB());
	}
}