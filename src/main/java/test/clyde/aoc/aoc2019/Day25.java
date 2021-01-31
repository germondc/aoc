package test.clyde.aoc.aoc2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.clyde.aoc.utils.Utils;

public class Day25 {
	private static List<String> INPUT = Utils.readFile("2019/Day25.txt");

	private List<String> input;

	public Day25(List<String> input) {
		this.input = input;
	}

	private String getOutput(List<Long> output) {
		StringBuilder sb = new StringBuilder();
		for (long l : output) {
			sb.append((char) l);
		}
		return sb.toString();
	}
	
	private static final List<String> deadly = Arrays.asList(new String[] { "photons", "molten lava", "giant electromagnet", "escape pod", "infinite loop" });
	private static final List<String> heavy = Arrays.asList(new String[] { "spool of cat6", "jam", "shell" });

	public String getRoomName(String output) {
		Pattern roomPattern = Pattern.compile("== (.*) ==");
		Matcher m = roomPattern.matcher(output);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}
	
	private class Room {
		String name;
		Map<String, Integer> doors;
		List<String> items;

		public Room(String name, String output) {
			this.name = name;
			Pattern doorPattern = Pattern.compile("- (north|south|east|west)\\n");
			Matcher m = doorPattern.matcher(output);
			doors = new HashMap<>();
			while (m.find()) {
				doors.put(m.group(1), 0);
			}
			Pattern itemPattern = Pattern.compile("- (.*)\\n");
			int index = output.indexOf("Items here:");
			items = new ArrayList<>();
			if (index != -1) {
				m = itemPattern.matcher(output.substring(index));

				while (m.find()) {
					items.add(m.group(1));
				}
			}
		}
		
		

		@Override
		public String toString() {
			return String.format("Room [name=%s, doors=%s, items=%s]", name, doors, items);
		}

		public String getCommand() {
			for (int index = 0; index < items.size(); index++) {
				String i = items.get(index);
				if (deadly.contains(i) || heavy.contains(i))
					continue;
				items.remove(index);
				return "take " + i;
			}

			String door = doors.entrySet().stream().sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
					.map(e -> e.getKey()).findFirst().get();
			doors.put(door, doors.get(door) + 1);
			return door;
		}
	}

	public void problemA() {
		Map<String, Room> rooms = new HashMap<>();
		IntCode ic = new IntCode(input.get(0));
		List<Long> input = new ArrayList<>();
		String output = getOutput(ic.getOutputUntilInputEmpty());
		System.out.println(output);
		String roomName = getRoomName(output);
		Room currentRoom = new Room(roomName, output);
		rooms.put(currentRoom.name, currentRoom);
		
		boolean manual = false;
		Scanner sc= new Scanner(System.in);

		while (true) {
			String command;
			if (manual) {
				command = sc.nextLine();
			} else {
				command = currentRoom.getCommand();
			}
			for (char c : command.toCharArray()) {
				input.add((long) c);
			}
			input.add(10L);
			ic.addToInput(input);
			input.clear();
			output = getOutput(ic.getOutputUntilInputEmpty());
			System.out.println(output);
			if (output.contains("by typing")) {
				break;
			} else if (output.contains("==")) {
				roomName = getRoomName(output);
				if (roomName.contains("Pressure-Sensitive Floor")) {
					manual = true;
				}
				if (rooms.containsKey(roomName)) {
					currentRoom = rooms.get(roomName);
				} else {
					currentRoom = new Room(roomName, output);
					rooms.put(roomName, currentRoom);
				}
			} 
		}
	}

	public static void main(String[] args) {
		new Day25(INPUT).problemA();
	}
}