package org.william.eva.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Frame {
	private int WIDTH = 300;
	private int HEIGHT = 300;
	private JFrame frame;

	public Frame() {
		initialize();
	}

	private void initialize() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {				
					frame = new JFrame();
					frame.setSize(WIDTH, HEIGHT);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
