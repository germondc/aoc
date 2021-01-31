package test.clyde.aoc.aoc2015;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

public class Day4 {
	public static void main(String[] args) {
		System.out.println("TEST: " + new Day4().getLowestValue("abcdef"));
		System.out.println("TEST: " + new Day4().getLowestValue("pqrstuv"));
		System.out.println("A: " + new Day4().getLowestValue("ckczppom"));
		System.out.println("B: " + new Day4().getLowestValue("ckczppom", "000000"));
	}

	private int getLowestValue(String secret) {
		return getLowestValue(secret, "00000");
	}
	
	private int getLowestValue(String secret, String zeros) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			int i = 0;
			while (true) {
				md.update((secret + i).getBytes());
				String hash = Hex.encodeHexString(md.digest());
				if (hash.startsWith(zeros))
					return i;
				i++;
			}
		} catch (Exception e) {
			throw new RuntimeException("failed", e);
		}
	}
}
