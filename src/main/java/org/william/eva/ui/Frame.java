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

import javax.swing.BorderFactory;
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
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollBar;
import java.awt.Scrollbar;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Label;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SpringLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import org.william.eva.input.KeyAction;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

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
		
		FlatDarkLaf flatDark = new FlatDarkLaf();
		FlatLightLaf flatLight = new FlatLightLaf();

		try {
			UIManager.setLookAndFeel(flatDark);
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
					
					JTextPane textPane = new JTextPane();
					textPane.setFont(new Font("Consolas", Font.PLAIN, 14));
					frame.getContentPane().add(textPane, BorderLayout.CENTER);
					
					JPanel panel = new JPanel();
					frame.getContentPane().add(panel, BorderLayout.SOUTH);
					
					JMenu mnNewMenu = new JMenu("File");
					menuBar.add(mnNewMenu);
					
					JMenuItem mntmNewMenuItem = new JMenuItem("Open");
								
					btnAction.openDialog(mntmNewMenuItem, jFile, frame, textPane);
					mnNewMenu.add(mntmNewMenuItem);
					
					JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save");
					
					btnAction.saveDialog(mntmNewMenuItem_1, jFile, frame);
					mnNewMenu.add(mntmNewMenuItem_1);
					
					JMenu mnNewMenu_1 = new JMenu("Window");
					menuBar.add(mnNewMenu_1);
					
					JMenuItem mntmNewMenuItem_2 = new JMenuItem("Preferences");
					mnNewMenu_1.add(mntmNewMenuItem_2);
					
					JTextPane textPane_1 = new JTextPane();
					textPane_1.setEnabled(true);
					textPane_1.setEditable(false);
					textPane_1.setBackground(new Color(36, 37, 43));
					GroupLayout gl_panel = new GroupLayout(panel);
					gl_panel.setHorizontalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(textPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
					);
					gl_panel.setVerticalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(textPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					);
					panel.setLayout(gl_panel);
					
					JPanel panel_1 = new JPanel();
					frame.getContentPane().add(panel_1, BorderLayout.WEST);
					panel_1.setLayout(new BorderLayout(0, 0));
					
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2, BorderLayout.SOUTH);
					panel_2.setLayout(new BorderLayout(0, 0));
					
					JButton btnNewButton = new JButton("Terminal");
					btnNewButton.setEnabled(false);
					btnNewButton.setFont(new Font("BIZ UDPGothic", Font.PLAIN, 11));
					btnNewButton.setBackground(new Color(36, 37, 43));
					panel_2.add(btnNewButton, BorderLayout.CENTER);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
