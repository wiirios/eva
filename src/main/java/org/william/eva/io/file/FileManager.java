package org.william.eva.io.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JTextPane;

import org.william.eva.io.Config;
import org.william.eva.io.Terminal;

public class FileManager {	
	public FileManager() {}
	
	public String getFileName(JFileChooser jFile) {
		if (jFile != null) return jFile.getName(jFile.getSelectedFile());
		
		return null;
	}
	
	public String getFileExtension(JFileChooser jFile) {
		String fileExtension = getFileName(jFile);
		if (jFile != null) return fileExtension.substring(fileExtension.indexOf("."));
		
		return null;
	}
	
	public Path getFilePath(JFileChooser jFile) {
		if (jFile != null) return Paths.get(String.valueOf(jFile.getSelectedFile()));
		
		return null;
	}
	
	public Long getFileSize(JFileChooser jFile) {
		Long result = null;
		
		try {
			result = Files.size(getFilePath(jFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String getFileText(JFileChooser jFile) {
		StringBuilder stringBuilder = new StringBuilder();		
		Terminal terminal = new Terminal();
		Config config = new Config("./src/main/resources/config.properties");
		String line = null;
		
		try (BufferedReader reader = Files.newBufferedReader(getFilePath(jFile))) {	
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append("\r");
			}
		} catch (IOException e) {			
			try {
				if (config.getProperties("fullerrorlog").equals("false")) {
					return terminal.logError(String.valueOf(e));
				} else {
					StringWriter stringWriter = new StringWriter();
					e.printStackTrace(new PrintWriter(stringWriter));
					
					return terminal.logError(String.valueOf(stringWriter));
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * Rewrites the selected file with the text contained in a JTextPane.
	 * 
	 * This method retrieves the text from the provided JTextPane and overwrites the file
	 * selected via a JFileChooser. If an IO exception occurs during the writing process,
	 * it will be caught and the stack trace will be printed.
	 * 
	 * @param jFile JFileChooser representing the file to be rewritten.
	 * @param textPane JTextPane containing the text to be written to the file.
	 * 
	 * @throws NullPointerException if jFile or textPane is null.
	 * @throws IOException if an error occurs during file writing.
	 */
	
	public void rewriteArchive(JFileChooser jFile ,JTextPane textPane) {
		String fileText = textPane.getText();
		int fileLenght = fileText.length();
		
		try (BufferedWriter writer = Files.newBufferedWriter(getFilePath(jFile))) {
			writer.write(fileText, 0, fileLenght);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
