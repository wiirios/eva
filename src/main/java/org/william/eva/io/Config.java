package org.william.eva.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Config  {
	private String propertiesPath;
	
	 private PropertiesConfiguration propertiesConfiguration;
	
	public Config(String propertiesPath) {
		this.propertiesPath = propertiesPath;
	}
	
	/**
	 * Retrieves a specified configuration value from a properties file.
	 * 
	 * This method reads a properties file and extracts the value associated 
	 * with the provided key. It allows for dynamic retrieval of any property 
	 * by passing the desired key as a parameter.
	 * 
	 * @param key of the property to be retrieved from the properties file.
	 * @return A String representing the value associated with the specified key. 
	 *         If the key does not exist, it returns null.
	 * 
	 * @throws IOException If an error occurs while reading the properties file. 
	 *                     This can happen if the file is not found, cannot be opened, 
	 *                     or there is an issue during the load process.
	 */
	
	public String getProperties(String key) throws IOException {
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(propertiesPath);
		
		properties.load(fileInputStream);
		return properties.getProperty(key);
	}
	
	/**
	 * Retrieves the current system theme.
	 * 
	 * This method checks if the operating system is Windows, then executes a command to read the value from the Windows registry
	 * that indicates whether the light or dark theme is being used. If the operating system is not Windows, the method currently 
	 * does not support other platforms and returns null.
	 * 
	 * The returned value will be 1 (light theme) or 0 (dark theme) based on the system configuration.
	 * 
	 * @return An integer indicating the system theme (1 for light theme, 0 for dark theme), or null if the operating system is not Windows 
	 *         (support for other platforms like Linux is not implemented!).
	 * @throws IOException If an error occurs while reading the registry value or executing the command.
	 */
	
	public Integer getSystemTheme() throws IOException {
		Charset charset = Charset.forName(getProperties("charset"));
		StringBuilder stringBuilder = new StringBuilder();
		String osName = System.getProperty("os.name");
		String line = null;
		Integer result;
		
		if (osName.toLowerCase().contains("windows")) {
			String[] regedit = new String[] {"cmd.exe", "/c" , "reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize\" /v AppsUseLightTheme"};
			File dir = new File("C:/");
			
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(regedit, null, dir);

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
	
	public void rewriteProperties(String key, String newValue) throws IOException, ConfigurationException {
		propertiesConfiguration = new PropertiesConfiguration(propertiesPath);
		propertiesConfiguration.setProperty(key, newValue);
		propertiesConfiguration.save();		
		
	}
}
