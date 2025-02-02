package org.william.eva.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Compiler implements Runnable {
	private String name;
	private String extension;
	private Path path;
	
	private boolean success = false;
	private StringBuilder stringBuilder;
	private String line;
	
	public Compiler(String name, String extension, Path path) {
		this.name = name;
		this.extension = extension;
		this.path = path;
	}

	@Override
	public void run() {
		switch (extension) {
		case ".java":
			process("javac");
			break;
		default:
			break;
		}
	}	

	private void process(String file) {
		stringBuilder = new StringBuilder();
		File dir = new File(path.toString().substring(0, path.toString().lastIndexOf("\\") + 1));
		String[] regedit = new String[] {"cmd.exe", "/c", String.join(" ", file, name)};
		
		try {
			Process process = Runtime.getRuntime().exec(regedit, null, dir);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			
			process.waitFor();
			boolean processSuccess = (process.exitValue() == 0);
			
			if (processSuccess) success = true ;
			
			while((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean compileSuccess() {		
		return success;
	}
	
	public void resetOutputState() {
		stringBuilder.setLength(0);
		line = null;
	}
	
	public String getOutput() {
		return stringBuilder.toString();
	}
}
