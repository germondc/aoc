package test.clyde.aoc.aoc2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class Day5 {

	private String problemA() {

		String code = "";
		int index = 0;
		String input = "cxdnnyjw";
		//input = "abc";
		while (code.length() < 8) {
			String hashed = getHexHash(input + index);
			if (index % 1000000 == 0)
				System.out.println(".");
			if (hashed.substring(0, 5).equals("00000")) {
				code += hashed.charAt(5);
			}
			index++;
		}

		return code;
	}

	private String problemB() {

		String code = "aaaaaaaa";
		
		int index = 0;
		String input = "cxdnnyjw";
		//input = "abc";
		int test = 255;
		while (test != 0) {
			String hashed = getHexHash(input + index);
			if (index % 1000000 == 0)
				System.out.println(".");
			if (hashed.substring(0, 5).equals("00000")) {
				try {
					int pos = Integer.valueOf(new Character(hashed.charAt(5)).toString());
					if (pos < 8 && (test & (1 << pos)) == (1 << pos)) {
						code = code.substring(0,pos)+hashed.charAt(6)+code.substring(pos+1);
						
						test -= (1 << pos);
					}
				} catch (Exception e) {}
				
			}
			index++;
		}

		return code;
	}

	private String getHexHash(String password) {
		try {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		return Hex.encodeHexString(digest).toUpperCase();
		} catch (Exception e) {
			throw new RuntimeException("failed to generate md5", e);
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {

		//System.out.println("A: " + new Day5().problemA());
		System.out.println("B: " + new Day5().problemB());
	}
}
