package test.clyde.aoc.aoc2015;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import test.clyde.aoc.utils.Utils;

public class Day12 {
	
	private static List<String> LINES = Utils.readFile("2015/Day12.txt");
	
	public static void main(String[] args) {
		new Day12().test();
	}
	
	private void test() {
		StringBuilder sb = new StringBuilder();
		LINES.stream().forEach(s -> sb.append(s));
		JSONObject jo = new JSONObject(sb.toString());
		System.out.println("A:" + getSum(jo));
		parseJSON(jo);
		System.out.println("B:" + getSum(jo));
	}
	
	private int getSum(JSONObject jo) {
		int total = 0;
		for (String s : jo.keySet()) {
			Object o = jo.get(s);
			total += getSumObject(o);
		}
		return total;
	}
	
	private int getSumObject(Object o) {
		if (o instanceof JSONObject) {
			return getSum((JSONObject) o);
		} else if (o instanceof JSONArray) {
			JSONArray ja = (JSONArray) o;
			return getSumArray(ja);
		} else if (o instanceof String) {
			return 0;
		} else if (o instanceof Integer) {
			return ((Integer)o);
		} else {
			return 0;
		}
	}
	
	private int getSumArray(JSONArray ja) {
		int runningTotal = 0;
		for (Object o : ja) {
			runningTotal += getSumObject(o);
		}
		return runningTotal;
	}
	
	private boolean parseJSON(JSONObject jo) {
		Iterator<String> iterator = jo.keys();
		while (iterator.hasNext()) {
			String s = iterator.next();
			Object o = jo.get(s);
			if (parseObject(o)) {
				if (o instanceof JSONObject) {
					iterator.remove();
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean parseArray(JSONArray array) {
		Iterator<Object> iterator = array.iterator();
		while (iterator.hasNext()) {
			Object o = iterator.next();
			if (parseObject(o) && o instanceof JSONObject) {
				iterator.remove();
			}
		}
		return false;
	}
	
	private boolean parseObject(Object o) {
		if (o instanceof JSONObject) {
			return parseJSON((JSONObject) o);
		} else if (o instanceof JSONArray) {
			JSONArray ja = (JSONArray) o;
			return parseArray(ja);
		} else if (o instanceof String) {
			String value = (String) o;
			if (value.equals("red"))
				return true;
		} else if (o instanceof Integer) {
			
		} else {
			return false;
		}
		return false;
	}
}
