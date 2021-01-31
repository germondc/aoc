package test.clyde.aoc.aoc2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day3 {
	
	private class Address {
		int x;
		int y;
		
		public Address(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + x;
			result = prime * result + y;
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
			Address other = (Address) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		private Day3 getEnclosingInstance() {
			return Day3.this;
		}
	}
	
	private static final List<Character> CHARS = Utils.readChars("Day3.txt");
	
	public static void main(String[] args) {
		System.out.println("A: " + new Day3().getHouses());
		System.out.println("B: " + new Day3().getHousesB());
	}
	
	private int getHouses() {
		Map<Address, Integer> vists = new HashMap<>();
		
		int x = 0;
		int y = 0;
		addVists(vists, new Address(x, y));
		
		for (char c : CHARS) {
			if (c == '>')
				x += 1;
			else if (c == '<')
				x -= 1;
			else if (c == '^')
				y -= 1;
			else if (c == 'v')
				y += 1;
			addVists(vists, new Address(x, y));
		}
		return vists.size();
	}
	
	private int getHousesB() {
		Map<Address, Integer> vists = new HashMap<>();
		
		int xS = 0;
		int yS = 0;
		int xR = 0;
		int yR = 0;
		addVists(vists, new Address(xS, yS));
		addVists(vists, new Address(xR, yR));
		
		for (int i=0; i<CHARS.size(); i += 2) {
			char cS = CHARS.get(i);
			char cR = CHARS.get(i+1);
			if (cS == '>')
				xS += 1;
			else if (cS == '<')
				xS -= 1;
			else if (cS == '^')
				yS -= 1;
			else if (cS == 'v')
				yS += 1;
			addVists(vists, new Address(xS, yS));
			
			if (cR == '>')
				xR += 1;
			else if (cR == '<')
				xR -= 1;
			else if (cR == '^')
				yR -= 1;
			else if (cR == 'v')
				yR += 1;
			addVists(vists, new Address(xR, yR));
		}
		return vists.size();
	}

	private void addVists(Map<Address, Integer> vists, Address address) {
		int vistsInc;
		if (vists.containsKey(address)) {
			vistsInc = vists.get(address) + 1;
		} else {
			vistsInc = 1;
		}
		vists.put(address, vistsInc);
	}
}
