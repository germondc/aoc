package test.clyde.aoc.aoc2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22 {
	
	private int bossDamage = 8;
	private int bossStartHp = 55;
	private int playerStartHp = 50;
	private int playerStartMana = 500;

	private enum Spell {
		MagicMissle(53,0,4,0),
		Drain(73,0,2,2),
		Shield(113,6,0,7),
		Poison(173,6,3,0),
		Recharge(229,5,0,101);

		int manaCost;
		int effectTime;
		int damage;
		int special;
		
		Spell(int manaCost, int effectTime, int damage,int special) {
			this.manaCost = manaCost;
			this.effectTime = effectTime;
			this.damage = damage;
			this.special = special;
		}
		
		public void cast(State state) {
			state.spellTimer.put(this, this.effectTime);
			if (this != Poison)
				state.bossHp -= this.damage;
			
			if (this==Drain) {
				state.playerHp += this.special;
			}
				
			state.playerMana -= this.manaCost;
		}
		
		public void takeEffect(State state) {
			if (state.spellTimer.get(this) > 0) {
				if (this==Shield) {
					state.playerArmor = this.special;
				} else if (this==Recharge) {
					state.playerMana += this.special;
				} else if (this==Poison) {
					state.bossHp -= this.damage;
				}
				state.spellTimer.put(this, state.spellTimer.get(this)-1);
			} else if (this==Shield) {
				state.playerArmor = 0;
			}
		}
	}
	
	private enum Status {
		PlayerDead,
		BossDead,
		OK
	}
	
	private class State implements Cloneable {
		int playerHp;
		int bossHp;
		int playerMana;
		int playerArmor;
		Map<Spell, Integer> spellTimer;
		
		public State() {
			playerMana = playerStartMana;
			bossHp = bossStartHp;
			playerHp = playerStartHp;
			spellTimer = new HashMap<>();
			for (Spell s : Spell.values()) {
				spellTimer.put(s, 0);
			}
		}
		
		public State(State state) {
			this.playerHp = state.playerHp;
			this.bossHp = state.bossHp;
			this.playerMana = state.playerMana;
			this.playerArmor = state.playerArmor;
			this.spellTimer = new HashMap<>();
			for (Spell s : Spell.values()) {
				this.spellTimer.put(s, state.spellTimer.get(s));
			}
		}
		
		public Status turns(Spell playerSpell, boolean isDifficult) {
			//player turn
			if (isDifficult)
				playerHp -= 1;
			if (playerHp <= 0)
				return Status.PlayerDead;
			for (Spell s : Spell.values()) {
				s.takeEffect(this);
			}
			playerSpell.cast(this);
			if (bossHp <= 0)
				return Status.BossDead;
			
			//boss turn
			for (Spell s : Spell.values()) {
				s.takeEffect(this);
			}
			if (bossHp <= 0)
				return Status.BossDead;
			playerHp -= (bossDamage - playerArmor);
			if (playerHp <= 0)
				return Status.PlayerDead;
			
			return Status.OK;
		}

		@Override
		protected State clone() throws CloneNotSupportedException {
			State clone = new State(this);
			return clone;
		}

		@Override
		public String toString() {
			return String.format("State [playerHp=%s, bossHp=%s, playerMana=%s, playerArmor=%s, spellTimer=%s]",
					playerHp, bossHp, playerMana, playerArmor, spellTimer);
		}
	}
	
	private List<Spell> getPossibleSpells(State currentState) {
		List<Spell> result = new ArrayList<>();
		for (Spell s : Spell.values()) {
			if (currentState.playerMana < s.manaCost)
				continue;
			if (currentState.spellTimer.get(s) > 1)
				continue;
			result.add(s);
		}
		return result;
	}
	
	private void test() {
		playerStartHp = 10;
		bossStartHp = 13;
		playerStartMana = 250;
		State state = new State();
		Status status = state.turns(Spell.Poison, false);
		status = state.turns(Spell.MagicMissle, false);
		System.out.println(status);
	}
	
	private void test1() {
		playerStartHp = 10;
		bossStartHp = 14;
		playerStartMana = 250;
		State state = new State();
		Status status = state.turns(Spell.Recharge, false);
		status = state.turns(Spell.Shield, false);
		status = state.turns(Spell.Drain, false);
		status = state.turns(Spell.Poison, false);
		status = state.turns(Spell.MagicMissle, false);
		System.out.println(status);
	}
	
	int best = Integer.MAX_VALUE;
	private int problemA() {
		best = Integer.MAX_VALUE;
		State state = new State();
		recurse(0, state, false);
		return best;
	}
	
	private int problemB() {
		best = Integer.MAX_VALUE;
		State state = new State();
		recurse(0, state, true);
		return best;
	}
	
	private void recurse(int currentMana, State currentState, boolean isDifficult) {
		for (Spell s : getPossibleSpells(currentState)) {
			State newState = new State(currentState);
			int manaUsed = currentMana + s.manaCost;
			Status status = newState.turns(s, isDifficult);
			if (status == Status.BossDead && manaUsed < best) {
				best = manaUsed;
				return;
			}
			if (status != Status.OK || manaUsed >= best) {
				return;
			}
			recurse(manaUsed, newState, isDifficult);
		}
	}
	
	public static void main(String[] args) {
//		new Day22().test();
//		new Day22().test1();
		
		System.out.println("A: " + new Day22().problemA());
		System.out.println("B: " + new Day22().problemB());
	}

}
