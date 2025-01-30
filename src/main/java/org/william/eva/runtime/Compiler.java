package org.william.eva.runtime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Compiler implements Runnable {
	private String name;
	private String extension;
	private Path path;
	
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
		String pathString = path.toString();
		File dir = new File(pathString.substring(0, pathString.lastIndexOf("\\") + 1));
		
		String[] javaCompileRegedit = new String[] {"cmd.exe", "/c", file + " " + name};
		
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec(javaCompileRegedit, null, dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
