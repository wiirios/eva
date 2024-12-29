package org.william.eva.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import org.william.eva.io.file.FileEntity;
import org.william.eva.io.file.FileManager;

public class KeyAction {
	FileManager fileManager = new FileManager();
	
	/**
	 * Configures a menu item to open a file dialog and handle file selection.
	 * 
	 * This method sets up a keyboard shortcut (Ctrl+O) for the specified menu item. When the menu item is 
	 * clicked or the shortcut is used, a file chooser dialog is displayed. If a file is selected, the 
	 * frame's title is updated with the file name, and the file's content is loaded into the provided 
	 * JTextPane.
	 * 
	 * @param mntmNewMenuItem The JMenuItem to be configured for opening the file dialog.
	 * @param jFile The JFileChooser instance used to display the file dialog.
	 * @param frame The JFrame whose title will be updated with the selected file's name.
	 * @param textPane The JTextPane where the content of the selected file will be displayed.
	 */
	
	public void openDialog(JMenuItem mntmNewMenuItem, JFileChooser jFile, JFrame frame, JTextPane textPane) {
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogOpen = jFile.showOpenDialog(null);
				
				if (dialogOpen == JFileChooser.APPROVE_OPTION) {
					FileEntity fileArchive = new FileEntity(fileManager.getFileName(jFile), fileManager.getFileExtension(jFile), fileManager.getFilePath(jFile), fileManager.getFileSize(jFile));

					frame.setTitle(fileArchive.getName());
					fileManager.writerTextPane(jFile, textPane);
					}
				}
		});
	}
	
	/**
	 * Configures a save dialog for a JMenuItem to save the contents of a JTextPane to a file.
	 * 
	 * This method assigns a keyboard shortcut (Ctrl + S) to the JMenuItem and adds an action listener.
	 * When triggered, it opens a save dialog using JFileChooser. If the user approves the save,
	 * the selected file will be overwritten with the current text in the JTextPane.
	 * 
	 * @param mntmNewMenuItem JMenuItem to which the save dialog will be attached.
	 * @param jFile JFileChooser to be used for file selection.
	 * @param frame JFrame that serves as the parent for the dialog.
	 * @param textPane JTextPane whose contents will be saved.
	 */
	
	public void saveDialog(JMenuItem mntmNewMenuItem, JFileChooser jFile, JFrame frame, JTextPane textPane) {
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int dialogSave = jFile.showSaveDialog(null);
				
				if (dialogSave == JFileChooser.APPROVE_OPTION) {
					fileManager.rewriteArchive(jFile, textPane);
				}
			}
		});
	}
	
	/**
	 * Working in
	 * @param mntmNewMenuItem
	 */
	
	public void runProject(JMenuItem mntmNewMenuItem) {
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
	}
	
	/**
	 * Working in
	 * @param mntmNewMenuItem
	 */
	
	public void buildProject(JMenuItem mntmNewMenuItem) {
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
	}
}
