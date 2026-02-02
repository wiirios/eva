package org.william.eva.input;

import java.io.BufferedWriter;
import java.nio.file.Files;
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
import org.william.eva.ui.TabManager;

public class KeyAction {
	private JFileChooser jFile;
	private JFrame frame;
	private TabManager tabManager;
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
	private Message compilerSuccess = Message.COMPILESUCCESSFUL;
	
	Set<String> supportedExtensions = new HashSet<>(Arrays.asList(".java", ".c", ".py", ".jl", ".cpp"));
	Set<String> compilableExtensions = new HashSet<>(Arrays.asList(".java", ".c", ".cpp"));
	
	public KeyAction(JFileChooser jFile, JFrame frame, TabManager tabManager, JTextPane terminalPane) {
		this.jFile = jFile;
		this.frame = frame;
		this.tabManager = tabManager;
		this.terminalPane = terminalPane;	
		
		jFile.updateUI();
	}
	
	/**
	 * Opens a file dialog and handles file selection.
	 * 
	 * This method displays a file chooser dialog when triggered. If a file is selected, the frame's title 
	 * is updated with the file name, and the file's content is loaded into the provided JTextPane. 
	 * The file chooser is configured to allow the user to browse and open files.
	 */
	public boolean openDialog() {
		int dialogOpen = this.jFile.showOpenDialog(frame.getParent());
		
		if (dialogOpen == JFileChooser.APPROVE_OPTION) {
			String fileName = fileManager.getFileName(this.jFile);
			String fileExtension = fileManager.getFileExtension(this.jFile);
			String filePath = fileManager.getFilePath(this.jFile).toString();
			long fileSize = fileManager.getFileSize(this.jFile);
			
			fileArchive = new FileEntity(fileName, fileExtension, fileManager.getFilePath(this.jFile), fileSize);
			String fileText = fileManager.getFileText(this.jFile);
		
			if (fileText.substring(0, terminal.labelLength).equals(terminal.label)) {
				terminalPane.setText(fileText);
			} else {
				JTextPane textPane = tabManager.openTab(fileName, filePath, fileText);
				
				frame.setTitle("Eva - " + fileName);
				terminalPane.setText(terminal.logFileAction(openFileEnum.getMessage(), fileName));
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Opens a save dialog to save the contents of a JTextPane to a file.
	 * 
	 * This method displays a save dialog using JFileChooser. If the user approves the save operation, 
	 * the contents of the JTextPane are written to the selected file, overwriting any existing content. 
	 * The file chooser allows the user to specify the location and name of the file to be saved.
	 */
	public void saveDialog() {
		JTextPane currentTextPane = tabManager.getCurrentTextPane();
		if (currentTextPane == null) {
			return;
		}
		
		int dialogSave = this.jFile.showSaveDialog(null);
		
		if (dialogSave == JFileChooser.APPROVE_OPTION) {			
			try (BufferedWriter write = Files.newBufferedWriter(fileManager.getFilePath(jFile))) {
				write.write(currentTextPane.getText(), 0, currentTextPane.getText().length());
				
				// Mark tab as saved
				String filePath = fileManager.getFilePath(jFile).toString();
				tabManager.markAsSaved(filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			terminalPane.setText(terminal.logFileAction(saveFileEnum.getMessage(), fileManager.getFileName(jFile)));
		}
	}
	
	/**
	 * Saves the current file without opening a dialog
	 */
	public void saveCurrentFile() {
		JTextPane currentTextPane = tabManager.getCurrentTextPane();
		String currentFilePath = tabManager.getCurrentFilePath();
		
		if (currentTextPane == null || currentFilePath == null) {
			return;
		}
		
		try (BufferedWriter write = Files.newBufferedWriter(java.nio.file.Paths.get(currentFilePath))) {
			write.write(currentTextPane.getText(), 0, currentTextPane.getText().length());
			tabManager.markAsSaved(currentFilePath);
			
			String fileName = java.nio.file.Paths.get(currentFilePath).getFileName().toString();
			terminalPane.setText(terminal.logFileAction(saveFileEnum.getMessage(), fileName));
		} catch (Exception e) {
			e.printStackTrace();
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
		String currentFilePath = tabManager.getCurrentFilePath();
		if (currentFilePath == null) {
			terminalPane.setText(terminal.logError("No file is currently open"));
			return;
		}
		
		java.nio.file.Path path = java.nio.file.Paths.get(currentFilePath);
		String fileName = path.getFileName().toString();
		String extension = "";
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0) {
			extension = fileName.substring(lastDot);
		}
		
		fileRunnable = new FileEntity(fileName, extension, path, 0L);
		runner = new Runner(fileRunnable.getName(), fileRunnable.getExtension(), fileRunnable.getPath());	
		Thread thread = new Thread(runner);
		
		terminalPane.setText(null);
				
		if (supportedExtensions.contains(fileRunnable.getExtension())) {
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			terminalPane.setText(runner.getOutput());
			runner.resetOutputState();
		} else {
			terminalPane.setText(terminal.logError(UnsupportedEx.getMessage()));
		}
	}
	
	/**
	 * Compiles the project by running the appropriate compiler for the given file.
	 * 
	 * This method creates a FileEntity from the file details (name, extension, path, and size),
	 * and initializes a Compiler with the file's name, extension, and path. It then checks if
	 * the file's extension is supported and compilable. If so, the compiler is run. Otherwise, an error
	 * message is displayed in the terminal.
	 */
	public void compileProject() {
		String currentFilePath = tabManager.getCurrentFilePath();
		if (currentFilePath == null) {
			terminalPane.setText(terminal.logError("No file is currently open"));
			return;
		}
		
		java.nio.file.Path path = java.nio.file.Paths.get(currentFilePath);
		String fileName = path.getFileName().toString();
		String extension = "";
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0) {
			extension = fileName.substring(lastDot);
		}
		
		fileCompiler = new FileEntity(fileName, extension, path, 0L);
		compiler = new Compiler(fileCompiler.getName(), fileCompiler.getExtension(), fileCompiler.getPath());	
		Thread thread = new Thread(compiler);
		
		if (supportedExtensions.contains(extension) && compilableExtensions.contains(extension)) {
			thread.start();
			
			try {
				thread.join();				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			
			if (compiler.compileSuccess()) {
				terminalPane.setText(terminal.logFileAction(compilerSuccess.getMessage(), fileCompiler.getName()));
				compiler.resetOutputState();
			} else {
				terminalPane.setText(compiler.getOutput());
				compiler.resetOutputState();
			}
		} else {
			terminalPane.setText(terminal.logError(CompilerUnsupportedEx.getMessage()));
		}
	}
	
	public Set<String> getExtensions() {
		return supportedExtensions;
	}
	
	public String jFileExtension() {
		return fileManager.getFileExtension(this.jFile);
	}
}