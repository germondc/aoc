package test.clyde.aoc.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day13 {

	private static List<String> LINES = Utils.readFile("2020/Day13.txt");

	public static void main(String[] args) {
		System.out.println("A: " + new Day13().problemA(LINES));
		// System.out.println("B: " + new Day13().problemB(LINES));
		System.out.println("B: " + new Day13().problemBB(LINES));
		System.out.println("B test: " + new Day13().part2(LINES));
	}

	private long problemA(List<String> lines) {
		long timestamp = Long.valueOf(LINES.get(0));
		String[] split = LINES.get(1).split(",");

		List<Integer> busses = new ArrayList<>();
		for (String bus : split) {
			if (!bus.equals("x")) {
				busses.add(Integer.valueOf(bus));
			}
		}

		Map<Long, Integer> closest = new HashMap<>();
		for (int bus : busses) {
			long index = 0;
			while (index < timestamp) {
				index += bus;
			}
			closest.put(index, bus);
		}

		long value = closest.keySet().stream().mapToLong(l -> l).min().getAsLong();
		int bus = closest.get(value);

		return (value - timestamp) * bus;
	}

	// Brute force
	private long problemB(List<String> lines) {
		String[] split = LINES.get(1).split(",");
//		split = "7,13,x,x,59,x,31,19".split(",");

		Map<Integer, Integer> busses = new HashMap<>();
		int index = 0;
		for (String bus : split) {
			if (!bus.equals("x")) {
				busses.put(Integer.valueOf(bus), index);
			}
			index++;
		}

		int biggest = busses.keySet().stream().mapToInt(i -> i).max().getAsInt();
		int diffForBiggest = busses.get(biggest);
		long timestamp = -diffForBiggest;
		boolean notDone = true;
		while (notDone) {
			timestamp += biggest;
			boolean allGood = true;
			for (Map.Entry<Integer, Integer> entry : busses.entrySet()) {
				int bus = entry.getKey();
				int diff = entry.getValue();
				if ((timestamp + diff) % bus != 0) {
					allGood = false;
					break;
				}
			}
			if (allGood)
				notDone = false;
		}

		return timestamp;
	}

	private long problemBB(List<String> lines) {
		String[] split = LINES.get(1).split(",");
//		split = "7,13,x,x,59,x,31,19".split(",");

		Map<Integer, Integer> busses = new HashMap<>();
		int index = 0;
		for (String bus : split) {
			if (!bus.equals("x")) {
				busses.put(Integer.valueOf(bus), index);
			}
			index++;
		}

		int biggest = busses.keySet().stream().mapToInt(i -> i).max().getAsInt();
		int biggest2 = busses.keySet().stream().filter(i -> i != biggest).mapToInt(i -> i).max().getAsInt();
		int diffForBiggest = busses.get(biggest);
		int diffForBiggest2 = busses.get(biggest2);
		long timestamp = -diffForBiggest;
		while ((timestamp + diffForBiggest2) % biggest2 != 0) {
			timestamp += biggest;
		}

		long newBiggest = biggest * biggest2;
		boolean allGood = false;
		while (!allGood) {
			timestamp += newBiggest;
			allGood = true;
			for (Map.Entry<Integer, Integer> entry : busses.entrySet()) {
				int bus = entry.getKey();
				int diff = entry.getValue();
				if ((timestamp + diff) % bus != 0) {
					allGood = false;
					break;
				}
			}
		}

		return timestamp;
	}
	
	
	
	
	public long part2(List<String> input) {
	    //		https://www.youtube.com/watch?v=zIFehsBHB8o
	    //		**chinese remainder theorem**
	    //		solves system of congruences in form of... 
	    //		x % n == b

	    //		sample input: 17,x,13,19 
	    //		system of congruences.. 
	    //		t % 17 == 0 
	    //		(t+2) % 13 == 0
	    //		(t+3) % 19 == 0

	    //		rearrange to fit x%n==b format 
	    //		t%17 == 0
	    //		t%13 == -2 (mod 13) == 11
	    //		t%19 == -3 (mod 19) == 16

	    //		ns[] = {17, 13, 19}
	    //		bs[] = {0, 11, 16}
	    //		Ns[] = {247, 323, 221}
	    //		xs[] = {2, 6, 8}

	    //		t = summation(bs * Ns * xs)
	    String[] busses = input.get(1).split(",");  

			ArrayList<Integer> ns = new ArrayList<Integer>(); // n[i] == busID
			ArrayList<Integer> bs = new ArrayList<Integer>(); // b[i] == (-1*i)%busID + busID
			ArrayList<Long> Ns = new ArrayList<Long>(); // N[i] == N/n[i] given N == prod of all n[i]
			ArrayList<Integer> xs = new ArrayList<Integer>(); // xs == inverse of N[i]... found manually by getInverse()
																// function

			long N = 1; // N == product of all ns
			for (int i = 0; i < busses.length; i++) {
				if (!busses[i].equals("x")) {
					ns.add(Integer.parseInt(busses[i]));
					bs.add((-1 * i) % Integer.parseInt(busses[i]) + Integer.parseInt(busses[i]));
					N *= Integer.parseInt(busses[i]);
				}
			}

			// populate Ns[] and xs[]
			for (int i = 0; i < ns.size(); i++) {
				Ns.add(N / ns.get(i));
				xs.add(getInverse(Ns.get(i), ns.get(i)));
			}

			// time to get an answer
			long sum = 0;
			for (int i = 0; i < ns.size(); i++) {
				sum += bs.get(i) * Ns.get(i) * xs.get(i);
			}
			sum %= N;
			return sum;
	  }

	  private static int getInverse(long Ni, int ni) {
			// Ni * xi == 1 (mod ni)
			// incrememnt xi until solution is found
			int xi = 1;
			while ((Ni * xi) % ni != 1) {
				xi++;
			}
			return xi;
		}
}
