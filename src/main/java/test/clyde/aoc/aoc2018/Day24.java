package test.clyde.aoc.aoc2018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day24 {
	private static List<String> LINES = Utils.readFile("2018/Day24.txt");
	private static List<String> TEST = Utils.readFile("2018/Day24a.txt");

	private class Army implements Comparable<Army> {
		boolean isImmune;
		int units;
		int hp;
		String[] immune;
		String[] weakness;
		int attack;
		String attackType;
		int initiative;

		public int getEffectivePower() {
			return units * attack;
		}

		public Army(boolean isImmune, int units, int hp, String[] immune, String[] weakness, int attack,
				String attackType, int initiative) {
			this.isImmune = isImmune;
			this.units = units;
			this.hp = hp;
			this.immune = immune;
			this.weakness = weakness;
			this.attack = attack;
			this.attackType = attackType;
			this.initiative = initiative;
			if (this.immune == null)
				this.immune = new String[0];
			if (this.weakness == null)
				this.weakness = new String[0];
		}

		public int takeAttack(Army attacker) {
			int damage = calculateDamage(attacker);
			int unitsKilled = (int) Math.floor(damage / hp);
			this.units -= unitsKilled;
			return unitsKilled;
		}

		public int calculateDamage(Army attacker) {
			for (String i : immune) {
				if (i.equals(attacker.attackType))
					return 0;
			}
			boolean isDouble = false;
			for (String w : weakness) {
				if (w.equals(attacker.attackType))
					isDouble = true;
			}
			return attacker.getEffectivePower() * (isDouble ? 2 : 1);
		}

		@Override
		public int compareTo(Army o) {
			if (this.getEffectivePower() > o.getEffectivePower())
				return -1;
			if (this.getEffectivePower() < o.getEffectivePower())
				return 1;
			if (this.initiative > o.initiative)
				return -1;
			else
				return 1;
		}

		@Override
		public String toString() {
			return String.format(
					"Army [isImmune=%s, units=%s, hp=%s, immune=%s, weakness=%s, attack=%s, attackType=%s, initiative=%s]",
					isImmune, units, hp, Arrays.toString(immune), Arrays.toString(weakness), attack, attackType,
					initiative);
		}
	}
	
	private long problemA(List<String> lines) {
		return Math.abs(solve(lines, 0));
	}

	private long solve(List<String> lines, int boost) {
		Pattern pat = Pattern.compile(
				"(\\d+) units each with (\\d+) hit points (.*)with an attack that does (\\d+) (.+) damage at initiative (\\d+)");
		Pattern weak_pat = Pattern.compile("weak to ([^;\\)]+)");
		Pattern immune_pat = Pattern.compile("immune to ([^;\\)]+)");
		
		List<Army> armies = new ArrayList<>();

		boolean isImmune = true;
		for (String line : lines) {
			if (line.contains("Immune") || line.length() == 0) {
				continue;
			} else if (line.contains("Infection")) {
				isImmune = false;
				continue;
			}

			Matcher m = pat.matcher(line);
			if (m.find()) {
				int units = Integer.valueOf(m.group(1));
				int hp = Integer.valueOf(m.group(2));
				
				String stats = m.group(3);
				Matcher weakM = weak_pat.matcher(stats);
				String[] weakness = null;
				if (weakM.find()) {
					weakness = weakM.group(1).split(", ");
				}
				
				String[] immune = null;
				Matcher immuneM = immune_pat.matcher(stats);
				if (immuneM.find()) {
					immune = immuneM.group(1).split(", ");
				}
				int attack = Integer.valueOf(m.group(4)) + (isImmune ? boost : 0);
				String attackType = m.group(5);
				int initiative = Integer.valueOf(m.group(6));
				Army army = new Army(isImmune, units, hp, immune, weakness, attack, attackType, initiative);
				armies.add(army);
			}
		}

		while (!isOver(armies)) {

			Collections.sort(armies);
			Map<Army, Army> round = new HashMap<>();
			for (Army attack : armies) {
				int best = 0;
				Army target = null;
				for (Army def : armies) {
					if (def == attack || def.isImmune == attack.isImmune || round.containsValue(def))
						continue;
					int possibleDamage = def.calculateDamage(attack);
					if (possibleDamage == 0)
						continue;
					if (possibleDamage > best) {
						best = possibleDamage;
						target = def;
					} else if (possibleDamage == best) {
						if (def.getEffectivePower() > target.getEffectivePower()) {
							target = def;
						} else if (def.getEffectivePower() == target.getEffectivePower()) {
							if (def.initiative > target.initiative) {
								target = def;
							}
						}
					}
				}
				if (target != null) {
					round.put(attack, target);
				}
			}
			Collections.sort(armies, attackComparator());
			int totalUnitsKilled = 0;
			for (Army attack : armies) {
				if (attack.units <= 0)
					continue;
				Army def = round.get(attack);
				if (def == null)
					continue;
				totalUnitsKilled += def.takeAttack(attack);
			}
			armies.removeIf(a -> a.units <= 0);
			if (totalUnitsKilled == 0)
				return -1;
		}

		long count = armies.stream().mapToLong(a -> a.units).sum();
		if (!armies.get(0).isImmune)
			count *= -1;
		return count;
	}

	private boolean isOver(List<Army> armies) {
		long count1 = armies.stream().filter(a -> a.isImmune).count();
		long count2 = armies.stream().filter(a -> !a.isImmune).count();
		return count1 == 0 || count2 == 0;
	}

	private Comparator<Army> attackComparator() {
		return new Comparator<Army>() {
			@Override
			public int compare(Army o1, Army o2) {
				return o2.initiative - o1.initiative;
			}
		};
	}

	private long problemB(List<String> lines) {
		long result = -1;
		int boost = 1;
		while (result < 0) {
			result = solve(lines, boost++);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("TestA: " + new Day24().problemA(TEST));
		System.out.println("A: " + new Day24().problemA(LINES));
		System.out.println("TestB: " + new Day24().solve(TEST, 1570));
		System.out.println("B: " + new Day24().problemB(LINES));
	}

}
