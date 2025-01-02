package org.william.eva.io;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTextPane;

public class Terminal {
	private LocalDateTime dateLocal;
	private StringBuilder stringBuilder;
	
	/**
	 * Logs an action performed on a file with a timestamp and returns the formatted log entry.
	 * This method captures the current system time and formats it in hours, minutes, and seconds.
	 * The log entry is built by appending the provided message and file name to the timestamp,
	 * and the resulting log string is returned.
	 * 
	 * @param message  A description of the action performed (e.g., "File opened", "File saved").
	 * @param fileName The name of the file associated with the action.
	 * @return A string representing the log entry in the format "H:m:s: message: fileName".
	 * 
	 * @throws NullPointerException if either {@code message} or {@code fileName} is null.
	 */
	
	public String logFileAction(String message, String fileName) {
		dateLocal = LocalDateTime.now(Clock.systemDefaultZone());
		String customPattern = dateLocal.format(DateTimeFormatter.ofPattern("H:m:s"));
		
		stringBuilder.append(customPattern + ": " + message + ": " + fileName);
		
		return String.valueOf(stringBuilder);
	}
}
