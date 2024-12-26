package org.william.eva.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Properties;

public class Config  {
	private String propertiesPath = "./src/main/resources/config.properties";
	
	/**
	 * Retrieves the theme configuration from a properties file.
	 * 
	 * This method reads a properties file and extracts
	 * the value associated with the "theme". The theme can either 
	 * be "light" or "dark", allowing the application to switch between these modes.
	 * 
	 * @return A String representing the theme specified in the properties file. 
	 *         If the key "theme" does not exist, it returns null.
	 * 
	 * @throws IOException If an error occurs while reading the properties file. 
	 *                     This can happen if the file is not found, cannot be opened, 
	 *                     or there is an issue during the load process.
	 */
	
	public String getTheme() throws IOException {
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(propertiesPath);
		
		properties.load(fileInputStream);
		String theme = properties.getProperty("theme");
		
		return theme;
	}
	
	public Integer getSystemTheme() throws IOException {
		Charset charset = Charset.forName("ISO-8859-1");
		StringBuilder stringBuilder = new StringBuilder();
		String osName = System.getProperty("os.name");
		String line = null;
		Integer result;
		
		if (osName.toLowerCase().contains("windows")) {
			String[] regedit = new String[] {"cmd.exe", "/c" , "reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize\" /v AppsUseLightTheme"};
			Process process = Runtime.getRuntime().exec(regedit);

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
			
			while((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			
			result = Integer.valueOf(stringBuilder.substring(stringBuilder.length() - 1));
		} else {
			return null;
		}
		
		return result;
	}
}
