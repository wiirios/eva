package org.william.eva.input;

import java.util.ArrayList;

// PS: I am likely going to change many of these methods to private later. (i guess)
// This will take longer than I thought

public class Stack {
	private static ArrayList<Character> stack = new ArrayList<Character>();
	private static int stackLength = 0;
	private static int stackLimit = 50;
	private static boolean isEmpty = true;
	
	public Stack() {}
	
	/**
	 * Adds a character to the stack and updates the stack's state.
	 *
	 * @param c the character to add to the stack
	 */

	public void push(char c) {		
		if(length() >= stackLimit) {
			stack.remove(0);
			stackLength--;
		}
		
		stack.add(c);
		isEmpty = false;
		
		if (empty() == false) stackLength++;
	}	
	
	/**
	 * Removes the last character from the stack, if the stack is not empty.
	 */
	
	public void pop() {
		if (empty() == false) {
			stack.remove(stackLength-1);

			if (stackLength == 1) isEmpty = true;
			
			stackLength--;
		}
	}
	
	/**
	 * Prints all characters currently in the stack.
	 */
	
	public void getAll() {		
		for (char i:stack) {
			System.out.println(i);
		}
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
	
	private static boolean empty() {
		return isEmpty;
	}
	
	/**
	 * Returns the current number of elements in the stack.
	 *
	 * @return the length of the stack
	 */
	
	private static int length() {
		return stackLength;
	}
}
