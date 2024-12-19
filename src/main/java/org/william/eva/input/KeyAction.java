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

public class KeyAction {
	/**
	 * get file name (String)
	 * @param file to get the name
	 * @return the name of the file including its extension eg: .java, .txt
	 */
	
	public static String getFileName(JFileChooser jFile) {
		String fileName = jFile.getName(jFile.getSelectedFile());
		return fileName;
	}
	
	/**
	 * given a file to return the path
	 * 
	 * @param file to get the path
	 * @return the path of file
	 */
	
	public static Path getFilePath(JFileChooser jFile) {
		File fileSelected = jFile.getSelectedFile();
		Path filePath = Paths.get(String.valueOf(fileSelected));
		return filePath;
	}
	
	public static void writerTextPane(JFileChooser jFile ,JTextPane textPane) {
		try (BufferedReader reader = Files.newBufferedReader(KeyAction.getFilePath(jFile))) {
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
	
	/**
	 * 
	 * @param mntmNewMenuItem
	 * @param jFile
	 * @param frame
	 * @param textPane
	 */
	
	public void openDialog(JMenuItem mntmNewMenuItem, JFileChooser jFile, JFrame frame, JTextPane textPane) {
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogOpen = jFile.showOpenDialog(null);
				
				if (dialogOpen == JFileChooser.APPROVE_OPTION) {
					frame.setTitle(KeyAction.getFileName(jFile));
					KeyAction.writerTextPane(jFile, textPane);
					}
				}
		});
	}
	
	/**
	 * Working in
	 * @param mntmNewMenuItem_1
	 * @param jFile
	 * @param frame
	 */
	
	public void saveDialog(JMenuItem mntmNewMenuItem_1, JFileChooser jFile, JFrame frame) {
		mntmNewMenuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int t = jFile.showSaveDialog(null);
				
				if (t == JFileChooser.APPROVE_OPTION) {
					try (BufferedReader reader = Files.newBufferedReader(KeyAction.getFilePath(jFile))) {
						// ...
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					System.err.println();
				}
			}
		});
	}
}
