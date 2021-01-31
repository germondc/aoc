package test.clyde.aoc.aoc2018;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import test.clyde.aoc.utils.Utils;

public class Day20 {
	private static List<Character> LINES = Utils.readChars("2018/Day20.txt");
	private static List<Character> TEST = Utils.readChars("2018/Day20a.txt");
	private static List<Character> TESTB = Utils.readChars("2018/Day20b.txt");

	private class Room extends Point {
		int id;
		int moveCounter;
		Map<Character, Room> rooms = new HashMap<>();

		public Room(int x, int y) {
			this();
			this.x = x;
			this.y = y;
		}

		public Room() {
			this.id = roomCounter++;
		}

		public Room moveToRoom(Character direction) {
			if (rooms.containsKey(direction))
				return rooms.get(direction);
			Character opposite;
			Room nextRoom = new Room(this.x, this.y);
			if (direction == 'N') {
				nextRoom.y -= 1;
				opposite = 'S';
			} else if (direction == 'S') {
				nextRoom.y += 1;
				opposite = 'N';
			} else if (direction == 'E') {
				nextRoom.x += 1;
				opposite = 'W';
			} else {
				nextRoom.x -= 1;
				opposite = 'E';
			}
			
			rooms.put(direction, nextRoom);
			nextRoom.rooms.put(opposite, this);
			return nextRoom;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
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
			Room other = (Room) obj;
			if (id != other.id)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return String.format("Room %s (%s, %s)", id, x, y);
		}
	}

	private int roomCounter = 0;

	private long problemA(List<Character> chars) throws Exception {
		int index = 1;
		Room start = new Room(0, 0);

		moveRooms(chars, index, start);

		long maxDoorCount = 0;
		start.moveCounter = 0;
		Deque<Room> queue = new ArrayDeque<>();
		queue.add(start);
		Set<Room> been = new HashSet<>();

		while (queue.size() > 0) {
			Room current = queue.removeLast();
			if (current.moveCounter > maxDoorCount)
				maxDoorCount = current.moveCounter;
			been.add(current);
			for (char direction : new char[] { 'N', 'S', 'E', 'W' }) {
				if (current.rooms.containsKey(direction)) {
					Room nextRoom = current.rooms.get(direction);
					if (been.contains(nextRoom)) {
						continue;
					}
					nextRoom.moveCounter = current.moveCounter + 1;
					queue.add(nextRoom);
				}
			}
		}
		
		long roomsMore1000 = been.stream().filter(r -> r.moveCounter >= 1000).count();
		System.out.println("B: " + roomsMore1000);

		return maxDoorCount;
	}

	private void countDoors(Room currentRoom, int currentCount) {

	}

	private int moveRooms(List<Character> chars, int index, Room currentRoom) {
		while (true) {
			char c = chars.get(index);
			if (c == '(') {
				while (chars.get(index) != ')') {
					index = moveRooms(chars, index + 1, currentRoom);
				}
			} else if (c == '|' || c == ')' || c == '$') {
				return index;
			} else {
				currentRoom = currentRoom.moveToRoom(c);
			}
			index++;
		}
	}

	private long problemB(List<Character> lines) throws Exception {
		return 0;
	}

	public static void main(String[] args) throws Exception {
//		System.out.println("TestA: " + new Day20().problemA(TEST));
		System.out.println("TestB: " + new Day20().problemA(TESTB));
		System.out.println("A: " + new Day20().problemA(LINES));
		System.out.println("B: " + new Day20().problemB(LINES));
	}

}
