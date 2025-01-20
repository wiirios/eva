package org.william.eva.input;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Undo {	
	private JTextPane textPane;
    private Stack stack;
    // private Stack stackRemoved;
    private Document document;
    
    private boolean isControlDown;
	
	public Undo(JTextPane textPane) {
		this.textPane = textPane;
		this.stack = new Stack();
		// this.stackRemoved = new Stack();
		this.document = textPane.getDocument();
		this.isControlDown = false;
		
		textPane.addKeyListener(new KeyListener() {			
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if (!Character.isISOControl(c)) {
					stack.push(c);
					// stackRemoved.clear();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {				
				if (e.getModifiersEx() == InputEvent.CTRL_DOWN_MASK) isControlDown = true;

				if (isControlDown & e.getKeyCode() == KeyEvent.VK_Z) undo();
			}		
			
			@Override
			public void keyReleased(KeyEvent e) {
				isControlDown = false;
			}						
		});
		
	}
	
	public JTextPane getTextPane() {
		return textPane;
	}

	public void setTextPane(JTextPane textPane) {
		this.textPane = textPane;
	}
	
	/**
	 * Performs an undo operation by removing the last character from the stack 
	 * and the document if they are not empty.
	 */

	private void undo() {
		if (!stack.empty()) {
			try {
				stack.pop();

                int docLength = document.getLength();
                if (docLength > 0) {
                    document.remove(docLength - 1, 1);
                }
			} catch (BadLocationException e) {
				e.printStackTrace();
			}				
		}
	}
}