package test.clyde.aoc.aoc2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day9 {

	Pattern PATTERN = Pattern.compile("(.*) to (.*) = (.*)");

	private static List<String> LINES = Utils.readFile("2015/Day9.txt");

	public static void main(String[] args) {
		System.out.println("A: " + new Day9().getMinDis());
		System.out.println("B: " + new Day9().getMaxDis());
	}
	
	private class IntWrap {
		Integer i;
		
		public IntWrap(Integer i) {
			this.i = i;
		}

		@Override
		public String toString() {
			return String.format("IntWrap [i=%s]", i);
		}
	}

	private class Destination {
		String city;
		int distance;

		public Destination(String city, int distance) {
			this.city = city;
			this.distance = distance;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((city == null) ? 0 : city.hashCode());
			result = prime * result + distance;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Destination other = (Destination) obj;
			if (city == null) {
				if (other.city != null)
					return false;
			} else if (!city.equals(other.city))
				return false;
			if (distance != other.distance)
				return false;
			return true;
		}

		private Day9 getEnclosingInstance() {
			return Day9.this;
		}
	}

	private int getMinDis() {

		Map<String, List<Destination>> destinations = new HashMap<>();
		for (String line : LINES) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}
			int dist = Integer.valueOf(m.group(3));
			String locationA = m.group(1);
			List<Destination> cityDest = destinations.get(locationA);
			if (cityDest == null) {
				cityDest = new ArrayList<>();
				destinations.put(locationA, cityDest);
			}
			String locationB = m.group(2);
			cityDest.add(new Destination(locationB, dist));
			
			cityDest = destinations.get(locationB);
			if (cityDest == null) {
				cityDest = new ArrayList<>();
				destinations.put(locationB, cityDest);
			}
			cityDest.add(new Destination(locationA, dist));
		}

		IntWrap shortestDist =new IntWrap(Integer.MAX_VALUE);
		for (String city : destinations.keySet()) {
			List<String> alreadyBeen = new ArrayList<>();
			alreadyBeen.add(city);
			List<Destination> possibleDest = possibleDest(destinations, city, alreadyBeen);
			getDistance(destinations, possibleDest, new IntWrap(0), shortestDist, alreadyBeen);
		}

		return shortestDist.i;
	}

	private void getDistance(Map<String, List<Destination>> destinations, List<Destination> possibleDest, IntWrap currentDistance, IntWrap currentBest, List<String> alreadyBeen) {
		if (possibleDest.size() == 0 && currentDistance.i < currentBest.i) {
			currentBest.i = currentDistance.i;
		}
		for (Destination dest : possibleDest) {
			int newDist = currentDistance.i + dest.distance;
			if (newDist > currentBest.i)
				return;
			List<String> newBeen = new ArrayList<>(alreadyBeen);
			newBeen.add(dest.city);

			List<Destination> restOfDest = possibleDest(destinations, dest.city, newBeen);
			getDistance(destinations, restOfDest, new IntWrap(newDist), currentBest, newBeen);

		}
	}
	
	private List<Destination> possibleDest(Map<String, List<Destination>> destinations, String city, List<String> alreadyBeen) {
		List<Destination> allDest = destinations.get(city);
		List<Destination> restOfDest = new ArrayList<>();
		for (Destination d : allDest) {
			if (!alreadyBeen.contains(d.city))
				restOfDest.add(d);
		}
		return restOfDest;
	}
	
	private int getMaxDis() {

		Map<String, List<Destination>> destinations = new HashMap<>();
		for (String line : LINES) {
			Matcher m = PATTERN.matcher(line);
			if (!m.find()) {
				System.out.println("failed to parse line: " + line);
				continue;
			}
			int dist = Integer.valueOf(m.group(3));
			String locationA = m.group(1);
			List<Destination> cityDest = destinations.get(locationA);
			if (cityDest == null) {
				cityDest = new ArrayList<>();
				destinations.put(locationA, cityDest);
			}
			String locationB = m.group(2);
			cityDest.add(new Destination(locationB, dist));
			
			cityDest = destinations.get(locationB);
			if (cityDest == null) {
				cityDest = new ArrayList<>();
				destinations.put(locationB, cityDest);
			}
			cityDest.add(new Destination(locationA, dist));
		}

		IntWrap shortestDist =new IntWrap(0);
		for (String city : destinations.keySet()) {
			List<String> alreadyBeen = new ArrayList<>();
			alreadyBeen.add(city);
			List<Destination> possibleDest = possibleDest(destinations, city, alreadyBeen);
			getDistanceB(destinations, possibleDest, new IntWrap(0), shortestDist, alreadyBeen);
		}

		return shortestDist.i;
	}

	private void getDistanceB(Map<String, List<Destination>> destinations, List<Destination> possibleDest, IntWrap currentDistance, IntWrap currentBest, List<String> alreadyBeen) {
		if (possibleDest.size() == 0 && currentDistance.i > currentBest.i) {
			currentBest.i = currentDistance.i;
		}
		for (Destination dest : possibleDest) {
			int newDist = currentDistance.i + dest.distance;
			List<String> newBeen = new ArrayList<>(alreadyBeen);
			newBeen.add(dest.city);

			List<Destination> restOfDest = possibleDest(destinations, dest.city, newBeen);
			getDistanceB(destinations, restOfDest, new IntWrap(newDist), currentBest, newBeen);

		}
	}
}
