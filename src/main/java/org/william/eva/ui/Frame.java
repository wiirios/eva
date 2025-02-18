package org.william.eva.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
import java.awt.Component;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import org.william.eva.annotation.Incomplete;
import org.william.eva.input.KeyAction;
import org.william.eva.input.Undo;
import org.william.eva.io.Config;

import javax.swing.JScrollPane;

public class Frame {
	private static final int HEIGHT = 600;
	private static final int WIDTH = HEIGHT * 16 / 9;
	
	private JFrame jFrame;
	private JFileChooser jFile;
	private Config config;
	private KeyAction btnAction;
	private Undo undo;
	
	public JTextPane terminalPane;
		
	public Frame() {
		initialize();
	}

	@Incomplete
	private void initialize() {
		jFile = new JFileChooser("c:");
		config = new Config("./src/main/resources/config.properties");

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
					jFrame = new JFrame();
					jFrame.setSize(WIDTH, HEIGHT);
					jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					jFrame.setLocationRelativeTo(null);
					
					JMenuBar menuBar = new JMenuBar();
					jFrame.setJMenuBar(menuBar);
					
					JPanel panel = new JPanel();
					jFrame.getContentPane().add(panel, BorderLayout.SOUTH);
					
					terminalPane = new JTextPane();
					terminalPane.setFont(new Font("Courier New", Font.PLAIN, 12));
					boolean isDarkMode = UIManager.getLookAndFeel().getName().equals(FlatDarkLaf.NAME);

					terminalPane.setBackground(isDarkMode ? new Color(36, 37, 43) : new Color(249, 249, 249));
					terminalPane.setForeground(isDarkMode ? new Color(255, 255, 255) : new Color(36, 37, 43));
					
					JTextPane textPane = new JTextPane();
					textPane.setVisible(false);
					textPane.setFont(new Font("Consolas", Font.PLAIN, 14)); 
					
					// create undo
					undo = new Undo(textPane);
					
					GroupLayout gl_panel = new GroupLayout(panel);
					gl_panel.setHorizontalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(terminalPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
					);
					gl_panel.setVerticalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(terminalPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					);
					panel.setLayout(gl_panel);

					jFrame.getContentPane().add(textPane, BorderLayout.CENTER);
					
					JMenu fileMenu = new JMenu("File");
					menuBar.add(fileMenu);
					
					JMenuItem openMenuItem = new JMenuItem("Open");
					JMenuItem saveMenuItem = new JMenuItem("Save");
					JMenuItem runMenuItem = new JMenuItem("Run");
					JMenuItem compileMenuItem = new JMenuItem("Compile");
					saveMenuItem.setEnabled(false);
					runMenuItem.setEnabled(false);				
					compileMenuItem.setEnabled(false);
					
					btnAction = new KeyAction(jFile, jFrame, textPane, terminalPane);
					
					fileMenu.add(openMenuItem);
					openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
					openMenuItem.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							if (btnAction.openDialog()) {
								saveMenuItem.setEnabled(true);
								runMenuItem.setEnabled(true);
								compileMenuItem.setEnabled(true);	
							}
						}
					});
					
					// save
					fileMenu.add(saveMenuItem);
					saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
					saveMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							btnAction.saveDialog();
						}
					});
					
					JMenu projectMenu = new JMenu("Project");
					menuBar.add(projectMenu);
					
					projectMenu.add(runMenuItem);
					runMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
					runMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							btnAction.runProject();
						}						
					});
										
					projectMenu.add(compileMenuItem);
					compileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
					compileMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							btnAction.compileProject();
						}
						
					});
					
					JMenu windowMenu = new JMenu("Window");
					menuBar.add(windowMenu);
					
					JMenuItem preferencesMenuItem = new JMenuItem("Preferences");
					preferencesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK | KeyEvent.ALT_DOWN_MASK));
					preferencesMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							new Dialog(jFrame);					
						}
						
					});
					windowMenu.add(preferencesMenuItem);
															
					JPanel panel_1 = new JPanel();
					jFrame.getContentPane().add(panel_1, BorderLayout.WEST);
					panel_1.setLayout(new BorderLayout(0, 0));
					
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2, BorderLayout.SOUTH);
					panel_2.setLayout(new BorderLayout(0, 0));
					
					JScrollPane jScrollPane = new JScrollPane(textPane);
					jFrame.getContentPane().add(jScrollPane);
					
					Component horizontalStrut = Box.createHorizontalStrut(55);
					jScrollPane.setRowHeaderView(horizontalStrut);
					
					jFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
