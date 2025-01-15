package org.william.eva.input;

import java.util.ArrayList;

public class Stack {
	private ArrayList<Character> stack = new ArrayList<Character>();
	private int stackLength = 0;
	private int stackLimit = 50;
	private boolean isEmpty = true;
	
	public Stack() {}
	
	/**
	 * Adds a character to the stack and updates the stack's state.
	 *
	 * @param c the character to add to the stack
	 */

	public void push(char c) {	
		if (length() > stackLimit) {
			stack.remove(0);
			stackLength--;
		}
		
		stack.add(c);
		isEmpty = false;
		stackLength++;
	}
	
	/**
	 * Removes the last character from the stack, if the stack is not empty.
	 */
	
	public void pop() {
		if (!empty()) {
			stack.remove(stackLength-1);
			stackLength--;
			isEmpty = stackLength == 0;
		}
	}
	
	/**
	 * get all characters currently in the stack.
	 */
	
	public String getAll() {	
		StringBuilder ch = new StringBuilder();
		
		for (char i:stack) {
			ch.append(i);
		}
		
		return String.valueOf(ch);
	}
	
	/**
	 * Returns the last character in the stack without removing it.
	 *
	 * @return the last character in the stack, or a space character if the stack is empty
	 */
	
	public char peek() {
		if (empty() == false) {
			return stack.get(stackLength-1);
		}
		return ' ';
	}
	
	/**
	 * Checks if the stack is empty.
	 *
	 * @return true if the stack is empty, false otherwise
	 */
	
	public boolean empty() {
		return isEmpty;
	}
	
	/**
	 * Returns the current number of elements in the stack.
	 *
	 * @return the length of the stack
	 */
	
	public int length() {
		return stackLength;
	}
}
