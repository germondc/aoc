package test.clyde.aoc.aoc2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

public class Day14 {

	private static String INPUT = "ihaygndm";

	private MessageDigest MD;
	private Map<Integer, String> hashes = new HashMap<>();
	private int problemA() throws NoSuchAlgorithmException {
		//INPUT = "abc";
		MD = MessageDigest.getInstance("MD5");
		int i = 0;
		int keyCount = 0;
		while (keyCount < 64) {
			
			String hash = getHashForIndex(i);
			Character threeInARow=getThreeInARow(hash, 3);
			if (threeInARow != null && testFiveInArow(i+1, threeInARow)) {
				keyCount++;
			}
				
			i++;
		}

		return i-1;
	}
	
	private int problemB() throws NoSuchAlgorithmException {
		//INPUT = "abc";
		MD = MessageDigest.getInstance("MD5");
		int i = 0;
		int keyCount = 0;
		while (keyCount < 64) {
			
			String hash = getHashForIndexB(i);
			Character threeInARow=getThreeInARow(hash, 3);
			if (threeInARow != null && testFiveInArowB(i+1, threeInARow)) {
				keyCount++;
			}
				
			i++;
		}

		return i-1;
	}
	
	private boolean testFiveInArow(int startIndex, char c) {
		String seq = createFive(c);
		for (int j=0; j<1000; j++) {
			String nextHash = getHashForIndex(j+startIndex);
			if (nextHash.contains(seq))
				return true;
		}
		return false;
	}
	
	private boolean testFiveInArowB(int startIndex, char c) {
		String seq = createFive(c);
		for (int j=0; j<1000; j++) {
			String nextHash = getHashForIndexB(j+startIndex);
			if (nextHash.contains(seq))
				return true;
		}
		return false;
	}
	
	private String getHashForIndex(int index) {
		if (!hashes.containsKey(index)) {
			MD.update((INPUT + index).getBytes());
			String hash = Hex.encodeHexString(MD.digest());
			hashes.put(index, hash);
		}
		
		return hashes.get(index);
	}
	
	private String getHashForIndexB(int index) {
		if (!hashes.containsKey(index)) {
			MD.update((INPUT + index).getBytes());
			String hash = Hex.encodeHexString(MD.digest()).toLowerCase();
			for (int i=0; i<2016; i++) {
				MD.update(hash.getBytes());
				hash = Hex.encodeHexString(MD.digest()).toLowerCase();
			}
			hashes.put(index, hash);
		}
		
		return hashes.get(index);
	}
	
	private String createFive(Character c) {
		char[] ca = new char[] { c,c,c,c,c};
		return new String(ca);
	}
	
	private Character getThreeInARow(String key, int seqLen) {
		for (int i=0; i<=key.length()-seqLen; i++) {
			char c = key.charAt(i);
			boolean isGood = true;
			for (int j=1; j<seqLen; j++) {
				if (key.charAt(i+j) != c) {
					isGood = false;
					break;
				}
			}
			if (isGood) {
				return c;
			}
		}
		return null;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println("A: " + new Day14().problemA());
		System.out.println("B: " + new Day14().problemB());
	}
}
