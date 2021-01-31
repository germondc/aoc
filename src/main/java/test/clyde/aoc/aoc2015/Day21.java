package test.clyde.aoc.aoc2015;

public class Day21 {
	
	int bossHp = 109;
	int bossDamage = 8;
	int bossArmor = 2;
	
	int playerHp = 100;
	
	private int[] weapons_cost = { 8,10,25,40,74};
	private int[] armor_cost = { 13,31,53,75,102};
	private int[] ring_cost = { 25,50,100,20,40,80};
	
	private void test() {
		int weaponDamage = 0;
		int weaponCost = 0;
		int armorArmor = 0;
		int armorCost = 0;
		int ring1Armor = 0;
		int ring1Damage = 0;
		int ring1Cost = 0;
		int ring2Armor = 0;
		int ring2Damage = 0;
		int ring2Cost = 0;
		
		int cheapest = Integer.MAX_VALUE;
		
		for (int weapon=0; weapon<weapons_cost.length; weapon++) {
			weaponDamage = weapon + 4;
			weaponCost = weapons_cost[weapon];
			for (int armor=-1; armor < armor_cost.length; armor++) {
				armorArmor = armor + 1;
				if (armor > -1)
					armorCost = armor_cost[armor];
				else
					armorCost = 0;
				for (int ring1=-1; ring1<ring_cost.length; ring1++) {
					ring1Armor = getRingArmor(ring1);
					ring1Damage = getRingDamage(ring1);
					if (ring1 > -1)
						ring1Cost = ring_cost[ring1];
					else
						ring1Cost = 0;
					for (int ring2=-1; ring2<ring_cost.length; ring2++) {
						if (ring2==ring1)
							continue;
						ring2Armor = getRingArmor(ring2);
						ring2Damage = getRingDamage(ring2);
						if (ring2 > -1)
							ring2Cost = ring_cost[ring2];
						else
							ring2Cost = 0;
						int playerDamage = weaponDamage + ring1Damage + ring2Damage;
						int playerArmor = armorArmor + ring1Armor + ring2Armor;
						
						if (!wins(playerDamage, playerArmor))
								continue;
						
						int totalGold = weaponCost + armorCost + ring1Cost + ring2Cost;
						if (totalGold < cheapest)
							cheapest = totalGold;
					}
				}
			}
		}
		
		System.out.println("A: " + cheapest);
	}
	
	private void testB() {
		int weaponDamage = 0;
		int weaponCost = 0;
		int armorArmor = 0;
		int armorCost = 0;
		int ring1Armor = 0;
		int ring1Damage = 0;
		int ring1Cost = 0;
		int ring2Armor = 0;
		int ring2Damage = 0;
		int ring2Cost = 0;
		
		int most = 0;
		
		for (int weapon=0; weapon<weapons_cost.length; weapon++) {
			weaponDamage = weapon + 4;
			weaponCost = weapons_cost[weapon];
			for (int armor=-1; armor < armor_cost.length; armor++) {
				armorArmor = armor + 1;
				if (armor > -1)
					armorCost = armor_cost[armor];
				else
					armorCost = 0;
				for (int ring1=-1; ring1<ring_cost.length; ring1++) {
					ring1Armor = getRingArmor(ring1);
					ring1Damage = getRingDamage(ring1);
					if (ring1 > -1)
						ring1Cost = ring_cost[ring1];
					else
						ring1Cost = 0;
					for (int ring2=-1; ring2<ring_cost.length; ring2++) {
						if (ring2==ring1)
							continue;
						ring2Armor = getRingArmor(ring2);
						ring2Damage = getRingDamage(ring2);
						if (ring2 > -1)
							ring2Cost = ring_cost[ring2];
						else
							ring2Cost = 0;
						int playerDamage = weaponDamage + ring1Damage + ring2Damage;
						int playerArmor = armorArmor + ring1Armor + ring2Armor;
						
						if (wins(playerDamage, playerArmor))
								continue;
						
						int totalGold = weaponCost + armorCost + ring1Cost + ring2Cost;
						if (totalGold > most)
							most = totalGold;
					}
				}
			}
		}
		
		System.out.println("B: " + most);
	}
	
	private int getRingDamage(int index) {
		if (index == -1 || index > 2)
			return 0;
		return index + 1;
	}
	
	private int getRingArmor(int index) {
		if (index < 3)
			return 0;
		return index - 2;
	}
	
	private boolean wins(int playerDamage, int playerArmor) {
		int damageToPlayer = bossDamage - playerArmor;
		if (damageToPlayer < 1)
			damageToPlayer = 1;
		int damageToBoss = playerDamage - bossArmor;
		if (damageToBoss < 1)
			damageToBoss = 1; 
		return (bossHp / damageToBoss) <= (playerHp / damageToPlayer);
	}
	
	public static void main(String[] argv) {
		new Day21().test();
		new Day21().testB();
	}
	
}
