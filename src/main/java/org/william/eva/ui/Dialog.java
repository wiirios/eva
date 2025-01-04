package org.william.eva.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class Dialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 400;
	private final int HEIGHT = 300;
	private JDialog jDialog;

	/**
	 * Create the application.
	 */
	public Dialog(JFrame parent) {
		super(parent);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		jDialog = new JDialog();
		jDialog.setSize(WIDTH, HEIGHT);
		jDialog.setModalityType(DEFAULT_MODALITY_TYPE);
		jDialog.setLocationRelativeTo(null);
		jDialog.setVisible(true);
	}

}
