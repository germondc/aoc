//package test.clyde.aoc.utils;
//
//import java.util.Iterator;
//import java.util.List;
//
//public class Combinations<T> implements Iterable<List<T>> {
//	
//	private List<T> current;
//	private int size;
//	
//	public Combinations(List<T> collection, int size) {
//		this.size = size;
//		this.current = collection;
//	}
//
//	public List<T> getCurrent() {
//		return current;
//	}
//	
//	public int getSize() {
//		return size;
//	}
//
//	@Override
//	public Iterator<List<T>> iterator() {
//		return new CombinationsIterator<T>(this);
//	}
//
//}
