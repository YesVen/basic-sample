package com.optsd.basic.sample.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CreateYourOwnIterableStructure {

	public static class LinkedList<T> implements Iterable<T> {
		Node<T> head, current;

		private static class Node<T> {
			T data;
			Node<T> next;

			Node(T data) {
				this.data = data;
			}
		}

		public LinkedList(T data) {
			head = new Node<>(data);
		}

		public void add(T data) {
			Node<T> current = head;
			while (current.next != null)
				current = current.next;
			current.next = new Node<>(data);
		}

		@Override
		public Iterator<T> iterator() {
			return new LinkedListIterator();
		}

		private class LinkedListIterator implements Iterator<T> {
			Node<T> node = head;

			@Override
			public boolean hasNext() {
				return node != null;
			}

			@Override
			public T next() {
				if (!hasNext())
					throw new NoSuchElementException();
				Node<T> prevNode = node;
				node = node.next;
				return prevNode.data;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Removal logic not implemented.");
			}
		}
	}

	static class App {
		public static void main(String[] args) {
			LinkedList<Integer> list = new LinkedList<>(1);
			list.add(2);
			list.add(4);
			list.add(3);
			
			// Test #1
			System.out.println("using Iterator:");
			Iterator<Integer> itr = list.iterator();
			while (itr.hasNext()) {
				Integer i = itr.next();
				System.out.print(i + " ");
			}
			
			// Test #2
			System.out.println("\n\nusing for-each:");
			for (Integer data : list) {
				System.out.print(data + " ");
			}
		}
	}

}
