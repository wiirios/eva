package org.william.eva.input;

import javax.swing.JTextPane;

public class Undo {
	private JTextPane textPane;
	
	public JTextPane getTextPane() {
		return textPane;
	}

	public void setTextPane(JTextPane textPane) {
		this.textPane = textPane;
	}
	
	public Undo(JTextPane textPane) {
		this.textPane = textPane;
	}
}