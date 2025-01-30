package org.william.eva.input;

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
import org.william.eva.runtime.Compiler;
import org.william.eva.runtime.Runner;

public class KeyAction {
	private JFileChooser jFile;
	private JFrame frame;
	private JTextPane textPane;
	private JTextPane terminalPane;

	private FileManager fileManager = new FileManager();
	private Terminal terminal = new Terminal();
	
	private FileEntity fileArchive;
	private FileEntity fileRunnable;
	private FileEntity fileCompiler;
	
	private Runner runner;
	private Compiler compiler;
	
	private Message openFileEnum = Message.OPENFILE;
	private Message UnsupportedEx = Message.UNSUPPORTEDEX;
	private Message CompilerUnsupportedEx = Message.NONCOMPILABLEEXT;
	private Message saveFileEnum = Message.SAVEFILE;
	
	Set<String> supportedExtensions = new HashSet<>(Arrays.asList(".java", ".c", ".py"));
	Set<String> compilableExtensions = new HashSet<>(Arrays.asList(".java", ".c"));
	
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
			terminalPane.setText(terminal.logFileAction(saveFileEnum.getMessage(), fileManager.getFileName(jFile)));
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
		runner = new Runner(fileRunnable.getName(), fileRunnable.getExtension(), fileRunnable.getPath());	
		Thread thread = new Thread(runner);
		
		terminalPane.setText(null);
				
		if (supportedExtensions.contains(fileRunnable.getExtension())) {
		
			/*
			 * There is an issue where the terminal output is not visible because the stringBuilder is being reset before the thread finishes executing.
			 * This causes a NullPointerException when trying to access the stringBuilder, as it is null at the time of access.
			 * It's unclear whether the thread is executing too quickly or if the code resetting the stringBuilder is running faster than the thread's execution, 
			 * causing the error that the stringBuilder is null.
			 * To mitigate this, I added a 5-second delay (Thread.sleep(5000)), which gives enough time for the thread to finish executing and the output to be processed.
			 * However, this is not an optimal solution, as it introduces an arbitrary delay and does not account for the actual execution time of the thread.
			 * I am currently working on a more robust solution to ensure the terminal output is updated only after the thread has finished processing.
			 */

			try {
				thread.start();
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			terminalPane.setText(runner.getOutput());
			runner.resetOutputState();
		} else {
			terminalPane.setText(terminal.logError(UnsupportedEx.getMessage()));
		}
	}
	
	public void compileProject() {
		fileCompiler = new FileEntity(fileManager.getFileName(this.jFile), fileManager.getFileExtension(this.jFile), fileManager.getFilePath(this.jFile), fileManager.getFileSize(this.jFile));
		compiler = new Compiler(fileCompiler.getName(), fileCompiler.getExtension(), fileCompiler.getPath());	
		
		String extension = fileCompiler.getExtension();
		
		if (supportedExtensions.contains(extension) && compilableExtensions.contains(extension)) {
			compiler.run();
		} else {
			terminalPane.setText(terminal.logError(CompilerUnsupportedEx.getMessage()));
		}
	}
}
