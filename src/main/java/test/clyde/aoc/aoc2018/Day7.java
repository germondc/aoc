package test.clyde.aoc.aoc2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.clyde.aoc.utils.Utils;

public class Day7 {
	private static List<String> LINES = Utils.readFile("2018/Day7.txt");
	private static List<String> TEST = Utils.readFile("2018/Day7a.txt");

	private String problemA(List<String> lines) {
		Map<String, Instruction> instructions = getInstructions(lines);

		List<String> steps = instructions.values().stream().map(i -> i.step).distinct().collect(Collectors.toList());
		List<String> allows = instructions.values().stream().flatMap(i -> i.allows.stream()).distinct()
				.collect(Collectors.toList());
		steps.removeAll(allows);

		Map<String, List<String>> dependencies = new HashMap<>();
		for (Map.Entry<String, Instruction> entry : instructions.entrySet()) {
			for (String allow : entry.getValue().allows) {
				dependencies.putIfAbsent(allow, new ArrayList<>());
				dependencies.get(allow).add(entry.getKey());
			}
		}

		List<String> order = new ArrayList<>();
		Queue<String> queue = new PriorityQueue<>(steps);
		String current = queue.poll();
		while (true) {
			order.add(current);
			Instruction instruction = instructions.get(current);
			if (instruction == null)
				break;
			List<String> currentAllows = instruction.allows;
			for (String currentAllow : currentAllows) {
				List<String> deps = dependencies.get(currentAllow);
				if (order.containsAll(deps)) {
					queue.add(currentAllow);
				}
			}

			current = queue.poll();
		}
		return order.stream().collect(Collectors.joining());
	}

	private long problemB(List<String> lines, int lag, int workerCount) {
		Map<String, Instruction> instructions = getInstructions(lines);

		List<String> steps = instructions.values().stream().map(i -> i.step).distinct().collect(Collectors.toList());
		List<String> allows = instructions.values().stream().flatMap(i -> i.allows.stream()).distinct()
				.collect(Collectors.toList());
		steps.removeAll(allows);

		Map<String, List<String>> dependencies = new HashMap<>();
		for (Map.Entry<String, Instruction> entry : instructions.entrySet()) {
			for (String allow : entry.getValue().allows) {
				dependencies.putIfAbsent(allow, new ArrayList<>());
				dependencies.get(allow).add(entry.getKey());
			}
		}

		List<String> order = new ArrayList<>();
		Queue<String> queue = new PriorityQueue<>(steps);
		List<Worker> workers = new ArrayList<>();
		int time = 0;
		while (workers.size() > 0 || queue.size() > 0) {

			Iterator<Worker> wi = workers.iterator();
			while (wi.hasNext()) {
				Worker worker = wi.next();
				worker.timeLeft--;
				if (worker.timeLeft == 0) {
					String current = worker.task;
					order.add(current);
					Instruction instruction = instructions.get(current);
					if (instruction != null) {
						List<String> currentAllows = instruction.allows;
						for (String currentAllow : currentAllows) {
							List<String> deps = dependencies.get(currentAllow);
							if (order.containsAll(deps)) {
								queue.add(currentAllow);
							}
						}
					}
					wi.remove();
				}
			}
			while (workers.size() < workerCount && queue.size() > 0) {
				Worker worker = new Worker();
				worker.task = queue.poll();
				worker.timeLeft = getTimeForTask(worker.task, lag);
				workers.add(worker);
			}
			time++;
		}
		return time-1;
	}

	private class Worker {
		String task;
		int timeLeft;
	}

	private class Instruction {
		String step;
		List<String> allows = new ArrayList<>();

		public Instruction(String step) {
			super();
			this.step = step;
		}

		@Override
		public String toString() {
			return String.format("%s -> %s", step, allows);
		}
	}

	private int getTimeForTask(String s, int lag) {
		return lag + (s.charAt(0) - 'A' + 1);
	}

	private Map<String, Instruction> getInstructions(List<String> lines) {
		Pattern pat = Pattern.compile("Step (.) must be finished before step (.) can begin.");
		Map<String, Instruction> instructions = new HashMap<>();
		for (String line : lines) {
			Matcher m = pat.matcher(line);
			if (m.find()) {
				instructions.putIfAbsent(m.group(1), new Instruction(m.group(1)));
				instructions.get(m.group(1)).allows.add(m.group(2));
			}

		}
		return instructions;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day7().problemA(TEST));
		System.out.println("A: " + new Day7().problemA(LINES));
		System.out.println("TestB: " + new Day7().problemB(TEST, 0, 2));
		System.out.println("B: " + new Day7().problemB(LINES, 60, 5));
	}

}
