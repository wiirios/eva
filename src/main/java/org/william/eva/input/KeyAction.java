package org.william.eva.input;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextPane;

import org.william.eva.io.Message;
import org.william.eva.io.Terminal;
import org.william.eva.io.file.FileEntity;
import org.william.eva.io.file.FileManager;
import org.william.eva.runtime.Run;

public class KeyAction {
	private JFileChooser jFile;
	private JFrame frame;
	private JTextPane textPane;
	private JTextPane terminalPane;

	private FileManager fileManager = new FileManager();
	private Terminal terminal = new Terminal();
	
	private FileEntity fileArchive;
	private FileEntity fileRunnable;
	private Run run;
	
	private Message UnsupportedEx = Message.UNSUPPORTEDEX;
	
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
	 * Executes the project file if its extension matches one of the valid extensions 
	 * in extensionsList. 
	 * 
	 * This method creates a FileEntity object to represent the file and 
	 * uses a Run object to execute it. If the file extension is valid, 
	 * the runnable() method of Run is called. Any IOException 
	 * during execution is caught and logged.
	 */
	
	public void runProject() {		
		fileRunnable = new FileEntity(fileManager.getFileName(this.jFile), fileManager.getFileExtension(this.jFile), fileManager.getFilePath(this.jFile), fileManager.getFileSize(this.jFile));
		run = new Run(fileRunnable.getName(), fileRunnable.getExtension(), fileRunnable.getPath());	
		
		terminalPane.setText(null);
		
		Set<String> extensionsList = new HashSet<>(Arrays.asList(".java", ".c", ".py"));
		
		if (extensionsList.contains(fileRunnable.getExtension())) {
			try {
				run.runnable();
				terminalPane.setText(run.getOutput());
				run.resetOutputState();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			terminalPane.setText(terminal.logError(UnsupportedEx.getMessage()));
		}
	}
	
	/**
	 * Working in
	 */
	
	public void compileProject() {
	}
}
