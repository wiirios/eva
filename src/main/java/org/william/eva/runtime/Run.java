package org.william.eva.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Run {
	private static String name;
	private static String extension;
	private static Path path;
	
	private static StringBuilder stringBuilder = new StringBuilder();
	private static String line = null;
	private Thread thread;
	
	public Run(String name, String extension, Path path) {
		this.name = name;
		this.extension = extension;
		this.path = path;
	}		
				
	private static class Runner implements Runnable {
		String pathString = path.toString();
		File dir = new File(pathString.substring(0, pathString.lastIndexOf("\\") + 1));
		
		@Override
		public void run() {
			switch (extension) {
			case ".java":
				String[] javaRunRegedit = new String[] {"cmd.exe", "/c", "java " + name};
				
				/* i will still create an option in the preferences to be able to customize the path to the evnp of the chosen language */
				// String[] envy = new String[] {"JAVA_HOME", "C:\\Program Files\\Java\\"};
				// String [] customEnvp;
									
				try {
					Runtime runtime = Runtime.getRuntime();
					Process process = runtime.exec(javaRunRegedit, null, dir);
					
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					while((line = bufferedReader.readLine()) != null) {
						stringBuilder.append(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}		
	}	
	
	public void runnable() throws IOException {	
		thread = new Thread(new Runner());
		thread.start();
	}
	
	public boolean isRun() {
		return thread.isAlive();
	}
	
	public void resetOutputState() {
		stringBuilder.setLength(0);
		line = null;
	}
	
	public String getOutput() {
		return stringBuilder.toString();
	}
	
	/*
	 * Working in
	 */
	
	public String getErrorOutput() {		
		return null;
	}
}
