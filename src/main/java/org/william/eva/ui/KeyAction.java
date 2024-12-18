package org.william.eva.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class KeyAction {
	
	public void openDialog(JMenuItem mntmNewMenuItem, JFileChooser jFile, JFrame frame) {
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogOpen = jFile.showOpenDialog(null);
				
				if (dialogOpen == JFileChooser.APPROVE_OPTION) {
					frame.setTitle(KeyAction.getFileName(jFile));
					
					try (BufferedReader reader = Files.newBufferedReader(KeyAction.getFilePath(jFile))){
						System.out.println(reader.readLine());
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
