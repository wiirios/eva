package org.william.eva.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class Dialog {
	private final int WIDTH = 400;
	private final int HEIGHT = 300;
	private JDialog jDialog;

	/**
	 * Create the application.
	 */
	public Dialog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		jDialog = new JDialog();
		jDialog.setSize(WIDTH, HEIGHT);
		jDialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		jDialog.setVisible(true);
	}

}
