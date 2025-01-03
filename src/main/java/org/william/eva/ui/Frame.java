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

import javax.swing.JDialog;
import javax.swing.JScrollPane;

public class Frame {
	private final int WIDTH = 400;
	private final int HEIGHT = 300;
	private boolean isOpen = false;
	private JFrame frame;
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
					frame = new JFrame();
					frame.setSize(WIDTH, HEIGHT);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setLocationRelativeTo(null);
					
					JMenuBar menuBar = new JMenuBar();
					frame.setJMenuBar(menuBar);
					
					JPanel panel = new JPanel();
					frame.getContentPane().add(panel, BorderLayout.SOUTH);
					
					JTextPane textPane = new JTextPane();
					textPane.setVisible(false);
					textPane.setFont(new Font("Consolas", Font.PLAIN, 14));
										
					JTextPane textPane_1 = new JTextPane();
					textPane_1.setEnabled(false);
					textPane_1.setEditable(false);
					textPane_1.setFont(new Font("Courier New", Font.PLAIN, 12));
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

					frame.getContentPane().add(textPane, BorderLayout.CENTER);
					
					JMenu mnNewMenu = new JMenu("File");
					menuBar.add(mnNewMenu);
					
					JMenuItem mntmNewMenuItem = new JMenuItem("Open");
					mnNewMenu.add(mntmNewMenuItem);
					mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
					mntmNewMenuItem.addActionListener(new ActionListener() {

						// Ensure that after pressing Ctrl+O and closing the file dialog without selecting a file, 
						// the save action cannot be triggered unless a file is already open. Adjusted the `isOpen` 
						// check to prevent unintended save operations.
						
						@Override
						public void actionPerformed(ActionEvent e) {
							btnAction.openDialog(jFile, frame, textPane, textPane_1);
							
							if (textPane.isVisible()) {
								isOpen = true;
							}
						}
					});
					
					JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save");
					mnNewMenu.add(mntmNewMenuItem_1);
					mntmNewMenuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
					mntmNewMenuItem_1.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (isOpen == false) {		
								textPane_1.setText(terminal.logError(isOpenEnum.getMessage()));
							} else {
								btnAction.saveDialog(jFile, textPane);
								textPane_1.setText(terminal.logFileAction(saveFileEnum.getMessage(), fileManager.getFileName(jFile)));
							}
						}
					});
					
					JMenu mnNewMenu_2 = new JMenu("Project");
					menuBar.add(mnNewMenu_2);
					
					JMenuItem mntmNewMenuItem_3 = new JMenuItem("Run");
					
					btnAction.runProject(mntmNewMenuItem_3);
					mnNewMenu_2.add(mntmNewMenuItem_3);
					
					JMenuItem mntmNewMenuItem_4 = new JMenuItem("Build");
					
					btnAction.buildProject(mntmNewMenuItem_4);
					mnNewMenu_2.add(mntmNewMenuItem_4);
					
					JMenu mnNewMenu_1 = new JMenu("Window");
					menuBar.add(mnNewMenu_1);
					
					JMenuItem mntmNewMenuItem_2 = new JMenuItem("Preferences");
					mnNewMenu_1.add(mntmNewMenuItem_2);
										
					JPanel panel_1 = new JPanel();
					frame.getContentPane().add(panel_1, BorderLayout.WEST);
					panel_1.setLayout(new BorderLayout(0, 0));
					
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2, BorderLayout.SOUTH);
					panel_2.setLayout(new BorderLayout(0, 0));
					
					JScrollPane jScrollPane = new JScrollPane(textPane);
					frame.getContentPane().add(jScrollPane);
					
					Component horizontalStrut = Box.createHorizontalStrut(55);
					jScrollPane.setRowHeaderView(horizontalStrut);
					
					JDialog jDialog = new JDialog(frame);
					jDialog.setTitle("Preferences");
					jDialog.setSize(WIDTH, HEIGHT);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
