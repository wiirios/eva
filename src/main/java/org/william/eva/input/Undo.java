package org.william.eva.input;

import javax.swing.JTextPane;

import org.william.eva.ui.Frame;

public class Undo {
	private JTextPane textPane;
	
	public Undo(JTextPane textPane) {
		this.textPane = textPane;
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