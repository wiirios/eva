package org.william.eva.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

public class Frame {
	private int WIDTH = 300;
	private int HEIGHT = 300;
	private JFrame frame;

	public Frame() {
		initialize();
	}

	private void initialize() {
		FlatDarkLaf.setup();
		
		try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
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
