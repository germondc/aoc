package test.clyde.aoc.utils;

import java.util.ArrayList;
import java.util.List;

public class NumberUtils {
	public static List<Long> primeFactors(long n) {
		List<Long> result = new ArrayList<>();
		while (n % 2 == 0) {
			result.add(2L);
			n /= 2;
		}

		for (long i = 3; i <= Math.sqrt(n); i += 2) {
			while (n % i == 0) {
				result.add(i);
				n /= i;
			}
		}

		if (n > 2)
			result.add(n);
		return result;
	}
	
	public static long lowestCommonMultiple(List<Long> numbers) {
		long lcm = numbers.get(0);
		long gcd = numbers.get(0);

		for (int i = 0; i < numbers.size(); i++) {
			gcd = greatestCommonDivisor(numbers.get(i), lcm);
			lcm = (lcm * numbers.get(i)) / gcd;
		}
		return lcm;
	}

	public static long greatestCommonDivisor(long a, long b) {
		if (b == 0)
			return a;

		return greatestCommonDivisor(b, a % b);
	}
}
