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
	 * Opens a file dialog and handles file selection.
	 * 
	 * This method displays a file chooser dialog when triggered. If a file is selected, the frame's title 
	 * is updated with the file name, and the file's content is loaded into the provided JTextPane. 
	 * The file chooser is configured to allow the user to browse and open files.
	 * 
	 * @param jFile The JFileChooser instance used to display the file dialog.
	 * @param frame The JFrame whose title will be updated with the selected file's name.
	 * @param textPane The JTextPane where the content of the selected file will be displayed.
	 */

	
	public void openDialog(JFileChooser jFile, JFrame frame, JTextPane textPane) {
		int dialogOpen = jFile.showOpenDialog(null);
		
		if (dialogOpen == JFileChooser.APPROVE_OPTION) {
			FileEntity fileArchive = new FileEntity(fileManager.getFileName(jFile), fileManager.getFileExtension(jFile), fileManager.getFilePath(jFile), fileManager.getFileSize(jFile));

			frame.setTitle(fileArchive.getName());
			fileManager.writerTextPane(jFile, textPane);
		}
	}
	
	/**
	 * Opens a save dialog to save the contents of a JTextPane to a file.
	 * 
	 * This method displays a save dialog using JFileChooser. If the user approves the save operation, 
	 * the contents of the JTextPane are written to the selected file, overwriting any existing content. 
	 * The file chooser allows the user to specify the location and name of the file to be saved.
	 * 
	 * @param jFile The JFileChooser instance used to display the save dialog.
	 * @param textPane The JTextPane whose contents will be saved to the selected file.
	 */
	
	public void saveDialog(JFileChooser jFile, JTextPane textPane) {
		int dialogSave = jFile.showSaveDialog(null);
		
		if (dialogSave == JFileChooser.APPROVE_OPTION) {
			fileManager.rewriteArchive(jFile, textPane);
		}
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
