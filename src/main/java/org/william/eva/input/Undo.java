package org.william.eva.input;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextPane;
import javax.swing.text.Document;

import org.william.eva.ui.Frame;

public class Undo {	
	private JTextPane textPane;
	private Stack stack;
	private Document document;
	
	private boolean isControlDown;
	
	public Undo(JTextPane textPane) {
		this.textPane = textPane;
		this.stack = new Stack();
		this.document = textPane.getDocument();
		this.isControlDown = false;
		
		textPane.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if (!Character.isISOControl(c)) stack.push(c);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getModifiersEx() == InputEvent.CTRL_DOWN_MASK) isControlDown = true;
				
				if (isControlDown & e.getKeyCode() == KeyEvent.VK_Z) {
				}
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
	
	public boolean canUndo() {
		return Frame.hasTyped;
	}
}