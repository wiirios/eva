package org.william.eva.input;

import java.util.ArrayList;

public class Stack {
	private ArrayList<Character> stack = new ArrayList<Character>();
	private int stackLength = 0;
	private static boolean isEmpty = true;
	
	public Stack() {}

	public void push(char c) {
		stack.add(c);
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
		for (char i:stack) {
			System.out.println(i);
		}
	}
	
	public char peek() {
		if (empty() == false) {
			return stack.get(stackLength-1);
		}
		return ' ';
	}
	
	public static boolean empty() {
		return isEmpty;
	}
	
	public int length() {
		return stackLength;
	}
}
