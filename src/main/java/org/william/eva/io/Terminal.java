package org.william.eva.io;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Terminal {
	private LocalDateTime dateLocal;
	
	public String logFileAction(String message, String fileName) {
		StringBuilder stringBuilder = new StringBuilder();
		
		dateLocal = LocalDateTime.now(Clock.systemDefaultZone());
		String customPattern = dateLocal.format(DateTimeFormatter.ofPattern("H:m:s"));
		
		stringBuilder.append(customPattern + ": " + message + ": " + fileName);
				
		return String.valueOf(stringBuilder);
	}
}
