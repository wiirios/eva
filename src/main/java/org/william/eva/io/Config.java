package org.william.eva.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config  {
	private String propertiesPath = "./src/main/resources/config.properties";
	
	public String getTheme() throws IOException {
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(propertiesPath);
		
		properties.load(fileInputStream);
		String theme = properties.getProperty("theme");
		
		return theme;
	}

}
