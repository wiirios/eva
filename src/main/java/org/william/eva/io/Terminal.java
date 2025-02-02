package org.william.eva.io;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Terminal {
	private static LocalDateTime dateLocal;
	private static StringBuilder stringBuilder;
	
	public final String label = "-- Eva Crash --";
	public final int labelLength = label.length();
	
	public Terminal() {}
	
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
		stringBuilder = new StringBuilder();
		dateLocal = LocalDateTime.now(Clock.systemDefaultZone());
		String customPattern = dateLocal.format(DateTimeFormatter.ofPattern("H:m:s"));
		
		stringBuilder.append(String.join(": ", customPattern, message, fileName));
		
		return stringBuilder.toString();
	}
	
	/**
	 * Logs an error message to the terminal with a timestamp.
	 * 
	 * This method formats the current time and appends it to the provided error message,  
	 * creating a standardized log entry. The log entry is prefixed by a label and includes  
	 * the time the error occurred.
	 * 
	 * @param message The error message to be logged.
	 * @return A formatted string containing the label, timestamp, and the provided error message.
	 */
	
	public String logError(String message) {
		stringBuilder = new StringBuilder();
		dateLocal = LocalDateTime.now(Clock.systemDefaultZone());
		String customPattern = dateLocal.format(DateTimeFormatter.ofPattern("H:m:s"));
		
		stringBuilder.append(String.join(" ", label, customPattern, message));
		
		return stringBuilder.toString();
	}
}
