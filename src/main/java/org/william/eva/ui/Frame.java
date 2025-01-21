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

import org.william.eva.input.KeyAction;
import org.william.eva.input.Undo;
import org.william.eva.io.Config;
import org.william.eva.io.Message;
import org.william.eva.io.Terminal;
import org.william.eva.io.file.FileManager;

import javax.swing.JScrollPane;

public class Frame {
	private static final int HEIGHT = 600;
	private static final int WIDTH = HEIGHT * 16 / 9;
	
	private JFrame jFrame;
	private JFileChooser jFile;
	private Config config;
	private KeyAction btnAction;
	private Terminal terminal;
	private FileManager fileManager;
	private Undo undo;
	
	private Message isOpenEnum = Message.ISOPEN;
	// private Message openFileEnum = Message.OPENFILE;
	// private Message closeFileEnum = Message.CLOSEDFILE;
	private Message saveFileEnum = Message.SAVEFILE;
		
	public Frame() {
		initialize();
	}

	private void initialize() {
		jFile = new JFileChooser("c:");
		config = new Config("./src/main/resources/config.properties");
		terminal = new Terminal();
		fileManager = new FileManager();

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
					
					JTextPane terminalPane = new JTextPane();
					terminalPane.setEnabled(false);
					terminalPane.setEditable(false);
					terminalPane.setFont(new Font("Courier New", Font.PLAIN, 12));
					terminalPane.setBackground(new Color(36, 37, 43));
					
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
					fileMenu.add(openMenuItem);
					openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
					openMenuItem.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							btnAction.openDialog();
						}
					});
					
					JMenuItem saveMenuItem = new JMenuItem("Save");
					fileMenu.add(saveMenuItem);
					saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
					saveMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							btnAction.saveDialog();
							terminalPane.setText(terminal.logFileAction(saveFileEnum.getMessage(), fileManager.getFileName(jFile)));
							
						}
					});
					
					btnAction = new KeyAction(jFile, jFrame, textPane, terminalPane);
					
					JMenu projectMenu = new JMenu("Project");
					menuBar.add(projectMenu);
					
					JMenuItem runMenuItem = new JMenuItem("Run");
					projectMenu.add(runMenuItem);
					runMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
					runMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// btnAction.runProject();
						}
						
					});
					
					JMenuItem buildMenuItem = new JMenuItem("Build");
					projectMenu.add(buildMenuItem);
					buildMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
					buildMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// btnAction.buildProject();
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