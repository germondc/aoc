package test.clyde.aoc.aoc2015;

import test.clyde.aoc.utils.Utils;

public class Day20 {
	
	private void test() {
		int input = 2900000;
		
		int house = 100;
		while (true) {
			if (Utils.divSum(house) >= input) {
				break;
			}
			house++;
		}
		System.out.println("A: " + house);
	}
	
	private void testB() {
		int input = 29000000;
		
		int house = 100;
		while (true) {
			if (divSum(house)*11 >= input) {
				break;
			}
			house++;
		}
		System.out.println("B: " + house);
	}

	public static void main(String[] argv) {
		new Day20().test();
		new Day20().testB();
	}
	
	public int divSum(int n) {
		if (n == 1)
			return 1;

		int result = 0;

		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {

				if (i == (n / i)) {
					if (i*50 >= n)
						result += i;
				} else {
					if (i*50 >= n)
						result += i;
					if (i <= 50)
						result += n / i;
				}
			}
		}

		return (result + n);
	}
}
