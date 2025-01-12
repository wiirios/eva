package org.william.eva.input;

import java.util.ArrayList;

public class Stack<T> {
	private ArrayList<T> stack = new ArrayList<T>();
	private int stackLength = 0;
	private static boolean isEmpty = true;
	
	public Stack() {}

	public void push(T value) {
		stack.add(value);
		isEmpty = false;
		
		if (empty() == false) stackLength++;
	}	
	
	public void pop() {
		if (empty() == false) {
			stack.remove(stackLength-1);
			
			if (stackLength == 1) {
				isEmpty = true;
			}
			stackLength--;
		}
	}
	
	public void getAll() {		
		for (T i:stack) {
			System.out.println(i);
		}
	}
	
	public T peek() {
		if (empty() == false) {
			return stack.get(stackLength-1);
		}
		return null;
	}
	
	public static boolean empty() {
		return isEmpty;
	}
	
	public int length() {
		return stackLength;
	}
}
