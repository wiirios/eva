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
		String pathString = path.toString();
		File dir = new File(pathString.substring(0, pathString.lastIndexOf("\\") + 1));
		
		switch(extension) {
		case ".java":
			String[] javaCompileRegedit = new String[] {"cmd.exe", "/c", "javac " + name};
			
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(javaCompileRegedit, null, dir);
			} catch (IOException e) {
				e.printStackTrace();
			}			
			break;
		default:
			break;
		}
	}	

}
