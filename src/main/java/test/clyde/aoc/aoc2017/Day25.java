package test.clyde.aoc.aoc2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.clyde.aoc.utils.Utils;

public class Day25 {

	private static List<String> LINES = Utils.readFile("2017/Day25.txt");
	private static List<String> TEST_LINES = Utils.readFile("2017/Day25a.txt");

	private class StateItem {
		boolean value;
		boolean isRight;
		String onto;
		public StateItem(boolean value, boolean isRight, String onto) {
			super();
			this.value = value;
			this.isRight = isRight;
			this.onto = onto;
		}
	}
	
	private class State {
		final List<StateItem> states = new ArrayList<>(2);
	}
	
	private long problemA(List<String> lines) {
		long steps = Long.valueOf(lines.get(1).split(" ")[5]);
		
		Map<String, State> states = new HashMap<>();
		
		int index = 3;
		while (index<lines.size()) {
			State state = new State();
			String stateName = Character.toString(lines.get(index).split(" ")[2].charAt(0));
			index += 2;
			boolean value1 = lines.get(index).split(" ")[8].replace(".", "").equals("1");
			boolean isRight1 = lines.get(++index).split(" ")[10].replace(".", "").equals("right");
			String onto1 = lines.get(++index).split(" ")[8].replace(".", "");
			state.states.add(new StateItem(value1, isRight1, onto1));
			index += 2;
			boolean value2 = lines.get(index).split(" ")[8].replace(".", "").equals("1");
			boolean isRight2 = lines.get(++index).split(" ")[10].replace(".", "").equals("right");
			String onto2 = lines.get(++index).split(" ")[8].replace(".", "");
			state.states.add(new StateItem(value2, isRight2, onto2));
			index +=2;
			states.put(stateName, state);
		}
		
		Map<Integer, Boolean> tape = new HashMap<>();
		index = 0;
		String currentState = "A";
		for (int i=0; i<steps; i++) {
			tape.putIfAbsent(index, false);
			boolean value = tape.get(index);
			int stateIndex = 0;
			if (value)
				stateIndex = 1;
			State state = states.get(currentState);
			tape.put(index, state.states.get(stateIndex).value);
			if (state.states.get(stateIndex).isRight) {
				index++;
			} else {
				index--;
			}
			currentState = state.states.get(stateIndex).onto;
		}
		
		return tape.values().stream().filter(b -> b).count();
	}
	
	private long problemB(List<String> lines) {
		
		return 0;
	}

	public static void main(String[] args) {
		System.out.println("TestA: " + new Day25().problemA(TEST_LINES));
		System.out.println("A: " + new Day25().problemA(LINES));
		System.out.println("TestB: " + new Day25().problemB(TEST_LINES));
		System.out.println("B: " + new Day25().problemB(LINES));
	}

}
