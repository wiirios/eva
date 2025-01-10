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
import org.william.eva.io.Config;
import org.william.eva.io.Message;
import org.william.eva.io.Terminal;
import org.william.eva.io.file.FileManager;

import javax.swing.JScrollPane;

public class Frame {
	private final int HEIGHT = 600;
	private final int WIDTH = HEIGHT * 16 / 9;
	private boolean isOpen = false;
	private JFrame jFrame;
	private Message isOpenEnum = Message.ISOPEN;
	private Message openFileEnum = Message.OPENFILE;
	private Message closeFileEnum = Message.CLOSEDFILE;
	private Message saveFileEnum = Message.SAVEFILE;
		
	public Frame() {
		initialize();
	}

	private void initialize() {
		JFileChooser jFile = new JFileChooser("c:");
		Config config = new Config();
		KeyAction btnAction = new KeyAction();
		Terminal terminal = new Terminal();
		FileManager fileManager = new FileManager();
		
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
					
					JTextPane textPane = new JTextPane();
					textPane.setVisible(false);
					textPane.setFont(new Font("Consolas", Font.PLAIN, 14));
										
					JTextPane terminalPane = new JTextPane();
					terminalPane.setEnabled(false);
					terminalPane.setEditable(false);
					terminalPane.setFont(new Font("Courier New", Font.PLAIN, 12));
					terminalPane.setBackground(new Color(36, 37, 43));
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

						// Ensure that after pressing Ctrl+O and closing the file dialog without selecting a file, 
						// the save action cannot be triggered unless a file is already open. Adjusted the `isOpen` 
						// check to prevent unintended save operations.
						
						@Override
						public void actionPerformed(ActionEvent e) {
							btnAction.openDialog(jFile, jFrame, textPane, terminalPane);
							
							if (textPane.isVisible()) isOpen = true;
						}
					});
					
					JMenuItem saveMenuItem = new JMenuItem("Save");
					fileMenu.add(saveMenuItem);
					saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
					saveMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (isOpen == false) {		
								terminalPane.setText(terminal.logError(isOpenEnum.getMessage()));
							} else {
								btnAction.saveDialog(jFile, textPane);
								terminalPane.setText(terminal.logFileAction(saveFileEnum.getMessage(), fileManager.getFileName(jFile)));
							}
						}
					});
					
					JMenu projectMenu = new JMenu("Project");
					menuBar.add(projectMenu);
					
					JMenuItem runMenuItem = new JMenuItem("Run");
					
					btnAction.runProject(runMenuItem);
					projectMenu.add(runMenuItem);
					
					JMenuItem buildMenuItem = new JMenuItem("Build");
					
					btnAction.buildProject(buildMenuItem);
					projectMenu.add(buildMenuItem);
					
					JMenu windowMenu = new JMenu("Window");
					menuBar.add(windowMenu);
					
					JMenuItem preferencesMenuItem = new JMenuItem("Preferences");
					preferencesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
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
