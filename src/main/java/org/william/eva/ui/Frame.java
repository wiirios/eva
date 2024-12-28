package org.william.eva.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
import org.william.eva.io.Config;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.JSplitPane;

public class Frame {
	private final int WIDTH = 400;
	private final int HEIGHT = 300;
	private JFrame frame;

	public Frame() {
		initialize();
	}

	private void initialize() {
		JFileChooser jFile = new JFileChooser("c:");
		Config config = new Config();
		
		FlatDarkLaf.setup();
		FlatLightLaf.setup();

		try {
			if (config.getSystemTheme() == 0) {
				UIManager.setLookAndFeel(new FlatDarkLaf());
			} else {
				UIManager.setLookAndFeel(new FlatLightLaf());
			}
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
					
					JMenu mnNewMenu_2 = new JMenu("Project");
					menuBar.add(mnNewMenu_2);
					
					JMenuItem mntmNewMenuItem_3 = new JMenuItem("Run");
					mnNewMenu_2.add(mntmNewMenuItem_3);
					
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
					
					JScrollPane jScrollPane = new JScrollPane(textPane);
					frame.getContentPane().add(jScrollPane);
					
					JDialog jDialog = new JDialog(frame);
					jDialog.setTitle("Preferences");
					jDialog.setSize(WIDTH, HEIGHT);
								
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
