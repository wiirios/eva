package org.william.eva.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Runner {
	private String name;
	private String extension;
	private Path path;
	
	private StringBuilder stringBuilder = new StringBuilder();
	private String line = null;
	private Thread thread;
	
	public Runner(String name, String extension, Path path) {
		this.name = name;
		this.extension = extension;
		this.path = path;
	}		
	
	public void runnable() throws IOException {	
		//thread = new Thread(new Runner());
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
