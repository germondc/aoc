package test.clyde.aoc.aoc2019;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.GoalSeek;
import test.clyde.aoc.utils.Utils;

public class Day14 {
	private static List<String> INPUT = Utils.readFile("2019/Day14.txt");
	private static List<String> TESTA = Utils.readFile("2019/Day14a.txt");
	private static List<String> TESTB = Utils.readFile("2019/Day14b.txt");
	private static List<String> TESTC = Utils.readFile("2019/Day14c.txt");
	private static List<String> TESTD = Utils.readFile("2019/Day14d.txt");
	private static List<String> TESTE = Utils.readFile("2019/Day14e.txt");

	private List<String> input;
	
	public Day14(List<String> input) {
		super();
		this.input = input;
	}

	private class Reaction {
		Map<Element, Long> ingredients = new HashMap<>();
		Element output;
		long outputYield;

		@Override
		public String toString() {
			return String.format("Reaction [%s => %s %s]", ingredients, outputYield, output);
		}
	}

	private class Element {
		String name;
		int level = -1;

		public Element(String name) {
			super();
			this.name = name;
		}

		@Override
		public String toString() {
			return String.format("%s", name);
		}
	}

	private Map<String, Reaction> getReactions(List<String> input) {
		Map<String, Reaction> reactions = new HashMap<>();
		Map<String, Element> elements = new HashMap<>();
		Pattern ingre = Pattern.compile("(\\d+) ([^ ,]+)[, ]");
		Pattern outputPat = Pattern.compile("=> (\\d+) ([^ ]+)");
		for (String line : input) {
			Reaction reaction = new Reaction();
			Matcher m = ingre.matcher(line);
			while (m.find()) {
				long amount = Long.valueOf(m.group(1));
				String name = m.group(2);
				Element e = new Element(name);
				if (name.equals("ORE"))
					e.level = 0;
				elements.putIfAbsent(name, e);
				reaction.ingredients.put(elements.get(name), amount);
			}
			m = outputPat.matcher(line);
			if (m.find()) {
				String name = m.group(2);
				elements.putIfAbsent(name, new Element(name));
				reaction.output = elements.get(name);
				reaction.outputYield = Long.valueOf(m.group(1));
			}
			reactions.put(reaction.output.name, reaction);
		}
		return reactions;
	}

	private long problemA() {
		Map<String, Reaction> reactions = getReactions(input);
		updateLevels(reactions);
		long ore = getIngredientsToCreate(reactions, 1L);
		return ore;
	}

	private void updateLevels(Map<String, Reaction> reactions) {
		boolean allDone = false;
		while (!allDone) {
			allDone = true;

			for (Reaction r : reactions.values()) {
				long undefined = r.ingredients.keySet().stream().filter(e -> e.level == -1).count();
				if (undefined == 0L) {
					int maxLevel = r.ingredients.keySet().stream().mapToInt(e -> e.level).max().getAsInt();
					r.output.level = maxLevel + 1;
				} else {
					allDone = false;
				}
			}
		}
	}

	private long getIngredientsToCreate(Map<String, Reaction> reactions, long initialMultiplier) {
		Reaction fuel = reactions.get("FUEL");
		fuel.outputYield *= initialMultiplier;
		fuel.ingredients.entrySet().stream()
				.forEach(e -> fuel.ingredients.put(e.getKey(), e.getValue() * initialMultiplier));
		boolean maveMore = true;
		while (maveMore) {
			int maxLevel = fuel.ingredients.entrySet().stream().filter(e -> e.getValue() > 0)
					.mapToInt(e -> e.getKey().level).max().getAsInt();
			Map<Element, Long> updateIngredients = new HashMap<>(fuel.ingredients);
			for (Map.Entry<Element, Long> entry : updateIngredients.entrySet()) {
				if (entry.getKey().level != maxLevel)
					continue;
				long amount = entry.getValue();
				Reaction next = reactions.get(entry.getKey().name);
				long multiplier = (long) Math.ceil((double) amount / (double) next.outputYield);
				long surplus = amount - next.outputYield * multiplier;
				fuel.ingredients.put(entry.getKey(), surplus);
				for (Map.Entry<Element, Long> nextEntry : next.ingredients.entrySet()) {
					fuel.ingredients.putIfAbsent(nextEntry.getKey(), 0L);
					fuel.ingredients.put(nextEntry.getKey(),
							fuel.ingredients.get(nextEntry.getKey()) + (nextEntry.getValue() * multiplier));
				}
			}

			maveMore = fuel.ingredients.entrySet().stream().anyMatch(e -> e.getKey().level > 0 && e.getValue() > 0);
		}

		return fuel.ingredients.entrySet().stream().filter(e -> e.getKey().name.equals("ORE")).map(e -> e.getValue())
				.findFirst().get();
	}

	private long problemB() {
		return GoalSeek.build(i -> {
			Map<String, Reaction> reactions = getReactions(input);
			updateLevels(reactions);
			return getIngredientsToCreate(reactions, i);
		}).seek(1000000000000L);
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day14(TESTA).problemA());
		System.out.println("TestA: " + new Day14(TESTB).problemA());
		System.out.println("TestA: " + new Day14(TESTC).problemA());
		System.out.println("TestA: " + new Day14(TESTD).problemA());
		System.out.println("TestA: " + new Day14(TESTE).problemA());
		System.out.println("A: " + new Day14(INPUT).problemA());
		System.out.println("TestB: " + new Day14(TESTC).problemB());
		System.out.println("TestB: " + new Day14(TESTD).problemB());
		System.out.println("TestB: " + new Day14(TESTE).problemB());
		System.out.println("B: " + new Day14(INPUT).problemB());
	}
}
