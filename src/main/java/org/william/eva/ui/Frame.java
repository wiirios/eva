package org.william.eva.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JScrollBar;
import java.awt.Scrollbar;

public class Frame {
	private final int WIDTH = 400;
	private final int HEIGHT = 300;
	private JFrame frame;

	public Frame() {
		initialize();
	}

	private void initialize() {
		JFileChooser jFile = new JFileChooser("c:");
		
		FlatDarkLaf.setup();
		FlatLightLaf.setup();
		
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
					
					KeyAction btnAction = new KeyAction();
					
					JMenuBar menuBar = new JMenuBar();
					frame.setJMenuBar(menuBar);
					
					JMenu mnNewMenu = new JMenu("File");
					menuBar.add(mnNewMenu);
					
					JMenuItem mntmNewMenuItem = new JMenuItem("Open");
								
					btnAction.openDialog(mntmNewMenuItem, jFile, frame);
					mnNewMenu.add(mntmNewMenuItem);
					
					JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save");
					
					btnAction.saveDialog(mntmNewMenuItem_1, jFile, frame);
					mnNewMenu.add(mntmNewMenuItem_1);
					
					JTextPane textPane = new JTextPane();
					textPane.setFont(new Font("Consolas", Font.PLAIN, 14));
					frame.getContentPane().add(textPane, BorderLayout.CENTER);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
