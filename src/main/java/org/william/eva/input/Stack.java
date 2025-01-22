package org.william.eva.input;

import java.util.ArrayList;

public class Stack {
	private ArrayList<Character> stack = new ArrayList<Character>();
	private int stackLength = 0;
	/** both the stack that will contain the keys that the user types and the keys that the user deleted will have a limit of 50 characters (this is kind of obvious but whatever) */
	private static int stackLimit = 50;
	private boolean isEmpty = true;
	
	public Stack() {}
	
	/**
	 * Adds a character to the stack and updates the stack's state.
	 *
	 * @param c the character to add to the stack
	 */

	protected void push(char c) {	
		if (length() >= stackLimit) {
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
	
	protected void pop() {
		if (!empty()) {
			stack.remove(stackLength - 1);
			stackLength--;
			isEmpty = stackLength == 0;
		}
	}
	
	/**
	 * get all characters currently in the stack.
	 */
	
	protected String getAll() {	
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
	
	protected char peek() {
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
	
	protected boolean empty() {
		return isEmpty;
	}
	
	/**
	 * Returns the current number of elements in the stack.
	 *
	 * @return the length of the stack
	 */
	
	private int length() {
		return stackLength;
	}
	
	/**
	 * clear the stack
	 */
	
	protected void clear() {
		stack.clear();
		isEmpty = true;
		stackLength = 0;
	}
}
