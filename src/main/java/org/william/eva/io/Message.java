package org.william.eva.io;

import java.awt.Color;

public enum Message {
	ISOPEN("It is not possible to save a file without first opening one", 1, Color.RED),
	OPENFILE("Open File", 2, Color.GREEN),
	CLOSEDFILE("Close File", 3, Color.RED),
	SAVEFILE("Save File", 4, Color.BLUE);
	
	private String message;
	private int id;
	private Color color;
	
	Message(String message, int id, Color color) {
		this.message = message;
		this.id = id;
		this.color = color;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
