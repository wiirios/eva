package org.william.eva.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Runner implements Runnable {
	private String name;
	private String extension;
	private Path path;
	
	private StringBuilder stringBuilder;
	private String line = null;
	
	public Runner(String name, String extension, Path path) {
		this.name = name;
		this.extension = extension;
		this.path = path;
	}

	@Override
	public void run() {
		switch (extension) {
		case ".java":
			process("java");
			break;
		case ".py":
			process("python");
			break;
		case ".jl":
			process("julia");
		default:
			break;
		}
	}
	
	/**
     * Executes the specified file on the operating system.
     * 
     * @param file the runner command (e.g., "java").
     * @throws IOException If an input/output error occurs during process execution.
     * @throws InterruptedException If the process is interrupted while waiting for its execution.
     */
	
	private void process(String file) {
		stringBuilder = new StringBuilder();
		File dir = new File(path.toString().substring(0, path.toString().lastIndexOf("\\") + 1));
		
		String[] regedit = new String[] {"cmd.exe", "/c", String.join(" ", file, name)};
		
		try {
			Process process = Runtime.getRuntime().exec(regedit, null, dir);
			BufferedReader bufferedReader;
			
			process.waitFor();
			boolean success = (process.exitValue() == 0);
			
			bufferedReader = success ? new BufferedReader(new InputStreamReader(process.getInputStream())) : new BufferedReader(new InputStreamReader(process.getErrorStream()));

			while((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	/**
     * Resets the output state by clearing the captured error output.
     */

	public void resetOutputState() {
		stringBuilder.setLength(0);
		line = null;
	}
	
	/**
     * Returns the captured output from the run process.
     * 
     * @return the output as a string.
     */
	
	public String getOutput() {
		return stringBuilder.toString();
	}
}
