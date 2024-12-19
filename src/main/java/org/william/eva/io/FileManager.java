package org.william.eva.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JTextPane;

public class FileManager {
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
		String fileName = jFile.getName(jFile.getSelectedFile());
		return fileName;
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
	
	public static Path getFilePath(JFileChooser jFile) {
		File fileSelected = jFile.getSelectedFile();
		Path filePath = Paths.get(String.valueOf(fileSelected));
		return filePath;
	}
	
	/**
	 * Reads the content of a file selected through a JFileChooser and writes it to a JTextPane.
	 * 
	 * This method uses a BufferedReader to read the content of the file line by line and appends it to a 
	 * StringBuilder. After reading the entire file, the content is set to the JTextPane as text.
	 *
	 * @param jFile The JFileChooser instance used to select the file.
	 * @param textPane The JTextPane where the content of the file will be displayed.
	 */
	
	public void writerTextPane(JFileChooser jFile ,JTextPane textPane) {
		try (BufferedReader reader = Files.newBufferedReader(getFilePath(jFile))) {
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append("\r");
			}
			textPane.setText(String.valueOf(stringBuilder));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
