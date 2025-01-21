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
	private static final String dot = ".";
	private Config config;
	
	public FileManager() {}
	
	/**
	 * Retrieves the name of the selected file, including its extension.
	 * 
	 * This method uses the JFileChooser to get the selected file and then extracts 
	 * its name, including the file extension (e.g., .java, .txt).
	 *
	 * @param jFile The JFileChooser instance used to select the file.
	 * @return The name of the selected file, including its extension (e.g., "example.txt").
	 */
	
	public String getFileName(JFileChooser jFile) {
		if (jFile != null) return jFile.getName(jFile.getSelectedFile());
		
		return null;
	}
	
	/**
	 * Retrieves the extension of the selected file
	 * 
	 * This method uses the JFileChooser to get the selected file and then extracts 
	 * its extension
	 *
	 * @param jFile The JFileChooser instance used to select the file.
	 * @return The extension of the selected file
	 */
	
	public String getFileExtension(JFileChooser jFile) {
		String fileExtension = getFileName(jFile);
		if (jFile != null) return fileExtension.substring(fileExtension.indexOf(dot));
		
		return null;
	}
	
	/**
	 * Returns the path of the file selected in the JFileChooser.
	 * 
	 * This method retrieves the selected file from the JFileChooser and converts it 
	 * into a Path object, which represents the location of the file in the file system.
	 *
	 * @param jFile The JFileChooser instance used to select the file.
	 * @return The Path representing the location of the selected file.
	 */
	
	public Path getFilePath(JFileChooser jFile) {
		if (jFile != null) return Paths.get(String.valueOf(jFile.getSelectedFile()));
		
		return null;
	}
	
	/**
	 * Returns the size of the file selected
	 * 
	 * This method returns the size of file in bytes
	 *
	 * @param jFile The JFileChooser instance used to select the file.
	 * @return The size of file or null if the file is not valid.
	 */
	
	public Long getFileSize(JFileChooser jFile) {
		Long result = null;
		
		try {
			result = Files.size(getFilePath(jFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Reads the content of a file selected through a JFileChooser and returns it as a String.
	 * 
	 * This method uses a BufferedReader to read the content of the file line by line and appends it to a 
	 * StringBuilder. After reading the entire file, the content is returned as a single String.
	 *
	 * @param jFile The JFileChooser instance used to select the file.
	 * @return A String containing the content of the selected file.
	 */
	
	public String writerTextPane(JFileChooser jFile) {
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		
		try (BufferedReader reader = Files.newBufferedReader(getFilePath(jFile))) {	
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append("\r");
			}
		} catch (IOException e) {
			config = new Config("./src/main/resources/config.properties");
			
			try {
				if (config.getProperties("fullerrorlog").equals("false")) {
					return new Terminal().logError(String.valueOf(e));
				} else {
					StringWriter stringWriter = new StringWriter();
					e.printStackTrace(new PrintWriter(stringWriter));
					
					return new Terminal().logError(String.valueOf(stringWriter));
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return String.valueOf(stringBuilder);
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
