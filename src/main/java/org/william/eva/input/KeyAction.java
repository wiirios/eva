package org.william.eva.input;

import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import org.william.eva.io.Message;
import org.william.eva.io.Terminal;
import org.william.eva.io.file.FileEntity;
import org.william.eva.io.file.FileManager;
import org.william.eva.runner.Run;

public class KeyAction {
	private JFileChooser jFile;
	private JFrame frame;
	private JTextPane textPane;
	private JTextPane terminalPane;

	private static FileManager fileManager = new FileManager();
	private Terminal terminal = new Terminal();
	
	/* for now only support .java */
	private String[] extensionsList = new String[]{".java"};
	
	private FileEntity fileArchive;
	private FileEntity fileRunnable;
	
	public KeyAction(JFileChooser jFile, JFrame frame, JTextPane textPane, JTextPane terminalPane) {
		this.jFile = jFile;
		this.frame = frame;
		this.textPane = textPane;
		this.terminalPane = terminalPane;
	}

	/**
	 * Opens a file dialog and handles file selection.
	 * 
	 * This method displays a file chooser dialog when triggered. If a file is selected, the frame's title 
	 * is updated with the file name, and the file's content is loaded into the provided JTextPane. 
	 * The file chooser is configured to allow the user to browse and open files.
	 */
	
	public void openDialog() {
		int dialogOpen = this.jFile.showOpenDialog(null);
		
		if (dialogOpen == JFileChooser.APPROVE_OPTION) {
			fileArchive = new FileEntity(fileManager.getFileName(this.jFile), fileManager.getFileExtension(this.jFile), fileManager.getFilePath(this.jFile), fileManager.getFileSize(this.jFile));
			String fileText = fileManager.writerTextPane(this.jFile);
		
			if (fileText.substring(0, terminal.labelLength).equals(terminal.label)) {
				terminalPane.setText(fileText);
			} else {
				Message openFileEnum = Message.OPENFILE;
				
				frame.setTitle(fileArchive.getName());
				textPane.setVisible(true);
				textPane.setText(fileText);
				terminalPane.setText(terminal.logFileAction(openFileEnum.getMessage(), fileManager.getFileName(this.jFile)));
			}			
		}
	}
	
	/**
	 * Opens a save dialog to save the contents of a JTextPane to a file.
	 * 
	 * This method displays a save dialog using JFileChooser. If the user approves the save operation, 
	 * the contents of the JTextPane are written to the selected file, overwriting any existing content. 
	 * The file chooser allows the user to specify the location and name of the file to be saved.
	 */
	
	public void saveDialog() {
		int dialogSave = this.jFile.showSaveDialog(null);
		
		if (dialogSave == JFileChooser.APPROVE_OPTION) {
			fileManager.rewriteArchive(this.jFile, this.textPane);
		}
	}
	
	/**
	 * Working in
	 */
	
	public void runProject() {
	}
	
	/**
	 * Working in
	 */
	
	public void buildProject(JMenuItem mntmNewMenuItem) {
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
	}
}
