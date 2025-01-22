package org.william.eva.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

import org.william.eva.ui.Frame;

public class Run {
	private static String name;
	private static String extension;
	private static Path path;
	
	public Run(String name, String extension, Path path) {
		this.name = name;
		this.extension = extension;
		this.path = path;
	}
	
	private static class Runner implements Runnable {	
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String pathString = path.toString();
		File dir = new File(pathString.substring(0, pathString.lastIndexOf("\\") + 1));
		
		@Override
		public void run() {
			switch (extension) {
			case ".java":
				String[] javaRegedit = new String[] {"cmd.exe", "/c", "java " + name};
				
				/* i will still create an option in the preferences to be able to customize the path to the evnp of the chosen language */
				// String[] envy = new String[] {"JAVA_HOME", "C:\\Program Files\\Java\\"};
				// String [] customEnvp;
								
				try {
					Runtime runtime = Runtime.getRuntime();
					Process process = runtime.exec(javaRegedit, null, dir);
					
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					while((line = bufferedReader.readLine()) != null) {
						stringBuilder.append(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				stringBuilder.setLength(0);
				line = null;
				break;
			default:
				break;
			}
		}
		
	}
	
	public void runnable() throws IOException {	
		Thread thread = new Thread(new Runner());
		thread.start();
	}
	
	public boolean compile() {
		
		return false;
	}
	
	private static void getOutput(String output) {
	}
	
	public String getErrorOutput() {
		
		return null;
	}
}
