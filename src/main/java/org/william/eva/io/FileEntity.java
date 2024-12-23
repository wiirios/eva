package org.william.eva.io;

import java.nio.file.Path;

public class FileEntity {
	String name;
	String extension;
	Path path;
	Long size;
	
	// constructor

	public FileEntity(String name, String extension, Path path, Long size){
		this.name = name;
		this.extension = extension;
		this.path = path;
		this.size = size;
	}
	
	// getters and setters
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
	
	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

}
