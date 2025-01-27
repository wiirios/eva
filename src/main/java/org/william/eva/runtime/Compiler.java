package org.william.eva.runtime;

import java.nio.file.Path;

public class Compiler {
	private String name;
	private String extension;
	private Path path;
	
	public Compiler(String name, String extension, Path path) {
		this.name = name;
		this.extension = extension;
		this.path = path;
	}	

}
