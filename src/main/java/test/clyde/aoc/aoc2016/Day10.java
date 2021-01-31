package test.clyde.aoc.aoc2016;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day10 {

	private static List<String> LINES = Utils.readFile("2016/Day10.txt");

	private Pattern BOT_PAT = Pattern.compile("bot ([0-9]+) gives low to (.*) ([0-9]+) and high to (.*) ([0-9]+)");
	private Pattern VAL_PAT = Pattern.compile("value ([0-9]+) goes to bot ([0-9]+)");

	private class Loader {
		int bot;
		int value;
		public Loader(int bot, int value) {
			super();
			this.bot = bot;
			this.value = value;
		}
	}
	
	private class Bot {
		List<Integer> values = new ArrayList<>();
		
		int getHigh() {
			if (values.size() != 2)
				return 0;
			if (values.get(0) > values.get(1))
				return values.get(0);
			else
				return values.get(1);
		}
		
		int getLow() {
			if (values.size() != 2)
				return 0;
			if (values.get(0) < values.get(1))
				return values.get(0);
			else
				return values.get(1);
		}
	}
	
	private class Instruction {
		int fromBot;
		boolean lowToBot;
		int toLow;
		boolean highToBot;
		int toHigh;
		public Instruction(int fromBot, boolean lowToBot, int toLow, boolean highToBot, int toHigh) {
			super();
			this.fromBot = fromBot;
			this.lowToBot = lowToBot;
			this.toLow = toLow;
			this.highToBot = highToBot;
			this.toHigh = toHigh;
		}
		
	}
	
	private int problemA() {
		
		List<Loader> loaders = new ArrayList<>();
		Map<Integer, Instruction> instructions = new HashMap<>();
		
		for (String line : LINES) {
			if (line.startsWith("bot")) {
				Matcher m = BOT_PAT.matcher(line);
				if (m.find()) {
					int fromBot = Integer.valueOf(m.group(1));
					boolean lowToBot = m.group(2).equals("bot");
					int toLow = Integer.valueOf(m.group(3));
					boolean highToBot = m.group(4).equals("bot");
					int toHigh = Integer.valueOf(m.group(5));
					instructions.put(fromBot, new Instruction(fromBot, lowToBot, toLow, highToBot, toHigh));
				}
			} else if (line.startsWith("value")) {
				Matcher m = VAL_PAT.matcher(line);
				if (m.find()) {
					int value = Integer.valueOf(m.group(1));
					int bot = Integer.valueOf(m.group(2));
					loaders.add(new Loader(bot, value));
				}
			} else {
				System.out.println("failed " + line);
			}
		}
		
		Map<Integer, Bot> currentState = new HashMap<>();
		for (Loader l : loaders) {
			Bot b = currentState.get(l.bot);
			if (b == null) {
				 b = new Bot();
					currentState.put(l.bot, b);
			}
			b.values.add(l.value);
		}
		
		while (true) {
			int fromBot = currentState.entrySet().stream().filter(e -> e.getValue().values.size() == 2).map(e -> e.getKey()).findAny().get();
			Instruction instruction = instructions.get(fromBot);
			int low = currentState.get(fromBot).getLow();
			int high = currentState.get(fromBot).getHigh();
			if (low == 17 && high == 61)
				return fromBot;
			currentState.get(fromBot).values.clear();
			if (instruction.lowToBot) {
				Bot lowBot = currentState.get(instruction.toLow);
				if (lowBot == null) {
					lowBot = new Bot();
					currentState.put(instruction.toLow, lowBot);
				}
				lowBot.values.add(low);
			}
			if (instruction.highToBot) {
				Bot highBot = currentState.get(instruction.toHigh);
				if (highBot == null) {
					highBot = new Bot();
					currentState.put(instruction.toHigh, highBot);
				}
				highBot.values.add(high);
			}
		}
	}

private int problemB() {
		
		List<Loader> loaders = new ArrayList<>();
		Map<Integer, Instruction> instructions = new HashMap<>();
		
		for (String line : LINES) {
			if (line.startsWith("bot")) {
				Matcher m = BOT_PAT.matcher(line);
				if (m.find()) {
					int fromBot = Integer.valueOf(m.group(1));
					boolean lowToBot = m.group(2).equals("bot");
					int toLow = Integer.valueOf(m.group(3));
					boolean highToBot = m.group(4).equals("bot");
					int toHigh = Integer.valueOf(m.group(5));
					instructions.put(fromBot, new Instruction(fromBot, lowToBot, toLow, highToBot, toHigh));
				}
			} else if (line.startsWith("value")) {
				Matcher m = VAL_PAT.matcher(line);
				if (m.find()) {
					int value = Integer.valueOf(m.group(1));
					int bot = Integer.valueOf(m.group(2));
					loaders.add(new Loader(bot, value));
				}
			} else {
				System.out.println("failed " + line);
			}
		}
		
		Map<Integer, Bot> currentState = new HashMap<>();
		for (Loader l : loaders) {
			Bot b = currentState.get(l.bot);
			if (b == null) {
				 b = new Bot();
					currentState.put(l.bot, b);
			}
			b.values.add(l.value);
		}
		
		Map<Integer, Integer> outputs = new HashMap<>();
		
		while (true) {
			int fromBot = currentState.entrySet().stream().filter(e -> e.getValue().values.size() == 2).map(e -> e.getKey()).findAny().get();
			Instruction instruction = instructions.get(fromBot);
			int low = currentState.get(fromBot).getLow();
			int high = currentState.get(fromBot).getHigh();
			currentState.get(fromBot).values.clear();
			if (instruction.lowToBot) {
				Bot lowBot = currentState.get(instruction.toLow);
				if (lowBot == null) {
					lowBot = new Bot();
					currentState.put(instruction.toLow, lowBot);
				}
				lowBot.values.add(low);
			} else {
				outputs.put(instruction.toLow, low);
			}
			if (instruction.highToBot) {
				Bot highBot = currentState.get(instruction.toHigh);
				if (highBot == null) {
					highBot = new Bot();
					currentState.put(instruction.toHigh, highBot);
				}
				highBot.values.add(high);
			} else {
				outputs.put(instruction.toHigh, high);
			}
			
			if (outputs.containsKey(0) && outputs.containsKey(1) && outputs.containsKey(2)) {
				return outputs.get(0) * outputs.get(1) * outputs.get(2);
			}
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {

		System.out.println("A: " + new Day10().problemA());
		System.out.println("B: " + new Day10().problemB());
	}
}
