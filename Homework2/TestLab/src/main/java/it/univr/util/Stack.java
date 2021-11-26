package it.univr.util;

public class Stack {

	private int[] storage;
	private int position;

	public Stack() {
		storage = new int[1024];
		position = 0;
	}

	public void push(int element) {
		storage[position] = element;
		position = position + 1;
	}

	public int pop() {
		position = position - 1;
		return storage[position];
	}

	public boolean isEmpty() {
		return position <= 0;
	}
}
